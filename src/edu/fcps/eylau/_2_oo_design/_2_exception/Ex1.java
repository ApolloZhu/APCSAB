package edu.fcps.eylau._2_oo_design._2_exception;

import edu.fcps.eylau._2_oo_design._2_exception.MyException;

public class Ex1
{
   public static void first() // throws MyException
   {
      second();
       /* try {
            second();
         }
      catch (MyException e)  {
            System.out.println("Caught MyException " + e);
         }  */
   } // first

   public static void second()  //throws MyException
   {
      try {
         third ();
      }
      catch (MyException e)  {
         System.out.println("Caught MyException " + e);
      }
      //third();
   }
   
   public static void third() throws MyException
   {
      throw new MyException ("yours");
   }
   
   public static void main (String [] args) //throws MyException
   {
      first();
      String  x = (String) new Object();
   }
}  // Ex1