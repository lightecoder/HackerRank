package udemi.sorting;

import java.util.Arrays;

public class StableCountingSort {
    public static void main(String[] args) {
        int[] arr = new int[]{3, 6, 2, 5, 3, 7, 6, 1, 2, 5, 1, 5, 9};
        stableCountingSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void stableCountingSort(int[] arr) {
        int[][] itemCounterMap = new int[arr.length][arr.length];
        for (int item : arr) {
            int index = 0;
            while (itemCounterMap[item][index] != 0) {
                index++;
            }
            itemCounterMap[item][index] = item;
        }
        int sortedIndex = 0;
        for (int item = 0; item < itemCounterMap.length; item++) {
            int itemIndex = 0;
            while (itemsLength(itemCounterMap[item]) > 0) {
                arr[sortedIndex++] = itemCounterMap[item][itemIndex];
                itemCounterMap[item][itemIndex++] = 0;
            }
        }
    }

    private static int itemsLength(int[] items) {
        int counter = 0;
        for (int item : items) {
            counter = item != 0 ? counter + 1 : counter;
        }
        return counter;
    }
}
