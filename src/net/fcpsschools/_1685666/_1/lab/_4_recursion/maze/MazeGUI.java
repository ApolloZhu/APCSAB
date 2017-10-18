package net.fcpsschools._1685666._1.lab._4_recursion.maze;

import net.fcpsschools._1685666._1.lab._4_recursion.PlaybackPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author ApolloZhu, Pd. 1
 */
public class MazeGUI extends PlaybackPanel implements MazeSolver.MSEventListener {
    private final MazeSolver solver = new MazeSolver();
    private final JButton pickStartButton, pickEndButton, editWallButton;
    private MazeCanvas canvas;
    private boolean isSelectingStart, isSelectingEnd, isEditingWall;
    private MazeSolver.Loc start, end;
    private MazeCoder.Block[][] map;

    public MazeGUI() {
        solver.addEventListner(canvas);
        JPanel panel = new JPanel();
        add(panel, BorderLayout.NORTH);
        panel.add(pickStartButton = new JButton("Pick Start"));
        pickStartButton.addActionListener(l -> {
            isSelectingEnd = false;
            isEditingWall = false;
            isSelectingStart = true;
        });
        panel.add(pickEndButton = new JButton("Pick End"));
        pickEndButton.addActionListener(l -> {
            isSelectingStart = false;
            isEditingWall = false;
            isSelectingEnd = true;
        });
        panel.add(editWallButton = new JButton("Edit Wall"));
        editWallButton.addActionListener(l -> {
            isSelectingStart = false;
            isSelectingEnd = false;
            isEditingWall = true;
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MazeCanvas canvas = (MazeCanvas) getCenterComponent();
                int x = e.getX() - canvas.getX();
                int y = e.getY() - canvas.getY();
                MazeSolver.Loc loc = canvas.getLoc(x, y);
                if (loc == null || loc.r < 0 || loc.c < 0
                        || loc.r >= map.length || loc.c >= map[loc.r].length) return;
                boolean notWall = !canvas.isWall(loc.r, loc.c);
                if (isSelectingStart && notWall) {
                    canvas.setStart(start = loc);
                    isSelectingStart = false;
                } else if (isSelectingEnd && notWall) {
                    canvas.setTarget(end = loc);
                    isSelectingEnd = false;
                } else if (isEditingWall && !loc.equals(start) && !loc.equals(end)) {
                    MazeCoder.clear(map);
                    canvas.setMap(map);
                    map[loc.r][loc.c] = notWall
                            ? MazeCoder.Block.WALL
                            : MazeCoder.Block.EMPTY;
                    canvas.setMap(map);
                } else return;
                MazeCoder.clear(map);
                canvas.setMap(map);
            }
        });
        canvas.setStart(start = new MazeSolver.Loc(0, 0));
        canvas.setTarget(end = new MazeSolver.Loc(map.length - 1, map[0].length - 1));
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
        if (canvas != null) return canvas;
        if (map != null) return canvas = new MazeCanvas(map);
        return canvas = new MazeCanvas(map = MazeCoder.EXAMPLE());
    }

    @Override
    protected void start() {
        pickStartButton.setEnabled(false);
        pickEndButton.setEnabled(false);
        editWallButton.setEnabled(false);
        isEditingWall = false;
        MazeCoder.clear(map);
        canvas.setMap(map);
        solver.addEventListner(this);
        solver.start(map, start.r, start.c, end.r, end.c);
    }

    @Override
    protected void terminate() {
        pickStartButton.setEnabled(true);
        pickEndButton.setEnabled(true);
        editWallButton.setEnabled(true);
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
