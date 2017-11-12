package net.fcpsschools._1685666._2.lab._1_stack;

import java.util.Scanner;
import java.util.Stack;

/**
 * @author ApolloZhu, Pd. 1
 * AP$$-Compp-utee-r Sic--cei--ience
 * Ca-noe$Ra3-fx-t
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
            } else
                stack.push(c);
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
