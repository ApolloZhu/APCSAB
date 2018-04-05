/**
 * Name: Zhiyu hu
 * Period: 1
 * Name of the Lab: Graph + Adjacency Matrix
 * Purpose of the Program: Basic Graph Op
 * Due Date:
 * Date Submitted:
 * What I learned: N/A
 * How I feel about this lab: N/A
 * What I wonder: N/A
 * Most Difficult Method: N/A
 * Credits: N/A
 */

package net.fcpsschools._1685666._3.lab._5_graph;

import java.util.LinkedList;
import java.util.List;

public class GraphAdjMat_shell {
    private int[][] grid; //adjacency matrix representation


    public GraphAdjMat_shell(int size) {
        // They'll know about illegal arg.
        grid = new int[size][size];
    }

    // Note: addEdge only changes grid, not the vertex list
    public boolean addEdge(int source, int target) {
        try {
            if (isEdge(source, target))
                return false;
            grid[source][target] = 1;
            return true;
        } catch (Throwable ignored) {
            return false;
        }
    }

    // Note: removeEdge only changes grid, not the vertex list
    public boolean removeEdge(int source, int target) {
        try {
            if (isEdge(source, target)) {
                grid[source][target] = 0;
                return true;
            } else return false;
        } catch (Throwable ignored) {
            return false;
        }
    }

    private boolean isEdge(int from, int to) {
        return 0 != grid[from][to];
    }

    // display contents of ajacency matrix
    public void displayGrid() {
        for (int[] r : grid) {
            for (int v : r)
                System.out.print(v);
            System.out.println();
        }
    }

    // count how many edges are in the graph
    public int edgeCount() {
        int count = 0;
        for (int[] r : grid)
            for (int v : r)
                if (0 != v)
                    count++;
        return count;
    }

    // return neighbors of "source" in a List structure
    // the neighbors of a source vertex are all the vertices
    // reachable by traveling along one (1) edge.
    public List<Integer> getNeighbors(int sourceVertex) {
        List<Integer> neighbors = new LinkedList<>();
        try {
            for (int i = 0; i < grid[sourceVertex].length; i++)
                if (0 != grid[sourceVertex][i])
                    neighbors.add(i);
        } finally {
            return neighbors;
        }
    }

    // Note: There are other methods that
    // we don't include here for a graph:
    // addVertex, removeVertex
}
