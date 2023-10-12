package leetcode;

import java.util.Arrays;

public class RemoveDuplicatesFromSortedArray_II {

    //[0,0,1,1,1,1,2,3,3]  ->  Output: 7, nums = [0,0,1,1,2,3,3,_,_]
    public static void main(String[] args) {
        System.out.println(new RemoveDuplicatesFromSortedArray_II().removeDuplicates(new int[]{0, 0, 1, 1, 1, 1, 2, 3, 3}));
    }
    public int removeDuplicates(int[] nums) {
        int i = 0;
        for (int n : nums)
            if (i < 2 || n > nums[i - 2])
                nums[i++] = n;
        System.out.println(Arrays.toString(nums));
        return i;
    }

    public int removeDuplicates2(int[] nums) {
        int index = 0;
        int switcher = 1;
        for (int head = 0; head < nums.length; head++) {
            if (nums[index] == nums[head]) {
                if (switcher == 1 && index != head) {
                    nums[++index] = nums[head];
                    switcher++;
                }
                continue;
            }
            nums[++index] = nums[head];
            switcher = 1;
        }
        return ++index;
    }
}
