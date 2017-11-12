package net.fcpsschools._1685666._2.lab._1_stack;

import java.util.Scanner;
import java.util.Stack;

/**
 * Name: Zhiyu Zhu
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
                if (LEFT.indexOf(stack.pop()) != rI) return false;
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
