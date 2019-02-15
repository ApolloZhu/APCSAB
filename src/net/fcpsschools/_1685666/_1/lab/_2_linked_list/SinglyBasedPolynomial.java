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
import net.fcpsschools._1685666._1.lab._1_interface.Polynomial;

import java.util.function.DoubleBinaryOperator;

/**
 * @author Apollo/Zhiyu Zhu/朱智语, Pd. 1
 * What I learned:
 * - Appending to the end of a list.
 * - The real-world application of this polynomial thing,
 * although array based, from https://github.com/davidshimjs/qrcodejs
 * <p>
 * What I wondered:
 * - How to efficiently implement these methods.
 * - How to specify complexity if the complexity of a part
 * of the operation varies based on different situation.
 */
public final class SinglyBasedPolynomial extends AbstractPolynomial {

    /**
     * Terms of this polynomial, ordered from the highest exponent to the lowest.
     * Should never be null; instead, use TermListNode.ZERO().
     *
     * @apiNote Although the packet says should be in ascending order,
     * but that would rather be less efficient when getting the degree;
     * to have a O(1) complexity, we have to introduce another variable
     * and update it every time the terms has changed. Thus descending.
     */
    private TermListNode terms;

    /**
     * Constructs a polynomial f(x) = 0.
     */
    public SinglyBasedPolynomial() {
        terms = TermListNode.ZERO();
    }

    /**
     * Constructs a polynomial using coefficients in natural order.
     *
     * @param coefficients coefficients in natural order, must include 0s.
     *                     For example, to construct 2x&sup2; - 4, use 2, 0, -4.
     * @implSpec Complexity: O(n)
     */
    public SinglyBasedPolynomial(double... coefficients) {
        TermListNode head = null, curTail = null, copy;
        for (int i = 0, degree = coefficients.length - 1; degree >= 0; i++, degree--) {
            if (coefficients[i] == 0) continue; // Skip unnecessary node
            copy = new TermListNode(degree, coefficients[i], null);
            if (head == null) head = curTail = copy;
            else curTail.setNext(curTail = copy);
        }
        terms = head == null ? TermListNode.ZERO() : head;
    }

    /**
     * Constructs a polynomial using terms given (in any order).
     *
     * @param components terms of this polynomial.
     * @implSpec Complexity: O(n^2)
     */
    public SinglyBasedPolynomial(PolynomialTerm... components) {
        Polynomial result = new SinglyBasedPolynomial();
        SinglyBasedPolynomial partial;
        for (PolynomialTerm term : components) {
            if (term.getCoefficient() == 0) continue;
            partial = new SinglyBasedPolynomial(); // avoid recursion
            partial.terms = new TermListNode(term, null);
            result = result.adding(partial); // Will perform sorting
        }
        terms = ((SinglyBasedPolynomial) result).terms;
        if (terms == null) terms = TermListNode.ZERO();
    }

    /**
     * Constructs this as an mathematically identical copy of the given polynomial.
     *
     * @param another the polynomial to copy.
     * @implSpec Complexity: ~O(n^2)
     */
    public SinglyBasedPolynomial(Polynomial another) {
        double coefficient;
        int degree = another.getDegree();
        // Chain from lowest to highest
        for (int i = 0; i <= degree; i++)
            if ((coefficient = another.getCoefficientForExponent(i)) != 0) // O(?)
                terms = new TermListNode(i, coefficient, terms);
        if (terms == null) terms = TermListNode.ZERO();
    }

    /**
     * Finds the term of a certain exponent, starting from a certain node.
     *
     * @param exponent exponent of the node to find.
     * @param start    the first node to search from.
     * @return null if not found; otherwise the node having the given exponent.
     * @apiNote This is mainly introduced for efficiency.
     */
    private static TermListNode getTermForExponent(int exponent, TermListNode start) {
        TermListNode term = start;
        double degree;
        while (term != null && (degree = term.getDegree()) != exponent) {
            // Since is in descending order, if current is less than exponent
            // We are never going to find one, thus quit.
            if (degree < exponent) return null;
            term = term.getNext();
        }
        return term;
    }

    /**
     * {@inheritDoc}
     *
     * @implSpec Complexity: O(1), since terms are in descending order.
     */
    @Override
    public int getDegree() {
        return terms.getDegree();
    }

    /**
     * {@inheritDoc}
     *
     * @implSpec Complexity: O(n)
     */
    @Override
    public double getCoefficientForExponent(int exponent) {
        TermListNode term = getTermForExponent(exponent, terms);
        if (term == null) return 0;
        return term.getCoefficient();
    }

    /**
     * {@inheritDoc}
     *
     * @implSpec Complexity: O(n)
     */
    @Override
    public Polynomial adding(Polynomial another) {
        return linearlyMergedUsing((self, that) -> self + that, another);
    }

    /**
     * {@inheritDoc}
     *
     * @implSpec Complexity: O(n)
     */
    @Override
    public Polynomial subtracting(Polynomial another) {
        return linearlyMergedUsing((self, that) -> self - that, another);
    }

