package net.fcpsschools._1685666._3.lab._2_hash;

public class ZhiyuZhu_Period1_LinearHashTable extends ZhiyuZhu_Period1_Hashtable {
    public ZhiyuZhu_Period1_LinearHashTable(int size) {
        super(size);
    }

    @Override
    protected void rehash() {
        ZhiyuZhu_Period1_LinearHashTable newTable = new ZhiyuZhu_Period1_LinearHashTable(size() * 2);
        for (Object obj: table) newTable.add(obj);
        table = newTable.table;
    }

    @Override
    protected void resolve(Object obj, int expectedIndex) {
        table[linearProbe(expectedIndex)] = obj;
    }

    /**
     * @apiNote implement this method recursively
     * @implNote infinite recursion prevented by super class
     */
    private int linearProbe(int index) {
        int nextIndex = (index + 1) % size();
        if (null == table[nextIndex]) return nextIndex;
        return linearProbe(nextIndex);
    }

    @Override
    protected boolean containsResolved(Object obj, int expectedIndex) {
        for (int i = 1; i < size(); i++) {
            int cur = (expectedIndex + i) % size();
            if (null == table[cur]) return false;
            if (table[cur].equals(obj)) return true;
        }
        return false;
    }
}
