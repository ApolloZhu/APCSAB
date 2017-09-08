package edu.fcps.eylau._2_oo_design._2_exception;

public class MyException extends Exception {

    /**
     * Constructor: an instance with message m
     */
    public MyException(String m) {
        super(m);
    }

    /**
     * Constructor: an instance with no message
     */
    public MyException() {
        super();
    }
}
