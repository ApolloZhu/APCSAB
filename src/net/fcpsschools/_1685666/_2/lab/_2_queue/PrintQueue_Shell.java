package net.fcpsschools._1685666._2.lab._2_queue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class PrintQueue_Shell {
    private static final Scanner in = new Scanner(System.in);
    private static final Queue<String> q = new LinkedList<>();
    private static int jobNumber = 100;

    public static void main(String[] args) throws Exception {
        String prompt = "Add, Print, Delete, eXit: ";
        System.out.print(prompt);
        String str = in.next();
        while (!str.equals("X")) {
            if (str.equals("A"))
                add();
            else if (str.equals("P"))
                printJob();
            else if (str.equals("D"))
                delete();
            else
                System.out.println("invalid command");
            printQueue();
            System.out.print(prompt);
            str = in.next();
        }
        in.close();
    }

    public static void add() {
        q.add(String.valueOf(jobNumber++));
    }

    public static void printJob() {
    }

    public static void delete() {
        System.out.print("Enter job number: ");
        String next = in.next();
        if (q.remove(next))
            System.out.println("Deleted job " + next);
        else
            System.out.println("Error--job does not exist");
    }

    public static void printQueue() {
        System.out.println(q);
    }
}
