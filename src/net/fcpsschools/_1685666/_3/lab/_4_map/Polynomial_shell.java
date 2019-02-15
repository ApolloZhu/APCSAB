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

package net.fcpsschools._1685666._3.lab._4_map;

import net.fcpsschools._1685666._1.lab._1_interface.AbstractPolynomial;
import net.fcpsschools._1685666._1.lab._1_interface.Polynomial;

import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.function.DoubleBinaryOperator;

/**
 * @author Apollo/Zhiyu Zhu/朱智语
 * Period: 1
 * Name of the Lab: Polynomial
 * Purpose of the Program: Implement with Map
 * Due Date: 2018/03/19 23:59
 * Date Submitted: 2018/03/19
 * What I learned:
 * - I can use a different comparator to reorder TreeMap.
 * How I feel about this lab
 * - I know you have installed Java 8,
 * so I'm using some lambda expressions,
 * and they look pretty nice.
 * What I wonder:
 * - Why aren't we using the Polynomial
 * interface from 1st quarter?
 * Most Difficult Method:
 * - putTerm. Forgot about 0 coefficient twice.
 * Credits:
 * - Me in first quarter for toString method.
 */
public class Polynomial_shell {
    public static void main(String[] args) {
        Polynomial poly = new MapBasedPolynomial()
                .addingTerm(1, -4)
                .addingTerm(3, 2)
                .addingTerm(0, 2);
        System.out.println(poly);
        final double evaluateAt = 2.0;
        System.out.println("f(" + evaluateAt + ") = "
                + poly.evaluatedAt(evaluateAt));

        Polynomial poly2 = new MapBasedPolynomial(2, 0, 1, -5, -3);
        printAs("g(x)", poly2);

        printAs("f + g", poly.adding(poly2));
        printAs("f * g", poly.multiplying(poly2));
        printAs("f - g", poly.subtracting(poly2));
        printAs("f'(x)", poly.getDerivative());
    }

    private static void printAs(String name, Polynomial polynomial) {
        if (polynomial instanceof AbstractPolynomial)
            ((AbstractPolynomial) polynomial).setName(name);
        System.out.println(polynomial);
    }
}


class MapBasedPolynomial extends AbstractPolynomial {
    private Map<Integer, Double> terms = new TreeMap<>(new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    });

    public MapBasedPolynomial() {
        // Do Nothing
    }

    public MapBasedPolynomial(double... coefficients) {
        for (int i = 0; i < coefficients.length; i++)
            putTerm(coefficients.length - 1 - i, coefficients[i]);
    }

    public MapBasedPolynomial(Polynomial another) {
        if (null == another) throw new NullPointerException();
        if (another instanceof MapBasedPolynomial) {
            ((MapBasedPolynomial) another).terms.forEach(this::putTerm);
        } else {
            for (int i = another.getDegree(); i >= 0; i--) {
                double coefficient = another.getCoefficientForExponent(i);
                putTerm(i, coefficient);
            }
        }
    }

    public MapBasedPolynomial(int exponent, double coefficient) {
        putTerm(exponent, coefficient);
    }

    public MapBasedPolynomial addingTerm(int exponent, double coefficient) {
        return (MapBasedPolynomial) this.adding(new MapBasedPolynomial(exponent, coefficient));
    }

    @Override
    public int getDegree() {
        try {
            return terms.keySet().iterator().next();
        } catch (NoSuchElementException ignored) {
            return 0;
        }
    }

    @Override
    public double getCoefficientForExponent(int exponent) {
        return terms.getOrDefault(exponent, 0.);
    }

    @Override
    public Polynomial adding(Polynomial another) {
        return linearlyMergedWith(another, (lhs, rhs) -> lhs + rhs);
    }

    @Override
    public Polynomial subtracting(Polynomial another) {
        return linearlyMergedWith(another, (lhs, rhs) -> lhs - rhs);
    }

    private Polynomial linearlyMergedWith(Polynomial another, DoubleBinaryOperator operation) {
        MapBasedPolynomial result = new MapBasedPolynomial(this);
        if (null == another) return result;
        if (another instanceof MapBasedPolynomial) {
            ((MapBasedPolynomial) another).terms.forEach((exponent, coefficient) -> {
                double lhs = result.terms.getOrDefault(exponent, 0.);
                double output = operation.applyAsDouble(lhs, coefficient);
                result.putTerm(exponent, output);
            });
        } else {
            for (int i = another.getDegree(); i >= 0; i--) {
                double coefficient = another.getCoefficientForExponent(i);
                double lhs = terms.getOrDefault(i, 0.);
                double output = operation.applyAsDouble(lhs, coefficient);
                result.putTerm(i, output);
            }
        }
        return result;
    }

    @Override
    public Polynomial getDerivative() {
        MapBasedPolynomial result = new MapBasedPolynomial();
        terms.forEach((exponent, coefficient) -> {
            result.putTerm(exponent - 1, coefficient * exponent);
        });
        return result;
    }

    @Override
    public Polynomial multiplying(Polynomial another) {
        Polynomial result = new MapBasedPolynomial();
        if (null == another) return result;
        if (another instanceof MapBasedPolynomial) {
            for (Map.Entry<Integer, Double> entry : ((MapBasedPolynomial) another).terms.entrySet()) {
                result = result.adding(multiplying(entry.getKey(), entry.getValue()));
            }
        } else {
            for (int i = another.getDegree(); i >= 0; i--) {
                double coefficient = another.getCoefficientForExponent(i);
                result = result.adding(multiplying(i, coefficient));
            }
        }
        return result;
    }

    private Polynomial multiplying(int exponent, double scalar) {
        MapBasedPolynomial result = new MapBasedPolynomial();
        terms.forEach((thisExponent, thisCoefficient) -> {
            result.putTerm(thisExponent + exponent, thisCoefficient * scalar);
        });
        return result;
    }

    public Polynomial multiplying(double scalar) {
        if (isValid(scalar)) {
            MapBasedPolynomial result = new MapBasedPolynomial(this);
            result.terms.replaceAll((exponent, coefficient) -> coefficient * scalar);
            return result;
        } else return new MapBasedPolynomial(scalar);
    }

    private boolean isValid(double coefficient) {
        return 0 != coefficient && Double.isFinite(coefficient) && !Double.isNaN(coefficient);
    }

    private void putTerm(int exponent, double coefficient) {
        if (exponent < 0) return;
        if (isValid(coefficient)) {
            terms.put(exponent, coefficient);
        } else if (0 == coefficient) {
            terms.remove(exponent);
        } else {
            terms.clear();
            terms.put(0, coefficient);
        }
    }
}

/*
expected output
   2x^3 + -4x + 2
   10.0
   2x^4 + x^2 + -5x + -3
   2x^4 + 2x^3 + x^2 + -9x + -1
   4x^7 + -6x^5 + -6x^4 + -10x^3 + 22x^2 + 2x + -6
actual output
    f(x) = 2x³ - 4x + 2
    f(2.0) = 10.0
    g(x) = 2x⁴ + x² - 5x - 3
    f + g = 2x⁴ + 2x³ + x² - 9x - 1
    f * g = 4x⁷ - 6x⁵ - 6x⁴ - 10x³ + 22x² + 2x - 6
    f - g = -2x⁴ + 2x³ - x² + x + 5
    f'(x) = 6x² - 4
 */
