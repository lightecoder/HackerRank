package hackerrank;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * ****        *  *
 * *          * **
 * *    OR   ** *
 * ****        *  *
 */

public class ZigzagConversion {

    public static void main(String[] args) {
        //        System.out.println(new ZigzagConversion().convert("PAYPALISHIRING", 4));
        System.out.println(new ZigzagConversion().convert("AB", 1));
    }

    public String convert(String s, int numRows) {
        if (numRows == 1) {
            return s;
        }
        char[] arr = s.toCharArray();
        String[] res = new String[numRows];
        Arrays.fill(res, "");
        int counter = 0;
        boolean vertical = true;
        for (char c : arr) {
            if (vertical) {
                res[counter++] += c;
                if (counter == numRows) {
                    vertical = false;
                    counter--;
                }
            }
            else {
                res[--counter] += c;
                if (counter == 0) {
                    vertical = true;
                    counter++;
                }
            }
        }

        return String.join("", res);
    }

}
