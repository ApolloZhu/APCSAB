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

package net.fcpsschools._1685666._3.lab._4_map;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Apollo/Zhiyu Zhu/朱智语
 * Date: 2018/3/11 23:59
 * Purpose: Learn how to create a map and its inverse
 * (its inverse may not be a 1-1 mapping; as such,
 * the value field for the reverse map is a collections class)
 * What I learned:
 * - HashMap might also be ordered, after a few trials.
 * How I feel about this lab:
 * - Looks a lot simpler with current version of Java.
 * I am wondering:
 * - How unlikely it is for a HashMap to be ordered.
 * - Where I can find an online platform that supports Java 9.
 */
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