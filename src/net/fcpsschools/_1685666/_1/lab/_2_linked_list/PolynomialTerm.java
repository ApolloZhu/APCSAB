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

package net.fcpsschools._1685666._1.lab._2_linked_list;

import net.fcpsschools._1685666._1.lab._1_interface.AbstractPolynomial;

/**
 * @author Apollo/Zhiyu Zhu/朱智语, Pd. 1
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
