package net.fcpsschools._1685666._1.lab._3_double_link_list;

import java.util.AbstractList;

/**
 * Name: Zhiyu Zhu
 * Period: Period 1
 * DLL.java - Implementing Doubly Linked List
 * Due Date: 10/7 23:59
 * Date Submitted:
 * ## What I learned:
 * - Adding and removing a node is more efficient than SLL.
 * ## How I feel about this lab:
 * - Abstracting the loop procedure took me some time to debug,
 * and had me introducing another enum,
 * but I believe the approach looks better now.
 * ## What I wonder:
 * - Although it doesn't matter, but should head.getNext by the first or last?
 */
public class DLL<E> extends AbstractList<E> {
    /**
     * dummy node--very useful--simplifies the code
     * head.getNext: first node in the list
     * head.getPrev:  last node in the list
     */
    private final DLNode<E> head = new DLNode<E>();
    private int size;

    /*
     [Apple, Banana, Cucumber, Dumpling, Escargot]
     Size: 5
     [Apple, Banana, Cucumber, Escargot]
     Size: 4
     Removed Dumpling
     Add at 3:   [Apple, Banana, Cucumber, Cheese, Escargot]
     Get values at 1 and first: Banana and Apple
     No change: [Apple, Banana, Cucumber, Cheese, Escargot]
     Apple is now removed!
     [Banana, Cucumber, Cheese, Escargot]
     Add first:  [Anchovie, Banana, Cucumber, Cheese, Escargot]
     Size: 5
     Set the second:  [Anchovie, Banana, Rread, Cheese, Escargot]
     */
    public static void main(String args[]) {
        DLL<String> list = new DLL<>();

        list.addLast("Apple");
        list.addLast("Banana");
        list.addLast("Cucumber");
        list.add("Dumpling");
        list.add("Escargot");
        System.out.println(list);
        System.out.println("Size: " + list.size());
        Object obj = list.remove(3);
        System.out.println(list);
        System.out.println("Size: " + list.size());
        System.out.println("Removed " + obj);
        System.out.print("Add at 3:   ");
        list.add(3, "Cheese");
        System.out.println(list);
        System.out.println("Get values at 1 and first: " + list.get(1) + " and " + list.getFirst());
        System.out.println("No change: " + list);
        System.out.println(list.removeFirst() + " is now removed!");
        System.out.println(list);
        System.out.print("Add first:  ");
        list.addFirst("Anchovie");
        System.out.println(list);
        System.out.println("Size: " + list.size());
        System.out.print("Set the second:  ");
        list.set(2, "Rread");
        System.out.println(list);
    }

    /**
     * @return size of this list.
     */
    public int size() {
        return size;
    }

    /**
     * Make sure a specific index is valid.
     *
     * @param index      index to check.
     * @param includeEnd if index for which index equals size is a valid index.
     * @throws IndexOutOfBoundsException if the given index is not a valid index.
     */
    protected void validateIndex(int index, boolean includeEnd)
            throws IndexOutOfBoundsException {
        if (index < 0 || (includeEnd ? index > size : index >= size))
            throw new IndexOutOfBoundsException(String.valueOf(index));
    }

    /**
     * Finds and returns the node at given index.
     *
     * @param index            index of the node to look for.
     * @param edgeCaseBehavior what happens when index equals to size.
     * @return node at the given index, or HEAD if edge case behavior is RETURN_HEAD.
     * @throws IndexOutOfBoundsException if index is less than 0 or greater than size,
     *                                   or when edge case behavior is EXCEPTION and index equals to size.
     * @throws IllegalStateException     You should never see this, but in case you do, file a bug report.
     */
    protected DLNode<E> nodeAtIndex(int index,
                                    BehaviorWhenIndexEqualsSize edgeCaseBehavior)
            throws IllegalStateException {
        // Early return if index equals size or is invalid.
        validateIndex(index, edgeCaseBehavior != BehaviorWhenIndexEqualsSize.EXCEPTION);
        if (index == size && edgeCaseBehavior == BehaviorWhenIndexEqualsSize.RETURN_HEAD) return head;
        // Setup loop with efficiency in mind.
        boolean direction = index < size / 2;
        int i = direction ? 0 : size - 1;
        DLNode<E> node = direction ? head.getNext() : head.getPrev();
        // Loop till node
        while (node != head) {
            if (i == index) return node;
            node = direction ? node.getNext() : node.getPrev();
            i += direction ? 1 : -1;
        }
        throw new IllegalStateException("File a bug report.");
    }

