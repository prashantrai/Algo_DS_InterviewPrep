package Amazon;

import java.util.ArrayDeque;
import java.util.Queue;

public class RottingOranges_994_Medium {
    public static void main(String[] args) {
        int[][][] tests = {
            {{2,1,1},{1,1,0},{0,1,1}},   // expected 4
            {{2,1,1},{0,1,1},{1,0,1}},   // expected -1
            {{0,2}},                     // expected 0
            {{1}},                       // expected -1
            {{2}},                       // expected 0
            {{0}},                       // expected 0
            {{2,2,2},{2,2,2}},           // expected 0
            {{1,1,1},{1,1,1}},           // expected -1
            {{2,1,1},{1,1,1},{1,1,2}},   // expected 2
            {{2,1,0,2},{1,1,1,0},{0,1,1,1}} // expected 4
        };

        int[] expected = {4, -1, 0, -1, 0, 0, 0, -1, 2, 4};

        for (int i = 0; i < tests.length; i++) {
            int result = orangesRotting(copyGrid(tests[i]));
            System.out.println("Test " + (i + 1) + ": got = " + result +
                               ", expected = " + expected[i] +
                               (result == expected[i] ? " ✅" : " ❌"));
        }
    }
    // Helper to avoid modifying original test grid
    private static int[][] copyGrid(int[][] grid) {
        int[][] copy = new int[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            copy[i] = grid[i].clone();
        }
        return copy;
    }
    
	// Solution starts here...
    
	/* 
    Key observation
        This is not normal BFS from one source.
        It is multi-source BFS because:
        - many rotten oranges may exist initially
        - all of them spread at the same time 
    */
    /* Interview script:
        - Since rot spreads to neighbors every minute, this is a 
        	shortest-time / level-order spread problem.
        - Because there can be multiple rotten oranges initially, 
        	I’ll use multi-source BFS.
        - I first add all rotten oranges to the queue and count fresh oranges.
        - Then each BFS level represents one unit i.e. minute.
        - Whenever I find a fresh neighbor, I rot it, decrease the fresh count, 
        	and push it into the queue.
        - At the end, if fresh count is still greater than 0, that means some 
        	oranges were unreachable, so return -1.
    */

    /* Step-by-step algorithm [multi-source BFS] ::
        - Traverse the grid once:
            count all fresh oranges
            add all rotten oranges to queue
        - If there are no fresh oranges, return 0
        - Run BFS:
            each loop over current queue size = one minute
            try 4 directions from each rotten orange
            if a neighbor is fresh:
                make it rotten
                decrease fresh count
                push into queue
        - After BFS:
            if fresh oranges still remain, return -1
            else return total minutes
    */
    
    // Time: O(m*n), every cell is visited at most once
    // Space: O(m*n), queue can hold many cells in worst case

    public static int orangesRotting(int[][] grid) {
        if(grid == null || grid.length == 0) {
            return 0;
        }
        int m = grid.length; 
        int n = grid[0].length;
        Queue<int[]> q = new ArrayDeque<>();
        int fresh = 0;

        // Step 1: collect all rotten oranges and count fresh oranges
        for(int i=0; i<m; i++) {
            for(int j=0; j<n; j++) {
                if(grid[i][j] == 2) {
                    q.offer(new int[]{i, j});
                } else if(grid[i][j] == 1) {
                    fresh++;
                }
            }
        }

        // No fresh oranges -> no time needed
        if (fresh == 0) return 0;

        int minutes = 0;
        int[][] dirs = {{1,0}, {0,1}, {-1,0}, {0,-1}};

        // Step 2: multi-source BFS
        while(!q.isEmpty() && fresh > 0) {
            int size = q.size();
            minutes++;  // one BFS level = one minute

            for(int i=0; i<size; i++) {
                int[] cell = q.poll();
                int r = cell[0];
                int c = cell[1];
                for(int[] d : dirs) {
                    int nr = r + d[0];
                    int nc = c + d[1];
                    // Only fresh oranges can become rotten
                    if(nr >= 0 && nr < m && nc >=0 && nc < n && grid[nr][nc] == 1) {
                        // mark as rotten
                        grid[nr][nc] = 2;
                        fresh--;
                        q.offer(new int[]{nr, nc});
                    }
                }

            }
        }
        // If fresh still remains, some oranges were unreachable
        return fresh == 0 ? minutes : -1;

    }

}
