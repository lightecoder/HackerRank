package udemi.sorting;

public class CountingStableSort {
    public static void countingSort(int[] array, int max) {
        int size = array.length;
        int[] count = new int[max + 1]; // Create a count array to store the count of each element

        int[] output = new int[size]; // Create an output array to store the sorted elements

        // Store the count of each element in the count array
        for (int j : array) {
            count[j]++;
        }

        // Modify the count array to store the actual position of each element in the output array
        for (int i = 1; i <= max; i++) {
            count[i] += count[i - 1];
        }

        // Build the output array
        for (int i = size - 1; i >= 0; i--) {
            output[count[array[i]] - 1] = array[i];
            count[array[i]]--;
        }

        // Copy the sorted elements back to the original array
        System.arraycopy(output, 0, array, 0, size);
    }

    public static void main(String[] args) {
        int[] array = {4, 2, 2, 8, 3, 3, 1};
        int max = 8; // Maximum element in the array

        countingSort(array, max);

        System.out.print("Sorted array: ");
        for (int i : array) {
            System.out.print(i + " ");
        }
    }
}

