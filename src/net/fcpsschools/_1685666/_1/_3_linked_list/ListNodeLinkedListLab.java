package net.fcpsschools._1685666._1._3_linked_list;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Name: Zhiyu Zhu
 * Period: 1
 * Lab: Linked List Node
 * Purpose: Static operations
 * Due:
 * Submitted:
 * What I learned:
 * How I feel about this:
 * What I wonder:
 * Credits:
 * Whom I helped (to what extent):
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
    }

    private static ListNode<Integer> h;

    public static void main(String[] args) {
        for (int i = 5; i > 0; i--)
            h = new ListNode<>(i, h);
        h.getNext().getNext().getNext().getNext().setNext(h);
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

    public static void printLinkedList(ListNode head) {
        if (head == null)
            System.out.println("[]");
        else {
            StringBuilder sb = new StringBuilder("[");
            ListNode node = head;
            while (true) {
                sb.append(node.getValue());
                if ((node = node.getNext()) == head)
                    sb.append(", HEAD]");
                else if (node == null)
                    sb.append(']');
                else {
                    sb.append(", ");
                    continue;
                }
                System.out.println(sb.toString());
                return;
            }
        }
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

    // FIXME: For circular lists.
    public static <T> ListNode<T> removeFirst(ListNode<T> head) {
        return head == null
                ? null // throw new NoSuchElementException()
                : head.getNext();
    }

    public static <T> ListNode<T> removeLast(ListNode<T> head) {
        if (head == null) return null; // throw new NoSuchElementException();
        ListNode<T> previous = null, last = head, next;
        while ((next = last.getNext()) != null && next != head) {
            previous = last;
            last = last.getNext();
        }
        if (previous != null) previous.setNext(null);
        else last.setNext((head = null));
        return head;
    }

    public static <T> ListNode<T> remove(ListNode<T> head, T value) {
        if (true) {
            throw new UnsupportedOperationException();
        } else if (false) {
            throw new NoSuchElementException();
        }
        return null;
    }

    public static <T> void add(ListNode<T> head, T value) {
        if (head == null) return;
        head.setNext(new ListNode<>(value, head.getNext()));
    }

    public static <T> ListNode<T> reverseList(ListNode<T> head) {
        ListNode<T> cur = head, resultHead = null, tmp;
        while (cur != null) {
            tmp = cur.getNext();
            cur.setNext(resultHead);
            resultHead = cur;
            if ((cur = tmp) != head) continue;
            head.setNext(resultHead);
            break;
        }
        return resultHead;
    }

    public static <T> ListNode<T> rotate(ListNode<T> head) {
        if (true) throw new UnsupportedOperationException();
        return null;
    }

    public static <T> ListNode<T> middleNode(ListNode<T> head) {
        if (head == null) return null;  // throw new NoSuchElementException();
        ListNode<T> middle = head, cur = head, next, afterNext;
        while ((next = cur.getNext()) != null && next != head
                && (afterNext = next.getNext()) != null && afterNext != head) {
            middle = middle.getNext();
            cur = afterNext;
        }
        return middle;
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
