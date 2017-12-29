package net.fcpsschools._1685666._2.lab._4_tree;

/**
 * NAME: Zhiyu Zhu
 * PERIOD: 1
 * DUE DATE: 2018-01-09
 * <p>
 * PURPOSE:
 * <p>
 * WHAT I LEARNED:
 * - Very excited to see bitwise operator (`&`) in real world again.
 * HOW I FEELED:
 * - Kinda disappointed to see how TreeNode is not generic.
 * CREDITS (BE SPECIFIC: FRIENDS, PEERS, ONLINE WEBSITE):
 */
public class ZhuZhiyuPeriod1_BT {
    public static void main(String[] args) {
        String s = "XCOMPUTERSCIENCE";

        TreeNode root = new TreeNode("" + s.charAt(1), null, null);

        // The root node has index 1
        // The second level nodes' indices: 2, 3
        // The third level nodes' indices: 4,5,6,7
        // Idea: based on the index of the node, log (pos) of base 2 calculates the level of the
        //          node: root (index 1) on level 1. Node with index 2 or 3 is on level 2
        for (int pos = 2; pos < s.length(); pos++)
            insert(root, "" + s.charAt(pos), pos, (int) (1 + Math.log(pos) / Math.log(2)));

        insert(root, "A", 17, 5);
        insert(root, "B", 18, 5);
        insert(root, "C", 37, 6); //B's right child

        displaySideway(root, 0);

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

        System.out.println("Min = " + min(root));
        System.out.println("Max = " + max(root));

        System.out.println("\nBy Level: ");
        displayLevelOrder(root);

    }

    /**
     * insert a new node in the tree based on the node's level
     */
    public static void insert(TreeNode t, String s, int pos, int level) {
        TreeNode p = t;
        for (int k = level - 2; k > 0; k--)
            // then 1 << 4 will insert four 0-bits at the right of 1
            // (binary representation of 1 is 1),
            // results in 10000 (in binary)
            p = (pos & (1 << k)) == 0 ? p.getLeft() : p.getRight();

        if (0 == (pos & 1))
            p.setLeft(new TreeNode(s, null, null));
        else
            p.setRight(new TreeNode(s, null, null));
    } // end of insert


    /**
     * display the tree sideway
     */
    public static void displaySideway(TreeNode t, int level) {
        if (null == t) return;
        displaySideway(t.getRight(), level + 1); //recurse right

        for (int k = 0; k < level; k++)
            System.out.print("\t");
        System.out.println(t.getValue());

        displaySideway(t.getLeft(), level + 1); //recurse left
    }


    public static void preorderTraverse(TreeNode t) {

    }

    public static void inorderTraverse(TreeNode t) {

    }


    public static void postorderTraverse(TreeNode t) {

    }


    public static int countNodes(TreeNode t) {
        return -1;
    }


    public static int countLeaves(TreeNode t) {
        return -1;
    }


    /**
     * count the number grandparent nodes
     */
    public static int countGrands(TreeNode t) {
        return -1;
    }

    /**
     * count the number of nodes that has only 1 child
     */
    public static int countOnlys(TreeNode t) {
        return -1;
    }

    public static int depth(TreeNode t) {
        return -1;
    }

    public static int height(TreeNode t) {
        return -1;
    }


    public static Object min(TreeNode t) {
        return null;
    }

    public static Object max(TreeNode t) {
        return null;
    }

    /**
     * level by level display of the nodes (starts from left to right for nodes on the same level)
     *
     * @implSpec This method is not recursive.
     * @implNote Hint: Use a local queue to store the children of the current node.
     */
    public static void displayLevelOrder(TreeNode t) {

    }
}

// @formatter:off
   /***************************************************

      ----jGRASP exec: java Lab01

    			E
    		E
    			C
    	M
    			N
    		T
    			E
    C
    			I
    		U
    			C
    	O
    			S
    					C
    				B
    		P
    				A
    			R

    Preorder: C O P R A S B C U C I M T E N E C E
    Inorder: R A P B C S O C U I C E T N M C E E
    Postorder: A R C B S P C I U O E N T C E E M C

    Nodes = 18
    Leaves = 8
    Grandparents = 5
    Only childs = 3

    Depth = 6
    Height = 5
    Min = A
    Max = U

    By Level:
    COMPUTERSCIENCEABC

    *******************************************************/
// @formatter:on