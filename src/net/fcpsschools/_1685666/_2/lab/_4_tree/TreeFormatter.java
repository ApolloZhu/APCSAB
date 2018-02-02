package net.fcpsschools._1685666._2.lab._4_tree;

import net.fcpsschools._1685666.AbstractTreeFormatter;

/**
 * A class that visualizes `TreeNode`
 * in a more human friendly way.
 *
 * @author ApolloZhu, Pd. 1
 */
public class TreeFormatter<E> extends AbstractTreeFormatter<TreeNode<E>, E> {
    /**
     * Some box drawing Strings.
     */
    private static final String SL = "┌", SR = "┐";
    /**
     * Some box drawing chars.
     */
    private static final char CL = '┌', CR = '┐', CT = '─';
    private final Iterator iterator = new Iterator();

    /**
     * @see #display(TreeNode t, int 0, boolean false)
     */
    public void display(TreeNode<E> t) {
        display(t, 1, false);
    }

    /**
     * Displays `TreeNode` in a more human friendly way.
     *
     * @param t          the tree node to display to System::out.
     * @param eachPad    extending node to occupy more spaces.
     * @param withSpaces should use spaces instead bars for padding.
     */
    public void display(TreeNode<E> t, int eachPad, boolean withSpaces) {
        super.display(iterator, t, eachPad, withSpaces);
    }

    protected class Iterator implements TreeIterator<TreeNode<E>, E> {
        @Override
        public TreeNode<E> getLeft(TreeNode<E> t) {
            return t.getLeft();
        }

        @Override
        public TreeNode<E> getRight(TreeNode<E> t) {
            return t.getRight();
        }

        @Override
        public E getValue(TreeNode<E> t) {
            return t.getValue();
        }
    }
}
