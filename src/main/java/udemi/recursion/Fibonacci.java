package udemi.recursion;

public class Fibonacci {
    public static void main(String[] args) {
        // fib -> 0,1,1,2,3,5,8,13,21...
        //fib(3) = 1
        //fib(1) = 0
        //fib(2) = 1
        //fib(8) = 13

        System.out.println(getFibonacci(1));
        System.out.println(getFibonacci(2));
        System.out.println(getFibonacci(3));
        System.out.println(getFibonacci(8));
        System.out.println(getFibonacci(9));
        System.out.println("#############");
        System.out.println(getFibonacciBad(1));
        System.out.println(getFibonacciBad(2));
        System.out.println(getFibonacciBad(3));
        System.out.println(getFibonacciBad(8));
        System.out.println(getFibonacciBad(9));
    }

    private static int getFibonacciBad(int i) {
        if (i == 1) {
            return 0;
        } else if (i == 2) {
            return 1;
        }
        return getFibonacciBad(i - 1) + getFibonacciBad(i - 2);
    }

    //O(n)
    private static int getFibonacci(int counter) {
        if (counter == 1) {
            return 0;
        }
        int[] buf = new int[1];
        fibDynamic(buf, counter, 1);
        return buf[0];
    }

    private static void fibDynamic(int[] buf, int counter, int prev) {
        int temp = buf[0];
        buf[0] += prev;
        if (counter == 2) return;
        fibDynamic(buf, --counter, temp);
    }
}
