package my.tasks;

//The Fibonacci Sequence is the series of numbers: 0, 1, 1, 2, 3, 5, 8, 13, 21, 34... fib(2)=1, fib(3)=2, fib(4)=3 ...
public class Fibonaci {

    public static void main(String[] args) {
        int fib = 5;
        printFibonaci(fib);
    }

    private static void printFibonaci(int num) {
        if (num < 0) {
            return;
        }
        int fib = getFibonaci(num);
        System.out.println(fib);
        printFibonaci(--num);
    }

    private static int getFibonaci(int num) {
        if (num <= 0) {
            return 0;
        } else if (num == 1) {
            return 1;
        } else {
            int fibonaci = getFibonaci(num - 1) + getFibonaci(num - 2);
            return fibonaci;
        }
    }

}
