package net.fcpsschools._1685666._3.lab._2_hash;

/*****************************************************************************
 Assignment: Notice that collisions occur. You are to implement
 three different collision schemes, namely, linear probing,
 rehashing, and chaining (using a LinkedList <Object>).

 Make sure you find a way to prove that your program works for linear probing, separate chaining. Show me enough cases to convince me that. Follow file naming convention when you are ready to send me the file.

 For separate chaining,if you want to use a table of LinkedList objects, it is fine with me.
 *****************************************************************************/
public abstract class Hashtable {
    protected Object[] table;

    protected Hashtable(int size) {
        this.table = new Object[size];
    }

    public static Hashtable init(int numSlots, boolean useProbing) {
        if (useProbing) return new LinearHashTable(numSlots);
        else return new LinkedHashTable(numSlots);
    }

    /**
     * Rehash
     */
    protected int expectedIndexOf(Object obj) {
        int hash = Math.abs(obj.hashCode());
        return hash % table.length;
    }

    public void add(Object obj) {
        if (null == obj) return;
        int index = expectedIndexOf(obj);
        if (null == table[index]) {
            table[index] = obj;
        } else resolve(obj, index);
    }

    protected abstract void resolve(Object obj, int expectedIndex);

    /**
     * @return true if obj can be found in the table
     */
    public boolean contains(Object obj) {
        if (null == obj) return false;
        int index = expectedIndexOf(obj);
        if (null == table[index]) return false;
        return obj.equals(table[index]) ||
                containsResolved(obj, index);
    }

    /**
     * Implement collision resolution methods,
     * one at a time.
     */
    protected abstract boolean containsResolved(Object obj, int expectedIndex);

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        int i = 0;
        while (i < table.length - 1) {
            sb.append(table[i++]);
            sb.append(", ");
        }
        sb.append(table[i]);
        sb.append(']');
        return sb.toString();
    }

    public enum CollisionResolutionMethod {
        LINEAR_PROBING, QUADRATIC_PROBING, CHAINING
    }
}
