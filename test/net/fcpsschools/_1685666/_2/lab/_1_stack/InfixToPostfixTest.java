package net.fcpsschools._1685666._2.lab._1_stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author ApolloZhu, Pd. 1
 */
class InfixToPostfixTest {
    void infixToPostfix(String infix, String expected) {
        assertEquals(expected, InfixToPostfix.convert(infix));
    }

    @Test
    void testConvert() {
        infixToPostfix("3+4-5+6",
                "3 4 + 5 - 6 +");
        infixToPostfix("(3+4)*5",
                "3 4 + 5 *");
        infixToPostfix("3*4+5*6",
                "3 4 * 5 6 * +");

        infixToPostfix("3+4*5",
                "3 4 5 * +");
        infixToPostfix("3*4+5",
                "3 4 * 5 +");
        infixToPostfix("(4+5)-5*3",
                "4 5 + 5 3 * -");
        infixToPostfix("(3+4)*(5+6)",
                "3 4 + 5 6 + *");
        infixToPostfix("(3*(4+5)-2)/5",
                "3 4 5 + * 2 - 5 /");
        infixToPostfix("8+1*2-9/3",
                "8 1 2 * + 9 3 / -");
    }
}
