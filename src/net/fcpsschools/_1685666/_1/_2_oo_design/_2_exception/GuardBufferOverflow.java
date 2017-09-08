package net.fcpsschools._1685666._1._2_oo_design._2_exception;

// R-2.14
public class GuardBufferOverflow {
    public static void main(String[] args) {
        try {
            args[args.length] = args[-1];
        } catch (ArrayIndexOutOfBoundsException ignored) {
            System.out.println("Don't try buffer overflow attacks in Java!");
        }
    }
}
