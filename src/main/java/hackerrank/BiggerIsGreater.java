package hackerrank;

import java.util.Arrays;

public class BiggerIsGreater {

    public static void main(String[] args) {
        String s = "6\n"
                   + "lmno\n"
                   + "dcba\n"
                   + "dcbb\n"
                   + "abdc\n"
                   + "abcd\n"
                   + "fedcbabcd";
        System.out.println(biggerIsGreater(s));
        //        test("1234565");
    }

    public static String biggerIsGreater(String w) {
        String[] arr = w.split("\n");
        StringBuilder sb = new StringBuilder();
        for (int k = 1; k < arr.length; k++) {
            boolean trigger = false;
            L:
            for (int i = arr[k].length() - 2; i >= 0; i--) {
                char [] chars = arr[k].substring(i + 1).toCharArray();
                Arrays.sort(chars);
                for (int j = 0; j < chars.length; j++) {
                    if (arr[k].charAt(i) < chars[j]) {
                        arr[k] = swapSort(arr[k], i, j, String.valueOf(chars));
                        trigger = true;
                        break L;
                    }
                }
            }
            if (trigger) {
                sb.append(arr[k]);
            }
            else {
                sb.append("no answer");
            }
            if (k != arr.length - 1) {
                sb.append("\n");
            }

        }
        return sb.toString();
    }

    private static String swapSort(String num, int i, int j, String str) {
        char replace = str.charAt(j);
        char [] chars = (str.substring(0, j) + num.charAt(i) + str.substring(j + 1)).toCharArray();
        Arrays.sort(chars);
        String sortPart = String.valueOf(chars);
        num = num.substring(0, i) + replace + sortPart;
        return num;
    }

    public static void test(String num) { // 12345
        L:
        for (int i = num.length() - 2; i > 0; i--) {
            for (int j = num.length() - 1; j > i; j--) {
                if (num.charAt(i) < num.charAt(j)) {
                    num = swap(num, i, j);
                    break L;
                }
            }
        }
        System.out.println(num);
    }

    private static String swap(String num, int i, int j) {
        num = num.substring(0, i) + num.charAt(j) + num.substring(i + 1, j) + num.charAt(i) + num.substring(j + 1);
        return num;
    }
}
