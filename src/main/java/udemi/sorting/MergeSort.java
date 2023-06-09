package udemi.sorting;

import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        int[] arr = new int[]{3, 7, 8, 5, 4, 2, 6, 1}; // length = 8
        mergeSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    // merge sort = recursively divide array in 2, sort, re-combine
    // run-time complexity = O(n Log n)
    // space complexity    = O(n)
    private static void mergeSort(int[] arr) {
        if (arr.length == 1) return;
        int middle = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, middle);
        int[] right = Arrays.copyOfRange(arr, middle, arr.length);
        mergeSort(left);
        mergeSort(right);
        merge(arr, left, right);
    }

    private static void merge(int[] arr, int[] left, int[] right) {
        int leftPartPointer = 0;
        int rightPartPointer = 0;
        for (int i = 0; i < arr.length; i++) {
            if (leftPartPointer < left.length && rightPartPointer < right.length) {
                if (left[leftPartPointer] < right[rightPartPointer]) {
                    arr[i] = left[leftPartPointer];
                    leftPartPointer++;
                } else {
                    arr[i] = right[rightPartPointer];
                    rightPartPointer++;
                }
            } else if (rightPartPointer < right.length) {
                arr[i] = right[rightPartPointer];
                rightPartPointer++;
            } else if (leftPartPointer < left.length) {
                arr[i] = left[leftPartPointer];
                leftPartPointer++;
            }
        }
    }
}
