package leetcode;

import java.util.Arrays;

public class NextPermutation {

    public static void main(String[] args) {
//        nextPermutation2(new int[]{3,2,1});
        nextPermutation2(new int[]{5,4,7,5,3,2}); // expected [5,5,2,3,4,7]
//        nextPermutation2(new int[]{2,4,3,1,0});
    }

    public static void nextPermutation2(int[] nums) {
        System.out.println("input =" + Arrays.toString(nums));
        if (nums.length == 1) return;
        for (int i = nums.length - 2, j = i + 1; i >= 0; i--, j--) {
            if (nums[i] < nums[j]) {
                sortAsc(nums,j);
                for(int k =j; k<nums.length;k++){
                    if(nums[i]<nums[k]){
                        swap(nums,i,k);
                        System.out.println("output =" + Arrays.toString(nums));
                        return;
                    }
                }
            }
        }
        sortAsc(nums,0);
        System.out.println("output =" + Arrays.toString(nums));
    }

    public static void sortAsc(int[] nums, int fromIndex){
        for(int i = fromIndex, j = nums.length - 1, counter =0; counter < (nums.length-fromIndex)/2; i++, j--,counter++){
            swap(nums,i,j);
        }
    }

    public static void swap(int[] nums, int i, int j){
        int temp;
        temp = nums[i];
        nums[i]=nums[j];
        nums[j]=temp;
    }


    public static void nextPermutation1(int[] nums) {
        System.out.println("input =" + Arrays.toString(nums));
        if (nums.length == 1) return;
        int temp;
        boolean nextDigitFound = false;
        for (int i = nums.length - 2, j = i + 1; i >= 0; i--, j--) {
            if (nums[i] < nums[j]) {
                int[] subArr = Arrays.copyOfRange(nums, j, nums.length);
                Arrays.sort(subArr);
                for (int k = 0; k < subArr.length; k++, j++) {
                    nums[j] = subArr[k];
                    if (nums[i] < subArr[k] && !nextDigitFound) {
                        nextDigitFound = true;
                        temp = nums[i];
                        nums[i] = subArr[k];
                        nums[j] = temp;
                        System.out.println("output =" + Arrays.toString(nums));
                    }
                }
                return;
            }
        }
        Arrays.sort(nums);
        System.out.println("asc output =" + Arrays.toString(nums));
    }
}
