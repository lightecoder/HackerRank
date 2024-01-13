package apple.task;

import java.util.*;

public class UniqueTripletSum {
    public static List<List<Integer>> threeSum(int[] nums, int m) {
        List<List<Integer>> result = new ArrayList<>();
        // Sort the array to use two pointers technique
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            int a = nums[i];
            int low = i + 1;
            int high = nums.length - 1;
            while (low < high) {
                int b = nums[low];
                int c = nums[high];
                int sum = a + b + c;
                if (sum == m) {
                    result.add(Arrays.asList(a, b, c));
                    low++;
                    high--;
                } else if (sum < m) {
                    low++;
                } else {
                    high--;
                }
            }
        }
        return result;
    }
    public static void main(String[] args) {
        int[] nums = {1, 5, 4, 12, 6, 7, 18, 2, 3, 50}; // assuming these are all unique
        int m = 10;
        List<List<Integer>> triplets = threeSum(nums, m);
        for (List<Integer> triplet : triplets) {
            System.out.println(triplet);
        }
    }
}
