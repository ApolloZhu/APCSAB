package net.fcpsschools._1685666._3._3_set;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class SetPractice {
    public static void main(String[] args) {
        Set<String> s = new HashSet<>();
        s.add("Mary");
        s.add("Joan");
        //duplicate!
        s.add("Mary");
        s.add("Dennis");
        s.add("Bob");
        s.add("MaryAnn");
        s.add("Zoe");
        System.out.println("Size:  " + s.size());
        Iterator<String> it = s.iterator();
        while (it.hasNext())
            System.out.print((String) it.next() + " ");
        System.out.println();

        //from HashSet to TreeSet
        Set<String> t = new TreeSet<>(s);
        it = t.iterator();
        while (it.hasNext())
            System.out.print(it.next() + " ");
        System.out.println();
        //print any Collection--wow!
        System.out.println(s);
        System.out.println(t);
    }
}
/******************

 Size:  3
 Joan Mary Dennis
 Dennis Joan Mary
 [Joan, Mary, Dennis]
 [Dennis, Joan, Mary]

 ************************/