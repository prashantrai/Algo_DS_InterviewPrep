package Google.extremelyHigh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class RainDropFlow {

	// Main method starts
	public static void main(String[] args) {

        RainDropFlow sol = new RainDropFlow();

        // Test 1: Everything eventually exits.
        int[][] h1 = {
                {9, 6, 3},
                {8, 5, 2},
                {7, 4, 1}
        };

        System.out.println("Test 1");
        System.out.println("Expected: all OUT");
        System.out.println("Actual:");

        Cell[][] result1 = sol.findDestinations(h1);
        printResult(result1);

        /*
         Expected:
         [OUT, OUT, OUT]
         [OUT, OUT, OUT]
         [OUT, OUT, OUT]
        */

        System.out.println();


        // Test 2: Center is an interior sink.
        int[][] h2 = {
                {10, 10, 10},
                {10, 1, 10},
                {10, 10, 10}
        };

        System.out.println("Test 2");
        System.out.println("Expected:");
        System.out.println("Center (1,1) -> (1,1)");
        System.out.println("Boundary cells -> OUT");
        System.out.println("Actual:");

        Cell[][] result2 = sol.findDestinations(h2);
        printResult(result2);

        System.out.println();


        // Test 3: Single cell.
        int[][] h3 = {
                {5}
        };

        System.out.println("Test 3");
        System.out.println("Expected: OUT");
        System.out.println("Actual:");

        Cell[][] result3 = sol.findDestinations(h3);
        printResult(result3);

        System.out.println();


        // Test 4: Single zero-height cell.
        int[][] h4 = {
                {0}
        };

        System.out.println("Test 4");
        System.out.println("Expected: (0,0)");
        System.out.println("Actual:");

        Cell[][] result4 = sol.findDestinations(h4);
        printResult(result4);

        System.out.println();


        // Test 5: Multiple cells share the same sink.
        /*
         In this test consider: (0,1) = 5
			
			Its neighbors include:
			
			up    = OUT = 0
			left  = (0,0) = 0
			right = (0,2) = 0
			down  = (1,1) = 1
			
			The minimum is: 0
			but there are three equally steep choices:
			
			OUT
			(0,0)
			(0,2)
			
			That violates our base assumption: 
			There is a unique steepest destination at every step.
			
			Our current code still produces an answer because it implicitly 
			uses the direction iteration order, but that is accidental tie-breaking.
         * */
        int[][] h5 = {
                {0, 5, 0},
                {5, 1, 5},
                {0, 5, 0}
        };

        System.out.println("Test 5");
        System.out.println("Expected:");
        System.out.println("(1,1) -> (1,1)");
        System.out.println("Actual:");

        Cell[][] result5 = sol.findDestinations(h5);
        printResult(result5);
    }
	private static void printResult(Cell[][] result) {

        for (Cell[] row : result) {
            System.out.println(Arrays.toString(row));
        }
    }
	
	
	// Solution starts...
	
	/* Thought Process A straightforward thought would be:
	 * For every cell: simulate the raindrop until it stops.
	 * But that repeats a lot of work.
	 * 
	 * Suppose: A -> B -> C -> D -> OUTSIDE 
	 *      		 ^ 
	 * 			E ---|
	 * 
	 * If we already computed: destination(B) = OUTSIDE
	 * then when processing A or E, we should not recompute: B -> C -> D -> OUTSIDE
	 * We can reuse the result.
	 * 
	 * That immediately suggests: DFS + memoization
	 * 
	 * For every cell: dfs(r, c)
	 * returns the final destination of a drop starting there.
	 * 
	 * Once calculated: memo[r][c]
	 * 
	 * stores the answer permanently.
	 */ 
	 /* Key Observation: Every move goes to a strictly lower height.
	 * Therefore: height(next) < height(current)
	 * A path can never return to a previous cell.
	 * 
	 * So: cycles are impossible
	 * That means we do not need a visited set for cycle detection in the base
	 * problem.
	 * 
	 * This is an important point to mention during the interview.
	 */ 
	 /* Interview Script: 
	 * 
	 * I'll treat every cell as a node whose next node is its steepest strictly
	 * lower neighbor. Since height strictly decreases after every move, there
	 * cannot be a cycle.
	 * 
	 * A naive simulation from every cell can repeatedly traverse the same suffix of
	 * a path, so I'll use DFS with memoization. dfs(r,c) will return the final
	 * destination of the drop starting at that cell.
	 * 
	 * I'll first check the four directions and find the lowest valid neighbor,
	 * treating an out-of-bounds neighbor as height 0. If no neighbor is strictly
	 * lower, the current cell is a sink. If the best move goes outside, the
	 * destination is OUTSIDE. Otherwise I'll recursively compute the destination of
	 * the next cell and memoize it.
	 * 
	 * Since every cell is solved once and each solve checks only four directions,
	 * the total time is O(m*n).
	 */
	
	/* Step-by-Step Algorithm
		For every cell (r, c):
		
		Step 1: If its destination is already memoized: return memo[r][c];
		Step 2:  Start with: bestHeight = current height
		This means initially we assume the drop stays at the current cell.
		
		Step 3: Check: up, down, left right
		For each direction:
		
		if neighbor is outside:
		    neighbor height = 0
		else:
		    neighbor height = height[nr][nc]
		
		If that height is strictly lower than our current best: 
			update best neighbor
		
		Step 4:  After examining all four directions:
			If we found no lower cell: current cell is a local minimum
		
		Therefore: destination = (r, c)
		
		Step 5: If the best neighbor is outside: destination = OUTSIDE
		
		Step 6: Otherwise: destination = dfs(bestRow, bestCol)
		
		Store it: memo[r][c] = destination
		
		and return it.
	 * */
	
	/* Complexity Analysis: 
		Let: N = m * n

		For each cell, we check exactly four directions.
		
		Therefore:
		
		Neighbor checks: O(4N) = O(N) = O(m * n)
		
		Each cell's DFS result is computed only once because of memoization.
		
		Time: O(4 * m * n) = O(m * n)
		
		Space: 
		Memoization: O(m * n)
		Worst-case recursive call stack: O(m * n)
		
		For example, the entire matrix could form one long descending path.
		
		Overall auxiliary space: O(m * n)
	 * */
	
	/* TIP : REMEMBER: The essential implementation is only:
		findDestinations(), dfs()
		isValid(), Cell
	 * */
	
	static class Cell {
		int r, c;
		Cell(int r, int c) {
			this.r = r; this.c = c;
		}
		@Override
        public String toString() {
            if (r == -1 && c == -1) {
//                return "OUT";
                return "("+r+","+c+")";
            }
            return "(" + r + "," + c + ")";
        }
	}
	
	private static final int[][] DIRS = {
            {-1, 0}, // up
            {1, 0},  // down
            {0, -1}, // left
            {0, 1}   // right
    };
	
	
	// Tell below to interviewer: 
	// For simplicity purpose, we are currently considering all
	// outside coordinates as [-1, -1]
	static final Cell OUTSIDE = new Cell(-1, -1);
	
	
	public Cell[][] findDestinations(int[][] height) {
		int rows = height.length;
		int cols = height[0].length;
		
		Cell[][] memo = new Cell[rows][cols];
		
		for(int r=0; r<rows; r++) {
			for(int c=0; c<cols; c++) {
				dfs(height, r, c, memo);
			}
		}
		
		return memo;
	}
	
	private Cell dfs(int[][] height, int r, int c, Cell[][] memo) {
		//Already computed
		if(memo[r][c] != null) return memo[r][c];
		
		// Consider current cell has the best height
		int bestHeight = height[r][c];
		
		int nextR = r;
		int nextC = c;
		
		boolean isOutside = false; // outside of matrix
		
		// Start examining all four directions
		for(int[] dir : DIRS) {
			int nr	 = r + dir[0];
			int nc = c + dir[1];
			
			// if neighbor is outside
			// Outside the matrix has height 0.
			if(!isValid(height, nr, nc)) {
				if(0 < bestHeight) {
					bestHeight = 0;
					isOutside = true;
				}
				continue;
			}
			
			// When not outside, then check if it's the 
			// less than bestHeight, if yes, this (height[nr][nc]) becomes 
			// the best height (as it's lower and water will move in that direction)
			if(height[nr][nc] < bestHeight) {
				bestHeight = height[nr][nc]; 
				
				// we only update the next location if we discover
				// neighborHeight < bestHeight
				nextR = nr;
				nextC = nc;
				
				isOutside = false;
			}
		}
		
		// Lowest move is outside the matrix.
		if(isOutside) {
			memo[r][c] = OUTSIDE;
			return memo[r][c]; 
		}
		
		// If we no lower cell found, current cell is a local minimum, i.e. a sink
		if(r == nextR && c == nextC) {
			memo[r][c] = new Cell(r, c);
			return memo[r][c];
		}
		
		// Otherwise perform dfs for next cell
		memo[r][c] = dfs(height, nextR, nextC, memo);
		
		return memo[r][c];
	}
	 
	
	
	private boolean isValid(int[][] height, int r, int c) {
		int rows = height.length;
		int cols = height[0].length;
		
		return r >= 0 && r < rows
                && c >= 0 && c < cols;
	}

	
	/** Follow-up 1 : — Avoid Recomputing Shared Paths : No code change needed */
	
	
	/** Follow-up 2 — Multiple Equally Steep Downhill Neighbors */
	
	/* Follow-up 2 - Variant A — Deterministic tie-breaking
	 	Suppose the rule is:

		If multiple neighbors have the same lowest height, choose by priority:
		up
		left
		down
		right
		
		Example:
		      3
		      ↑
		3 ←   7   → 3
		      ↓
		      8
		
		Both up and left have height 3.
		
		Because up has higher priority:
		
		7 -> up
		
		and we completely ignore the left path.
		
		Can we achieve this with minimal changes?
		
		Yes. Very minimal.
		
		Your current code already behaves like deterministic tie-breaking because 
		it only replaces the current best when it finds a strictly lower value:
		
		if (h[nr][nc] < bestHeight) {
		    bestHeight = h[nr][nc];
		    nextR = nr;
		    nextC = nc;
		}
		
		If another neighbor later has the same height:
		
		h[nr][nc] == bestHeight
		
		we do nothing.
		
		So the first minimum encountered wins.
		
		Therefore, all you really need is to arrange DIRS in the required priority order.
		
		For example:
		private static final int[][] DIRS = {
		        {-1, 0}, // up
		        {0, -1}, // left
		        {1, 0},  // down
		        {0, 1}   // right
		};
		
		Now this:
		
		      3
		      ↑
		3 ←   7
		
		works like:
		
		check up:
		    3 < 7
		    best = 3
		    choose up
		
		check left:
		    3 < 3? No
		
		keep up
		
		So for Variant A, practically no algorithm change is needed.
		
		You can tell the interviewer:
		
		Since tie-breaking is deterministic, each cell still has exactly 
		one next cell. I can keep the same DFS and memoization. I only need 
		to process neighbors in the requested priority order and keep the first minimum.
		
		Complexity remains:
		
		Time:  O(m * n)
		Space: O(m * n) 
	 * */
	
	/* Follow-up 2 - Variant B — Water can split
	 	This is the more interesting follow-up.

		Now if several neighbors have the same lowest height:
		      3
		      ↑
		3 ←   7
		
		water follows both.
		
		So instead of: 7 -> up
		
		we have:
		      up 3
		     /
		7 ---
		     \
		      left 3
		
		Those two paths could eventually lead to different sinks:
		
		        B -> Sink1
		       /
		A ----
		       \
		        C -> Sink2
		
		Therefore: destination(A) is no longer one Cell.
		
		It might be: {Sink1, Sink2}
		
		That is the biggest conceptual change.
		
		
	 *	What Changes in Our Current Solution?

		Currently: Cell[][] memo;
		
		means: Every starting cell has exactly one final destination.
		
		For splitting water, change it conceptually to: 
		Set<Cell>[][] memo;
		
		Now: memo[r][c]
		
		contains all destinations reachable from (r,c).
		
		For example: memo[A] = {Sink1, Sink2}
	  
	 * */
	
	// Adding here for reference only. This is a working dfs part of the follow-up
	// For complete implementation refer: RainDropFlow_WaterSplitWhenMultipleEqualDownhillNeighbor_FollowUp2_Variant2.java
	private Set<Cell> dfs(int[][] height, int r, int c, Set<Cell>[][] memo) {
		//Already computed
		if(memo[r][c] != null) { 
			return memo[r][c];
		}
		
		// Consider current cell has the best height
		int bestHeight = height[r][c];
		
		List<Cell> next = new ArrayList<>(); // Follow-up 2: Water split
		
		// Follow-up 2: Water split
		// Start examining all four directions
		for(int[] dir : DIRS) {
			int nr = r + dir[0];
			int nc = c + dir[1];
			
			int nextHeight;	// Follow-up 2: Water split
			Cell nextCell;	// Follow-up 2: Water split
			
			// Outside the matrix has height 0.
			if(!isValid(height, nr, nc)) {
				nextHeight = 0;
				nextCell = OUTSIDE;
			}
			// when inside grid, get the neighbor
			else { 
				nextHeight = height[nr][nc];
				nextCell = new Cell(nr, nc);
			}
			
			/* Logic here is: 
			 lower than current best
			    -> this is now the best
			    -> throw away old candidates
			
			 equal to current best
			    -> another equally steep path
			    -> keep it too
			 * */
			
			// Found a strictly better downhill height.
			if(nextHeight < bestHeight) {
				bestHeight = nextHeight;
				next.clear();
				next.add(nextCell);
			}
			// Another neighbor ties for the same best downhill height.
			else if(nextHeight == bestHeight && bestHeight < height[r][c]) {
				next.add(nextCell);
			}
		}
		
		// No strictly lower neighbor -> current cell is a sink.
		if(next.isEmpty()) {
			Set<Cell> result = new LinkedHashSet<>();
			result.add(new Cell(r,c));
			memo[r][c] = result;
			
			return memo[r][c];
		}
		
		// Water Split, i.e. follows every equally steep best neighbor.
		Set<Cell> result = new LinkedHashSet<>();
		for(Cell cell : next) {
			if(cell == OUTSIDE) {
				result.add(OUTSIDE);
			} 
			else {
				Set<Cell> res = dfs(height, cell.r, cell.c, memo); 
				result.addAll(res);
			}
		}
		
		memo[r][c] = result;
		
		return memo[r][c];
		
	}
	
	
	
	
	private boolean __;// this is just to enable the collapse option in comment block.
	
	
}


