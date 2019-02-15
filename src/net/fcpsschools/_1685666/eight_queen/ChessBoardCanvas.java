/*
MIT License

Copyright (c) 2017-2019 Apollo/Zhiyu Zhu/朱智语 <public-apollonian@outlook.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package net.fcpsschools._1685666.eight_queen;

import javax.swing.*;
import java.awt.*;

/**
 * @author Apollo/Zhiyu Zhu/朱智语, Pd. 1
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

    public void setSize(int size) {
        this.sizeCount = size;
        repaint();
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
                if (i == 0)
                    g.drawString((sizeCount - j) + "",
                            x, y + side / 2);
                else if (j == sizeCount)
                    g.drawString(EightQueenSolver.legendX(i - 1).toUpperCase(),
                            x, y + side / 2);
                else {
                    g.setColor((i + j) % 2 == 1 ? light : dark);
                    g.fillRect(x, y, side, side);
                    painter.paintPiece(g, i - 1, j, x, y, side, side);
                }
            }
    }
}
