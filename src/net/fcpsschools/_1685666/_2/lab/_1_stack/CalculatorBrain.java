package net.fcpsschools._1685666._2.lab._1_stack;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * @author ApolloZhu, Pd. 1
 */
public class CalculatorBrain {
    public static <E> String toString(Stack<E> stack) {
        StringBuilder sb = new StringBuilder();
        for (E element : stack) {
            sb.append(element);
            sb.append(' ');
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static double evaluatePostfix(String s) {
        Stack<String> operands = new Stack<>();
        // Remove extra empty spaces.
        for (String token : s.trim()
                .replaceAll("\\s+", " ")
                .split(" ")) {
            // Binary operators require 2 operands
            // Thus we shall check if that is satisfied.
            if (Operator.isBinary(token)) {
                String rhs = null;
                try {
                    // We may or may not get our operands.
                    rhs = operands.pop();
                    double result = Operator.evaluate(
                            token, operands.pop(), rhs);
                    // We put the evaluated result back.
                    operands.push(String.valueOf(result));
                    continue;
                } catch (EmptyStackException e) {
                    // Push the operand back if not used.
                    if (rhs != null) operands.push(rhs);
                }
            }
            // Some operator might be both binary and unary.
            // For example, + and -.
            // Will throw empty stack exception if no operand.
            if (Operator.isUnary(token))
                operands.push(String.valueOf(Operator
                        .evaluate(token, operands.pop())));
            else // Constants has no requirements for operands.
                if (Operator.isConstant(token))
                    operands.push(token);
                else throw new IllegalArgumentException(
                        "unrecognized token '" + token + "'");
        }
        double result = Operator.evaluate(operands.pop());
        // We shall have no more operands left.
        if (!operands.isEmpty())
            throw new IllegalArgumentException(
                    "unused operands: " + toString(operands));
        return result;
    }
}
