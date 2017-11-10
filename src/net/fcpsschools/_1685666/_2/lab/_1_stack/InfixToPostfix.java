package net.fcpsschools._1685666._2.lab._1_stack;

import java.util.Stack;

/**
 * @author ApolloZhu, Pd. 1
 */
public class InfixToPostfix {
    public static String convert(String s) {
        Stack<Character> operators = new Stack<>();
        StringBuilder postfix = new StringBuilder();
        for (char c : s.toCharArray()) {
            if ("1234567890".indexOf(c) != -1) {
                postfix.append(c);
            } else if (c == '(') {
                operators.push(c);
            } else {
                while (!operators.isEmpty()) {
                    if (isLower(operators.peek(), c))
                        break;
                    if (operators.peek() == '(') {
                        if (c == ')') operators.pop();
                        break;
                    }
                    postfix.append(" ");
                    postfix.append(operators.pop());
                }
                if (c != ')') {
                    postfix.append(" ");
                    operators.push(c);
                }
            }
        }
        while (!operators.isEmpty()) {
            postfix.append(" ");
            postfix.append(operators.pop());
        }
        return postfix.toString();
    }

    private static boolean isLower(char lhs, char rhs) {
        return Operator.compare("" + lhs, "" + rhs)
                == Operator.Relation.LOWER;
    }
}
