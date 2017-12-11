package net.fcpsschools._1685666._2._3_collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * NAME: Zhiyu Zhu
 * PERIOD: 1
 * DUE DATE: 2017/12/11 9:40
 * ## CONCERNS:
 * - Please use a better naming for methods to indicate if they are mutating the parameter or not.
 * ## PURPOSE:
 * - Use for-each loops or iterators, not regular for-loops
 * ## LEARNED:
 * - AbstractCollection#toString uses
 * - The ArrayList that Arrays.asList returns is not java.util.ArrayList
 * - Arrays.asList doesn't work when the array is primitive
 * ## CREDITS:
 * - JDK Source Code Authors
 */
public class IteratorLab_shell {
    public static void main(String[] args) {
        System.out.println("Iterator Lab\n");
        int[] rawNumbers = {-9, 4, 2, 5, -10, 6, -4, 24, 20, -28};
        for (int n : rawNumbers) System.out.print(n + " ");
        ArrayList<Integer> numbers = createNumbers(rawNumbers);
        System.out.println("\nArrayList: " + numbers); //Implicit Iterator!
        System.out.println("Count negative numbers: " + countNeg(numbers));
        System.out.println("Average: " + average(numbers));
        System.out.println("Replace negative numbers: " + replaceNeg(numbers));
        System.out.println("Delete zeros: " + deleteZero(numbers));
        String[] rawMovies = {"High_Noon", "High_Noon", "Star_Wars", "Tron", "Mary_Poppins",
                "Dr_No", "Dr_No", "Mary_Poppins", "High_Noon", "Tron"};
        ArrayList<String> movies = createMovies(rawMovies);
        System.out.println("Movies: " + movies);
        System.out.println("Movies: " + removeDupes(movies));
    }

    // pre: an array of just int values
    // post: return an ArrayList containing all the values
    public static ArrayList<Integer> createNumbers(int[] rawNumbers) {
        ArrayList<Integer> list = new ArrayList<>(rawNumbers.length);
        for (int v : rawNumbers) list.add(v);
        return list;
    }

    // pre: an array of just Strings
    // post: return an ArrayList containing all the Strings
    public static <T> ArrayList<T> createMovies(T[] rawWords) {
        return new ArrayList<>(Arrays.asList(rawWords));
    }

    // pre: ArrayList a is not empty and contains only Integer objects
    // post: return the number of negative values in the ArrayList a
    public static int countNeg(ArrayList<Integer> a) {
        int count = 0;
        for (int v : a) if (v < 0) count++;
        return count;
    }

    // pre: ArrayList a is not empty and contains only Integer objects
    // post: return the average of all values in the ArrayList a
    public static double average(ArrayList<Integer> a) {
        int sum = 0;
        for (int v : a) sum += v;
        return 0;
    }

    // pre: ArrayList a is not empty and contains only Integer objects
    // post: replaces all negative values with 0
    public static ArrayList<Integer> replaceNeg(ArrayList<Integer> a) {
        ListIterator<Integer> iterator = a.listIterator();
        while (iterator.hasNext())
            if (iterator.next() < 0)
                iterator.set(0);
        return a;
    }

    // pre: ArrayList a is not empty and contains only Integer objects
    // post: deletes all zeros in the ArrayList a
    public static ArrayList<Integer> deleteZero(ArrayList<Integer> a) {
        Iterator<Integer> iterator = a.iterator();
        while (iterator.hasNext())
            if (iterator.next() == 0)
                iterator.remove();
        return a;
    }

    // pre: ArrayList a is not empty and contains only String objects
    // post: return ArrayList without duplicate movie titles
    // strategy: start with an empty array and add movies as needed
    public static <T> ArrayList<T> removeDupes(ArrayList<T> a) {
        ArrayList<T> list = new ArrayList<>(a.size());
        for (T v : a)
            if (!list.contains(v))
                list.add(v);
        return list;
        /* // Set Implementation
        Set<T> set = new LinkedHashSet<>(a.size());
        set.addAll(a);
        return new ArrayList<>(set); */
    }
}
