package net.fcpsschools._1685666._1.lab._4_recursion.maze;

import net.fcpsschools._1685666._1.lab._4_recursion.eight_queen.PiecePainter;

import javax.swing.*;
import java.awt.*;

/**
 * @author ApolloZhu, Pd. 1
 */
public class MazeCanvas extends JPanel implements MazeSolver.MSEventListener {
    private MazeCoder.Block[][] map;
    private boolean[][][] cache;
    private final PiecePainter wallPainter = (graphics, r, c, x, y, w, h) -> {
        // Update cache
        if (cache == null) return;
        if (cache[r][c] == null) {
            cache[r][c] = new boolean[9];
            for (int i = r - 1, k = 0; i <= r + 1; i++)
                for (int j = c - 1; j <= c + 1; j++, k++)
                    cache[r][c][k] = isWall(i, j);
        }
        // Draw fences
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(Color.BLACK);
        int centerX = x + w / 2;
        int centerY = y + h / 2;
        for (int i = 0; i < 9; i++)
            if (i != 4 && cache[r][c][i]) {
                int vX = i % 3, vY = i / 3;
                int lX = vX == 0 ? x : vX == 1 ? centerX : x + w;
                int lY = vY == 0 ? y : vY == 1 ? centerY : y + h;
                g.drawLine(lX, lY, centerX, centerY);
            }
    };
    private Path[][] paths;
    private final PiecePainter pathPainter = (g, r, c, x, y, w, h) -> {
        Path path = paths[r][c];
        if (path == null) return; // In between trials
        g.drawLine(x + w / 2, y + h / 2,
                x + w / 2 + path.direction.dy() * w,
                y + h / 2 + path.direction.dx() * h);
    };
    private Color diffColor = Color.GREEN;
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
        if (start != null && r == start.r && c == start.c)
            g.drawOval(x + w / 5, y + h / 5, w * 3 / 5, h * 3 / 5);
        if (end != null && r == end.r && c == end.c) {
            g.drawLine(x + w / 5, y + h / 5, x + w * 4 / 5, y + h * 4 / 5);
            g.drawLine(x + w * 4 / 5, y + h / 5, x + w / 5, y + h * 4 / 5);
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
                g.drawLine(x + w / 2, y + h / 5, x + w * 4 / 5, y + h * 4 / 5);
                g.drawLine(x + w * 4 / 5, y + h * 4 / 5, x + w / 5, y + h * 4 / 5);
                g.drawLine(x + w / 5, y + h * 4 / 5, x + w / 2, y + h / 5);
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

    private boolean isWall(int r, int c) {
        return get(r, c) == MazeCoder.Block.WALL;
    }

    private MazeCoder.Block get(int r, int c) {
        try {
            return map[r][c];
        } catch (Exception e) {
            return MazeCoder.Block.WALL;
        }
    }

    private void setMap(MazeCoder.Block[][] map) {
        this.map = map;
        this.cache = new boolean[map.length][map[0].length][];
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int side = Math.min(getWidth() / map[0].length, getHeight() / map.length);
        int xOffset = (getWidth() - side * map[0].length) / 2;
        int yOffset = (getHeight() - side * map.length) / 2;
        for (int j = 0; j < map[0].length; j++)
            for (int i = 0; i < map.length; i++) {
                int x = xOffset + side * j;
                int y = yOffset + side * i;
                painter.paintPiece(g, i, j, x, y, side, side);
            }
    }

    @Override
    public void started(int r, int c, int tR, int tC, MazeCoder.Block[][] map) {
        this.start = new MazeSolver.Loc(r, c);
        this.end = new MazeSolver.Loc(tR, tC);
        this.paths = new Path[map.length][map[0].length];
        commonColor = Color.BLUE;
        setMap(map);
    }

    @Override
    public void tryout(int r, int c, MazeSolver.Direction direction, String path, MazeCoder.Block[][] map) {
        diffColor = Color.CYAN;
        paths[r][c] = diff = new Path(r, c, direction);
        setMap(map);
    }

    @Override
    public void found(int tR, int tC, String path, MazeCoder.Block[][] map) {
        commonColor = Color.GREEN;
        diff = null;
        setMap(map);
    }

    @Override
    public void failed(int r, int c, String path, MazeCoder.Block[][] map) {
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
