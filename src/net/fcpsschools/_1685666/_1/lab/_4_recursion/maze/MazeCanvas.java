package net.fcpsschools._1685666._1.lab._4_recursion.maze;

import net.fcpsschools._1685666._1.lab._4_recursion.eight_queen.PiecePainter;

import javax.swing.*;
import java.awt.*;

/**
 * @author ApolloZhu, Pd. 1
 */
public class MazeCanvas extends JPanel {
    private MazeSolver.Block[][] map;
    private boolean[][][] cache;
    private PiecePainter painter = (graphics, r, c, x, y, w, h) -> {
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(Color.GRAY);
        g.drawRect(x, y, w, h);
        if (cache[r][c] == null) {
            cache[r][c] = new boolean[9];
            for (int i = r - 1, k = 0; i <= r + 1; i++)
                for (int j = c - 1; j <= c + 1; j++, k++)
                    cache[r][c][k] = isWall(i, j);
        }
        if (!cache[r][c][4]) return;
        g.setColor(Color.BLACK);
        Stroke stroke = g.getStroke();
        g.setStroke(new BasicStroke(w / 5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int centerX = x + w / 2;
        int centerY = y + h / 2;
        for (int i = 0; i < 9; i++)
            if (cache[r][c][i]) {
                int vX = i % 3, vY = i / 3;
                int lX = vX == 0 ? x : vX == 1 ? centerX : x + w;
                int lY = vY == 0 ? y : vY == 1 ? centerY : y + h;
                g.drawLine(lX, lY, centerX, centerY);
            }
        g.setStroke(stroke);
    };

    public MazeCanvas(MazeSolver.Block[][] map) {
        this.painter = painter;
        setMap(map);
    }


    private boolean isWall(int r, int c) {
        return get(r, c) == MazeSolver.Block.WALL;
    }

    private MazeSolver.Block get(int r, int c) {
        try {
            return map[r][c];
        } catch (Exception e) {
            return MazeSolver.Block.WALL;
        }
    }

    public void setMap(MazeSolver.Block[][] map) {
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
}
