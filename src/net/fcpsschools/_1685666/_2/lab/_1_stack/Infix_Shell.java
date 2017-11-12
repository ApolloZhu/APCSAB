package net.fcpsschools._1685666._2.lab._1_stack;

import java.util.Scanner;

public class Infix_Shell {
    public static void main(String[] args) {
        System.out.println("Enter an infix expression, single digits");
        System.out.println("such as 1+2*3 or (1+2)*3");
        Scanner keyboard = new Scanner(System.in);//  (3*(4+5)-2)/5
        String s = keyboard.next(), postfix;
        while (!s.equals("-1")) {
            postfix = Infix.toPostfix(s);
            System.out.println(s + "  -->  " + postfix + "  -->  " + Postfix.eval(postfix) + "\n");
            s = keyboard.next();
        }
    }
}
