package Amazon;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class MinimizeGridInconvenience_Amazon_OA_7Aug2026 {

	public static void main(String[] args) {
        int passed = 0;
        int total = 0;

        // Test 1: Problem description example
        // grid = [[0,0,0,1],[0,0,0,1]] -> optimal to convert (0,0), result inconvenience = 1
        total++;
        List<List<Integer>> grid1 = toGrid(new int[][]{
            {0, 0, 0, 1},
            {0, 0, 0, 1}
        });
        passed += runTest("Description example (2x4, two centers at col 3)",
                grid1, 1);

        // Test 2: Sample Case 0
        // n=3, m=4, all zeros -> expected output 2
        total++;
        List<List<Integer>> grid2 = toGrid(new int[][]{
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        });
        passed += runTest("Sample Case 0 (3x4, all zeros)",
                grid2, 2);

        // Test 3: Sample Case 1
        // n=1, m=1, grid = [[0]] -> expected output 0
        total++;
        List<List<Integer>> grid3 = toGrid(new int[][]{
            {0}
        });
        passed += runTest("Sample Case 1 (1x1, single zero)",
                grid3, 0);

        // Test 4: Failed test screenshot
        // n=2, m=4, grid = [[0,0,0,1],[0,0,0,1]] -> expected output 1 (previously got 0)
        total++;
        List<List<Integer>> grid4 = toGrid(new int[][]{
            {0, 0, 0, 1},
            {0, 0, 0, 1}
        });
        passed += runTest("Failed test screenshot (2x4, two centers at col 3)",
                grid4, 1);

        System.out.println();
        System.out.println(passed + " / " + total + " test cases passed");
    }

    private static int runTest(String name, List<List<Integer>> grid, int expected) {
        int actual = getMinInconvenience(grid);
        boolean ok = actual == expected;

        System.out.println((ok ? "PASS" : "FAIL") + " - " + name
                + " | expected=" + expected + " actual=" + actual);

        return ok ? 1 : 0;
    }

    private static List<List<Integer>> toGrid(int[][] arr) {
        List<List<Integer>> grid = new ArrayList<>();

        for (int[] row : arr) {
            List<Integer> rowList = new ArrayList<>();
            for (int value : row) {
                rowList.add(value);
            }
            grid.add(rowList);
        }

        return grid;
    }

    
    // Time: O(n × m × log(max(n,m)))
    // Space: O(n × m) 
    private static int getMinInconvenience(List<List<Integer>> grid) {
        int n = grid.size();
        int m = grid.get(0).size();

        int[][] dist = new int[n][m];
        for (int r = 0; r < n; r++) {
            Arrays.fill(dist[r], Integer.MAX_VALUE);
        }

        Queue<int[]> queue = new ArrayDeque<>();

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (grid.get(r).get(c) == 1) {
                    dist[r][c] = 0;
                    queue.offer(new int[]{r, c});
                }
            }
        }

        // 8 directions -> Chebyshev distance via BFS
        int[][] directions = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int r = current[0];
            int c = current[1];

            for (int[] direction : directions) {
                int nr = r + direction[0];
                int nc = c + direction[1];

                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && dist[nr][nc] == Integer.MAX_VALUE) {
                    dist[nr][nc] = dist[r][c] + 1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        // If there were no existing centers, every cell stays at MAX_VALUE,
        // which canAchieve correctly treats as "bad" for any limit.

        int high = n + m; // safe upper bound on achievable Chebyshev distance

        int low = 0;
        while (low < high) {
            int mid = low + (high - low) / 2;

            if (canAchieve(dist, n, m, mid)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }

    private static boolean canAchieve(int[][] dist, int n, int m, int limit) {
        int minR = Integer.MAX_VALUE;
        int maxR = Integer.MIN_VALUE;
        int minC = Integer.MAX_VALUE;
        int maxC = Integer.MIN_VALUE;

        boolean hasBadCell = false;

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (dist[r][c] > limit) {
                    hasBadCell = true;
                    minR = Math.min(minR, r);
                    maxR = Math.max(maxR, r);
                    minC = Math.min(minC, c);
                    maxC = Math.max(maxC, c);
                }
            }
        }

        if (!hasBadCell) {
            return true;
        }

        // New center (x, y) must satisfy |x - r| <= limit and |y - c| <= limit
        // for every bad cell (r, c). That gives a square range for x and y.
        int xLow = Math.max(0, maxR - limit);
        int xHigh = Math.min(n - 1, minR + limit);

        int yLow = Math.max(0, maxC - limit);
        int yHigh = Math.min(m - 1, minC + limit);

        return xLow <= xHigh && yLow <= yHigh;
    }
	
	
}


/**
Problem: Minimize Grid Inconvenience (Amazon Delivery Centers)

Description:

Amazon has multiple delivery centers all over the world. A city is given in the form of a grid 
where 1s are delivery centers and all other places are marked as 0. 

Distance between two cells is defined as the maximum absolute distance between x-coordinates and 
y-coordinates. 

For example, distance between (1,2) and (2,4) is max(|1-2|, |2-4|) = 2. 
(Note: this is Chebyshev distance, also known as chessboard distance.)

The inconvenience of the grid is defined as the maximum distance of any place marked 0 from its 
nearest delivery center.

Amazon is planning to open one new delivery center to reduce the inconvenience of the grid. 
Minimize the inconvenience of the grid by converting at most one 0 (any place) to 1 (a delivery center), 
and report this minimum value.


Example walkthrough (n=2 rows, m=4 columns):

grid = [[0,0,0,1],
		[0,0,0,1]]

Distances to nearest delivery centers:

3 2 1 0
3 2 1 0

Initial inconvenience = 3.

Converting (0,0) to a delivery center gives:

1 0 0 1
0 0 0 1

New inconvenience = 1, with distances:

0 1 1 0
1 1 1 0


Function Description:

Complete the function getMinInconvenience in the editor below.

getMinInconvenience has the following parameter(s):

int grid[n][m]: 2D binary matrix

Returns:

int: the minimum inconvenience possible

Constraints:

1 ≤ n, m ≤ 500
0 ≤ grid[i][j] ≤ 1

Sample Case 0:

Input (STDIN):

n = 3
m = 4
grid = [[0,0,0,0],[0,0,0,0],[0,0,0,0]]

Output: 2

Explanation: It is optimal to convert (1,1) to 1, resulting in:

0 0 0 0
0 1 0 0
0 0 0 0

Distance of each cell from its nearest 1:

1 1 1 2
1 0 1 2
1 1 1 2

(Max distance = 2)

Sample Case 1:

Input:

n = 1
m = 1
grid = [[0]]

Output: 0

Explanation: It is optimal to convert the only 0 to 1.
*/