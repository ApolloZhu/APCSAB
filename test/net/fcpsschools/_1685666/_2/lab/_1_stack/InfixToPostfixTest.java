package net.fcpsschools._1685666._2.lab._1_stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author ApolloZhu, Pd. 1
 */
class InfixToPostfixTest {
    private void infixToPostfix(String infix, String expected) {
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

        assertThrows(IllegalArgumentException.class,
                () -> InfixToPostfix.convert("((5+70)))"));
        infixToPostfix("((3+4*(50+6",
                "3 4 50 6 + * +");
        infixToPostfix("(52+7.0", "52 7.0 +");
        assertThrows(NumberFormatException.class,
                () -> InfixToPostfix.convert("7.2.55"));

        infixToPostfix("cos(( 5 % 3) !^ 3*pi)",
                "5 3 % ! 3 ^ pi * cos");

        infixToPostfix("-3+4*5",
                "3 - 4 5 * +");
    }
}
