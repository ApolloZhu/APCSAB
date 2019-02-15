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

package net.fcpsschools._1685666._3.lab._3_set;



import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Apollo/Zhiyu Zhu/朱智语
 * Date: 2018/3/2 23:59
 * What I learned:
 * - In Java, the `union` operation is called `retainAll`.
 * How I feel about this lab:
 * - OK.
 * What I wonder:
 * - I suspect this won't compile with Java 7.
 * Question:  If Java didn't implement Sets, how would you solve this programming problem?  Be creative!
 * 1. Reimplement set ourselves:
 *   Use a HashMap/TreeMap/Hashtable to put the characters as keys and a dummy object as value.
 *   Or use any other collection, add only if `contains` returns false.
 * 2. ASCII based LUT:
 *   Use a look up table of size ('~' - '!' + 1), a-z will be from location 'a' - '!' to 'z' - '!',
 *   A-Z will be from location `A` - '!' to 'Z' - '!', the rest will be "other punctuation marks".
 */
public class ZhiyuZhu_Period1_SetsOfLetters_shell {
    public static void main(String[] args) throws Throwable {
        Path path = Paths.get(ZhiyuZhu_Period1_SetsOfLetters_shell.class
                .getResource("declarationLast.txt")
                .getPath()
        );
        // need to create 3 sets of Character
        // lower case, upper case, punctuation marks
        Set<Character> lower = null, upper = null, other = null;
        // for each line read
        for (String line : Files.readAllLines(path)) {
            // I think this hash set will be ordered.
            Set<Character> l = new HashSet<>(26);
            Set<Character> u = new TreeSet<>();
            Set<Character> o = new TreeSet<>();
            // convert to a character array by using toCharArrays()
            // for each character in each line
            // put each to its corresponding set
            for (char c : line.toCharArray()) {
                // check if it is a letter (use Character.isLetter()
                if (Character.isLetter(c)) {
                    // AND lower case (use Character.isLowerCase()
                    if (Character.isLowerCase(c)) {
                        l.add(c);
                    } else { // upper case (use Character.isUpperCase()
                        u.add(c);
                    }
                } else o.add(c); // otherwise, it is a punctuation mark
            }
            System.out.println(line);
            System.out.println("Lower Case: " + l);
            System.out.println("Upper Case: " + u);
            System.out.println("Other: " + o);
            System.out.println();
            // Need to check the common characters in each group:
            // lower, upper, punctuation marks.
            lower = union(lower, l);
            upper = union(upper, u);
            other = union(other, o);
        }

        System.out.println("Common Lower Case: " + lower);
        System.out.println("Common Upper Case: " + upper);
        System.out.println("Common Other: " + other);
    }

    // You need to use an iterator to iterate over each set.
    // You might need to use the contains and remove methods of Set.
    private static <E> Set<E> union(Set<E> a, final Set<E> b) {
        if (null == a) return b;
        // a.retainAll(b); // Easy Approach
        // a.removeIf(e -> !b.contains(e)); // Java 8 Approach
        Iterator<E> iterator = a.iterator();
        while (iterator.hasNext()) {
            E next = iterator.next();
            if (!b.contains(next))
                iterator.remove();
        }
        return a;
    }
}
/*
We, therefore, the Representatives of the united States of
Lower Case: [a, d, e, f, h, i, n, o, p, r, s, t, u, v]
Upper Case: [R, S, W]
Other: [ , ,]

America, in General Congress, Assembled, appealing to the
Lower Case: [a, b, c, d, e, g, h, i, l, m, n, o, p, r, s, t]
Upper Case: [A, C, G]
Other: [ , ,]

Supreme Judge of the world for the rectitude of our intentions,
Lower Case: [c, d, e, f, g, h, i, l, m, n, o, p, r, s, t, u, w]
Upper Case: [J, S]
Other: [ , ,]

do, in the Name, and by the Authority of the good People of
Lower Case: [a, b, d, e, f, g, h, i, l, m, n, o, p, r, t, u, y]
Upper Case: [A, N, P]
Other: [ , ,]

these Colonies, solemnly publish and declare, That these United
Lower Case: [a, b, c, d, e, h, i, l, m, n, o, p, r, s, t, u, y]
Upper Case: [C, T, U]
Other: [ , ,]

Colonies are, and of Right ought to be Free and Independent
Lower Case: [a, b, d, e, f, g, h, i, l, n, o, p, r, s, t, u]
Upper Case: [C, F, I, R]
Other: [ , ,]

States; that they are Absolved from all Allegiance to the
Lower Case: [a, b, c, d, e, f, g, h, i, l, m, n, o, r, s, t, v, y]
Upper Case: [A, S]
Other: [ , ;]

British Crown, and that all political connection between them
Lower Case: [a, b, c, d, e, h, i, l, m, n, o, p, r, s, t, w]
Upper Case: [B, C]
Other: [ , ,]

and the State of Great Britain, is and ought to be totally
Lower Case: [a, b, d, e, f, g, h, i, l, n, o, r, s, t, u, y]
Upper Case: [B, G, S]
Other: [ , ,]

dissolved; and that as Free and Independent States, they have
Lower Case: [a, d, e, h, i, l, n, o, p, r, s, t, v, y]
Upper Case: [F, I, S]
Other: [ , ,, ;]

full Power to levy War, conclude Peace, contract Alliances,
Lower Case: [a, c, d, e, f, i, l, n, o, r, s, t, u, v, w, y]
Upper Case: [A, P, W]
Other: [ , ,]

establish Commerce, and to do all other Acts and Things which
Lower Case: [a, b, c, d, e, g, h, i, l, m, n, o, r, s, t, w]
Upper Case: [A, C, T]
Other: [ , ,]

Independent States may of right do. And for the support of this
Lower Case: [a, d, e, f, g, h, i, m, n, o, p, r, s, t, u, y]
Upper Case: [A, I, S]
Other: [ , .]

Declaration, with a firm reliance on the protection of divine
Lower Case: [a, c, d, e, f, h, i, l, m, n, o, p, r, t, v, w]
Upper Case: [D]
Other: [ , ,]

Providence, we mutually pledge to each other our Lives, our
Lower Case: [a, c, d, e, g, h, i, l, m, n, o, p, r, s, t, u, v, w, y]
Upper Case: [L, P]
Other: [ , ,]

Fortunes and our sacred Honor.
Lower Case: [a, c, d, e, n, o, r, s, t, u]
Upper Case: [F, H]
Other: [ , .]

Common Lower Case: [d, e, n, o, r, t]
Common Upper Case: []
Common Other: [ ]
*/
