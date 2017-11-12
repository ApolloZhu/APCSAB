package net.fcpsschools._1685666._2.lab._1_stack;

import java.util.Stack;

/**
 * @author ApolloZhu, Pd. 1
 */
public class Infix {
    public static String toPostfix(String s) {
        if (s == null) return null;
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
                // Handle negative sign
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
                // Find shortest possible operator to use.
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
                boolean mightBeRightAssociateUnary = Operator.isRightAssociateUnary(op);
                boolean isUnary = false;
                boolean closed = c != ')';
                String peek = operators.isEmpty()
                        ? null : operators.peek();
                if (closed && Operator.isBinary(op)) {
                    int prevLast = i - 1 - op.length();
                    String prev = postfix.substring(postfix.lastIndexOf(" ") + 1);
                    int peekLen = peek == null ? 0 : peek.length();
                    boolean hasLHS = false;
                    // Nothing else in front
                    if (i != 1) {
                        // The thing in front is not a number or constant.
                        hasLHS = Operator.isConstant(prev);
                        if (!hasLHS) hasLHS = s.charAt(prevLast) == ')';
                        // Thus that is an operator or parenthesis
                        if (!hasLHS) {
                            // noLHS if such is not left associated
                            String prevOp = s.substring(prevLast + 1 - peekLen, prevLast + 1);
                            assert prevOp.length() == peekLen;
                            hasLHS = prevOp.equals(peek) && Operator.isLeftAssociateUnary(peek);
                        }
                    }
                    if (!hasLHS)
                        if (mightBeRightAssociateUnary) isUnary = true;
                        else throw new IllegalArgumentException(
                                "Missing first operand for binary operator: " + op);
                    // Doesn't have an operand after
                    // FIXME: Has an operand if is a right associate unary op.
                    if (i == s.length() || s.charAt(i) == ')')
                        if (Operator.isLeftAssociateUnary(op)) isUnary = true;
                        else throw new IllegalArgumentException(
                                "Missing second operand for binary operator: " + op);
                }

                while (!operators.isEmpty()) {
                    peek = operators.peek();
                    if (isLower(peek, op)) break;
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
                    if (mightBeRightAssociateUnary && isUnary &&
                            Operator.isRightAssociateUnary(peek))
                        break;
                    postfix.append(' ');
                    postfix.append(operators.pop());
                }
                if (!closed) throw new IllegalArgumentException("extra ')'");
                if (!isUnary) postfix.append(' ');
                if (c != ')') operators.push(op);
            }
        }

        while (!operators.isEmpty()) {
            String top = operators.pop();
            if (top.equals("(")) continue;
            postfix.append(' ');
            postfix.append(top);
        }
        // FIXME: Should correctly space tokens.
        return postfix.toString().replaceAll("\\s+", " ").trim();
    }

    private static boolean isNumber(char c) {
        return "1234567890".indexOf(c) != -1;
    }

    private static boolean isLower(String lhs, String rhs) {
        return Operator.compare(lhs, rhs) == Operator.Relation.LOWER;
    }
}
