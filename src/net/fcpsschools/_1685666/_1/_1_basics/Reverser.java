package net.fcpsschools._1685666._1._1_basics;

import java.util.Scanner;

/**
 * P-1.26
 * <p>
 * # Test Instruction
 * javac Reverser.java
 * java -cp ../../../../.. net.fcpsschools._1685666._1._1_basics.Reverser < path/to/input
 */
public class Reverser {
    public static void stdoutReversedSTDIN() {
        Scanner in = new Scanner(System.in);
        StringBuilder out = new StringBuilder();
        while (in.hasNextLine())
            out.insert(0, "\n" + in.nextLine());
        System.out.print(out);
    }

    public static void main(String[] args) {
        stdoutReversedSTDIN();
    }
}
