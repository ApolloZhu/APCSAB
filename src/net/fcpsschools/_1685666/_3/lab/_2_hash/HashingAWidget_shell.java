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

package net.fcpsschools._1685666._3.lab._2_hash;

import java.util.*;

/**
 * @author Apollo/Zhiyu Zhu/朱智语
 * Period: 1
 * Date: Not due
 * What I learned:
 * How I feel about this lab:
 * What I wonder:
 */
public class HashingAWidget_shell {
    public static void main(String[] args) {
        Set<Widget> tSet = new TreeSet<>();
        Set<Widget> hSet = new HashSet<>();

        Widget a = new Widget(2, 3);   //same or different?
        Widget b = new Widget(2, 3);
        Widget c = new Widget(2, 3);
        // c = b;

        tSet.add(a);
        tSet.add(b);
        tSet.add(c);

        hSet.add(a);
        hSet.add(b);
        hSet.add(c);

        System.out.println(a.hashCode() + " " + b.hashCode() + " " + c.hashCode());

        System.out.println("TreeSet:  " + tSet);
        System.out.println("HashSet:  " + hSet);
    }
}

/***************************************
 Modify the Widget class so that it hashes on its
 values, not on its address.  Be sure that compareTo(),
 equals(Object) and hashCode() agree with each other.
 ****************************************/
class Widget implements Comparable<Widget> {
    private int myPounds, myOunces;

    public Widget() {
        this(0);
    }

    public Widget(int x) {
        this(x, 0);
    }

    public Widget(Widget arg) {
        this(arg.getPounds(), arg.getOunces());
    }

    public Widget(int x, int y) {
        setPounds(x);
        setOunces(y);
    }

    public int getPounds() {
        return myPounds;
    }

    public int getOunces() {
        return myOunces;
    }

    public void setPounds(int x) {
        myPounds = x;
    }

    public void setOunces(int x) {
        myPounds += x / 16;
        myOunces = x % 16;
    }

    public int compareTo(Widget w) {
        return getPounds() * 16 + getOunces() -
                w.getPounds() * 16 - w.getOunces();
    }

    public boolean equals(Widget arg) {
        return getPounds() == arg.getPounds() &&
                getOunces() == arg.getOunces();
    }

    public String toString() {
        return "Widget of " + getPounds() + " pounds and " + getOunces() + " ounces";
    }

    public boolean equals(Object arg) {
        if (null == arg) return false;
        if (arg instanceof Widget) {
            return this.equals((Widget) arg);
        } else return false;
    }

    public int hashCode() {
        return toString().hashCode();
    }
}
