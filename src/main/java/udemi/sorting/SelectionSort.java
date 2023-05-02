package udemi.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectionSort {
    public static void main(String[] args) {
        List<Integer> arr = new ArrayList<>(Arrays.asList(3, 1, 10, 4, 8, 5));
        selectionSort(arr);
        System.out.println(arr);
    }

    private static void selectionSort(List<Integer> arr) {
        for (int firstUnSortedPartitionIndex = 0; firstUnSortedPartitionIndex < arr.size(); firstUnSortedPartitionIndex++) {
            int minElement = arr.get(firstUnSortedPartitionIndex);
            int minElementIndex = firstUnSortedPartitionIndex;
            for (int pointer = firstUnSortedPartitionIndex + 1; pointer < arr.size(); pointer++) {
                if (arr.get(pointer) < minElement) {
                    minElement = arr.get(pointer);
                    minElementIndex = pointer;
                }
            }
            if (arr.get(firstUnSortedPartitionIndex) > minElement) {
                swap(arr, firstUnSortedPartitionIndex, minElementIndex);
            }
        }
    }

    private static void swap(List<Integer> arr, int lastSortedPartitionIndex, int pointer) {
        int temp = arr.get(lastSortedPartitionIndex);
        arr.set(lastSortedPartitionIndex, arr.get(pointer));
        arr.set(pointer, temp);
    }
}
