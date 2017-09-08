package edu.fcps.eylau._2_oo_design._2_exception;/*
1. How to use "throws" to delay an exception from showing up and propagate
   the exception back to the calling method.
2. The "throw" command is different: it can be placed any where in a program.
2. 
*/

public class TestRunTimeException2 {
    public static void main(String[] args) {
        try {
            wait1();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("index out of bound");
        }
        // check if the program can get to this point
        System.out.println("after catch block");

        // can throw an Exception object any where in a program
        // this will terminate the program!
        throw new IndexOutOfBoundsException("Index out of bound");

    }  // end of main

    //
    public static void wait1() throws IndexOutOfBoundsException // propagate the Exception back to the calling program
    {
        int[] a = new int[3];
        a[6] = 12;
    }   // end of wait1
}  // end of TestRunTimeException2