package net.fcpsschools._1685666._3.lab._2_hash;

/*****************************************************************************
 Assignment: Notice that collisions occur. You are to implement
 three different collision schemes, namely, linear probing,
 rehashing, and chaining (using a LinkedList <Object>).

 Make sure you find a way to prove that your program works for linear probing, separate chaining. Show me enough cases to convince me that. Follow file naming convention when you are ready to send me the file.

 For separate chaining,if you want to use a table of LinkedList objects, it is fine with me.
 *****************************************************************************/
public abstract class Hashtable<E> {

    private int size;
    private boolean useProbing;
    private E[] table;

    public static <E> Hashtable<E> init(int numSlots, boolean useProbing) {
        return null;
    }

    protected Hashtable(E[] table) {
        this.table = table;
    }

    public void add(E obj) {
        int code = Math.abs(obj.hashCode());
        System.out.println("Hash Code: " + code);
        int index = code % size;
        if (null == table[index]) {
            table[index] = obj;
        } else {
            if (useProbing) { /*Linear Probing*/
                // TODO: call the linearProbe method
            } else { /*Chaining*/
                // TODO: call the chaining method
            }
        }
    }

    // returns true if obj can be found in the table
    public boolean contains(E obj) {
        int code = Math.abs(obj.hashCode());
        int index = code % size;

        if (useProbing) {
            /*Linear Probing*/
        } else {
            /*Chaining*/
        }
        return true;
    }//contains

    // TODO: implement collision resolution methods one at a time

    /**
     * @param index
     * @return
     * @apiNote implement this method recursively
     */
    public int linearProbe(int index) {
        return 0;
    }

    /**
     * One student asked me what the method chaining returns. It returns the head of the new linked list (each table entry is a singly linked list of ListNodes) so that you can do:    table[index] = chaining (index, obj).
     *
     * @apiNote each table element is a singly linked list of Objects
     */
    public E chaining(int index, E obj) {
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        int i = 0;
        while (i < table.length - 1) {
            sb.append(table[i++]);
            sb.append(", ");
        }
        sb.append(table[i]);
        sb.append(']');
        return null;
    }

    public enum CollisionResolutionMethod {
        LINEAR_PROBING, QUADRATIC_PROBING, CHAINING, REHASHING
    }
}
