package leetcode;

import java.util.HashMap;
import java.util.Map;

public class LongestConsecutiveCharacters {

    public static void main(String[] args) {
        String str = "aaabcddbbbbea"; // bbbb - longest
        findLongestConsecutiveCharSubString(str);
    }

    private static void findLongestConsecutiveCharSubString(String str) {
        Map<Character, Integer> map = new HashMap<>();
        char[] charArray = str.toCharArray();
        char prev = ' ';
        int counter = 0;
        int maxCount = 0;
        char maxChar = ' ';
        for (char current : charArray) {
            if (map.containsKey(current)) {
                if (current == prev) {
                    counter++;
                }
                else {
                    int finalCounter = counter;
                    map.compute(prev, (k, v) -> v > finalCounter ? v : finalCounter);
                    counter = 1;
                }
            }
            else {
                map.put(current, 1);
                int finalCounter = counter;
                map.computeIfPresent(prev, (k, v) -> v > finalCounter ? v : finalCounter);
                counter = 1;
            }
            if (maxCount < counter) {
                maxCount = counter;
                maxChar = prev;
            }

            prev = current;
        }
        System.out.println("MaxChar : " + maxChar + " : " + maxCount);
    }

}
