package net.fcpsschools._1685666._1.lab._2_linked_list;

import net.fcpsschools._1685666._1.lab._1_interface.AbstractPolynomial;

/**
 * @author ApolloZhu, Pd. 1
 */
public final class PolynomialTerm {
    public static final PolynomialTerm ZERO = new PolynomialTerm(0, 0);
    private int degree;
    private double coefficient;

    public PolynomialTerm(int degree, double coefficient) {
        this.degree = degree;
        this.coefficient = coefficient;
    }

    public int getDegree() {
        return degree;
    }

    public double getCoefficient() {
        return coefficient;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PolynomialTerm) {
            PolynomialTerm that = (PolynomialTerm) obj;
            return getDegree() == that.getDegree()
                    && getCoefficient() == that.getCoefficient();
        } else {
            return super.equals(obj);
        }
    }

    @Override
    public String toString() {
        return coefficient < 0 ? "-" : ""
                + AbstractPolynomial.makeSimpleCoefficient(coefficient, degree)
                + (degree == 0 ? "" : "x" + AbstractPolynomial.makePrettyExponent(degree));
    }
}
