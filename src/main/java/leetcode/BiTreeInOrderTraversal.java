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
        printTree(root);
        System.out.println("#########-printInOrder-############");
        printInOrder(root);
        System.out.println("##########-printPreOrder-###########");
        printPreOrder(root);
        System.out.println("###########-printPostOrder-##########");
        printPostOrder(root);
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

    public static void printInOrder(Node root) {
        if (root == null) {
            return;
        }
        printInOrder(root.left);
        System.out.println(root.data);
        printInOrder(root.right);
    }

    public static void printPreOrder(Node root) {
        if (root == null) {
            return;
        }
        System.out.println(root.data);
        printPreOrder(root.left);
        printPreOrder(root.right);
    }

    public static void printPostOrder(Node root) {
        if (root == null) {
            return;
        }
        printPostOrder(root.left);
        printPostOrder(root.right);
        System.out.println(root.data);
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
