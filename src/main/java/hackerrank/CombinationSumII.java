package hackerrank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CombinationSumII {

    public static void main(String[] args) {
        //        System.out.println(new CombinationSumII().combinationSum(new int[]{2, 5, 2, 1, 2}, 5));
        System.out.println(new CombinationSumII().combinationSum2(new int[]{10, 1, 2, 7, 6, 1, 5}, 8));
        //        System.out.println(new CombinationSumII().combinationSum(new int[]{2,2,2}, 2));
        //        System.out.println(new CombinationSumII().combinationSum(new int[]{4, 1, 1, 4, 4, 4, 4, 2, 3, 5}, 10));
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> list = new LinkedList<>();
        Arrays.sort(candidates);
        backtrack(list, new ArrayList<>(), candidates, target, 0);
        return list;
    }

    private void backtrack(List<List<Integer>> list, List<Integer> tempList, int[] cand, int remain, int start) {

        if (remain < 0) {
            return;
        } /** no solution */
        else if (remain == 0) {
            list.add(new ArrayList<>(tempList));
        }
        else {
            for (int i = start; i < cand.length; i++) {
                if (i > start && cand[i] == cand[i - 1]) {
                    continue;
                } /** skip duplicates */
                tempList.add(cand[i]);
                backtrack(list, tempList, cand, remain - cand[i], i + 1);
                tempList.remove(tempList.size() - 1);
            }
        }
    }


}