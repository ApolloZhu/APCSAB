package net.fcpsschools._1685666._2.lab._1_stack;

import java.util.Scanner;

public class Infix_Shell {
    public static void main(String[] args) {
        System.out.println("Enter an infix expression, single digits");
        System.out.println("such as 1+2*3 or (1+2)*3");
        Scanner keyboard = new Scanner(System.in);//  (3*(4+5)-2)/5
        String s = keyboard.next();
        while (!s.equals("-1")) {
            System.out.println(s + "  -->  " + trans(s) + "  -->  " + Postfix.eval(trans(s)) + "\n");
            s = keyboard.next();
        }
    }

    public static String trans(String x) {
        return Infix.toPostfix(x);
    }
}
