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

package net.fcpsschools._1685666._3.lab._4_map;

import javax.swing.*;
import java.awt.*;

/**
 * @author Apollo/Zhiyu Zhu/朱智语
 */
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
