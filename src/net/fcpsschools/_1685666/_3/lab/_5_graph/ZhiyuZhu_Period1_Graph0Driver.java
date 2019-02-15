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

import java.util.Scanner;

/**
 * @author Apollo/Zhiyu Zhu/朱智语
 */
public class ZhiyuZhu_Period1_Graph0Driver {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter size of adjacency matrix: ");
        int size = kb.nextInt();
        ZhiyuZhu_Period1_GraphAdjMat_shell g =
                new ZhiyuZhu_Period1_GraphAdjMat_shell(size);
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
