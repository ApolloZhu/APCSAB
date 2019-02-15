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

package net.fcpsschools._1685666._2.lab._2_queue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * @author Apollo/Zhiyu Zhu/朱智语, Pd. 1
 * How I feel: the delete should be FIFO, not assigned.
 */
public class PrintQueue_Shell {
    private static final Scanner in = new Scanner(System.in);
    private static final Queue<String> q = new LinkedList<>();
    private static int jobNumber = 100;

    public static void main(String[] args) throws Exception {
        String str, prompt = "Add, Print, Delete, eXit: ";
        System.out.print(prompt);
        while (!(str = in.next()).equals("X")) {
            if (str.equals("A")) add();
            else if (str.equals("P")) printJob();
            else if (str.equals("D")) delete();
            else System.out.println("invalid command");
            printQueue();
            System.out.print(prompt);
        }
    }

    public static void add() {
        q.add(String.valueOf(jobNumber++));
    }

    public static void printJob() {
        // Do nothing
    }

    // Although it is required to behave like this
    // I don't feel like this is an queue behavior
    public static void delete() {
        System.out.print("Enter job number: ");
        String next = in.next();
        if (q.remove(next)) System.out.println("Deleted job " + next);
        else System.out.println("Error--job does not exist");
    }

    public static void printQueue() {
        System.out.println(q);
    }
}

/*
Add, Print, Delete, eXit: A
[100]
Add, Print, Delete, eXit: A
[100, 101]
Add, Print, Delete, eXit: A
[100, 101, 102]
Add, Print, Delete, eXit: P
[100, 101, 102]
Add, Print, Delete, eXit: A
[100, 101, 102, 103]
Add, Print, Delete, eXit: D
Enter job number: 105
Error--job does not exist
[100, 101, 102, 103]
Add, Print, Delete, eXit: D
Enter job number: 102
Deleted job 102
[100, 101, 103]
Add, Print, Delete, eXit: X
*/
