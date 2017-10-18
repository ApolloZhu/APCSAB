package net.fcpsschools._1685666._1.lab._4_recursion.maze;

/**
 * @author ApolloZhu, Pd. 1
 */
public class MazeCoder {
    private static final int VISITED = 3;
    private static final int PATH = 7;
    private static int[][] grid = {
            {1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1},
            {1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1},
            {0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0},
            {1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 1},
            {1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1},
            {1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    public static Block[][] EXAMPLE() {
        return decode(grid, 0, 1, VISITED, PATH);
    }

    public static Block[][] decode(int[][] intMap, int wall, int empty, int visited, int path) {
        Block[][] converted = new Block[intMap.length][intMap[0].length];
        for (int i = 0; i < intMap.length; i++)
            for (int j = 0; j < intMap[i].length; j++)
                if (intMap[i][j] == wall) converted[i][j] = Block.WALL;
                else if (intMap[i][j] == empty) converted[i][j] = Block.EMPTY;
                else if (intMap[i][j] == visited) converted[i][j] = Block.VISITED;
                else if (intMap[i][j] == path) converted[i][j] = Block.PATH;
        return converted;
    }

    public static int[][] encode(Block[][] map, int wall, int empty, int visited, int path) {
        int[][] converted = new int[map.length][map[0].length];
        for (int i = 0; i < map.length; i++)
            for (int j = 0; j < map[i].length; j++)
                if (map[i][j] == Block.WALL) converted[i][j] = wall;
                else if (map[i][j] == Block.EMPTY) converted[i][j] = empty;
                else if (map[i][j] == Block.VISITED) converted[i][j] = visited;
                else if (map[i][j] == Block.PATH) converted[i][j] = path;
        return converted;
    }

    public static void print(int[][] grid) {
        System.out.println(toString(grid));
    }

    public static String toString(int[][] grid) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++)
                sb.append(grid[i][j]).append(' ');
            sb.append("\n");
        }
        return sb.toString();
    }

    public enum Block {WALL, EMPTY, VISITED, PATH}
}
