package net.fcpsschools._1685666._1._3_linked_list;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Name: Zhiyu Zhu
 * Period: 1
 * Lab: Linked List Node
 * Purpose: Static operations
 * Due:
 * Submitted:
 * What I learned:
 * - how to implement the operations that cares for the circular lists.
 * How I feel about this: very interesting, it requires a lot of think of how to deal with the edge cases,
 * I mean, when the list is circular.
 * What I wonder:
 * Credits: my whiteboard and pen for trial and errors. 5 oranges for visualizing the operation.
 * Whom I helped (to what extent): Liam, help him understand the structure of shell program
 */

public class ListNodeLinkedListLab {
    private static class ListNode<E> {
        private E value;
        private ListNode<E> next;

        public ListNode(E value, ListNode<E> next) {
            setValue(value);
            setNext(next);
        }

        public E getValue() {
            return value;
        }

        public ListNode<E> getNext() {
            return next;
        }

        public void setValue(E value) {
            this.value = value;
        }

        public void setNext(ListNode<E> next) {
            this.next = next;
        }

        @Override
        public String toString() {
            String str = super.toString();
            return str.substring(str.indexOf("@"));
        }
    }

    private static ListNode<Integer> h;

    public static void main(String[] args) {
        for (int i = 5; i > 0; i--)
            h = new ListNode<>(i, h);
        h.getNext().getNext().getNext().getNext().setNext(h);
        ListNode<Integer> d = copy(h);
        printLinkedList(h);
        printLinkedList(d);
        char option;
        while ((option = getMenuOptionByAsking()) != 'z')
            try {
                OPERATIONS[option - 'a'].run();
            } catch (IndexOutOfBoundsException ignored) {
                System.out.println("Invalid option!");
            }
    }

    private static final Scanner INPUT = new Scanner(System.in);

    private static final Runnable[] OPERATIONS = {
            () -> {
                System.out.println("list: ");
                printLinkedList(h);
            },
            () -> {
                System.out.println("The List has at least two element?");
                System.out.println(hasTwo(h));
            },
            () -> {
                System.out.print("The size of the array is: ");
                System.out.println(size(h));
            },
            () -> {
                h = removeFirst(h);
                System.out.print("New list is: ");
                printLinkedList(h);
            },
            () -> {
                System.out.println("Enter number: ");
                add(h, INPUT.nextInt());
                System.out.println("New list is: ");
                printLinkedList(h);
            },
            () -> {
                h = reverseList(h);
                System.out.println("Reverse is: ");
                printLinkedList(h);
            },
            () -> {
                h = rotate(h);
                System.out.println("rotated array is: ");
                printLinkedList(h);
            },
            () -> {
                printLinkedList(h);
                System.out.println("\nmiddle node is: " + middleNode(h).getValue());
            },
            () -> {
                h = removeLast(h);
                System.out.print("New list is: ");
                printLinkedList(h);
            }
    };

    public static String format(ListNode head) {
        if (head == null) return "[]";
        StringBuilder sb = new StringBuilder("[");
        ListNode node = head;
        while (true) {
            sb.append(node.getValue());
            sb.append(" ");
            sb.append(node);
            if ((node = node.getNext()) == head)
                sb.append(", HEAD]");
            else if (node == null)
                sb.append(']');
            else {
                sb.append(", ");
                continue;
            }
            return sb.toString();
        }
    }

    public static void printLinkedList(ListNode head) {
        System.out.println(format(head));
    }

    public static boolean hasTwo(ListNode head) {
        return size(head) >= 2;
    }

    public static int size(ListNode head) {
        int count = 0;
        for (ListNode node = head, next; node != null;
             node = ((next = node.getNext()) == head) ? null : next)
            count++;
        return count;
    }

    public static <E> ListNode<E> removeFirst(ListNode<E> head) {
        if (head == null) return null;
        for (ListNode<E> node = head; node != null; node = node.getNext())
            if (node.getNext() == head)
                node.setNext(null);
        ListNode<E> newHead = head.getNext();
        head.setNext(null);
        return newHead;
    }

    public static <E> ListNode<E> removeLast(ListNode<E> head) {
        if (head == null) return null;
        ListNode<E> previous = null, last = head, next;
        while ((next = last.getNext()) != null && next != head) {
            previous = last;
            last = last.getNext();
        }
        if (previous != null) previous.setNext(null);
        else last.setNext((head = null));
        return head;
    }

    public static <E> ListNode<E> remove(ListNode<E> head, E value) {
        return delete(head, value, false);
    }

    public static <E> void add/*After*/(ListNode<E> head, E value) {
        if (head == null) return;
        head.setNext(new ListNode<>(value, head.getNext()));
    }

