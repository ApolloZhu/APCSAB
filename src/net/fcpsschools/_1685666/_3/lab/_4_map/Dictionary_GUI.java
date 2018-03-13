package net.fcpsschools._1685666._3.lab._4_map;

import javax.swing.*;
import java.awt.*;

public class Dictionary_GUI {
    private static final JFrame frame =
            new JFrame("Dictionary - Zhiyu Zhu, Period 1");

    public static void main(String[] args) {
        frame.setContentPane(new DictionaryPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(size);
        frame.setAutoRequestFocus(true);
        frame.requestFocus();
        frame.toFront();
        frame.setVisible(true);
    }

    public static void showError(Throwable thrown) {
        String message = thrown.getLocalizedMessage();
        if (message == null || message.isEmpty())
            message = "Something went wrong.";
        JOptionPane.showMessageDialog(frame, message, "Error!", JOptionPane.ERROR_MESSAGE
        );
    }
}
