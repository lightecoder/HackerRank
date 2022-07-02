package toptal;

import static java.util.Collections.reverseOrder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CarsSeats {

    public static void main(String[] args) {
        System.out.println(new CarsSeats().solution(new int[]{1, 2, 2, 1, 1}, new int[]{1, 3, 3, 4, 2}));// 2
    }

    public int solution(int[] P, int[] S) {
        int sumP = Arrays.stream(P).sum();
        int carsCounter = 0;
        List<Integer> arrS = Arrays.stream(S).boxed().sorted(reverseOrder()).collect(Collectors.toList());
        while (sumP > 0) {
            sumP -= arrS.get(carsCounter++);
        }
        return carsCounter;
    }
}
