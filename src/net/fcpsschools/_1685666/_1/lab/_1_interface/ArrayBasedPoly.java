/*
MIT License

Copyright (c) 2017-2019 Apollo/Zhiyu Zhu/朱智语 <public-apollonian@outlook.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package net.fcpsschools._1685666._1.lab._1_interface;

import java.util.function.DoubleBinaryOperator;

/**
 * An implementation of polynomial using array.
 *
 * @apiNote  I introduced variadic parameter to classmates
 * @implNote Practiced using functional protocol DoubleBinaryOperator
 *
 * @author Apollo/Zhiyu Zhu/朱智语, Pd. 1
 */
public class ArrayBasedPoly extends AbstractPolynomial {
    /**
     * Coefficients indexed by exponent.
     */
    protected double[] coefficients = {0};

    /**
     * @see ArrayBasedPoly#ArrayBasedPoly(String, String, double...)
     * @see AbstractPolynomial#DEFAULT_NAME
     */
    public /*convenience*/ ArrayBasedPoly(double... coefficients) {
        this(DEFAULT_NAME, coefficients);
    }

    /**
     * @see #ArrayBasedPoly(String, String, double...)
     * @see AbstractPolynomial#DEFAULT_VARIABLE_NAME
     */
    public /*convenience*/ ArrayBasedPoly(String name, double... coefficients) {
        this(name, DEFAULT_VARIABLE_NAME, coefficients);
        setName(name);
    }

    /**
     * Constructs and returns a single term ArrayBasedPoly.
     *
     * @param coefficient coefficient of the given term.
     * @param exponent    exponent of that term,
     *                    which is also the degree of the polynomial.
     * @return a single term ArrayBasedPoly `coefficient`x^`exponent`.
     * @throws IllegalArgumentException if exponent is negative.
     * @see #ArrayBasedPoly(double... coefficients)
     */
    public static ArrayBasedPoly makeSingleTerm(double coefficient, int exponent) {
        if (exponent < 0) throw new IllegalArgumentException("Negative exponent is not supported");
        double[] coefficients = new double[exponent + 1];
        coefficients[0] = coefficient;
        return new ArrayBasedPoly(coefficients);
    }

    /**
     * Constructs an array based polynomial using coefficients in natural order.
     *
     * @param coefficients coefficients in natural order, must include 0s.
     *                     For example, to construct 2x² - 4, use 2, 0, -4.
     * @throws IllegalArgumentException if any of the coefficients is not a number.
     * @see AbstractPolynomial#AbstractPolynomial(String, String)
     */
    public ArrayBasedPoly(String name, String variableName, double... coefficients) {
        super(name, variableName);
        // Empty polynomial treated as f(x) = 0
        if (coefficients == null || coefficients.length == 0) return;
        // Constant polynomial
        if (coefficients.length == 1) {
            this.coefficients[0] = coefficients[0];
            return;
        }
        // Find the actual degree
        int offset = 0;
        while (coefficients[offset] == 0) offset++;
        this.coefficients = new double[coefficients.length - offset];
        // Assign back of the reversely ordered coefficients to the front
        for (int i = 0; i < this.coefficients.length; i++) {
            double c = coefficients[coefficients.length - 1 - i];
            if (Double.isNaN(c))
                throw new IllegalArgumentException("Coefficient of exponent " + i + " is not a number");
            this.coefficients[i] = c;
        }
    }

    /**
     * Being a clone of another polynomial.
     *
     * @param polynomial polynomial to replicate.
     */
    public ArrayBasedPoly(Polynomial polynomial) {
        // Validate
        if (polynomial == null) return;
        int degree = polynomial.getDegree();
        while (polynomial.getCoefficientForExponent(degree) == 0) degree--;
        // Copy attributes
        coefficients = new double[degree + 1];
        for (; degree >= 0; degree--)
            coefficients[degree] = polynomial.getCoefficientForExponent(degree);
        if (polynomial instanceof AbstractPolynomial) {
            AbstractPolynomial ap = (AbstractPolynomial) polynomial;
            setName(ap.getName());
            setVariableName(ap.getVariableName());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDegree() {
        return coefficients.length - 1;
    }

    /**
     * {@inheritDoc}
     *
     * @implSpec coefficient for exponent
     * <ol>
     * <li>greater than the degree</li>
     * <li>less than zero</li>
     * </ol>
     * will be zero.
     */
    @Override
    public double getCoefficientForExponent(int exponent) {
        try {
            return coefficients[exponent];
        } catch (Exception ignored) {
            // Most likely ArrayIndexOutOfRangeException
            // Meaning this does not contain that degree
            // Thus the coefficient is definitely a zero
            return 0;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Polynomial adding(Polynomial another) {
        return linearlyMergedUsing((self, other) -> self + other, another);
    }

    /**
     * {@inheritDoc}
     */
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
        // Validate
        int thisDegree = getDegree();
        int thatDegree = another.getDegree();
        while (another.getCoefficientForExponent(thatDegree) == 0) thatDegree--;
        // Allocate according to the highest degree
        int maxDegree = Math.max(thisDegree, thatDegree);
        double[] resultCoefficientsInNaturalOrder = new double[maxDegree + 1];
        // Apply operator on each term
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

    /**
     * {@inheritDoc}
     *
     * @implSpec Name and variable name is not derived to derivative.
     */
    @Override
    public Polynomial getDerivative() {
        int degree = getDegree();
        double[] coefficientsOfDerivative = new double[degree];
        for (int i = 1; i <= degree; i++)
            coefficientsOfDerivative[i - 1] = getCoefficientForExponent(i) * i;

        ArrayBasedPoly derivative = new ArrayBasedPoly();
        derivative.coefficients = coefficientsOfDerivative;
        return derivative;
    }
}
