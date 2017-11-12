package net.fcpsschools._1685666._2.lab._1_stack;

import java.util.Scanner;

public class Postfix_Shell {
    public static void main(String[] args) {
        System.out.println("Enter a valid postfix expression (tokens separated by spaces),");
        System.out.println("such as \"3 5 * 1 +\"; Enter -1 to exit.");
        Scanner keyboard = new Scanner(System.in);
        String s;
        while (!(s = keyboard.nextLine()).equals("-1")) {
            System.out.println(s + "  --->  " + Postfix.eval(s) + "\n");
            // System.out.println((s = "3 5 4 * + 7 *") + " = " + Postfix.eval(s) + "\n");
            // System.out.println((s = "8 2 -") + " = " + Postfix.eval(s) + "\n");
            // System.out.println((s = "8 2 /") + " = " + Postfix.eval(s) + "\n");
        }
    }
}
