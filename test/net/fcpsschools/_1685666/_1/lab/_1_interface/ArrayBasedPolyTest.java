package net.fcpsschools._1685666._1.lab._1_interface;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArrayBasedPolyTest {
    // https://github.com/ApolloZhu/APCSA/blob/master/Second_Quarter/Lab/Class/5/QuadraticEquationUnitTest.java
    @Test
    void textQuadraticFormat() {
        cmpUpToQuadratic("0", 0);
        cmpUpToQuadratic("1", 1);
        cmpUpToQuadratic("-1", -1);
        cmpUpToQuadratic("2", 2);
        cmpUpToQuadratic("-3", -3);
        cmpUpToQuadratic("x", 1, 0);
        cmpUpToQuadratic("x + 1", 1, 1);
        cmpUpToQuadratic("x - 1", 1, -1);
        cmpUpToQuadratic("x + 2", 1, 2);
        cmpUpToQuadratic("x - 3", 1, -3);
        cmpUpToQuadratic("-x", -1, 0);
        cmpUpToQuadratic("-x + 1", -1, 1);
        cmpUpToQuadratic("-x - 1", -1, -1);
        cmpUpToQuadratic("-x + 2", -1, 2);
        cmpUpToQuadratic("-x - 3", -1, -3);
        cmpUpToQuadratic("2x", 2, 0);
        cmpUpToQuadratic("2x + 1", 2, 1);
        cmpUpToQuadratic("2x - 1", 2, -1);
        cmpUpToQuadratic("2x + 2", 2, 2);
        cmpUpToQuadratic("2x - 3", 2, -3);
        cmpUpToQuadratic("-3x", -3, 0);
        cmpUpToQuadratic("-3x + 1", -3, 1);
        cmpUpToQuadratic("-3x - 1", -3, -1);
        cmpUpToQuadratic("-3x + 2", -3, 2);
        cmpUpToQuadratic("-3x - 3", -3, -3);
        cmpUpToQuadratic("x^2", 1, 0, 0);
        cmpUpToQuadratic("x^2 + 1", 1, 0, 1);
        cmpUpToQuadratic("x^2 - 1", 1, 0, -1);
        cmpUpToQuadratic("x^2 + 2", 1, 0, 2);
        cmpUpToQuadratic("x^2 - 3", 1, 0, -3);
        cmpUpToQuadratic("x^2 + x", 1, 1, 0);
        cmpUpToQuadratic("x^2 + x + 1", 1, 1, 1);
        cmpUpToQuadratic("x^2 + x - 1", 1, 1, -1);
        cmpUpToQuadratic("x^2 + x + 2", 1, 1, 2);
        cmpUpToQuadratic("x^2 + x - 3", 1, 1, -3);
        cmpUpToQuadratic("x^2 - x", 1, -1, 0);
        cmpUpToQuadratic("x^2 - x + 1", 1, -1, 1);
        cmpUpToQuadratic("x^2 - x - 1", 1, -1, -1);
        cmpUpToQuadratic("x^2 - x + 2", 1, -1, 2);
        cmpUpToQuadratic("x^2 - x - 3", 1, -1, -3);
        cmpUpToQuadratic("x^2 + 2x", 1, 2, 0);
        cmpUpToQuadratic("x^2 + 2x + 1", 1, 2, 1);
        cmpUpToQuadratic("x^2 + 2x - 1", 1, 2, -1);
        cmpUpToQuadratic("x^2 + 2x + 2", 1, 2, 2);
        cmpUpToQuadratic("x^2 + 2x - 3", 1, 2, -3);
        cmpUpToQuadratic("x^2 - 3x", 1, -3, 0);
        cmpUpToQuadratic("x^2 - 3x + 1", 1, -3, 1);
        cmpUpToQuadratic("x^2 - 3x - 1", 1, -3, -1);
        cmpUpToQuadratic("x^2 - 3x + 2", 1, -3, 2);
        cmpUpToQuadratic("x^2 - 3x - 3", 1, -3, -3);
        cmpUpToQuadratic("-x^2", -1, 0, 0);
        cmpUpToQuadratic("-x^2 + 1", -1, 0, 1);
        cmpUpToQuadratic("-x^2 - 1", -1, 0, -1);
        cmpUpToQuadratic("-x^2 + 2", -1, 0, 2);
        cmpUpToQuadratic("-x^2 - 3", -1, 0, -3);
        cmpUpToQuadratic("-x^2 + x", -1, 1, 0);
        cmpUpToQuadratic("-x^2 + x + 1", -1, 1, 1);
        cmpUpToQuadratic("-x^2 + x - 1", -1, 1, -1);
        cmpUpToQuadratic("-x^2 + x + 2", -1, 1, 2);
        cmpUpToQuadratic("-x^2 + x - 3", -1, 1, -3);
        cmpUpToQuadratic("-x^2 - x", -1, -1, 0);
        cmpUpToQuadratic("-x^2 - x + 1", -1, -1, 1);
        cmpUpToQuadratic("-x^2 - x - 1", -1, -1, -1);
        cmpUpToQuadratic("-x^2 - x + 2", -1, -1, 2);
        cmpUpToQuadratic("-x^2 - x - 3", -1, -1, -3);
        cmpUpToQuadratic("-x^2 + 2x", -1, 2, 0);
        cmpUpToQuadratic("-x^2 + 2x + 1", -1, 2, 1);
        cmpUpToQuadratic("-x^2 + 2x - 1", -1, 2, -1);
        cmpUpToQuadratic("-x^2 + 2x + 2", -1, 2, 2);
        cmpUpToQuadratic("-x^2 + 2x - 3", -1, 2, -3);
        cmpUpToQuadratic("-x^2 - 3x", -1, -3, 0);
        cmpUpToQuadratic("-x^2 - 3x + 1", -1, -3, 1);
        cmpUpToQuadratic("-x^2 - 3x - 1", -1, -3, -1);
        cmpUpToQuadratic("-x^2 - 3x + 2", -1, -3, 2);
        cmpUpToQuadratic("-x^2 - 3x - 3", -1, -3, -3);
        cmpUpToQuadratic("2x^2", 2, 0, 0);
        cmpUpToQuadratic("2x^2 + 1", 2, 0, 1);
        cmpUpToQuadratic("2x^2 - 1", 2, 0, -1);
        cmpUpToQuadratic("2x^2 + 2", 2, 0, 2);
        cmpUpToQuadratic("2x^2 - 3", 2, 0, -3);
        cmpUpToQuadratic("2x^2 + x", 2, 1, 0);
        cmpUpToQuadratic("2x^2 + x + 1", 2, 1, 1);
        cmpUpToQuadratic("2x^2 + x - 1", 2, 1, -1);
        cmpUpToQuadratic("2x^2 + x + 2", 2, 1, 2);
        cmpUpToQuadratic("2x^2 + x - 3", 2, 1, -3);
        cmpUpToQuadratic("2x^2 - x", 2, -1, 0);
        cmpUpToQuadratic("2x^2 - x + 1", 2, -1, 1);
        cmpUpToQuadratic("2x^2 - x - 1", 2, -1, -1);
        cmpUpToQuadratic("2x^2 - x + 2", 2, -1, 2);
        cmpUpToQuadratic("2x^2 - x - 3", 2, -1, -3);
        cmpUpToQuadratic("2x^2 + 2x", 2, 2, 0);
        cmpUpToQuadratic("2x^2 + 2x + 1", 2, 2, 1);
        cmpUpToQuadratic("2x^2 + 2x - 1", 2, 2, -1);
        cmpUpToQuadratic("2x^2 + 2x + 2", 2, 2, 2);
        cmpUpToQuadratic("2x^2 + 2x - 3", 2, 2, -3);
        cmpUpToQuadratic("2x^2 - 3x", 2, -3, 0);
        cmpUpToQuadratic("2x^2 - 3x + 1", 2, -3, 1);
        cmpUpToQuadratic("2x^2 - 3x - 1", 2, -3, -1);
        cmpUpToQuadratic("2x^2 - 3x + 2", 2, -3, 2);
        cmpUpToQuadratic("2x^2 - 3x - 3", 2, -3, -3);
        cmpUpToQuadratic("-3x^2", -3, 0, 0);
        cmpUpToQuadratic("-3x^2 + 1", -3, 0, 1);
        cmpUpToQuadratic("-3x^2 - 1", -3, 0, -1);
        cmpUpToQuadratic("-3x^2 + 2", -3, 0, 2);
        cmpUpToQuadratic("-3x^2 - 3", -3, 0, -3);
        cmpUpToQuadratic("-3x^2 + x", -3, 1, 0);
        cmpUpToQuadratic("-3x^2 + x + 1", -3, 1, 1);
        cmpUpToQuadratic("-3x^2 + x - 1", -3, 1, -1);
        cmpUpToQuadratic("-3x^2 + x + 2", -3, 1, 2);
        cmpUpToQuadratic("-3x^2 + x - 3", -3, 1, -3);
        cmpUpToQuadratic("-3x^2 - x", -3, -1, 0);
        cmpUpToQuadratic("-3x^2 - x + 1", -3, -1, 1);
        cmpUpToQuadratic("-3x^2 - x - 1", -3, -1, -1);
        cmpUpToQuadratic("-3x^2 - x + 2", -3, -1, 2);
        cmpUpToQuadratic("-3x^2 - x - 3", -3, -1, -3);
        cmpUpToQuadratic("-3x^2 + 2x", -3, 2, 0);
        cmpUpToQuadratic("-3x^2 + 2x + 1", -3, 2, 1);
        cmpUpToQuadratic("-3x^2 + 2x - 1", -3, 2, -1);
        cmpUpToQuadratic("-3x^2 + 2x + 2", -3, 2, 2);
        cmpUpToQuadratic("-3x^2 + 2x - 3", -3, 2, -3);
        cmpUpToQuadratic("-3x^2 - 3x", -3, -3, 0);
        cmpUpToQuadratic("-3x^2 - 3x + 1", -3, -3, 1);
        cmpUpToQuadratic("-3x^2 - 3x - 1", -3, -3, -1);
        cmpUpToQuadratic("-3x^2 - 3x + 2", -3, -3, 2);
        cmpUpToQuadratic("-3x^2 - 3x - 3", -3, -3, -3);
    }

    private static void cmpUpToQuadratic(String expected, double... coefficients) {
        assertRepresents("f(x) = " + expected.replaceAll("\\^2", "²"),
                new ArrayBasedPoly(coefficients));
    }

    @Test
    void testEmpty() {
        String constantZero = "f(x) = 0";
        assertRepresents(constantZero, new ArrayBasedPoly());
        assertRepresents(constantZero, new ArrayBasedPoly((double[]) null));
        assertRepresents(constantZero, new ArrayBasedPoly(new double[0]));
        assertRepresents(constantZero, new ArrayBasedPoly());
    }

    @Test
    void testWrongDegree() {
        ArrayBasedPoly abp = new ArrayBasedPoly();
        abp.components = new double[]{0, 1, 2, 3, 0, 0};
        Polynomial correct = new ArrayBasedPoly(abp);
        assertEquals(correct, abp);
        assertRepresents("f(x) = 3x³ + 2x² + x", correct);
    }

    private static final ArrayBasedPoly polynomial = new ArrayBasedPoly(-12, 11, 10, -9, 8, 7, 6.5, 0, 4, -3.1, 2);

    @Test
    void testFormatWithDifferentName() {
        AbstractPolynomial ap = new ArrayBasedPoly(polynomial);
        assertEquals(polynomial, ap);

        ap.setName("v");
        ap.setVariableName("t");
        assertRepresents("v = -12t¹⁰ + 11t⁹ + 10t⁸ - 9t⁷ + 8t⁶ + 7t⁵ + 6.5t⁴ + 4t² - 3.1t + 2", ap);
    }

    @Test
    void testEvaluation() {
        assertEquals(-4396.2, polynomial.evaluatedAt(2));
    }

    @Test
    void testDerivative() {
        assertRepresents("f(x) = -120x⁹ + 99x⁸ + 80x⁷ - 63x⁶ + 48x⁵ + 35x⁴ + 26x³ + 8x - 3.1", polynomial.getDerivative());
    }

    @Test
    void testAdding() {
        assertRepresents("f(x) = 11x⁹ + 10x⁸ - 9x⁷ + 8x⁶ + 7x⁵ + 6.5x⁴ + 4x² - 3.1x", polynomial.adding(new ArrayBasedPoly(12, 0, 0, 0, 0, 0, 0, 0, 0, 0, -2)));
        assertRepresents("f(x) = -12x¹⁰ - 109x⁹ + 109x⁸ + 71x⁷ - 55x⁶ + 55x⁵ + 41.5x⁴ + 26x³ + 4x² + 4.9x - 1.1", polynomial.adding(polynomial.getDerivative()));
    }

    @Test
    void testSubtracting() {
        assertRepresents("f(x) = x⁷ + 8x⁶ + 7x⁵ + 6.5x⁴ + 4x² - 3.1x + 2", polynomial.subtracting(new ArrayBasedPoly(-12., 11, 10, -10, 0, 0, 0, 0, 0, 0, 0)));
    }

    private static void assertRepresents(String s, Polynomial p) {
        assertEquals(s, p.toString());
    }
}
