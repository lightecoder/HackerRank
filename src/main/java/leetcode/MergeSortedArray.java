package leetcode;

public class MergeSortedArray {
    public static void main(String[] args) {
        new MergeSortedArray().merge(new int[]{1, 2, 3, 0, 0, 0}, 3, new int[]{2, 5, 6}, 3);
    }


    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int tail = m + n - 1;
        for (int mIndex = m - 1, nIndex = n - 1; tail >= 0; ) {
            if (mIndex >= 0 && nIndex >= 0) {
                if (nums1[mIndex] > nums2[nIndex]) {
                    nums1[tail--] = nums1[mIndex--];
                } else {
                    nums1[tail--] = nums2[nIndex--];
                }
            } else if(mIndex >= 0){
                nums1[tail--] = nums1[mIndex--];
            } else{
                nums1[tail--] = nums2[nIndex--];
            }
        }
    }
}
