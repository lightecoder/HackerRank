package leetcode;

import static leetcode.BiTreeInOrderTraversal.printTree;

public class BinaryTreeHeight {

    public static void main(String[] args) {
        MyNode root = new MyNode(15);
        root.insert(10);
        root.insert(8);
        root.insert(12);
        root.insert(13);
        root.insert(20);
        root.insert(17);
        root.insert(25);
        root.insert(30);
        root.insert(27);
        root.insert(28);
        root.insert(16);
        root.insert(5);
        printTree(root);
        //        findTreeHeightBadSolution(root);
        //        System.out.println(height);
        System.out.println(findTreeHeight(root));
    }

    private static int findTreeHeight(MyNode root) {
        if (root == null) {
            return -1;
        }
        return Math.max(findTreeHeight(root.left), findTreeHeight(root.right)) + 1;
    }

    static int count = 0;
    static int height = 0;

    private static void findTreeHeightBadSolution(MyNode root) {
        if (root == null) {
            return;
        }
        findTreeHeightBadSolution(root.left);
        count++;
        findTreeHeightBadSolution(root.right);
        height = Math.max(height, count);
        count--;
    }

}
