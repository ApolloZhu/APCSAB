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

package net.fcpsschools._1685666._3.lab._2_hash;

/**
 * @author Apollo/Zhiyu Zhu/朱智语
 */
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
