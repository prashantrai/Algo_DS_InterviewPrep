package Google.extremelyHigh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Two_D_PointClustering_FollowUp_WhenMillionsPoint {

	public static void main(String[] args) {
        runTest(
                "Normal case",
				new int[][] { { 0, 0 }, { 1, 1 }, { 10, 10 }, 
                	{ 11, 10 }, { 30, 30 } },
                2, 3 // int d, int expected
        );

        runTest(
                "Transitive connection",
				new int[][] { { 0, 0 }, { 2, 0 }, { 4, 0 } },
                2, 1 // int d, int expected
        );

        runTest(
                "All separate",
				new int[][] { { 0, 0 }, { 100, 100 }, { 200, 200 } },
                2, 3 // int d, int expected
        );

		runTest("Single point", new int[][] { { 5, 5 } }, 
				2, 1); // int d, int expected

		runTest("Empty", new int[][] {}, 2, 0); // points, int d, int expected

		runTest("Exact boundary", new int[][] { { 0, 0 }, { 3, 4 } }, 5, 1); // points, d, expected

        runTest(
                "Negative coordinates",
				new int[][] { { -2, -2 }, { -1, -1 }, { 10, 10 }
                },
                2, 2 // int d, int expected
        );

        runTest(
                "Duplicate points with D zero",
				new int[][] { { 1, 1 }, { 1, 1 }, { 10, 10 } },
                0, 2 // int d, int expected
        );
    }
	private static void runTest(String name, int[][] points, int d, int expected) {

		int actual = countClusters(points, d);

		System.out.println(name + " | Expected: " + expected + ", Actual: " + actual + ", Result: "
				+ (expected == actual ? "PASS" : "FAIL"));
	}
	
	// Solution starts...
	
	static class Cell {
        int x;
        int y;

        Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}

			if (!(obj instanceof Cell)) {
				return false;
			}

			Cell other = (Cell) obj;
			return x == other.x && y == other.y;
		}

        @Override
        public int hashCode() {
            return 31 * Integer.hashCode(x)
                    + Integer.hashCode(y);
        }
    }
	
	public static int countClusters(int[][] points, int d) {
		int n = points.length;
		
		if(n == 0) return 0;
		
		// countClustersForZeroDistance
		if(d == 0) {
			return countClustersForZeroDistance(points);
		}
		
		int[] parent = new int[n];
		int[] size = new int[n];
		
		for(int i=0; i<n; i++) {
			parent[i] = i;
			size[i] = 1;
		}
		
		/* This stores:
			grid cell -> indexes of points inside that cell
			
			For example, after processing:
			A = (1,1)
			B = (2,2)
			C = (8,8)
			
			the map might look like:
			Cell(0,0) -> [0,1]
			Cell(2,2) -> [2]
			
			Why indexes instead of points themselves?
			
			Because later we need:
			points[j][0]
			points[j][1]
			
			and we also need index j for Union-Find:
			
			union(i, j, parent, size)
			
			So the index gives us both.
		 * */
		Map<Cell, List<Integer>> grid = new HashMap<>();
		
		int cluster = n;
		long maxDist = (long) d * d; // dx² + dy² <= D²
		
		// Check every pair once and merge connected components.
		for(int i=0; i<n; i++) {
			int x = points[i][0];
			int y = points[i][1];
			
			// floorDiv correctly handles negative coordinates.
			/* Example: 
			 	Suppose: d = 3 and point = (8,8)
				Then: cellX = 8 / 3 = 2 and  cellY = 8 / 3 = 2
				So (8,8) belongs to: Cell(2,2)
				
				Another example: point = (2,2)
					cellX = 2 / 3 = 0;  cellY = 2 / 3 = 0
					So (2,2) belongs to: Cell(0,0)
				
				Math.floorDiv() is used instead of normal division 
				mainly because it behaves correctly for negative coordinates.
					For example:
					x = -1 and d = 3
					
					Normal Java integer division gives: -1 / 3 = 0
					but geometrically -1 should belong to the cell 
					immediately left of zero:
					
					cell = -1
					
					And: Math.floorDiv(-1, 3) correctly returns: -1
			 * */
			int cellX = Math.floorDiv(x, d);
			int cellY = Math.floorDiv(y, d);
			
			// Check current cell + 8 surrounding cells.
			for(int dxCell = -1; dxCell <= 1; dxCell++) {
				for(int dyCell = -1; dyCell <= 1; dyCell++) {
					
					Cell neighbor = new Cell(cellX + dxCell, 
													cellY + dyCell);
					
					List<Integer> candidates = grid.get(neighbor);
					
					if(candidates == null) continue;
					
					for(int j : candidates) {
						/* Suppose:
							current point = (2,2)
							candidate     = (1,1)
							
							Then:
							dx = 2 - 1 = 1
							dy = 2 - 1 = 1
							
							distSq = 1² + 1² = 2
						 * */
						double dx = (double) points[i][0] - points[j][0];
						double dy = (double) points[i][1] - points[j][1];
						
						double distSq = dx * dx + dy * dy;
						
						// Are points connected, i.e. are they within the eucledian distance d
						if(distSq <= maxDist) {
							// points are connected, merge their components using Union-Find
							if(union(i, j, parent, size)) {
								cluster--;
							}
						}
					}

				}
			}// closed "for(int dxCell..."
			
			// Add current point only after checking previous points.
			// Process each point against only previously seen nearby points.
			// The grid contains only points that were already processed, so each pair
			// is checked once: when the later point is processed, it checks the earlier one.
			// We add the current point to the grid only after these checks, which also
			// avoids comparing the point with itself.
			/* A better example with 3 nearby points
			 * 
			 * Suppose: A = (1,1) B = (2,1) C = (2,2) d = 3
			 * All three are close enough to potentially connect.
			 * 
			 * Processing order: A B C 
			 * Process A: grid before = {}
			 * 	Comparisons: none
			 * 	Then: grid = { A } 
			 * 
			 * Process B: grid before = { A }
			 * 	Comparisons: B -> A
			 * 	Then add B: grid = { A, B } 
			 * 
			 * Process C: grid before = { A, B }
			 * 	Comparisons: C -> A C -> B
			 * 	Then add C: grid = { A, B, C }
			 * 
			 * So overall, we checked exactly these pairs:
			 * A-B A-C B-C
			 * 
			 * Each pair once.
			 * 
			 * We did not check: B-A C-A C-B
			 * 
			 * because those are just duplicates.
			 */
			
			Cell current = new Cell(cellX, cellY);
			grid.computeIfAbsent(current, v -> new ArrayList<>()).add(i);
			
		}
		
		return cluster;
		
	}
	
	private static int find(int x, int[] parent) {
		if(parent[x] != x) {
			parent[x] = find(parent[x], parent);
		}
		
		return parent[x];
	}
	
	public static boolean union(int a, int b, int[] parent, int[] size) {
		int rootA = find(a, parent);
		int rootB = find(b, parent);
		
		// Already belong to the same cluster.
		if(rootA == rootB) {
			return false;
		}
		
		// Attach the smaller tree to the larger tree.
		// instead of swap we are using else block for readability purpose
		if (size[rootA] < size[rootB]) {
		    // Attach A under larger tree B.
		    parent[rootA] = rootB;
		    size[rootB] += size[rootA];
		} else {
		    // Attach B under larger tree A.
		    parent[rootB] = rootA;
		    size[rootA] += size[rootB];
		}
		
		return true;
	}
	
	 
	/* 
	Handles the special case when d == 0.
	With zero allowed distance, only points at the exact same coordinates
	can belong to the same cluster. Therefore, the number of clusters is
	simply the number of unique coordinates.
	This also avoids division by zero when calculating grid cells using d.
	
	For example:
	points =  {{1, 1}, {1, 1},
	{2, 2},
	{5, 5}, {5, 5}}
	
	d = 0
	
	The distance between the first two points is:
	sqrt((1 - 1)^2 + (1 - 1)^2) = 0
	
	So they belong to the same cluster.
	
	Similarly, the two (5,5) points belong to the same cluster.
	
	But (1,1), (2,2), and (5,5) are different coordinates, so they are separate clusters.
	
	Therefore:
	
	Cluster 1: (1,1), (1,1)
	Cluster 2: (2,2)
	Cluster 3: (5,5), (5,5)
	
	Answer = 3, which would be the set size in this case, i.e. the no of clusters 
	
	That is exactly what this method computes.
	 * */
	private static int countClustersForZeroDistance(int[][] points) {
		Set<String> uniquePoints = new HashSet<>();
		
		for(int[] point : points) {
			uniquePoints.add(point[0] + "," + point[1]);
		}
		
		return uniquePoints.size();
	}
	
	/** Follow-up : “What if there are millions of points? O(N²) won't work.” */
	/*
		The most important interviewer follow-up is:
		
		“What if there are millions of points? O(N²) won't work.”
		
		Then the answer changes to:
		
		Spatial Hashing / Grid Buckets
		    +
		Union Find
		
		With grid cell size D, a point only needs to compare against 
		points in its own cell and the 8 neighboring cells:
		
		+-----+-----+-----+
		| NW  |  N  | NE  |
		+-----+-----+-----+
		|  W  |  P  |  E  |
		+-----+-----+-----+
		| SW  |  S  | SE  |
		+-----+-----+-----+
		
		That is the part I would especially prepare for this problem, 
		because it turns the basic connected-components question into 
		the much more interesting Staff/L5-level follow-up.
	 * */
	
}


