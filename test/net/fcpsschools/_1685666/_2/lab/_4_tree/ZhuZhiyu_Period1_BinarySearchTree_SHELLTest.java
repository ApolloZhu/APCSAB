package net.fcpsschools._1685666._2.lab._4_tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * NAME: Zhiyu Zhu
 * PERIOD: 1
 * DUE DATE: 1/15 23:59
 * <p>
 * PURPOSE:
 * - Test ZhuZhiyu_Period1_BinarySearchTree_SHELL.
 */
class ZhuZhiyu_Period1_BinarySearchTree_SHELLTest {
    TreeNode<String> treeOf(String s) {
        TreeNode<String> t = null;
        for (char c : s.toCharArray())
            t = ZhuZhiyu_Period1_BinarySearchTree_SHELL
                    .insert(t, String.valueOf(c));
        return t;
    }

    @Test
    void find() {
        String s = "american";
        TreeNode<String> t = treeOf(s);
        TreeFormatter.display(t);
        System.out.print("Inorder: ");
        ZhuZhiyu_Period1_BinarySearchTree_SHELL.smallToLarge(t);
        assertFalse(ZhuZhiyu_Period1_BinarySearchTree_SHELL.find(t, "x"));

        System.out.println();

        s = "MAENIRAC";
        t = treeOf(s);
        TreeFormatter.display(t);
        System.out.print("Inorder: ");
        ZhuZhiyu_Period1_BinarySearchTree_SHELL.smallToLarge(t);
        assertTrue(ZhuZhiyu_Period1_BinarySearchTree_SHELL.find(t, "I"));
    }

    @Test
    void min() {
        String s = "american";
        TreeNode<String> t = treeOf(s);
        assertEquals("a", ZhuZhiyu_Period1_BinarySearchTree_SHELL.min(t));

        s = "MAENIRAC";
        t = treeOf(s);
        assertEquals("A", ZhuZhiyu_Period1_BinarySearchTree_SHELL.min(t));
    }

    @Test
    void max() {
        String s = "american";
        TreeNode<String> t = treeOf(s);
        assertEquals("r", ZhuZhiyu_Period1_BinarySearchTree_SHELL.max(t));

        s = "MAENIRAC";
        t = treeOf(s);
        assertEquals("R", ZhuZhiyu_Period1_BinarySearchTree_SHELL.max(t));
    }

    <E> void assertTreeEquals(TreeNode<E> expected, TreeNode<E> actual) {
        if (expected == actual) return;
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected.getValue(), actual.getValue());
        assertTreeEquals(expected.getLeft(), actual.getLeft());
        assertTreeEquals(expected.getRight(), actual.getRight());
    }

    void delete(String expected, String original, String v) {
        TreeNode<String> originalTree = treeOf(original);
        TreeNode<String> expectedTree = treeOf(expected);
        originalTree = ZhuZhiyu_Period1_BinarySearchTree_SHELL.delete(originalTree, v);
        assertTreeEquals(expectedTree, originalTree);
    }

    void deleteN(String expected, String original) {
        delete(expected, original, "N");
    }

    @Test
    void delete_1() {
        deleteN("ECSBPWAR", "ECSBPWANR");
        delete("HDJAGKFOELTMSN", "HDJAGKFOELTMSUN", "U");
        delete("HDJAGKFOELTMUN", "HDJAGKFOELTMSUN", "S");

    }

    @Test
    void delete_2a() {
        deleteN("SPTOR", "SNTPOR");
        delete("HDJAGOFLTEMSUN", "HDJAGKFOELTMSUN", "K");
    }


    @Test
    void delete_2b() {
        deleteN("HBRJVIKSZ", "HBRNVJSZIK");
        delete("HDJAFKEOLTMSUN", "HDJAGKFOELTMSUN", "G");
    }

    @Test
    void delete_3ai() {
        deleteN("DBMACFSEJH", "DBNACFSEJHM");
        delete("HDJAGKFNELTMSU", "HDJAGKFOELTMSUN", "O");
    }

    @Test
    void delete_3aii() {
        deleteN("KFSAGPXQ", "NFSAKPXGQ");
        delete("GDJAFKEOLTMSUN", "HDJAGKFOELTMSUN", "H");
    }

    @Test
    void delete_3b() {
        deleteN("RGVCPAE", "RNVGPCAE");
        delete("HAJGKFOELTMSUN", "HDJAGKFOELTMSUN", "D");
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