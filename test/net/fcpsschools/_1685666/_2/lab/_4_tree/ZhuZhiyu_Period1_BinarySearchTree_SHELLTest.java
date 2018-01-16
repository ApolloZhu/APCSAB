package net.fcpsschools._1685666._2.lab._4_tree;

import org.junit.jupiter.api.Test;

import static net.fcpsschools._1685666._2.lab._4_tree.ZhuZhiyu_Period1_BinarySearchTree_SHELL.*;
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

    @Test
    void testFind() {
        String s = "american";
        TreeNode<String> t = treeOf(s);
        TreeFormatter.display(t);
        System.out.print("Inorder: ");
        smallToLarge(t);
        assertFalse(find(t, "x"));

        System.out.println();

        s = "MAENIRAC";
        t = treeOf(s);
        TreeFormatter.display(t);
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