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

// Lab : BXT_shell
// Name: Zhiyu Zhu
// Date: 2018/01/23

package net.fcpsschools._1685666._2.lab._4_tree;

import net.fcpsschools._1685666._2.lab._1_stack.Operators;

import java.util.Scanner;

/**
 * Driver for a binary expression tree class,
 * build an expression tree, display it, and evaluate it.
 * <p>
 * INPUT: a postfix string, each token separated by a space.
 *
 * @author Apollo/Zhiyu Zhu/朱智语
 */
public class BXT_shell {
    public static void main(String[] args) {
        BXT tree = new BXT();
        Operators.printInfo();
        Scanner sc = new Scanner(System.in);
        // Prompt the user to input postfix expressions.
        System.out.print("\nInput postfix string: ");
        // Input a postfix expression
        String s = sc.nextLine();   // 14 -5 / ,   3 4 5 + *  ,  2 3 + 5 / 4 5 - *
        try {
            // The BXT can build itself from a postorder expression.
            tree.buildTree(s);
        } catch (Throwable t) {
            System.out.print("Can't build tree: ");
            System.out.println(t.getLocalizedMessage());
            System.out.println("Existing...");
            System.exit(t.hashCode());
        }
        // It can print itself.
        tree.display();
        // It prints an inorder string.
        System.out.print("Infix order:  ");
        tree.inorderTraverse();
        System.out.print("\nPrefix order: ");
        // It prints a preorder string.
        tree.preorderTraverse();
        // It can evaluate itself.
        try {
            System.out.print("\nEvaluates to: " + tree.evaluateTree());
        } catch (Throwable t) {
            System.out.print("\nIncalculable: " + t.getLocalizedMessage());
        }
        System.out.println();
    }
}
/*
Supported Binary Operators: ^ * % / + -
Supported Right Associate Unary Operators: - deg + csc log √ sec rad tan sin arctan arcsin sqrt cot cos arccos
Supported Left Associate Unary Operators: !
Supported Constants: π e pi
Supported Number Format: Double.parseDouble

Input postfix string: 14 -5 /
 ┌── / ──┐
 14      -5
Infix order:  14/(-5)
Prefix order: / 14 -5
Evaluates to: -2.8

Input postfix string: 3 4 5 + *
 ┌─ * ────┐
 3     ┌─ + ─┐
       4     5
Infix order:  3*(4+5)
Prefix order: * 3 + 4 5
Evaluates to: 27.0

Input postfix string: 2 3 + 5 / 4 5 - *
          ┌──── * ────┐
    ┌──── / ─┐     ┌─ - ─┐
 ┌─ + ─┐     5     4     5
 2     3
Infix order:  (2+3)/5*(4-5)
Prefix order: * / + 2 3 5 - 4 5
Evaluates to: -1.0

Input postfix string: 5 6 4 * ! ^ cos
 cos ───────┐
       ┌─── ^ ──────────────────┐
       5              ┌──────── !
                 ┌─── * ───┐
                 6         4
Infix order:  cos(5^(6*4)!)
Prefix order: cos ^ 5 ! * 6 4
Evaluates to: 1.0

Input postfix string: 1.44 sqrt
 sqrt ──┐
       1.44
Infix order:  sqrt(1.44)
Prefix order: sqrt 1.44
Evaluates to: 1.2

Input postfix string: e 0 /
 ┌─ / ─┐
 e     0
Infix order:  e/0
Prefix order: / e 0
Incalculable: / by zero
*/
