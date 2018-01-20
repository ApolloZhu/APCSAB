package net.fcpsschools._1685666._2.lab._4_tree;
//Name: Zhiyu Zhu; Date: 1/18\9/18

import net.fcpsschools._1685666._2.lab._1_stack.Operators;

import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Represents a binary expression tree.
 * The BXT can build itself from a postorder expression.  It can
 * evaluate and print itself. It also prints an inorder string and a preorder string.
 * <p>
 * As you might expect, you will build an expression tree, display it, and evaluate it. This time, you will encapsulate the behavior in a BXT class. That means that all the methods will be instance methods. You will need a short driver class, which the teacher has written.
 * Let's also allow decimal input and output, and negative numbers. If you require that the tokens in the input string be separated by a space, you can use the StringTokenizer class.
 * Prompt the user to input postfix expressions.
 * <p>
 * Build a BXT
 * You need to change the string into a tree. Hint 1: The input is a string with spaces. Instantiate a StringTokenizer object and use its two helpful methods, hasMoreTokens() and nextToken().
 * In a postfix string the operator is preceded by two operands (numbers). This suggests a stack of TreeNodes would be useful. Hint 3: Each operator is a TreeNode with two children. Hint 4: If the token is an operator, do what? Else it's a number, so do what?
 * Display Infix and Prefix orders
 * You don't need help with this.
 * Evaluating the Expression
 * Do this recursively. Notice that all the recursive methods in the BXT class are really two methods, where the public no-argument method calls the private recursive method with arguments. This two-step pattern is standard for recursive operations in classes.
 * If the node is an operator, recursively evaluate the left child and the right child, and return the result. Else the node is a number, so it can be converted into a double, and returned.
 * Challenge
 * Print the infix order with parentheses, if needed.
 */
public class BXT {
    private int count;
    private TreeNode<String> root;

    public BXT() {
        count = 0;
        root = null;
    }

    public BXT(String obj) {
        count = 1;
        root = new TreeNode<>(obj, null, null);
    }

    /**
     * Builds a BXT from a postfix expression.
     *
     * @apiNote Uses a helper stack of TreeNodes.
     */
    public void buildTree(String str) {
        Stack<TreeNode<String>> nodeStack = new Stack<>();
        StringTokenizer tokenizer = new StringTokenizer(str);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            //TODO: enter code here
/*
Input a postfix expression Create a stack of TreeNodes.
* Use StringTokenizer to tokenize the postfix expression while the tokenizer still has token, do the following:
– If the token is an operand, create a leaf TreeNode and push onto the stack
– else if the token is an operator, pop two TreeNodes from the stack and form a new inner node. Let the new TreeNode be the root. Push the root onto the stack*/
        }
    }

    public double evaluateTree() {
        return evaluateNode(root);
    }

    private double evaluateNode(TreeNode root)  //recursive
    {
        /*
         Start from the root
• If the root is a operator
Evaluate the left subtree (let the value left)
Evaluate the right subtree (let the value right) Evaluate: left <op> right
• else // not an inner node
Evaluate the leaf node (use Double.parseDouble)
Advanced CS*/
        //TODO: enter code here

        return 0;
    }

    private double computeTerm(String s, double a, double b) {
        return Operators.evaluate(s, "" + a, "" + b);
    }

    private boolean isOperator(String s) {
        return Operators.isBinary(s);
    }

    //display() from TreeLab
    public void display() {
        TreeFormatter.display(root);
    }

    // inorder traverse
    public void inorderTraverse() {
/*Specialization of an inorder traversal
– print operand or operator when visiting node
– print “(“ before traversing left subtree
– print “)“ after traversing right subtree


Algorithm printExp(v) if left (v) ≠ null
print(“(’’)
printExp (left(v)) print(v.element ())
if right(v) ≠ null
printExp (right(v)) print (“)’’)
*/
    }

    // preorder traverse
    public void preorderTraverse() {

    }
}
