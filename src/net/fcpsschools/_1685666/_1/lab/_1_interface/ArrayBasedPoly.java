package net.fcpsschools._1685666._1.lab._1_interface;

import java.util.function.DoubleBinaryOperator;

public class ArrayBasedPoly extends AbstractPolynomial {
    /**
     * Indexed by exponent.
     */
    protected double[] components = {0};

    public /*convenience*/ ArrayBasedPoly(String name, String variableName, double... components) {
        this(name, components);
        setVariableName(variableName);
    }

    public /*convenience*/ ArrayBasedPoly(String name, double... components) {
        this(components);
        setName(name);
    }

    /**
     * Constructs an array based polynomial using coefficients in natural order.
     *
     * @param components coefficients in natural order, must include 0s.
     *                   For example, to construct 2x² - 4, use 2, 0, -4.
     * @throws IllegalArgumentException if any of the coefficients is not a number.
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

    public ArrayBasedPoly(Polynomial polynomial) {
        if (polynomial == null) return;
        int degree = polynomial.getDegree(), offset = 0;
        while (polynomial.getCoefficientForExponent(degree) == 0) degree--;
        components = new double[degree + 1];
        for (; degree >= 0; degree--)
            components[degree] = polynomial.getCoefficientForExponent(degree);
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
    public Polynomial adding(Polynomial another) {
        return linearlyMergedUsing((self, other) -> self + other, another);
    }

    @Override
    public Polynomial subtracting(Polynomial another) {
        return linearlyMergedUsing((self, other) -> self - other, another);
    }

    /**
     * Form a new polynomial by operating on each like term of self and another polynomial.
     * This and the other is not changed.
     *
     * @param operator how to combine each term of this with each term of the `another` polynomial.
     * @param another  the other polynomial involved in the computation.
     * @return the resulting polynomial.
     */
    protected Polynomial linearlyMergedUsing(DoubleBinaryOperator operator, /*with*/ Polynomial another) {
        int thisDegree = getDegree();
        int thatDegree = another.getDegree();
        while (another.getCoefficientForExponent(thatDegree) == 0) thatDegree--;

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
    public Polynomial getDerivative() {
        int degree = getDegree();
        double[] coefficientsOfDerivative = new double[degree];
        for (int i = 1; i <= degree; i++)
            coefficientsOfDerivative[i - 1] = getCoefficientForExponent(i) * i;

        ArrayBasedPoly derivative = new ArrayBasedPoly();
        derivative.components = coefficientsOfDerivative;
        return derivative;
    }
}
