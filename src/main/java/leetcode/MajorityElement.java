package leetcode;

public class MajorityElement {

    /**
     * -109 <= nums[i] <= 109
     * Given an array nums of size n, return the majority element.
     * The majority element is the element that appears more than ⌊n / 2⌋ times. You may assume that the majority element always exists in the array.
     * Input: nums = [2,2,1,1,1,2,2]
     * Output: 2
     */

    public static void main(String[] args) {
        System.out.println(majorityElement(new int[]{3,2,3}));
    }

    public static int majorityElement(int[] nums) {
        int majority = nums[0];
        int counter = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == majority) {
                counter++;
            } else {
                counter--;
            }
            if(counter==0){
                majority = nums[i];
                counter++;
            }
        }

        return majority;
    }
}
