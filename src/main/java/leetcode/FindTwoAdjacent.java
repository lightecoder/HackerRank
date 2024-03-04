package leetcode;

public class FindTwoAdjacent {
    public static void main(String[] args) {
        adjacentSum("100100"); // 2
        adjacentSum("010110"); // 3
        adjacentSum("111000"); // 3
        adjacentSum("111*000"); // 0
        adjacentSum("11*1000"); // 1
        adjacentSum("11*1*010"); // 1
        adjacentSum("11100000"); // 3
    }

    static void adjacentSum(String s) {
        int counter = 0;//1
        for (int i = 0; i < s.length() - 1; i++) {// i=3
            if (isAdjacent(s.charAt(i), s.charAt(i + 1))) { // i =2 [10]
                counter++;//1
                int first = i;//
                int second = i + 1;
                while (first > 0 && second < s.length() - 1 && isAdjacent(s.charAt(first - 1), s.charAt(second + 1))) {
                    counter++;
                    i++;
                    first--;
                    second++;
                }
                i++;
            }
        }
        System.out.println(counter);
    }

    private static boolean isAdjacent(char a, char b) {
        return (a == '1' && b == '0') || (a == '0' && b == '1');
    }
}
