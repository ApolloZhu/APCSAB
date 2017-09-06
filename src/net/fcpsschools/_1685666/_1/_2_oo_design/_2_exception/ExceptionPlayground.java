package net.fcpsschools._1685666._1._2_oo_design._2_exception;

import edu.fcps.eylau._2_oo_design._2_exception.MyException;

import java.io.IOError;

public class ExceptionPlayground {

    static <E> void set() throws MyException {
        E[] a = (E[]) new Object[0];
    }

    public static void main(String[] args) {
        try {
            set();
        } catch (Exception e) {
            System.out.println("Even I know it doesn't throw");
        }
        throw new IOError(new Exception());
    }
}
