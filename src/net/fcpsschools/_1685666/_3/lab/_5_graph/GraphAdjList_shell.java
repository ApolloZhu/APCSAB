package net.fcpsschools._1685666._3.lab._5_graph;
/**
 * Name: Zhiyu Zhu
 * Period: Period 1
 * Name of the Lab:
 * - Graph 3: Edge List Representation of Graph
 * - Graph 4: DFS and BFS
 * Purpose of the Program:
 * -[x] EdgeList
 * -[x] DFS-BFS
 * -[ ] EdgeListCities
 * Due Date: 2018/04/19 23:59:59
 * Date Submitted: 2018/04/19
 * What I learned:
 * - How to write DFS/BFS on graphs.
 * How I feel about this lab:
 * - It's OK.
 * What I wonder:
 * - How to do lab 5.
 * Most Difficult Method:
 * - The toString in Vertex class told me to print the
 * adjacency list, and I got stack overflow. Then I fixed it,
 * but I realized the output is of different format...
 * So finally I changed it back to just print the name of Vertex.
 * Credits:
 * - Retrieved and adapted from:
 * - http://en.literateprograms.org/Dijkstra's_algorithm_(Java)
 * Students whom I helped (to what extent):
 * - Bayan: explained why we might want to update map when adding an edge.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// Each vertex name links to its neighbors
class Vertex implements AZGraphVisualizer.AZVertex<Vertex> {
    // Since Vertex is not Comparable, we'll pass in
    private static Comparator<Vertex> comparator =
            // a comparator for tree set to sort the set.
            Comparator.comparing(Vertex::getName);

    // Name of the Vertex
    private String name;

    /**
     * This "list" stores the "destination" vertex of a "source" vertex.
     *
     * @apiNote We are using a set instead to filter duplicate.
     */
    private Set<Vertex> adjacencies;

    public Vertex(String argName) {
        this.name = argName;
    }

    public String toString() {
        return name;
    }

    // returns the name of the vertex, not its index
    public String getName() {
        return name;
    }

    // Returns the "list" that stores the
    // "destination" vertices of the "source" vertex
    // Accessor of instance variable adjacencies
    public Set<Vertex> getAdjacencies() {
        if (null == adjacencies)
            // Lazy initialization
            adjacencies = new TreeSet<>(comparator);
        return adjacencies;
    }
}

public class GraphAdjList_shell implements AZGraphVisualizer.AZGraph<Vertex> {
    // create a list of Vertex objects
    // 1. One ArrayList of Vertex and each vertex has a structure
    // (an ArrayList) that connects to an adjacent vertex list.
    private ArrayList<Vertex> vertices = new ArrayList<>();

    // map name of a vertex to an index
    // 2. One map that maps vertex string name to a vertex index.
    private Map<String, Integer> nameToIndex = new HashMap<>();

    // Accessor of vertices
    public List<Vertex> getVertices() {
        return vertices;
    }

    // Just to make the other program compile.
    public Map<String, Integer> getVertexMap() {
        return nameToIndex;
    }

    // Returns the vertex object that associates with the index i
    public Vertex getVertex(int i) {
        return vertices.get(i);
    }

    // use the name of a vertex to find the Vertex list
    // through which its adjacent edges can be found
    public Vertex getVertex(String vertexName) {
        try {
            return getVertex(nameToIndex.get(vertexName));
        } catch (Throwable ignored) {
            return null;
        }
    }

    // Add the vertex with name v to the vertex list
    public void addVertex(String v) {
        // b. add the vertex to the vertex-to-integer map
        nameToIndex.put(v, vertices.size());
        // a. add the new vertex to the vertex list
        vertices.add(new Vertex(v));
    }

    /**
     * Updating the string-to-index map and
     * add an edge to the vertex 'source' adjacency list.
     *
     * @param source
     * @param target
     * @return if the graph is updated.
     * @apiNote Vertex's adjacency list is implemented using a Set.
     */
    public boolean addEdge(String source, String target) {
        Vertex from = getNonnullVertex(source);
        Vertex to = getNonnullVertex(target);
        // With set, no need to worry about duplicate
        return from.getAdjacencies().add(to);
    }

    /**
     * Fetch the vertex with given name.
     * Will add that vertex if not currently exists.
     *
     * @param vertexName name of the vertex to retrieve.
     * @return vertex with given name in the graph.
     */
    private Vertex getNonnullVertex(String vertexName) {
        Vertex vertex = getVertex(vertexName);
        if (null != vertex) return vertex;
        // Add if needed
        addVertex(vertexName);
        return getVertex(vertexName);
    }

