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
 * - Confirmed the syntax of constrained generics.
 * CREDITS (BE SPECIFIC: FRIENDS, PEERS, ONLINE WEBSITE):
 * - Mr. Lau: the PURPOSE part above.
 */
public class ZhuZhiyu_Period1_BinarySearchTree_SHELL {
    public static void main(final String[] args) {
        // Prompt the user for an input string.
        final Scanner in = new Scanner(System.in);
        System.out.print("Build tree with: ");
        TreeNode<String> t = null;
        // Build a Binary Search Tree using Comparable
        for (char c : in.nextLine().toCharArray()) {
            // Instead of String
            t = insert(t, String.valueOf(c));
        }
        while (true) {
            // Display the tree (take the code from the Tree Lab).
            TreeFormatter.display(t);
            // Display the tree's minimum and maximum values.
            System.out.println("Min: " + min(t));
            System.out.println("Max: " + max(t));
            // Print the data in order from smallest to largest.
            smallToLarge(t);
            System.out.print("\nI want to: ");
            String cmd = in.next().toLowerCase();
            if (cmd.startsWith("a")) { // Add
                t = insert(t, in.next());
            } else if (cmd.startsWith("d")) { // Delete
                String target = in.next();
                if (find(t, target)) {
                    t = delete(t, target);
                } else {
                    System.out.println(target + " is not in the tree.");
                }
            } else if (cmd.startsWith("f")) { // Find
                String target = in.next();
                System.out.println(target + " is " +
                        (find(t, target) ? "" : "not ") +
                        "in the tree.");
            } else break;
        }
    }

    /**
     * Build a BST.
     *
     * @implNote If the node is null, insert the new node.
     * Else if the item is less, set the left node and recur to the left.
     * Else, if the item is greater, set the right node and recur to the right.
     * @apiNote recursive algorithm.
     */
    public static <E extends Comparable<E>>
    TreeNode<E> insert(final TreeNode<E> t, final E s) {
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
    public static <E extends Comparable<E>>
    boolean find(final TreeNode<E> t, final E x) {
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
    public static <E> E max(final TreeNode<E> t) {
        if (null == t) return null;
        if (null != t.getRight()) return max(t.getRight());
        return t.getValue();
    }

    /**
     * Print the data in order from smallest to largest.
     */
    public static void smallToLarge(final TreeNode t) {
        if (null == t) return;
        smallToLarge(t.getLeft());
        System.out.print(t.getValue() + " ");
        smallToLarge(t.getRight());
    }

    public static <E extends Comparable<E>>
    TreeNode<E> delete(final TreeNode<E> node, final E v) {
        if (null == node) return null;
        int test = v.compareTo(node.getValue());
        if (0 > test) {
            node.setLeft(delete(node.getLeft(), v));
        } else if (0 < test) {
            node.setRight(delete(node.getRight(), v));
        } else {
            boolean noLeft = null == node.getLeft();
            boolean noRight = null == node.getRight();
            if (noLeft && noRight) return null; // case 1
            if (noLeft) return node.getRight(); // case 2a
            if (noRight) return node.getLeft(); // case 2b
            TreeNode<E> parent = node, largest = node.getLeft();
            while (null != largest.getRight()) {
                parent = largest;
                largest = largest.getRight();
            }
            if (node == parent) { // case 3b
                node.setValue(largest.getValue());
                parent.setLeft(largest.getLeft());
            } else { // case 3a
                node.setValue(largest.getValue());
                parent.setRight(largest.getLeft());
            }
        }
        return node;
    }
}

/*
Build tree with: 537246819
       ┌─────5─────┐
    ┌──3──┐     ┌──7──┐
 ┌──2     4     6     8──┐
 1                       9
Min: 1
Max: 9
1 2 3 4 5 6 7 8 9
I want to: find 5
5 is in the tree.
       ┌─────5─────┐
    ┌──3──┐     ┌──7──┐
 ┌──2     4     6     8──┐
 1                       9
Min: 1
Max: 9
1 2 3 4 5 6 7 8 9
I want to: delete 5
       ┌──4─────┐
    ┌──3     ┌──7──┐
 ┌──2        6     8──┐
 1                    9
Min: 1
Max: 9
1 2 3 4 6 7 8 9
I want to: find 5
5 is not in the tree.
       ┌──4─────┐
    ┌──3     ┌──7──┐
 ┌──2        6     8──┐
 1                    9
Min: 1
Max: 9
1 2 3 4 6 7 8 9
I want to: add 5
       ┌──4────────┐
    ┌──3        ┌──7──┐
 ┌──2        ┌──6     8──┐
 1           5           9
Min: 1
Max: 9
1 2 3 4 5 6 7 8 9
I want to: quit
*/
