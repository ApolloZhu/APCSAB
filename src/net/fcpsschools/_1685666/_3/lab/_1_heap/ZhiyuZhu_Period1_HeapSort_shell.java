package net.fcpsschools._1685666._3.lab._1_heap;

/**
 * Name: Zhiyu Zhu, Period 1
 * Date: 2018/02/08 23:59:59
 * What I learned:
 * - Heap sort implementation
 * How I feel about this lab:
 * - {@link #createRandom(double[])} should be re-documented.
 * I am wondering:
 * - Is it possible to make heap using top down approach.
 * Acknowledgement:
 * - Joseph Xu for simplified implementation of {@link #heapDown}
 */
public class ZhiyuZhu_Period1_HeapSort_shell {
    private static final int SIZE = 9;

    public static void main(String[] args) {
        // Part 1: Given a heap, sort it. Do this part first.
        double heap[] = {-1, 99, 80, 85, 17, 30, 84, 2, 16, 1};
        display(heap);
        sort(heap);
        display(heap);
        // Part 2: Generate 100 random numbers, make a heap, sort it.
        heap = new double[SIZE + 1];
        createRandom(heap);
        display(heap);
        makeHeap(heap, SIZE);
        display(heap);
        sort(heap);
        display(heap);
    }

    //region Part 1

    static void display(double[] array) {
        for (int k = 1; k < array.length; k++)
            System.out.print(array[k] + "    ");
        System.out.println("\n");
    }

    /**
     * Sorts a max heap to increasing array,
     * assuming the array is full.
     *
     * @param array a max heap whose elements
     *              are at index 1...array.length.
     * @apiNote element at index 0 is untouched.
     */
    public static void sort(double[] array) {
        for (int i = array.length - 1; i > 1; i--) {
            swap(array, 1, i);
            heapDown(array, 1, i - 1);
        }
    }

    private static void swap(double[] array, int a, int b) {
        double temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    private static void heapDown(double[] array, int k, int size) {
        if (k < 1 || size / 2 < k) return;
        int left = k * 2, right = left + 1;
        if (right <= size && array[right] > array[left]) {
            if (array[right] > array[k]) {
                swap(array, k, right);
                heapDown(array, right, size);
            }
        } else if (array[left] > array[k]) {
            swap(array, k, left);
            heapDown(array, left, size);
        }
    }
    //endregion

    //region Part 2

    /**
     * Turns an array to a max heap of given size.
     *
     * @param array unordered array to alter to a max heap.
     * @param size  size of heap, should be greater than -1,
     *              and less than array.length - 1.
     */
    private static void makeHeap(double[] array, int size) {
        for (int i = size / 2; i > 1; i--)
            heapDown(array, i, size);
    }

    // Generate 100 random numbers (between 1 and 100,
    // formatted to 2 decimal places)
    public static void createRandom(double[] array) {
        for (int i = 1; i < array.length; i++)
            array[i] = (int) (Math.random() * 100) + 1;
    }
    //endregion
}
