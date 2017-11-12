package net.fcpsschools._1685666.maze;

import net.fcpsschools._1685666.eight_queen.PiecePainter;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/**
 * @author ApolloZhu, Pd. 1
 */
public class MazeCanvas extends JPanel implements MazeSolver.MSEventListener {
    public static final PiecePainter START_PAINTER = (g, r, c, x, y, w, h) -> {
        g.drawOval(x + w / 5, y + h / 5,
                w * 3 / 5, h * 3 / 5);
    };
    public static final PiecePainter TARGET_PAINTER = (g, r, c, x, y, w, h) -> {
        g.drawLine(x + w / 5, y + h / 5,
                x + w * 4 / 5, y + h * 4 / 5);
        g.drawLine(x + w * 4 / 5, y + h / 5,
                x + w / 5, y + h * 4 / 5);
    };
    private static final Color DIFF_COLOR_NEW = new Color(102, 204, 255);
    private static final Color COMMON_COLOR_FOUND = new Color(29, 135, 17);
    LinkedList<PiecePainter> painters = new LinkedList<>();
    private MazeCoder.Block[][] map;
    private final PiecePainter wallPainter = (graphics, r, c, x, y, w, h) -> {
        // Draw fences
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(Color.BLACK);
        int centerX = x + w / 2;
        int centerY = y + h / 2;
        boolean hasNeighbor = false;
        for (int i = r - 1, k = 0; i <= r + 1; i++)
            for (int j = c - 1; j <= c + 1; j++, k++)
                if (k != 4 && isWall(i, j)) {
                    hasNeighbor = true;
                    int vX = k % 3, vY = k / 3;
                    int lX = vX == 0 ? x : vX == 1 ? centerX : x + w;
                    int lY = vY == 0 ? y : vY == 1 ? centerY : y + h;
                    g.drawLine(lX, lY, centerX, centerY);
                }
        if (hasNeighbor) return;
        g.drawLine(x, centerY, centerX, y);
        g.drawLine(centerX, y, x + w, centerY);
        g.drawLine(x + w, centerY, centerX, y + h);
        g.drawLine(centerX, y + h, x, centerY);
    };
    private Path[][] paths;
    private final PiecePainter pathPainter = (graphics, r, c, x, y, w, h) -> {
        Graphics2D g = (Graphics2D) graphics;
        Path path = paths[r][c];
        if (path == null) return; // In between trials
        int headX = x + w / 2 + path.direction.dy() * w;
        int headY = y + h / 2 + path.direction.dx() * h;
        Stroke stroke = g.getStroke();
        g.setStroke(new BasicStroke(w / 10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawLine(x + w / 2, y + h / 2, headX, headY);
        int dX = w / 5;
        int dY = h / 5;
        switch (path.direction) {
            case UP:
                g.drawLine(headX, headY, headX + dX, headY + dY);
                g.drawLine(headX, headY, headX - dX, headY + dY);
                break;
            case DOWN:
                g.drawLine(headX, headY, headX + dX, headY - dY);
                g.drawLine(headX, headY, headX - dX, headY - dY);
                break;
            case LEFT:
                g.drawLine(headX, headY, headX + dX, headY + dY);
                g.drawLine(headX, headY, headX + dX, headY - dY);
                break;
            case RIGHT:
                g.drawLine(headX, headY, headX - dX, headY + dY);
                g.drawLine(headX, headY, headX - dX, headY - dY);
                break;
        }
        g.setStroke(stroke);
    };
    private Color diffColor;
    private Color commonColor = Color.BLUE;
    private Path diff = null;
    private MazeSolver.Loc start, end;
    private final PiecePainter painter = (graphics, r, c, x, y, w, h) -> {
        Graphics2D g = (Graphics2D) graphics;

        g.setColor(Color.GRAY);
        g.drawRect(x, y, w, h);

        Stroke stroke = g.getStroke();
        g.setStroke(new BasicStroke(w / 5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        g.setColor(Color.RED);
        if (start != null && r == start.getR() && c == start.getC())
            START_PAINTER.paintPiece(g, r, c, x, y, w, h);
        if (end != null && r == end.getR() && c == end.getC()) {
            TARGET_PAINTER.paintPiece(g, r, c, x, y, w, h);
            g.setStroke(stroke);
            return;
        }

        switch (map[r][c]) {
            case WALL:
                g.setColor(Color.BLACK);
                wallPainter.paintPiece(g, r, c, x, y, w, h);
                break;
            case VISITED:
                g.setColor(Color.ORANGE);
                g.drawLine(x + w / 2, y + h / 5,
                        x + w * 4 / 5, y + h * 4 / 5);
                g.drawLine(x + w * 4 / 5, y + h * 4 / 5,
                        x + w / 5, y + h * 4 / 5);
                g.drawLine(x + w / 5, y + h * 4 / 5,
                        x + w / 2, y + h / 5);
                break;
            case PATH:
                g.setColor(commonColor);
                pathPainter.paintPiece(g, r, c, x, y, w, h);
                break;
            default:
                break;
        }
        if (diff != null && diff.r == r && diff.c == c) {
            g.setColor(diffColor);
            pathPainter.paintPiece(g, r, c, x, y, w, h);
        }
        g.setStroke(stroke);
    };

    public MazeCanvas(MazeCoder.Block[][] map) {
        setMap(map);
    }

    public boolean isWall(int r, int c) {
        return get(r, c) == MazeCoder.Block.WALL;
    }

    public MazeCoder.Block get(int r, int c) {
        try {
            return map[r][c];
        } catch (Exception e) {
            return MazeCoder.Block.WALL;
        }
    }

    public void setMap(MazeCoder.Block[][] map) {
        this.map = map;
        repaint();
    }

    public void addPainter(PiecePainter painter) {
        painters.add(painter);
    }

    public void removePainter(PiecePainter painter) {
        painters.remove(painter);
    }

    private int getSide() {
        return Math.min(getWidth() / map[0].length, getHeight() / map.length);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int side = getSide();
        int xOffset = (getWidth() - side * map[0].length) / 2;
        int yOffset = (getHeight() - side * map.length) / 2;
        for (int j = 0; j < map[0].length; j++)
            for (int i = 0; i < map.length; i++) {
                int r = i, c = j;
                int x = xOffset + side * j;
                int y = yOffset + side * i;
                painter.paintPiece(g, r, c, x, y, side, side);
                painters.forEach(l -> l.paintPiece(g, r, c, x, y, side, side));
            }
    }

    public MazeSolver.Loc getLoc(int x, int y) {
        int side = Math.min(getWidth() / map[0].length,
                getHeight() / map.length);
        int xOffset = (getWidth() - side * map[0].length) / 2;
        int yOffset = (getHeight() - side * map.length) / 2;
        int r = (y - yOffset) / side;
        int c = (x - xOffset) / side;
        if (r < 0 || c < 0 || r >= map.length || c >= map[0].length) return null;
        return new MazeSolver.Loc(r, c);
    }

    public void setStart(MazeSolver.Loc start) {
        this.start = start;
        repaint();
    }

    public void setTarget(MazeSolver.Loc target) {
        this.end = target;
        repaint();
    }

    @Override
    public void started(int r, int c, int tR, int tC, MazeCoder.Block[][] map) {
        setStart(new MazeSolver.Loc(r, c));
        setTarget(new MazeSolver.Loc(tR, tC));
        this.paths = new Path[map.length][map[0].length];
        commonColor = Color.BLUE;
        setMap(map);
    }

    @Override
    public void tryout(int r, int c, MazeSolver.Direction direction, Object path, MazeCoder.Block[][] map) {
        diffColor = DIFF_COLOR_NEW;
        paths[r][c] = diff = new Path(r, c, direction);
        setMap(map);
    }

    @Override
    public void found(int tR, int tC, Object path, MazeCoder.Block[][] map) {
        commonColor = COMMON_COLOR_FOUND;
        diff = null;
        setMap(map);
    }

    @Override
    public void failed(int r, int c, Object path, MazeCoder.Block[][] map) {
        diffColor = Color.RED;
        diff = paths[r][c];
        paths[r][c] = null;
        setMap(map);
    }

    @Override
    public void ended(boolean hasPath, MazeCoder.Block[][] map) {
        setMap(map);
    }

    private class Path {
        int r, c;
        MazeSolver.Direction direction;

        Path(int r, int c, MazeSolver.Direction direction) {
            this.r = r;
            this.c = c;
            this.direction = direction;
        }
    }
}
