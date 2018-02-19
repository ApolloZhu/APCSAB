package net.fcpsschools._1685666._3.lab._2_hash;


public class LinkedHashTable extends Hashtable {
    public LinkedHashTable(int size) {
        super(size);
    }

    @Override
    protected void resolve(Object obj, int expectedIndex) {
        if (table[expectedIndex] instanceof ListNode) {
            table[expectedIndex] = new ListNode(obj, (ListNode) table[expectedIndex]);
        } else {
            ListNode previous = new ListNode(table[expectedIndex], null);
            table[expectedIndex] = new ListNode(obj, previous);
        }
    }

    @Override
    public boolean containsResolved(Object obj, int expectedIndex) {
        if (table[expectedIndex] instanceof ListNode) {
            ListNode node = (ListNode) table[expectedIndex];
            do {
                if (obj.equals(node.value))
                    return true;
            } while (null != (node = node.next));
            return false;
        } else return false;
    }

    private static class ListNode {
        private final Object value;
        private final ListNode next;

        private ListNode(Object initValue, ListNode initNext) {
            value = initValue;
            next = initNext;
        }

        @Override
        public String toString() {
            String rest = null == next ? ""
                    : "~>" + next.toString();
            return String.valueOf(value) + rest;
        }
    }
}
