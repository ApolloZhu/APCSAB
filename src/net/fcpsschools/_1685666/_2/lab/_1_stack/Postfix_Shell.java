package net.fcpsschools._1685666._2.lab._1_stack;

import java.util.Scanner;

public class Postfix_Shell {
    public static void main(String[] args) {
        System.out.println("Enter a valid postfix expression (tokens separated by spaces),");
        System.out.println("such as 3 5 * 1 +");
        Scanner keyboard = new Scanner(System.in);
        String s;
        while (!(s = keyboard.nextLine()).equals("-1")) {
            System.out.println(s + "  --->  " + eval(s) + "\n");
            // System.out.println((s = "354*+7*") + " = " + eval(s) + "\n");
            // System.out.println((s = "82-") + " = " + eval(s) + "\n");
            // System.out.println((s = "82/") + " = " + eval(s) + "\n");
        }
    }

    public static int eval(String x) {
        return (int) Postfix.eval(x);
    }
}