package hackerrank;

public class BubbleAllZeroes {

    /*
    Given [1,3,0,6,0,0,-1,0]
    Expected [1,3,6,-1,0,0,0,0]
     */

    public int[] bubbleAllZeroesRight(int[] source) {
        int zeroPointer = 0;
        int pointer = 1;
        while (pointer < source.length) {
            if (source[zeroPointer] == 0) {
                if (source[pointer] != 0) {
                    swap(source, zeroPointer, pointer);
                    zeroPointer++;
                }
            }
            else {
                zeroPointer++;
            }
            pointer++;
        }

        return source;
    }

    private void swap(int[] source, int zeroPointer, int pointer) {
        int temp = source[zeroPointer];
        source[zeroPointer] = source[pointer];
        source[pointer] = temp;
    }

}



