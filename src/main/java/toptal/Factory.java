package toptal;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class Factory {

    public static void main(String[] args) {
        System.out.println(new Factory().solution(new int[]{5, 19, 8, 1}));
        System.out.println(new Factory().solution(new int[]{10, 10}));
        System.out.println(new Factory().solution(new int[]{3, 0, 5}));
    }

    //O(nlog n)
    private int solution(int[] ints) {
        int filtersCounter = 0;
        PriorityQueue<Double> queue = new PriorityQueue<>(Collections.reverseOrder());
        //Total -> O(nlog n)
        for (int i : ints) {//O(n)
            queue.add((double) i); //O(log n)
        }
        double pollutions = Arrays.stream(ints).sum(); //O(n)
        double pollutionsLimit = (double) (Arrays.stream(ints).sum()) / 2; //O(n)
        //Total => O(n) * (O(log n) + O(log n)) => O(n) * O(2*log n) => O(nlog n)
        while (pollutions > pollutionsLimit) {//O(n)
            double half = queue.poll() / 2; //O(log n)
            pollutions -= half;
            queue.add(half); //O(log n)
            filtersCounter++;
        }
        return filtersCounter;
    }
}
