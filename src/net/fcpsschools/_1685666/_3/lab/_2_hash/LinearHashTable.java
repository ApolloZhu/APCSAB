package net.fcpsschools._1685666._3.lab._2_hash;

public class LinearHashTable extends Hashtable {
    public LinearHashTable(int size) {
        super(size);
    }

    @Override
    protected void resolve(Object obj, int expectedIndex) {
        int resolvedIndex = linearProbe(expectedIndex);
        if (resolvedIndex < 0) return;
        table[resolvedIndex] = obj;
    }

    /**
     * FIXME: Infinite recursion
     *
     * @apiNote implement this method recursively
     */
    private int linearProbe(int index) {
        int nextIndex = (index + 1) % table.length;
        if (null == table[nextIndex]) return nextIndex;
        return linearProbe(nextIndex) - 1;
    }

    @Override
    protected boolean containsResolved(Object obj, int expectedIndex) {
        for (int i = 1; i < table.length; i++) {
            int cur = (expectedIndex + i) % table.length;
            if (null == table[cur]) return false;
            if (table[cur].equals(obj)) return true;
        }
        return false;
    }
}
