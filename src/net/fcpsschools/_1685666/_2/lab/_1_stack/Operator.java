package net.fcpsschools._1685666._2.lab._1_stack;

import java.util.Hashtable;
import java.util.Map;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

/**
 * @author ApolloZhu, Pd. 1
 */
public class Operator {
    public static final Hashtable<String, Double> CONSTANT = new Hashtable<>();
    public static final Hashtable<String, DoubleUnaryOperator> UNARY = new Hashtable<>();
    public static final BinaryOperator EXPONENTIATION = new BinaryOperator();
    public static final BinaryOperator MULTIPLICATION = new BinaryOperator();
    public static final BinaryOperator ADDITION = new BinaryOperator();

    static {
        CONSTANT.put("pi", Math.PI);
        CONSTANT.put("e", Math.E);
        UNARY.put("sin", Math::sin);
        UNARY.put("cos", Math::cos);
        UNARY.put("tan", Math::tan);
        UNARY.put("!", a -> {
            int x;
            if (a < 0 || (x = (int) a) != a)
                throw new IllegalArgumentException(a + "!");
            int result = 1;
            for (int i = 2; i <= x; i++) result *= i;
            return result;
        });
        UNARY.put("+", a -> a);
        UNARY.put("-", a -> -a);
        EXPONENTIATION.put("^", Math::pow);
        MULTIPLICATION.put("*", (a, b) -> a * b);
        MULTIPLICATION.put("/", (a, b) -> a / b);
        MULTIPLICATION.put("%", (a, b) -> a % b);
        ADDITION.put("+", (a, b) -> a + b);
        ADDITION.put("-", (a, b) -> a - b);
    }

    public static Relation compare(String op1, String op2) {
        int p1 = precedenceOf(op1);
        int p2 = precedenceOf(op2);
        if (p1 == -1 || p2 == -1) return Relation.UNDEFINED;
        return p1 == p2 ? Relation.SAME :
                p1 < p2 ? Relation.LOWER : Relation.HIGHER;
    }

    private static int precedenceOf(String op) {
        if (ADDITION.containsKey(op)) return 0;
        if (MULTIPLICATION.containsKey(op)) return 1;
        if (EXPONENTIATION.containsKey(op)) return 2;
        if (isUnary(op)) return 3;
        if (isConstant(op)) return 4;
        return -1;
    }

    public static boolean isConstant(String constant) {
        try {
            Double.parseDouble(constant);
            return true;
        } catch (Exception e) {
            return CONSTANT.containsKey(constant);
        }
    }

    public static double evaluate(String constant) {
        try {
            return Double.parseDouble(constant);
        } catch (NumberFormatException e) {
            for (Map.Entry<String, Double> entry : CONSTANT.entrySet())
                if (entry.getKey().equals(constant))
                    return entry.getValue();
            throw new IllegalArgumentException("constant '" + constant + "' not found");
        }
    }

    public static boolean isUnary(String op) {
        return UNARY.containsKey(op);
    }

    public static double evaluate(String op, String operand) {
        for (Map.Entry<String, DoubleUnaryOperator> entry : UNARY.entrySet())
            if (entry.getKey().equals(op))
                return entry.getValue().applyAsDouble(evaluate(operand));
        throw new IllegalArgumentException("unary operator '" + op + "' not found");
    }

    public static boolean isBinary(String op) {
        int precedence = precedenceOf(op);
        return 0 <= precedence && precedence <= 2;
    }

    public static double evaluate(String op, String lhs, String rhs) {
        double a = evaluate(lhs), b = evaluate(rhs);
        for (Map.Entry<String, DoubleBinaryOperator> entry : EXPONENTIATION.entrySet())
            if (entry.getKey().equals(op))
                return entry.getValue().applyAsDouble(a, b);
        for (Map.Entry<String, DoubleBinaryOperator> entry : MULTIPLICATION.entrySet())
            if (entry.getKey().equals(op))
                return entry.getValue().applyAsDouble(a, b);
        for (Map.Entry<String, DoubleBinaryOperator> entry : ADDITION.entrySet())
            if (entry.getKey().equals(op))
                return entry.getValue().applyAsDouble(a, b);
        throw new IllegalArgumentException("binary operator '" + op + "' not found");
    }

    public static boolean isOperator(String s) {
        return isUnary(s) || isBinary(s);
    }

    enum Relation {LOWER, HIGHER, SAME, UNDEFINED}

    private static class BinaryOperator
            extends Hashtable<String, DoubleBinaryOperator> {
    }
}
