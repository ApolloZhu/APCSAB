package net.fcpsschools._1685666._3.lab._5_graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Implement the topological sort algorithm,
 * using an array and a queue.
 */
public class TopologicalSort {
    public static final String INVALID = "Has cycle";
    private static final int USED = -1;

    private static int indexOf(Vertex v, GraphAdjList_shell g) {
        return g.getVertexMap().get(v.getName());
    }

    public static String topoSortArray(GraphAdjList_shell graph) {
        StringBuilder result = new StringBuilder();
        int count = graph.getVertices().size();
        int[] in = new int[count];
        for (int i = 0; i < count; i++) {
            Vertex current = graph.getVertex(i);
            for (Vertex neighbor : current.getAdjacencies()) {
                in[indexOf(neighbor, graph)]++;
            }
        }
        for (int i = 0; i < count; i++) {
            int toUse;
            for (toUse = 0; toUse < count; toUse++) {
                if (0 == in[toUse]) break;
            }
            in[toUse] = USED;
            Vertex current = graph.getVertex(toUse);
            for (Vertex neighbor : current.getAdjacencies()) {
                in[indexOf(neighbor, graph)]--;
            }
            result.append(current).append(' ');
        }
        return result.toString();
    }

    public static String topoSortQueue(GraphAdjList_shell graph) {
        Queue<Vertex> eligible = new LinkedList<>(graph.getVertices());
        StringBuilder result = new StringBuilder();
        int count = graph.getVertices().size();
        int[] in = new int[count];
        for (Vertex vertex : graph.getVertices()) {
            for (Vertex neighbor : vertex.getAdjacencies()) {
                in[indexOf(neighbor, graph)]++;
                // It no longer has an in degree of 0
                eligible.remove(neighbor);
            }
        }
        Vertex current;
        while (null != (current = eligible.poll())) {
            in[indexOf(current, graph)] = USED;
            for (Vertex neighbor : current.getAdjacencies())
                if (0 == --in[indexOf(neighbor, graph)])
                    eligible.add(neighbor);
            result.append(current).append(' ');
        }
        return result.toString();
    }

    public static void main(String[] args) {
        // The second graph on the classwork worksheet
        GraphAdjList_shell graph = new GraphAdjList_shell();
        String[] v = {null, "V1", "V2", "V3", "V4", "V5", "V6", "V7"};
        graph.addEdge(v[1], v[2]);
        graph.addEdge(v[1], v[3]);
        graph.addEdge(v[1], v[4]);
        graph.addEdge(v[2], v[4]);
        graph.addEdge(v[2], v[5]);
        graph.addEdge(v[3], v[6]);
        graph.addEdge(v[4], v[3]);
        graph.addEdge(v[4], v[6]);
        graph.addEdge(v[4], v[7]);
        graph.addEdge(v[5], v[4]);
        graph.addEdge(v[5], v[7]);
        graph.addEdge(v[7], v[6]);
        // Put comments
        // Make outputs easy to follow
        System.out.println(topoSortArray(graph));
        System.out.println(topoSortQueue(graph));
    }
}
