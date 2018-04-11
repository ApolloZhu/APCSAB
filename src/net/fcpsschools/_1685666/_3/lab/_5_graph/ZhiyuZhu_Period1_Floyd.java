package net.fcpsschools._1685666._3.lab._5_graph;

import java.io.File;
import java.util.Scanner;

public class ZhiyuZhu_Period1_Floyd {
    public static void main(String[] args) throws Throwable {
        Scanner kb = new Scanner(System.in);
        System.out.print("Floyd's Algorithm! Enter file of names: ");
        //cities
        String fileNames = kb.next() + ".txt";
        Scanner sc = new Scanner(new File(fileNames));
        int size = sc.nextInt();
        ZhiyuZhu_Period1_GraphAdjMat_shell g =
                new ZhiyuZhu_Period1_GraphAdjMat_shell(size);
        g.readNames(fileNames);
        System.out.print("Enter file of the matrix: ");
        //citymatrixweighted
        String fileGrid = kb.next() + ".txt";

        g.readGrid(fileGrid);
        System.out.println("\nAdjacency Matrix");
        g.displayGrid();
        System.out.println("\nNumber of Edges: " + g.edgeCount());
        // Floyd's Algorithm
        g.allPairsWeighted();
        g.displayVertices();
        System.out.println("\nCost Matrix");
        // Display the weighted graph
        g.displayGrid();
        System.out.println("\nNumber of Edges: " + g.edgeCount());

        while (true) {
            System.out.print("\nWhat is the cost?  " +
                    "Enter start city (-1 to exit): ");
            String from = kb.next();
            if (from.equals("-1")) break;
            System.out.print("                Enter end city: ");
            String to = kb.next();
            System.out.println(g.getCost(from, to));
        }
    }
}
