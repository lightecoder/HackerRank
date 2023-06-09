package udemi.recursion;

public class Factorial {
    public static void main(String[] args) {
        System.out.println(getFactorial(5));
    }

    private static int getFactorial(int i) {
        return i == 2 ? 2 : i * getFactorial(i - 1);
    }
}
