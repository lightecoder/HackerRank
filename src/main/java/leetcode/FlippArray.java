package leetcode;

import java.util.Arrays;

public class FlippArray {
    public static void main(String[] args) {

        int[][] arr2 = {
                {1, 2,},  //3,1
                {3, 4,}}; //4,2
        int[][] arr1 = {
                {1, 2, 3}, //7,4,1
                {4, 5, 6}, //8,5,2
                {7, 8, 9}};//9,6,3
        int[][] arr3 = {
                {1,   2,  3,  4}, //    {13,9, 5,1}
                {5,   6,  7,  8}, //->  {14,10,6,2}
                {9,  10, 11, 12},//     {15,11,7,3}
                {13, 14, 15, 16}};//    {16,12,8,4}
        int[][] arr = {
                {1,   2,  3,  4,  5,  6}, //    {31,25,19,13, 7,1}
                {7,   8,  9, 10, 11, 12}, //->  {32,26,20,14, 8,2}
                {13, 14, 15, 16, 17, 18}, //    {33,27,21,15, 9,3}
                {19, 20, 21, 22, 23 ,24}, //    {34,28,22,16,10,4}
                {25, 26, 27, 28, 29 ,30}, //    {35,29,23,17,11,5}
                {31, 32, 33, 34, 35 ,36}};//    {36,30,24,18,12,6}
        int temp1;
        int temp2;
        int length = arr.length;
        int firstIndex = 0;
        int lastIndex = length - 1;
        int iteration = 0;
        for(int m = 2; m < length; m++){
            for (int i = iteration, k = length - 1- iteration; i < length - 1-iteration && k > iteration; i++, k--) {
                temp1 = arr[i][lastIndex];
                arr[i][lastIndex] = arr[firstIndex][i];
                temp2 = temp1;
                temp1 = arr[lastIndex][k];
                arr[lastIndex][k] = temp2;
                temp2 = temp1;
                temp1 = arr[k][firstIndex];
                arr[k][firstIndex] = temp2;
                arr[firstIndex][i] = temp1;
            }
            iteration++;
            firstIndex++;
            lastIndex--;
        }

        for(int[] ar : arr){
            System.out.println(Arrays.toString(ar));
        }
    }
}
