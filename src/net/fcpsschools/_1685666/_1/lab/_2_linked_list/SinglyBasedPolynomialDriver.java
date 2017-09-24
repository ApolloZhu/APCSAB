package net.fcpsschools._1685666._1.lab._2_linked_list;

import net.fcpsschools._1685666._1.lab._1_interface.AbstractPolynomial;
import net.fcpsschools._1685666._1.lab._1_interface.Polynomial;

// test client
public class SinglyBasedPolynomialDriver {
    public static void main(String[] args) {

        SinglyBasedPolynomial p1 = new SinglyBasedPolynomial(
                new PolynomialTerm(3, 4),
                new PolynomialTerm(2, 3),
                new PolynomialTerm(0, 1)
        );  // 4x^3 + 3x^2 + 1
        p1.setName("p1(x)");
        System.out.println(p1);

        Polynomial p2 = new SinglyBasedPolynomial(-5, -2);   // - 5x - 2
        ((AbstractPolynomial) p2).setName("p2(x)");
        System.out.println(p2);                 // p2(x) = - 5x^1 - 2

        Polynomial p3 = new SinglyBasedPolynomial(new PolynomialTerm(1, -4));  // coeff, exp = -4x
        ((AbstractPolynomial) p3).setName("p3(x)");
        System.out.println(p3);

        System.out.println(p1.adding(p2));
        Polynomial p = p1.adding(p2).adding(p2);   // 4x^3 + 3x^2 - 10x - 3
        ((AbstractPolynomial) p).setName("p(x)");
        System.out.println(p);       // p(x) = 4x^3 + 3x^2 - 10x^1 - 3

        Polynomial p4 = p.subtracting(p3);   // 4x^3 + 3x^2 - 6x^1 - 3   <====
        ((AbstractPolynomial) p4).setName("p4(x)");
        System.out.println(p4);

        Polynomial p5 = p4.getDerivative();   // 12x^2 + 6x^1 - 6   <====
        ((AbstractPolynomial) p5).setName("p5(x)");
        System.out.println(p5);

        Polynomial clone = new SinglyBasedPolynomial(p5);
        ((AbstractPolynomial) clone).setName("clone(x)");
        System.out.println(clone);

        Polynomial product = p1.multiplying(p2);
        System.out.println("product of p1(x) and p2(x) is     " + product);

        System.out.println("p5(0) = " + p5.evaluatedAt(0));
        System.out.println("p5(1) = " + p5.evaluatedAt(1));
    }
}
/*
p1(x) = 4x³ + 3x² + 1
p2(x) = -5x - 2
p3(x) = -4x
f(x) = 4x³ + 3x² - 5x - 1
p(x) = 4x³ + 3x² - 10x - 3
p4(x) = 4x³ + 3x² - 6x - 3
p5(x) = 12x² + 6x - 6
clone(x) = 12x² + 6x - 6
product of p1(x) and p2(x) is     f(x) = -20x⁴ - 23x³ - 6x² - 5x - 2
p5(0) = -6.0
p5(1) = 12.0
*/
