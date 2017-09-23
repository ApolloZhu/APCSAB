package net.fcpsschools._1685666._1.lab._3_linked_list;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author ApolloZhu, Pd. 1
 */
class ListNodeLinkedListLabTest {

    static ListNodeLinkedListLab.ListNode<Integer> makeRepeatingList() {
        ListNodeLinkedListLab.ListNode<Integer> h = null;
        for (int j = 0; j < 3; j++)
            for (int i = 5; i > 0; i--)
                h = new ListNodeLinkedListLab.ListNode<>(i, h);
        return h;
    }

    static ListNodeLinkedListLab.ListNode<Integer> makeSortedList(int start, int length) {
        ListNodeLinkedListLab.ListNode<Integer> h = null;
        for (int i = start + length - 1; i >= start; i--)
            h = new ListNodeLinkedListLab.ListNode<>(i, h);
        return h;
    }

    static ListNodeLinkedListLab.ListNode<Integer> makeSortedList(int length) {
        return makeSortedList(1, length);
    }

    static ListNodeLinkedListLab.ListNode<Integer> makeCircularList() {
        ListNodeLinkedListLab.ListNode<Integer> h = null;
        for (int i = 5; i > 0; i--)
            h = new ListNodeLinkedListLab.ListNode<>(i, h);
        h.getNext().getNext().getNext().getNext().setNext(h);
        return h;
    }


    @Test
    void printLinkedList() {
        ListNodeLinkedListLab.printLinkedList(makeSortedList(5));
        ListNodeLinkedListLab.printLinkedList(makeCircularList());
        ListNodeLinkedListLab.printLinkedList(null);
    }

    @Test
    void hasTwo() {
        assertEquals(true, ListNodeLinkedListLab.hasTwo(makeSortedList(5)));
        assertEquals(true, ListNodeLinkedListLab.hasTwo(makeCircularList()));
        assertEquals(false, ListNodeLinkedListLab.hasTwo(makeSortedList(1)));
        assertEquals(false, ListNodeLinkedListLab.hasTwo(null));
    }

    @Test
    void size() {
        for (int i = 0; i < 5; i++)
            assertEquals(i, ListNodeLinkedListLab.size(makeSortedList(i)));
        assertEquals(5, ListNodeLinkedListLab.size(makeCircularList()));
    }

    @Test
    void removeFirst() {
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.removeFirst(makeCircularList()));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.removeFirst(makeSortedList(5)));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.removeFirst(makeSortedList(1)));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.removeFirst(null));
    }

    @Test
    void removeLast() {
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.removeLast(makeCircularList()));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.removeLast(makeSortedList(5)));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.removeLast(makeSortedList(1)));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.removeLast(null));
    }

    @Test
    void add() {
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.add(makeCircularList(), -1));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.add(makeSortedList(5), -1));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.add(makeSortedList(1), -1));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.add(null, -1));
    }

    @Test
    void addFirst() {
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.addFirst(makeSortedList(5), -1));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.addFirst(makeSortedList(1), -1));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.addFirst(null, -1));
    }

    @Test
    void addLast() {
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.addLast(makeCircularList(), -1));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.addLast(makeSortedList(5), -1));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.addLast(makeSortedList(1), -1));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.addLast(null, -1));
    }

    @Test
    void reverseList() {
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.reverseList(makeCircularList()));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.reverseList(makeSortedList(5)));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.reverseList(makeSortedList(1)));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.reverseList(null));
    }

    @Test
    void rotate() {
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.rotate(makeCircularList()));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.rotate(makeSortedList(5)));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.rotate(makeSortedList(1)));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.rotate(null));
    }

    @Test
    void middleNode() {
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.middleNode(makeCircularList()));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.middleNode(makeSortedList(5)));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.middleNode(makeSortedList(1)));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.middleNode(null));
    }

    @Test
    void insertInOrder() {
        for (int i = -1; i < 7; i++) {
            ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.insertInOrder(makeSortedList(5), i));
            ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.insertInOrder(makeSortedList(1), i));
            ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.insertInOrder(null, i));
            System.out.println();
        }
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.insertInOrder(makeCircularList(), 6));
    }

    @Test
    void find() {
        for (int i = 0; i < 7; i++) {
            ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.find(makeCircularList(), i));
            ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.find(makeSortedList(5), i));
            ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.find(makeSortedList(1), i));
            ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.find(null, i));
            System.out.println();
        }
    }

    @Test
    void findLast() {
        for (int i = 0; i < 7; i++) {
            ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.findLast(makeCircularList(), i));
            ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.findLast(makeRepeatingList(), i));
            ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.findLast(makeSortedList(5), i));
            ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.findLast(makeSortedList(1), i));
            ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.findLast(null, i));
            System.out.println();
        }
    }

    @Test
    void copy() {
        for (ListNodeLinkedListLab.ListNode<Integer> h : new ListNodeLinkedListLab.ListNode[]{
                makeCircularList(), makeSortedList(5), makeSortedList(1), null
        }) {
            ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.copy(h));
            ListNodeLinkedListLab.printLinkedList(h);
        }
    }

    @Test
    void copyToArrayList() {
        System.out.println(ListNodeLinkedListLab.copyToArrayList(makeCircularList()));
        System.out.println(ListNodeLinkedListLab.copyToArrayList(makeSortedList(5)));
        System.out.println(ListNodeLinkedListLab.copyToArrayList(makeSortedList(1)));
        System.out.println(ListNodeLinkedListLab.copyToArrayList(null));
    }

    @Test
    void delete() {
        for (int i = 0; i < 7; i++) {
            for (boolean deleteAll : new boolean[]{true, false}) {
                ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.delete(makeCircularList(), i, deleteAll));
                ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.delete(makeRepeatingList(), i, deleteAll));
                ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.delete(makeSortedList(5), i, deleteAll));
                ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.delete(makeSortedList(1), i, deleteAll));
                ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.delete(null, i, deleteAll));
            }
        }
    }

    @Test
    void appendList() {
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.appendList(null, null));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.appendList(makeSortedList(5), null));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.appendList(null, makeSortedList(5)));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.appendList(makeCircularList(), makeSortedList(5)));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.appendList(makeSortedList(5), makeCircularList()));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.appendList(makeCircularList(), makeCircularList()));

    }

    @Test
    void mergeSorted() {
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.mergeSorted(null, null));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.mergeSorted(makeSortedList(5), null));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.mergeSorted(null, makeSortedList(5)));
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.mergeSorted(makeSortedList(2, 9), makeSortedList(5)));
    }

    @Test
    void splitEvenOdd() {
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.splitEvenOdd(makeCircularList()).getKey());
        ListNodeLinkedListLab.printLinkedList(ListNodeLinkedListLab.splitEvenOdd(makeCircularList()).getValue());
    }
}