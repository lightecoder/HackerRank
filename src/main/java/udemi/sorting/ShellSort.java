package udemi.sorting;

import java.util.Arrays;

public class ShellSort {
    public static void main(String[] args) {
        int[] arr = new int[]{9, 2, 8, 3, 20, 1, 7, 4, 6, 0, 5, 15}; // length =12
        System.out.println(Arrays.toString(shellSort(arr)));
    }

    private static int[] shellSort(int[] arr) {
        int shift = arr.length / 2;
        while (shift > 0) {
            for (int i = 0; i + shift < arr.length; i++) {
                if (arr[i] > arr[i + shift]) {
                    int insertItem = arr[i + shift];
                    int k = i;
                    for (; k >= 0 && arr[k] > insertItem; k -= shift) {
                        arr[k + shift] = arr[k];
                    }
                    arr[k + shift] = insertItem;
                }
            }
            shift /= 2;
        }
        return arr;
    }
}
