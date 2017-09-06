package net.fcpsschools._1685666._1.lab._1_interface;

/**
 * Abstract polynomial handling evaluation, formatting, and equality check.
 *
 * @author ApolloZhu
 */
public abstract class AbstractPolynomial implements Polynomial {
    @Override
    public double evaluatedAt(double x) {
        double answer = getCoefficientForExponent(0);
        int degree = getDegree();
        if (degree == 0 || x == 0) return answer;
        if (Double.isNaN(x)) return Double.NaN;

        INFINITY_CHECK:
        if (Double.isInfinite(x)) {
            double sign = allCoefficientsHaveSameSignThroughDegree(degree);
            if (Double.isNaN(sign)) return Double.NaN;
            if (sign == 0) throw new IllegalStateException("File a radar please");
            return sign * x;
        }

        for (int i = 1; i <= degree; i++)
            answer += Math.pow(x, i) * getCoefficientForExponent(i);

        return answer;
    }

    /**
     * Check if all coefficients of the polynomial has the same sign.
     *
     * @param degree the highest degree to check.
     * @return 0                if the polynomial is constant;
     * Double.POSITIVE_INFINITY if all coefficients are positive or zero;
     * Double.NEGATIVE_INFINITY if all coefficients are negative or zero;
     * Double.NaN               if any of the coefficients has different sign or is not a number.
     */
    private double allCoefficientsHaveSameSignThroughDegree(double degree) {
        double sign = 0;
        for (int i = 0; i <= degree; i++) {
            double c = getCoefficientForExponent(i);
            if (Double.isNaN(c)) return Double.NaN;
            if (c == 0) continue;
            double indicator = c * Double.POSITIVE_INFINITY;
            if (indicator == Double.POSITIVE_INFINITY)
                if (sign == 0) sign = Double.POSITIVE_INFINITY;
                else if (sign == Double.NEGATIVE_INFINITY) return Double.NaN;
            if (indicator == Double.NEGATIVE_INFINITY)
                if (sign == 0) sign = Double.NEGATIVE_INFINITY;
                else if (sign == Double.POSITIVE_INFINITY) return Double.NaN;
        }
        return sign;
    }

    /**
     * Name of this polynomial.
     */
    private String name = "f(x)";

    /**
     * Name of the variable used in this polynomial.
     */
    private String variableName = "x";

    /**
     * Name of this polynomial, default to "f(x)".
     *
     * @return polynomial name.
     */
    public String getName() {
        return name;
    }

    /**
     * Name of the variable used in this polynomial, default to "x".
     *
     * @return variable name.
     */
    public String getVariableName() {
        return variableName;
    }

    /**
     * Set name of this polynomial.
     *
     * @param name new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set name of the variable used in this polynomial.
     *
     * @param variableName new variable name.
     */
    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    /**
     * Pretty output for polynomial.
     *
     * @return human friendly polynomial presentation.
     */
    @Override
    public String toString() {
        int degree = getDegree();
        StringBuilder sb = new StringBuilder(name + " = ");
        for (int i = degree; i >= 0; i--)
            sb.append(makePrettyTermForExponent(i));
        return sb.toString();
    }

    /**
     * Format term identified by certain exponent.
     *
     * @param exponent exponent of the term to format.
     * @return human friendly term representation within context.
     */
    private String makePrettyTermForExponent(int exponent) {
        int degree = getDegree();
        if (exponent > degree) return "";
        double coefficient = getCoefficientForExponent(exponent);
        if (coefficient == 0 && (exponent != 0 || degree > 0)) return "";
        String x = exponent == 0 ? "" : variableName + makePrettyExponent(exponent);
        String conjunctionToPreviousTerm = exponent == degree
                ? (coefficient >= 0 ? "" : "-")
                : (coefficient > 0 ? " + " : " - ");
        return conjunctionToPreviousTerm + makeSimpleCoefficient(coefficient, exponent) + x;
    }

    /**
     * Superscript 0-9, indexed by value.
     *
     * @link https://en.wikipedia.org/wiki/Unicode_subscripts_and_superscripts
     */
    private static final String[] SUPERSCRIPTS = {"\u2070", "\u00B9", "\u00B2", "\u00B3", "\u2074", "\u2075", "\u2076", "\u2077", "\u2078", "\u2079"};

    /**
     * The negative prefix of a exponent (superscript representation).
     */
    private static final String SUPERSCRIPT_MINUS_SIGN = "\u207B";

    /**
     * Transforming int to exponent (superscript representation).
     *
     * @param exponent exponent to transform.
     * @return human friendly exponent representation within context.
     */
    private static String makePrettyExponent(int exponent) {
        if (exponent == 0 || exponent == 1) return "";
        StringBuilder sb = new StringBuilder();

        int mutableCopy = Math.abs(exponent);
        while (mutableCopy > 0) {
            sb.insert(0, SUPERSCRIPTS[mutableCopy % 10]);
            mutableCopy /= 10;
        }
        return (exponent > 0 ? "" : SUPERSCRIPT_MINUS_SIGN) + sb.toString();
    }

    /**
     * Simplify double coefficient into int if possible.
     *
     * @param coefficient coefficient to simplify.
     * @param exponent    exponent of the term with the given coefficient.
     * @return absolute value of the simplified coefficient, or empty string if unnecessary.
     */
    private static String makeSimpleCoefficient(double coefficient, int exponent) {
        coefficient = Math.abs(coefficient);
        int intCoefficient = (int) coefficient;
        return (coefficient == intCoefficient)
                ? ((intCoefficient == 1 && exponent != 0) ? "" : "" + intCoefficient)
                : "" + coefficient;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Polynomial)) return super.equals(obj);
        Polynomial another = (Polynomial) obj;
        int exponent = getDegree();
        int thatDegree = another.getDegree();
        while (another.getCoefficientForExponent(thatDegree) == 0) thatDegree--;

        if (exponent != thatDegree) return false;
        for (; exponent >= 0; exponent--)
            if (getCoefficientForExponent(exponent) != another.getCoefficientForExponent(exponent))
                return false;
        return true;
    }
}
