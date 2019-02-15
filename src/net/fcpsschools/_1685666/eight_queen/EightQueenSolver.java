/*
MIT License

Copyright (c) 2017-2019 Apollo/Zhiyu Zhu/朱智语 <public-apollonian@outlook.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package net.fcpsschools._1685666.eight_queen;

import javax.swing.event.EventListenerList;
import java.util.EventListener;
import java.util.function.Consumer;

/**
 * @author Apollo/Zhiyu Zhu/朱智语, Pd. 1
 * Learned how to stop a thread, although unsafely.
 */
public class EightQueenSolver {
    private final int queenCount;
    private final EventListenerList list = new EventListenerList();
    private Thread thread;
    private long solutionCount;

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

    public static String legendX(int x) {
        String c = "" + (char) ('a' + x % 26);
        if (x < 26) return c;
        return legendX(x / 26 - 1) + c;
    }

    public Thread getThread() {
        return thread;
    }

    public void start() {
        solutionCount = 0;
        thread = Thread.currentThread();
        place(1, new boolean[queenCount][queenCount]);
        stop();
    }

    @SuppressWarnings("deprecation")
    public void stop() {
        forEachMoveEventListener(l -> l.ended(solutionCount));
        thread.stop();
    }

    private void place(int queen, boolean[][] board) {
        if (queen > queenCount) {
            solutionCount++;
            forEachMoveEventListener(l -> l.found(board, solutionCount));
        } else {
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

        void found(boolean[][] board, long SolutionCount);

        void ended(long solutionCount);
    }
}
