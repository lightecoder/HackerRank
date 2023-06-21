package udemi.sorting;

import java.util.Arrays;

public class CountingSort {
    public static void main(String[] args) {
        int[] arr = new int[]{3, 6, 2, 5, 3, 7, 6, 1, 2, 5, 1, 5, 9};
        countingSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void countingSort(int[] arr) {
        int[] itemCounterMap = new int[arr.length];
        for (int item : arr) {
            itemCounterMap[item]++;
        }
        int sortedIndex = 0;
        for (int item = 0; item < itemCounterMap.length; item++) {
            while (itemCounterMap[item]-- > 0) {
                arr[sortedIndex++] = item;
            }
        }
    }
}
