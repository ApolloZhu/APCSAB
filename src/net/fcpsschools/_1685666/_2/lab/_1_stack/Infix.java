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

import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * @author Apollo/Zhiyu Zhu/朱智语, Pd. 1
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
                operators.push(new Op(String.valueOf(c), Op.Type.LEFT_PAREN));
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
                String opSymbol = String.valueOf(c);
                boolean isClosingParenthesis = isClosingParenthesis(c);
                boolean closed = !isClosingParenthesis;
                Op thisOp = new Op(opSymbol, Op.Type.UNKNOWN);
                if (closed) {
                    // Find shortest possible operator to use.
                    thisOp = detectNext(s, i - 1);
                    i += (opSymbol = thisOp.getSymbol()).length() - 1;
                    if (thisOp.isInvalid())
                        throw new IllegalArgumentException(
                                "unrecognized operator: " + opSymbol);
                    if (thisOp.isConstant()) {
                        postfix.append(opSymbol);
                        continue;
                    }
                    // Validate current operator.
                    String prev = postfix.substring(postfix.lastIndexOf(" ") + 1);
                    Op.Requirement requirement = operatorMeetsTypeRequirement
                            (s, i - 1, operators, prev, thisOp).get(0);
                    if (requirement != Op.Requirement.MET)
                        throw new IllegalArgumentException(thisOp + " " + requirement);
                }

                Op peek;
                while (!operators.isEmpty()) {
                    peek = operators.peek();
                    if (isLower(peek.getSymbol(), opSymbol)) break;
                    if (peek.isOpeningParenthesis()) {
                        if (isClosingParenthesis) operators.pop();
                        closed = isParenthesisMatch(peek, c);
                        if (!operators.isEmpty() &&
                                operators.peek().isRightAssociateUnary()) {
                            postfix.append(' ');
                            postfix.append(operators.pop().getSymbol());
                        }
                        if (closed || !isClosingParenthesis) break;
                        continue;
                    }
                    // If both are right associate unary, break.
                    if (thisOp.isRightAssociateUnary()
                            && peek.isRightAssociateUnary()) break;
                    postfix.append(' ');
                    postfix.append(operators.pop().getSymbol());
                }

                if (isClosingParenthesis)
                    if (closed) continue;
                    else throw new IllegalArgumentException("extra '" + c + "'");
                if (!thisOp.isUnary()) postfix.append(' ');
                operators.push(thisOp);
            }
        }

        while (!operators.isEmpty()) {
            Op top = operators.pop();
            if (top.isOpeningParenthesis()) continue;
            postfix.append(' ');
            postfix.append(top.getSymbol());
        }
        return postfix.toString();
    }

    protected static Op detectNext(String s, int startIndex) {
        if (startIndex < 0 || startIndex >= s.length())
            return new Op(null, Op.Type.INVALID);
        String op = String.valueOf(s.charAt(startIndex));
        final int MAX_INDEX = s.length() - 1;
        while (true) {
            // FIXME: Inaccurate constant detection.
            if (Operators.isConstant(op))
                return new Op(op, Op.Type.CONSTANT);
            if (Operators.isOperator(op))
                return new Op(op, Op.Type.UNKNOWN);
            if (startIndex < MAX_INDEX) {
                char next = s.charAt(++startIndex);
                if (!isNumber(next) && !isOpeningParenthesis(next)) {
                    op += next;
                    continue;
                }
            }
            return new Op(op, Op.Type.INVALID);
        }
    }

    protected static List<Op.Requirement> operatorMeetsTypeRequirement
            (String s, int startIndex, Stack<Op> operators,
             String previousOperand, Op operator) {

        String op = operator.getSymbol();
        if (operator.isInvalid())
            throw new IllegalArgumentException("Invalid operator '" + op + "'");
        if (operator.isOpeningParenthesis())
            return op.length() == 1 && isOpeningParenthesis(op.charAt(0))
                    ? Collections.singletonList(Op.Requirement.MET) :
                    Collections.singletonList(Op.Requirement.NOT_MET_UNSPECIFIED);

        boolean mightBeRightAssociateUnary = Operators.isRightAssociateUnary(op);
        boolean mightBeLeftAssociateUnary = Operators.isLeftAssociateUnary(op);
        boolean isBinary = Operators.isBinary(op);
        boolean isUnary = !isBinary && Operators.isUnary(op);
        operator.setType(isUnary ? (mightBeLeftAssociateUnary
                ? Op.Type.LEFT_UNARY : Op.Type.RIGHT_UNARY)
                : isBinary ? Op.Type.BINARY : operator.getType());
        Op peek = operators.isEmpty() ? null : operators.peek();
        int prevLast = startIndex - op.length();
        int peekLen = peek == null ? 0 : peek.getSymbol().length();
        boolean hasLHS = false;
        // Nothing else in front
        if (startIndex != 0) {
            // The thing in front is not a number or constant.
            hasLHS = Operators.isConstant(previousOperand);
            if (!hasLHS)
                hasLHS = prevLast > -1 && isClosingParenthesis(s.charAt(prevLast));
            // Thus that is an operator or parenthesis
            if (!hasLHS) {
                // noLHS if such is not left associated
                String prevOp = s.substring(prevLast + 1 - peekLen, prevLast + 1);
                hasLHS = peek != null && prevOp.equals(peek.getSymbol())
                        && peek.isLeftAssociateUnary();
            }
        }
        if (isBinary && !hasLHS)
            if (mightBeRightAssociateUnary) operator.setType(Op.Type.RIGHT_UNARY);
            else return Collections.singletonList(Op.Requirement.NO_LHS);
        // Doesn't have an operand after
        boolean hasRHS = false;
        Op next = detectNext(s, startIndex + 1);
        String nextSymbol = next.getSymbol();
        if (startIndex < s.length() - 1) {
            hasRHS = isOpeningParenthesis(nextSymbol.charAt(0));
            // FIXME: Inaccurate rhs detection.
            if (!hasRHS) hasRHS = Operators.isConstant(nextSymbol);
            if (!hasRHS) hasRHS = Operators.isRightAssociateUnary(nextSymbol);
        }
        if (isBinary && !hasRHS)
            if (mightBeLeftAssociateUnary) operator.setType(Op.Type.LEFT_UNARY);
            else return Collections.singletonList(Op.Requirement.NO_RHS);
        if (operator.isRightAssociateUnary() && hasLHS)
            return Collections.singletonList(Op.Requirement.EXTRA_LHS);
        // FIXME: Inaccurate rhs detection.
        if (operator.isLeftAssociateUnary() && hasRHS
                && !Operators.isBinary(nextSymbol))
            return Collections.singletonList(Op.Requirement.EXTRA_RHS);
        return Collections.singletonList(Op.Requirement.MET);
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
                isParenthesisMatch(op.getSymbol().charAt(0), close);
    }

    protected static boolean isParenthesisMatch(char open, char close) {
        return isOpeningParenthesis(open) &&
                OPEN_PAREN.indexOf(open) == CLOSE_PAREN.indexOf(close);
    }


}

class Op {
    private String symbol;
    private Type type;

    public Op(String symbol, Type type) {
        this.symbol = symbol;
        this.type = type;
    }

    public String getSymbol() {
        return symbol;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return getType() + " " + getSymbol();
    }

    public boolean isBinary() {
        return type == Type.BINARY;
    }

    public boolean isLeftAssociateUnary() {
        return type == Type.LEFT_UNARY;
    }

    public boolean isRightAssociateUnary() {
        return type == Type.RIGHT_UNARY;
    }

    public boolean isUnary() {
        return isLeftAssociateUnary() || isRightAssociateUnary();
    }

    public boolean isOpeningParenthesis() {
        return type == Type.LEFT_PAREN;
    }

    public boolean isConstant() {
        return type == Type.CONSTANT;
    }

    public boolean isTypeUnknwon() {
        return type == Type.UNKNOWN;
    }

    public boolean isInvalid() {
        return type == Type.INVALID;
    }

    enum Type {BINARY, LEFT_UNARY, RIGHT_UNARY, LEFT_PAREN, CONSTANT, UNKNOWN, INVALID}

    enum Requirement {MET, NO_LHS, NO_RHS, EXTRA_LHS, EXTRA_RHS, NOT_MET_UNSPECIFIED}
}
