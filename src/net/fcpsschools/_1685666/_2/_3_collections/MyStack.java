package net.fcpsschools._1685666._2._3_collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyStack<E> implements Iterable<E> {
    private ListNode head;
    private int size;

    public MyStack() {
    }

    public static void main(String[] args) {
        MyStack<Integer> t = new MyStack<>();
        for (Integer v = 9; v >= 0; v--) t.push(v);
        Iterator<Integer> iter = t.iterator();
        System.out.println("Traversing t using an iterator: ");
        iter.forEachRemaining(v -> System.out.print(v + " "));
        System.out.println("\n\nTraversing t using a for-each loop: ");
        for (int p : t) System.out.print(p + " ");
    }

    public E push(E v) {
        head = new ListNode(v, head);
        size++;
        return v;
    }

    public E seek(E v) {
        if (head == null) throw new NoSuchElementException();
        return head.getValue();
    }

    public E pop() {
        if (head == null) throw new NoSuchElementException();
        E value = head.getValue();
        head = head.getNext();
        size--;
        return value;
    }

    public boolean isEmpty() {
        return size == 0 || head == null;
    }

    public Iterator<E> iterator() {
        return new MyIterator();
    }

    public class MyIterator implements Iterator<E> {
        private ListNode current = head;
        private int size = -1;

        public boolean hasNext() {
            return current != null;
        }

        public E next() {
            if (-1 == size) size = MyStack.this.size;
            if (size != size()) throw new ConcurrentModificationException();
            E value = current.value;
            current = current.getNext();
            size -= 1;
            return value;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class ListNode {
        private E value;
        private ListNode next;

        public ListNode(E value, ListNode next) {
            this.value = value;
            this.next = next;
        }

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }

        public ListNode getNext() {
            return next;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }
    }
}

/* // Outputs
Traversing t using an iterator:
0 1 2 3 4 5 6 7 8 9

Traversing t using a for-each loop:
0 1 2 3 4 5 6 7 8 9
*/
