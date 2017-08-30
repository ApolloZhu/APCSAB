package net.fcpsschools._1685666._1.lab._1_interface;

public class PolynomialsFormatter {
    public static String format(Polynomials polynomials) {
        StringBuilder sb = new StringBuilder();
        for (int i = polynomials.getDegree(); i >= 0; i--) {
            sb.append(format(polynomials, i));
        }
        return sb.toString();
    }

    private static String format(Polynomials polynomials, int exponent) {
        return polynomials.getCoefficientForExponent(exponent) + "x^" + exponent + " ";
    }
}
