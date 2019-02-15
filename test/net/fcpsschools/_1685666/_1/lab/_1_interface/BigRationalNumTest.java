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

package net.fcpsschools._1685666._1.lab._1_interface;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Apollo/Zhiyu Zhu/朱智语
 */
class BigRationalNumTest {
    @Test
    void testIntToString() {
        assertEquals("-2", new BigRationalNum(-2).toString());
    }

    @Test
    void testRationalToString() {
        assertEquals("1/3", new BigRationalNum(1, 3).toString());
    }

    @Test
    void testSimplify() {
        assertEquals("2/3", new BigRationalNum(4, 6).toString());
    }

    @Test
    void testSimplifyNegative() {
        assertEquals("-5/6", new BigRationalNum(-10, 12).toString());
    }

    @Test
    void testSignFix() {
        assertEquals("-5/6", new BigRationalNum(10, -12).toString());
    }

    @Test
    void testNegated() {
        assertEquals("1/2", new BigRationalNum(1, -2).negated().toString());
    }

    @Test
    void testInverted() {
        assertEquals("1/2", new BigRationalNum(2).inverted().toString());
    }

    @Test
    void testEquals() {
        assertTrue(new BigRationalNum(-2, 4).negated().equals(new BigRationalNum(2, 1).inverted()));
    }

    @Test
    void testOperation() {
        BigRationalNum negativeHalf = new BigRationalNum(2, -4);
        BigRationalNum two = new BigRationalNum(2);

        negativeHalf.add(two);
        assertEquals("3/2", negativeHalf.toString());
        two.subtract(negativeHalf);
        assertEquals("1/2", two.toString());
        negativeHalf.multiply(two);
        assertEquals("3/4", negativeHalf.toString());
        two.divide(negativeHalf);
        assertEquals("2/3", two.toString());
    }

    @Test
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> new BigRationalNum(1, 0));
    }
}
