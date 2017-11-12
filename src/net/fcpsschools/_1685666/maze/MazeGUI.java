package net.fcpsschools._1685666.maze;

import javax.swing.*;
import java.awt.*;

/**
 * @author ApolloZhu, Pd. 1
 */
public class MazeGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String[] solverTypes = {"Stack Based", "Recursive"};
        Object selectedSolver = JOptionPane
                .showInputDialog(null,
                        "Choose maze solver type", "Maze",
                        JOptionPane.INFORMATION_MESSAGE, null,
                        solverTypes, solverTypes[0]);
        frame.setContentPane(new MazePanel(
                selectedSolver.equals(solverTypes[0])
                        ? MazeSolver.makeStackBased()
                        : selectedSolver.equals(solverTypes[1])
                        ? MazeSolver.makeRecursive()
                        : null
        ));
        frame.setVisible(true);
    }
}
