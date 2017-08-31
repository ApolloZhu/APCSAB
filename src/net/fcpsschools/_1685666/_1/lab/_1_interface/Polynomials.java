package net.fcpsschools._1685666._1.lab._1_interface;

/**
 * Single-variable polynomials whose coefficients are of type double
 * and whose exponents are of type int and non-negative.
 */
public interface Polynomials {
    /**
     * The degree of the polynomials.
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
     * Evaluate and returns the value of this polynomials with certain value.
     * <p>
     * Implementation should consider x been positive and negative infinity.
     * Behavior when x is not a number is unspecified.
     *
     * @param x value to plug in.
     * @return the value of this polynomials at given x.
     */
    double evaluatedAt(double x);

    /**
     * Forms a new polynomials by adding this and another together.
     * This and the other is unchanged.
     *
     * @param another the other polynomial to add.
     * @return resulting polynomials by adding this and the given polynomials.
     */
    Polynomials adding(Polynomials another);

    /**
     * Forms a new polynomials by subtracting another polynomials from this.
     * This and the other is unchanged.
     *
     * @param another the other polynomials to add.
     * @return resulting polynomials by subtracting the given polynomials from this.
     */
    Polynomials subtracting(Polynomials another);

    /**
     * This is unchanged.
     *
     * @return
     */
    Polynomials getDerivative();
}
