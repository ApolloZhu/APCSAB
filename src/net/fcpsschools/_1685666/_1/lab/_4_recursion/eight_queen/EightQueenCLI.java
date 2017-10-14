package net.fcpsschools._1685666._1.lab._4_recursion.eight_queen;

import java.util.Scanner;

/**
 * @author ApolloZhu, Pd. 1
 */
public class EightQueenCLI implements EightQueenSolver.MoveEventListener {
    private static final Scanner in = new Scanner(System.in);
    private int solutionCount;

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
    public void found(boolean[][] board) {
        solutionCount++;
        print(board);
        // System.out.print("Return to continue...");
        // in.nextLine();
    }

    @Override
    public void ended() {
        System.out.println(solutionCount > 0
                ? solutionCount + " Solutions."
                : "Unsolvable.");
    }
}
