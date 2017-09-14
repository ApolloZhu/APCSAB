package edu.fcps.eylau._3_linked_list;
/**
* Name:                       
* Period: 
* Name of the Lab: 
* Purpose of the Program: 
* Due Date: 
* Date Submitted: 
* What I learned: 
* How I feel about this lab: 
* What I wonder:  
* Credits:  
* Students whom I helped (to what extent):
*/
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ListNodeLinkedListLab
{

   private static class ListNode <E> 
   {    
      private E value;    
      private ListNode <E>  next; 
      public ListNode  (E  initValue, ListNode <E> initNext)    
      { 
         value = initValue; 
         next = initNext; 
      }  
      public E getValue()  
      { 
         return value; 
      }    
   
      public ListNode <E> getNext() 
      { 
         return next;  
      } 
   
      public void setValue(E theNewValue)
      { 
         value = theNewValue;
      }
   
      public void setNext(ListNode  <E> theNewNext)
      { 
         next = theNewNext; 
      }
   
   }  // end of ListNode

   public static void main(String [] args)
   {
      Scanner input = new Scanner (System.in);
      ListNode <Integer> h = new ListNode( 5, null);
      h= new ListNode(4, h);
      h= new ListNode(3, h);
      h= new ListNode(2, h);
      h= new ListNode(1, h);
        
      char option ;
      do
      {
         option = menu();
         if( option  == 'a')
         {
            System.out.println("list: ");
            printLinkedList(h);
         }	
         else if(option == 'b')
         {
            System.out.println("The List has atleast two element?");
            System.out.println(hasTwo(h));
         }	
         else if( option  =='c')
         {
            System.out.print("The size of the array is: ");
            System.out.println(size(h));
         }	
         else if( option  == 'd')
         {
            h = removeFirst(h);
            System.out.print("New list is: ");
            printLinkedList(h);
         }
         else  if( option  == 'e')
         {
            System.out.println("Enter number: ");
            int num = input.nextInt();
            add(h, new Integer(num));
            System.out.println("New list is: ");
            printLinkedList(h);
         }
         else if( option  == 'f')
         {
            h = reverseList(h);
            System.out.println("Reverse is: ");
            printLinkedList(h);
         }
         else if( option  == 'g')
         {
            h = rotate(h);
            System.out.println("rotated array is: ");
            printLinkedList(h);
         }
         else if( option  == 'h')
         {
            printLinkedList(h);
            System.out.println("\nmiddle node is: "+middleNode(h).getValue());
         }
         
         else if( option  == 'i')
         {
            h = removeLast(h);
            System.out.print("New list is: ");
            printLinkedList(h);
         }
      
      } while (option != 'z');
   
   }  // end of main
   
   public static void printLinkedList(ListNode <Integer> head)
   {
   
   }
   public static boolean hasTwo(ListNode  <Integer> head)
   {
      return false;
   
   }
   public static int size(ListNode <Integer> head)
   {
      return 0;
   }

   public static ListNode <Integer> removeFirst(ListNode <Integer> head)
   {	
      return null;
   }

   public static ListNode <Integer> removeLast(ListNode <Integer>  head)
   {	
      return null;
   }
   public static ListNode <Integer> remove(ListNode head, Integer value)
   {	
      return null;
   }

   public static void add(ListNode <Integer> head, Integer value)
   {
   
   }

   public static ListNode <Integer>  reverseList(ListNode <Integer> head)
   {
      return null;
   }

   public static ListNode <Integer> rotate(ListNode <Integer> head)
   {
      return null;
   }

   public static ListNode <Integer> middleNode(ListNode <Integer>head)
   {
      return null;
   }


   public static char menu()
   {
      Scanner input = new Scanner (System.in);
      System.out.println("\n====> What would you like to do?");
      System.out.println("a) Print list");
      System.out.println("b) Check if list has at least two nodes");
      System.out.println("c) Get size of the list");
      System.out.println("d) Remove first node");
      System.out.println("e) Add a node");
      System.out.println("f) Reverse");
      System.out.println("g) Rotate");
      System.out.println("h) Get middle node");
      System.out.println("i) Remove last node");
      System.out.println("z) Quit?");
      String choice = input.next();
      return choice.charAt(0);   
   }  // end of menu
}