package net.fcpsschools._1685666._1.lab._1_interface;

import java.util.function.DoubleBinaryOperator;

public class ArrayBasedPoly extends AbstractPolynomials {
    public static void main(String[] args) {
        // f(x) = 0
        System.out.println(new ArrayBasedPoly(0));

        Polynomials polynomial = new ArrayBasedPoly(-12, 11, 10, -9, 8, 7, 6.5, 0, 4, -3.1, 2);
        ((ArrayBasedPoly) polynomial).setName("v");
        ((ArrayBasedPoly) polynomial).setVariableName("t");

        // v = -12t¹⁰ + 11t⁹ + 10t⁸ - 9t⁷ + 8t⁶ + 7t⁵ + 6.5t⁴ + 4t² - 3.1t + 2
        System.out.println(polynomial);

        // v(4) = -4396.2
        System.out.println("v(4) = " + polynomial.evaluatedAt(2));

        // f(x) = -120x⁹ + 99x⁸ + 80x⁷ - 63x⁶ + 48x⁵ + 35x⁴ + 26x³ + 8x - 3.1
        System.out.println(polynomial.getDerivative());

        // f(x) = -12x¹⁰ - 109x⁹ + 109x⁸ + 71x⁷ - 55x⁶ + 55x⁵ + 41.5x⁴ + 26x³ + 4x² + 4.9x - 1.1
        System.out.println(polynomial.adding(polynomial.getDerivative()));

        // f(x) = 11x⁹ + 10x⁸ - 9x⁷ + 8x⁶ + 7x⁵ + 6.5x⁴ + 4x² - 3.1x
        System.out.println(polynomial.adding(new ArrayBasedPoly(12, 0, 0, 0, 0, 0, 0, 0, 0, 0, -2)));
    }

    /**
     * Indexed by exponent.
     */
    private double[] components = {0};

    /**
     * Constructs an array based polynomials.
     *
     * @param components coefficients in natural order, must include 0s.
     *                   For example, to construct 2x² - 4, use 2, 0, -4.
     * @throws IllegalArgumentException if any of the coefficient is not a number.
     */
    public ArrayBasedPoly(double... components) {
        if (components == null || components.length == 0) return;
        if (components.length == 1) {
            this.components[0] = components[0];
            return;
        }

        int offset = 0;
        while (components[offset] == 0) offset++;
        this.components = new double[components.length - offset];
        for (int i = 0; i < this.components.length; i++) {
            double c = components[components.length - 1 - i];
            if (Double.isNaN(c))
                throw new IllegalArgumentException("Coefficient of exponent " + i + " is not a number");
            this.components[i] = c;
        }
    }

    @Override
    public int getDegree() {
        return components.length - 1;
    }

    @Override
    public double getCoefficientForExponent(int exponent) {
        try {
            return components[exponent];
        } catch (Exception ignored) {
            return 0;
        }
    }

    @Override
    public Polynomials adding(Polynomials another) {
        return linearlyMergedUsing((self, other) -> self + other, another);
    }

    @Override
    public Polynomials subtracting(Polynomials another) {
        return linearlyMergedUsing((self, other) -> self - other, another);
    }

    /**
     * Form a new polynomials by operating on each like term of self and another polynomials.
     * This and the other is not changed.
     *
     * @param operator how to combine each term of this with each term of the `another` polynomials.
     * @param another  the other polynomials involved in the computation.
     * @return the resulting polynomials.
     */
    protected Polynomials linearlyMergedUsing(DoubleBinaryOperator operator, /*with*/ Polynomials another) {
        int thisDegree = getDegree();
        int thatDegree = another.getDegree();
        int maxDegree = Math.max(thisDegree, thatDegree);
        double[] resultCoefficientsInNaturalOrder = new double[maxDegree + 1];
        for (int exponent = maxDegree, i = 0; exponent >= 0; exponent--, i++) {
            if (exponent <= thisDegree)
                resultCoefficientsInNaturalOrder[i] += getCoefficientForExponent(exponent);
            if (exponent <= thatDegree)
                resultCoefficientsInNaturalOrder[i] = operator.applyAsDouble(
                        resultCoefficientsInNaturalOrder[i],
                        another.getCoefficientForExponent(exponent)
                );
        }
        return new ArrayBasedPoly(resultCoefficientsInNaturalOrder);
    }

    @Override
    public Polynomials getDerivative() {
        int degree = getDegree();
        double[] coefficientsOfDerivative = new double[degree];
        for (int i = 1; i <= degree; i++)
            coefficientsOfDerivative[i - 1] = getCoefficientForExponent(i) * i;

        ArrayBasedPoly derivative = new ArrayBasedPoly();
        derivative.components = coefficientsOfDerivative;
        return derivative;
    }
}
