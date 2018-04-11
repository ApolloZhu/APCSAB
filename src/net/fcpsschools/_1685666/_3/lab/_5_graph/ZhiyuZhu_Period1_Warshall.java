package net.fcpsschools._1685666._3.lab._5_graph;

import java.io.File;
import java.util.Scanner;

public class ZhiyuZhu_Period1_Warshall {
    public static void main(String[] args) throws Throwable {
        Scanner kb = new Scanner(System.in);
        // Associate vertex names to indices
        System.out.print("Warshall's Algorithm! Enter file of names: ");
        // Name: cities
        String fileNames = kb.next() + ".txt";
        Scanner sc = new Scanner(new File(fileNames));
        // read the number of cities from the first line of the file cities.txt
        int size = sc.nextInt();
        //  create the graph represented by an adjacency matrix
        ZhiyuZhu_Period1_GraphAdjMat_shell g =
                new ZhiyuZhu_Period1_GraphAdjMat_shell(size);
        //  read the names of cities from cities.txt
        g.readNames(fileNames);
        // Read the graph contents from citymatrix.txt
        System.out.print("Enter file of the matrix: ");
        // Name of the matrix: citymatrix
        String fileGrid = kb.next() + ".txt";
        // use the matrix in citymatrix.txt to initialize the adjacency matrix
        g.readGrid(fileGrid);

        System.out.println("Adjacency Matrix");
        // display the graph--it is a directed graph
        g.displayGrid();


        System.out.println("Number of Edges: " + g.edgeCount());

        // Compute the reachability matrix
        System.out.println("\nReachability Matrix");
        // apply Warshall's algorithm to find all pairs reachability matrix;
        // aka the transitive closure matrix
        g.allPairsReachability();
        // display the <name, index> table for vertices in the graph
        g.displayVertices();
        // display the all pairs reachability matrix
        g.displayGrid();
        // display the edge count of the transitive closure matrix
        System.out.println("Number of Edges: " + g.edgeCount());


        //  EXTENSION part in Graph 1 handout: test if a city is reachable from another city
        System.out.println("\n EXTENSION");
        while (true) {
            System.out.print("Is it reachable?  Enter start city (-1 to exit): ");
            String from = kb.next();
            if (from.equals("-1"))
                break;
            System.out.print("                Enter end city: ");
            String to = kb.next();
            System.out.println(g.isEdge(from, to));
        }

        // Read from the all pairs reachable matrix the list of every city's reachable cities
        System.out.println("List of every city's reachable cities: ");
        for (String city : g.getVertices().keySet())
            System.out.println(city + "--> " + g.getReachables(city));

        // Get information from the all-pairs reachable matrix
        while (true) {
            System.out.print("List the reachable cities from: ");
            String from = kb.next();
            if (from.equals("-1"))
                break;
            System.out.println(g.getReachables(from));
        }
    }
}
