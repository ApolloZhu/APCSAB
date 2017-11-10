package net.fcpsschools._1685666._2.lab._1_stack;

import java.util.Scanner;
import java.util.Stack;

/**
 * @author ApolloZhu, Pd. 1
 */
public class ParenMatch {
    public static final String LEFT = "([{<";
    public static final String RIGHT = ")]}>";

    public static boolean check(String s) {
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
        String expression = new Scanner(System.in).nextLine();
        System.out.println(check(expression)
                ? expression + " is good."
                : "No. Bad. " + expression);
    }
}
