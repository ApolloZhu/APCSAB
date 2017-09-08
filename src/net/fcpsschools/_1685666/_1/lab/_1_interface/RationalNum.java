package net.fcpsschools._1685666._1.lab._1_interface;

/**
 * Representing a rational number using a fraction.
 *
 * @author ApolloZhu, Pd. 1
 */
public class RationalNum {
    private int numerator, denominator;

    public RationalNum(int numerator) {
        this(numerator, 1);
    }

    public /*convenience*/ RationalNum(RationalNum another) {
        this(another.numerator, another.denominator);
    }

    public RationalNum(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        fix();
    }

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

    // Using Euclidean Algorithm
    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public RationalNum negated() {
        return new RationalNum(-numerator, denominator);
    }

    public RationalNum inverted() {
        return new RationalNum(denominator, numerator);
    }

    public void add(RationalNum another) {
        // Keep the original value
        int thisNumerator = numerator;
        int thisDenominator = denominator;
        denominator = thisDenominator * another.denominator;
        numerator = thisNumerator * another.denominator
                + thisDenominator * another.numerator;
        fix();
    }

    public void subtract(RationalNum another) {
        add(another.negated());
    }

    public void multiply(RationalNum another) {
        numerator *= another.numerator;
        denominator *= another.denominator;
        fix();
    }

    public void divide(RationalNum another) {
        multiply(another.inverted());
    }

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

    @Override
    public String toString() {
        if (denominator == 1) return "" + numerator;
        else return numerator + "/" + denominator;
    }
}
