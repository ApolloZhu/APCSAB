package net.fcpsschools._1685666._3.lab._2_hash;

public abstract class Hashtable {
    protected Object[] table;
    protected int count;

    protected Hashtable(int size) {
        this.table = new Object[size];
    }

    public static Hashtable init(int numSlots, boolean useProbing) {
        if (useProbing) return new LinearHashTable(numSlots);
        else return new LinkedHashTable(numSlots);
    }

    public static Hashtable init(int numSlots, CollisionResolutionMethod method) {
        switch (method) {
            case LINEAR_PROBING:
                return new LinearHashTable(numSlots);
            case QUADRATIC_PROBING:
                return new QuadraticHashTable(numSlots);
            case CHAINING:
                return new LinkedHashTable(numSlots);
        }
        throw new EnumConstantNotPresentException(
                CollisionResolutionMethod.class, method.name()
        );
    }

    public int size() {
        return table.length;
    }

    public double loadFactor() {
        return count / size();
    }

    protected int expectedIndexOf(Object obj) {
        int hash = Math.abs(obj.hashCode());
        return hash % size();
    }

    public void add(Object obj) {
        if (null == obj) return;
        if (count >= size()) rehash();
        int index = expectedIndexOf(obj);
        if (null == table[index]) {
            table[index] = obj;
        } else resolve(obj, index);
        count++;
    }

    protected abstract void rehash();

    protected abstract void resolve(Object obj, int expectedIndex);

    /**
     * @return true if obj can be found in the table
     */
    public boolean contains(Object obj) {
        if (null == obj || 0 == count) return false;
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
        while (i < size() - 1) {
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
