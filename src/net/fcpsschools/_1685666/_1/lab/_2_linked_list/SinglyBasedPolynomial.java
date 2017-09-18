package net.fcpsschools._1685666._1.lab._2_linked_list;

import javafx.util.Pair;
import net.fcpsschools._1685666._1.lab._1_interface.AbstractPolynomial;
import net.fcpsschools._1685666._1.lab._1_interface.Polynomial;

import java.util.LinkedList;

/**
 * @author ApolloZhu, Pd. 1
 */
public class SinglyBasedPolynomial extends AbstractPolynomial {
    private LinkedList<Pair<Integer, Double>> coefficients = new LinkedList<>();

    public SinglyBasedPolynomial() {
    }

    public SinglyBasedPolynomial(Polynomial another) {
    }

    @Override
    public int getDegree() {
        return 0;
    }

    @Override
    public double getCoefficientForExponent(int exponent) {
        return 0;
    }

    @Override
    public Polynomial adding(Polynomial another) {
        return null;
    }

    @Override
    public Polynomial subtracting(Polynomial another) {
        return null;
    }

    public Polynomial multiplying(Polynomial another) {
        return null;
    }

    @Override
    public Polynomial getDerivative() {
        return null;
    }
}
