package neetcode;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static neetcode.Sorting.bubbleSort;
import static neetcode.Sorting.insertionSort;

class Sorting {
    public static void main(String[] args) {
        Random random = new Random();
        int[] arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(10);
        }
//        bubbleSort(arr);
        insertionSort(arr);
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
        int mid = start + (end - start) / 2;
        mergeSortRecursion(arr, start, mid);            // left
        mergeSortRecursion(arr, mid + 1, end);     // right
        merge(arr, start, mid, end);
    }

    private static void merge(int[] arr, int start, int mid, int end) {

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
    @Warmup(iterations = 5)
    @Measurement(iterations = 10)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void insertionSortBenchmark() {
        insertionSort(arr.clone());
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Warmup(iterations = 5)
    @Measurement(iterations = 10)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void bubbleSortBenchmark() {
        bubbleSort(arr.clone());
    }

    public static void main(String[] args) throws Exception {
        new Runner(new OptionsBuilder()
                .include(SortingBenchmark.class.getSimpleName())
                .forks(1)
                .build())
                .run();
    }
}
