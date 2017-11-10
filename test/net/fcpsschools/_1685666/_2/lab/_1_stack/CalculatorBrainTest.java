package net.fcpsschools._1685666._2.lab._1_stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author ApolloZhu, Pd. 1
 */
class CalculatorBrainTest {
    void evalTo(int expected, String postfix) {
        assertEquals(expected,
                CalculatorBrain.evaluatePostfix(postfix));
    }

    @Test
    void testEvaluatePostfix() {
        evalTo(23,
                "3 4 5 * +");
        evalTo(17,
                "3 4 * 5 +");
        evalTo(-6,
                "4 5 + 5 3 * -");
        evalTo(77,
                "3 4 + 5 6 + *");
        evalTo(5,
                "3 4 5 + * 2 - 5 /");
        evalTo(7,
                "8 1 2 * + 9 3 / -");
    }
}
