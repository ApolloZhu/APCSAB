package net.fcpsschools._1685666._3.lab._5_graph;

import java.util.Scanner;

public class Graph0Driver {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter size of adjacency matrix: ");
        int size = kb.nextInt();
        GraphAdjMat_shell g = new GraphAdjMat_shell(size);
        System.out.println("Adjacency Matrix");
        g.displayGrid();
        System.out.println("Add edges, source<space>target<enter>."
                + "Enter -1 to end.");
        int source;
        while (-1 != (source = kb.nextInt())) {
            g.addEdge(source, kb.nextInt());
            g.displayGrid();
        }

        g.displayGrid();
        System.out.print("Remove an edge? ");
        if (kb.next().equalsIgnoreCase("Y")) {
            System.out.print("Remove which edge?  ");
            g.removeEdge(kb.nextInt(), kb.nextInt());
            g.displayGrid();
        }

        System.out.println("Number of edges: " + g.edgeCount());
        System.out.println("The neighbors of each vertex: ");
        for (int i = 0; i < size; i++)
            System.out.println(i + ": " + g.getNeighbors(i));
    }
}
