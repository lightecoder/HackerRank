package leetcode;

import static leetcode.BiTreeInOrderTraversal.printInOrder;
import static leetcode.BiTreeInOrderTraversal.printTree;

public class CheckIfBinarySearchTree {

    public static void main(String[] args) {
        MyNode root = new MyNode(15);
        root.insert(10);
        root.insert(8);
        root.insert(12);
        root.insert(13);
        root.insert(20);
        root.insert(17);
        root.insert(25);
        printTree(root);
        printInOrder(root);
        System.out.println(isBinarySearchTree(root, -1));

    }

    private static boolean isBinarySearchTree(MyNode root, Integer prev) {
        if (root == null) {
            return true;
        }
        boolean left = isBinarySearchTree(root.left, prev);
        if (root.data < prev) {
            return false;
        }
        prev = root.data;
        boolean right = isBinarySearchTree(root.right, prev);
        return left && right;
    }

}
