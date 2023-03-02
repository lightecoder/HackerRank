package my.tasks;

/**
 * 1
 * 4 3
 * 4 9 6
 * output = 14
 */
public class TriangleMergeBottomToTop {
    public static void main(String[] args) {
        System.out.println(getMaxTriangleSum(new String[]{"1", "43", "496"}));
    }

    private static int getMaxTriangleSum(String[] arr) {
        int[] previous = arr[arr.length - 1].chars().map(x -> x - '0').toArray();
        int[] current = null;
        for (int i = arr.length - 2; i >= 0; i--) {
            current = arr[i].chars().map(x -> x - '0').toArray();
            for (int k = 1; k < previous.length; k++) {
                current[k - 1] += Math.max(previous[k - 1], previous[k]);
            }
            previous = current;
        }
        return current == null ? previous[0] : current[0];
    }
}
