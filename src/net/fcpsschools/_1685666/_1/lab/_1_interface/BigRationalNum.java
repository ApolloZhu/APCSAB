package net.fcpsschools._1685666._1.lab._1_interface;

/* What I learned?
 * > The actual definition of Euclidean Algorithm.
 * > Looked through one of my old programs for recursive implementation.
 * > BigDecimal class has similar features, so I extended Number and implemented Comparable,
 *   and switched to BigInteger as internal representation.
 * > After Mr. Lau showed someone else's approach, I realized BigInteger has it's own gcd implementation.
 */

/* How I feel about this lab?
 * Toooooooo easy. Polynomial lab is more interesting.
 */

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Representing a rational number using a fraction.
 *
 * @author ApolloZhu, Pd. 1
 */
public class BigRationalNum extends Number implements Comparable<BigRationalNum> {
    private BigInteger numerator, denominator;

    /**
     * Constructs a rational integer of value passed in.
     *
     * @param numerator the integer.
     */
    public /*convenience*/ BigRationalNum(long numerator) {
        this(numerator, 1);
    }

    /**
     * Constructs a clone of another rational number.
     *
     * @param another the rational number to replicate.
     */
    public /*convenience*/ BigRationalNum(BigRationalNum another) {
        this(another.numerator, another.denominator);
    }

    /**
     * Constructs a rational number as a fraction.
     *
     * @param numerator   the numerator.
     * @param denominator the denominator.
     * @implSpec this will always be in the lowest ratio.
     */
    public /*convenience*/ BigRationalNum(long numerator, long denominator) {
        this(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
    }

    /**
     * Constructs a rational number as a fraction.
     *
     * @param numerator   the numerator.
     * @param denominator the denominator.
     * @implSpec this will always be in the lowest ratio.
     */
    public BigRationalNum(BigInteger numerator, BigInteger denominator) {
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
        if (denominator.equals(BigInteger.ZERO)) throw new ArithmeticException("/ by zero");
        // Reduce
        // BigInteger gcd = gcd(numerator, denominator);
        BigInteger gcd = numerator.gcd(denominator);
        if (!gcd.equals(BigInteger.ONE)) {
            numerator = numerator.divide(gcd);
            denominator = denominator.divide(gcd);
        }
        // Align
        if (denominator.compareTo(BigInteger.ZERO) < 0) {
            numerator = numerator.negate();
            denominator = denominator.negate();
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
    private static BigInteger gcd(BigInteger a, BigInteger b) {
        return b.equals(BigInteger.ZERO) ? a : gcd(b, a.remainder(b));
    }

    /**
     * Returns a negated version of this, this is unchanged.
     *
     * @return a new rational number which sum with this is zero.
     */
    public BigRationalNum negated() {
        return new BigRationalNum(numerator.negate(), denominator);
    }

    /**
     * Returns a inverted version of this, this is unchanged.
     *
     * @return a new rational number which multiply this will be negative one.
     */
    public BigRationalNum inverted() {
        return new BigRationalNum(denominator, numerator);
    }

    /**
     * Add another to this, another is unchanged.
     *
     * @param another to add.
     */
    public void add(BigRationalNum another) {
        // Keep the original value
        BigInteger thisNumerator = numerator;
        BigInteger thisDenominator = denominator;
        denominator = thisDenominator.multiply(another.denominator);
        numerator = thisNumerator.multiply(another.denominator)
                .add(thisDenominator.multiply(another.numerator));
        fix();
    }

    /**
     * Subtract another from this, another is unchanged.
     *
     * @param another to subtract.
     */
    public void subtract(BigRationalNum another) {
        add(another.negated());
    }

    /**
     * This s multiplied by another, another is unchanged.
     *
     * @param another the multiplier.
     */
    public void multiply(BigRationalNum another) {
        numerator = numerator.multiply(another.numerator);
        denominator = denominator.multiply(another.denominator);
        fix();
    }

    /**
     * This is divided by another.
     *
     * @param another the divisor.
     */
    public void divide(BigRationalNum another) {
        multiply(another.inverted());
    }

    /**
     * Check if this is equal to obj.
     *
     * @param obj the object to compare to.
     * @return true if obj is BigRationalNum and is equal to this,
     * false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BigRationalNum) {
            BigRationalNum that = (BigRationalNum) obj;
            return numerator.equals(that.numerator)
                    && denominator.equals(that.denominator);
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
        if (denominator.equals(BigInteger.ONE)) return "" + numerator;
        else return numerator + "/" + denominator;
    }

    /**
     * Compares this object with the specified object for order.
     *
     * @param o the object to be compared with.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than o.
     * @throws NullPointerException if o is null
     * @throws ClassCastException   if o is not BigRationalNum.
     */
    @Override
    public int compareTo(BigRationalNum o) {
        BigRationalNum copy = new BigRationalNum(this);
        copy.subtract(o);
        return copy.intValue();
    }

    /**
     * Returns the value of this as an int, which may involve rounding or truncation.
     *
     * @return the numeric value represented by this in int.
     */
    @Override
    public int intValue() {
        return (int) longValue();
    }

    /**
     * Returns the value of this as a long, which may involve rounding or truncation.
     *
     * @return the numeric value represented by this in long.
     */
    @Override
    public long longValue() {
        return numerator.divide(denominator).longValue();
    }

    /**
     * Returns the value of this as a float, which may involve rounding.
     *
     * @return the numeric value represented by this in float.
     */
    @Override
    public float floatValue() {
        return (float) doubleValue();
    }

    /**
     * Returns the value of this as a double, which may involve rounding.
     *
     * @return the numeric value represented by this in double.
     */
    @Override
    public double doubleValue() {
        return new BigDecimal(numerator)
                .divide(new BigDecimal(denominator)).doubleValue();
    }
}
