package net.fcpsschools._1685666._2.lab._1_stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author ApolloZhu, Pd. 1
 */
class CalculatorBrainTest {
    private static void evalPostfixTo(double expected, String postfix) {
        assertEquals(expected,
                CalculatorBrain.evaluatePostfix(postfix));
    }

    private static void evalInfixTo(double expected, String infix) {
        assertEquals(expected,
                CalculatorBrain.evaluatePostfix(
                        InfixToPostfix.convert(infix)));
    }

    @Test
    void testEvaluatePostfix() {
        evalPostfixTo(23,
                "3 4 5 * +");
        evalPostfixTo(17,
                "3 4 * 5 +");
        evalPostfixTo(-6,
                "4 5 + 5 3 * -");
        evalPostfixTo(77,
                "3 4 + 5 6 + *");
        evalPostfixTo(5,
                "3 4 5 + * 2 - 5 /");
        evalPostfixTo(7,
                "8 1 2 * + 9 3 / -");
        evalPostfixTo(1,
                "5 3 % ! 3 ^ pi * cos");
        evalPostfixTo(17,
                "    -3  4 5 *  + ");
        evalPostfixTo(17,
                "3 - 4 5 * +");

        evalInfixTo(2, "log(100)");
    }

    @Test
    void testNewOperator() {
        Operator.CONSTANT.put("😀", -1.0);
        evalInfixTo(-3, "😀 * 3");
        Operator.registerUnaryOperator("%", Operator.Associativity.LEFT, a -> a / 100);
        evalInfixTo(0.98, "(10^2-2)%");
        Operator.registerUnaryOperator("sqrt", Operator.Associativity.RIGHT, Math::sqrt);
        // FIXME: Incorrect conversion.
        System.out.println(InfixToPostfix.convert("sqrt(sin(1-red(30)))"));
        evalInfixTo(0.25, "sqrt(sin(1-red(30)))");
    }
}
