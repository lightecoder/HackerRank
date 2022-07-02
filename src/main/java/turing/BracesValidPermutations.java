package turing;

import java.util.ArrayList;
import java.util.List;

public class BracesValidPermutations {

    public static void main(String[] args) {
        // "((()))", "(()())", "(())()", "()(())", "()()()";
        new BracesValidPermutations().solution(3).stream()
                .distinct()
                .forEach(System.out::println);
    }

    private List<String> solution(int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            sb.append("()");
        }
        return getPermutationsRec(new ArrayList<>(), sb.toString(), "");
    }

    private List<String> getPermutationsRec(List<String> result, String remaining, String prefix) {
        if (remaining.length() == 0 && isValid(prefix)) {
            result.add(prefix);
            return result;
        }
        for (int i = 0; i < remaining.length(); i++) {
            getPermutationsRec(result,
                               remaining.substring(0, i) + remaining.substring(i + 1),
                               prefix + remaining.charAt(i));
        }
        return result;
    }

    private boolean isValid(String par) {
        int counter = 0;
        if (par.charAt(0) != '(') {
            return false;
        }
        for (char sym : par.toCharArray()) {
            if (counter < 0) {
                return false;
            }
            if (sym == '(') {
                counter++;
            } else {
                counter--;
            }
        }
        return counter == 0;
    }
}
