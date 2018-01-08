package net.fcpsschools._1685666._2.lab._4_tree;

import java.util.LinkedList;
import java.util.List;
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

/**
 * A class that visualizes `TreeNode`
 * in a more human friendly way.
 *
 * @author ApolloZhu, Pd. 1
 */
class TreeFormatter {
    /**
     * Some box drawing Strings.
     */
    private static final String SL = "┌", SR = "┐";
    /**
     * Some box drawing chars.
     */
    private static final char CL = '┌', CR = '┐', CT = '─';

    /**
     * @see #display(TreeNode t, int 0, boolean false)
     */
    public static void display(TreeNode t) {
        display(t, 1, false);
    }

    /**
     * Displays `TreeNode` in a more human friendly way.
     *
     * @param t          the tree node to display to System::out.
     * @param eachPad    extending node to occupy more spaces.
     * @param withSpaces should use spaces instead bars for padding.
     */
    public static void display(TreeNode t, int eachPad, boolean withSpaces) {
        List<String[]> lines = new LinkedList<>(); // For quicker appending.
        int maxNodeWidth = build(t, null, 0, lines) + eachPad * 2;
        String cur;
        for (int level = 0; ; level++) {
            // The StringBuilder replacement
            char[] arr = new char[lines.size() * maxNodeWidth];
            int i = 0, start = -1;
            boolean needsRight = false;
            for (String[] subList : lines) {
                if (null == (cur = get(subList, level, maxNodeWidth))) {
                    i += maxNodeWidth;
                } else {
                    char[] curA = cur.toCharArray();
                    for (int j = 0; j < curA.length; j++, i++) {
                        final char c = arr[i] = curA[j];
                        if (!Character.isWhitespace(c)) {
                            // This is why we can't print directly.
                            if (CR == c || needsRight) {
                                int offset = needsRight && withSpaces ? eachPad : 0;
                                int bound = i - offset;
                                for (int k = start; k < bound; k++)
                                    arr[k] = CT;
                            }
                            needsRight = CL == c;
                            start = i + (!needsRight && withSpaces ? 1 + eachPad : 1);
                        }
                    }
                }
            }
            if (-1 == start) break;
            for (char c : arr) System.out.print(0 == c ? ' ' : c);
            System.out.println();
        }
    }

    /**
     * Generates a blank String of given length.
     *
     * @param length number of whitespaces.
     * @return a blank String of length `width`.
     */
    private static String emptyStringOfLength(int length) {
        return length < 1 ? "" : String.format("%" + length + "s", "");
    }

    /**
     * Fetches a String from array safely,
     * and patches with whitespaces
     * to fill up the given width.
     *
     * @param list  array of Strings.
     * @param index desired position to get.
     * @param width desired length for String.
     * @return null if anything went wrong,
     * or the String at the given index in list
     * extended to the given width.
     */
    private static String get(String[] list, int index, int width) {
        try {
            String v = list[index];
            int padding = width - v.length();
            int left = padding / 2, right = padding - left;
            return emptyStringOfLength(left) + v + emptyStringOfLength(right);
        } catch (Throwable t) {
            // Handles null and out of bounds.
            return null;
        }
    }

    /**
     * Transforms a Tree to a 2D Collection.
     *
     * @param t     the TreeNode to transform.
     * @param top   the indicator on top of `t`.
     * @param level the level of the node.
     * @param store the output 2D Collection.
     * @return maximum width needed to present any node.
     * @apiNote The outer layer uses List since
     * it can not be determined upfront, but the
     * inner array has a fixed size for efficiency.
     */
    private static int build(TreeNode t, String top, int level, List<String[]> store) {
        if (null == t) return 0;
        int max = 1, nextLevel = level + 1;
        max = Math.max(build(t.getLeft(), SL, nextLevel, store), max);

        String[] list = new String[level + 1];
        String value = String.valueOf(t.getValue());
        if (top != null) list[level - 1] = top;
        list[level] = value;
        max = Math.max(value.length(), max);
        store.add(list);

        max = Math.max(build(t.getRight(), SR, nextLevel, store), max);
        return max;
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
