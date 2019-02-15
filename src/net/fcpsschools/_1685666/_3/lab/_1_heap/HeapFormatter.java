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

package net.fcpsschools._1685666._3.lab._1_heap;

import net.fcpsschools._1685666.AbstractTreeFormatter;

/**
 * A class that visualizes heaps
 * in a more human friendly way.
 *
 * @author Apollo/Zhiyu Zhu/朱智语, Pd. 1
 */
public class HeapFormatter<E> extends AbstractTreeFormatter<Integer, E> {
    public static <E> void display(int numItems, E[] heap) {
        display(0, false, numItems, heap);
    }

    @SafeVarargs
    public static <E> void display(int eachPad, boolean withSpaces, int numItems, E... heap) {
        Iterator<E> iterator = new Iterator<>(numItems, heap);
        new HeapFormatter<E>().display(iterator, 1, eachPad, withSpaces);
    }

    protected static class Iterator<E> implements AbstractTreeFormatter.TreeIterator<Integer, E> {
        private int numItems;
        private E[] items;

        public Iterator(int numItems, E[] items) {
            this.numItems = numItems;
            this.items = items;
        }

        private Integer nullIfInvalid(int t) {
            if (t > 0 && t <= numItems) return t;
            return null;
        }

        @Override
        public Integer getLeft(Integer t) {
            return nullIfInvalid(t * 2);
        }

        @Override
        public Integer getRight(Integer t) {
            return nullIfInvalid(t * 2 + 1);
        }

        @Override
        public E getValue(Integer t) {
            return items[t];
        }
    }
}
