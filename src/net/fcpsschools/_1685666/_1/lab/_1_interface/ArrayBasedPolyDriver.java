package net.fcpsschools._1685666._1.lab._1_interface;

public class ArrayBasedPolyDriver {
    public static void main(String[] args) {
        double[] c = {4, 3, 0, 1};
        double[] c1 = {-5, -2};

        Polynomial p1 = new ArrayBasedPoly("p1(x)", c);
        System.out.println(p1);

        Polynomial p2 = new ArrayBasedPoly(c1);
        ((ArrayBasedPoly) p2).setName("p2(x)");
        System.out.println(p2);

        Polynomial p3 = new ArrayBasedPoly("p3(x)", -4, 1);
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
p3(x) = -4x + 1
p(x) = 4x³ + 3x² - 10x - 3
p4(x) = 4x³ + 3x² - 6x - 4
p5(x) = 12x² + 6x - 6
p5(0) = -6.0
p5(1) = 12.0
*/
