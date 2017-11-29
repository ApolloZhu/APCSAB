package net.fcpsschools._1685666._2.lab._2_queue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * @author Zhiyu Zhu, Pd. 1
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
