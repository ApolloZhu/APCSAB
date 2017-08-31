package net.fcpsschools._1685666._1.lab._1_interface;

public class ArrayBasedPoly extends AbstractPolynomials {
    public static void main(String[] args) {
        Polynomials polynomial = new ArrayBasedPoly(-12, 11, 10, -9, 8, 7, 6.5, 0, 4, -3.1, 2);
        System.out.println(polynomial);
        System.out.println(polynomial.getDerivative());
        System.out.println(polynomial.evaluatedForX(4));
        System.out.println(new ArrayBasedPoly(0));
    }

    private double[] components = {0};

    /**
     * Constructs an array based polynomial.
     *
     * @param components coefficients in natural order, must include 0s. For 2x^2 - 4, use 2, 0, -4.
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
        return null;
    }

    @Override
    public Polynomials subtracting(Polynomials another) {
        return null;
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
