package net.fcpsschools._1685666._2.lab._1_stack;

import java.util.Stack;

/**
 * @author ApolloZhu, Pd. 1
 */
public class InfixToPostfix {
    public static String convert(String s) {
        Stack<String> operators = new Stack<>();
        StringBuilder postfix = new StringBuilder();
        s = s.replaceAll("\\s", "");
        int i = 0;
        boolean hasDot = false;
        MAINLOOP:
        while (i < s.length()) {
            char c = s.charAt(i++);
            if (isNumber(c)) {
                postfix.append(c);
            } else if (c == '.') {
                if (hasDot) throw new NumberFormatException();
                postfix.append(c);
                hasDot = true;
            } else if (c == '(') {
                hasDot = false;
                operators.push("(");
            } else {
                hasDot = false;
                if (c == '-') {
                    char before = i == 1 ? ' ' : s.charAt(i - 2);
                    if (!isNumber(before) && before != ')') {
                        char after = s.charAt(i);
                        if (isNumber(after) || after == '.') {
                            postfix.append(c);
                            continue;
                        }
                    }
                }
                String op = "" + c;
                if (c != ')') {
                    while (true) {
                        if (Operator.isConstant(op)) {
                            postfix.append(op);
                            continue MAINLOOP;
                        }
                        if (Operator.isOperator(op)) break;
                        if (i < s.length()) {
                            char next = s.charAt(i++);
                            if (!isNumber(next) &&
                                    "()".indexOf(next) == -1) {
                                if (next != ' ') op += next;
                                continue;
                            }
                        }
                        throw new IllegalArgumentException(
                                "unrecognized operator: " + op);
                    }
                }
                boolean closed = false;
                while (!operators.isEmpty()) {
                    if (isLower(operators.peek(), c))
                        break;
                    if (operators.peek().equals("(")) {
                        if (c == ')') {
                            operators.pop();
                            closed = true;
                        }
                        break;
                    }
                    postfix.append(' ');
                    postfix.append(operators.pop());
                }
                if (c != ')') {
                    if (Operator.isBinary(op)) {
                        boolean isUnary = false;
                        if (i == 1 || s.charAt(i - 2) == '(')
                            if (op.equals("+") || op.equals("-"))
                                isUnary = true;
                            else throw new IllegalArgumentException(
                                    "Missing first operand for binary operator: " + op);
                        if (i == s.length())
                            throw new IllegalArgumentException(
                                    "Missing second operand for binary operator: " + op);
                        if (!isUnary) postfix.append(' ');
                    }
                    operators.push(op);
                } else if (!closed)
                    throw new IllegalArgumentException("extra ')'");
            }
        }

        while (!operators.isEmpty()) {
            String top = operators.pop();
            if (top.equals("(")) continue;
            postfix.append(' ');
            postfix.append(top);
        }
        return postfix.toString();
    }

    private static boolean isNumber(char c) {
        return "1234567890".indexOf(c) != -1;
    }

    private static boolean isLower(String lhs, char rhs) {
        return Operator.compare(lhs, String.valueOf(rhs))
                == Operator.Relation.LOWER;
    }
}
