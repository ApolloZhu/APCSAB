package net.fcpsschools._1685666._3.lab._1_heap;

import net.fcpsschools._1685666.AbstractTreeFormatter;

/**
 * A class that visualizes heaps
 * in a more human friendly way.
 *
 * @author ApolloZhu, Pd. 1
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
