package udemi.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BobbleSort {
    public static void main(String[] args) {
        List<Integer> arr = new ArrayList<>(Arrays.asList(5, 3, 8, 1, 3, 4));
        bobbleSort(arr);
        System.out.println(arr);
    }

    private static void bobbleSort(List<Integer> arr) {
        for (int i = arr.size() - 1; i >= 0; i--) {
            for (int k = 0; k < i; k++) {
                if (arr.get(k) > arr.get(k + 1)) {
                    swap(arr, k);
                }
            }
        }
    }

    private static void swap(List<Integer> arr, int k) {
        int temp = arr.get(k);
        arr.set(k, arr.get(k + 1));
        arr.set(k + 1, temp);
    }
}
