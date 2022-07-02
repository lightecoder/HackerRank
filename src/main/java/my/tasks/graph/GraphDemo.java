package my.tasks.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class GraphDemo {

    public static void main(String[] args) {
        Graph graph = new Graph(1);
        fillGraph(graph);
        long start = System.nanoTime();
        System.out.println("DFS started...");
        Graph.Node target = graph.findNodeDFS(3);
        System.out.println("DFS time = " + (System.nanoTime() - start) + " -> " + target);
        start = System.nanoTime();
        System.out.println("BFS started...");
        target = graph.findNodeBFS(3);
        System.out.println("BFS time = " + (System.nanoTime() - start) + " -> " + target);
    }

    private static void fillGraph(Graph graph) {
        graph.addAdjacents(graph.getRoot(), Set.of(
                graph.getNode(2),
                graph.getNode(3),
                graph.getNode(4)
        ));
        graph.addAdjacents(graph.findNodeBFS(2), Set.of(
                graph.getNode(5),
                graph.getNode(6)
        ));
        graph.addAdjacents(graph.findNodeBFS(3), Set.of(
                graph.getNode(7),
                graph.getNode(8)
        ));
        graph.addAdjacents(graph.findNodeBFS(4), Set.of(
                graph.getNode(9),
                graph.getNode(8)
        ));
        graph.addAdjacents(graph.findNodeBFS(5), Set.of(
                graph.getNode(10)
        ));
        graph.addAdjacents(graph.findNodeBFS(6), Set.of(
                graph.getNode(11)
        ));
        graph.addAdjacents(graph.findNodeBFS(9), Set.of(
                graph.getNode(12)
        ));
        graph.addAdjacents(graph.findNodeBFS(8), Set.of(
                graph.getNode(13)
        ));
        graph.addAdjacents(graph.findNodeBFS(10), Set.of(
                graph.getNode(14)
        ));
        graph.addAdjacents(graph.findNodeBFS(13), Set.of(
                graph.getNode(12)
        ));
        graph.addAdjacents(graph.findNodeBFS(14), Set.of(
                graph.getNode(16)
        ));
        graph.addAdjacents(graph.findNodeBFS(16), Set.of(
                graph.getNode(11)
        ));
        graph.addAdjacents(graph.findNodeBFS(12), Set.of(
                graph.getNode(15)
        ));
        graph.addAdjacents(graph.findNodeBFS(15), Set.of(
                graph.getNode(11)
        ));
    }
}

class Graph {

    private Node root;

    public Node getRoot() {
        return root;
    }

    public Graph(int id) {
        this.root = new Node(id);
    }

    public Node findNodeDFS(int dest) {
        Set<Node> visited = new HashSet<>();
        return findNodeDFSRecursion(root, dest, visited);
    }

    private Node findNodeDFSRecursion(Node source, int destId, Set<Node> visited) {
        if (source.id == destId) {
            System.out.println("Node " + source.id);
            return source;
        }
        if (!visited.contains(source)) {
            System.out.println("Node " + source.id);
            visited.add(source);
            for (Node node : source.adjacents) {
                Node target = findNodeDFSRecursion(node, destId, visited);
                if (target != null) {
                    return target;
                }
            }
        }
        return null;
    }

    public Node findNodeBFS(int id) {
        if (id == root.id) {
            return root;
        }
        Set<Node> visited = new HashSet<>();
        LinkedList<Node> queue = new LinkedList<>(root.adjacents);

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            System.out.println("Node " + node.id);
            if (visited.contains(node)) {
                continue;
            }
            if (node.id == id) {
                return node;
            }
            visited.add(node);
            queue.addAll(node.adjacents);
        }

        return null;
    }

    public void addAdjacents(Node source, Set<Node> adjacents) {
        source.adjacents = adjacents;
    }

    public Node getNode(int id) {
        Node node = findNodeBFS(id);
        return node == null ? new Node(id) : node;
    }

    public static class Node {

        private int id;
        private Set<Node> adjacents = new HashSet<>();

        public Node(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Node{" +
                   "id=" + id +
                   '}';
        }
    }
}
