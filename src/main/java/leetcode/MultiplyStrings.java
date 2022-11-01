package leetcode;

public class MultiplyStrings {

    public static void main(String[] args) {
        //        System.out.println(new MultiplyStrings().multiply("498828660196", "840477629533"));
        System.out.println(new MultiplyStrings().multiply("0", "0"));
    }

    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        int[] result = new int[200];
        int index;
        for (int i = num1.length() - 1, k = 0; i >= 0; i--, k++) {
            index = k;
            for (int j = num2.length() - 1; j >= 0; j--) {
                int n1 = num1.charAt(i) - 48;
                int n2 = num2.charAt(j) - 48;
                int sum = n1 * n2;
                int nextSum = result[index] + sum;
                boolean aboveTen = false;
                if (nextSum >= 10) {
                    result[index++] = nextSum % 10;
                    nextSum /= 10;
                    aboveTen = true;
                }
                if (aboveTen) {
                    result[index] += nextSum % 10;
                    index--;
                }
                else {
                    result[index] = nextSum % 10;
                }
                index++;
            }
        }
        StringBuilder sb = new StringBuilder();
        boolean started = false;
        for (int i = result.length - 1; i >= 0; i--) {
            if (!started && result[i] > 0) {
                sb.append(result[i]);
                started = true;
            }
            else if (started) {
                sb.append(result[i]);
            }
        }
        return sb.toString();
    }
}
