package net.fcpsschools._1685666._3.lab._2_hash;

import java.util.Scanner;

/**
 * Name: Zhiyu Zhu
 * Date: 2018/02/20 23:59:59
 * <p>
 * What I learned:
 * -
 * How I feel about this lab:
 * -
 * What I wonder:
 * -
 * Credits:
 * -
 */
public class HashCode_shell {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter the size of the array: ");
        int arrayLength = keyboard.nextInt(); // 20
        keyboard.nextLine();
        System.out.println("Use Linear Probing? (Y/N): ");
        boolean useProbing = keyboard.nextLine().equals("Y");
        Hashtable table = Hashtable.init(arrayLength, useProbing);
        System.out.println(table);

        System.out.print("\nEnter the number of items: ");
        int numItems = keyboard.nextInt(); // 15
        keyboard.nextLine();
        System.out.println("\nThe Load Factor is " + (double) numItems / arrayLength);
        for (int i = 0; i < numItems; i++) {
            System.out.println("\nNext item: ");
            table.add(keyboard.nextLine());
        }

        System.out.println();
        System.out.print("Search for: ");
        String key = keyboard.nextLine();
        boolean found = table.contains(key);
        System.out.println(key + (found ? " " : " NOT ") + "found");
        System.out.println(table);
    }
}
