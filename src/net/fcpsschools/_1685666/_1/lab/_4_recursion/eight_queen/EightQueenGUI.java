package net.fcpsschools._1685666._1.lab._4_recursion.eight_queen;

import com.sun.javafx.runtime.async.BackgroundExecutor;
import layout.SpringUtilities;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;
import java.util.function.BiFunction;

/**
 * @author ApolloZhu, Pd. 1
 * Learned how to run the solver on a background queue
 * And playing around with threads.
 * so it will not block the UI (repaint).
 */
public class EightQueenGUI extends JPanel implements EightQueenSolver.MoveEventListener {
    private final JButton start = new JButton("Start");
    private final JLabel status = new JLabel("Waiting...");
    private final JSlider slider = new JSlider(JSlider.VERTICAL, 0, 200, 100);
    private final JButton pauseResume = new JButton("Pause");
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
    private int size;
    private EightQueenSolver solver;
    private double scaleFactor;


    @SuppressWarnings("deprecation")
    public EightQueenGUI() {
        setLayout(new BorderLayout());
        // Status
        add(status, BorderLayout.SOUTH);
        status.setHorizontalAlignment(SwingConstants.CENTER);
        // Chess Board Canvas
        setSize(size = 8);
        // Controls
        JPanel controls = new JPanel();
        add(controls, BorderLayout.EAST);
        controls.add(start);
        controls.add(pauseResume);
        JLabel tfLabel = new JLabel("Size: ");
        controls.add(tfLabel);
        JTextField tf = new JTextField(String.valueOf(size));
        controls.add(tf);
        controls.add(slider);

        // Controls
        controls.setLayout(new SpringLayout());
        controls.setAlignmentX(SwingConstants.CENTER);
        // Start
        start.addActionListener(ignored -> {
            BackgroundExecutor.getExecutor().execute(() -> {
                if (start.getText().equals("Start")) {
                    start.setText("Terminate");
                    try {
                        setSize(Integer.parseUnsignedInt(tf.getText()));
                    } catch (Exception e) {
                    }
                    solver = new EightQueenSolver(size);
                    solver.addMoveEventListener(this);
                    pauseResume.setEnabled(true);
                    solver.start();
                } else {
                    solver.stop();
                }
            });
        });
        // Pause Resume
        pauseResume.setEnabled(false);
        pauseResume.addActionListener(l -> {
            if (pauseResume.getText().equals("Pause")) {
                pause();
            } else {
                resume();
            }
        });
        // Text Field
        tf.addActionListener(ignored -> {
            try {
                setSize(Integer.parseUnsignedInt(tf.getText()));
                ended(-1);
            } catch (Exception e) {
            }
        });
        // Speed Control Slider
        Hashtable<Integer, JLabel> sliderLabels = new Hashtable<>();
        sliderLabels.put(0, new JLabel("Pause"));
        sliderLabels.put(10, new JLabel("Slow"));
        sliderLabels.put(100, new JLabel("Normal"));
        sliderLabels.put(190, new JLabel("Fast"));
        sliderLabels.put(200, new JLabel("Non Stop"));
        slider.setLabelTable(sliderLabels);
        slider.setPaintLabels(true);

        scaleFactor = slider.getValue();
        slider.addChangeListener(ignored -> {
            double oldValue = scaleFactor;
            double newValue = slider.getValue();
            scaleFactor = newValue;
            if (oldValue == 0 && newValue != 0) {
                resume();
            }
            if (newValue == 0 && oldValue != 0) {
                pause();
            }
        });

        SpringUtilities.makeCompactGrid(controls, 5, 1, 0, 8, 0, 8);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Whatever Queen Question");
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        EightQueenGUI gui = new EightQueenGUI();
        frame.setContentPane(gui);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    protected final String loc(int r, int c) {
        return (size - r) + EightQueenSolver.legendX(c);
    }

    public void setSize(int size) {
        this.size = size;
        if (canvas != null) remove(canvas);
        add(canvas = new ChessBoardCanvas(size, painter), BorderLayout.CENTER);
        SwingUtilities.updateComponentTreeUI(this);
    }

    private void updateState(boolean[][] board) {
        this.board = board;
        canvas.repaint();
        try {
            if (scaleFactor == 200 || diff == null) return; // Non stop
            double percentage = Math.max(scaleFactor / 100, 0.1);
            long interval = (long) (diff.displayIntervalUnit * 100 / percentage);
            solver.getThread().sleep(interval);
        } catch (Exception e) {
        }
    }

    @Override
    public void check(int queenCount, boolean[][] board, int r, int c) {
        diff = new Diff(Qualifier.make(r, c), 1, PiecePainter.makeCirclePainter(Color.YELLOW));
        status.setText("Checking " + loc(r, c));
        updateState(board);
    }

    @Override

    public void placed(int queenCount, boolean[][] board, int r, int c) {
        diff = new Diff(Qualifier.make(r, c), 1, PiecePainter.makeCirclePainter(Color.GREEN));
        status.setText("Place at " + loc(r, c));
        updateState(board);
    }

    @Override
    public void failed(int queenCount, boolean[][] board) {
        diff = new Diff(allPieces, 2, PiecePainter.makeCirclePainter(Color.RED));
        status.setText("Not A Solution");
        updateState(board);
    }

    @Override
    public void restore(int queenCount, boolean[][] board, int rRestored, int cRestored) {
        diff = new Diff(Qualifier.make(rRestored, cRestored), 1, PiecePainter.makeCirclePainter(Color.RED));
        status.setText("Retract " + loc(rRestored, cRestored));
        updateState(board);
    }

    @Override
    public void found(boolean[][] board, long solutionCount) {
        diff = new Diff(allPieces, 5, PiecePainter.makeCirclePainter(Color.GREEN));
        status.setText("Found Solution #" + solutionCount);
        updateState(board);
        pause();
    }

    @Override
    public void ended(long solutionCount) {
        pauseResume.setEnabled(false);
        solver.removeMoveEventListener(this);
        diff = null;
        if (solutionCount >= 0)
            status.setText("Solution Count: " + solutionCount);
        else
            status.setText("Waiting...");
        updateState(null);
        pauseResume.setText("Pause");
        start.setText("Start");
    }

    @SuppressWarnings("deprecation")
    private void pause() {
        pauseResume.setText("Resume");
        solver.thread.suspend();
    }

    @SuppressWarnings("deprecation")
    private void resume() {
        if (scaleFactor == 0) {
            scaleFactor = 100;
            slider.setValue(100);
        }
        pauseResume.setText("Pause");
        solver.thread.resume();
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
