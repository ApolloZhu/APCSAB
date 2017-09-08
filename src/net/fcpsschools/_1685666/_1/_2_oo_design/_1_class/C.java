package net.fcpsschools._1685666._1._2_oo_design._1_class;

// C-2.21
public class C extends B {
    int x = -1;

    void checkedSetAX(int i)
            throws NoSuchFieldException, IllegalAccessException {
        A.class.getDeclaredField("x").setInt(this, i);
    }

    void uncheckedSetAX(int i) throws ClassCastException {
        ((A) this).x = i;
    }

    public static void main(String[] args) {
        C c = new C(); // A.x = 0, B.x = 13, C.x = -1

        try {
            c.checkedSetAX(1);
            System.out.println(
                    A.class.getDeclaredField("x").getInt(c) + " " +
                            c.getClass().getSuperclass().getDeclaredField("x").getInt(c)
                            + " " + c.x
            );
        } catch (NoSuchFieldException | IllegalAccessException e) {

        }

        c.uncheckedSetAX(2);
        System.out.println(((A) c).x + " " + ((B) c).x + " " + c.x);
    }
}
