package hackerrank;

public class ReverseInteger {

    public static void main(String[] args) {
        System.out.println(new ReverseInteger().reverse(1500000009));
    }

    public int reverse(int x) {
        long res;
        if (x >= 0) {
            res = Long.parseLong(new StringBuilder(String.valueOf(x)).reverse().toString());
        }
        else {
            res = Long.parseLong(
                    new StringBuilder(String.valueOf(x).replace("-", "")).append("-").reverse().toString());
        }
        if (res < Integer.MIN_VALUE || res > Integer.MAX_VALUE) {
            return 0;
        }
        return (int) res;
    }
}
