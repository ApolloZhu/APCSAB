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

import java.util.Scanner;

/**
 * P-1.26
 * <p>
 * # Test Instruction
 * javac Reverser.java
 * java -cp ../../../../.. net.fcpsschools._1685666._1._1_basics.Reverser < path/to/input
 *
 * @author Apollo/Zhiyu Zhu/朱智语
 */
public class Reverser {
    public static void stdoutReversedSTDIN() {
        Scanner in = new Scanner(System.in);
        StringBuilder out = new StringBuilder();
        while (in.hasNextLine())
            out.insert(0, "\n" + in.nextLine());
        System.out.print(out);
    }

    public static void main(String[] args) {
        stdoutReversedSTDIN();
    }
}
