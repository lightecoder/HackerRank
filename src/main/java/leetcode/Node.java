package leetcode;

public class Node {
    public Node left, right;
    public int data;

    public Node(int data) {
        this.data = data;
    }

    public boolean contains(int value) {
        if (value < data) {
            if (left != null) {
                return left.contains(value);
            } else {
                return false;
            }
        } else if (value > data) {
            if (right != null) {
                return right.contains(value);
            } else {
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
