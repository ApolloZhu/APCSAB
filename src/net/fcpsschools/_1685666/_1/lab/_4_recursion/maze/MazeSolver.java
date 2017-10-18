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

    public void stop() {
        forEachListener(l -> l.ended(false, grid));
    }

    public boolean start(MazeCoder.Block[][] input, int r, int c, int tR, int tC) {
        grid = input;
        if (get(r, c) == MazeCoder.Block.WALL || get(tR, tC) == MazeCoder.Block.WALL)
            throw new IllegalArgumentException("We don't walk through walls.");
        set(r, c, MazeCoder.Block.EMPTY);
        set(tR, tC, MazeCoder.Block.EMPTY);
        boolean hasPath = findAnExitHelper(r, c, tR, tC, "", null);
        forEachListener(l -> l.ended(hasPath, grid));
        return hasPath;
    }

    protected MazeCoder.Block get(int x, int y) {
        try {
            return grid[x][y];
        } catch (Exception e) {
            return MazeCoder.Block.WALL;
        }
    }

    protected void set(int x, int y, MazeCoder.Block block) {
        try {
            grid[x][y] = block;
        } catch (Exception e) {
        }
    }

    private boolean findAnExitHelper(int x, int y, int tX, int tY, String path, Direction direction) {
        if (direction != null) {
            Loc back = direction.reverse(x, y);
            forEachListener(l -> l.tryout(back.r, back.c, direction, path, grid));
        } else forEachListener(l -> l.started(x, y, tX, tY, grid));
        if (get(x, y) != MazeCoder.Block.EMPTY) return false;

        String newPath = path + "[" + x + "," + y + "]";
        grid[x][y] = MazeCoder.Block.PATH;
        if (x == tX && y == tY) {
            forEachListener(l -> l.found(x, y, newPath, grid));
            return true;
        }
        int dX = tX - x, dY = tY - y;
        boolean isOnPathToDestination;
        CHECK:
        if (Math.abs(dX) <= Math.abs(dY)) {
            if (dX < 0 && findAnExitHelper(x - 1, y, tX, tY, newPath, Direction.UP)
                    || dX > 0 && findAnExitHelper(x + 1, y, tX, tY, newPath, Direction.DOWN)
                    || dY < 0 && findAnExitHelper(x, y - 1, tX, tY, newPath, Direction.LEFT)
                    || dY > 0 && findAnExitHelper(x, y + 1, tX, tY, newPath, Direction.RIGHT)
                    || dX <= 0 && findAnExitHelper(x + 1, y, tX, tY, newPath, Direction.DOWN)
                    || findAnExitHelper(x - 1, y, tX, tY, newPath, Direction.UP)
                    || dY < 0 && findAnExitHelper(x, y + 1, tX, tY, newPath, Direction.RIGHT)
                    || findAnExitHelper(x, y - 1, tX, tY, newPath, Direction.LEFT)) return true;
        } else {
            if (dY < 0 && findAnExitHelper(x, y - 1, tX, tY, newPath, Direction.LEFT)
                    || dY > 0 && findAnExitHelper(x, y + 1, tX, tY, newPath, Direction.RIGHT)
                    || dX < 0 && findAnExitHelper(x - 1, y, tX, tY, newPath, Direction.UP)
                    || dX > 0 && findAnExitHelper(x + 1, y, tX, tY, newPath, Direction.DOWN)
                    || dY <= 0 && findAnExitHelper(x, y + 1, tX, tY, newPath, Direction.RIGHT)
                    || findAnExitHelper(x, y - 1, tX, tY, newPath, Direction.LEFT)
                    || dX < 0 && findAnExitHelper(x + 1, y, tX, tY, newPath, Direction.DOWN)
                    || findAnExitHelper(x - 1, y, tX, tY, newPath, Direction.UP)) return true;
        }
        if (direction != null) {
            grid[x][y] = MazeCoder.Block.VISITED;
            forEachListener(l -> l.failed(x, y, path, grid));
        }
        return false;
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

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Loc) {
                Loc loc = (Loc) obj;
                return r == loc.r && c == loc.c;
            }
            return false;
        }
    }
}
