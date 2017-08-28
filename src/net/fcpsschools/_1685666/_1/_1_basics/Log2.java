package net.fcpsschools._1685666._1._1_basics;

// C-1.19
public class Log2 {
    public static int log(int num) {
        int count = 0;
        double number = num;
        while (number > 2) {
            number /= 2;
            count++;
        }
        return count;
    }

    public static void main(String[] args) {
        for (int i = -1; i <= 17; i++)
            System.out.printf("%2d: %d\n", i, log(i));
    }
}
