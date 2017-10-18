package net.fcpsschools._1685666._1.lab._4_recursion.maze;

import net.fcpsschools._1685666._1.lab._4_recursion.PlaybackPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author ApolloZhu, Pd. 1
 */
public class MazeGUI extends PlaybackPanel implements MazeSolver.MSEventListener {
    private final MazeSolver solver = new MazeSolver();
    private MazeCanvas canvas;

    public MazeGUI() {
        solver.addEventListner(canvas);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new MazeGUI());
        frame.setVisible(true);
    }

    @Override
    protected Component getCenterComponent() {
        return canvas = new MazeCanvas(MazeCoder.EXAMPLE());
    }

    @Override
    protected void start() {
        MazeCoder.Block[][] map = MazeCoder.EXAMPLE();
        canvas.setMap(map);
        solver.addEventListner(this);
        solver.start(map, 0, 0, map.length - 1, map[0].length - 1);
    }

    @Override
    protected void terminate() {
        solver.removeEventListner(this);
        solver.stop();
        super.terminate();
    }

    @Override
    public void started(int r, int c, int tR, int tC, MazeCoder.Block[][] map) {
        sleep();
    }

    @Override
    public void tryout(int r, int c, MazeSolver.Direction direction, String path, MazeCoder.Block[][] map) {
        sleep();
    }

    @Override
    public void found(int tR, int tC, String path, MazeCoder.Block[][] map) {
        sleep();
    }

    @Override
    public void failed(int r, int c, String path, MazeCoder.Block[][] map) {
        sleep();
    }

    @Override
    public void ended(boolean hasPath, MazeCoder.Block[][] map) {
        terminate();
    }
}
