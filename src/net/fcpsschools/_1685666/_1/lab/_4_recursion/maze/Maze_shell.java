package net.fcpsschools._1685666._1.lab._4_recursion.maze;

import java.util.Scanner;

public class Maze_shell {
    private static int[][] grid = MazeCoder.encode(MazeCoder.EXAMPLE(), 0, 1, 3, 7);
    private static MazeSolver solver = new MazeSolver();
    private static MazeSolver.MSEventListener listener = new MazeSolver.MSEventListener() {
        @Override
        public void started(int r, int c, int tR, int tC, MazeCoder.Block[][] map) {
        }

        @Override
        public void tryout(int r, int c, MazeSolver.Direction direction, String path, MazeCoder.Block[][] map) {
        }

        @Override
        public void found(int tR, int tC, String path, MazeCoder.Block[][] map) {
            System.out.println(path);
        }

        @Override
        public void failed(int r, int c, String path, MazeCoder.Block[][] map) {
        }

        @Override
        public void ended(boolean hasPath, MazeCoder.Block[][] map) {
            grid = MazeCoder.encode(map, 0, 1, 3, 7);
        }
    };

    public static void main(String[] args) {
        // Assume that the exit of the maze is at
        // the lower right hand corner of the grid
        // display the maze
        solver.addEventListner(listener);
        MazeCoder.print(grid);
        Scanner input = new Scanner(System.in);

        System.out.println("Enter current location (x and y coordinates: ");
        int startX = input.nextInt();
        int startY = input.nextInt();

        while (!findAnExit(startX, startY)) {
            System.out.println("Still trapped inside!");
            MazeCoder.print(grid);

            System.out.println("Re-enter current location (x and y coordinates: ");
            startX = input.nextInt();
            startY = input.nextInt();
        }

        System.out.println("Successfully exit the maze!!!");

        // display the path (indicated by 7) that leads to the exit of the maze
        // also display locations tried
        MazeCoder.print(grid);
    }

    private static boolean findAnExit(int x, int y) {
        return solver.start(MazeCoder.decode(grid, 0, 1, 3, 7), x, y, grid.length - 1, grid[0].length - 1);
    }
}

/*
 7 8
 [7,8][7,9][7,10][7,11][7,12]
 Successfully exit the maze!!!

 3 3 3 0 3 3 0 0 0 3 3 3 3 
 3 0 3 3 3 0 3 3 3 3 0 0 3 
 0 0 0 0 3 0 3 0 3 0 1 0 0 
 3 3 3 0 3 3 3 0 3 0 0 1 1 
 3 0 3 0 0 0 0 3 3 3 0 0 1 
 3 0 3 3 3 3 3 3 0 3 3 3 0 
 3 0 0 0 0 0 0 0 0 0 0 0 0 
 3 3 3 3 3 3 3 3 7 7 7 7 7
*/

/*
 0 0
 [0,0][0,1][0,2][1,2][1,3][1,4][2,4][3,4][3,5][3,6][2,6][1,6][1,7][1,8][2,8][3,8][4,8][4,7][5,7][5,6][5,5][5,4][5,3][5,2]
 [4,2][3,2][3,1][3,0][4,0][5,0][6,0][7,0][7,1][7,2][7,3][7,4][7,5][7,6][7,7][7,8][7,9][7,10][7,11][7,12]
 Successfully exit the maze!!!

 7 7 7 0 1 1 0 0 0 1 1 1 1 
 3 0 7 7 7 0 7 7 7 1 0 0 1 
 0 0 0 0 7 0 7 0 7 0 1 0 0 
 7 7 7 0 7 7 7 0 7 0 0 1 1 
 7 0 7 0 0 0 0 7 7 1 0 0 1 
 7 0 7 7 7 7 7 7 7 7 7 7 7
*/

/*
 3 12
 no way out!

 1 1 1 0 1 1 0 0 0 1 1 1 1 
 1 0 1 1 1 0 1 1 1 1 0 0 1 
 0 0 0 0 1 0 1 0 1 0 1 0 0 
 1 1 1 0 1 1 1 0 1 0 0 3 3 
 1 0 1 0 0 0 0 1 1 1 0 0 3 
 1 0 1 1 1 1 1 1 0 1 1 1 0 
 1 0 0 0 0 0 0 0 0 0 0 0 0 
 1 1 1 1 1 1 1 1 1 1 1 1 1
 */
