package net.fcpsschools._1685666._1.lab._4_recursion.eight_queen;

import javax.swing.*;
import java.awt.*;

/**
 * @author ApolloZhu, Pd. 1
 */
public class ChessBoardCanvas extends JPanel {
    private int sizeCount;

    private Color light = Color.WHITE;
    private Color dark = Color.BLACK;
    private PiecePainter painter;

    public ChessBoardCanvas(int size, PiecePainter painter) {
        this.sizeCount = size;
        this.painter = painter;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int total = sizeCount + 1;
        int side = Math.min(getWidth(), getHeight()) / total;
        int xOffset = (getWidth() - side * total) / 2;
        int yOffset = (getHeight() - side * total) / 2;
        for (int i = 0; i < total; i++)
            for (int j = 0; j < total; j++) {
                if (i == 0 && j == sizeCount) continue;
                int x = xOffset + side * i;
                int y = yOffset + side * j;
                g.setColor(Color.BLACK);
                if (i == 0) {
                    g.drawString((sizeCount - j) + "",
                            x, y + side / 2);
                } else if (j == sizeCount) {
                    g.drawString(EightQueenSolver.legendX(i - 1),
                            x, y + side / 2);
                } else {
                    g.setColor((i + j) % 2 == 1 ? light : dark);
                    g.fillRect(x, y, side, side);
                    painter.paintPiece(g, i - 1, j, x, y, side, side);
                }
            }
    }
}
