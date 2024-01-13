package neetcode;

public class RunnerBinarySearch {
    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 5, 7, 8, 10, 12, 15, 17, 18, 20};
        System.out.println(binarySearch(19, arr, 0, arr.length - 1));
    }

    private static int binarySearch(int target, int[] arr, int left, int right) {
        if (left > right) return -1;
        int middle = (left + right) / 2;
        if (target > arr[middle]) {
            return binarySearch(target,arr,middle+1, right);
        } else if (target < arr[middle]){
            return binarySearch(target,arr,left, middle-1);
        } else{
            return arr[middle];
        }
    }
}
