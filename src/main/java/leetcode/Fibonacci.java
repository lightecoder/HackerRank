package leetcode;

public class Fibonacci {

    // F1=1; F2=1;
    // Fn = Fn-1 + Fn-2;
    public static void main(String[] args) {
        int fib = 10;
        long start = System.currentTimeMillis();
        System.out.println(getFibRecursion(fib));
        long finish = System.currentTimeMillis();
        System.out.println("getFibRecursion time: " + (finish - start));
        start = System.currentTimeMillis();
        getFibDynamic(fib, 1, 1);
        finish = System.currentTimeMillis();
        System.out.println("getFibDynamic time: " + (finish - start));
        start = System.currentTimeMillis();
        System.out.println(getFibRecursionDynamic(fib, new int[fib + 2]));
        finish = System.currentTimeMillis();
        System.out.println("getFibRecursionDynamic time: " + (finish - start));
        System.out.println(getFibDynamicLoop(fib));
    }

    private static int getFibRecursionDynamic(int num, int[] memory) {
        if (memory[num] != 0) {
            return memory[num];
        }
        int fib;
        if (num <= 1) {
            return 1;
        }
        else {
            fib = getFibRecursionDynamic(num - 1, memory) + getFibRecursionDynamic(num - 2, memory);
            memory[num] = fib;
        }
        return fib;
    }

    private static int getFibDynamicLoop(int num) {
        int agg = 1;
        int prev = 0;
        while (num > 0) {
            int temp = agg;
            agg += prev;
            prev = temp;
            num--;
        }
        return agg;
    }

    private static void getFibDynamic(int num, int agg, int prev) {
        if (num == 1) {
            System.out.println(agg);
            return;
        }
        int temp = agg;
        agg += prev;
        prev = temp;
        getFibDynamic(num - 1, agg, prev);
    }

    private static int getFibRecursion(int num) {
        if (num <= 1) {
            return 1;
        }
        return getFibRecursion(num - 1) + getFibRecursion(num - 2);
    }
}
