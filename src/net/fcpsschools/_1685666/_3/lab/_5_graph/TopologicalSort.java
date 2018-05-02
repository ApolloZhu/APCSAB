package net.fcpsschools._1685666._3.lab._5_graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Implement the topological sort algorithm,
 * using an array and a queue.
 */
public class TopologicalSort {
    // Identify the maximum in-degree of a used vertex.
    private static final int USED = -1;

    /**
     * Gets and returns the index of a vertex in a graph.
     *
     * @param v vertex.
     * @param g graph containing the vertex `v`.
     * @return index of vertex `v` in graph `g`.
     */
    private static int indexOf(Vertex v, GraphAdjList_shell g) {
        return g.getVertexMap().get(v.getName());
    }

    public static String topoSortArray(GraphAdjList_shell graph) {
        StringBuilder result = new StringBuilder();
        int count = graph.getVertices().size();
        // O(V + E) to initialize in-degree array
        int[] in = new int[count];
        for (int i = 0; i < count; i++) {
            Vertex current = graph.getVertex(i);
            for (Vertex neighbor : current.getAdjacencies()) {
                in[indexOf(neighbor, graph)]++;
            }
        }
        // O(V) to add all vertices to sorted result
        MAINLOOP:
        for (int i = 0; i < count; i++) {
            // O(V) for each iteration to
            for (int toUse = 0; toUse < count; toUse++) {
                if (0 == in[toUse]) { // find 0 in-degree vertex
                    Vertex current = graph.getVertex(toUse);
                    // Mark as used and add to output
                    in[toUse] = USED;
                    result.append(current).append(' ');
                    // Total of O(E) to reduce neighbor' in-degree
                    for (Vertex neighbor : current.getAdjacencies())
                        in[indexOf(neighbor, graph)]--;
                    continue MAINLOOP;
                }
            }
        }
        return result.toString();
    }

    public static String topoSortQueue(GraphAdjList_shell graph) {
        // We enqueue all vertices, assuming them having 0 in-degree
        Queue<Vertex> eligible = new LinkedList<>(graph.getVertices());
        int count = graph.getVertices().size();
        // O(V + E) to initialize in-degree array
        int[] in = new int[count];
        for (Vertex vertex : graph.getVertices()) {
            for (Vertex neighbor : vertex.getAdjacencies()) {
                // When we increase the in-degree of a vertex
                in[indexOf(neighbor, graph)]++;
                // It no longer has an in degree of 0
                eligible.remove(neighbor);
                // And it's ok to remove more than once
            }
        }
        StringBuilder result = new StringBuilder();
        Vertex current;
        // O(V + E) to sort. For next vertex of 0 in-degree
        while (null != (current = eligible.poll())) {
            // Mark it as visited/USED
            in[indexOf(current, graph)] = USED;
            result.append(current).append(' ');
            for (Vertex neighbor : current.getAdjacencies())
                // Enqueue neighbor of 0 in-degree after update
                if (0 == --in[indexOf(neighbor, graph)])
                    eligible.add(neighbor);
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
        System.out.printf("Graph: \n%s\n\n", graph);
        System.out.println("After topological sort: ");
        System.out.println("Array based: " + topoSortArray(graph));
        System.out.println("Queue based: " + topoSortQueue(graph));
    }
}
