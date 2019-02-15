// All rights reserved.

package net.fcpsschools._1685666._1._2_oo_design._1_class;

/**
 * @author May & Sirena
 */
public class ProtectedABC {
    static class A {
        protected int x;

        public A setX(int y) {
            x = y;
            return this;
        }

        @Override
        public String toString() {
            return "" + x;
        }
    }

    static class B extends A {
        private int x = 1;

        @Override
        public String toString() {
            return super.toString() + " " + x;
        }
    }

    static class C extends B {
        private int x = 2;

        @Override
        public String toString() {
            return super.toString() + " " + x;
        }
    }

    public static void main(String[] args) {
        System.out.println(new C().setX(3));
    }
}
