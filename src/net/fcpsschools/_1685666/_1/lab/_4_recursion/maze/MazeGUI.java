package net.fcpsschools._1685666._1.lab._4_recursion.maze;

import com.sun.javafx.runtime.async.BackgroundExecutor;

import javax.swing.*;
import java.awt.*;

/**
 * @author ApolloZhu, Pd. 1
 */
public class MazeGUI extends JPanel implements MazeSolver.MSEventListener {
    private final MazeCanvas canvas = new MazeCanvas(MazeCoder.EXAMPLE);
    private final MazeSolver solver = new MazeSolver();

    public MazeGUI() {
        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);
        solver.addEventListner(canvas);
        solver.addEventListner(this);
        BackgroundExecutor.getExecutor().execute(() -> solver.start(MazeCoder.EXAMPLE, 0, 0, MazeCoder.EXAMPLE.length - 1, MazeCoder.EXAMPLE[0].length - 1));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new MazeGUI());
        frame.setVisible(true);
    }

    private void sleep() {
        try {
            solver.getThread().sleep(100);
        } catch (Exception e) {
        }
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
        sleep();
    }
}
