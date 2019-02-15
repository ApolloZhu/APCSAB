/*
MIT License

Copyright (c) 2017-2019 Apollo/Zhiyu Zhu/朱智语 <public-apollonian@outlook.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package net.fcpsschools._1685666._2.lab._1_stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Apollo/Zhiyu Zhu/朱智语, Pd. 1
 */
class InfixTest {
    static void infixToPostfix(String infix, String expected) {
        assertEquals(expected, Infix.toPostfix(infix));
    }

    static void assertInvalidInfix(String infix) {
        assertThrows(IllegalArgumentException.class,
                () -> System.out.println(Infix.toPostfix(infix)));
    }

    @Test
    void testGivenExample() {
        infixToPostfix("(3*(4+5)-2)/5",
                "3 4 5 + * 2 - 5 /");
    }

    @Test
    void testBasic() {
        infixToPostfix("3+4-5+6",
                "3 4 + 5 - 6 +");
        infixToPostfix("(3+4)*5",
                "3 4 + 5 *");
        infixToPostfix("3*4+5*6",
                "3 4 * 5 6 * +");
    }

    @Test
    void testBinary() {
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

    @Test
    void testUnmatched() {
        assertInvalidInfix("((5+70)))");
        infixToPostfix("((3+4*(50+6",
                "3 4 50 6 + * +");
        infixToPostfix("(52+7.0", "52 7.0 +");
        assertThrows(NumberFormatException.class,
                () -> Infix.toPostfix("7.2.55"));
    }

    @Test
    void testDifferentStyle() {
        infixToPostfix("5+7", "5 7 +");
        infixToPostfix("(5+7)", "5 7 +");
        infixToPostfix("((5+7)*3", "5 7 + 3 *");
        infixToPostfix("[(5+7]*3", "5 7 + 3 *");
        infixToPostfix("([(5+7)*3]", "5 7 + 3 *");
        infixToPostfix("((5+7)*3)", "5 7 + 3 *");
        infixToPostfix("<{5+7}*3>", "5 7 + 3 *");
        infixToPostfix("(5+7)*3", "5 7 + 3 *");
        infixToPostfix("5+(7*3)", "5 7 3 * +");

        infixToPostfix("5+7*3", "5 7 3 * +");

        assertInvalidInfix(")5+7(");
        assertInvalidInfix("[(5+7]*3)");
        assertInvalidInfix("[(5+7)*3])");
        assertInvalidInfix("[(5+7)*]3");
    }

    @Test
    void testInvalid() {
        assertInvalidInfix("1+");
        assertInvalidInfix("*1");
        assertInvalidInfix("(1+)+2");
        assertInvalidInfix("(/1)!2");

        assertInvalidInfix("1!2");
        assertInvalidInfix("2 sqrt 1");
    }

    @Test
    void testNegate() {
        infixToPostfix(" - 3+4*-5",
                "-3 4 -5 * +");
        infixToPostfix("-(3+4)",
                "3 4 + -");
        infixToPostfix("+(-(-3+4))",
                "-3 4 + - +");
    }

    @Test
    void testComplex() {
        infixToPostfix("sqrt(sqrt(sqrt(2^2^2!!!",
                "2 2 ^ 2 ! ! ! ^ sqrt sqrt sqrt");
        infixToPostfix("cos(( 5 % -3) !^ 3*pi)",
                "5 -3 % ! 3 ^ pi * cos");
        infixToPostfix("tan(2*3^(3+2-3.123)/12.2)+sin(pi)+cos(2)*sqrt(144)",
                "2 3 3 2 + 3.123 - ^ * 12.2 / tan pi sin + 2 cos 144 sqrt * +");
    }
}
