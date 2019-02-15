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

package net.fcpsschools._1685666._3._3_set;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Apollo/Zhiyu Zhu/朱智语
 */
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