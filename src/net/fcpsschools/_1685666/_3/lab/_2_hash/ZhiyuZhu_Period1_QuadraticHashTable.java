package net.fcpsschools._1685666._3.lab._2_hash;

/**
 * Andrew Li pointed out that quadratic probing requires
 * the table size to be prime and <=50% load capacity.
 */
public class ZhiyuZhu_Period1_QuadraticHashTable extends ZhiyuZhu_Period1_Hashtable {
    protected ZhiyuZhu_Period1_QuadraticHashTable(int size) {
        super(nextPrime(size));
    }

    protected static boolean isPrime(int n) {
        if (2 == n) return true;
        if (2 > n || 0 == n % 2) return false;
        for (int i = 3; i * i < n; i += 2)
            if (0 == n % i)
                return false;
        return true;
    }

    protected static int nextPrime(int n) {
        if (n <= 2) return 2;
        while (n > 0 && !isPrime(n)) n++;
        return n <= 2 ? 2 : n;
    }

    @Override
    public void add(Object obj) {
        if (count * 2 >= size()) rehash();
        super.add(obj);
    }

    @Override
    protected void rehash() {
        int size = nextPrime(size() * 2);
        ZhiyuZhu_Period1_QuadraticHashTable newTable = new ZhiyuZhu_Period1_QuadraticHashTable(size);
        for (Object obj : table) newTable.add(obj);
        table = newTable.table;
    }

    @Override
    protected void resolve(Object obj, int expectedIndex) {
        for (int i = 1; i < size(); i++) {
            int index = (expectedIndex + i * i) % size();
            if (null != table[index]) continue;
            table[index] = obj;
        }
    }

    @Override
    protected boolean containsResolved(Object obj, int expectedIndex) {
        for (int i = 1; i < size(); i++) {
            int index = (expectedIndex + i * i) % size();
            if (null == table[index]) return false;
            if (table[index].equals(obj)) return true;
        }
        return false;
    }
}
