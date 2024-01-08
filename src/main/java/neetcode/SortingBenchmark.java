package neetcode;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static neetcode.Sorting.*;

class Sorting {
    public static void main(String[] args) {
        Random random = new Random();
//        int[] arr = new int[]{7, 1, 0, 3, 3};
        int[] arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(10);
        }
//        bubbleSort(arr);
//        insertionSort(arr);
//        mergeSort(arr);
        System.out.println(Arrays.toString(arr));
        quickSort(arr);
        System.out.println(Arrays.toString(arr));
    }


    public static void bubbleSort(int[] arr) {
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 1; j < arr.length - i; j++) {
                if (arr[j] < arr[j - 1]) {
                    swap(arr, j, j - 1);
                }
            }
        }
        long timeFinish = System.currentTimeMillis();
        System.out.println("Time Bubble = " + (timeFinish - timeStart) + " ms");
    }

    public static void insertionSort(int[] arr) {
        long timeStart = System.currentTimeMillis();
        for (int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j >= 0 && arr[j + 1] < arr[j]; j--) {
                swap(arr, j + 1, j);
            }
        }
        long timeFinish = System.currentTimeMillis();
        System.out.println("Time Insertion = " + (timeFinish - timeStart) + " ms");
    }

    public static void mergeSort(int[] arr) {
        long timeStart = System.currentTimeMillis();
        mergeSortRecursion(arr, 0, arr.length - 1);
        long timeFinish = System.currentTimeMillis();
        System.out.println("Time Merge = " + (timeFinish - timeStart) + " ms");
    }

    private static void mergeSortRecursion(int[] arr, int start, int end) {
        if (start == end) return;
        int mid = (end + start) / 2;
        mergeSortRecursion(arr, start, mid);            // left
        mergeSortRecursion(arr, mid + 1, end);     // right
        merge(arr, start, mid, end);
    }


    public static void quickSort(int[] arr) {
        long timeStart = System.currentTimeMillis();
        quickSortRecursion(arr, 0, arr.length - 1);
        long timeFinish = System.currentTimeMillis();
        System.out.println("Time Merge = " + (timeFinish - timeStart) + " ms");
    }

    private static void quickSortRecursion(int[] arr, int start, int pivot) {
        if (start >= pivot) return;
        int i = start;
        for (int j = start; j < pivot; j++) {
            if (arr[j] <= arr[pivot]) {
                swap(arr, i++, j);
            }
        }
        swap(arr, i, pivot);
        quickSortRecursion(arr, start, i - 1);
        quickSortRecursion(arr, i, pivot);
    }

    private static void merge(int[] arr, int start, int mid, int end) {
        int[] arrLeft = new int[mid - start + 1];
        int[] arrRight = new int[end - mid];
        System.arraycopy(arr, start, arrLeft, 0, arrLeft.length);
        System.arraycopy(arr, mid + 1, arrRight, 0, arrRight.length);
        int leftPointer = 0;
        int rightPointer = 0;
        for (int i = start; i <= end; i++) {
            if (leftPointer >= arrLeft.length && rightPointer < arrRight.length) {
                arr[i] = arrRight[rightPointer++];
            } else if (leftPointer < arrLeft.length && rightPointer >= arrRight.length) {
                arr[i] = arrLeft[leftPointer++];
            } else {
                if (arrLeft[leftPointer] < arrRight[rightPointer]) {
                    arr[i] = arrLeft[leftPointer++];
                } else {
                    arr[i] = arrRight[rightPointer++];
                }
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

@State(Scope.Thread)
public class SortingBenchmark {
    private static final int SIZE = 100_000;
    private int[] arr;

    @Setup(Level.Invocation)
    public void setUp() {
        Random random = new Random();
        arr = new int[SIZE];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(1000);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Warmup(iterations = 3)
    @Measurement(iterations = 5)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void mergeSortBenchmark() {
        mergeSort(arr.clone());
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Warmup(iterations = 3)
    @Measurement(iterations = 5)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void insertionSortBenchmark() {
        insertionSort(arr.clone());
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Warmup(iterations = 3)
    @Measurement(iterations = 5)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void bubbleSortBenchmark() {
        bubbleSort(arr.clone());
    }


    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Warmup(iterations = 3)
    @Measurement(iterations = 5)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void quickSortBenchmark() {
        quickSort(arr.clone());
    }

    public static void main(String[] args) throws Exception {
        new Runner(new OptionsBuilder()
                .include(SortingBenchmark.class.getSimpleName())
                .forks(1)
                .build())
                .run();
    }
}
