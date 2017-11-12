package net.fcpsschools._1685666._2.lab._1_stack;

import java.util.Stack;

/**
 * @author ApolloZhu, Pd. 1
 */
public class Infix {
    public static String toPostfix(String s) {
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
                String op = String.valueOf(c);
                if (c != ')') {
                    while (true) {
                        if (Operator.isConstant(op)) {
                            postfix.append(op);
                            continue MAINLOOP;
                        }
                        if (Operator.isOperator(op)) break;
                        if (i < s.length()) {
                            char next = s.charAt(i++);
                            if (!isNumber(next) && next != '(') {
                                op += next;
                                continue;
                            }
                        }
                        throw new IllegalArgumentException(
                                "unrecognized operator: " + op);
                    }
                }
                boolean closed = false;
                boolean isRightAssociateUnary = Operator.isRightAssociateUnary(op);
                while (!operators.isEmpty()) {
                    String peek = operators.peek();
                    if (isLower(peek, op))
                        break;
                    if (peek.equals("(")) {
                        if (c == ')') {
                            operators.pop();
                            closed = true;
                        }
                        if (!operators.isEmpty() &&
                                Operator.isRightAssociateUnary(operators.peek())) {
                            postfix.append(' ');
                            postfix.append(operators.pop());
                        }
                        break;
                    }
                    if (isRightAssociateUnary &&
                            Operator.isRightAssociateUnary(peek))
                        break;
                    postfix.append(' ');
                    postfix.append(operators.pop());
                }
                if (c != ')') {
                    if (Operator.isBinary(op)) {
                        boolean isUnary = false;
                        int index;
                        if (i == 1 || // Nothing else in front
                                // Or the one in front is not a number
                                !isNumber(s.charAt(i - 1 - op.length()))
                                        && (index = postfix.lastIndexOf(" ")) != -1
                                        && !Operator.isConstant(postfix.substring(index + 1))
                                        // Thus that is an operator,
                                        // noLHS if such is not left associated
                                        && s.charAt(i - 1 - op.length()) != ')'
                                        && !operators.isEmpty() &&
                                        !Operator.isLeftAssociateUnary(operators.peek()))
                            if (Operator.isRightAssociateUnary(op))
                                isUnary = true;
                            else throw new IllegalArgumentException(
                                    "Missing first operand for binary operator: " + op);
                        // Doesn't have an operand after
                        // FIXME: Has an operand if is a right associate unary op.
                        if (i == s.length() || s.charAt(i) == ')')
                            if (Operator.isLeftAssociateUnary(op))
                                isUnary = true;
                            else throw new IllegalArgumentException(
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

    private static boolean isLower(String lhs, String rhs) {
        return Operator.compare(lhs, rhs) == Operator.Relation.LOWER;
    }
}
