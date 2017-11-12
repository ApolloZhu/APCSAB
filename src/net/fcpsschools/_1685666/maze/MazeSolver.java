package net.fcpsschools._1685666.maze;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.event.EventListenerList;
import java.util.EventListener;
import java.util.function.Consumer;

/**
 * @author ApolloZhu, Pd. 1
 */
public abstract class MazeSolver {

    private final EventListenerList list = new EventListenerList();
    private MazeCoder.Block[][] grid;

    public static MazeSolver makeRecursive() {
        return new RecursiveMazeSolver();
    }

    public static MazeSolver makeStackBased() {
        throw new NotImplementedException();
    }

    public abstract boolean start(MazeCoder.Block[][] input,
                                  int r, int c, int tR, int tC);

    public void stop() {
        forEachListener(l -> l.ended(false, grid));
    }

    public MazeCoder.Block[][] getGrid() {
        return grid;
    }

    public void setGrid(MazeCoder.Block[][] grid) {
        this.grid = grid;
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

    public void addEventListener(MSEventListener l) {
        list.add(MSEventListener.class, l);
    }

    public void removeEventListener(MSEventListener l) {
        list.remove(MSEventListener.class, l);
    }

    protected void forEachListener(Consumer<MSEventListener> consumer) {
        for (MSEventListener l : list.getListeners(MSEventListener.class))
            consumer.accept(l);
    }

    public interface MSEventListener extends EventListener {
        void started(int r, int c, int tR, int tC, MazeCoder.Block[][] map);

        void tryout(int r, int c, RecursiveMazeSolver.Direction direction, String path, MazeCoder.Block[][] map);

        void found(int tR, int tC, String path, MazeCoder.Block[][] map);

        void failed(int r, int c, String path, MazeCoder.Block[][] map);

        void ended(boolean hasPath, MazeCoder.Block[][] map);
    }
}
