package Google.extremelyHigh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class RainDropFlow_WaterSplitWhenMultipleEqualDownhillNeighbor_FollowUp2_Variant2 {

	/* Thought Process 
		Our original solution relied on: Every cell has one next cell.
		
		So: destination(current) = destination(bestNeighbor)
		
		Now a cell can have multiple equally steep lowest neighbors.
		Example:
		        3
		        ↑
		  3  ←  7
		
		Both 3s are equally good.
		
		If water splits:
		
		        3 → Sink A
		       /
		      7
		       \
		        3 → Sink B
		
		the answer for 7 is: {Sink A, Sink B}
		
		So instead of memoizing one Cell, we memoize: Set<Cell>
		
		For the current cell: 
		 destinations(current)
		    = union of destinations of all equally-lowest neighbors
		
		That is the only major algorithmic change from the orignal code.
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
	/*
	 * Interview Script: 
	 * In the original solution, every cell had one outgoing edge,
	 * so dfs(r,c) returned one final destination. With this follow-up, several
	 * neighbors can tie for the minimum height, and water follows all of them.
	 * 
	 * I can keep the same DFS and memoization structure. The main change is that
	 * each cell now memoizes a set of final destinations instead of one
	 * destination.
	 * 
	 * While checking the four neighbors, I'll maintain the lowest height found and
	 * a list of all neighbors having that height. If I find a strictly lower
	 * height, I'll clear the previous candidates and start a new list. If I find
	 * another neighbor with the same best height, I'll add it.
	 * 
	 * Then I'll recursively resolve all those neighbors and union their destination
	 * sets. 
	 * 
	 * Since movement is still strictly downhill, cycles are still impossible.
	 */
	/*
	 What I Would Actually Change Live?

		If the interviewer gives this follow-up after you've already 
		implemented the original solution, I would say:

		I don't need to rewrite the DFS. I'll change the memo entry 
		from one Cell to a Set<Cell>. Then instead of remembering 
		one best neighbor, I'll collect all neighbors tied at the 
		minimum height. 
		
		Finally, I'll DFS all of them and union their destination sets.
	 * */
	
	
	
	/** Step-by-Step Algorithm  
	 * For dfs(r,c):
	 * 
	 * Step 1 — Memoization
	 * 
	 * If already computed: return memo[r][c]; 
	 * 
	 * Step 2 — Start With Current Height 
	 * 	bestHeight = height[r][c] 
	 * 	bestNeighbors = []
	 * 
	 * No neighbor has been selected yet.
	 * 
	 * 
	 * Step 3 — Check All Four Directions
	 * For each neighbor 
	 * 		determine: neighborHeight
	 * 		where: outside -> height 0
	 * 		If: neighborHeight < bestHeight
	 * 
	 * 	we found a better downhill choice:
	 * 
	 * 		bestHeight = neighborHeight 
	 * 		clear previous candidates add this neighbor
	 * 
	 * If: neighborHeight == bestHeight
	 * 
	 * and that height is actually lower than the current cell:
	 * 
	 * 	add this neighbor too 
	 * 
	 * Step 4 — No Lower Neighbor
	 * 
	 * If: bestNeighbors is empty
	 * 
	 * the current cell is a sink.
	 * 
	 * Return: { currentCell } 
	 * 
	 * Step 5 — Follow Every Best Neighbor
	 * 
	 * For every best neighbor:
	 * 
	 * 		if OUTSIDE: 
	 * 			add OUTSIDE 
	 * 		else: 
	 * 			addAll(dfs(neighbor)) 
	 * 
	 * Step 6 — Memoize
	 * 		memo[r][c] = result
	 * 
	 * and return it.
	 */
	
	/* Complexity Analysis: 
		Let: N = m * n

		Each cell is DFS-computed once, and each cell checks four neighbors.

		Ignoring the cost of storing destination sets:

		Graph traversal = O(4N)
		                = O(N)
		                = O(m*n)
		                
		But now each cell can contain multiple destinations.

		Let:
		K = maximum number of distinct final destinations
		    reachable from one cell
		
		Merging destination sets can cost up to: O(K) per cell.
		
		So a safe worst-case bound is:
		Time:  O(m * n * K)
		Space: O(m * n * K)
		
		In typical cases where the number of reachable sinks is small, it behaves much closer to:
		O(m*n)
		
		The recursion stack is still:
		O(m*n)
		
		in the worst case.
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
                return "OUT";
//                return "("+r+","+c+")";
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
	
	public Set<Cell>[][] findDestinations(int[][] height) {
		int rows = height.length;
		int cols = height[0].length;
		
		//Cell[][] memo = new Cell[rows][cols];
		
		Set<Cell>[][] memo = new Set[rows][cols]; // Follow-up 2: Water split
		
		for(int r=0; r<rows; r++) {
			for(int c=0; c<cols; c++) {
				dfs(height, r, c, memo);
			}
		}
		
		return memo;
	}
	
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
	
	private boolean isValid(int[][] height, int r, int c) {
		int rows = height.length;
		int cols = height[0].length;
		
		return r >= 0 && r < rows
                && c >= 0 && c < cols;
	}

	
	
	// Main method with tests - Main method starts
	public static void main(String[] args) {

        RainDropFlow_WaterSplitWhenMultipleEqualDownhillNeighbor_FollowUp2_Variant2 sol = new RainDropFlow_WaterSplitWhenMultipleEqualDownhillNeighbor_FollowUp2_Variant2();

     // Test 1: Everything eventually exits.
        int[][] h1 = {
                {9, 6, 3},
                {8, 5, 2},
                {7, 4, 1}
        };

        System.out.println("Test 1");
        System.out.println("Expected: every cell -> [OUT]");
        System.out.println("Actual:");

        Set<Cell>[][] result1 = sol.findDestinations(h1); // Follow-up 2: Water split
        printResult(result1);

        /*
         Expected:
         [[OUT], [OUT], [OUT]]
         [[OUT], [OUT], [OUT]]
         [[OUT], [OUT], [OUT]]
        */

        System.out.println();


        // Test 2: Center is an interior sink.
        int[][] h2 = {
                {10, 10, 10},
                {10,  1, 10},
                {10, 10, 10}
        };

        System.out.println("Test 2");
        System.out.println("Expected:");
        System.out.println("Center (1,1) -> [(1,1)]");
        System.out.println("Boundary cells -> [OUT]");
        System.out.println("Actual:");

        Set<Cell>[][] result2 = sol.findDestinations(h2); // Follow-up 2: Water split
        printResult(result2);

        System.out.println();


        // Test 3: Single positive cell.
        int[][] h3 = {
                {5}
        };

        System.out.println("Test 3");
        System.out.println("Expected: [OUT]");
        System.out.println("Actual:");

        Set<Cell>[][] result3 = sol.findDestinations(h3); // Follow-up 2: Water split
        printResult(result3);

        System.out.println();


        // Test 4: Single zero-height cell.
        int[][] h4 = {
                {0}
        };

        System.out.println("Test 4");
        System.out.println("Expected: [(0,0)]");
        System.out.println("Actual:");

        Set<Cell>[][] result4 = sol.findDestinations(h4); // Follow-up 2: Water split
        printResult(result4);

        System.out.println();


        // Follow-up 2 test: water splits across equal minimum neighbors. 
        int[][] h5 = {                                               
                {0, 5, 0},                                           
                {5, 1, 5},                                           
                {0, 5, 0}                                            
        };                                                            

        System.out.println("Test 5 - Water Split");                   
        System.out.println("Expected examples:");                     
        System.out.println("(0,1) -> [OUT, (0,0), (0,2)]");           
        System.out.println("(1,0) -> [(0,0), (2,0), OUT]");
        System.out.println("(1,1) -> [(1,1)]");                       
        System.out.println("Actual:");                                

        Set<Cell>[][] result5 = sol.findDestinations(h5);             
        printResult(result5);                                         

        System.out.println();                                         


        // Interior split that reaches two different sinks.             
        int[][] h6 = {                                                 
                {0, 9, 0, 9, 0},                                      
                {9, 8, 9, 8, 9},                                      
                {9, 6, 7, 6, 9},                                      
                {9, 4, 5, 3, 9},                                      
                {9, 2, 9, 1, 9}                                       
        };                                                             

        System.out.println("Test 6 - Multiple Flow Paths");            
        System.out.println("Actual:");                                 

        Set<Cell>[][] result6 = sol.findDestinations(h6);              
        printResult(result6);  
    }
	private static void printResult(Set<Cell>[][] result) { // Follow-up 2: Water split

        for (Set<Cell>[] row : result) { 
            System.out.println(Arrays.toString(row));
        }
    }
	
	
	
	/** Follow-up 2 — Multiple Equally Steep Downhill Neighbors */
	
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
