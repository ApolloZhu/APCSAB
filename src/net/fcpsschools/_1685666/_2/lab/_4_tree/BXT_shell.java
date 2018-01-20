//Name:      Date:
package net.fcpsschools._1685666._2.lab._4_tree;

import net.fcpsschools._1685666._2.lab._1_stack.Operators;

import java.util.Scanner;

/**
 * Driver for a binary expression tree class,
 * build an expression tree, display it, and evaluate it.
 * <p>
 * INPUT: a postfix string, each token separated by a space.
 */
public class BXT_shell {
    public static void main(String[] args) {
        BXT tree = new BXT();
        Operators.printSupportedBinary();
        Operators.printSupportedConstant();
        Scanner sc = new Scanner(System.in);
        // Prompt the user to input postfix expressions.
        System.out.print("Input postfix string: ");
        // Input a postfix expression
        String s = sc.nextLine();   // 14 -5 / ,   3 4 5 + *  ,  2 3 + 5 / 4 5 - *
        // The BXT can build itself from a postorder expression.
        tree.buildTree(s);
        // It can print itself.
        tree.display();
        // It prints an inorder string.
        System.out.print("Infix order:  ");
        tree.inorderTraverse();
        System.out.print("\nPrefix order:  ");
        // It prints a preorder string.
        tree.preorderTraverse();
        // It can evaluate itself.
        System.out.print("\nEvaluates to " + tree.evaluateTree());
    }
}
/*
Supported Binary Operators: ^ * % / + -
Supported Constants: e pi
Supported Number Format: Double.parseDouble

Input postfix string: 14 -5 /
 ┌───/───┐
 14      -5
Infix order:  14/(-5)
Prefix order:  / 14 -5
Evaluates to -2.8

Input postfix string: 3 4 5 + *  ,  2 3 + 5 / 4 5 - *
          ┌─────*─────┐
    ┌─────/──┐     ┌──-──┐
 ┌──+──┐     5     4     5
 2     3
Infix order:  (2+3)/5*(4-5)
Prefix order:  * / + 2 3 5 - 4 5
Evaluates to -1.0
*/
