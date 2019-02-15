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
import java.util.Stack;

/**
 * @author Apollo/Zhiyu Zhu/朱智语
 * Date: 2017/11/12
 * Period: 1
 * What I Learned: This is leet code problem 20.
 */
public class ParenMatch_Shell {
    public static final String LEFT = "([{<";
    public static final String RIGHT = ")]}>";

    /**
     * @return if LEFT and RIGHT in @param s matches.
     */
    public static boolean check(String s) {
        if (s == null) return true;
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (LEFT.indexOf(c) != -1)
                stack.push(c);
            else {
                int rI = RIGHT.indexOf(c);
                // Not a closing parenthesis
                if (rI == -1) continue;
                // A closing parenthesis is alone
                if (stack.isEmpty()) return false;
                // Closing parenthesis doesn't match
                if (LEFT.indexOf(stack.pop()) != rI)
                    return false;
            }
        }
        // Has unclosed parentheses
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println("Enter an expression with grouping symbols,");
        System.out.println("such as (2+3)-[5*(6+1)]IndexMals");
        Scanner keyboard = new Scanner(System.in);
        String s;
        while (!(s = keyboard.next()).equals("-1")) {
            System.out.println((check(s)
                    ? s + " is good."
                    : "No, no, no.  Bad.  " + s) + "\n");
        }
    }
}
