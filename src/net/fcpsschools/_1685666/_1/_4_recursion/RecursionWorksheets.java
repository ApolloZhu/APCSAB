package net.fcpsschools._1685666._1._4_recursion;

/**
 * @author ApolloZhu, Pd. 1
 */
public class RecursionWorksheets {
    public static void main(String[] args) {
        System.out.println(iterateLog2(4));
        System.out.println(iterateLog2(5));
        System.out.println(recurLog2(4));
        System.out.println(recurLog2(5));
        System.out.println(iterateCountDigits(1));
        System.out.println(iterateCountDigits(9));
        System.out.println(iterateCountDigits(10));
        System.out.println(recurCountDigits(1));
        System.out.println(recurCountDigits(9));
        System.out.println(recurCountDigits(10));
    }
    static int iterateLog2(int n) {
        int c = 0;
        while (n > 1) {
            n /= 2;
            c++;
        }
        return c;
    }
    static int recurLog2(int n) {
        return n < 2 ? 0 : 1 + recurLog2(n / 2);
    }
    static int iterateCountDigits(int n) {
        int c = 1;
        while (n > 9) {
            n /= 10;
            c++;
        }
        return c;
    }
    static int recurCountDigits(int n) {
        return n < 10 ? 1 : 1 + recurCountDigits(n / 10);
    }
}
