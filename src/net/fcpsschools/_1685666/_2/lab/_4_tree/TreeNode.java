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

/**
 * TreeNode class for the AP Exams
 *
 * @author Apollo/Zhiyu Zhu/朱智语
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
