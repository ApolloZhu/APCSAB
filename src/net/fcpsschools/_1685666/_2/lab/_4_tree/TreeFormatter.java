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

package net.fcpsschools._1685666._2.lab._4_tree;

import net.fcpsschools._1685666.AbstractTreeFormatter;

/**
 * A class that visualizes `TreeNode`
 * in a more human friendly way.
 *
 * @author Apollo/Zhiyu Zhu/朱智语, Pd. 1
 */
public class TreeFormatter<E> extends AbstractTreeFormatter<TreeNode<E>, E> {
    private final Iterator iterator = new Iterator();

    /**
     * @see #display(TreeNode root, int 0, boolean false)
     */
    public void display(TreeNode<E> root) {
        display(root, 1, false);
    }

    /**
     * Displays `TreeNode` in a more human friendly way.
     *
     * @param root       the tree node to display to System::out.
     * @param eachPad    extending node to occupy more spaces.
     * @param withSpaces should use spaces instead bars for padding.
     */
    public void display(TreeNode<E> root, int eachPad, boolean withSpaces) {
        super.display(iterator, root, eachPad, withSpaces);
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
