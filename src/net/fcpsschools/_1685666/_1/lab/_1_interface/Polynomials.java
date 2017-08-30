package net.fcpsschools._1685666._1.lab._1_interface;

/**
 * Single-variable polynomials whose coefficients are of type double and whose exponents are of type int and non-negative.
 */
public interface Polynomials {
    int getDegree();

    double getCoefficientForExponent(int exponent);

    double evaluatedForX(double x);

    Polynomials adding(Polynomials another);

    Polynomials subtracting(Polynomials another);

    Polynomials getDerivative();
}
