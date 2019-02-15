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

package net.fcpsschools._1685666._2.lab._4_tree;

import net.fcpsschools._1685666._2.lab._1_stack.Operators;

import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Represents a binary expression tree,
 * supports decimal and negative.
 *
 * @author Apollo/Zhiyu Zhu/朱智语
 * @since 1/18/18
 */
public class BXT {
    private static final TreeFormatter<String> formatter = new TreeFormatter<>();
    private TreeNode<String> root;

    public BXT() {
    }

    public BXT(String obj) {
        buildTree(obj);
    }

    /***
     * Evaluating the node.
     * @param root the node to start from
     * @apiNote recursive
     */
    private static double evaluateNode(TreeNode<String> root) {
        if (null == root) return Double.NaN;
        String val = root.getValue();
        // If the root is a operator
        if (isOperator(val)) {
            // Return the result of left <op> right.
            return computeTerm(val,
                    // Recursively evaluate the left subtree (let the value left)
                    evaluateNode(root.getLeft()),
                    // Recursively evaluate the right subtree (let the value right)
                    evaluateNode(root.getRight()));
        } else { // not an inner node, the node is a number
            // Evaluate the leaf node, convert into a double, and return.
            return Operators.evaluate(val);
        }
    }

    private static double computeTerm(String s, double a, double b) {
        if (Operators.isBinary(s)) {
            return Operators.evaluate(s, "" + a, "" + b);
        } else if (Operators.isLeftAssociateUnary(s)) {
            return Operators.evaluate(s, "" + a);
        } else if (Operators.isRightAssociateUnary(s)) {
            return Operators.evaluate(s, "" + b);
        } else throw new IllegalStateException(s + " is not an operator");
    }

    private static boolean isOperator(String s) {
        return Operators.isOperator(s);
    }

    /**
     * inorder traverse
     *
     * @param node
     */
    private static void printExpression(TreeNode<String> node, String parent) {
        if (null == node) return;
        String operator = node.getValue();
        boolean isLeaf = null == node.getLeft() && null == node.getRight();
        boolean isNegative = isLeaf && operator.startsWith("-");
        boolean lowerPrecedence = null != parent &&
                Operators.Relation.LOWER == Operators.compare(operator, parent);
        boolean isRightAssociate = !Operators.isBinary(parent)
                && Operators.isRightAssociateUnary(parent);
        boolean needsParenthesis = isRightAssociate || lowerPrecedence || isNegative;
        System.out.print(needsParenthesis ? "(" : "");
        // print “(“ before traversing left subtree
        printExpression(node.getLeft(), operator);
        // print operand or operator when visiting node
        System.out.print(operator);
        // print “)“ after traversing right subtree
        printExpression(node.getRight(), operator);
        System.out.print(needsParenthesis ? ")" : "");
    }

    private static <E> String toString(Stack<TreeNode<E>> stack) {
        if (null == stack) return null;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (TreeNode node : stack) {
            sb.append(node.getValue());
            sb.append(", ");
        }
        sb.delete(sb.lastIndexOf(", "), sb.length());
        return sb.append("]").toString();
    }

    /**
     * Builds a BXT from a postfix expression.
     *
     * @param str the postfix string to change into a tree,
     *            tokens separated by a space.
     * @apiNote Uses a helper stack of TreeNodes.
     */
    public void buildTree(String str) {
        if (null == str) return;
        // Create a stack of TreeNodes, since the operator is preceded by two operands.
        Stack<TreeNode<String>> nodeStack = new Stack<>();
        // Instantiate a StringTokenizer object
        // Use StringTokenizer to tokenize the postfix expression
        StringTokenizer tokenizer = new StringTokenizer(str);
        // While the tokenizer still has token, do the following:
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if ("pi".equals(token)) token = "π";
            // If the token is an operator,
            if (isOperator(token)) {
                TreeNode<String> operand = nodeStack.pop();
                // Let the new TreeNode be the root, and
                // push the root operator onto the stack
                if (Operators.isBinary(token)) {
                    // Pop two TreeNodes from the stack
                    // Form a new inner TreeNode with two children.
                    nodeStack.push(new TreeNode<>(token, nodeStack.pop(), operand));
                } else if (Operators.isLeftAssociateUnary(token)) {
                    nodeStack.push(new TreeNode<>(token, operand, null));
                } else if (Operators.isRightAssociateUnary(token)) {
                    nodeStack.push(new TreeNode<>(token, null, operand));
                } else throw new IllegalStateException(token + " can't be classified");
            } else { // If the token is an operand
                // Create a leaf TreeNode and push onto the stack
                nodeStack.push(new TreeNode<>(token, null, null));
            }
        }
        if (1 != nodeStack.size())
            throw new IllegalStateException("Unused " + toString(nodeStack));
        root = nodeStack.pop();
    }

    /**
     * Evaluating the expression
     *
     * @apiNote the public no-argument method calls
     * the private recursive method with arguments.
     */
    public double evaluateTree() {
        return evaluateNode(root);
    }

    /***
     * display() from TreeLab
     */
    public void display() {
        formatter.display(root, 1, true);
    }

    /**
     * Print the infix order with parentheses, if needed.
     */
    public void inorderTraverse() {
        printExpression(root, null);
    }

    // preorder traverse
    public void preorderTraverse() {
        Stack<TreeNode<String>> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode<String> node = stack.pop();
            if (null == node) continue;
            System.out.print(node.getValue() + " ");
            stack.push(node.getRight());
            stack.push(node.getLeft());
        }
    }
}
