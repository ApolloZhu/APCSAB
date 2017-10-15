package net.fcpsschools._1685666._1.lab._4_recursion.eight_queen;

import java.awt.*;

/**
 * @author ApolloZhu, Pd. 1
 */
public interface PiecePainter {
    static PiecePainter makeCirclePainter(Color color) {
        return new PiecePainter() {
            @Override
            public void paintPiece(Graphics g, int r, int c, int x, int y, int w, int h) {
                Color original = g.getColor();
                g.setColor(color);
                g.fillOval(x, y, w, h);
                g.setColor(original);
            }
        };
    }

    void paintPiece(Graphics g, int r, int c, int x, int y, int w, int h);
}
