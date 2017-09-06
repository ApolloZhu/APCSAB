package edu.fcps.eylau._2_oo_design._2_exception;/*
1. Cannot have a try block without a catch block
2. Can have multiple catch blocks after the try block
3. Use a catch block to "capture" the exception  allows a program from crashing

*/
// check the run time error
// catch the error using try-catch blocks
import java.io.*;
public class TestRunTimeException
{
   public static void main (String [] args)
   {
      try
      {
         int [] a = new int [3];
         a[3] = 12;
      }
      catch (IndexOutOfBoundsException e)  
      {
         System.out.println ("index out of bound");
      }  // end of catch
      System.out.println ("after catch block");
   }   // end of main
}  // end of TestRunTimeException