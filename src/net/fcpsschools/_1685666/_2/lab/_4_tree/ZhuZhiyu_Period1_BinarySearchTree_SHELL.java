package net.fcpsschools._1685666._2.lab._4_tree;

import java.util.Scanner;

/**
 * NAME: Zhiyu Zhu
 * PERIOD: 1
 * DUE DATE: 1/15 23:59
 * <p>
 * PURPOSE:
 * - Practice with a Binary Search Tree and TreeNode.
 * WHAT I LEARNED:
 * - Confirmed the syntax for constrained generics.
 * CREDITS (BE SPECIFIC: FRIENDS, PEERS, ONLINE WEBSITE):
 * - Mr. Lau: the PURPOSE part above.
 */
public class ZhuZhiyu_Period1_BinarySearchTree_SHELL {
    public static void main(String[] args) {
        // Prompt the user for an input string.
        final Scanner in = new Scanner(System.in);
        System.out.print("Nodes: ");
        TreeNode<Character> t = null;
        // Build a Binary Search Tree using Comparable
        for (char c : in.nextLine().toCharArray())
            // Instead of String
            t = insert(t, c);
        // Display the tree (take the code from the Tree Lab).
        TreeFormatter.display(t);
        // Prompt the user for a target and search the tree for it.
        System.out.print("To find in tree: ");
        char target = in.next().charAt(0);
        System.out.println(target + " is " +
                (find(t, target) ? "" : "not ") +
                "in the tree.");
        // Display the tree's minimum and maximum values.
        System.out.println("Min: " + min(t));
        System.out.println("Max: " + max(t));
        // Print the data in order from smallest to largest.
        smallToLarge(t);
    }

    /**
     * Build a BST.
     *
     * @implNote If the node is null, insert the new node.
     * Else if the item is less, set the left node and recur to the left.
     * Else, if the item is greater, set the right node and recur to the right.
     * @apiNote recursive algorithm.
     */
    public static <E extends Comparable<E>> TreeNode<E> insert(TreeNode<E> t, E s) {
        if (null == t) return new TreeNode<>(s);
        boolean isLess = 0 >= s.compareTo(t.getValue());
        if (isLess) t.setLeft(insert(t.getLeft(), s));
        else t.setRight(insert(t.getRight(), s));
        return t;
    }

    /**
     * Find the target.
     *
     * @apiNote Iterative algorithm:
     * While t is not null, if the t's value equals the target, return true.
     * If the target is less than the t's value, go left, otherwise go right.
     * If the target is not found, return false.
     * <p>
     * @implNote Recursive algorithm: If the tree is empty,
     * return false.  If the target is less than the current node
     * value, return the left subtree.  If the target is greater, return
     * the right subtree.  Otherwise, return true.
     */
    public static <E extends Comparable<E>> boolean find(TreeNode<E> t, E x) {
        if (null == t) return false;
        int test = x.compareTo(t.getValue());
        if (0 > test) return find(t.getLeft(), x);
        if (0 < test) return find(t.getRight(), x);
        return true;
    }

    /**
     * Hint: look at several BSTs.
     * Where are the min values always located?
     *
     * @param t starting at the root.
     * @return the min value in the BST.
     * @apiNote use iteration.
     */
    public static <E> E min(TreeNode<E> t) {
        if (null == t) return null;
        while (null != t.getLeft()) t = t.getLeft();
        return t.getValue();
    }

    /**
     * @param t starting at the root.
     * @return the max value in the BST.
     * @apiNote use recursion!
     */
    public static <E> E max(TreeNode<E> t) {
        if (null == t) return null;
        if (null != t.getRight()) return max(t.getRight());
        return t.getValue();
    }

    /**
     * Print the data in order from smallest to largest.
     */
    public static void smallToLarge(TreeNode t) {
        if (null == t) return;
        smallToLarge(t.getLeft());
        System.out.print(t.getValue() + " ");
        smallToLarge(t.getRight());
    }
}

/*
Nodes: 537246819
       ┌─────5─────┐
    ┌──3──┐     ┌──7──┐
 ┌──2     4     6     8──┐
 1                       9
To find in tree: 0
0 is not in the tree.
Min: 1
Max: 9
1 2 3 4 5 6 7 8 9
*/
