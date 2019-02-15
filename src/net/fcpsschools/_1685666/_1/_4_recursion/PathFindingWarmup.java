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

/**
 * @author Apollo/Zhiyu Zhu/朱智语, Pd. 1
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
