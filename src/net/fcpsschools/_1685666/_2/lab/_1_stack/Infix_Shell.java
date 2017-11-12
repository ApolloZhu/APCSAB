package net.fcpsschools._1685666._2.lab._1_stack;

import java.util.Scanner;

public class Infix_Shell {
    public static void main(String[] args) {
        Operators.printInfo();
        Scanner keyboard = new Scanner(System.in);
        String s = null, postfix;
        do {
            try {
                if ((postfix = Infix.toPostfix(s)) != null)
                    System.out.println(s + "  -->  " + postfix +
                            "  -->  " + Postfix.eval(postfix));
            } catch (Exception e) {
                System.err.println(e.getLocalizedMessage());
            }
            System.out.println("\nEnter an infix expression");
            System.out.println("such as 1+2*3 or (1+2)*3");
        } while (!(s = keyboard.nextLine()).equals("-1"));
    }
}
