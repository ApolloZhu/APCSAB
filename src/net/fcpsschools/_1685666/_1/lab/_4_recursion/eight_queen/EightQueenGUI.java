package net.fcpsschools._1685666._1.lab._4_recursion.eight_queen;

import com.sun.javafx.runtime.async.BackgroundExecutor;

import javax.swing.*;
import java.awt.*;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.function.BiFunction;

/**
 * @author ApolloZhu, Pd. 1
 * Learned how to run the solver on a background queue
 * so it will not block the UI (repaint).
 */
public class EightQueenGUI extends JPanel implements EightQueenSolver.MoveEventListener {
    private boolean[][] board;
    private final Qualifier allPieces = (x, y) -> board != null && board[x][y];
    private final Diff universal = new Diff(allPieces, 1, PiecePainter.makeCirclePainter(Color.GRAY));
    private Diff diff;
    private final PiecePainter painter = new PiecePainter() {
        @Override
        public void paintPiece(Graphics g, int r, int c, int x, int y, int w, int h) {
            if (diff != null && diff.qualifier.apply(r, c))
                diff.painter.paintPiece(g, r, c, x, y, w, h);
            else if (universal.qualifier.apply(r, c))
                universal.painter.paintPiece(g, r, c, x, y, w, h);
        }
    };
    private ChessBoardCanvas canvas;
    private int size = 4;
    private EightQueenSolver solver = new EightQueenSolver(size);
    private double scaleFactor;
    private JButton start = new JButton("Start");

    @SuppressWarnings("deprecation")
    public EightQueenGUI() {
        solver.addMoveEventListener(new EightQueenCLI());
        setLayout(new BorderLayout());
        add(canvas = new ChessBoardCanvas(size, painter), BorderLayout.CENTER);

        JPanel controls = new JPanel();
        add(controls, BorderLayout.EAST);
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
        controls.add(start);
        start.addActionListener(ignored -> {
            BackgroundExecutor.getExecutor().execute(() -> {
                if (start.getText().equals("Start")) {
                    start.setText("Stop");
                    solver.addMoveEventListener(this);
                    solver.start();
                } else {
                    solver.stop();
                }
            });
        });

        // Speed Control Slider
        JSlider slider = new JSlider(JSlider.VERTICAL, 0, 200, 100);
        controls.add(slider);

        Dictionary<Integer, JLabel> sliderLabels = new Hashtable<>();
        sliderLabels.put(0, new JLabel("Pause"));
        sliderLabels.put(10, new JLabel("Slow"));
        sliderLabels.put(100, new JLabel("Normal"));
        sliderLabels.put(200, new JLabel("Fast"));
        slider.setLabelTable(sliderLabels);
        slider.setPaintLabels(true);

        scaleFactor = slider.getValue();
        slider.addChangeListener(ignored -> {
            double oldValue = scaleFactor;
            double newValue = slider.getValue();
            if (oldValue == 0 && newValue != 0)
                solver.getThread().resume();
            if (newValue == 0 && oldValue != 0)
                solver.getThread().suspend();
            scaleFactor = newValue;
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Whatever Queen Question");
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        EightQueenGUI gui = new EightQueenGUI();
        frame.setContentPane(gui);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    static String loc(int size, int r, int c) {
        return (size - r) + "" + (char) (c + 'A');
    }

    private void updateState(boolean[][] board) {
        this.board = board;
        canvas.repaint();
        try {
            if (diff != null) {
                double percentage = Math.max(scaleFactor / 100, 0.1);
                long interval = (long) (diff.displayIntervalUnit * 100 / percentage);
                solver.getThread().sleep(interval);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void check(int queenCount, boolean[][] board, int r, int c) {
        diff = new Diff(Qualifier.make(r, c), 1, PiecePainter.makeCirclePainter(Color.YELLOW));
        updateState(board);
    }

    @Override
    public void placed(int queenCount, boolean[][] board, int r, int c) {
        diff = new Diff(Qualifier.make(r, c), 1, PiecePainter.makeCirclePainter(Color.GREEN));
        updateState(board);
    }

    @Override
    public void failed(int queenCount, boolean[][] board) {
        diff = new Diff(allPieces, 2, PiecePainter.makeCirclePainter(Color.RED));
        updateState(board);
    }

    @Override
    public void restore(int queenCount, boolean[][] board, int rRestored, int cRestored) {
        diff = new Diff(Qualifier.make(rRestored, cRestored), 1, PiecePainter.makeCirclePainter(Color.RED));
        updateState(board);
    }

    @Override
    public void found(boolean[][] board) {
        diff = new Diff(allPieces, 5, PiecePainter.makeCirclePainter(Color.GREEN));
        updateState(board);
    }

    @Override
    public void ended() {
        diff = null;
        updateState(null);
        solver.removeMoveEventListener(this);
        start.setText("Start");
    }

    private interface Qualifier extends BiFunction<Integer, Integer, Boolean> {

        static Qualifier make(int r, int c) {
            return (x, y) -> r == x && c == y;
        }
    }

    private static class Diff {
        PiecePainter painter;
        double displayIntervalUnit;
        Qualifier qualifier;

        Diff(Qualifier qualifier, double unit, PiecePainter painter) {
            this.qualifier = qualifier;
            this.painter = painter;
            this.displayIntervalUnit = unit;
        }
    }
}
