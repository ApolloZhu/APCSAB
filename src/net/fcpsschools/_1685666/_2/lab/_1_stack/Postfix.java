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

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * @author Apollo/Zhiyu Zhu/朱智语, Pd. 1
 */
public class Postfix {
    public static <E> String toString(Stack<E> stack) {
        if (stack == null) return null;
        StringBuilder sb = new StringBuilder();
        for (E element : stack) {
            sb.append(element);
            sb.append(' ');
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static double eval(String s) {
        if (s == null) return Double.NaN;
        Stack<String> operands = new Stack<>();
        // Remove extra empty spaces.
        for (String token : s.trim()
                .replaceAll("\\s+", " ")
                .split(" ")) {
            // Binary operators require 2 operands
            // Thus we shall check if that is satisfied.
            if (Operators.isBinary(token)) {
                String rhs = null;
                try {
                    // We may or may not get our operands.
                    rhs = operands.pop();
                    double result = Operators.evaluate(
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
            if (Operators.isUnary(token))
                operands.push(String.valueOf(Operators
                        .evaluate(token, operands.pop())));
            else // Constants has no requirements for operands.
                if (Operators.isConstant(token))
                    operands.push(token);
                else throw new IllegalArgumentException(
                        "unrecognized token '" + token + "'");
        }
        double result = Operators.evaluate(operands.pop());
        // We shall have no more operands left.
        if (!operands.isEmpty())
            throw new IllegalArgumentException(
                    "unused operands: " + toString(operands));
        return result;
    }
}