/* # Rain Drop Flow / 2D Height Map 

	**Reported:** September 4, 2026
	**Company:** Google
	**Round:** Technical interview, exact round not identified
	**Priority:** **EXTREMELY HIGH**
	**Difficulty:** Medium → Medium-Hard with follow-ups
	
	## Problem Statement
	
	You are given an `m x n` matrix:
	
	```text
	height[r][c]
	```
	
	where `height[r][c]` represents the elevation of cell `(r, c)`.
	
	Assume that **one raindrop starts independently at every cell**.
	
	From a cell `(r, c)`, the drop considers its four orthogonal neighbors:
	
	```text
	up
	down
	left
	right
	```
	
	There is no diagonal movement.
	
	The drop always moves to the neighbor having the **lowest height**, provided that neighbor is **strictly lower** than the current cell.
	
	Positions immediately outside the grid are considered to have height `0`. Therefore, a boundary cell may allow water to leave the matrix if moving outside is the steepest downhill move.
	
	If there is no strictly lower neighbor, the drop stops at its current cell.
	
	For every cell in the matrix, return the **final destination** of the raindrop that starts there.
	
	A destination can therefore be:
	
	```text
	1. A cell inside the matrix where the drop gets stuck.
	2. A position immediately outside the matrix where the drop exits.
	```
	
	For the base problem, assume there is a **unique steepest destination at every step**. In other words, ties do not need to be handled initially.
	
	---
	
	## Example 1 — Paths Merge
	
	```text
	height =
	
	[
	  [9, 6, 3],
	  [8, 5, 2],
	  [7, 4, 1]
	]
	```
	
	Consider several starting cells:
	
	```text
	(0,0) height 9
	   ↓
	(0,1) height 6
	   ↓
	(0,2) height 3
	   ↓
	(1,2) height 2
	   ↓
	(2,2) height 1
	   ↓
	outside height 0
	```
	
	Another drop:
	
	```text
	(1,1) height 5
	   ↓
	(1,2) height 2
	   ↓
	(2,2) height 1
	   ↓
	outside
	```
	
	Notice that the paths merge at `(1,2)`.
	
	Once we know:
	
	```text
	destination(1,2) = outside
	```
	
	we should **reuse that result** instead of recomputing the remaining path.
	
	All cells in this example eventually drain outside.
	
	---
	
	# Example 2 — Interior Sink
	
	Suppose outside cells are reachable only by moving orthogonally one position beyond a boundary.
	
	```text
	height =
	
	[
	  [10, 10, 10],
	  [10,  1, 10],
	  [10, 10, 10]
	]
	```
	
	From the center:
	
	```text
	(1,1) = 1
	```
	
	all four neighbors have height `10`.
	
	There is no lower neighbor, so:
	
	```text
	destination(1,1) = (1,1)
	```
	
	The center is a **sink/local minimum**.
	
	For a corner such as `(0,0)`:
	
	```text
	height = 10
	```
	
	an adjacent outside position has height `0`, so the drop exits the matrix.
	
	Thus different cells may have different destinations.
	
	---
	
	# Example 3 — Shared Sink
	
	```text
	height =
	
	[
	  [8, 7, 8],
	  [7, 1, 7],
	  [8, 7, 8]
	]
	```
	
	Ignoring boundary exits for a moment, several cells would flow toward:
	
	```text
	(1,1) = 1
	```
	
	For example:
	
	```text
	(0,1) 7
	   ↓
	(1,1) 1
	```
	
	and:
	
	```text
	(1,0) 7
	   →
	(1,1) 1
	```
	
	Both paths have the same final destination.
	
	This illustrates why computing the path independently from every cell can perform a lot of duplicate work.
	
	---
	
	# Example 4 — Why Memoization Matters
	
	Consider a long decreasing path:
	
	```text
	9 → 8 → 7 → 6 → 5 → 4 → 3 → 2 → 1
	```
	
	Without memoization:
	
	```text
	starting from 9 -> traverse 9 cells
	starting from 8 -> traverse 8 cells
	starting from 7 -> traverse 7 cells
	...
	```
	
	That can approach quadratic work.
	
	With memoization:
	
	```text
	destination(1) = X
	destination(2) = destination(1)
	destination(3) = destination(2)
	...
	```
	
	Each cell's destination only needs to be resolved once.
	
	---
	
	# Expected Function
	
	One reasonable interview API would be:
	
	```java
	Destination[][] findDestinations(int[][] height);
	```
	
	where every output entry corresponds to its starting cell:
	
	```text
	result[r][c] = final destination of water starting at (r,c)
	```
	
	For example, `Destination` might contain:
	
	```text
	row
	col
	```
	
	An outside coordinate such as:
	
	```text
	(-1, 2)
	(m, 1)
	(3, -1)
	(0, n)
	```
	
	can represent where the drop leaves the matrix.
	
	The interviewer may instead allow a special marker such as:
	
	```text
	OUTSIDE
	```
	
	Either representation is reasonable; this should be clarified before implementation.
	
	---
	
	# Important Clarifying Questions
	
	Before coding, I would quickly confirm:
	
	```text
	1. Only four directions, correct? No diagonals?
	
	2. Must the next cell be strictly lower?
	
	3. If the current cell is on the boundary, should outside height 0
	   participate exactly like another neighbor?
	
	4. Can multiple neighbors have the same minimum height?
	
	5. If there is a tie, how should it be resolved?
	```
	
	For the **base version**, I would suggest assuming:
	
	```text
	4-directional movement
	strictly downhill
	outside height = 0
	unique steepest move
	```
	
	This produces a clean deterministic graph.
	
	---
	
	# What the Problem Is Really Testing
	
	The useful mental model is:
	
	> Every grid cell is a node with at most one outgoing edge.
	
	If:
	
	```text
	next[r][c] = steepest lower neighbor
	```
	
	then the grid effectively becomes a collection of directed chains that eventually terminate at:
	
	```text
	a sink
	or
	outside
	```
	
	Multiple chains may merge.
	
	For example:
	
	```text
	A ──→ C ──→ D ──→ OUTSIDE
	      ↑
	B ────┘
	```
	
	After computing:
	
	```text
	destination(C)
	```
	
	both `A` and `B` can reuse it.
	
	That is the core optimization.
	
	---
	
	# Likely Optimal Direction
	
	### DFS + Memoization
	
	For each cell:
	
	```text
	dfs(r, c)
	```
	
	does:
	
	```text
	if destination already known:
	    return it
	
	find steepest lower neighbor
	
	if none:
	    destination = current cell
	
	otherwise:
	    destination = dfs(next cell)
	
	memoize destination
	return destination
	```
	
	Because every move goes to a **strictly smaller height**, cycles are impossible in the base problem.
	
	That observation is worth explicitly mentioning during the interview.
	
	### Complexity
	
	Let:
	
	```text
	N = m * n
	```
	
	Every cell's destination is computed once.
	
	Finding the best neighbor checks at most:
	
	```text
	4 neighbors
	```
	
	Therefore:
	
	```text
	Time:  O(4N) = O(N) = O(m * n)
	
	Space: O(N)
	```
	
	for memoization, plus potentially:
	
	```text
	O(N)
	```
	
	recursion stack in the worst case.
	
	---
	
	# Closest LeetCode Problems
	
	There is **no exact LeetCode equivalent**.
	
	The closest conceptual problems are:
	
	**LC 329 — Longest Increasing Path in a Matrix**
	
	Very similar because strict height ordering creates a DAG and DFS + memoization is natural.
	
	**LC 417 — Pacific Atlantic Water Flow**
	
	Related because both involve reasoning about water movement through a height grid, although LC 417 uses a different reverse-traversal technique and does not compute a deterministic final destination.
	
	I would consider **LC 329 more useful preparation for the algorithmic technique** than LC 417.
	
	---
	
	# Top 5 Likely Google Follow-ups
	
	## Follow-up 1 — Avoid Recomputing Shared Paths
	
	### Priority: **EXTREMELY HIGH**
	
	The interviewer asks:
	
	> Running the simulation independently from every cell could repeatedly traverse the same paths. How would you avoid that?
	
	Example:
	
	```text
	A ──→ C ──→ D ──→ E
	      ↑
	B ────┘
	```
	
	Once:
	
	```text
	destination(C) = E
	```
	
	has been computed, starting from `A` or `B` should not traverse:
	
	```text
	C → D → E
	```
	
	again.
	
	### Expected direction
	
	Use:
	
	```text
	DFS + memoization
	```
	
	Store:
	
	```text
	destination[r][c]
	```
	
	after resolving each cell.
	
	Then:
	
	```text
	Time: O(m * n)
	Space: O(m * n)
	```
	
	This is almost certainly the **main optimization the interviewer wants to see**.
	
	---
	
	# Follow-up 2 — Multiple Equally Steep Downhill Neighbors
	
	### Priority: **VERY HIGH**
	
	Now remove the uniqueness guarantee.
	
	A cell may have multiple neighbors with the same lowest height.
	
	Example:
	
	```text
	      3
	      ↑
	3 ←   7   → 3
	      ↓
	      8
	```
	
	There are two equally low destinations:
	
	```text
	left = 3
	up   = 3
	```
	
	The interviewer can define one of two variants.
	
	### Variant A — Deterministic tie-breaking
	
	For example:
	
	```text
	up
	left
	down
	right
	```
	
	priority.
	
	The existing memoization approach still works because each cell continues to have only one outgoing edge.
	
	### Variant B — Water can split
	
	The drop can follow **every equally steep path**.
	
	Now one starting cell may have:
	
	```text
	multiple final destinations
	```
	
	For example:
	
	```text
	        B → Sink1
	       /
	A ────
	       \
	        C → Sink2
	```
	
	Instead of storing:
	
	```text
	Destination
	```
	
	we may need:
	
	```text
	Set<Destination>
	```
	
	per cell.
	
	This is a particularly strong Google follow-up because it changes the structure from a simple functional graph to a DAG with potentially multiple outgoing edges. A recent Google interview report specifically mentions a follow-up involving multiple lower points / multiple basins. ([LeetCode][1])
	
	---
	
	# Follow-up 3 — Return Basin Sizes
	
	### Priority: **HIGH**
	
	Instead of returning the destination for every cell, the interviewer asks:
	
	> How many starting cells eventually drain into each destination?
	
	For example:
	
	```text
	A ──→ X
	B ──→ X
	C ──→ X
	
	D ──→ Y
	E ──→ Y
	```
	
	Return something equivalent to:
	
	```text
	X -> 3
	Y -> 2
	```
	
	### Expected direction
	
	First compute:
	
	```text
	destination[r][c]
	```
	
	using the existing memoized DFS.
	
	Then make one additional pass through all cells:
	
	```text
	count[destination[r][c]]++
	```
	
	Complexity remains:
	
	```text
	DFS:        O(m*n)
	count pass: O(m*n)
	
	Total: O(2*m*n)
	       = O(m*n)
	```
	
	This is a natural extension because it tests whether the candidate can reuse the computation instead of redesigning everything.
	
	---
	
	# Follow-up 4 — Determine Only Whether Water Escapes
	
	### Priority: **MEDIUM-HIGH**
	
	Instead of returning the exact destination:
	
	> For each starting cell, return whether its water eventually leaves the matrix.
	
	Return:
	
	```text
	true  -> eventually exits
	false -> reaches an interior sink
	```
	
	Example:
	
	```text
	[
	  [true,  true, true ],
	  [true, false, true ],
	  [true,  true, true ]
	]
	```
	
	### Expected direction
	
	Memoization becomes even simpler:
	
	```text
	Boolean[][] escapes
	```
	
	DFS returns:
	
	```text
	true / false
	```
	
	instead of a coordinate.
	
	The algorithm remains:
	
	```text
	O(m*n)
	```
	
	This follow-up tests whether you recognize that the **underlying graph traversal does not change** just because the stored result changes.
	
	---
	
	# Follow-up 5 — Huge Matrix / Avoid Recursive DFS
	
	### Priority: **MEDIUM**
	
	Suppose:
	
	```text
	m * n is extremely large
	```
	
	and a descending path could contain hundreds of thousands or millions of cells.
	
	Recursive DFS could cause:
	
	```text
	StackOverflowError
	```
	
	The interviewer asks:
	
	> Can we implement the same idea without recursion?
	
	Example:
	
	```text
	1000000 → 999999 → 999998 → ... → 1 → outside
	```
	
	### Expected direction
	
	Use **iterative path walking**.
	
	Starting at a cell:
	
	```text
	List<Cell> path
	```
	
	Follow the next pointer until reaching either:
	
	```text
	1. a previously memoized cell
	2. a sink
	3. outside
	```
	
	Suppose:
	
	```text
	A → B → C → D → X
	```
	
	and:
	
	```text
	destination(X)
	```
	
	is already known.
	
	Then walk backward:
	
	```text
	destination(D) = destination(X)
	destination(C) = destination(X)
	destination(B) = destination(X)
	destination(A) = destination(X)
	```
	
	This preserves:
	
	```text
	Time:  O(m*n)
	Space: O(m*n)
	```
	
	while avoiding dependence on the Java call stack.
	
	---
	
	# Follow-up Priority Order
	
	For Google phone-screen preparation, I would rank them:
	
	| Rank  | Follow-up                                            | Priority           |
	| ----- | ---------------------------------------------------- | ------------------ |
	| **1** | Avoid recomputing shared paths using memoization     | **EXTREMELY HIGH** |
	| **2** | Equal steepest neighbors / multiple flow paths       | **VERY HIGH**      |
	| **3** | Return basin sizes / number of cells per destination | **HIGH**           |
	| **4** | Return only whether each cell escapes                | **MEDIUM-HIGH**    |
	| **5** | Very large grid / replace recursive DFS              | **MEDIUM**         |
	
	## Recommendation
	
	**Keep this at EXTREMELY HIGH priority.**
	
	For your phone-screen preparation, I would specifically be ready to implement **Follow-up 1 as part of the original solution rather than waiting for the interviewer to ask for it**. The interview-ready solution should therefore be:
	
	```text
	DFS
	+
	memoization
	+
	4-neighbor scan
	+
	explicit outside handling
	```
	
	and not a separate simulation from every cell.
	
	The part I would spend the most time understanding is the transition from **one deterministic downhill neighbor** to **multiple downhill neighbors**. That changes the result from:
	
	```text
	one destination per cell
	```
	
	to potentially:
	
	```text
	a set of destinations per cell
	```
	
	and is the most meaningful algorithmic follow-up after memoization. ([LeetCode][1])
	
	[1]: https://leetcode.com/discuss/post/6716605/google-l4-round-1-question-by-noob_maste-n7y8/?utm_source=chatgpt.com "Google L4 round 1 question - Discuss - LeetCode"

 * */
