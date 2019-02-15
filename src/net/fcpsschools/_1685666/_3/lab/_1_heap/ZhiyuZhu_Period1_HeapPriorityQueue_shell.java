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
// Name: Zhiyu Zhu


import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * @author Apollo/Zhiyu Zhu/朱智语
 *
 * Date: 2018/02/04
 * What I learned:
 * - Using array to represent a complete tree.
 * How I feel about this lab:
 * - Either too hard, or too easy to test.
 * I am wondering:
 * - Why can't they redesign Java to erase generic type info after compilation?
 * - How to choose a good default capacity?
 * @param <E> Type of elements in queue.
 */
public class ZhiyuZhu_Period1_HeapPriorityQueue_shell<E extends Comparable<E>> extends PriorityQueue<E> {
    private static final int DFLT_CAPACITY = 1023;
    private E items[];  // use a 1-D array instead of ArrayList
    private int numItems;  // number of elements in items

    public ZhiyuZhu_Period1_HeapPriorityQueue_shell() {
        this(DFLT_CAPACITY);
    }

    @SuppressWarnings({"unchecked"})
    /// Can hold up to `initialCapacity-1` elements.
    public ZhiyuZhu_Period1_HeapPriorityQueue_shell(int initialCapacity) {
        items = (E[]) new Comparable[initialCapacity];
    }

    public static void main(String[] args) {
        // 1-6-5-7-8-9, add 10
        Heap<Integer> heap = heapOf(1, 6, 5, 7, 8, 9);
        heap.display();
        System.out.println("Add 0");
        heap.add(0);
        while (!heap.isEmpty()) {
            heap.display();
            System.out.println("Removed " + heap.remove());
        }
    }

    @SafeVarargs
    private static <E extends Comparable<E>> Heap<E> heapOf(E... elements) {
        Heap<E> heap = new Heap<>(elements.length + 1);
        Collections.addAll(heap, elements);
        return heap;
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
        items[1] = items[numItems];
        items[numItems--] = null;
        reheapDown();
        return toRemove;
    }

    public boolean add(E obj) {
        if (items.length == ++numItems)
            doubleCapacity();
        items[numItems] = obj;
        reheapUp();
        return true;
    }

    private void reheapDown() {
        reheapDown(1);
    }

    private void reheapDown(int i) {
        if (numItems / 2 < i) return;
        final int left = i * 2, right = i * 2 + 1;
        if (numItems >= right && items[right].compareTo(items[left]) < 0) {
            if (items[right].compareTo(items[i]) < 0) {
                swapAt(right, i);
                reheapDown(left);
            }
        } else {
            if (items[left].compareTo(items[i]) < 0) {
                swapAt(left, i);
                reheapDown(left);
            }
        }
    }

    private void reheapUp() {
        reheapUp(numItems);
    }

    private void reheapUp(int i) {
        if (1 >= i) return;
        int parent = i / 2;
        if (items[i].compareTo(items[parent]) >= 0) return;
        swapAt(parent, i);
        reheapUp(i / 2);
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
        HeapFormatter.display(numItems, items);
    }

    private static class Heap<E extends Comparable<E>>
            extends ZhiyuZhu_Period1_HeapPriorityQueue_shell<E> {
        private Heap() {
            super();
        }

        private Heap(int initialCapacity) {
            super(initialCapacity);
        }
    }
}
/*
 ┌─1─┐
┌6┐ ┌5
7 8 9
Add 0
 ┌─0─┐
┌6┐ ┌1┐
7 8 9 5
Removed 0
 ┌─1─┐
┌6┐ ┌5
7 8 9
Removed 1
 ┌─5┐
┌6┐ 9
7 8
Removed 5
 ┌6┐
┌7 9
8
Removed 6
┌7┐
8 9
Removed 7
┌8
9
Removed 8
9
Removed 9
*/