/*
 C6. 2D Point Clustering | Priority: EXTREMELY HIGH
	
	Given points: (x1,y1), (x2,y2), ...
	
	and threshold D, two points belong to the same graph if:
	
	distance(p1,p2) <= D
	
	Clusters are connected components.
	
	Return the number of clusters.
	
	Example:
	
	(0,0)
	(1,1)
	(10,10)
	(11,10)
	(30,30)
	
	D = 2
	
	returns: 3
	
	Initial solution
	Build O(n²) edges
	+
	DFS/BFS/Union-Find
	
	
	Important follow-up: 
	
	For millions of points, avoid O(n²) using:
	
	spatial hashing / grid buckets
	
	Closest:
	
	547 / 200.
 
 */


/*
12. Counting connected components

	This is particularly useful for your 2D Point Clustering problem.
	
	Suppose initially there are 5 points.
	
	That means:
	
	components = 5
	
	Every time union(a,b) successfully joins two previously separate groups:
	
	components--
	
	Example:
	
	Initially:
	
	A B C D E
	
	components = 5
	
	Connect:
	
	A-B
	
	Now:
	
	AB C D E
	
	components = 4
	
	Connect:
	
	B-C
	
	Now:
	
	ABC D E
	
	components = 3
	
	Connect:
	
	D-E
	
	Now:
	
	ABC DE
	
	components = 2
	
	That's why returning a boolean from union() is convenient:
	
	if (union(a, b)) {
	    components--;
	}
* */