    // returns the string representation of the adjacency list
    public String toString() {
        StringBuilder sb = new StringBuilder();
        vertices.forEach(v -> sb
                .append(v.getName())
                .append(" -> ")
                .append(v.getAdjacencies())
                .append('\n')
        );
        return sb.toString().trim();
    }

    // MARK: - Lab 4

    // Non-recursive version of DFS
    // This method calls depthFirstSearch (Vertex v)
    public List<Vertex> depthFirstSearch(String name) {
        return depthFirstSearch(getVertex(name));
    }

    // returns a list of vertices visited during DFS
    private List<Vertex> depthFirstSearch(Vertex v) {
        List<Vertex> allReachable = new ArrayList<>(vertices.size());
        if (null == v) return allReachable;
        Stack<Vertex> next = new Stack<>();
        next.push(v);
        while (!next.isEmpty()) {
            Vertex current = next.pop();
            if (allReachable.contains(current)) continue;
            allReachable.add(current);
            // Push all on stack
            next.addAll(current.getAdjacencies());
        }
        return allReachable;
    }

    // Recursive version of DFS
    // This method calls depthFirstRecur (Vertex v)
    // returns a list of vertices visited during DFS
    public List<Vertex> depthFirstRecur(String name) {
        return depthFirstRecur(getVertex(name));
    }

    private List<Vertex> depthFirstRecur(Vertex v) {
        List<Vertex> allReachable = new ArrayList<>(vertices.size());
        if (null == v) return allReachable;
        depthFirstRecurHelper(v, allReachable);
        return allReachable;
    }

    // DFS Recur Helper
    // reachable: is the list of vertices "recorded" during the process of DFS
    private void depthFirstRecurHelper(Vertex v, List<Vertex> reachable) {
        if (reachable.contains(v)) return;
        reachable.add(v);
        v.getAdjacencies().forEach(neighbor ->
                depthFirstRecurHelper(neighbor, reachable));
    }

    //  Non-recursive version of  BFS
    // This method calls breadthFirstSearch (Vertex v)
    // returns a list of vertices visited during BFS
    public List<Vertex> breadthFirstSearch(String name) {
        return breadthFirstSearch(getVertex(name));
    }

    private List<Vertex> breadthFirstSearch(Vertex v) {
        List<Vertex> allReachable = new ArrayList<>(vertices.size());
        Queue<Vertex> next = new LinkedList<>();
        next.add(v);
        while (null != (v = next.poll())) {
            if (allReachable.contains(v)) continue;
            allReachable.add(v);
            // Enqueue all neighbors
            next.addAll(v.getAdjacencies());
        }
        return allReachable;
    }

    /********************************************************/
    /****************Graphs 5:  EdgeList with Cities  *********/
    /********************************************************/
    public void graphFromEdgeListData(String fileName) throws FileNotFoundException {
        try (Scanner file = new Scanner(new File(fileName))) {
            while (file.hasNextLine()) {
                String[] line = file.nextLine().trim().split("\\s+");
                addEdge(line[0], line[1]);
            }
        }
    }

    // count how many edges are in the graph.
    // What is the big-O?   ______O(|V|)_______
    public int edgeCount() {
        int count = 0;
        for (Vertex vertex : vertices)
            count += vertex.getAdjacencies().size();
        return count;
    }

    // What is the big-O?   _____O(|V|+|E|)____
    // What algorithm should we use to answer this question? DFS
    public boolean isReachable(String source, String target) {
        // get the path (a sequence of vertices) resulted from DFS (source is the start vertex)
        return depthFirstSearch(source)
                // if the target vertex is in that path ==> true
                // else                                 ==> false
                .contains(getVertex(target));
    }

    // Check if the graph is a connected graph or not.
    // Definition of a connected directed   graph  _________________________
    // Definition of a connected undirected graph   _________________________
    // For an undirected/digraph graph, a graph is connected
    // if FOR ALL PAIRS OF VERTICES u, v, there exists a path from u to v
    // Approach: for each pair of vertices (u, v) in the graph (should we use nested loops?),
    // see if v is reachable from u to v or not.
    public boolean isConnected() {
        int size = vertices.size();
        for (Vertex vertex : vertices)
            if (size > depthFirstSearch(vertex).size())
                return false;
        return true;
    }

    public boolean hasCycle() {
        Stack<Vertex> toVisit = new Stack<>();
        List<Vertex> visited = new LinkedList<>();
        Vertex current;
        for (Vertex vertex : vertices) {
            toVisit.clear();
            visited.clear();
            toVisit.push(vertex);
            while (!toVisit.isEmpty()) {
                current = toVisit.pop();
                if (visited.contains(current)) return true;
                visited.add(current);
                toVisit.addAll(current.getAdjacencies());
            }
        }
        return false;
    }
}
