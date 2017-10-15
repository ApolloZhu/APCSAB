package net.fcpsschools._1685666._1.lab._4_recursion.eight_queen;

import com.sun.javafx.runtime.async.BackgroundExecutor;

import javax.swing.*;
import java.awt.*;
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

    public EightQueenGUI() {
        solver.addMoveEventListener(this);
        solver.addMoveEventListener(new EightQueenCLI());
        setLayout(new BorderLayout());
        add(canvas = new ChessBoardCanvas(size, painter), BorderLayout.CENTER);

        JPanel controls = new JPanel();
        add(controls, BorderLayout.SOUTH);
        JButton start = new JButton("Start");
        controls.add(start);
        start.addActionListener(ignored -> {
            start.setEnabled(false);
            BackgroundExecutor.getExecutor().execute(() -> {
                solver.start();
                start.setEnabled(true);
            });
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
            if (diff != null)
                solver.getThread().sleep((long) diff.displayIntervalUnit * 100);
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
