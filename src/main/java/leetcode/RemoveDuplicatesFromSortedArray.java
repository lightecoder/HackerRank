package leetcode;

public class RemoveDuplicatesFromSortedArray {
    //          i         k
    // [0,1,2,3,4,2,2,3,3,4]  ->  [0,1,2,3,4,_,_,_,_,_] , return = 5
    public int removeDuplicates(int[] nums) {
        int i = 0;
        for (int k = 0; k < nums.length; k++) {
            if (nums[i] == nums[k]) continue;
            nums[++i] = nums[k];
        }
        return ++i;
    }
}
