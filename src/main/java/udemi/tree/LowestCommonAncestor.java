package udemi.tree;

class Runner {
    public static void main(String[] args) {
        Node root = new Node("a");
        Node nodeB = new Node("b");
        Node nodeC = new Node("c");
        Node nodeD = new Node("d");
        Node nodeE = new Node("e");
        Node nodeF = new Node("f");
        Node nodeG = new Node("g");
        Node nodeH = new Node("h");
        Node nodeI = new Node("i");
        Node nodeJ = new Node("j");

        root.parent = null;
        nodeB.parent = root;
        nodeC.parent = root;
        nodeD.parent = nodeB;
        nodeE.parent = nodeB;
        nodeF.parent = nodeC;
        nodeG.parent = nodeC;
        nodeH.parent = nodeD;
        nodeI.parent = nodeE;
        nodeJ.parent = nodeE;

        LowestCommonAncestor lca = new LowestCommonAncestor();

        // test case 1
        Node lcaNode = lca.getLCA(nodeH, nodeI);
        System.out.println("The LCA of node 'h' and 'i' is: " + lcaNode.value); // should print 'b'

        // test case 2
        lcaNode = lca.getLCA(nodeF, nodeG);
        System.out.println("The LCA of node 'f' and 'g' is: " + lcaNode.value); // should print 'c'

        // test case 3
        lcaNode = lca.getLCA(nodeI, nodeJ);
        System.out.println("The LCA of node 'i' and 'j' is: " + lcaNode.value); // should print 'e'
    }
}

class Node {
    String value;
    Node parent;

    public Node(String value) {
        this.value = value;
        this.parent = null;
    }
}

class LowestCommonAncestor {

    public Node getLCA(Node p, Node q) {
        int depthP = getDepth(p);
        int depthQ = getDepth(q);
        //straight the levels for both Nodes on the same depth
        while (depthP > depthQ) {
            p = p.parent;
            depthP--;
        }

        while (depthQ > depthP) {
            q = q.parent;
            depthQ--;
        }
        //now Nodes on the same depth level, go up and find commonn ancestor
        while (p != q) {
            p = p.parent;
            q = q.parent;
        }

        return p;
    }

    private int getDepth(Node node) {
        int depth = 0;
        while (node != null) {
            node = node.parent;
            depth++;
        }
        return depth;
    }
}