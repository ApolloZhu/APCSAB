package net.fcpsschools._1685666._3.lab._1_heap;

//Name:
//Date:
// What I learned:
// How I feel about this lab:
// I am wondering:
public class ZhiyuZhu_Period1_HeapSort_shell {
    public static final int SIZE = 9;

    public static void main(String[] args) {
        //Part 1: Given a heap, sort it. Do this part first.
        double heap[] = {-1, 99, 80, 85, 17, 30, 84, 2, 16, 1};
        display(heap);
        sort(heap);
        display(heap);

        //Part 2:  Generate 100 random numbers, make a heap, sort it.
        // double[] heap = new double[SIZE + 1];
        // createRandom(heap);
        // display(heap);
        // makeHeap(heap, SIZE);
        // display(heap);
        // sort(heap);
        // display(heap);
    }

    //******* Part 1 ******************************************
    public static void display(double[] array) {
        for (int k = 1; k < array.length; k++)
            System.out.print(array[k] + "    ");
        System.out.println("\n");
    }

    public static void sort(double[] array) {

    }

    public static void swap(double[] array, int a, int b) {
        double temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    public static void heapDown(double[] array, int k, int size) {

    }

    // ****** Part 2 *******************************************
    public static void makeHeap(double[] array, int size) {

    }

    // Generate 100 random numbers (between 1 and 100, formatted to 2 decimal places)
    public static void createRandom(double[] array) {

    }
}

