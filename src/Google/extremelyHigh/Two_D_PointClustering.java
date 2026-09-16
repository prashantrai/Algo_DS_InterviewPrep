package Google.extremelyHigh;

public class Two_D_PointClustering {

	public static void main(String[] args) {

		runTest("Normal case", 
				new int[][] { { 0, 0 }, { 1, 1 }, { 10, 10 }, 
							{ 11, 10 }, { 30, 30 } }, 2, 3);

		runTest("Single point", new int[][] { { 5, 5 } }, 2, 1);

		runTest("Empty input", new int[][] {}, 2, 0);

		runTest("All points connected", 
				new int[][] { { 0, 0 }, { 1, 0 }, { 2, 0 } }, 2, 1);

		runTest("No points connected", 
				new int[][] { { 0, 0 }, { 10, 10 }, { 20, 20 } }, 2, 3);

		runTest("Exactly distance D", 
				new int[][] { { 0, 0 }, { 3, 4 } }, 5, 1);

		/* A transitive connection means two points can belong to the same 
		 * cluster even if they are not directly within distance D of each other, 
		 * as long as there is a chain of connections between them.
		 *    A -------- B -------- C
			(0,0)      (2,0)      (4,0)
		 * 
		 * if D = 1, then A and C here transitive connection.
		 * */
		runTest("Transitive connection", 
				new int[][] { { 0, 0 }, { 2, 0 }, { 4, 0 } }, 2, 1);

		runTest("Two separate clusters", 
				new int[][] { { 0, 0 }, { 1, 0 }, { 10, 10 }, { 11, 10 } }, 2, 2);

		runTest("Duplicate points", 
				new int[][] { { 1, 1 }, { 1, 1 }, { 10, 10 } }, 0, 2);

		runTest("Negative coordinates", 
				new int[][] { { -2, -2 }, { -1, -1 }, { 10, 10 } }, 2, 2);

		runTest("D is zero", new int[][] { { 0, 0 }, { 0, 0 }, { 1, 1 } }, 0, 2);
	}

	private static void runTest(String name, int[][] points, int d, int expected) {

		int actual = countClusters(points, d);

		System.out.println(name + " | Expected: " + expected + ", Actual: " + actual + ", Result: "
				+ (expected == actual ? "PASS" : "FAIL"));
	}

	
	// Solution starts...
	
	public static int countClusters(int[][] points, int d) {
		int n = points.length;
		
		if(n == 0) return 0;
		
		int[] parent = new int[n];
		int[] size = new int[n];
		
		for(int i=0; i<n; i++) {
			parent[i] = i;
			size[i] = 1;
		}
		
		int cluster = n;
		long maxDist = (long) d * d; // dx² + dy² <= D²
		
		// Check every pair once and merge connected components.
		for(int i=0; i<n; i++) {
			for(int j=i+1; j<n; j++) {
				long distX = (long) points[i][0] - points[j][0];
				long distY = (long) points[i][1] - points[j][1];
				
				// dx² + dy² <= D²
				if(distX * distX + distY * distY <= maxDist) {
					// union i and j
					if(union(i, j, parent, size)) {
						// Counting connected components (example added at the end of the file, to help understand)
						// Every time union(a,b) successfully joins two previously separate groups
						// if two different components/groups were merged, decrement clusters.
						cluster--;
					}
				}
				
			}
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
		/* this works too
		if(size[rootA] < size[rootB]) {
			// we are just swaping and assigning/making rootA point to bigger tree, nothing special
			int temp = rootA;
			rootA = rootB;
			rootB = temp;
		}
		parent[rootB] = rootA;
		size[rootA] += size[rootB]; */
		
		// Always make the root of the smaller group point to the root of the larger group.
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