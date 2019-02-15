/*
MIT License

Copyright (c) 2017-2019 Apollo/Zhiyu Zhu/朱智语 <public-apollonian@outlook.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package net.fcpsschools._1685666._1.lab._4_recursion;

import java.util.Scanner;

/**
 * @author Apollo/Zhiyu Zhu/朱智语
 */
public class RecursionLab {
    /**
     * Check if a certain precondition is met.
     *
     * @param satisfied precondition to check.
     * @param message   message to display if precondition not met.
     * @return if precondition is met.
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
    public static String reverse(long x) {
        return x != 0 && x % 10 == 0
                // Trailing/Preceding 0s
                ? reverse(x / 10)
                : reverseHelper(x);
    }

    /**
     * Reverses and returns a String with digits of a number reversed.
     * Has zeros in front if x is a multiple of 10.
     *
     * @param x the number to reverse.
     * @return the String containing digits in x reversed.
     */
    private static String reverseHelper(long x) {
        return x < 0 ? "-" + reverseHelper(-x) // Handle negative
                : x % 10 + (x > 9 ? reverseHelper(x / 10) : "");
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
            System.out.format(",%03d", x % 1000);
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
                c = Character.toLowerCase(c);
                if (precondition(c >= 'a' && c <= 'z',
                        "That letter is not valid"))
                    letters(c);
            } else {
                if (choice > 1 && choice < 7)
                    System.out.print("Enter a number: ");
                if (choice == 2)
                    System.out.println(twos(scan.nextInt()));
                else if (choice == 3) {
                    int n = scan.nextInt();
                    System.out.println(n + " is"
                            + (powerof3(n) ? "" : " not")
                            + " a power of 3.");
                } else if (choice == 4)
                    System.out.println(reverse(scan.nextLong()));
                else if (choice == 5)
                    base5(scan.nextInt());
                else if (choice == 6)
                    printWithCommas(scan.nextInt());
            }
        } while (choice != 7);
    }
}

/*
1) Letters
2) Twos
3) Power Of 3
4) Reverse
5) Base 5
6) Print With Commas
7) Exit
>> 1
Enter a letter: a
a

>> 1
Enter a letter: z
abcdefghijklmnopqrstuvwxyz

>> 1
Enter a letter: 0
That letter is not valid

>> 2
Enter a number: -12
2

>> 3
Enter a number: 81
81 is a power of 3.

>> 3
Enter a number: 1
1 is not a power of 3.

>> 4
Enter a number: -12
-21

>> 4
Enter a number: 123456
654321

>> 4
Enter a number: 10230
3201

>> 5
Enter a number: 136
1021

>> 5
Enter a number: -5
-10

>> 6
Enter a number: 12045670
12,045,670

>> 6
Enter a number: 1
1

>> 6
Enter a number: -1000
-1,000

>> 7

Process finished with exit code 0
 */
