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

package net.fcpsschools._1685666._3.lab._2_hash;

import java.util.Scanner;

import static net.fcpsschools._1685666._3.lab._2_hash.ZhiyuZhu_Period1_Hashtable.CollisionResolutionMethod.*;

/**
 * @author Apollo/Zhiyu Zhu/朱智语
 * Date: 2018/02/20 23:59:59
 * <p>
 * What I learned:
 * - Quadratic probing has special requirements, see {@link ZhiyuZhu_Period1_QuadraticHashTable}.
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
public class ZhiyuZhu_Period1_HashCode_shell {
    private static ZhiyuZhu_Period1_Hashtable.CollisionResolutionMethod[] methods
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
        ZhiyuZhu_Period1_Hashtable table = ZhiyuZhu_Period1_Hashtable.init(arrayLength, methods[strategy]);
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
