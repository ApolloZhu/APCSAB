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
                boolean isClosingParenthesis = isClosingParenthesis(c);
                boolean closed = !isClosingParenthesis;
                if (closed) {
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
                boolean mightBeLeftAssociateUnary = Operators.isLeftAssociateUnary(op);
                boolean isBinary = Operators.isBinary(op);
                boolean isUnary = !isBinary && Operators.isUnary(op);
                Op peek = operators.isEmpty() ? null : operators.peek();
                if (closed) {
                    int prevLast = i - 1 - op.length();
                    String prev = postfix.substring(postfix.lastIndexOf(" ") + 1);
                    int peekLen = peek == null ? 0 : peek.symbol.length();
                    boolean hasLHS = false;
                    // Nothing else in front
                    if (i != 1) {
                        // The thing in front is not a number or constant.
                        hasLHS = Operators.isConstant(prev);
                        if (!hasLHS)
                            hasLHS = prevLast > -1 && isClosingParenthesis(s.charAt(prevLast));
                        // Thus that is an operator or parenthesis
                        if (!hasLHS) {
                            // noLHS if such is not left associated
                            String prevOp = s.substring(prevLast + 1 - peekLen, prevLast + 1);
                            hasLHS = peek != null && prevOp.equals(peek.symbol)
                                    && peek.isLeftAssociateUnary();
                        }
                    }
                    if (isBinary && !hasLHS)
                        if (mightBeRightAssociateUnary) isUnary = true;
                        else throw new IllegalArgumentException(
                                "Missing first operand for binary operator: " + op);
                    // Doesn't have an operand after
                    boolean hasRHS = false;
                    if (i < s.length()) {
                        hasRHS = isOpeningParenthesis(s.charAt(i));
                        // FIXME: Inability to recognize token with length more than one.
                        String next = String.valueOf(s.charAt(i));
                        if (!hasRHS) hasRHS = Operators.isConstant(next);
                        if (!hasRHS) hasRHS = Operators.isRightAssociateUnary(next);
                    }
                    if (isBinary && !hasRHS)
                        if (mightBeLeftAssociateUnary) isUnary = true;
                        else throw new IllegalArgumentException(
                                "Missing second operand for binary operator: " + op);
                    if (isUnary) {
                        if (mightBeRightAssociateUnary && hasLHS)
                            throw new IllegalArgumentException("Extra left hand side operand " +
                                    "for right associate unary operator '" + op + "'");
                        if (mightBeLeftAssociateUnary && hasRHS)
                            throw new IllegalArgumentException("Extra right hand side operand " +
                                    "for left associate unary operator '" + op + "'");
                    }
                }

                while (!operators.isEmpty()) {
                    peek = operators.peek();
                    if (isLower(peek.symbol, op)) break;
                    if (peek.isOpeningParenthesis()) {
                        if (isClosingParenthesis) operators.pop();
                        closed = isParenthesisMatch(peek, c);
                        if (!operators.isEmpty() &&
                                operators.peek().isRightAssociateUnary()) {
                            postfix.append(' ');
                            postfix.append(operators.pop());
                        }
                        if (closed || !isClosingParenthesis) break;
                        continue;
                    }
                    // If both are right associate unary, break.
                    if (mightBeRightAssociateUnary && isUnary
                            && peek.isRightAssociateUnary()) break;
                    postfix.append(' ');
                    postfix.append(operators.pop());
                }

                if (isClosingParenthesis)
                    if (closed) continue;
                    else throw new IllegalArgumentException("extra '" + c + "'");
                if (!isUnary) postfix.append(' ');
                operators.push(new Op(op, isBinary ? OperatorType.BINARY :
                        mightBeRightAssociateUnary ? OperatorType.RIGHT_UNARY
                                : OperatorType.LEFT_UNARY));
            }
        }

        while (!operators.isEmpty()) {
            Op top = operators.pop();
            if (top.isOpeningParenthesis()) continue;
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
        return op.isOpeningParenthesis() &&
                isParenthesisMatch(op.symbol.charAt(0), close);
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

        public boolean isBinary() {
            return type == OperatorType.BINARY;
        }

        public boolean isLeftAssociateUnary() {
            return type == OperatorType.LEFT_UNARY;

        }

        public boolean isRightAssociateUnary() {
            return type == OperatorType.RIGHT_UNARY;
        }

        public boolean isOpeningParenthesis() {
            return type == OperatorType.LEFT_PAREN;
        }
    }
}
