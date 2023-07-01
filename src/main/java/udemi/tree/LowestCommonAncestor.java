package udemi.tree;

import leetcode.Node;

import static leetcode.BiTreeInOrderTraversal.printTree;

public class LowestCommonAncestor {

    public Node lowestCommonAncestor(Node root, Node p, Node q) {
        // Base case: if the root is null or matches either p or q, return the root
        if (root == null || root == p || root == q) {
            return root;
        }

        // Recursively search for the LCA in the left and right subtrees
        Node left = lowestCommonAncestor(root.left, p, q);
        Node right = lowestCommonAncestor(root.right, p, q);

        // If both left and right subtrees have a valid LCA, then the current node is the LCA
        if (left != null && right != null) {
            return root;
        }

        // If only one of the subtrees has a valid LCA, return that LCA
        return (left != null) ? left : right;
    }

    public static void main(String[] args) {
        // Create a binary tree
        Node root = new Node(3);
        Node node5 = new Node(5);
        root.left = node5;
        root.right = new Node(1);
        Node node6 = new Node(6);
        root.left.left = node6;//
        root.left.right = new Node(2);
        root.right.left = new Node(0);
        root.right.right = new Node(8);
        root.left.right.left = new Node(7);
        Node node4 = new Node(4);
        root.left.right.right = node4;//

        printTree(root);

        LowestCommonAncestor lca = new LowestCommonAncestor();

        // Find the lowest common ancestor of nodes with values 5 and 1
        Node result = lca.lowestCommonAncestor(root, node4, node6);
        System.out.println("Lowest Common Ancestor: " + result.data); // Output: 3
    }


}
