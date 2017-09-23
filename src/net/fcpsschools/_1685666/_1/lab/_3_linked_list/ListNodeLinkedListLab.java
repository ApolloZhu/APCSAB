package net.fcpsschools._1685666._1.lab._3_linked_list;

import javafx.util.Pair;

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
    private static final Scanner INPUT = new Scanner(System.in);
    private static ListNode<Integer> h;
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

    public static void main(String[] args) {
        for (int j = 0; j < 3; j++)
            for (int i = 5; i > 0; i--)
                h = new ListNode<>(i, h);
//        h.getNext().getNext().getNext().getNext().setNext(h);
        printLinkedList(h);
        h = delete(h, 1, true);
        printLinkedList(h);
        char option;
        while ((option = getMenuOptionByAsking()) != 'z')
            try {
                OPERATIONS[option - 'a'].run();
            } catch (IndexOutOfBoundsException ignored) {
                System.out.println("Invalid option!");
            }
    }

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

    public static <E> ListNode<E> add/*After*/(ListNode<E> head, E value) {
        if (head == null) return new ListNode<>(value, null);
        head.setNext(new ListNode<>(value, head.getNext()));
        return head;
    }

    /**
     * Doesn't support circular linked list for performance reason.
     *
     * @param head
     * @param value
     * @param <E>
     * @return
     */
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

    /**
     * Doesn't support insert in order for circular list for performance reason, except when the element is larger than all other nodes in the list.
     *
     * @param head
     * @param value
     * @param <E>
     * @return
     */
    public static <E extends Comparable<E>> ListNode<E> insertInOrder(ListNode<E> head, E value) {
        if (head == null) return new ListNode<>(value, null);
        ListNode<E> previous = null, node = head, toInsert, newHead = head;
        while (node != null) {
            if (node.getValue().compareTo(value) <= 0) {
                previous = node;
                node = node.getNext();
                if (node == head) break;
                continue;
            }
            toInsert = new ListNode<>(value, node);
            if (previous == null) newHead = toInsert;
            else previous.setNext(toInsert);
            return newHead;
        }
        previous.setNext(new ListNode<>(value, node));
        return newHead;
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

    /**
     * @param head
     * @param <E>
     * @return the head of the clone of the given list.
     * @implSpec Complexity: O(n)
     */
    public static <E> ListNode<E> copy(ListNode<E> head) {
        ListNode<E> newHead = null,  // Head of the new list
                node = head,         // Temporary for iteration in the old list
                nodecpy,             // A hard copy of `node`
                newNode = null,      // Temporary for iteration in the new list
                next;                // Next node in the old list
        while (node != null) {
            // Make a copy:
            nodecpy = new ListNode<>(node.getValue(), next = node.getNext());
            // If not the first element, link it to the previous one in the new list
            if (newNode != null) newNode.setNext(nodecpy);
                // Else if is the first element, set it as the new head
            else newHead = nodecpy;
            // Record the last element in the new list
            newNode = nodecpy;
            // If circular, stop
            if (next == head) {
                nodecpy.setNext(newHead);
                break;
            }
            // Move on to the next element in the old list
            node = next;
        }
        return newHead;
    }

    public static <E> ArrayList<E> copyToArrayList(ListNode<E> head) {
        ArrayList<E> list = new ArrayList<>();
        for (ListNode<E> node = head; node != null; node = node.getNext() == head ? null : node.getNext())
            list.add(node.getValue());
        return list;
    }

    /**
     * May behave like rotate for circular list when the element to remove is the first one for performance reasons.
     *
     * @param head
     * @param value
     * @param deleteAll
     * @param <E>
     * @return
     */
    public static <E> ListNode<E> delete(ListNode<E> head, E value, boolean deleteAll) {
        ListNode<E> previous = null, node = head, next, newHead = head;
        while (node != null) {
            next = node.getNext();
            if (node.getValue().equals(value)) {
                // Remove first element will shift the new head
                if (previous == null) newHead = next;
                    // Otherwise remove from the previous node
                else previous.setNext(next);
                node.setNext(null);
                node = previous;
                // Break if only remove one
                if (!deleteAll) break;
            }
            // Break circular list
            if (next == head) break;
            // Update to next round
            previous = node;
            node = next;
        }
        return newHead;
    }

    public static <E> ListNode<E> appendList(ListNode<E> h1, ListNode<E> h2) {
        if (h1 == null) return h2;
        if (h2 == null) return h1;
        ListNode<E> node = h1, t1, t2;
        while ((t1 = node.getNext()) != null && t1 != h1) node = t1;
        node.setNext(h2);
        node = h2;
        while ((t2 = node.getNext()) != null && t2 != h2) node = t2;
        // If list 2 is not circular, keep it as it is
        // Otherwise insert it into list 1 even if list 1 is circular
        if (t1 == h1 || t2 == h2) node.setNext(h1);
        return h1;
    }

    /**
     * Merge two sorted list into a new sorted list.
     *
     * @param h1  head of a list with elements sorted in ascending order.
     * @param h2  head of another list with elements sorted in ascending order.
     * @param <E> any comparable.
     * @return head of the merged list, with elements sorted in ascending order.
     * @implSpec h1 and h2 are unchanged.
     */
    // FIXME: Doesn't work
    public static <E extends Comparable<E>> ListNode<E> mergeSorted(ListNode<E> h1, ListNode<E> h2) {
        if (h1 == null) return h2;
        if (h2 == null) return h1;
        ListNode<E> i = h1, j = h2, k = null, newHead = null, copy;
        while (i != null || j != null) {
            if (j == null || (i.getValue()).compareTo(j.getValue()) <= 0) {
                copy = new ListNode<>(i.getValue(), null);
                i = i.getNext() == h1 ? null : i.getNext();
            } else {
                copy = new ListNode<>(j.getValue(), null);
                j = j.getNext() == h2 ? null : j.getNext();
            }
            if (k == null) k = newHead = copy;
            else k.setNext(copy);
        }
        return newHead;
    }

    /**
     * Split the original list by index, arranged in random order. Original is emptied.
     *
     * @param head
     * @param <E>
     * @return
     */
    public static <E> Pair<ListNode<E>, ListNode<E>> splitEvenOdd(ListNode<E> head) {
        ListNode<E> even = null, odd = null, node = head, next;
        boolean isEven = true;
        while (node != null) {
            next = node.getNext();
            if (isEven) {
                node.setNext(even);
                even = node;
            } else {
                node.setNext(odd);
                odd = node;
            }
            isEven = !isEven;
            node = next == head ? null : next;
        }
        return new Pair<>(even, odd);
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

    static class ListNode<E> {
        private E value;
        private ListNode<E> next;

        public ListNode(E value, ListNode<E> next) {
            setValue(value);
            setNext(next);
        }

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }

        public ListNode<E> getNext() {
            return next;
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
}
