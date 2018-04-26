package net.fcpsschools._1685666._3.lab._5_graph;

import java.io.File;
import java.util.Scanner;

/**
 * NAME: Zhiyu Zhu
 * PERIOD: 1
 * ASSIGNMENT: Adjacency List + DFS/BFS
 * DESCRIPTION: Driver class for Graph lab 5
 * DUE DATE: 2018/04/25 23:59:59
 * WHAT I LEARNED:
 * - It's called strongly connected for directed graph
 * HOW I FELT:
 * - Very hard to do the GUI. Only had time to fix a small bug.
 * WHAT I WONDER:
 * - How to integrate the visualizer to highlight the traversal.
 * CREDITS:
 * - Sirena Yu: Definition of Connected Components in Directed Graph
 */

public class EdgeList_Driver {
    public static void main(String[] args) throws Throwable {
        //////////////    for GRAPH 3 ///////////////////
        System.out.println("Graph 3:  Edge List Representation (UNWEIGHTED)! ");
        GraphAdjList_shell g = new GraphAdjList_shell();
        for (char name = 'A'; name <= 'D'; name++)
            g.addVertex("" + name);
        g.addEdge("A", "B");
        g.addEdge("B", "C");
        g.addEdge("C", "C");
        g.addEdge("C", "D");
        g.addEdge("D", "C");
        g.addEdge("D", "A");
        if (g.isConnected()) System.out.println("Connected");
        else System.out.println(g.connectedComponentsCount() + " connected components");
        System.out.println(g);

        //////////////    for GRAPH 4 ///////////////////
        System.out.println("\nGraph 4: Depth First Search");
        for (String name : g.getVertexMap().keySet())
            System.out.println(g.depthFirstSearch(name));

        System.out.println("\nGraph 4: Breadth First Search");
        for (String name : g.getVertexMap().keySet())
            System.out.println(g.breadthFirstSearch(name));


        //////////////    for GRAPH 5 ///////////////////

        System.out.println("\nGraph 5:  Edge List with Cities! ");
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter file of cities and edges: ");
        //cityEdgeList
        String fileOfCities = kb.next() + ".txt";
        Scanner sc = new Scanner(new File(fileOfCities));
        GraphAdjList_shell gVertexIsCityName = new GraphAdjList_shell();
        gVertexIsCityName.graphFromEdgeListData(fileOfCities);
        AZGraphVisualizer.display(gVertexIsCityName);


        System.out.println("\nAdjacency List");
        System.out.println(gVertexIsCityName);

        System.out.println("Number of edges: " + gVertexIsCityName.edgeCount());

        System.out.print("\nIs this graph connected? ");
        if (g.isConnected()) System.out.println("Connected");
        else System.out.println(g.connectedComponentsCount() + " connected components");
        System.out.print("Does this graph have cycles? " + gVertexIsCityName.hasCycle());

        System.out.println("\nDepth First Search");
        for (String name : gVertexIsCityName.getVertexMap().keySet())
            System.out.println(gVertexIsCityName.depthFirstSearch(name));


        while (true) {
            System.out.print("\nIs it reachable?  Enter start city (-1 to exit): ");
            String from = kb.next();
            if (from.equals("-1")) break;
            System.out.print("                    Enter end city: ");
            String to = kb.next();
            System.out.println(gVertexIsCityName.isReachable(from, to));
        }
    }

    /*  // Why is this even here?
        /////////////////////// ??? /////////////////////////////////
        for (Vertex v : g.getVertices())
            System.out.printf("DFS from vertex %s: %s\n",
                    v.getName(), g.depthFirstSearch(v.getName()));

        for (Vertex v : g.getVertices())
            System.out.printf("BFS from vertex %s: %s\n",
                    v.getName(), g.breadthFirstSearch(v.getName()));
    */
}
