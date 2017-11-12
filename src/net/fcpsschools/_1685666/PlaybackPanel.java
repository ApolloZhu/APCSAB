package net.fcpsschools._1685666;

import com.sun.javafx.runtime.async.BackgroundExecutor;
import layout.SpringUtilities;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

/**
 * @author ApolloZhu, Pd. 1
 */
public abstract class PlaybackPanel extends JPanel {

    private final JButton start = new JButton("Start");
    private final JSlider slider = new JSlider(JSlider.VERTICAL, 0, 200, 100);
    private final JButton pauseResume = new JButton("Pause");
    Thread thread;
    private int size;
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
            BackgroundExecutor.getExecutor().execute(() -> {
                if (start.getText().equals("Start")) {
                    start.setText("Terminate");
                    pauseResume.setText("Pause");
                    pauseResume.setEnabled(true);
                    thread = Thread.currentThread();
                    start();
                } else terminate();
            });
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
        sliderLabels.put(10, new JLabel("Slow"));
        sliderLabels.put(100, new JLabel("Normal"));
        sliderLabels.put(190, new JLabel("Fast"));
        sliderLabels.put(200, new JLabel("Non Stop"));
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
            if (scaleFactor == 200) return; // Non stop
            double percentage = Math.max(scaleFactor / 100, 0.1);
            long interval = (long) (unit * 100 / percentage);
            thread.sleep(interval);
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
