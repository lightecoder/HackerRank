package leetcode;

public class BiTreeInOrderTraversal {
    public static void main(String[] args) {
        Node root = new Node(10);
        root.insert(5);
        root.insert(2);
        root.insert(8);
        root.insert(3);
        root.insert(15);
        root.insert(12);
        root.insert(18);
        printInorder(root);
        printTree(root);
        System.out.println("contains - " + root.contains(19));
    }

    private static String s = "";

    public static void printTree(Node root) {
        if (root == null) {
            return;
        }
        s += "   ";
        printTree(root.right);
        System.out.println(s + root.data);
        printTree(root.left);
        s = s.substring(0, s.length() - 3);
    }

    public static void printInorder(Node root) {
        if (root == null) return;
        printInorder(root.left);
        System.out.println(root.data);
        printInorder(root.right);
    }
}

class Node {
    Node left, right;
    int data;

    public Node(int data) {
        this.data = data;
    }

    public boolean contains(int value) {
        if (value < data) {
            if(left != null){
                return left.contains(value);
            }else{
                return false;
            }
        }else if(value > data){
            if(right != null) {
               return right.contains(value);
            } else{
                return false;
            }
        } else {
            return true;
        }
    }

    public void insert(int val) {
        if (val < data) {
            if (left == null) {
                left = new Node(val);
            } else {
                left.insert(val);
            }
        } else {
            if (right == null) {
                right = new Node(val);
            } else {
                right.insert(val);
            }
        }
    }

}
