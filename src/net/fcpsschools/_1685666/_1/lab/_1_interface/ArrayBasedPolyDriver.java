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

/**
 * @author Apollo/Zhiyu Zhu/朱智语
 */
public class ArrayBasedPolyDriver {
    public static void main(String[] args) {
        double[] c = {4, 3, 0, 1};
        double[] c1 = {-5, -2};

        Polynomial p1 = new ArrayBasedPoly("p1(x)", c);
        System.out.println(p1);

        Polynomial p2 = new ArrayBasedPoly(c1);
        ((ArrayBasedPoly) p2).setName("p2(x)");
        System.out.println(p2);

        Polynomial p3 = ArrayBasedPoly.makeSingleTerm(-4, 1);
        ((ArrayBasedPoly) p3).setName("p3(x)");
        System.out.println(p3);

        Polynomial p = p1.adding(p2).adding(p2);
        ((AbstractPolynomial) p).setName("p(x)");
        System.out.println(p);

        Polynomial p4 = p.subtracting(p3);
        ((AbstractPolynomial) p4).setName("p4(x)");
        System.out.println(p4);

        Polynomial p5 = p4.getDerivative();
        ((AbstractPolynomial) p5).setName("p5(x)");
        System.out.println(p5);

        System.out.println("p5(0) = " + p5.evaluatedAt(0));
        System.out.println("p5(1) = " + p5.evaluatedAt(1));
    }
}
/*
p1(x) = 4x³ + 3x² + 1
p2(x) = -5x - 2
p3(x) = -4x
p(x) = 4x³ + 3x² - 10x - 3
p4(x) = 4x³ + 3x² - 6x - 3
p5(x) = 12x² + 6x - 6
p5(0) = -6.0
p5(1) = 12.0
*/
