package leetcode;

import java.util.HashSet;
import java.util.Set;

public class FirstRecurringCharacter {

    public static void main(String[] args) {
        String str = "DPCABACCU";
        findFirstRecurringChar(str);
    }

    private static void findFirstRecurringChar(String str) {
        Set<Character> charSet = new HashSet<>();
        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            if (charSet.contains(currentChar)) {
                System.out.println(currentChar);
                return;
            }
            charSet.add(str.charAt(i));
        }
    }

}
