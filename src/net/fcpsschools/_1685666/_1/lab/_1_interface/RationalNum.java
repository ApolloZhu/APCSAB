package net.fcpsschools._1685666._1.lab._1_interface;

/* What I learned?
 * > The actual definition of Euclidean Algorithm.
 * > Looked through one of my old programs for recursive implementation.
 * > BigDecimal class has similar features.
 */

/* How I feel about this lab?
 * Toooooooo easy.
 */

/**
 * Representing a rational number using a fraction.
 *
 * @author ApolloZhu, Pd. 1
 */
public class RationalNum {
    private int numerator, denominator;

    /**
     * Constructs a rational integer of value passed in.
     *
     * @param numerator the integer.
     */
    public RationalNum(int numerator) {
        this(numerator, 1);
    }

    /**
     * Constructs a clone of another rational number.
     *
     * @param another the rational number to replicate.
     */
    public /*convenience*/ RationalNum(RationalNum another) {
        this(another.numerator, another.denominator);
    }

    /**
     * Constructs a rational number as a fraction.
     *
     * @param numerator   the numerator.
     * @param denominator the denominator.
     * @implSpec this will always be in the lowest ratio.
     */
    public RationalNum(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        fix();
    }

    /**
     * Make sure
     * 1. the denominator is not zero
     * 2. this is in the lowest ratio
     * 3. only numerator can be negative
     *
     * @throws ArithmeticException if denominator is zero.
     */
    private void fix() {
        // Validate
        if (denominator == 0) throw new ArithmeticException("/ by zero");
        // Reduce
        int gcd = gcd(numerator, denominator);
        if (gcd != 1) {
            numerator /= gcd;
            denominator /= gcd;
        }
        // Align
        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }
    }

    /**
     * Recursively finds the greatest common divisor
     * using the Euclidean Algorithm.
     *
     * @param a the divisor if b is 0, otherwise the previous b.
     * @param b reduced to zero, otherwise is a mod b.
     * @return the greatest common divisor for a and b.
     * @implSpec Euclidean Algorithm is defined as follows:
     * 1. if a or b is 0, then gcd(a,b) is b or a, stop;
     * 2. since a = bk + m, gcd(a, b) = gcd(b, m).
     */
    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    /**
     * Returns a negated version of this, this is unchanged.
     *
     * @return a new rational number which sum with this is zero.
     */
    public RationalNum negated() {
        return new RationalNum(-numerator, denominator);
    }

    /**
     * Returns a inverted version of this, this is unchanged.
     *
     * @return a new rational number which multiply this will be negative one.
     */
    public RationalNum inverted() {
        return new RationalNum(denominator, numerator);
    }

    /**
     * Add another to this, another is unchanged.
     *
     * @param another to add.
     */
    public void add(RationalNum another) {
        // Keep the original value
        int thisNumerator = numerator;
        int thisDenominator = denominator;
        denominator = thisDenominator * another.denominator;
        numerator = thisNumerator * another.denominator
                + thisDenominator * another.numerator;
        fix();
    }

    /**
     * Subtract another from this, another is unchanged.
     *
     * @param another to subtract.
     */
    public void subtract(RationalNum another) {
        add(another.negated());
    }

    /**
     * This s multiplied by another, another is unchanged.
     *
     * @param another the multiplier.
     */
    public void multiply(RationalNum another) {
        numerator *= another.numerator;
        denominator *= another.denominator;
        fix();
    }

    /**
     * This is divided by another.
     *
     * @param another the divisor.
     */
    public void divide(RationalNum another) {
        multiply(another.inverted());
    }

    /**
     * Check if this is equal to obj.
     *
     * @param obj the object to compare to.
     * @return true if obj is RationalNum and is equal to this,
     * false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RationalNum) {
            RationalNum that = (RationalNum) obj;
            return numerator == that.numerator
                    && denominator == that.denominator;
        } else {
            return super.equals(obj);
        }
    }

    /**
     * Human friendly representation of this rational number.
     *
     * @return an integer if denominator is 1,
     * otherwise a fraction.
     */
    @Override
    public String toString() {
        if (denominator == 1) return "" + numerator;
        else return numerator + "/" + denominator;
    }
}
