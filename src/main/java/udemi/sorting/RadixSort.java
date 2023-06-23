package udemi.sorting;

import java.util.Arrays;

public class RadixSort {
    public static void main(String[] args) {
        int[] arr = new int[]{12, 345, 69, 73, 1246, 389, 401, 5, 78, 1145};
        System.out.println(Arrays.toString(radixSort(arr)));
    }

    private static int[] radixSort(int[] arr) {
        int digitLength = getMaxDigitLength(arr);
        double power = 1;
        while (digitLength > 0) {
            int[][] counterItemMap = new int[arr.length][arr.length];
            for (int item : arr) {
                int itemIndex = (int) (item % Math.pow(10, power) / Math.pow(10, power - 1));
                putToItemMap(counterItemMap, itemIndex, item);
            }
            power++;
            digitLength--;
            arr = flatMap(counterItemMap, arr.length);
        }
        return arr;
    }

    private static void putToItemMap(int[][] counterItemMap, int itemIndex, int item) {
        for (int i = 0; i < counterItemMap[itemIndex].length; i++) {
            if (counterItemMap[itemIndex][i] == 0) {
                counterItemMap[itemIndex][i] = item;
                break;
            }
        }
    }

    private static int[] flatMap(int[][] counterItemMap, int length) {
        int[] result = new int[length];
        int resultIndex = 0;
        for (int[] ints : counterItemMap) {
            for (int anInt : ints) {
                if (anInt != 0) {
                    result[resultIndex++] = anInt;
                }
            }
        }
        return result;
    }

    private static int getMaxDigitLength(int[] arr) {
        int digitLength = 0;
        for (int item : arr) {
            digitLength = Math.max(getDigitLength(item), digitLength);
        }
        return digitLength;
    }

    private static int getDigitLength(int item) {
        int length = 0;
        while (item > 0) {
            item = item / 10;
            length++;
        }
        return length;
    }
}
