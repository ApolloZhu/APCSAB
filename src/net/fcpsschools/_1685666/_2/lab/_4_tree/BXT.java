package net.fcpsschools._1685666._2.lab._4_tree;

import net.fcpsschools._1685666._2.lab._1_stack.Operators;

import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Represents a binary expression tree,
 * supports decimal and negative.
 *
 * @author Zhiyu Zhu
 * @since 1/18/18
 */
public class BXT {
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
        return Operators.evaluate(s, "" + a, "" + b);
    }

    private static boolean isOperator(String s) {
        return Operators.isBinary(s);
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
        boolean lowerPrecedency = null != parent &&
                Operators.Relation.LOWER == Operators.compare(operator, parent);
        boolean needsParenthesis = lowerPrecedency || isNegative;
        System.out.print(needsParenthesis ? "(" : "");
        // print “(“ before traversing left subtree
        printExpression(node.getLeft(), operator);
        // print operand or operator when visiting node
        System.out.print(operator);
        // print “)“ after traversing right subtree
        printExpression(node.getRight(), operator);
        System.out.print(needsParenthesis ? ")" : "");
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
            // If the token is an operator,
            if (isOperator(token)) {
                // Pop two TreeNodes from the stack and .
                TreeNode<String> right = nodeStack.pop();
                // Form a new operator, a inner TreeNode with two children.
                // Let the new TreeNode be the root, and
                // push the root onto the stack
                nodeStack.push(new TreeNode<>(token, nodeStack.pop(), right));
            } else { // If the token is an operand
                // Create a leaf TreeNode and push onto the stack
                nodeStack.push(new TreeNode<>(token, null, null));
            }
        }
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
        TreeFormatter.display(root);
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
