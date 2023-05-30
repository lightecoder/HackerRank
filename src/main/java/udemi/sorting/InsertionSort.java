package udemi.sorting;

import java.util.ArrayList;
import java.util.List;

public class InsertionSort {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(List.of(1, 3, 6, 4, 2, 8, 9));
        new InsertionSort().insertionSort(list);
        System.out.println(list);
    }

    private void insertionSort(List<Integer> list) {
        for (int pointer = 1; pointer < list.size(); pointer++) {
            int temp = list.get(pointer);
            int leftComparePartIndex = pointer - 1;
            for (; leftComparePartIndex >= 0; leftComparePartIndex--) {
                if (temp < list.get(leftComparePartIndex)) {
                    moveToNextPosition(list, leftComparePartIndex);
                } else {
                    break;
                }
            }
            list.set(leftComparePartIndex + 1, temp);
        }
    }

    private void moveToNextPosition(List<Integer> list, int leftComparePartIndex) {
        list.set(leftComparePartIndex + 1, list.get(leftComparePartIndex));
    }


}
