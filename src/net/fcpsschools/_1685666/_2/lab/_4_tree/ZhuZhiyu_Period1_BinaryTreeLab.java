package net.fcpsschools._1685666._2.lab._4_tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * NAME: Zhiyu Zhu
 * PERIOD: 1
 * DUE DATE: 2018-01-09
 * <p>
 * PURPOSE:
 * - Practice common TreeNode tasks.
 * WHAT I LEARNED:
 * - Everyone has a different opinion on what level means.
 * - Java log is base e, not 2.
 * HOW I FEEL:
 * - Kinda disappointed and wonders why TreeNode is not generic.
 * - Very excited to see bitwise operator (`&` and `<<`) in real world again.
 * CREDITS (BE SPECIFIC: FRIENDS, PEERS, ONLINE WEBSITE):
 */
public class ZhuZhiyu_Period1_BinaryTreeLab {
    public static void main(String[] args) {
        String s = "XCOMPUTERSCIENCE";

        TreeNode root = new TreeNode(s.charAt(1), null, null);

        // The root node has index 1, on level 0
        // The first level nodes' indices: 2, 3
        // The second level nodes' indices: 4,5,6,7
        // Idea: based on the index of the node,
        // log_2(pos) calculates the level of the node.
        for (int pos = 2; pos < s.length(); pos++)
            insert(root, s.charAt(pos), pos, (int) (Math.log(pos) / Math.log(2)));

        insert(root, "A", 17, 4);
        insert(root, "B", 18, 4);
        insert(root, "C", 37, 5); //B's right child

        // displaySideway(root, 0);
        TreeFormatter.display(root);

        System.out.print("\nPreorder: ");
        preorderTraverse(root);
        System.out.print("\nInorder: ");
        inorderTraverse(root);
        System.out.print("\nPostorder: ");
        postorderTraverse(root);

        System.out.println("\n\nNodes = " + countNodes(root));
        System.out.println("Leaves = " + countLeaves(root));
        System.out.println("Grandparents = " + countGrands(root));
        System.out.println("Only childs = " + countOnlys(root));

        // System.out.println("\nDepth = " + numOfLevels(root));
        System.out.println("Height = " + height(root));
        System.out.println("Width  = " + width(root));

        System.out.println("Min = " + min(root));
        System.out.println("Max = " + max(root));

        System.out.println("\nBy Level: ");
        displayLevelOrder(root);

        System.out.println();
    }

    /**
     * insert a new node in the tree based on the node's level
     */
    public static void insert(TreeNode t, Object s, int pos, int level) {
        TreeNode p = t;
        for (int k = level - 1; k > 0; k--)
            p = 0 == (pos & (1 << k)) ? p.getLeft() : p.getRight();
        if (0 == (pos & 1)) p.setLeft(new TreeNode(s, null, null));
        else p.setRight(new TreeNode(s, null, null));
    }

    /**
     * display the tree sideway
     */
    public static void displaySideway(TreeNode t, int level) {
        if (null == t) return;
        displaySideway(t.getRight(), level + 1); //recurse right

        for (int k = 0; k < level; k++) System.out.print("\t");
        System.out.println(t.getValue());

        displaySideway(t.getLeft(), level + 1); //recurse left
    }

    public static void preorderTraverse(TreeNode t) {
        if (null == t) return;
        System.out.print(t.getValue() + " ");
        preorderTraverse(t.getLeft());
        preorderTraverse(t.getRight());
    }

    public static void inorderTraverse(TreeNode t) {
        if (null == t) return;
        inorderTraverse(t.getLeft());
        System.out.print(t.getValue() + " ");
        inorderTraverse(t.getRight());
    }

    public static void postorderTraverse(TreeNode t) {
        if (null == t) return;
        postorderTraverse(t.getLeft());
        postorderTraverse(t.getRight());
        System.out.print(t.getValue() + " ");
    }

