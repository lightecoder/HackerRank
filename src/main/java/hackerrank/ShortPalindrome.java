package hackerrank;

import java.util.Arrays;

public class ShortPalindrome {

    public static void main(String[] args) {
                String input = "a";
//        String input = "12215";
        printPalindromTuple(input);
    }

    private static void printPalindromTuple(String input) {
        char[] buffer = new char[4];
        int palindromCounter = 0;
        int counter = 0;
        System.out.println(getPalindroms(buffer, input, palindromCounter, counter));
    }

    private static int getPalindroms(char[] buffer, String input, int palindromCounter, int counter) {
        int index = 0;
        if (counter > 3) {
            return palindromCounter;
        }
        for (char item : input.substring(index).toCharArray()) {
            buffer[counter] = item;
            System.out.println(buffer);
            if (buffer[0] == buffer[3] && buffer[1] == buffer[2]) {
                palindromCounter++;
                System.out.println("match palindrom: " + Arrays.toString(buffer));
            }
            palindromCounter = getPalindroms(buffer, input.substring(++index), palindromCounter, ++counter);

            buffer[--counter] = ' ';
        }
        return palindromCounter;
    }

}