    public static <E> ListNode<E> addFirst(ListNode<E> head, E value) {
        return new ListNode<>(value, head);
    }

    public static <E> ListNode<E> addLast(ListNode<E> head, E value) {
        if (head == null) return new ListNode<>(value, null);
        ListNode<E> node = head, next;
        while ((next = node.getNext()) != null && next != head) node = next;
        node.setNext(new ListNode<>(value, node.getNext()));
        return head;
    }

    public static <E> ListNode<E> reverseList(ListNode<E> head) {
        ListNode<E> cur = head, resultHead = null, tmp;
        while (cur != null) { // handled head == null
            tmp = cur.getNext();
            cur.setNext(resultHead);
            resultHead = cur;
            if ((cur = tmp) != head) continue;
            head.setNext(resultHead);
            break;
        }
        return resultHead;
    }

    // Rotate one to the right
    public static <E> ListNode<E> rotate(ListNode<E> head) {
        if (head == null) return null;
        ListNode<E> node = head, previous = null, next;
        while ((next = node.getNext()) != null && next != head) {
            previous = node;
            node = next;
        }
        // circular:
        if (next == head) return node;
        // size(head) == 1
        if (previous == null) return head;
        previous.setNext(null);
        node.setNext(head);
        return node;
    }

    public static <E> ListNode<E> middleNode(ListNode<E> head) {
        if (head == null) return null;
        ListNode<E> middle = head, cur = head, next, afterNext;
        while ((next = cur.getNext()) != null && next != head
                && (afterNext = next.getNext()) != null && afterNext != head) {
            middle = middle.getNext();
            cur = afterNext;
        }
        return middle;
    }

    public static <E> ListNode<E> insertInorder(ListNode<E> head, E value) {
        if (head == null) return null;
        throw new UnsupportedOperationException();
    }

    public static <E> ListNode<E> find(ListNode<E> head, E value) {
        if (head == null) return null;
        ListNode<E> node = head, next;
        while (node != null) {
            if (node.getValue().equals(value)) return node;
            if ((next = node.getNext()) == head) break;
            node = next;
        }
        return null;
    }

    public static <E> ListNode<E> findLast(ListNode<E> head, E value) {
        if (head == null) return null;
        ListNode<E> node = head, next, result = null;
        while (node != null) {
            if (node.getValue().equals(value)) result = node;
            if ((next = node.getNext()) == head) break;
            node = next;
        }
        return result;
    }

    public static <E> ListNode<E> copy(ListNode<E> head) {
        ListNode<E> node = head, next, newHead = null, cur, newNode = null;
        while (node != null) {
            cur = new ListNode<>(node.getValue(), next = node.getNext());
            if (newNode != null) newNode.setNext(cur);
            newNode = cur;
            if (newHead == null) newHead = cur;
            if (next == head) {
                cur.setNext(newHead);
                break;
            }
            node = next;
        }
        return newHead;
    }

    public static <E> ArrayList<E> copyToArrayList(ListNode<E> head) {
        if (head == null) return new ArrayList<>();
        throw new UnsupportedOperationException();
    }

    public static <E> ListNode<E> delete(ListNode<E> head, E value, boolean deleteAll) {
        if (head == null) return null;
        throw new UnsupportedOperationException("RETURN NEW HEAD");
    }

    public static <E> ListNode<E> appendList(ListNode<E> h1, ListNode<E> h2) {
        if (h1 == null) return h2;
        if (h2 == null) return h1;
        ListNode<E> node = h1, t1, t2;
        while ((t1 = node.getNext()) != null && t1 != h1) node = t1;
        node.setNext(h2);
        node = h2;
        while ((t2 = node.getNext()) != null && t2 != h2) node = t2;
        // If list 2 is circular, keep it as it is
        // Otherwise insert it into list 1 even if list 1 is circular
        if (t2 != h2) node.setNext(t1);
        return h1;
    }

    public static <E> ListNode<E> mergeSorted(ListNode<E> h1, ListNode<E> h2) {
        if (h1 == null) return h2;
        if (h2 == null) return h1;
        throw new UnsupportedOperationException();
    }

    public static char getMenuOptionByAsking() {
        System.out.println("\n====> What would you like to do?\n" +
                "a) Print list\n" +
                "b) Check if list has at least two nodes\n" +
                "c) Get size of the list\n" +
                "d) Remove first node\n" +
                "e) Add a node\n" +
                "f) Reverse\n" +
                "g) Rotate\n" +
                "h) Get middle node\n" +
                "i) Remove last node\n" +
                "z) Quit");
        return INPUT.next().charAt(0);
    }
}
