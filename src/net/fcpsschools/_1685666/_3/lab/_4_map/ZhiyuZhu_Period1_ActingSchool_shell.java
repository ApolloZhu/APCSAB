package net.fcpsschools._1685666._3.lab._4_map;
// Name: Zhiyu Zhu
// Date: 2018/3/11 23:59
// Purpose: Learn how to create a map and its inverse
// (its inverse may not be a 1-1 mapping; as such,
// the value field for the reverse map is a collections class)
// What I learned:
// - HashMap might also be ordered, after a few trials.
// How I feel about this lab:
// - Looks a lot simpler with current version of Java.
// I am wondering:
// - How unlikely it is for a HashMap to be ordered.
// - Where I can find an online platform that supports Java 9.

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ZhiyuZhu_Period1_ActingSchool_shell {
    public static void main(String[] args) {
        // Not ordered map, made with Java 9
        Map<String, String> sGrades = Map.of(
                "Jack Nicholson", "A-",
                "Humphrey Bogart", "A+",
                "Audrey Hepburn", "A",
                "Meryl Streep", "A-",
                "Jimmy Stewart", "A"
        );
        // ordered:
        Map<String, List<String>> reversed = new TreeMap<>();

        // display initial data
        for (String name : sGrades.keySet()) {
            String grade = sGrades.get(name);
            System.out.println(name + " (" + grade + ")");
            // reverse the map
            if (!reversed.containsKey(grade))
                // put an empty list there to append to
                reversed.put(grade, new LinkedList<>());
            reversed.get(grade).add(name);
        }
        System.out.println();
        // display the reversed map, using Java 8
        reversed.forEach(/*pair of*/(grade, nameList) ->
                System.out.println(grade + ": " + nameList)
        );
    }
}

/**********************
 Audrey Hepburn (A)
 Humphrey Bogart (A+)
 Jack Nicholson (A-)
 Jimmy Stewart (A)
 Meryl Streep (A-)

 A: [Audrey Hepburn, Jimmy Stewart]
 A+: [Humphrey Bogart]
 A-: [Jack Nicholson, Meryl Streep]

 *********************/