package net.fcpsschools._1685666.maze;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * @author ApolloZhu, Pd. 1
 */
public class MazeGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MazeSolver.Type[] types = MazeSolver.Type.values();
        String[] solverTypes = Arrays.stream(types)
                .map(MazeSolver.Type::description).toArray(String[]::new);
        Object selectedSolver = JOptionPane
                .showInputDialog(null,
                        "Choose maze solver type", "Maze",
                        JOptionPane.INFORMATION_MESSAGE, null,
                        solverTypes, solverTypes[0]);

        for (int i = 0; i < types.length; i++)
            if (solverTypes[i].equals(selectedSolver)) {
                frame.setContentPane(new MazePanel(types[i].init()));
                break;
            }
        frame.setVisible(true);
    }
}
