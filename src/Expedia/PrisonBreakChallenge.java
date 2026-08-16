package Expedia;

import java.util.Arrays;

public class PrisonBreakChallenge {

	public static void main(String[] args) {

        // Example 1
        System.out.println(largestArea(
                6, 6,
                new int[]{2},
                new int[]{2}
        )); // 4

        // Example 2
        System.out.println(largestArea(
                6, 6,
                new int[]{2, 3},
                new int[]{2}
        )); // 6

        // Example 3
        System.out.println(largestArea(
                8, 9,
                new int[]{2, 3, 4, 7},
                new int[]{1, 2, 5}
        )); // 12

        // No walls removed
        System.out.println(largestArea(
                5, 5,
                new int[]{},
                new int[]{}
        )); // 1

        // Entire horizontal chain removed
        System.out.println(largestArea(
                5, 5,
                new int[]{1, 2, 3, 4},
                new int[]{}
        )); // 5

        // Unsorted input
        System.out.println(largestArea(
                10, 10,
                new int[]{6, 3, 4, 5},
                new int[]{8, 2, 1}
        )); // 15
    }

	
	/* Interview Script

		Let me restate the problem first. Removing a wall merges the two adjacent rows 
		or columns separated by that wall. If multiple removed walls are consecutive, 
		they merge an even larger continuous block. So the largest hole depends only 
		on the largest merged row block and the largest merged column block.

		Since the input only gives the removed wall indices, I don't need to model the 
		entire grid. I'll solve the horizontal and vertical dimensions independently. 
		For each array, I'll sort the wall indices and scan once to find the longest 
		consecutive sequence. If the longest consecutive run has length k, 
		it merges k + 1 rows or columns. Finally, I multiply the largest merged height 
		and width to get the maximum area.

		This approach is efficient because it only processes the removed walls. 
		The time complexity is dominated by sorting: O(H log H + V log V), and the 
		scan is linear. The extra space is O(1) apart from the sorting implementation. 
	 * */
	
	
	/* Time: O(H log H + V log V);  Space: O(1) */
	
	public static long largestArea(int n, int m, int[] h, int[] v) {
		
		// Find the maximum merged height after removing horizontal walls.
		int maxHeight = getLargestSegment(h);
		
		// Find the maximum merged width after removing vertical walls.
		int maxWidth = getLargestSegment(v);
		
		// Area can exceed int, so compute using long.
		return (long) maxHeight * maxWidth;
	}
	
	// Returns the largest merged segment size produced by removed walls.
    private static int getLargestSegment(int[] walls) {

    	// If no wall is removed, every segment remains size 1.
    	if(walls == null || walls.length == 0) return 1;
    	
    	// Sorting places consecutive wall indices together.
    	Arrays.sort(walls);
    	
    	// Current consecutive run length (number of removed walls).
    	int currentRun = 1;
    	
    	// Best run seen so far.
    	int longestRun = 1;
    	
    	// Extend the run whenever the next wall is exactly adjacent.
    	for(int i=1; i<walls.length; i++) {
    		if(walls[i] == walls[i-1] + 1) { // is consecutive walls
    			currentRun++;
    		} else {
    			// Sequence broke, start a new run.
    			currentRun = 1;
    		}
    		longestRun = Math.max(longestRun, currentRun);
    	}
    	
    	// k removed walls merge k+1 cells.
    	return longestRun + 1;
    }
	

}

/*
 * Prison Break Algorithm Challenge
	
	Problem Statement: 
	
	A prison floor consists of n × m cells.
	Initially, every pair of adjacent rows is separated by a horizontal wall, 
	and every pair of adjacent columns is separated by a vertical wall.
	
	You are given:
	
	h[]: indices of horizontal walls removed.
	v[]: indices of vertical walls removed.
	
	When consecutive walls are removed, adjacent cells merge into a larger rectangular hole.
	
	Return the area of the largest hole that can be formed.
	
	Example 1
	n = 6
	m = 6
	
	h = [2]
	v = [2]
	
	Output: 4
	
	Explanation: 
	Removing one horizontal wall joins 2 rows.
	Removing one vertical wall joins 2 columns.
	
	Largest hole: 2 × 2 = 4
	
	
	Example 2: 
	n = 6
	m = 6

	h = [2,3]
	v = [2]
	
	Output: 6
	
	Explanation: 
	Rows merged: 3 rows
	Columns merged: 2 columns
	
	Largest hole: 3 × 2 = 6
	
	Example 3
	n = 8
	m = 9
	
	h = [2,3,4,7]
	v = [1,2,5]
	
	
	Output: 12
	
	Explanation
	Longest consecutive horizontal removals: 2,3,4
	
	→ merge 4 rows.
	
	Longest consecutive vertical removals: 1,2
	
	→ merge 3 columns.
	
	Largest hole: 4 × 3 = 12
 */

/* Prison Break Algorithm Challenge

Problem Statement: 
A prison floor consists of an n × m grid of cells (n rows, m columns).
Initially, all adjacent rows and columns are separated by walls. 
There are n-1 horizontal walls and m-1 vertical walls.

You are given:
h[]: An array of integers representing the 1-based indices of horizontal walls removed.
v[]: An array of integers representing the 1-based indices of vertical walls removed.

When x consecutive walls are removed, x + 1 adjacent cells merge into a single larger rectangular hole. 

Return the area (as a 64-bit integer / long) of the largest continuous hole that can be formed.

Constraints:
- 1 ≤ n, m ≤ 10^9 (Note: Grid cannot be explicitly visualized/allocated in memory)
- 0 ≤ length of h ≤ n - 1
- 0 ≤ length of v ≤ m - 1
- Elements in h and v are unique but may be unsorted.

Example 1:
n = 6, m = 6
h = [2]
v = [2]
Output: 4
Explanation: Horizontal wall 2 is removed (merges rows 2 and 3 -> height 2). Vertical wall 2 is removed (merges columns 2 and 3 -> width 2). Max area = 2 × 2 = 4.

Example 2:
n = 6, m = 6
h = [2, 3]
v = [2]
Output: 6
Explanation: Consecutive horizontal walls 2 and 3 are removed (merges rows 2, 3, and 4 -> height 3). Vertical wall 2 is removed (width 2). Max area = 3 × 2 = 6.

Example 3:
n = 8, m = 9
h = [2, 3, 4, 7]
v = [1, 2, 5]
Output: 12
Explanation: Longest consecutive horizontal block is [2, 3, 4] (3 walls = height 4). Longest consecutive vertical block is [1, 2] (2 walls = width 3). Max area = 4 × 3 = 12.

 
 */