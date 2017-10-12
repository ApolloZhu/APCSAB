package net.fcpsschools._1685666._1._4_recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ApolloZhu, Pd. 1
 */
public class BacktrackingWarmup {
    final static int m = 3, n = 5;
    static int[][] cache = new int[m][n];
    static ArrayList<String>[][] ways = new ArrayList[m + 1][n + 1];

    static int count(int x, int y) {
        if (x == 0 || y == 0) return 1;
        if (cache[x - 1][y - 1] != 0) return cache[x - 1][y - 1];
        cache[x - 1][y - 1] = count(x - 1, y) + count(x, y - 1) + count(x - 1, y - 1);
        return cache[x - 1][y - 1];
    }

    static List<String> path(int x, int y) {
        if (x == 0 && y == 0) return Arrays.asList("");
        if (ways[x][y] != null) return ways[x][y];
        ArrayList<String> result = new ArrayList<>();
        if (x > 0)
            for (String s : path(x - 1, y))
                result.add(s + "E ");
        if (y > 0)
            for (String s : path(x, y - 1))
                result.add(s + "N ");
        if (x > 0 && y > 0)
            for (String s : path(x - 1, y - 1))
                result.add(s + "NE ");
        return ways[x][y] = result;
    }

    public static void main(String[] args) {
        for (String s : path(m, n)) {
            System.out.println(s);
        }
    }
}
