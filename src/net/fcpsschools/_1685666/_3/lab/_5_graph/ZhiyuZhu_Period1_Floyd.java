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
import java.util.Scanner;

/**
 * @author Apollo/Zhiyu Zhu/朱智语
 */
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
