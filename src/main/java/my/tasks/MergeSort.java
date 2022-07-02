package my.tasks;

import java.util.Arrays;

public class MergeSort {

    public static void main(String[] args) {
        int[] arr = new int[]{2, 10, 8, 7, 3, 12, 1};
        sortArray(arr);
        System.out.println(Arrays.toString(arr));
    }

    //time complexity = n log n
    private static void sortArray(int[] arr) {
        int length = arr.length;
        if (length < 2) {
            return;
        }

        int mid = length / 2;

        int[] leftArr = Arrays.copyOfRange(arr, 0, mid);
        int[] rightArr = Arrays.copyOfRange(arr, mid, length);

        sortArray(leftArr);
        sortArray(rightArr);

        merge(arr, leftArr, rightArr);
    }

    private static void merge(int[] arr, int[] leftArr, int[] rightArr) {
        int originIndex = 0;
        int leftIndex = 0;
        int rightIndex = 0;
        while (leftIndex < leftArr.length && rightIndex < rightArr.length) {
            if (leftArr[leftIndex] <= rightArr[rightIndex]) {
                arr[originIndex] = leftArr[leftIndex];
                leftIndex++;
            } else {
                arr[originIndex] = rightArr[rightIndex];
                rightIndex++;
            }
            originIndex++;
        }
        while (leftIndex < leftArr.length) {
            arr[originIndex] = leftArr[leftIndex];
            leftIndex++;
            originIndex++;
        }
        while (rightIndex < rightArr.length) {
            arr[originIndex] = rightArr[rightIndex];
            rightIndex++;
            originIndex++;
        }
    }


}
