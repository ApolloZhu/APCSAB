package net.fcpsschools._1685666._1.lab._4_recursion;

/**
 * Name: Zhiyu Zhu
 * Period: Period 1
 * AreaFill_shell.java - Recursively fill an area
 * Due Date: 10/17 23:59
 * Date Submitted:
 * ## What I learned:
 * - Using JFileChooser to select a file from the disk
 * - Using the NIO package for reading contents of a file
 * - Using method references in stream APIs
 * ## How I feel about this lab:
 * - Very relaxing.
 * ## What I wonder:
 * - Is it more efficient to use try-catch or 4 comparisons
 * when accessing array elements through indices.
 */

import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class AreaFill_shell {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Choose a Map");
            while (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) ;
            char[][] grid = read(chooser.getSelectedFile().getAbsolutePath());
            if (grid == null) System.exit(0);

            while (true) {
                display(grid);

                System.out.print("\nEnter ROW COL to fill from: ");
                int row = sc.nextInt();
                int col = sc.nextInt();
                // Input invalid indices to quit.
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
            return Files.readAllLines(Paths.get(filename))
                    // Convert to a stream without first line
                    // Because we don't need row and col count
                    .stream().skip(1)
                    // Filter out empty lines.
                    .filter(line -> line.length() > 0)
                    // Transform each string to array of chars.
                    .map(String::toCharArray)
                    // Collect the arrays into a 2D array.
                    .toArray(char[][]::new);
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
        try {
            // Handles both g == null
            // And index out of bounds.
            char ignored = g[r][c];
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
/*
xxxx............................
...xx...........................
..xxxxxxxxxxxx..................
..x.........xxxxxxx.............
..x...........0000xxxx..........
..xxxxxxxxxxxx0..000............
..xxxxxxxxx...0...00.....0000000
..........xx.......0000000000000
.....xxxxxxxxx........0.........
....xx.................00000....
....xx.....................00...
.....xxxxxxxxxxxxxxxxxx....00...
......................xx...00...
.......................xxxx00000
............................0000

Enter ROW COL to fill from: 0 0
Replace x with: 0
0000............................
...00...........................
..000000000000..................
..0.........0000000.............
..0...........00000000..........
..0000000000000..000............
..000000000...0...00.....0000000
..........00.......0000000000000
.....000000000........0.........
....00.................00000....
....00.....................00...
.....000000000000000000....00...
......................00...00...
.......................000000000
............................0000

Enter ROW COL to fill from: 0 31
Replace . with: +
0000++++++++++++++++++++++++++++
...00+++++++++++++++++++++++++++
..000000000000++++++++++++++++++
..0.........0000000+++++++++++++
..0...........00000000++++++++++
..0000000000000..000++++++++++++
..000000000...0...00+++++0000000
..........00.......0000000000000
.....000000000........0.........
....00.................00000....
....00.....................00...
.....000000000000000000....00...
......................00...00...
.......................000000000
............................0000

Enter ROW COL to fill from: 14 0
Replace . with: -
0000++++++++++++++++++++++++++++
---00+++++++++++++++++++++++++++
--000000000000++++++++++++++++++
--0.........0000000+++++++++++++
--0...........00000000++++++++++
--0000000000000..000++++++++++++
--000000000...0...00+++++0000000
----------00.......0000000000000
-----000000000........0.........
----00.................00000....
----00.....................00...
-----000000000000000000....00...
----------------------00...00...
-----------------------000000000
----------------------------0000

Enter ROW COL to fill from: 14 31
Replace 0 with: *
****++++++++++++++++++++++++++++
---**+++++++++++++++++++++++++++
--************++++++++++++++++++
--*.........*******+++++++++++++
--*...........********++++++++++
--*************..***++++++++++++
--*********...*...**+++++*******
----------**.......*************
-----*********........*.........
----**.................*****....
----**.....................**...
-----******************....**...
----------------------**...**...
-----------------------*********
----------------------------****

Enter ROW COL to fill from: 7 15
Replace . with: ?
****++++++++++++++++++++++++++++
---**+++++++++++++++++++++++++++
--************++++++++++++++++++
--*.........*******+++++++++++++
--*...........********++++++++++
--*************??***++++++++++++
--*********???*???**+++++*******
----------**???????*************
-----*********????????*.........
----**?????????????????*****....
----**?????????????????????**...
-----******************????**...
----------------------**???**...
-----------------------*********
----------------------------****

Enter ROW COL to fill from: -1 -1
*/
