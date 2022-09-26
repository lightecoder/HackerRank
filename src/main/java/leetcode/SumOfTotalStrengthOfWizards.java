package leetcode;

import java.util.Arrays;

public class SumOfTotalStrengthOfWizards {

    public static void main(String[] args) {
        int[] strength = new int[]{1, 3, 1, 2};
        int[] strength1 = new int[]{5, 4, 6};
        int[] strength2 =
                new int[]{747, 812, 112, 1230, 1426, 1477, 1388, 976, 849, 1431, 1885, 1845, 1070, 1980, 280, 1075, 232,
                          1330, 1868, 1696, 1361, 1822, 524, 1899, 1904, 538, 731, 985, 279, 1608, 1558, 930, 1232,
                          1497, 875, 1850, 1173, 805, 1720, 33, 233, 330, 1429, 1688, 281, 362, 1963, 927, 1688, 256,
                          1594, 1823, 743, 553, 1633, 1898, 1101, 1278, 717, 522, 1926, 1451, 119, 1283, 1016, 194, 780,
                          1436, 1233, 710, 1608, 523, 874, 1779, 1822, 134, 1984};
        //                System.out.println(
        //                        "Sum of Total " + Arrays.toString(strength) + ": " + new SumOfTotalStrengthOfWizards().totalStrength(
        //                                strength));
        //                System.out.println(
        //                        "Sum of Total " + Arrays.toString(strength1) + ": " + new SumOfTotalStrengthOfWizards().totalStrength(
        //                                strength1));
        System.out.println(
                "Sum of Total " + Arrays.toString(strength2) + ": " + new SumOfTotalStrengthOfWizards().totalStrength(
                        strength2));
    }

    public int totalStrength2(int[] strength) {
        int totalSum = 0;

        for (int i = 0; i < strength.length; i++) {
            for (int j = 0; j < strength.length - i; j++) {
                for (int k = 0; k <= i; k++) {

                }
            }
        }
        return totalSum;
    }

    public int totalStrength(int[] strength) {
        int totalSum = 0;
        int sum = Arrays.stream(strength).sum();
        int[][] minSumAggregator = new int[strength.length][2];

        for (int i = 0; i < strength.length; i++) {
            if (i == 10) {
                System.out.println();
            }
            for (int j = 0, k = i; j < strength.length - i; j++, k++) {
                if (minSumAggregator[j][0] != 0) {
                    minSumAggregator[j][0] = Math.min(minSumAggregator[j][0], strength[k]);
                    minSumAggregator[j][1] += strength[k];
                }
                else {
                    minSumAggregator[j][0] = strength[k];
                    minSumAggregator[j][1] = strength[k];
                }
                if (i == 76) {
                    System.out.println();
                }
                totalSum += (minSumAggregator[j][0] * minSumAggregator[j][1]);

                if (totalSum < 0) {
                    System.out.println();
                }
            }
        }
        return totalSum;
    }
}