    public static int countNodes(TreeNode t) {
        if (null == t) return 0;
        return 1 + countNodes(t.getLeft()) + countNodes(t.getRight());
    }

    public static int countLeaves(TreeNode t) {
        if (null == t) return 0;
        if (isLeafOrNull(t)) return 1;
        return countLeaves(t.getLeft()) + countLeaves(t.getRight());
    }

    /**
     * count the number grandparent nodes
     */
    public static int countGrands(TreeNode t) {
        boolean isGrand = null != t &&
                !(isLeafOrNull(t.getLeft()) && isLeafOrNull(t.getRight()));
        return isGrand ? 1 + countGrands(t.getLeft()) + countGrands(t.getRight()) : 0;
    }

    private static boolean isLeafOrNull(TreeNode t) {
        return null == t || null == t.getLeft() && null == t.getRight();
    }

    /**
     * count the number of nodes that has only 1 child
     */
    public static int countOnlys(TreeNode t) {
        if (null == t) return 0;
        int sum = 0;
        if ((null == t.getLeft()) != (null == t.getRight())) sum = 1;
        return sum + countOnlys(t.getLeft()) + countOnlys(t.getRight());
    }

    public static int depth(TreeNode t) {
        if (null == t) return -1;
        return 1 + Math.max(height(t.getLeft()), height(t.getRight()));
    }

    public static int height(TreeNode t) {
        return depth(t);
    }

    public static int width(TreeNode t) {
        int maxWdith = 0, levelWidth = 0;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(t);
        q.add(null);
        while (!q.isEmpty()) {
            if (null == (t = q.poll())) {
                if (levelWidth > maxWdith)
                    maxWdith = levelWidth;
                levelWidth = 0;
                if (null != q.peek()) q.add(null);
            } else {
                levelWidth++;
                if (null != t.getLeft()) q.add(t.getLeft());
                if (null != t.getRight()) q.add(t.getRight());
            }
        }
        return maxWdith;
    }

    public static Object min(TreeNode t) {
        if (null == t) return null;
        String v = String.valueOf(t.getValue());
        for (TreeNode node : new TreeNode[]{t.getLeft(), t.getRight()}) {
            Object obj = min(node);
            if (null == obj) continue;
            String candidate = String.valueOf(obj);
            if (candidate.compareTo(v) < 0) v = candidate;
        }
        return v;
    }

    public static Object max(TreeNode t) {
        if (null == t) return null;
        String v = String.valueOf(t.getValue());
        for (TreeNode node : new TreeNode[]{t.getLeft(), t.getRight()}) {
            Object obj = max(node);
            if (null == obj) continue;
            String candidate = String.valueOf(obj);
            if (candidate.compareTo(v) > 0) v = candidate;
        }
        return v;
    }

    /**
     * level by level display of the nodes (starts from left to right for nodes on the same level)
     *
     * @implSpec This method is not recursive.
     * @implNote Hint: Use a local queue to store the children of the current node.
     */
    public static void displayLevelOrder(TreeNode t) {
        Queue<TreeNode> q = new LinkedList<>();
        q.add(t);

        while (null != (t = q.poll())) {
            System.out.print(t.getValue());
            if (null != t.getLeft()) q.add(t.getLeft());
            if (null != t.getRight()) q.add(t.getRight());
        }
    }
}

 /*
                   ┌───────────C───────────┐
       ┌───────────O─────┐           ┌─────M─────┐
 ┌─────P────────┐     ┌──U──┐     ┌──T──┐     ┌──E──┐
 R──┐     ┌─────S     C     I     E     N     C     E
    A     B──┐
             C

Preorder: C O P R A S B C U C I M T E N E C E
Inorder: R A P B C S O C U I C E T N M C E E
Postorder: A R C B S P C I U O E N T C E E M C

Nodes = 18
Leaves = 8
Grandparents = 5
Only childs = 3
Height = 5
Width  = 8
Min = A
Max = U

By Level:
COMPUTERSCIENCEABC
*/
