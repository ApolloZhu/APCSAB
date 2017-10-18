package net.fcpsschools._1685666._1._4_recursion;

/**
 * @author ApolloZhu, Pd. 1
 */
public class RecursionReview {
    public static void main(String[] args) {
        oddDigit("", 3);
    }

    public static void oddDigit(String per, int digit) {
        if (digit == 0) System.out.println(per);
        else for (int i = 1; i < 10; i += 2)
            oddDigit(per + i, digit - 1);
    }
}
