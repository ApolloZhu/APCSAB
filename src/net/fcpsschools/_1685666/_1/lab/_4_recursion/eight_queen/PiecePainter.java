package net.fcpsschools._1685666._1.lab._4_recursion.eight_queen;

import javax.swing.*;
import java.awt.*;

/**
 * @author ApolloZhu, Pd. 1
 */
public interface PiecePainter {
    Image QUEEN = new ImageIcon(PiecePainter.class.getResource("queen.png")).getImage();
    Color CLEAR = new Color(0, 0, 0, 0);

    static PiecePainter makeCirclePainter(Color color) {
        return (g, r, c, x, y, w, h) -> {
            Color original = g.getColor();
            g.setColor(color);
            g.fillOval(x, y, w, h);
            g.setColor(original);
        };
    }

    static PiecePainter makeQueenPainter(Color color) {
        return (g, r, c, x, y, w, h) -> {
            makeCirclePainter(color).paintPiece(g, r, c, x, y, w, h);
            g.drawImage(QUEEN, x, y, w, h, null);
        };
    }

    void paintPiece(Graphics g, int r, int c, int x, int y, int w, int h);
}
