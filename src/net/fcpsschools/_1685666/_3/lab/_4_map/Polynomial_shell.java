package net.fcpsschools._1685666._3.lab._4_map;

import net.fcpsschools._1685666._1.lab._1_interface.AbstractPolynomial;
import net.fcpsschools._1685666._1.lab._1_interface.Polynomial;

import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.function.DoubleBinaryOperator;

/**
 * Name:
 * Period:
 * Name of the Lab:
 * Purpose of the Program:
 * Due Date:
 * Date Submitted:
 * What I learned:
 * <p>
 * How I feel about this lab
 * <p>
 * What I wonder:
 * <p>
 * Most Difficult Method:
 * <p>
 * Credits:
 */
public class Polynomial_shell {
    public static void main(String[] args) {
        Polynomial poly = new MapBasedPolynomial()
                .addingTerm(1, -4)
                .addingTerm(3, 2)
                .addingTerm(0, 2);
        System.out.println(poly);
        double evaluateAt = 2.0;
        System.out.println("Evaluated at " + evaluateAt + ": " + poly.evaluatedAt(evaluateAt));

        // Polynomial poly2 = new MapBasedPolynomial(2, 0, 1, -5, -3);
        Polynomial poly2 = new MapBasedPolynomial(-3, -5, 1, 0, 2);
        System.out.println(poly2);

        System.out.println(poly.adding(poly2));
        System.out.println(poly.multiplying(poly2));
        System.out.println(poly.subtracting(poly2));
        System.out.println(poly.getDerivative());
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
        if (0 == coefficient) terms.remove(exponent);
        if (isValid(coefficient)) {
            terms.put(exponent, coefficient);
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
 */