    /**
     * Form a new polynomial by operating on each like term of self and another polynomial.
     * A non-existing term is treated as a term with coefficient of 0.
     * This and the other is not changed.
     *
     * @param operator how to combine each pair of like terms of this and `another` polynomial.
     * @param another  the other polynomial involved in the computation.
     * @return the resulting polynomial.
     * @implSpec Complexity: O(n)
     */
    protected Polynomial linearlyMergedUsing(DoubleBinaryOperator operator, /*with*/ Polynomial another) {
        // Validate
        int thisDegree = getDegree();
        int thatDegree = another.getDegree();
        while (another.getCoefficientForExponent(thatDegree) == 0) thatDegree--;
        // Allocate according to the highest degree
        int maxDegree = Math.max(thisDegree, thatDegree);
        TermListNode newHead = null, curTail = null, i = terms,
                /*for efficiency*/ tmp, copy;
        double coefficient;
        // Apply operator on each term
        for (int exponent = maxDegree; exponent >= 0; exponent--) {
            coefficient = 0;
            if (exponent <= thisDegree)
                if ((tmp = getTermForExponent(exponent, i)) != null)
                    coefficient = (i = tmp).getCoefficient();
            if (exponent <= thatDegree)
                coefficient = operator.applyAsDouble(coefficient,
                        another.getCoefficientForExponent(exponent));
            if (coefficient == 0) continue; // Skip null
            copy = new TermListNode(exponent, coefficient, null);
            if (newHead == null) newHead = curTail = copy;
            else curTail.setNext(curTail = copy);
        }
        SinglyBasedPolynomial result = new SinglyBasedPolynomial();
        if (newHead != null) result.terms = newHead;
        return result;
    }

    /**
     * Forms a new polynomial by multiplying another polynomial with this.
     * This and the other are unchanged.
     *
     * @param another the other polynomial to multiply with.
     * @return resulting polynomial by multiplying the given polynomial with this.
     * @implSpec Complexity: ~O(n^3)
     */
    public Polynomial multiplying(Polynomial another) {
        Polynomial result = new SinglyBasedPolynomial();
        SinglyBasedPolynomial partial;
        PolynomialTerm term;
        TermListNode head, curTail, copy;
        double thatCoefficient, thisCoefficient;
        int thisDegree;

        for (TermListNode node = terms; node != null; node = node.getNext()) { // O(n)
            // Initialize
            head = curTail = null;
            term = node.getTerm();
            if ((thisCoefficient = term.getCoefficient()) == 0) continue;
            thisDegree = term.getDegree();

            for (int thatDegree = another.getDegree(); thatDegree >= 0; thatDegree--) { // O(n)
                thatCoefficient = another.getCoefficientForExponent(thatDegree); // O(?)
                if (thatCoefficient == 0) continue;
                // Multiply
                copy = new TermListNode(thisDegree + thatDegree, thisCoefficient * thatCoefficient, null);
                if (head == null) head = curTail = copy;
                else curTail.setNext(curTail = copy);
            }
            partial = new SinglyBasedPolynomial();
            // This is ordered, multiplied with in descending order.
            // So the result is also in descending order.
            if (head != null) partial.terms = head;
            else continue;
            result = result.adding(partial); // O(n)
        }
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @implSpec Complexity: O(n)
     */
    @Override
    public Polynomial getDerivative() {
        TermListNode newHead = null, curTail = null, copy;
        int degree;
        double coefficient;
        for (TermListNode term = terms; term != null; term = term.getNext()) {
            degree = term.getDegree();
            coefficient = term.getCoefficient() * degree--;
            if (coefficient == 0) continue;
            copy = new TermListNode(degree, coefficient, null);
            if (newHead == null) newHead = curTail = copy;
            else curTail.setNext(curTail = copy);
        }
        SinglyBasedPolynomial result = new SinglyBasedPolynomial();
        if (newHead != null) result.terms = newHead;
        return result;
    }

    /**
     * There should be NO setter for degree and coefficient.
     */
    private static class TermListNode {
        /**
         * This term.
         */
        private PolynomialTerm term;

        /**
         * The next term in the list.
         */
        private TermListNode next;

        /**
         * Constructs a term in the polynomial list.
         *
         * @param degree      the degree of this term.
         * @param coefficient the coefficient of this term.
         * @param next        the next term in the list.
         */
        private TermListNode(int degree, double coefficient, TermListNode next) {
            this(new PolynomialTerm(degree, coefficient), next);
        }

        /**
         * Constructs a term in the polynomial list.
         *
         * @param term the term.
         * @param next the next term in the list.
         */
        private TermListNode(PolynomialTerm term, TermListNode next) {
            this.term = term == null ? PolynomialTerm.ZERO : term;
            this.next = next;
        }

        /**
         * Returns a new constant term of zero.
         *
         * @return a newly created instance of this as 0x^0.
         */
        private static TermListNode ZERO() {
            return new TermListNode(PolynomialTerm.ZERO, null);
        }

        /**
         * Returns this term.
         *
         * @return the term.
         */
        public PolynomialTerm getTerm() {
            return term;
        }

        /**
         * Returns the degree of this term.
         *
         * @return the degree.
         */
        public int getDegree() {
            return getTerm().getDegree();
        }

        /**
         * Returns the coefficient of this term.
         *
         * @return the coefficient.
         */
        public double getCoefficient() {
            return getTerm().getCoefficient();
        }

        /**
         * Returns the next term inn the polynomial list.
         *
         * @return null, or should be the next term with a smaller exponent.
         */
        public TermListNode getNext() {
            return next;
        }

        /**
         * Set the next term in the polynomial list.
         *
         * @param next null, or the next term (should have a smaller exponent).
         */
        public void setNext(TermListNode next) {
            this.next = next;
        }
    }
}
