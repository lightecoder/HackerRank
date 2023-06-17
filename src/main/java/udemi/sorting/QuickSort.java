package udemi.sorting;

import java.util.Arrays;

import static leetcode.NextPermutation.swap;

public class QuickSort {
    public static void main(String[] args) {
        int[] arr = new int[]{8, 2, 4, 7, 1, 3, 9, 6, 5};
        quickSort(arr, 0, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));

    }

    private static void quickSort(int[] arr, int tailPointer, int headPointer, int pivotPointer) {
        if (headPointer >= pivotPointer) return;
        while (headPointer != pivotPointer) {
            if (arr[headPointer] < arr[pivotPointer]) {
                swap(arr, headPointer, tailPointer++);
            }
            headPointer++;
        }
        swap(arr, tailPointer++, pivotPointer);
        quickSort(arr, 0, 0, tailPointer - 2);
        quickSort(arr, tailPointer, tailPointer, pivotPointer);
    }
}
