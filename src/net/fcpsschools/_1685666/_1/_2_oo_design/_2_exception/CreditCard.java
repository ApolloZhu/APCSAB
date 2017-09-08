package net.fcpsschools._1685666._1._2_oo_design._2_exception;

// R-2.15
public class CreditCard {
    protected double balance;

    public void makePayment(double amount) {
        if (amount < 0) throw new IllegalArgumentException();
        balance -= amount;
    }

    /*Implmentation not shown...*/
}
