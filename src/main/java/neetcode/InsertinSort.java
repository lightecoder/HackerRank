package neetcode;

import java.util.Arrays;

public class InsertinSort {
    public static void main(String[] args) {
        int[] arr = new int[]{3, 2, 6, 9, 1, 7, 3};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void sort(int[] arr) {
        for(int i = 0; i<arr.length; i++){
            for(int j=0; j<=i; j++){
                if(arr[i]<arr[j]){
                    swap(arr,i,j);
                }
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i]=arr[j];
        arr[j]=temp;
    }
}
