package my.tasks;

import java.util.ArrayList;
import java.util.List;

public class ArrayLeftRotation {

    /*
     * Complete the 'rotLeft' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER_ARRAY a
     *  2. INTEGER d
     */
    public static void main(String[] args) {
        System.out.println(rotLeft(List.of(1,2,3,4), 2));
    }

    //given [1,2,3,4] -> [3,1,2,3] d=2, size = 4 ,
    public static List<Integer> rotLeft(List<Integer> a, int d) {
        List<Integer> resultArray = new ArrayList<>(); // Space O(n)
        int index = d;
        for (int i = 0; i < a.size(); i++) { //Time O(n)
            if (index < a.size()) {
                resultArray.add(a.get(index++));
            }
            if (index >= a.size()) {
                index = 0;
            }
        }
        return resultArray;
    }

}
