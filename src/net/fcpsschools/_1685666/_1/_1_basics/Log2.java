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

package net.fcpsschools._1685666._1._1_basics;

/**
 * C-1.19
 *
 * @author Apollo/Zhiyu Zhu/朱智语
 */
public class Log2 {
    public static int log(int num) {
        int count = 0;
        double number = num;
        while (number > 2) {
            number /= 2;
            count++;
        }
        return count;
    }

    /*
     log₂(-1): 0   log₂( 0): 0   log₂( 1): 0
     log₂( 2): 0   log₂( 3): 1   log₂( 4): 1
     log₂( 5): 2   log₂( 6): 2   log₂( 7): 2
     log₂( 8): 2   log₂( 9): 3   log₂(10): 3
     log₂(11): 3   log₂(12): 3   log₂(13): 3
     log₂(14): 3   log₂(15): 3   log₂(16): 3
     log₂(17): 4
     */
    public static void main(String[] args) {
        for (int i = -1; i <= 17; i += 3) {
            for (int j = i; j < i + 3 && j <= 17; j++)
                System.out.printf("log\u2082(%2d): %d   ", j, log(j));
            System.out.println();
        }
    }
}
