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

package net.fcpsschools._1685666;

import layout.SpringUtilities;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Apollo/Zhiyu Zhu/朱智语, Pd. 1
 */
public abstract class PlaybackPanel extends JPanel {
    private final int MAX = 1000;
    private final JButton start = new JButton("Start");
    private final JSlider slider = new JSlider(JSlider.VERTICAL, 0, MAX, 10);
    private final JButton pauseResume = new JButton("Pause");
    Thread thread;
    private double scaleFactor;

    public PlaybackPanel() {
        setLayout(new BorderLayout());
        add(getCenterComponent(), BorderLayout.CENTER);
        // Controls
        JPanel controls = new JPanel();
        controls.setLayout(new SpringLayout());
        controls.setAlignmentX(SwingConstants.CENTER);
        add(controls, BorderLayout.EAST);
        controls.add(start);
        controls.add(pauseResume);
        controls.add(slider);

        // Start
        start.addActionListener(ignored -> {
            new Thread(() -> {
                if (start.getText().equals("Start")) {
                    start.setText("Terminate");
                    pauseResume.setText("Pause");
                    pauseResume.setEnabled(true);
                    thread = Thread.currentThread();
                    try {
                        start();
                    } catch (Throwable t) {
                        if (t instanceof ThreadDeath) return;
                        String message = t.getLocalizedMessage();
                        if (message == null || message.isEmpty())
                            message = "Something went wrong. Please see system log for details.";
                        Logger.getGlobal().log(Level.WARNING, "Subclass implementation failed", t);
                        JOptionPane.showMessageDialog(this,
                                message, "Oops!", JOptionPane.WARNING_MESSAGE);
                        terminate();
                    }
                } else terminate();
            }).start();
        });
        // Pause Resume
        pauseResume.setEnabled(false);
        pauseResume.addActionListener(l -> {
            if (pauseResume.getText().equals("Pause")) pause();
            else resume();
        });
        // Speed Control Slider
        Hashtable<Integer, JLabel> sliderLabels = new Hashtable<>();
        sliderLabels.put(0, new JLabel("Pause"));
        sliderLabels.put(50, new JLabel("Slow"));
        sliderLabels.put(100, new JLabel("Normal"));
        sliderLabels.put(800, new JLabel("Fast"));
        sliderLabels.put(MAX, new JLabel("Non Stop"));
        slider.setLabelTable(sliderLabels);
        slider.setPaintLabels(true);

        scaleFactor = slider.getValue();
        slider.addChangeListener(ignored -> {
            double oldValue = scaleFactor;
            double newValue = slider.getValue();
            scaleFactor = newValue;
            if (oldValue == 0 && newValue != 0) resume();
            if (newValue == 0 && oldValue != 0) pause();
        });

        SpringUtilities.makeCompactGrid(controls, 3, 1, 0, 8, 8, 8);
    }

    protected abstract Component getCenterComponent();

    protected abstract void start();

    @SuppressWarnings("deprecation")
    protected void terminate() {
        start.setText("Start");
        pauseResume.setEnabled(false);
        if (thread != null) thread.stop();
    }

    protected void sleep() {
        sleep(1);
    }

    protected void performUpdate() {
        getCenterComponent().repaint();
    }

    protected void sleep(int unit) {
        performUpdate();
        try {
            if (scaleFactor == MAX) return; // Non stop
            double percentage = Math.max(scaleFactor / 100, 0.1);
            long interval = (long) (unit * 25 / percentage);
            Thread.sleep(interval);
        } catch (Exception e) {
        }
    }

    @SuppressWarnings("deprecation")
    protected void pause() {
        performUpdate();
        pauseResume.setText("Resume");
        thread.suspend();
    }

    @SuppressWarnings("deprecation")
    protected void resume() {
        if (scaleFactor == 0) {
            scaleFactor = 100;
            slider.setValue(100);
        }
        pauseResume.setText("Pause");
        thread.resume();
        performUpdate();
    }
}
