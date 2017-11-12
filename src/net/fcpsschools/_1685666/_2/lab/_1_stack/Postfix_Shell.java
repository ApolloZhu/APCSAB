package net.fcpsschools._1685666._2.lab._1_stack;

import java.util.Scanner;

public class Postfix_Shell {
    public static void main(String[] args) {
        Operators.printInfo();
        Scanner keyboard = new Scanner(System.in);
        String s = null;
        do {
            try {
                if (s != null) System.out.println(s + "  --->  " + Postfix.eval(s));
            } catch (Exception e) {
                System.err.println(e.getLocalizedMessage());
            }
            System.out.println("\nEnter a valid postfix expression (tokens separated by spaces),");
            System.out.println("such as \"3 5 * 1 +\"; Enter -1 to exit.");
        } while (!(s = keyboard.nextLine()).equals("-1"));
    }
}
