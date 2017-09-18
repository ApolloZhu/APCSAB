package net.fcpsschools._1685666._1.lab._2_linked_list;

import net.fcpsschools._1685666._1.lab._1_interface.Polynomial;

// test client
public class SinglyBasedPolynomialDriver {
    public static void main(String[] args) {

        SinglyBasedPolynomial p1 = new SinglyBasedPolynomial();  // 4x^3 + 3x^2 + 1
        System.out.println("p1(x) =     " + p1);

        Polynomial p2 = new SinglyBasedPolynomial();   // - 5x - 2
        System.out.println("p2(x) =     " + p2);                 // p2(x) = - 5x^1 - 2

        Polynomial p3 = new SinglyBasedPolynomial();  // coeff, exp = -4x
        System.out.println("p3(x) =     " + p3);

        Polynomial p = p1.adding(p2).adding(p2);   // 4x^3 + 3x^2 - 10x - 3
        System.out.println("p(x) =     " + p);       // p(x) = 4x^3 + 3x^2 - 10x^1 - 3

        Polynomial p4 = p.subtracting(p3);   // 4x^3 + 3x^2 - 6x^1 - 3   <====
        System.out.println("p4(x) =     " + p4);

        Polynomial p5 = p4.getDerivative();   // 12x^2 + 6x^1 - 6   <====
        System.out.println("p5(x) =     " + p5);

        Polynomial clone = new SinglyBasedPolynomial(p5);
        System.out.println("clone(x) =     " + clone);

        Polynomial product = p1.multiplying(p2);
        System.out.println("product of p1(x) and p2(x) is     " + product);

        System.out.println("p5(0) = " + p5.evaluatedAt(0));
        System.out.println("p5(1) = " + p5.evaluatedAt(1));
    }
}

