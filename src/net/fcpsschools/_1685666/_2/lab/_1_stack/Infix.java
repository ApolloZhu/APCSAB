package net.fcpsschools._1685666._2.lab._1_stack;

import java.util.Stack;

/**
 * @author ApolloZhu, Pd. 1
 */
public class Infix {
    private static final String OPEN_PAREN = "([{<";
    private static final String CLOSE_PAREN = ")]}>";

    public static String toPostfix(String s) {
        if (s == null) return null;
        Stack<Op> operators = new Stack<>();
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
            } else if (isOpeningParenthesis(c)) {
                hasDot = false;
                operators.push(new Op(String.valueOf(c), OperatorType.LEFT_PAREN));
            } else {
                hasDot = false;
                // Handle negative sign
                if (c == '-') {
                    char before = i == 1 ? ' ' : s.charAt(i - 2);
                    if (!isNumber(before) && !isClosingParenthesis(before)) {
                        char after = s.charAt(i);
                        if (isNumber(after) || after == '.') {
                            postfix.append(c);
                            continue;
                        }
                    }
                }
                // Find shortest possible operator to use.
                String op = String.valueOf(c);
                if (!isClosingParenthesis(c)) {
                    while (true) {
                        if (Operators.isConstant(op)) {
                            postfix.append(op);
                            continue MAINLOOP;
                        }
                        if (Operators.isOperator(op)) break;
                        if (i < s.length()) {
                            char next = s.charAt(i++);
                            if (!isNumber(next) && !isOpeningParenthesis(next)) {
                                op += next;
                                continue;
                            }
                        }
                        throw new IllegalArgumentException(
                                "unrecognized operator: " + op);
                    }
                }
                boolean mightBeRightAssociateUnary = Operators.isRightAssociateUnary(op);
                boolean isBinary = Operators.isBinary(op);
                boolean isUnary = !isBinary && Operators.isUnary(op);
                boolean closed = !isClosingParenthesis(c);
                Op peek = operators.isEmpty() ? null : operators.peek();
                if (closed && isBinary) {
                    int prevLast = i - 1 - op.length();
                    String prev = postfix.substring(postfix.lastIndexOf(" ") + 1);
                    int peekLen = peek == null ? 0 : peek.symbol.length();
                    boolean hasLHS = false;
                    // Nothing else in front
                    if (i != 1) {
                        // The thing in front is not a number or constant.
                        hasLHS = Operators.isConstant(prev);
                        if (!hasLHS) hasLHS = isClosingParenthesis(s.charAt(prevLast));
                        // Thus that is an operator or parenthesis
                        if (!hasLHS) {
                            // noLHS if such is not left associated
                            String prevOp = s.substring(prevLast + 1 - peekLen, prevLast + 1);
                            assert prevOp.length() == peekLen;
                            hasLHS = prevOp.equals(peek.symbol) && peek.type == OperatorType.LEFT_UNARY;
                        }
                    }
                    if (!hasLHS)
                        if (mightBeRightAssociateUnary) isUnary = true;
                        else throw new IllegalArgumentException(
                                "Missing first operand for binary operator: " + op);
                    // Doesn't have an operand after
                    // FIXME: Has an operand if is a right associate unary op.
                    if (i == s.length() || isClosingParenthesis(s.charAt(i)))
                        if (Operators.isLeftAssociateUnary(op)) isUnary = true;
                        else throw new IllegalArgumentException(
                                "Missing second operand for binary operator: " + op);
                }

                while (!operators.isEmpty()) {
                    peek = operators.peek();
                    if (isLower(peek.symbol, op)) break;
                    if (peek.type == OperatorType.LEFT_PAREN) {
                        if (isParenthesisMatch(peek, c)) {
                            operators.pop();
                            closed = true;
                        }
                        if (!operators.isEmpty() &&
                                operators.peek().type == OperatorType.RIGHT_UNARY) {
                            postfix.append(' ');
                            postfix.append(operators.pop());
                        }
                        break;
                    }
                    if (mightBeRightAssociateUnary && isUnary &&
                            peek.type == OperatorType.RIGHT_UNARY)
                        break;
                    postfix.append(' ');
                    postfix.append(operators.pop());
                }

                if (!closed) throw new IllegalArgumentException("extra '" + c + "'");
                if (isClosingParenthesis(c)) continue;
                if (!isUnary) postfix.append(' ');
                operators.push(new Op(op, isBinary ? OperatorType.BINARY :
                        mightBeRightAssociateUnary ? OperatorType.RIGHT_UNARY
                                : OperatorType.LEFT_UNARY));
            }
        }

        while (!operators.isEmpty()) {
            Op top = operators.pop();
            if (top.type == OperatorType.LEFT_PAREN) continue;
            postfix.append(' ');
            postfix.append(top);
        }
        return postfix.toString();
    }

    protected static boolean isNumber(char c) {
        return "1234567890".indexOf(c) != -1;
    }

    protected static boolean isLower(String lhs, String rhs) {
        return Operators.compare(lhs, rhs) == Operators.Relation.LOWER;
    }

    protected static boolean isOpeningParenthesis(char c) {
        return OPEN_PAREN.indexOf(c) != -1;
    }

    protected static boolean isClosingParenthesis(char c) {
        return CLOSE_PAREN.indexOf(c) != -1;
    }

    private static boolean isParenthesisMatch(Op op, char close) {
        return op.symbol.length() == 1 && isParenthesisMatch(op.symbol.charAt(0), close);
    }

    protected static boolean isParenthesisMatch(char open, char close) {
        return isOpeningParenthesis(open) &&
                OPEN_PAREN.indexOf(open) == CLOSE_PAREN.indexOf(close);
    }

    enum OperatorType {BINARY, LEFT_UNARY, RIGHT_UNARY, LEFT_PAREN}

    private static class Op {
        private String symbol;
        private OperatorType type;

        public Op(String symbol, OperatorType type) {
            this.symbol = symbol;
            this.type = type;
        }

        @Override
        public String toString() {
            return symbol;
        }
    }
}
