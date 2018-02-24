package net.fcpsschools._1685666._3.lab._2_hash;

import java.util.*;

/**
 * Name: Zhiyu Zhu
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
