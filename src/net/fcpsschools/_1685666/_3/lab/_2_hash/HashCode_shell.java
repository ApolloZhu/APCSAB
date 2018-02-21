package net.fcpsschools._1685666._3.lab._2_hash;

import java.util.Scanner;

import static net.fcpsschools._1685666._3.lab._2_hash.Hashtable.CollisionResolutionMethod.*;

/**
 * Name: Zhiyu Zhu
 * Date: 2018/02/20 23:59:59
 * <p>
 * What I learned:
 * - Quadratic probing has special requirements, see {@link QuadraticHashTable}.
 * How I feel about this lab:
 * - Looks better to have an abstract super class.
 * - Maybe I can introduce a new abstract method called "shouldRehash".
 * What I wonder:
 * - Do people really use quadratic probing?
 * - What's the most efficient way to find the next prime size?
 * - When to rehash a linked list based hash table?
 * Credits:
 * - Andrew Li and google "quadratic probing half prime"
 */
public class HashCode_shell {
    private static Hashtable.CollisionResolutionMethod[] methods
            = {LINEAR_PROBING, QUADRATIC_PROBING, CHAINING};

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter the size of the array: ");
        int arrayLength = keyboard.nextInt(); // 20
        keyboard.nextLine();
        System.out.print("Resolve collision using:\n" +
                "0. Linear Probing\n" +
                "1. Quadratic Probing\n" +
                "2. Chaining\n" +
                ">_ ");
        int strategy = Integer.parseInt(keyboard.nextLine());
        Hashtable table = Hashtable.init(arrayLength, methods[strategy]);
        System.out.print("Enter the number of items: ");
        int numItems = keyboard.nextInt(); // 15
        keyboard.nextLine();
        System.out.println("The Load Factor is " + (double) numItems / table.size());
        for (int i = 0; i < numItems; i++) {
            System.out.print("Next item: ");
            table.add(keyboard.nextLine());
        }
        System.out.println(table);
        System.out.print("Search for: ");
        String key = keyboard.nextLine();
        boolean found = table.contains(key);
        System.out.println(key + " is " + (found ? "" : "NOT ") + "found");
    }
}

/*
Enter the size of the array: 4
Resolve collision using:
0. Linear Probing
1. Quadratic Probing
2. Chaining
>_ 0
Enter the number of items: 3
The Load Factor is 0.75
Next item: 1
Next item: 2
Next item: 5
[null, 1, 2, 5]
Search for: 5
5 is found

Enter the size of the array: 4
Resolve collision using:
0. Linear Probing
1. Quadratic Probing
2. Chaining
>_ 0
Enter the number of items: 3
The Load Factor is 0.75
Next item: 5
Next item: 2
Next item: 1
[null, 5, 2, 1]
Search for: 5
5 is found

Enter the size of the array: 4
Resolve collision using:
0. Linear Probing
1. Quadratic Probing
2. Chaining
>_ 0
Enter the number of items: 3
The Load Factor is 0.75
Next item: 5
Next item: 2
Next item: 1
[null, 5, 2, 1]
Search for: 3
3 is NOT found
*/

/*
Enter the size of the array: 4
Resolve collision using:
0. Linear Probing
1. Quadratic Probing
2. Chaining
>_ 1
Enter the number of items: 3
The Load Factor is 0.6
Next item: 1
Next item: 2
Next item: 6
[2, null, null, 6, 1]
Search for: 6
6 is found

Enter the size of the array: 4
Resolve collision using:
0. Linear Probing
1. Quadratic Probing
2. Chaining
>_ 1
Enter the number of items: 3
The Load Factor is 0.6
Next item: 6
Next item: 2
Next item: 1
[2, null, null, 1, 6]
Search for: 6
6 is found

Enter the size of the array: 4
Resolve collision using:
0. Linear Probing
1. Quadratic Probing
2. Chaining
>_ 1
Enter the number of items: 3
The Load Factor is 0.6
Next item: 6
Next item: 2
Next item: 1
[2, null, null, 1, 6]
Search for: 3
3 is NOT found
*/

/*
Enter the size of the array: 4
Resolve collision using:
0. Linear Probing
1. Quadratic Probing
2. Chaining
>_ 2
Enter the number of items: 3
The Load Factor is 0.75
Next item: 1
Next item: 2
Next item: 5
[null, 5~>1, 2, null]
Search for: 5
5 is found

Enter the size of the array: 4
Resolve collision using:
0. Linear Probing
1. Quadratic Probing
2. Chaining
>_ 2
Enter the number of items: 3
The Load Factor is 0.75
Next item: 5
Next item: 2
Next item: 1
[null, 1~>5, 2, null]
Search for: 5
5 is found

Enter the size of the array: 4
Resolve collision using:
0. Linear Probing
1. Quadratic Probing
2. Chaining
>_ 2
Enter the number of items: 3
The Load Factor is 0.75
Next item: 5
Next item: 2
Next item: 1
[null, 1~>5, 2, null]
Search for: 3
3 is NOT found
*/
