package net.fcpsschools._1685666._1.lab._1_interface;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ArrayBasedPoly implements Polynomials {

    public static void main(String[] args) {
        Polynomials polynomial = new ArrayBasedPoly(new double[0]);
        System.out.println(PolynomialsFormatter.format(polynomial));
        System.out.println(polynomial.evaluatedForX(Double.POSITIVE_INFINITY));
    }

    private double[] components = {0};

    /**
     * Constructs a polynomial
     *
     * @param components coefficients in natural order, must include 0s. For 2x^2 - 4, use 2, 0, -4.
     */
    public ArrayBasedPoly(double... components) {
        int length;
        if (components == null || (length = components.length) == 0) return;
        this.components = new double[length];
        for (int i = 0; i < length; i++) {
            double c = components[length - 1 - i];
            if (Double.isNaN(c)) throw new ArithmeticException("Not a number");
            this.components[i] = c;
        }
    }


    @Override
    public int getDegree() {
        return components.length - 1;
    }

    @Override
    public double getCoefficientForExponent(int exponent) {
        int degree = getDegree();
        return exponent < 0 || exponent > degree ? 0 : components[exponent];
    }

    @Override
    public double evaluatedForX(double x) {
        if (getDegree() == 0) return components[0];
        if (Double.isNaN(x)) return Double.NaN;

        INFINITE_CHECK:
        if (Double.isInfinite(x)) {
            double sign = hasSameSign();
            if (sign == 0) throw new IllegalStateException();
            if (Double.isNaN(sign)) return Double.NaN;
            return sign * x;
        }

        throw new NotImplementedException();
    }

    private double hasSameSign() {
        double sign = 0;
        for (double c : components) {
            if (c == 0) continue;
            double indicator = c * Double.POSITIVE_INFINITY;
            if (indicator == Double.POSITIVE_INFINITY)
                if (sign == 0) sign = Double.POSITIVE_INFINITY;
                else if (sign == Double.NEGATIVE_INFINITY) return Double.NaN;
            if (indicator == Double.NEGATIVE_INFINITY)
                if (sign == 0) sign = Double.NEGATIVE_INFINITY;
                else if (sign == Double.POSITIVE_INFINITY) return Double.NaN;
        }
        return sign;
    }

    @Override
    public Polynomials adding(Polynomials another) {
        return null;
    }

    @Override
    public Polynomials subtracting(Polynomials another) {
        return null;
    }

    @Override
    public Polynomials getDerivative() {
        return null;
    }
}
