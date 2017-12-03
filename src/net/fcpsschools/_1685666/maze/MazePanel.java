package net.fcpsschools._1685666.maze;

import net.fcpsschools._1685666.PlaybackPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

/**
 * @author ApolloZhu, Pd. 1
 */
public class MazePanel extends PlaybackPanel implements MazeSolver.MSEventListener {
    private static MazeSolver.Type[] types = MazeSolver.Type.values();
    private final JButton pickStartButton, pickEndButton, editWallButton;
    private final JComboBox<String> solverComboBox;
    private MazeSolver solver;
    private MazeCanvas canvas;
    private boolean isSelectingStart, isSelectingEnd, isEditingWall;
    private MazeSolver.Loc start, end;
    private MazeCoder.Block[][] map;
    private int selectedSolverIndex;

    public MazePanel() {
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

        String[] solverTypes = Arrays.stream(types)
                .map(MazeSolver.Type::description).toArray(String[]::new);
        panel.add(solverComboBox = new JComboBox<>(solverTypes));
        setMazeSolverAtIndex(selectedSolverIndex);
        solverComboBox.addActionListener(ignored ->
                setMazeSolverAtIndex(solverComboBox.getSelectedIndex()));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MazeCanvas canvas = (MazeCanvas) getCenterComponent();
                int x = e.getX() - canvas.getX();
                int y = e.getY() - canvas.getY();
                MazeSolver.Loc loc = canvas.getLoc(x, y);
                if (loc == null || loc.getR() < 0 || loc.getC() < 0
                        || loc.getR() >= map.length || loc.getC() >= map[loc.getR()].length) return;
                boolean notWall = !canvas.isWall(loc.getR(), loc.getC());
                if (isSelectingStart && notWall) {
                    canvas.setStart(start = loc);
                    isSelectingStart = false;
                } else if (isSelectingEnd && notWall) {
                    canvas.setTarget(end = loc);
                    isSelectingEnd = false;
                } else if (isEditingWall && !loc.equals(start) && !loc.equals(end)) {
                    MazeCoder.clear(map);
                    canvas.setMap(map);
                    map[loc.getR()][loc.getC()] = notWall
                            ? MazeCoder.Block.WALL
                            : MazeCoder.Block.EMPTY;
                    canvas.setMap(map);
                } else return;
                clearMap();
            }
        });
        canvas.setStart(start = new MazeSolver.Loc(0, 0));
        canvas.setTarget(end = new MazeSolver.Loc(map.length - 1, map[0].length - 1));
    }

    @Override
    protected Component getCenterComponent() {
        if (canvas != null) return canvas;
        if (map != null) return canvas = new MazeCanvas(map);
        return canvas = new MazeCanvas(map = MazeCoder.EXAMPLE());
    }

    public void setMazeSolverAtIndex(int index) {
        index = Math.max(Math.min(types.length, index), 0);
        if (solver != null && selectedSolverIndex == index) return;
        setMazeSolver(types[selectedSolverIndex = index].init());
    }

    public void setMazeSolver(MazeSolver solver) {
        terminate();
        clearMap();
        if (this.solver != null)
            this.solver.removeEventListener(canvas);
        this.solver = solver;
        for (int i = 0; i < types.length; i++)
            if (types[i].getClass().equals(solver.getClass())) {
                solverComboBox.setSelectedIndex(i);
                break;
            }
        solver.addEventListener(canvas);
    }

    @Override
    protected void start() {
        pickStartButton.setEnabled(false);
        pickEndButton.setEnabled(false);
        editWallButton.setEnabled(false);
        solverComboBox.setEnabled(false);
        isEditingWall = false;
        clearMap();
        solver.addEventListener(this);
        solver.start(map, start.getR(), start.getC(), end.getR(), end.getC());
    }

    @Override
    protected void terminate() {
        terminate(false);
    }

    protected void terminate(boolean hasPath) {
        pickStartButton.setEnabled(true);
        pickEndButton.setEnabled(true);
        editWallButton.setEnabled(true);
        solverComboBox.setEnabled(true);
        if (solver != null) {
            solver.removeEventListener(this);
            solver.stop(hasPath);
        }
        super.terminate();
    }

    protected void clearMap() {
        MazeCoder.clear(map);
        canvas.resetMap(map);
    }

    @Override
    public void started(int r, int c, int tR, int tC, MazeCoder.Block[][] map) {
        sleep();
    }

    @Override
    public void tryout(int r, int c, MazeSolver.Direction direction, Object path, MazeCoder.Block[][] map) {
        sleep();
    }

    @Override
    public void found(int tR, int tC, Object path, MazeCoder.Block[][] map) {
        sleep();
    }

    @Override
    public void failed(int r, int c, Object path, MazeCoder.Block[][] map) {
        sleep();
    }

    @Override
    public void ended(boolean hasPath, MazeCoder.Block[][] map) {
        terminate(hasPath);
    }
}
