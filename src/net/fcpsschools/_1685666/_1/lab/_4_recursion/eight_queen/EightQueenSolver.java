package net.fcpsschools._1685666._1.lab._4_recursion.eight_queen;

import javax.swing.event.EventListenerList;
import java.util.EventListener;
import java.util.function.Consumer;

/**
 * @author ApolloZhu, Pd. 1
 * Learned how to stop a thread, although unsafely.
 */
public class EightQueenSolver {
    private final int queenCount;
    private final EventListenerList list = new EventListenerList();
    Thread thread;

    public EightQueenSolver(int size) {
        if (size < 1) throw new IllegalArgumentException("Wrong size: " + size);
        queenCount = size;
    }

    private static boolean get(boolean[][] board, int r, int c) {
        try {
            return board[r][c];
        } catch (Exception e) {
            return false;
        }
    }

    public Thread getThread() {
        return thread;
    }

    public void start() {
        thread = Thread.currentThread();
        place(1, new boolean[queenCount][queenCount]);
        stop();
    }

    @SuppressWarnings("deprecation")
    public void stop() {
        forEachMoveEventListener(MoveEventListener::ended);
        Thread.currentThread().stop();
    }

    private void place(int queen, boolean[][] board) {
        if (queen > queenCount)
            forEachMoveEventListener(l -> l.found(board));
        else {
            boolean hasPlace = false;
            for (int c = 0; c < queenCount; c++) {
                // Try
                int x = queen - 1, y = c;
                forEachMoveEventListener(l -> l.check(queen, board, x, y));
                if (isSafeToPlaceAt(board, x, y)) {
                    // Place
                    hasPlace = true;
                    board[x][y] = true;
                    forEachMoveEventListener(l -> l.placed(queen, board, x, y));
                    // Backtrack
                    place(queen + 1, board);
                    // Restore
                    board[x][y] = false;
                    forEachMoveEventListener(l -> l.restore(queen, board, x, y));
                }
            }
            if (!hasPlace) forEachMoveEventListener(l -> l.failed(queen, board));
        }
    }

    public boolean isSafeToPlaceAt(boolean[][] board, int r, int c) {
        for (int i = 0; i < board.length; i++) {
            if (board[r][i]) return false;
            if (board[i][c]) return false;
            // Too lazy to use a more efficient approach.
            if (get(board, r - i, c - i)) return false;
            if (get(board, r - i, c + i)) return false;
            if (get(board, r + i, c - i)) return false;
            if (get(board, r + i, c + i)) return false;
        }
        return true;
    }

    public void addMoveEventListener(MoveEventListener listener) {
        list.add(MoveEventListener.class, listener);
    }

    public void removeMoveEventListener(MoveEventListener listener) {
        list.remove(MoveEventListener.class, listener);
    }

    private void forEachMoveEventListener(Consumer<MoveEventListener> update) {
        for (MoveEventListener l : list.getListeners(MoveEventListener.class)) update.accept(l);
    }

    public interface MoveEventListener extends EventListener {
        void check(int queenCount, boolean[][] board, int r, int c);

        void placed(int queenCount, boolean[][] board, int r, int c);

        void failed(int queenCount, boolean[][] board);

        void restore(int queenCount, boolean[][] board, int rRestored, int cRestored);

        void found(boolean[][] board);

        void ended();
    }
}
