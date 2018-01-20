//Name:      Date:
package net.fcpsschools._1685666._2.lab._4_tree;

import java.util.Scanner;

/**
 * Driver for a binary expression tree class.
 * Input: a postfix string, each token separated by a space.
 */
public class BXT_shell {
    public static void main(String[] args) {
        BXT tree = new BXT();
        Scanner sc = new Scanner(System.in);
        System.out.print("Input postfix string: ");
        String s = sc.nextLine();   // 14 -5 / ,   3 4 5 + *  ,  2 3 + 5 / 4 5 - *
        tree.buildTree(s);
        tree.display();
        System.out.print("Infix order:  ");
        tree.inorderTraverse();
        System.out.print("\nPrefix order:  ");
        tree.preorderTraverse();
        System.out.print("\nEvaluates to " + tree.evaluateTree());
    }
}
/*
Input postfix string: 14 -5 /
-5
/
14
Infix order: 14 / -5
Prefix order: / 14 -5 Evaluates to -2.8
*/
/*
Input postfix string: 2 3 + 5 / 4 5 - * 5
-
4
*
5
/
3
+
2
Infix order: 2 + 3 / 5 * 4 - 5
Prefix order: * / + 2 3 5 - 4 5
Evaluates to -1.0
*/
