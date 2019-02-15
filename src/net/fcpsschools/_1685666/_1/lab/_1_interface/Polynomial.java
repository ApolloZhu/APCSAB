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

/**
 * Single-variable polynomial whose coefficients are of type double
 * and whose exponents are of type int and non-negative.
 *
 * @author Apollo/Zhiyu Zhu/朱智语, Pd. 1
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
     * This and the other polynomial are unchanged.
     *
     * @param another the other polynomial to add.
     * @return resulting polynomial by adding this and the given polynomial.
     */
    Polynomial adding(Polynomial another);

    /**
     * Forms a new polynomial by subtracting another polynomial from this.
     * This and the other polynomial are unchanged.
     *
     * @param another the other polynomial to subtract.
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

    /**
     * Forms a new polynomial by multiplying this with another polynomial.
     * This and the other polynomial are unchanged.
     *
     * @param another the other polynomial to multiply with.
     * @return resulting polynomial by multiplying this with the given polynomial.
     * @throws UnsupportedOperationException by default.
     */
    default Polynomial multiplying(Polynomial another) {
        throw new UnsupportedOperationException();
    }
}
