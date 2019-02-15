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
 * @author Apollo/Zhiyu Zhu/朱智语, Pd. 1
 * What I learned: Stack.
 */
public class TextEditor {
    static <E> String dump(Stack<E> stack) {
        if (stack.isEmpty()) return "";
        E top = stack.pop();
        return dump(stack) + top;
    }

    static Stack<Character> process(String s) {
        if (s == null) return null;
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray())
            if (c == '$')
                while (!stack.isEmpty())
                    stack.pop();
            else if (c == '-') {
                if (!stack.isEmpty())
                    stack.pop();
            } else stack.push(c);
        return stack;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Stack<Character> stack;
        do {
            System.out.print("Enter a line of text: ");
            stack = process(in.nextLine());
            System.out.print("Here is the line you entered: ");
            System.out.println(dump(stack));
            System.out.print("Again (y/n)? ");
        } while (!in.nextLine().toLowerCase().equals("n"));
    }
}

// Enter a line of text: AP$$-Compp-utee-r Sic--cei--ience
// Here is the line you entered: Computer Science
// Again (y/n)?
// Enter a line of text: Ca-noe$Ra3-fx-t
// Here is the line you entered: Raft
// Again (y/n)? n
