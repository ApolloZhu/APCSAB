package net.fcpsschools._1685666._1.lab._4_recursion.maze;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.util.EventListener;
import java.util.function.Consumer;

/**
 * @author ApolloZhu, Pd. 1
 */
public class MazeSolver {
    private EventListenerList list = new EventListenerList();
    private Block[][] grid;
    private MazeCanvas canvas;

    public MazeSolver(Block[][] grid) {
        this.grid = grid;
    }

    public static Block[][] convert(int[][] intMap, int wall, int empty, int visited, int path) {
        Block[][] converted = new Block[intMap.length][intMap[0].length];
        for (int i = 0; i < intMap.length; i++)
            for (int j = 0; j < intMap[i].length; j++)
                if (intMap[i][j] == wall) converted[i][j] = Block.WALL;
                else if (intMap[i][j] == empty) converted[i][j] = Block.EMPTY;
                else if (intMap[i][j] == visited) converted[i][j] = Block.VISITED;
                else if (intMap[i][j] == path) converted[i][j] = Block.PATH;
        return converted;
    }

    public boolean start() {
        JFrame frame = new JFrame();
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(canvas = new MazeCanvas(grid));
        frame.setVisible(true);
        return findAnExitHelper(0, 0, "");
    }

    private boolean findAnExitHelper(int x, int y, String path) {
        if (x < 0 || y < 0 || x >= grid.length || y >= grid[x].length || grid[x][y] != Block.EMPTY)
            return false;
        String newPath = path + "[" + x + "," + y + "]";
        grid[x][y] = Block.PATH;
        canvas.setMap(grid);
        if (x == grid.length - 1 && y == grid[x].length - 1) {
            System.out.println(newPath);
            return true;
        }
        boolean result = findAnExitHelper(x + 1, y, newPath)
                || findAnExitHelper(x, y + 1, newPath)
                || findAnExitHelper(x - 1, y, newPath)
                || findAnExitHelper(x, y - 1, newPath);
        if (!result) {
            grid[x][y] = Block.VISITED;
            canvas.setMap(grid);
        }
        return result;
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

    public enum Block {WALL, EMPTY, VISITED, PATH}

    public enum Direction {UP, RIGHT, DOWN, LEFT}

    public interface MSEventListener extends EventListener {
        void trail(Direction direction, String path, Block[][] map);

        void found(String path, Block[][] map);

        void failed(String path, Block[][] map);

        void ended();
    }
}
