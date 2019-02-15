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

package net.fcpsschools._1685666._1.lab._2_linked_list;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Apollo/Zhiyu Zhu/朱智语
 * Period: 1
 * Lab: Linked List Node
 * Purpose: Static operations on singly linked list nodes.
 * Due: 9/24 23:59:59
 * Submitted: 9/25 1:00 exactly. (Scheduled email)
 * What I learned:
 * - implementation of the general operations on (circular) linked list nodes.
 * <p>
 * How I feel about this:
 * - Very interesting, it requires a lot of think of how to deal with the edge cases.
 * - I mean, especially null pointers, and when the list is circular.
 * <p>
 * What I wonder:
 * - What if I turn in this late after an hour it is due, although I finished long before.
 * - How should we make a decision between efficiency and robustness? (e.g. removeFirst when list is circular).
 * - How can Java enforce the caller to not discard the return value by default.
 * - How can Java APIs follow a certain naming convention to allude its side effect (if is mutating)
 * - How to efficiently deal with partially circular lists (e.g. 0x01 -> 0x02 -> 0x03 -> 0x04 -> 0x02)?
 * <p>
 * Credits:
 * - My whiteboard and pen for trial and errors.
 * - 5 oranges for visualizing the operations.
 * - May Gong for providing a test data to help me realize a NullPointerException in mergeSorted.
 * <p>
 * Whom I helped (to what extent):
 * - Liam, helped him understand the structure of the shell program.
 * - May Gong, helped her understand why her code is behaving differently that what she think it will do.
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
        printLinkedList(h);
        char option;
        while ((option = getMenuOptionByAsking()) != 'z')
            try {
                OPERATIONS[option - 'a'].run();
            } catch (IndexOutOfBoundsException ignored) {
                System.out.println("Invalid option!");
            }
    }

    /**
     * Formats the given linked list and returns the formatted String.
     *
     * @param head the first node in the linked list.
     * @return array like representation for the list.
     */
    public static String format(ListNode head) {
        if (head == null) return "[]";
        StringBuilder sb = new StringBuilder("[");
        ListNode node = head;
        while (true) {
            sb.append(node.getValue());
            sb.append(" ");
            sb.append(node); // Memory address
            if ((node = node.getNext()) == head)
                sb.append(", HEAD]"); // Circular list
            else if (node == null)
                sb.append(']');
            else {
                sb.append(", ");
                continue;
            }
            return sb.toString();
        }
    }

    /**
     * Prints the given linked list on a line.
     *
     * @param head the first node in the list.
     */
    public static void printLinkedList(ListNode head) {
        System.out.println(format(head));
    }

    /**
     * Checks if the given linked list has two or more nodes.
     *
     * @param head the first node in the list.
     * @return true if the list has at least two nodes.
     */
    public static boolean hasTwo(ListNode head) {
        return size(head) >= 2;
    }

    /**
     * Counts and returns how many nodes are there in the list.
     *
     * @param head the first node in the list.
     * @return the length of the linked list.
     */
    public static int size(ListNode head) {
        int count = 0;
        for (ListNode node = head, next; node != null;
             node = ((next = node.getNext()) == head) ? null : next)
            count++;
        return count;
    }

    /**
     * Removes the first node in the list.
     *
     * @param head the first node in the list.
     * @param <E>  any class.
     * @return the new head for the list.
     */
    public static <E> ListNode<E> removeFirst(ListNode<E> head) {
        if (head == null) return null;
        for (ListNode<E> node = head; node != null; node = node.getNext())
            if (node.getNext() == head)
                node.setNext(null);
        ListNode<E> newHead = head.getNext();
        head.setNext(null);
        return newHead;
    }

    /**
     * Removes the last node in the list.
     *
     * @param head the first node in the list.
     * @param <E>  any class.
     * @return the new head of the list.
     */
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

    /**
     * Add an element after the first node with the given value.
     *
     * @param head  the first node in the list.
     * @param value the value to add.
     * @param <E>   any class.
     * @return the new head of the list.
     */
    public static <E> ListNode<E> add/*After*/(ListNode<E> head, E value) {
        if (head == null) return new ListNode<>(value, null);
        head.setNext(new ListNode<>(value, head.getNext()));
        return head;
    }

    /**
     * Add an element before the first node with the given value.
     * Doesn't support circular linked list for performance reason.
     *
     * @param head  the first node in the list.
     * @param value the value to add.
     * @param <E>   any class.
     * @return the new head of the list.
     */
    public static <E> ListNode<E> addFirst(ListNode<E> head, E value) {
        return new ListNode<>(value, head);
    }

    /**
     * Add an element after the entire list with the given value.
     *
     * @param head  the first node in the list.
     * @param value the value to add.
     * @param <E>   any class.
     * @return the new head of the list.
     */
    public static <E> ListNode<E> addLast(ListNode<E> head, E value) {
        if (head == null) return new ListNode<>(value, null);
        ListNode<E> node = head, next;
        while ((next = node.getNext()) != null && next != head) node = next;
        node.setNext(new ListNode<>(value, node.getNext()));
        return head;
    }

    /**
     * Efficiently reverses the given list.
     *
     * @param head the first node in the list.
     * @param <E>  any class.
     * @return the new head of the list.
     */
    public static /*mutating*/ <E> ListNode<E> reverseList(ListNode<E> head) {
        ListNode<E> cur = head, resultHead = null, tmp;
        while (cur != null) { // Checks if is the last element.
            tmp = cur.getNext(); // Keep a hold on the next node in the list.
            cur.setNext(resultHead); // Attach the current list head
            resultHead = cur; // Update head to current.
            if ((cur = tmp) != head) continue; // Move cur to the next
            head.setNext(resultHead); // If circular, make the reverse circular
            break; // Exit loop for circular.
        }
        return resultHead;
    }

    /**
     * Moves each node to its next index, substituting the last as the first instead.
     *
     * @param head the first node in the list.
     * @param <E>  any class.
     * @return the new head of the list.
     */
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

    /**
     * Returns the node in the middle of the given list.
     *
     * @param head the first node in the list.
     * @param <E>  any class.
     * @return pointer to the node at index (n-1) / 2; null otherwise.
     */
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
     * Inserts a node in an ascending list, and the list is still in order afterwards.
     * Doesn't support circular list for performance reason,
     * except when the element is larger than all other nodes in the list.
     *
     * @param head  the first node in an ascending list.
     * @param value the value to insert.
     * @param <E>   any comparable.
     * @return the new head of the list.
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
        // If bigger than all elements.
        previous.setNext(new ListNode<>(value, node));
        return newHead;
    }

    /**
     * Finds and returns the first node with the given value.
     *
     * @param head  the first node in the list.
     * @param value the value to find in the list.
     * @param <E>   any class.
     * @return pointer to the node in the list with given value; null otherwise.
     */
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

    /**
     * Finds and returns the last node with the given value.
     *
     * @param head  the first node in the list.
     * @param value the value to find in the list.
     * @param <E>   any class.
     * @return pointer to the last node in the list with given value; null otherwise.
     */
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
     * Make a clone of the given list. The original is not changed.
     *
     * @param head the first node in the list.
     * @param <E>  any class.
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

    /**
     * Make a copy of the list as an array list.
     *
     * @param head the first node in the list.
     * @param <E>  any class.
     * @return the array list, with same content as the list.
     */
    public static <E> ArrayList<E> copyToArrayList(ListNode<E> head) {
        ArrayList list = new ArrayList();
        for (ListNode<E> node = head, next; node != null; node = next) {
            list.add(node.getValue());
            if ((next = node.getNext()) != head) continue;
            list.add(list);
            next = null;
        }
        return list;
    }

    /**
     * Remove first element, or all elements if `deleteAll`, with value equalling to the given value.
     * For performance reasons, may behave like rotate for circular lists when the element to remove is the first one,
     *
     * @param head      the first node in the list.
     * @param value     the value of the node(s) to remove.
     * @param deleteAll true if should remove all matching elements; false if only removes the first one.
     * @param <E>       any class.
     * @return the new head of the list.
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

    /**
     * Append the second list at the rear of the first list.
     *
     * @param h1  the head of the first list.
     * @param h2  the head of the second list.
     * @param <E> any class.
     * @return the head of the new list.
     */
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
     * The original lists are unchanged.
     *
     * @param h1  head of a list with elements sorted in ascending order.
     * @param h2  head of another list with elements sorted in ascending order.
     * @param <E> any comparable.
     * @return head of the merged list, with elements sorted in ascending order.
     */
    public static <E extends Comparable<E>> ListNode<E> mergeSorted(ListNode<E> h1, ListNode<E> h2) {
        if (h1 == null) return h2;
        if (h2 == null) return h1;
        ListNode<E> i = h1, j = h2, k = null, newHead = null, copy;
        while (i != null || j != null) {
            if (j == null ||
                    i != null && (i.getValue()).compareTo(j.getValue()) <= 0) {
                copy = new ListNode<>(i.getValue(), null);
                i = i.getNext() == h1 ? null : i.getNext();
            } else {
                copy = new ListNode<>(j.getValue(), null);
                j = j.getNext() == h2 ? null : j.getNext();
            }
            if (k == null) newHead = k = copy;
            else k.setNext(k = copy);
        }
        return newHead;
    }

    /**
     * Split the original list by index, arranged in random order. Original list is emptied.
     *
     * @param head the first node in the list.
     * @param <E>  any class.
     * @return a pair of list nodes,
     * the first list are the nodes with even an even index,
     * with the rest nodes in the second list.
     */
    public static <E> ImmutablePair<ListNode<E>, ListNode<E>> splitEvenOdd(ListNode<E> head) {
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
        return new ImmutablePair<>(even, odd);
    }

    /**
     * Prompts the user to enter the code for an operation to perform.
     *
     * @return a char representing an operation.
     */
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

    static class ImmutablePair<U, V> {
        private U first;
        private V second;

        private ImmutablePair(U first, V second) {
            this.first = first;
            this.second = second;
        }

        public U getFirst() {
            return first;
        }

        public V getSecond() {
            return second;
        }
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

        /**
         * A simple notation for identifying this node by memory address.
         *
         * @return the memory address of this.
         */
        @Override
        public String toString() {
            String str = super.toString();
            return str.substring(str.indexOf("@"));
        }
    }
}
