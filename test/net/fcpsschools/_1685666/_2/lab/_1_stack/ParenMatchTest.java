package net.fcpsschools._1685666._2.lab._1_stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author ApolloZhu, Pd. 1
 */
class ParenMatchTest {
    void assertMatches(String s) {
        assertTrue(ParenMatch.check(s));
    }

    void assertInvalid(String s) {
        assertFalse(ParenMatch.check(s));
    }

    @Test
    void testCheck() {
        assertMatches("5+7");
        assertMatches("(5+7)");
        assertInvalid(")5+7(");
        assertMatches("((5+7)*3)");
        assertMatches("[(5+7)*]3");
        assertMatches("<{5+7}*3>");
        assertMatches("(5+7)*3");
        assertMatches("5+(7*3)");
        assertInvalid("((5+7)*3");
        assertInvalid("[(5+7]*3)");
        assertInvalid("[(5+7)*3])");
        assertInvalid("([(5+7)*3]");
    }
}
