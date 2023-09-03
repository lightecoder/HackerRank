package leetcode;

public class MinMaxValueInBinarySearchTree {

    public static void main(String[] args) {
        MyNode root = new MyNode(15);
        root.insert(10);
        root.insert(8);
        root.insert(12);
        root.insert(20);
        root.insert(17);
        root.insert(25);
        //        printTree(root);
        //        printInorder(root);
        System.out.println(findMinValue(root));
        System.out.println(findMaxValue(root));
    }

    private static int findMinValue(MyNode root) {
        if (root.left == null) {
            return root.data;
        }
        return findMinValue(root.left);
    }

    private static int findMaxValue(MyNode root) {
        if (root.right == null) {
            return root.data;
        }
        return findMaxValue(root.right);
    }

}
