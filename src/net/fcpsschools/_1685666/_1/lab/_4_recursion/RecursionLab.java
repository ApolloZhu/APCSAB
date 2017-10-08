package net.fcpsschools._1685666._1.lab._4_recursion;

import java.util.Scanner;

public class RecursionLab {
    /**
     * @param satisfied
     * @param message
     * @return
     */
    private static boolean precondition(boolean satisfied, String message) {
        if (!satisfied) {
            System.out.println(message);
            return false;
        }
        return true;
    }

    // Pre: c is a lower case letter
    // Post: all lower case letters a-char c are printed
    public static void letters(char c) {
        if (c > 'a') letters((char) (c - 1));
        System.out.print(c);
    }

    // Post: returns number of times two can go into x
    public static int twos(/*in*/ int x) {
        return x != 0 && x % 2 == 0 ? 1 + twos(x / 2) : 0;
    }

    // Post: returns if x is a power of 3
    public static boolean /*is*/powerof3(int x) {
        return x / 3 == 1 || x != 0 && x % 3 == 0 && powerof3(x / 3);
    }

    // Post: returns String of x reversed
    // FIXME: Preceding Zeros
    public static String reverse(long x) {
        return x < 0 ? "-" + reverse(-x) // Handle negative
                : x % 10 + (x > 9 ? reverse(x / 10) : "");
    }

    // Post: Prints x in base 5
    public static void base5(int x) {
        if (x < 0) {
            System.out.print("-");
            base5(-x);
        } else {
            int next = x / 5;
            if (next != 0) base5(next);
            System.out.print(x % 5);
        }
    }

    // Post: Prints x with commas
    public static void printWithCommas(long x) {
        if (x < 0) {
            System.out.print("-");
            printWithCommas(-x);
        } else if (x > 999) {
            printWithCommas(x / 1000);
            System.out.print(",");
            System.out.printf("%03d", x % 1000);
        } else {
            System.out.print(x % 1000);
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int choice;
        do {
            System.out.print("\n\n" +
                    "1) Letters\n" +
                    "2) Twos\n" +
                    "3) Power Of 3\n" +
                    "4) Reverse\n" +
                    "5) Base 5\n" +
                    "6) Print With Commas\n" +
                    "7) Exit\n" +
                    ">> ");
            choice = scan.nextInt();
            if (choice == 1) {
                System.out.print("Enter a letter: ");
                char c = scan.next().charAt(0);
                if (precondition(c >= 'a' && c <= 'z',
                        "That letter is not valid"))
                    letters(c);
            } else {
                System.out.print("Enter a number: ");
                if (choice == 2)
                    System.out.println(twos(scan.nextInt()));
                else if (choice == 3)
                    System.out.println(powerof3(scan.nextInt()));
                else if (choice == 4)
                    System.out.println(reverse(scan.nextLong()));
                else if (choice == 5)
                    base5(scan.nextInt());
                else if (choice == 6)
                    printWithCommas(scan.nextInt());
            }
        } while (choice != 7);
    }
}
