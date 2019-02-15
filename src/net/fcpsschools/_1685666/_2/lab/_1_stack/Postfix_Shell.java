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

package net.fcpsschools._1685666._2.lab._1_stack;

import java.util.Scanner;

/**
 * @author Apollo/Zhiyu Zhu/朱智语
 */
public class Postfix_Shell {
    public static void main(String[] args) {
        Operators.printInfo();
        Scanner keyboard = new Scanner(System.in);
        String s = null;
        do {
            try {
                if (s != null) System.out.println(s + "  --->  " + Postfix.eval(s));
            } catch (Exception e) {
                System.err.println(e.getLocalizedMessage());
            }
            System.out.println("\nEnter a valid postfix expression (tokens separated by spaces),");
            System.out.println("such as \"3 5 * 1 +\"; Enter -1 to exit.");
        } while (!(s = keyboard.nextLine()).equals("-1"));
    }
}
