package my.tasks;

public class ReverseLinkedList {

    public static void main(String[] args) {
        Node root = new Node(1, new Node(2, new Node(3, new Node(4, null))));
        System.out.println(root);
        System.out.println(revertList(root));
    }

    private static Node revertList(Node root) {
        Node pointer = root.next;
        root.next = null;
        while (pointer != null) {
            Node temp = pointer.next;
            pointer.next = root;
            root = pointer;
            pointer = temp;
        }
        return root;
    }
}

class Node {

    int item;
    Node next;

    public Node(int item, Node next) {
        this.item = item;
        this.next = next;
    }

    @Override
    public String toString() {
        return "[" +
               "item=" + item +
               ", next=" + next +
               ']';
    }
}

