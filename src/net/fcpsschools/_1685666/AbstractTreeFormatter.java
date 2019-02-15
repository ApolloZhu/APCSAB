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

package net.fcpsschools._1685666;

import java.util.LinkedList;
import java.util.List;

/**
 * A class that visualizes trees
 * in a more human friendly way.
 *
 * @author Apollo/Zhiyu Zhu/朱智语, Pd. 1
 */
public class AbstractTreeFormatter<TreeNodeType, ValueType> {
    /**
     * Some box drawing Strings.
     */
    private static final String SL = "┌", SR = "┐";
    /**
     * Some box drawing chars.
     */
    private static final char CL = '┌', CR = '┐', CT = '─';

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
     * @see #display(TreeIterator i, ValueType t, int 0, boolean false)
     */
    public void display(TreeIterator<TreeNodeType, ValueType> i, TreeNodeType t) {
        display(i, t, 1, false);
    }

    /**
     * Displays `TreeNode` in a more human friendly way.
     *
     * @param t          the tree node to display to System::out.
     * @param eachPad    extending node to occupy more spaces.
     * @param withSpaces should use spaces instead bars for padding.
     */
    public void display(TreeIterator<TreeNodeType, ValueType> iterator, TreeNodeType t, int eachPad, boolean withSpaces) {
        List<String[]> lines = new LinkedList<>(); // For quicker appending.
        int maxNodeWidth = build(iterator, t, null, 0, lines) + eachPad * 2;
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
    private int build(TreeIterator<TreeNodeType, ValueType> i, TreeNodeType t, String top, int level, List<String[]> store) {
        if (null == t) return 0;
        int max = 1, nextLevel = level + 1;
        max = Math.max(build(i, i.getLeft(t), SL, nextLevel, store), max);

        String[] list = new String[level + 1];
        String value = String.valueOf(i.getValue(t));
        if (top != null) list[level - 1] = top;
        list[level] = value;
        max = Math.max(value.length(), max);
        store.add(list);

        max = Math.max(build(i, i.getRight(t), SR, nextLevel, store), max);
        return max;
    }

    public interface TreeIterator<TreeNodeType, ValueType> {
        TreeNodeType getLeft(TreeNodeType t);

        TreeNodeType getRight(TreeNodeType t);

        ValueType getValue(TreeNodeType t);
    }
}
