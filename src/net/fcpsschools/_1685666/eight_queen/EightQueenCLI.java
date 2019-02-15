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

import java.util.Scanner;

/**
 * @author Apollo/Zhiyu Zhu/朱智语, Pd. 1
 */
public class EightQueenCLI implements EightQueenSolver.MoveEventListener {
    private static final Scanner in = new Scanner(System.in);

    static void print(boolean[][] board) {
        for (boolean[] row : board) {
            for (boolean hasQueen : row)
                System.out.print(hasQueen ? "x" : "o");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        EightQueenSolver solver = new EightQueenSolver(8);
        solver.addMoveEventListener(new EightQueenCLI());
        solver.start();
    }

    @Override
    public void check(int queenCount, boolean[][] board, int r, int c) {
        System.out.println("Checking: " + r + "," + c);
    }

    @Override
    public void placed(int queenCount, boolean[][] board, int r, int c) {
        System.out.println("Placed at " + r + "," + c);
    }

    @Override
    public void failed(int queenCount, boolean[][] board) {
        System.out.println("Failed when " + queenCount);
    }

    @Override
    public void restore(int queenCount, boolean[][] board, int rRestored, int cRestored) {
        System.out.println("Revoke at " + rRestored + "," + cRestored);
    }

    @Override
    public void found(boolean[][] board, long solutionCount) {
        print(board);
        System.out.print("Return to continue...");
        in.nextLine();
    }

    @Override
    public void ended(long solutionCount) {
        System.out.println(solutionCount > 0
                ? solutionCount + " Solutions."
                : "Unsolvable.");
    }
}
