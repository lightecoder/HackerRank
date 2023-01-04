package my.tasks;

//The Fibonacci Sequence is the series of numbers: 0, 1, 1, 2, 3, 5, 8, 13, 21, 34... fib(2)=1, fib(3)=2, fib(4)=3 ...
public class Fibonacci {

    public static void main(String[] args) {
        int fib = 5;
//        printFibonaci(fib);
//        System.out.println();
        System.out.println(getFibonacciDynamically(7));
    }

    private static int getFibonacciDynamically(int fib) {
//        return getFib(fib, new int[fib + 1]);
        return getFib2ConstMemory(fib, new int[2]);
    }

    private static int getFib(int fib, int[] memory) {
        if (fib == 1) {
            memory[1] = 1;
            memory[0] = 1;
            return 1;
        }
        getFib(fib - 1, memory); // fib =2
        memory[fib] = memory[fib - 1] + memory[fib - 2];
        return memory[fib];
    }

    private static int getFib2ConstMemory(int fib, int[] memory) {
        if (fib == 1) {
            memory[1] = 1;
            memory[0] = 1;
            return 1;
        }
        getFib2ConstMemory(fib - 1, memory); // fib =2
        int temp = memory[1];
        memory[1] = memory[0] + memory[1];
        memory[0] = temp;
        return memory[1];
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
            return getFibonaci(num - 1) + getFibonaci(num - 2);
        }
    }

}
