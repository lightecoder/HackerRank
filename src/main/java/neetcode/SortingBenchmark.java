package neetcode;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static neetcode.Sorting.sortBubble;
import static neetcode.Sorting.sortInsertion;

class Sorting {

    public static void sortBubble(int[] arr) {
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 1; j < arr.length - i; j++) {
                if (arr[j] < arr[j-1]) {
                    swap(arr, j, j-1);
                }
            }
        }
        long timeFinish = System.currentTimeMillis();
        System.out.println("Time Bubble = " + (timeFinish - timeStart) + " ms");
    }

    public static void sortInsertion(int[] arr) {
        long timeStart = System.currentTimeMillis();
        for (int i = 1; i < arr.length; i++) {
            int pointer = i;
            for (int j = i - 1; j >= 0; j--) {
                if (arr[pointer] < arr[j]) {
                    swap(arr, pointer--, j);
                } else {
                    break;
                }
            }
        }
        long timeFinish = System.currentTimeMillis();
        System.out.println("Time Insertion = " + (timeFinish - timeStart) + " ms");
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
        sortInsertion(arr.clone());
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Warmup(iterations = 5)
    @Measurement(iterations = 10)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void bubbleSortBenchmark() {
        sortBubble(arr.clone());
    }

    public static void main(String[] args) throws Exception {
        new Runner(new OptionsBuilder()
                .include(SortingBenchmark.class.getSimpleName())
                .forks(1)
                .build())
                .run();
    }
}
