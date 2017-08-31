package net.fcpsschools._1685666._1.lab._1_interface;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;


class RationalNumTest {
    @Test
    void testIntToString() {
        assertEquals("-2", new RationalNum(-2).toString());
    }

    @Test
    void testRationalToString() {
        assertEquals("1/3", new RationalNum(1, 3).toString());
    }

    @Test
    void testSimplify() {
        assertEquals("2/3", new RationalNum(4, 6).toString());

    }

    @Test
    void testSimplifyNegative() {
        assertEquals("-5/6", new RationalNum(-10, 12).toString());
    }

    @Test
    void testSignFix() {
        assertEquals("-5/6", new RationalNum(10, -12).toString());
    }

    @Test
    void testNegated() {
        assertEquals("1/2", new RationalNum(1, -2).negated().toString());
    }

    @Test
    void testInverted() {
        assertEquals("1/2", new RationalNum(2).inverted().toString());
    }

    @Test
    void testEquals() {
        assertTrue(new RationalNum(-2, 4).negated().equals(new RationalNum(2, 1).inverted()));
    }

    @Test
    void testOperation() {
        RationalNum negativeHalf = new RationalNum(2, -4);
        RationalNum two = new RationalNum(2);

        negativeHalf.add(two);
        assertEquals("3/2", negativeHalf.toString());
        two.subtract(negativeHalf);
        assertEquals("1/2", two.toString());
        negativeHalf.multiply(two);
        assertEquals("3/4", negativeHalf.toString());
        two.divide(negativeHalf);
        assertEquals("2/3", two.toString());
    }

    @Test
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> new RationalNum(1, 0));
    }
}