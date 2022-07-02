package turing;

import java.util.ArrayList;
import java.util.List;

public class Permutations {

    public static void main(String[] args) {
        new Permutations().printAllPermutations("1234");
    }


    private void printAllPermutations(String permutation) {
        List<String> permutations = getPermutationsRec(new ArrayList<>(), permutation, "");
        permutations.forEach(System.out::println);
    }

    private List<String> getPermutationsRec(List<String> permutations, String remaining, String permutation) {
        if (remaining.length() == 0) {
            permutations.add(permutation);
            return permutations;
        }
        for (int i = 0; i < remaining.length(); i++) {
            getPermutationsRec(permutations,
                               remaining.substring(0, i) + remaining.substring(i + 1),
                               permutation + remaining.charAt(i));
        }
        return permutations;
    }

}
