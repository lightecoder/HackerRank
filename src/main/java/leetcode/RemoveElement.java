package leetcode;

public class RemoveElement {
    public static void main(String[] args) {// expected: [0,1,4,0,3]
        System.out.println(new RemoveElement().removeElement(new int[]{0, 1, 2, 2, 3, 0, 4, 2, 2}, 2));
    }

    public int removeElement(int[] nums, int val) {
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[index] = nums[i];
                index++;
            }
        }
        return index;
    }

    public int removeElement2(int[] nums, int val) {
        int counter = nums.length;
        for (int i = 0, j = nums.length - 1; i <= j; ) {
            if (nums[i] == val) {
                counter--;
                while (j > 0 && nums[j] == val && i != j) {
                    j--;
                    counter--;
                }
                nums[i] = nums[j--];
            }
            i++;
        }
        return counter;
    }
}
