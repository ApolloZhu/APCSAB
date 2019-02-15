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

package net.fcpsschools._1685666._1._4_recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Apollo/Zhiyu Zhu/朱智语, Pd. 1
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