    /**
     * Appends an object to the end of this.
     * Size is increased by one.
     *
     * @param obj value to append to the end of the list.
     * @return true.
     */
    public boolean add(E obj) {
        addLast(obj);
        return true;
    }

    /**
     * Inserts an object at position of certain index.
     * Size is increased by one.
     *
     * @param index index to insert at.
     * @param obj   object to insert at the given index.
     */
    public void add(int index, E obj) {
        DLNode<E> node = nodeAtIndex(index, BehaviorWhenIndexEqualsSize.RETURN_HEAD);
        DLNode<E> toAdd = new DLNode<>(obj, node.getPrev(), node);
        node.getPrev().setNext(toAdd);
        node.setPrev(toAdd);
        size++;
    }

    /**
     * Finds and returns the value of the node at a given position.
     *
     * @param index index of the position to get value from.
     * @return the value at the specified index.
     */
    public E get(int index) {
        return nodeAtIndex(index, BehaviorWhenIndexEqualsSize.EXCEPTION).getValue();
    }

    /**
     * Set the value at a certain index to a new value.
     *
     * @param index index of the node to change value.
     * @param obj   the new value to update to.
     * @return the old value at the specified index.
     */
    public E set(int index, E obj) {
        DLNode<E> node = nodeAtIndex(index, BehaviorWhenIndexEqualsSize.EXCEPTION);
        final E value = node.getValue();
        node.setValue(obj);
        return value;
    }

    /**
     * Removes the node at a specific position of a certain index.
     * Size is decreased by one.
     *
     * @param index index of the position of the node to remove.
     * @return the value of the node removed.
     */
    public E remove(int index) {
        DLNode<E> node = nodeAtIndex(index, BehaviorWhenIndexEqualsSize.EXCEPTION);
        final E value = node.getValue();
        node.getPrev().setNext(node.getNext());
        node.getNext().setPrev(node.getPrev());
        size--;
        // Perform Clean Up
        node.setPrev(null);
        node.setNext(null);
        return value;
    }

    /**
     * Inserts an object at the front of this list.
     * Size is increased by one.
     *
     * @param obj value to insert in front.
     */
    public void addFirst(E obj) {
        add(0, obj);
    }

    /**
     * Appends an object after the end of this list.
     * Size is increased by one.
     *
     * @param obj value to insert in front.
     */
    public void addLast(E obj) {
        add(size, obj);
    }

    /**
     * @return the first value in this list.
     */
    public E getFirst() {
        return head.getNext().getValue();
    }

    /**
     * @return the last value in this list.
     */
    public E getLast() {
        return head.getPrev().getValue();
    }

    /**
     * Removes the node in the front.
     * Size is decreased by one.
     *
     * @return the original value at the head of this list.
     */
    public E removeFirst() {
        return remove(0);
    }

    /**
     * Removes the node at the rear.
     * Size is decreased by one.
     *
     * @return the original value at the rear of this list.
     */
    public E removeLast() {
        return remove(size - 1);
    }

    public String toString() {
        if (size == 0
                || /*although unlikely*/ head == null
                || head.getNext() == null
                || head.getPrev() == null)
            return "[]";
        StringBuilder sb = new StringBuilder("[");
        DLNode<E> node = head.getNext();
        while (true) {
            sb.append(node.getValue());
            if ((node = node.getNext()) == head
                    || /*although unlikely*/ node == null)
                sb.append(']');
            else {
                sb.append(", ");
                continue;
            }
            return sb.toString();
        }
    }

    /**
     * Behavior of nodeAtIndex when the given index equals to size of this list.
     *
     * @see #nodeAtIndex(int, BehaviorWhenIndexEqualsSize)
     */
    private enum BehaviorWhenIndexEqualsSize {
        EXCEPTION, RETURN_HEAD
    }

    private class DLNode<E> {
        private E value;
        private DLNode prev;
        private DLNode next;

        public DLNode(E arg, DLNode<E> p, DLNode<E> n) {
            value = arg;
            prev = p;
            next = n;
        }

        public DLNode() {
            value = null;
            next = this;
            prev = this;
        }

        public DLNode<E> getNext() {
            return next;
        }

        public void setNext(DLNode<E> arg) {
            next = arg;
        }

        public DLNode<E> getPrev() {
            return prev;
        }

        public void setPrev(DLNode<E> arg) {
            prev = arg;
        }

        public E getValue() {
            return value;
        }

        public void setValue(E arg) {
            value = arg;
        }
    }
}
