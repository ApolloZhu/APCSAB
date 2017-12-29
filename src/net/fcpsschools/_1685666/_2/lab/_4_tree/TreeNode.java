package net.fcpsschools._1685666._2.lab._4_tree;

/**
 * TreeNode class for the AP Exams
 */
public class TreeNode {
    private Object value;
    private TreeNode left, right;

    public TreeNode(Object initValue) {
        value = initValue;
        left = null;
        right = null;
    }

    public TreeNode(Object initValue, TreeNode initLeft, TreeNode initRight) {
        value = initValue;
        left = initLeft;
        right = initRight;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object theNewValue) {
        value = theNewValue;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode theNewLeft) {
        left = theNewLeft;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode theNewRight) {
        right = theNewRight;
    }
}
