package net.fcpsschools._1685666.eight_queen;

import net.fcpsschools._1685666.PlaybackPanel;

import javax.swing.*;
import java.awt.*;
import java.util.function.BiFunction;

/**
 * @author ApolloZhu, Pd. 1
 * Learned how to run the solver on a background queue
 * And playing around with threads.
 * so it will not block the UI (repaint).
 */
public class EightQueenGUI extends PlaybackPanel
        implements EightQueenSolver.MoveEventListener {
    private static final JFrame frame = new JFrame("8 Queen Problem");
    private final JLabel status = new JLabel("Waiting...");
    private boolean[][] board;
    private final Qualifier allPieces = (x, y) -> board != null && board[x][y];
    private final Diff universal = new Diff(allPieces, 1, PiecePainter.makeQueenPainter(Color.GRAY));
    private Diff diff;
    private ChessBoardCanvas canvas;
    private int size;
    private final JTextField tf = new JTextField(String.valueOf(size));
    private EightQueenSolver solver;

    public EightQueenGUI() {
        // Status
        add(status, BorderLayout.SOUTH);
        status.setHorizontalAlignment(SwingConstants.CENTER);

        // Controls
        JPanel controls = new JPanel();
        add(controls, BorderLayout.NORTH);
        JLabel tfLabel = new JLabel("Size: ");
        controls.add(tfLabel);
        controls.add(tf);
        // Text Field
        tf.addActionListener(ignored -> {
            try {
                int size = Integer.parseUnsignedInt(tf.getText());
                ended(-1);
                setSize(size);
            } catch (Exception e) {
                tf.setText(String.valueOf(size));
            }
        });
    }

    public static void main(String[] args) {
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        EightQueenGUI gui = new EightQueenGUI();
        frame.setContentPane(gui);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    protected Component getCenterComponent() {
        if (canvas != null) return canvas;
        // Chess Board Canvas
        PiecePainter painter = (g, r, c, x, y, w, h) -> {
            if (diff != null && diff.qualifier != null && diff.qualifier.apply(r, c))
                diff.painter.paintPiece(g, r, c, x, y, w, h);
            else if (universal.qualifier.apply(r, c))
                universal.painter.paintPiece(g, r, c, x, y, w, h);
        };
        return canvas = new ChessBoardCanvas(size = 8, painter);
    }

    @Override
    protected void start() {
        try {
            setSize(Integer.parseUnsignedInt(tf.getText()));
        } catch (Exception e) {
            e.printStackTrace();
            tf.setText(String.valueOf(size));
        }
        solver = new EightQueenSolver(size);
        solver.addMoveEventListener(this);
        solver.start();
    }

    protected final String loc(int r, int c) {
        return EightQueenSolver.legendX(r) + (size - c);
    }

    public void setSize(int size) {
        if (size == this.size) return;
        this.size = size;
        canvas.setSize(size);
        frame.setTitle(size + " Queen Problem");
    }

    private void updateState(boolean[][] board) {
        this.board = board;
        if (diff != null) sleep((int) diff.displayIntervalUnit);
    }

    @Override
    public void check(int queenCount, boolean[][] board, int r, int c) {
        diff = new Diff(Qualifier.make(r, c), 1, PiecePainter.makeQueenPainter(Color.YELLOW));
        status.setText("Checking " + loc(r, c));
        updateState(board);
    }

    @Override
    public void placed(int queenCount, boolean[][] board, int r, int c) {
        diff = new Diff(Qualifier.make(r, c), 1, PiecePainter.makeQueenPainter(Color.GREEN));
        status.setText("Place at " + loc(r, c));
        updateState(board);
    }

    @Override
    public void failed(int queenCount, boolean[][] board) {
        diff = new Diff(allPieces, 2, PiecePainter.makeQueenPainter(Color.RED));
        status.setText("Not A Solution");
        updateState(board);
    }

    @Override
    public void restore(int queenCount, boolean[][] board, int rRestored, int cRestored) {
        diff = new Diff(Qualifier.make(rRestored, cRestored), 1, PiecePainter.makeQueenPainter(Color.RED));
        status.setText("Retract " + loc(rRestored, cRestored));
        updateState(board);
    }

    @Override
    public void found(boolean[][] board, long solutionCount) {
        diff = new Diff(allPieces, 5, PiecePainter.makeQueenPainter(Color.GREEN));
        status.setText("Found Solution #" + solutionCount);
        updateState(board);
        pause();
    }

    @Override
    protected void terminate() {
        super.terminate();
        if (solver != null) solver.stop();
        diff = null;
        updateState(null);
    }

    @Override
    public void ended(long solutionCount) {
        status.setText(solutionCount < 0 ? "Waiting..."
                : "Solution Count: " + solutionCount);
        if (solver != null)
            solver.removeMoveEventListener(this);
        terminate();
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
            this.displayIntervalUnit = unit;
            this.painter = painter;
        }
    }
}
