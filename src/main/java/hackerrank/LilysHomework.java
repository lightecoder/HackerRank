package hackerrank;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LilysHomework {
    public static void main(String[] args) {
        System.out.println(lilysHomework(new ArrayList<>(List.of(3, 4, 2, 5, 1))));//14253, 12453, 12354, 12345
        //
        Stream.of(1, 2, 3, 4, 5).mapToInt(x->x).average();
    }

    public static int lilysHomework(List<Integer> arr) {
        int counter = 0;
        int min;
        int index = 0;
        for (int i = 0; i < arr.size(); i++) {
            min = arr.get(i);
            for (int j = i; j < arr.size(); j++) {
                if (min > arr.get(j)) {
                    min = arr.get(j);
                    index = j;
                }
            }
            if (min < arr.get(i)) {
                int temp = arr.get(i);
                arr.set(i, min);
                arr.set(index, temp);
                counter++;
            }
        }
        return counter;
    }
}
