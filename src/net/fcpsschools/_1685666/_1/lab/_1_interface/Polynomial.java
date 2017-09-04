package net.fcpsschools._1685666._1.lab._1_interface;

/**
 * Single-variable polynomial whose coefficients are of type double
 * and whose exponents are of type int and non-negative.
 */
public interface Polynomial {
    /**
     * The degree of the polynomial.
     *
     * @return highest exponent.
     */
    int getDegree();

    /**
     * The coefficient of the term having certain exponent.
     * Behavior when retrieving coefficient for exponent
     * greater than the degree or less than zero is unspecified.
     *
     * @param exponent exponent to get coefficient for.
     * @return coefficient of the given exponent.
     */
    double getCoefficientForExponent(int exponent);

    /**
     * Evaluate and returns the value of this polynomial with certain value.
     * <p>
     * Implementation should consider x been positive and negative infinity.
     * Behavior when x is not a number is unspecified.
     *
     * @param x value to plug in.
     * @return the value of this polynomial at given x.
     */
    double evaluatedAt(double x);

    /**
     * Forms a new polynomial by adding this and another together.
     * This and the other is unchanged.
     *
     * @param another the other polynomial to add.
     * @return resulting polynomial by adding this and the given polynomial.
     */
    Polynomial adding(Polynomial another);

    /**
     * Forms a new polynomial by subtracting another polynomial from this.
     * This and the other is unchanged.
     *
     * @param another the other polynomial to add.
     * @return resulting polynomial by subtracting the given polynomial from this.
     */
    Polynomial subtracting(Polynomial another);

    /**
     * Forms a new polynomial that is the derivative of this polynomial.
     * This is unchanged.
     *
     * @return the derivative of this polynomial.
     */
    Polynomial getDerivative();
}
