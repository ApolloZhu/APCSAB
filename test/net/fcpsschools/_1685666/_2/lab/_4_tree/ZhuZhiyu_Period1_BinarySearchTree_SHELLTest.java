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

package net.fcpsschools._1685666._2.lab._4_tree;

import org.junit.jupiter.api.Test;

import static net.fcpsschools._1685666._2.lab._4_tree.ZhuZhiyu_Period1_BinarySearchTree_SHELL.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Apollo/Zhiyu Zhu/朱智语
 * PERIOD: 1
 * DUE DATE: 1/15 23:59
 * <p>
 * PURPOSE:
 * - Test ZhuZhiyu_Period1_BinarySearchTree_SHELL.
 */
class ZhuZhiyu_Period1_BinarySearchTree_SHELLTest {

    private TreeFormatter formatter = new TreeFormatter();

    @Test
    void testFind() {
        String s = "american";
        TreeNode<String> t = treeOf(s);
        formatter.display(t);
        System.out.print("Inorder: ");
        smallToLarge(t);
        assertFalse(find(t, "x"));

        System.out.println();

        s = "MAENIRAC";
        t = treeOf(s);
        formatter.display(t);
        System.out.print("Inorder: ");
        smallToLarge(t);
        assertTrue(find(t, "I"));
    }

    @Test
    void testMin() {
        String s = "american";
        TreeNode<String> t = treeOf(s);
        assertEquals("a", min(t));

        s = "MAENIRAC";
        t = treeOf(s);
        assertEquals("A", min(t));
    }

    @Test
    void testMax() {
        String s = "american";
        TreeNode<String> t = treeOf(s);
        assertEquals("r", max(t));

        s = "MAENIRAC";
        t = treeOf(s);
        assertEquals("R", max(t));
    }

    private <E> void assertTreeEquals(TreeNode<E> expected, TreeNode<E> actual) {
        if (expected == actual) return;
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected.getValue(), actual.getValue());
        assertTreeEquals(expected.getLeft(), actual.getLeft());
        assertTreeEquals(expected.getRight(), actual.getRight());
    }

    private void testDelete(String expected, String original, String v) {
        TreeNode<String> originalTree = treeOf(original);
        TreeNode<String> expectedTree = treeOf(expected);
        originalTree = delete(originalTree, v);
        assertTreeEquals(expectedTree, originalTree);
    }

    private void deleteN(String expected, String original) {
        testDelete(expected, original, "N");
    }

    @Test
    void delete_1() {
        deleteN("ECSBPWAR", "ECSBPWANR");
        testDelete("HDJAGKFOELTMSN", "HDJAGKFOELTMSUN", "U");
        testDelete("HDJAGKFOELTMUN", "HDJAGKFOELTMSUN", "S");

    }

    @Test
    void delete_2a() {
        deleteN("SPTOR", "SNTPOR");
        testDelete("HDJAGOFLTEMSUN", "HDJAGKFOELTMSUN", "K");
    }


    @Test
    void delete_2b() {
        deleteN("HBRJVIKSZ", "HBRNVJSZIK");
        testDelete("HDJAFKEOLTMSUN", "HDJAGKFOELTMSUN", "G");
    }

    @Test
    void delete_3ai() {
        deleteN("DBMACFSEJH", "DBNACFSEJHM");
        testDelete("HDJAGKFNELTMSU", "HDJAGKFOELTMSUN", "O");
    }

    @Test
    void delete_3aii() {
        deleteN("KFSAGPXQ", "NFSAKPXGQ");
        testDelete("GDJAFKEOLTMSUN", "HDJAGKFOELTMSUN", "H");
    }

    @Test
    void delete_3b() {
        deleteN("RGVCPAE", "RNVGPCAE");
        testDelete("HAJGKFOELTMSUN", "HDJAGKFOELTMSUN", "D");
    }

    @Test
    void testToPrefix() {
        assertEquals("DBACFEGHI", toPrefix("ABCDEFGHI", "ACBEIHGFD"));
    }
}

/*
 ┌──a───────────┐
 a        ┌─────m─────┐
       ┌──e──┐     ┌──r
       c     i     n
Inorder: a a c e i m n r
    ┌───────────M──┐
 ┌──A─────┐        N──┐
 A     ┌──E──┐        R
       C     I
Inorder: A A C E I M N R
*/