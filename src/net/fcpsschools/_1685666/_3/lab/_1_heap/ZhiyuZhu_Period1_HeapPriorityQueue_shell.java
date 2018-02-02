package net.fcpsschools._1685666._3.lab._1_heap;
// Name: Zhiyu Zhu
// Date: 2018/02/04
// What I learned:
// How I feel about this lab:
// I am wondering:

import net.fcpsschools._1685666.AbstractTreeFormatter;

import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class ZhiyuZhu_Period1_HeapPriorityQueue_shell
        <E extends Comparable<E>>
        extends PriorityQueue<E> {

    private static final int DFLT_CAPACITY = 1024;
    private final Iterator iterator = new Iterator();
    private final AbstractTreeFormatter<Integer, E> formatter = new AbstractTreeFormatter<>();
    private E items[];  // use a 1-D array instead of ArrayList
    private int numItems;  // number of elements in items

    public ZhiyuZhu_Period1_HeapPriorityQueue_shell() {
        this(DFLT_CAPACITY);
    }

    @SuppressWarnings({"unchecked"})
    public ZhiyuZhu_Period1_HeapPriorityQueue_shell(int initialCapacity) {
        items = (E[]) new Comparable[initialCapacity];
    }

    public static void main(String[] args) {
        ZhiyuZhu_Period1_HeapPriorityQueue_shell<Integer> heap = new ZhiyuZhu_Period1_HeapPriorityQueue_shell<>();
        // TODO: Tests
    }

    public boolean isEmpty() {
        return 0 == numItems;
    }

    public E peek() {
        try {
            return items[1];
        } catch (ArrayIndexOutOfBoundsException ignored) {
            throw new NoSuchElementException();
        }
    }

    public E remove() {
        E toRemove = peek();
        if (1 == numItems) {
            items[numItems--] = null;
        } else {
            items[1] = items[numItems--];
            reheapDown();
        }
        return toRemove;
    }

    public boolean add(E obj) {
        if (items.length == numItems)
            doubleCapacity();
        items[++numItems] = obj;
        reheapUp();
        return true;
    }

    private void reheapDown() {
        int endIndex = numItems / 2;
        for (int i = 1; i <= endIndex; i *= 2) {
            int left = i * 2, right = i * 2 + 1;
            if (items[left].compareTo(items[right]) < 0) {
                if (items[left].compareTo(items[i]) < 0) {
                    swapAt(left, i);
                } else return;
            } else {
                if (items[right].compareTo(items[i]) < 0) {
                    swapAt(right, i);
                } else return;
            }
        }
    }

    private void reheapUp() {
        for (int i = numItems; i > 1; i /= 2) {
            int parent = i / 2;
            if (items[i].compareTo(items[parent]) < 0) {
                swapAt(parent, i);
            } else return;
        }
    }

    private void swapAt(int i, int j) {
        E element = items[i];
        items[i] = items[j];
        items[j] = element;
    }

    @SuppressWarnings({"unchecked"})
    private void doubleCapacity() {
        Comparable[] copy = new Comparable[items.length * 2];
        System.arraycopy(items, 0, copy, 0, items.length);
        items = (E[]) copy;
    }

    public void display() {
        formatter.display(iterator, 1);
    }

    protected class Iterator implements AbstractTreeFormatter.TreeIterator<Integer, E> {
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
