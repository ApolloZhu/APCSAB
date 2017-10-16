package net.fcpsschools._1685666._1._4_recursion;

/**
 * @author ApolloZhu, Pd. 1
 */
public class PathFindingWarmup {
    public static void main(String[] args) {
        traverse(new int[][]{
                {0, 1, 2, 3, 4},
                {10, 11, 12, 13, 14},
                {20, 21, 22, 23, 24},
                {30, 31, 32, 33, 34},
                {40, 41, 42, 43, 44}
        }, 0, 0);
        System.out.println("\n");
        permute("", "ABC");
    }

    static void traverse(int[][] grid, int r, int c) {
        System.out.print(grid[r][c] + " ");
        if (c < grid[r].length - 1) traverse(grid, r, c + 1);
        else if (r < grid.length - 1) traverse(grid, r + 1, 0);
    }

    static void permute(String sofar, String rest) {
        if (rest.length() == 0) System.out.println(sofar);
        else
            for (int i = 0; i < rest.length(); i++)
                permute(sofar + rest.substring(i, i + 1),
                        rest.substring(0, i)
                                + rest.substring(i + 1));
    }
}
