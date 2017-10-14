package net.fcpsschools._1685666._1.lab._4_recursion.eight_queen;

import com.sun.javafx.runtime.async.BackgroundExecutor;

import javax.swing.*;
import java.awt.*;

/**
 * @author ApolloZhu, Pd. 1
 * Learned how to run the solver on a background queue
 * so it will not block the UI (repaint).
 */
public class EightQueenGUI extends JPanel implements EightQueenSolver.MoveEventListener {

    private Diff diff;
    private Color universal = Color.GRAY;
    private int size = 4;
    private EightQueenChessBoardCanvas canvas;
    private boolean[][] board;
    private EightQueenSolver solver = new EightQueenSolver(size);

    public EightQueenGUI() {
        solver.addMoveEventListener(this);
        solver.addMoveEventListener(new EightQueenCLI());
        setLayout(new BorderLayout());
        add(canvas = new EightQueenChessBoardCanvas(size), BorderLayout.CENTER);

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
        solver.sleep(200);
    }

    @Override
    public void check(int queenCount, boolean[][] board, int r, int c) {
        diff = new Diff(r, c, Color.YELLOW);
        updateState(board);
    }

    @Override
    public void placed(int queenCount, boolean[][] board, int r, int c) {
        diff = new Diff(r, c, Color.GREEN);
        updateState(board);
    }

    @Override
    public void failed(int queenCount, boolean[][] board) {
        diff = null;
        universal = Color.RED;
        updateState(board);
        universal = Color.GRAY;
    }

    @Override
    public void restore(int queenCount, boolean[][] board, int rRestored, int cRestored) {
        diff = new Diff(rRestored, cRestored, Color.RED);
        updateState(board);
    }

    @Override
    public void found(boolean[][] board) {
        diff = null;
        universal = Color.GREEN;
        updateState(board);
        universal = Color.GRAY;
    }

    @Override
    public void ended() {
        diff = null;
        updateState(null);
    }

    private static class Diff {
        Color color;
        int r, c;

        Diff(int r, int c, Color color) {
            this.r = r;
            this.c = c;
            this.color = color;
        }
    }

    private class EightQueenChessBoardCanvas extends ChessBoardCanvas {
        EightQueenChessBoardCanvas(int size) {
            super(size);
        }

        @Override
        protected void paintPiece(Graphics g, int r, int c, int x, int y, int w, int h) {
            if (diff != null && diff.r == r && diff.c == c) g.setColor(diff.color);
            else if (board != null && board[r][c]) g.setColor(universal);
            else return;
            g.fillOval(x, y, w, h);
        }
    }
}
