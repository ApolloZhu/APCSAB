package net.fcpsschools._1685666._1.lab._4_recursion;

//name: Zhiyu Zhu
//date: Oct. 7, 2017
// Practiced using the NIO package, the stream APIs,
// try-with-resources, checking array indices in a weird way

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class AreaFill_shell {
    public static char[][] grid = null;

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Filename: ");
            String filename = sc.next();
            if ((grid = read(filename)) == null) System.exit(0);
            while (true) {
                display(grid);
                System.out.print("\nEnter ROW COL to fill from: ");
                int row = sc.nextInt();
                int col = sc.nextInt();
                if (!areValidIndices(grid, row, col)) break;
                char old = grid[row][col], with;
                System.out.print("Replace " + old + " with: ");
                with = sc.next().charAt(0);
                fill(grid, row, col, old, with);
            }
        }
    }

    public static char[][] read(String filename) {
        try {
            // Read contents of the file.
            List<String> contents =
                    Files.readAllLines(Paths.get(filename));
            // Get rid of the first line.
            contents.remove(0);
            return contents.stream()
                    // Filter out empty lines.
                    .filter(line -> line.length() > 1)
                    // Transform each string to array of chars.
                    .map(line -> line.toCharArray())
                    // Collect the arrays into a 2D array.
                    .toArray(size -> new char[size][]);
        } catch (Exception e) {
            // Handle file not found, etc.
            return null;
        }
    }

    public static void display(char[][] g) {
        if (g == null) return;
        for (char[] row : g) {
            for (char ch : row)
                System.out.print(ch);
            System.out.println();
        }
    }

    /**
     * Checks if the indices are valid.
     *
     * @param g the 2D array to access.
     * @param r the row to access.
     * @param c the column to access.
     * @return if the indices are within range.
     */
    private static boolean areValidIndices(char[][] g, int r, int c) {
        try { // Be bold session.
            char ch = g[r][c];
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void fill(char[][] g, int r, int c, char ch, char with) {
        // Safety first.
        if (!areValidIndices(g, r, c) || g[r][c] != ch) return;
        g[r][c] = with; // Fill myself,
        // then fill my neighbors.
        fill(g, r - 1, c, ch, with);
        fill(g, r + 1, c, ch, with);
        fill(g, r, c - 1, ch, with);
        fill(g, r, c + 1, ch, with);
    }
}
