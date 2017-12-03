package net.fcpsschools._1685666.maze;

import net.fcpsschools._1685666.eight_queen.PiecePainter;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ApolloZhu, Pd. 1
 */
public class MazeCanvas extends JPanel implements MazeSolver.MSEventListener {
    public static final PiecePainter START_PAINTER = (g, r, c, x, y, w, h) -> {
        g.setColor(Color.RED);
        g.drawOval(x + w / 5, y + h / 5,
                w * 3 / 5, h * 3 / 5);
    };
    public static final PiecePainter TARGET_PAINTER = (g, r, c, x, y, w, h) -> {
        g.setColor(Color.RED);
        g.drawLine(x + w / 5, y + h / 5,
                x + w * 4 / 5, y + h * 4 / 5);
        g.drawLine(x + w * 4 / 5, y + h / 5,
                x + w / 5, y + h * 4 / 5);
    };
    private static final Color DIFF_COLOR_NEW = new Color(102, 204, 255);
    private static final Color COMMON_COLOR_FOUND = new Color(29, 135, 17);
    private static final Color COMMON_COLOR_NORMAL = Color.blue;
    private static final Color COMMON_COLOR_FAILED = Color.red;
    List<PiecePainter> painters = new LinkedList<>();
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
    private Color diffColor;
    private final PiecePainter diffPainter = (graphics, r, c, x, y, w, h) -> {
        Graphics2D g = (Graphics2D) graphics;
        Path path = paths[r][c];
        if (path == null) return; // In between trials
        int headX = x + w / 2 + path.direction.dy() * w;
        int headY = y + h / 2 + path.direction.dx() * h;
        Stroke stroke = g.getStroke();
        g.setStroke(new BasicStroke(w / 10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.setColor(diffColor);
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
    private Color commonColor = COMMON_COLOR_NORMAL;
    private final PiecePainter multiPathPainter = (graphics, r, c, x, y, w, h) -> {
        // Draw multi path
        Graphics2D g = (Graphics2D) graphics;
        g.setStroke(new BasicStroke(w / 10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.setColor(commonColor);
        int centerX = x + w / 2;
        int centerY = y + h / 2;
        boolean hasNeighbor = false;
        for (int i = r - 1, k = 0; i <= r + 1; i++)
            for (int j = c - 1; j <= c + 1; j++, k++)
                if ((i == r || j == c) && isPath(i, j)) {
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
    private Path diff = null;
    private MazeSolver.Loc start, end;
    private final PiecePainter painter = (graphics, r, c, x, y, w, h) -> {
        Graphics2D g = (Graphics2D) graphics;

        g.setColor(Color.GRAY);
        g.drawRect(x, y, w, h);

        Stroke stroke = g.getStroke();
        g.setStroke(new BasicStroke(w / 5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        if (start != null && r == start.getR() && c == start.getC())
            START_PAINTER.paintPiece(g, r, c, x, y, w, h);
        if (end != null && r == end.getR() && c == end.getC()) {
            TARGET_PAINTER.paintPiece(g, r, c, x, y, w, h);
            g.setStroke(stroke);
            return;
        }

        switch (map[r][c]) {
            case WALL:
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
                multiPathPainter.paintPiece(g, r, c, x, y, w, h);
                break;
            default:
                break;
        }
        if (diff != null && diff.r == r && diff.c == c) {
            diffPainter.paintPiece(g, r, c, x, y, w, h);
        }
        g.setStroke(stroke);
    };

    public MazeCanvas(MazeCoder.Block[][] map) {
        setMap(map);
    }

    public boolean isWall(int r, int c) {
        return get(r, c) == MazeCoder.Block.WALL;
    }

    public boolean isPath(int r, int c) {
        return get(r, c) == MazeCoder.Block.PATH;
    }

    public MazeCoder.Block get(int r, int c) {
        try {
            return map[r][c];
        } catch (Exception e) {
            return MazeCoder.Block.WALL;
        }
    }

    public void resetMap(MazeCoder.Block[][] map) {
        this.map = map;
        reset();
        repaint();
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
        if (map == null || map.length == 0 || map[0].length == 0) return 0;
        return Math.min(getWidth() / map[0].length, getHeight() / map.length);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (map == null || map.length == 0 || map[0].length == 0) return;
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
        if (map == null || map.length == 0 || map[0].length == 0) return null;
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

    protected void reset() {
        commonColor = COMMON_COLOR_NORMAL;
        this.paths = new Path[map.length][map[0].length];
    }

    @Override
    public void started(int r, int c, int tR, int tC, MazeCoder.Block[][] map) {
        setStart(new MazeSolver.Loc(r, c));
        setTarget(new MazeSolver.Loc(tR, tC));
        resetMap(map);
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
        diffColor = COMMON_COLOR_FAILED;
        diff = paths[r][c];
        paths[r][c] = null;
        setMap(map);
    }

    @Override
    public void ended(boolean hasPath, MazeCoder.Block[][] map) {
        commonColor = hasPath ? COMMON_COLOR_FOUND : COMMON_COLOR_FAILED;
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
