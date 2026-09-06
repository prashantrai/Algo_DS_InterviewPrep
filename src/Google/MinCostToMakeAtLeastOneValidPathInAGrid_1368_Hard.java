package Google;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class MinCostToMakeAtLeastOneValidPathInAGrid_1368_Hard {

	static void runTest(int[][] grid, int expected, String name) {
        int actual = minCost(grid);
        System.out.println(name);
        System.out.println("Grid     : " + Arrays.deepToString(grid));
        System.out.println("Expected : " + expected);
        System.out.println("Actual   : " + actual);
        System.out.println(actual == expected ? "PASS\n" : "FAIL\n");
    }

    public static void main(String[] args) {
        // Edge case: single cell
        runTest(new int[][]{
                {1}
        }, 0, "Single cell");

        // Edge case: single row, already valid
        runTest(new int[][]{
                {1, 1, 1}
        }, 0, "Single row already valid");

        // Edge case: single row, all need changes except last
        runTest(new int[][]{
                {2, 2, 2}
        }, 2, "Single row needs changes");

        // Edge case: single column, already valid
        runTest(new int[][]{
                {3},
                {3},
                {3}
        }, 0, "Single column already valid");

        // Edge case: single column, needs changes
        runTest(new int[][]{
                {4},
                {4},
                {4}
        }, 2, "Single column needs changes");

        // Normal case: already has a valid path
        runTest(new int[][]{
                {1, 3},
                {1, 3}
        }, 0, "2x2 already valid");

        // Normal case: exactly one change needed
        runTest(new int[][]{
                {1, 2},
                {4, 3}
        }, 1, "2x2 one change needed");

        // Edge case: arrows point outside often
        runTest(new int[][]{
                {2, 2},
                {2, 2}
        }, 2, "Arrows pointing outside");
    }
	
	
	 /* Interview Explanation Before Coding
	 * 
	 * “I’ll model the grid as a graph where every cell is a node and we can move to
	 * its four neighboring cells.
	 * 
	 * The important observation is that an edge has only two possible costs. If I
	 * move in the direction currently stored in the cell, that move costs zero
	 * because I don’t need to change anything. Otherwise the move costs one because
	 * I would change that cell’s arrow.
	 * 
	 * Since all edge weights are either zero or one, instead of using a priority
	 * queue with Dijkstra, I can use 0-1 BFS with a deque.
	 * 
	 * I’ll keep a distance matrix containing the minimum modification cost to reach
	 * each cell. When I find a cheaper path to a neighbor, if that move costs zero
	 * I add the neighbor to the front of the deque, otherwise I add it to the back.
	 * 
	 * This ensures zero-cost transitions are processed earlier, so the deque
	 * behaves similarly to Dijkstra’s priority queue for 0/1 weights. The answer is
	 * the minimum distance to the bottom-right cell.”
	 */
	
	/* 
    Complexity
      Time: O(m*n) — Each cell is visited at most once for each edge weight (0 or 1).
        Since we process 0-weight edges before 1-weight edges (by adding to the front 
        of the deque), each cell gets its final shortest distance when it's first processed. 
        
        No cell is processed more than once with the same cost. Therefore, the time 
        complexity is linear with respect to the number of cells, giving us O(n⋅m).
        
      Space: O(m*n), algorithm uses a deque that in the worst case might contain all 
        cells of the grid, taking O(n⋅m) space. We also maintain the minCost array of 
        size n×m. Therefore, the total space complexity is O(n⋅m).
    */

    public static int minCost(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dist = new int[m][n];
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        dist[0][0] = 0;

        // direction encoding: 1=right, 2=left, 3=down, 4=up
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        Deque<int[]> deque = new ArrayDeque<>();
        deque.offerFirst(new int[]{0, 0});

        while (!deque.isEmpty()) {
            int[] cur = deque.pollFirst();
            int r = cur[0], c = cur[1];

            for (int dir = 0; dir < 4; dir++) {
                int nr = r + dirs[dir][0];
                int nc = c + dirs[dir][1];
                if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                    continue;
                }

                int cost = (grid[r][c] == dir + 1) ? 0 : 1;

                if (dist[r][c] + cost < dist[nr][nc]) {
                    dist[nr][nc] = dist[r][c] + cost;
                    if (cost == 0) {
                        deque.offerFirst(new int[]{nr, nc});
                    } else {
                        deque.offerLast(new int[]{nr, nc});
                    }
                }
            }
        }

        return dist[m - 1][n - 1];
    }
	

}

/*
 * # Key Insight
 * 
 * Treat every grid cell as a graph node, with up to four outgoing edges.
 * 
 * For each possible move:
 * 
 * if it follows the current cell's arrow, edge cost is 0; otherwise, edge cost
 * is 1.
 * 
 * Because every edge costs only 0 or 1, we can use a Deque: put zero-cost moves
 * at the front and one-cost moves at the back. This processes cheaper paths
 * first and gives us the shortest cost without needing a priority queue.
 * 
 * 
 * # Thought Process
 * 
 * A normal BFS is not enough because different moves have different costs.
 * 
 * Dijkstra would work because all costs are non-negative, but here the weights
 * have the special property that they are only 0 or 1.
 * 
 * That lets us simplify Dijkstra into 0-1 BFS:
 * 
 * cost 0 -> addFirst() cost 1 -> addLast()
 * 
 * We maintain the minimum cost found for every cell and relax neighboring cells
 * exactly like a shortest-path algorithm.
 * 
 */
 /* Interview Explanation Before Coding
 * 
 * “I’ll model the grid as a graph where every cell is a node and we can move to
 * its four neighboring cells.
 * 
 * The important observation is that an edge has only two possible costs. If I
 * move in the direction currently stored in the cell, that move costs zero
 * because I don’t need to change anything. Otherwise the move costs one because
 * I would change that cell’s arrow.
 * 
 * Since all edge weights are either zero or one, instead of using a priority
 * queue with Dijkstra, I can use 0-1 BFS with a deque.
 * 
 * I’ll keep a distance matrix containing the minimum modification cost to reach
 * each cell. When I find a cheaper path to a neighbor, if that move costs zero
 * I add the neighbor to the front of the deque, otherwise I add it to the back.
 * 
 * This ensures zero-cost transitions are processed earlier, so the deque
 * behaves similarly to Dijkstra’s priority queue for 0/1 weights. The answer is
 * the minimum distance to the bottom-right cell.”
 */