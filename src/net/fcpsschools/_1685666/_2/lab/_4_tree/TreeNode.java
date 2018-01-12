package net.fcpsschools._1685666._2.lab._4_tree;

/**
 * TreeNode class for the AP Exams
 */
public class TreeNode<E> {
    private E value;
    private TreeNode<E> left, right;

    public TreeNode(E initValue) {
        value = initValue;
        left = null;
        right = null;
    }

    public TreeNode(E initValue, TreeNode initLeft, TreeNode initRight) {
        value = initValue;
        left = initLeft;
        right = initRight;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E theNewValue) {
        value = theNewValue;
    }

    public TreeNode<E> getLeft() {
        return left;
    }

    public void setLeft(TreeNode<E> theNewLeft) {
        left = theNewLeft;
    }

    public TreeNode<E> getRight() {
        return right;
    }

    public void setRight(TreeNode<E> theNewRight) {
        right = theNewRight;
    }
}
