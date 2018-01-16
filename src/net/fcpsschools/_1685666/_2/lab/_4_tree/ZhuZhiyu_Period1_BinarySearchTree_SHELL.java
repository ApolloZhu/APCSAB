package net.fcpsschools._1685666._2.lab._4_tree;

import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.Queue;
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
 * - My Maze Solver program: how to use JFileChooser.
 */
public class ZhuZhiyu_Period1_BinarySearchTree_SHELL {
    private static JDialog getWindow() {
        JDialog window = new JDialog();
        window.setVisible(true);
        window.toFront();
        window.setAlwaysOnTop(true);
        return window;
    }

    public static void main(final String[] args) {
        // Prompt the user for an input string.
        final Scanner in = new Scanner(System.in);
        System.out.print("Build tree with: ");
        TreeNode<String> t = treeOf(in.nextLine());
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
            } else if (cmd.startsWith("e")) { // Export
                export(t);
            } else if (cmd.startsWith("f")) { // Find
                String target = in.next();
                System.out.println(target + " is " +
                        (find(t, target) ? "" : "not ") +
                        "in the tree.");
            } else if (cmd.startsWith("i")) { // Import
                t = load(t);
            } else if (cmd.startsWith("p")) { // Prefix
                System.out.print("Infix: ");
                String infix = in.next();
                System.out.print("Postfix: ");
                String postfix = in.next();
                System.out.print("Prefix: ");
                System.out.println(toPrefix(infix, postfix));
            } else System.exit(0);
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

    private static String inLevelOrder(TreeNode t) {
        StringBuilder builder = new StringBuilder();
        Queue<TreeNode> q = new LinkedList<>();
        q.add(t);
        while (!q.isEmpty()) {
            t = q.poll();
            if (null == t) continue;
            builder.append(t.getValue());
            q.add(t.getLeft());
            q.add(t.getRight());
        }
        return builder.toString();
    }

    /**
     * Build a Binary Search Tree using String
     */
    public static TreeNode<String> treeOf(String s) {
        TreeNode<String> t = null;
        for (char c : s.toCharArray()) {
            t = insert(t, String.valueOf(c));
        }
        return t;
    }

    public static boolean export(TreeNode t) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save tree as...");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (JFileChooser.APPROVE_OPTION == chooser.showSaveDialog(getWindow())) {
            Path path = chooser.getSelectedFile().toPath();
            try {
                Files.write(path, inLevelOrder(t).getBytes(), StandardOpenOption.CREATE);
                System.out.println("Tree saved to " + path);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else System.out.println("You didn't choose a path to save the tree.");
        return false;
    }

    public static TreeNode<String> load(TreeNode<String> t) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Open tree file");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(getWindow())) {
            try {
                Path path = chooser.getSelectedFile().toPath();
                String s = Files.readAllLines(path).get(0);
                TreeNode<String> tree = treeOf(s);
                System.out.println("Loaded tree in " + path);
                return tree;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else System.out.println("You didn't choose a tree.");
        return t;
    }

    public static String toPrefix(String infix, String postfix) {
        if (1 >= postfix.length()) return postfix;
        int last = postfix.length() - 1;
        char val = postfix.charAt(last);
        int mid = infix.indexOf(val);
        String left = infix.substring(0, mid);
        String right = infix.substring(mid + 1);
        return val + toPrefix(left, postfix.substring(0, left.length()))
                + toPrefix(right, postfix.substring(left.length(), last));
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
I want to: export
Tree saved to /Users/Apollonian/tree.txt
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
I want to: import
Loaded tree in /Users/Apollonian/tree.txt
       ┌─────5─────┐
    ┌──3──┐     ┌──7──┐
 ┌──2     4     6     8──┐
 1                       9
Min: 1
Max: 9
1 2 3 4 5 6 7 8 9
I want to: quit
*/
