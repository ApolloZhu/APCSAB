package net.fcpsschools._1685666._1.lab._4_recursion.maze;

import javax.swing.event.EventListenerList;
import java.util.EventListener;
import java.util.function.Consumer;

/**
 * @author ApolloZhu, Pd. 1
 */
public class MazeSolver {
    private final EventListenerList list = new EventListenerList();
    private MazeCoder.Block[][] grid;
    private Thread thread;

    public Thread getThread() {
        return thread;
    }

    public boolean start(MazeCoder.Block[][] input, int r, int c, int tR, int tC) {
        grid = input;
        thread = Thread.currentThread();
        boolean hasPath = findAnExitHelper(r, c, tR, tC, "", null);
        forEachListener(l -> l.ended(hasPath, grid));
        return hasPath;
    }

    private boolean findAnExitHelper(int x, int y, int tX, int tY, String path, Direction direction) {
        if (direction != null) {
            Loc back = direction.reverse(x, y);
            forEachListener(l -> l.tryout(back.r, back.c, direction, path, grid));
        } else forEachListener(l -> l.started(x, y, tX, tY, grid));

        if (x < 0 || y < 0 || x >= grid.length || y >= grid[x].length
                || grid[x][y] != MazeCoder.Block.EMPTY) return false;

        String newPath = path + "[" + x + "," + y + "]";
        grid[x][y] = MazeCoder.Block.PATH;
        if (x == tX && y == tY) {
            forEachListener(l -> l.found(x, y, newPath, grid));
            return true;
        }
        boolean isOnPathToDestination
                = findAnExitHelper(x, y + 1, tX, tY, newPath, Direction.RIGHT)
                || findAnExitHelper(x, y - 1, tX, tY, newPath, Direction.LEFT)
                || findAnExitHelper(x + 1, y, tX, tY, newPath, Direction.DOWN)
                || findAnExitHelper(x - 1, y, tX, tY, newPath, Direction.UP);
        if (!isOnPathToDestination && direction != null) {
            grid[x][y] = MazeCoder.Block.VISITED;
            forEachListener(l -> l.failed(x, y, path, grid));
        }
        return isOnPathToDestination;
    }

    public void addEventListner(MSEventListener l) {
        list.add(MSEventListener.class, l);
    }

    public void removeEventListner(MSEventListener l) {
        list.remove(MSEventListener.class, l);
    }

    private void forEachListener(Consumer<MSEventListener> consumer) {
        for (MSEventListener l : list.getListeners(MSEventListener.class))
            consumer.accept(l);
    }


    public enum Direction {
        UP, RIGHT, DOWN, LEFT;

        Loc reverse(int r, int c) {
            return new Loc(r - dx(), c - dy());
        }

        int dx() {
            switch (this) {
                case DOWN:
                    return 1;
                case UP:
                    return -1;
            }
            return 0;
        }


        int dy() {
            switch (this) {
                case RIGHT:
                    return 1;
                case LEFT:
                    return -1;
            }
            return 0;
        }
    }

    public interface MSEventListener extends EventListener {
        void started(int r, int c, int tR, int tC, MazeCoder.Block[][] map);

        void tryout(int r, int c, Direction direction, String path, MazeCoder.Block[][] map);

        void found(int tR, int tC, String path, MazeCoder.Block[][] map);

        void failed(int r, int c, String path, MazeCoder.Block[][] map);

        void ended(boolean hasPath, MazeCoder.Block[][] map);
    }

    public static class Loc {
        int r, c;

        Loc(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
}
