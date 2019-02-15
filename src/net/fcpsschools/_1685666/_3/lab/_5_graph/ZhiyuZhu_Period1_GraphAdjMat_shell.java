/*
MIT License

Copyright (c) 2017-2019 Apollo/Zhiyu Zhu/朱智语 <public-apollonian@outlook.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package net.fcpsschools._1685666._3.lab._5_graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Apollo/Zhiyu Zhu/朱智语
 * Period: 1
 * Name of the Lab: Warshall-Floyd
 * Purpose of the Program: Reachability
 * Due Date: 2018/04/12
 * Date Submitted: 2018/04/11
 * What I learned: N/A
 * How I feel about this lab: N/A
 * What I wonder: N/A
 * Most Difficult Method: N/A
 * Credits: N/A
 */
public class ZhiyuZhu_Period1_GraphAdjMat_shell {
    public static final int NO_EDGE = 9999;
    // list of vertex names
    ArrayList<String> nameList;
    // adjacency matrix representation
    private int[][] grid;
    // a map for associating vertex names to indices
    private Map<String, Integer> vertices;

    public ZhiyuZhu_Period1_GraphAdjMat_shell(int size) {
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

    // this method uses an adjacency matrix to determine
    // if there is an edge from "from" to "to"
    private boolean isEdge(int from, int to) {
        return 0 != grid[from][to] &&
                NO_EDGE != grid[from][to];
    }

    // display contents of ajacency matrix
    public void displayGrid() {
        for (int[] r : grid) {
            for (int v : r)
                System.out.printf("%-5d", v);
            System.out.println();
        }
    }

    // count how many edges are in the graph
    public int edgeCount() {
        int count = 0;
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++)
                if (isEdge(i, j))
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
                if (isEdge(sourceVertex, i))
                    neighbors.add(i);
        } finally {
            return neighbors;
        }
    }

    // use the vertex TreeMap to get the integer value for a vertex
    public boolean isEdge(String from, String to) {
        // you need to write this method
        try {
            return isEdge(vertices.get(from), vertices.get(to));
        } catch (Throwable ignored) {
            return false;
        }
    }

    //needed for the extension
    public Map<String, Integer> getVertices() {
        return vertices;
    }

    public void readNames(String fileName) throws Throwable {
        Scanner file = new Scanner(new File(fileName));
        int size = file.nextInt();
        file.nextLine();
        nameList = new ArrayList<>(size);
        // start reading names from the file: cities.txt
        // Do two things here:
        // 1. create a TreeMap that stores <name, index> pairs;
        // name of the map declared above is: vertices
        vertices = new TreeMap<>();
        // 2. update the vertex list:  nameList
        for (int i = 0; i < size; i++) {
            String city = file.nextLine().trim();
            nameList.add(city);
            vertices.put(city, i);
        }
    }

    public void readGrid(String fileName) throws FileNotFoundException {
        Scanner file = new Scanner(new File(fileName));
        int size = file.nextInt();
        file.nextLine();
        grid = new int[size][size];
        // use nested for loops to initialize the adjacency matrix
        // your code goes here...
        for (int i = 0; i < size; i++) {
            String[] values = file.nextLine()
                    .trim().split("\\s+");
            grid[i] = Arrays.stream(values)
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
    }

    // display the vertex map contents.
    // Refer to Graph 1 handout
    public void displayVertices() {
        for (String city : vertices.keySet())
            System.out.println(vertices.get(city) + "-" + city);
    }

    // Warshall extension
    public List<String> getReachables(String from) {
        List<Integer> reachables = getNeighbors(vertices.get(from));
        Map<Integer, String> reversed = new HashMap<>(vertices.size());
        for (String city : vertices.keySet())
            reversed.put(vertices.get(city), city);
        return reachables.stream().map(reversed::get)
                .collect(Collectors.toList());
    }

    // Implement the Warshall algorithm.
    // The algorithm generates the all pairs reachability matrix
    // (aka transitive closure matrix)
    public void allPairsReachability() {
        for (int vertex = 0; vertex < grid.length; vertex++)
            for (int in = 0; in < grid.length; in++)
                for (int out = 0; out < grid.length; out++)
                    if (2 == grid[in][vertex] + grid[vertex][out])
                        grid[in][out] = 1;
    }

    // Implement Floyd's algorithm to find
    // all pairs shortest path (the path cost) matrix
    public void allPairsWeighted() {
        for (int vertex = 0; vertex < grid.length; vertex++)
            for (int in = 0; in < grid.length; in++)
                for (int out = 0; out < grid.length; out++)
                    grid[in][out] = Math.min(grid[in][out],
                            grid[in][vertex] + grid[vertex][out]);
    }

    // Called from Floyd.java
    public int getCost(String from, String to) {
        return getCost(vertices.get(from), vertices.get(to));
    }

    public int getCost(int from, int to) {
        return grid[from][to];
    }
}
