package net.fcpsschools._1685666._2.lab._1_stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author ApolloZhu, Pd. 1
 */
class PostfixTest {
    private static void evalPostfixTo(double expected, double delta, String postfix) {
        assertEquals(expected,
                Postfix.eval(postfix), delta);
    }

    private static void evalPostfixToExactly(double expected, String postfix) {
        assertEquals(expected,
                Postfix.eval(postfix));
    }

    private static void evalInfixTo(double expected, double delta, String infix) {
        evalPostfixTo(expected, delta, Infix.toPostfix(infix));
    }

    private static void evalInfixToExactly(double expected, String infix) {
        evalPostfixToExactly(expected, Infix.toPostfix(infix));
    }

    @Test
    void testGivenExample() {
        evalPostfixToExactly(161, "3 5 4 * + 7 *");
        evalPostfixToExactly(6, "8 2 -");
        evalPostfixToExactly(4, "8 2 /");
    }

    @Test
    void testEvaluatePostfix() {
        evalPostfixToExactly(23,
                "3 4 5 * +");
        evalPostfixToExactly(17,
                "3 4 * 5 +");
        evalPostfixToExactly(-6,
                "4 5 + 5 3 * -");
        evalPostfixToExactly(77,
                "3 4 + 5 6 + *");
        evalPostfixToExactly(5,
                "3 4 5 + * 2 - 5 /");
        evalPostfixToExactly(7,
                "8 1 2 * + 9 3 / -");
        evalPostfixToExactly(1,
                "5 3 % ! 3 ^ pi * cos");
        evalPostfixToExactly(17,
                "    -3  4 5 *  + ");
        evalPostfixToExactly(17,
                "3 - 4 5 * +");
    }

    @Test
    void testEvalInfix() {
        evalInfixToExactly(2, "log(100)");

        evalInfixTo(-1.54053662, 1E-7,
                "tan(2*3^(3+2-3.123)/12.2)+sin(pi)+cos(2)*sqrt(144)");

        evalInfixToExactly(720, "3!!");

        assertThrows(ArithmeticException.class,
                () -> System.out.println(Postfix.eval(Infix.toPostfix("1/0"))));
    }

    @Test
    void testNewOperator() {
        Operators.CONSTANT.put("😀", -1.0);
        evalInfixToExactly(-3,
                "😀 * 3");
        Operators.registerUnaryOperator("%",
                Operators.Associativity.LEFT, a -> a / 100);
        // Notice the first is binary, the second one
        // is the newly registered left associate unary
        evalInfixToExactly(0.98,
                "(98 % 10^2)%");

        Operators.registerUnaryOperator("√",
                Operators.Associativity.RIGHT, Math::sqrt);
        InfixTest.infixToPostfix("√√√(2^3*sin(pi/6))",
                "2 3 ^ pi 6 / sin * √ √ √");
        evalInfixTo(1.189207115, 1E-9,
                "√√√(2^3*sin(pi/6))");
        evalInfixToExactly(0, "3!!-√(√(720^2)^2)");
    }
}
