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
		
		// parent is the core array used by Union-Find / Disjoint Set Union (DSU) 
		// to track which cluster each point belongs to.
		// i.e which node should I go to next when trying to find the 
		// representative/root of this cluster?
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


/*
 Below is a cleaner, interview-ready version of the problem with the connectivity rule made explicit, since the important detail is that clustering is **transitive**.

# 2D Point Clustering

### Priority: EXTREMELY HIGH

## Problem Statement

You are given a list of points in a 2D plane:

```text
(x1, y1), (x2, y2), ..., (xn, yn)
```

and a distance threshold `D`.

Two points are considered **directly connected** if the Euclidean distance between them is at most `D`.

That is, points:

```text
p1 = (x1, y1)
p2 = (x2, y2)
```

are directly connected when:

```text
sqrt((x1 - x2)^2 + (y1 - y2)^2) <= D
```

A **cluster** is a connected component of points.

In other words, two points belong to the same cluster if:

- they are directly connected, or
- there exists a sequence of directly connected points joining them.

Return the **number of clusters**.

---

## Important Detail: Connectivity Is Transitive

Points do **not** all need to be within distance `D` of each other to belong to the same cluster.

For example:

```text
A ---- B ---- C
```

If:

```text
distance(A, B) <= D
distance(B, C) <= D
```

then `A`, `B`, and `C` belong to the same cluster even if:

```text
distance(A, C) > D
```

---

## Example 1

```text
points = [
    (0, 0),
    (1, 1),
    (10, 10),
    (11, 10),
    (30, 30)
]

D = 2
```

Connections:

```text
(0,0) <-> (1,1)

(10,10) <-> (11,10)

(30,30)
```

Therefore the clusters are:

```text
Cluster 1: (0,0), (1,1)

Cluster 2: (10,10), (11,10)

Cluster 3: (30,30)
```

Output:

```text
3
```

---

## Example 2 — Transitive Connection

```text
points = [
    (0, 0),
    (2, 0),
    (4, 0)
]

D = 2
```

Distances:

```text
(0,0) -> (2,0) = 2
(2,0) -> (4,0) = 2
(0,0) -> (4,0) = 4
```

Although `(0,0)` and `(4,0)` are not directly connected, they are connected through `(2,0)`.

Therefore:

```text
(0,0) -- (2,0) -- (4,0)
```

Output:

```text
1
```

---

## Example 3 — No Points Are Connected

```text
points = [
    (0, 0),
    (10, 0),
    (20, 0),
    (30, 0)
]

D = 3
```

Every point forms its own cluster.

Output:

```text
4
```

---

## Example 4 — All Points Connected Indirectly

```text
points = [
    (0, 0),
    (1, 0),
    (2, 0),
    (3, 0),
    (4, 0)
]

D = 1
```

Connections form a chain:

```text
(0,0) -- (1,0) -- (2,0) -- (3,0) -- (4,0)
```

Output:

```text
1
```

---

## Example 5 — Duplicate Points

```text
points = [
    (1, 1),
    (1, 1),
    (10, 10)
]

D = 0
```

The first two points have distance `0`, so they form one cluster.

The third point forms another.

Output:

```text
2
```

---

## Suggested Java Method Signature

```java
public static int countClusters(int[][] points, double distance)
```

where:

```text
points[i][0] = x coordinate
points[i][1] = y coordinate
```

For example:

```java
int[][] points = {
    {0, 0},
    {1, 1},
    {10, 10},
    {11, 10},
    {30, 30}
};

countClusters(points, 2.0);
```

returns:

```text
3
```

---

## Constraints

A reasonable first version of the problem can use:

```text
1 <= points.length <= 10^4
-10^9 <= x, y <= 10^9
D >= 0
```

You may assume the coordinates are integers.

---

# Expected Initial Approach

The points can be viewed as an **undirected graph**:

```text
point = vertex

distance(p1, p2) <= D
        =
edge between p1 and p2
```

The problem therefore becomes:

> Find the number of connected components in the graph.

Possible approaches include:

```text
DFS / BFS
Union-Find (Disjoint Set Union)
```

A straightforward solution compares every pair of points:

```text
for every i:
    for every j > i:
        if distance(i, j) <= D:
            connect i and j
```

Using Union-Find, every qualifying pair can be unioned.

### Complexity

```text
Pair comparisons: O(n²)

Union-Find operations:
approximately O(α(n)) each

Overall:
O(n²)
```

A useful implementation detail is to avoid `sqrt` and compare squared distances:

```text
dx * dx + dy * dy <= D * D
```

---

# Important Follow-up — Millions of Points

### Priority: EXTREMELY HIGH

Suppose:

```text
n = millions of points
```

An `O(n²)` solution is no longer practical.

How would you improve it?

### Expected Direction

Use:

```text
spatial hashing
+
grid buckets
+
Union-Find
```

Divide the plane into grid cells with side length approximately:

```text
D
```

For a point `(x, y)`, compute its cell:

```text
cellX = floor(x / D)
cellY = floor(y / D)
```

A point can only be within distance `D` of points in:

```text
its own cell
+
the 8 neighboring cells
```

So instead of comparing the point against every other point, only compare it against points in those nearby buckets.

Conceptually:

```text
+-------+-------+-------+
|       | check |       |
+-------+-------+-------+
| check |   P   | check |
+-------+-------+-------+
|       | check |       |
+-------+-------+-------+
```

More precisely, inspect all nine cells surrounding the point's bucket.

This can reduce the number of candidate comparisons dramatically when points are reasonably distributed.

### Expected Complexity

Typically:

```text
O(n + number of nearby candidate pairs)
```

rather than:

```text
O(n²)
```

Worst case can still degrade toward `O(n²)` if a huge number of points occupy the same small region.

---

## Likely Interview Follow-ups

**Follow-up 1 — Large Scale / Spatial Hashing — EXTREMELY HIGH**

Millions of points make pairwise comparison impossible. Design a grid-bucket solution that only checks spatially nearby points.

**Follow-up 2 — Return the Actual Clusters — VERY HIGH**

Instead of returning only:

```text
3
```

return something such as:

```text
[
    [(0,0), (1,1)],
    [(10,10), (11,10)],
    [(30,30)]
]
```

With Union-Find, group points by their final root.

**Follow-up 3 — Cluster Sizes — HIGH**

Return the size of every cluster, or return the largest cluster size.

Example:

```text
[2, 2, 1]
```

**Follow-up 4 — Streaming Points — HIGH**

Points arrive one at a time.

After each new point is inserted, return the current number of clusters.

This naturally extends the:

```text
spatial hash + Union-Find
```

approach.

---

## Closest LeetCode Problems

There is no exact standard LeetCode problem for the geometry + threshold version, but the underlying connected-component idea is closely related to:

```text
LC 547 — Number of Provinces
```

Very close conceptually:

```text
Given connections between vertices,
count connected components.
```

Also related:

```text
LC 200 — Number of Islands
```

Same connected-component idea, but connectivity is defined by adjacent grid cells instead of Euclidean distance.

For the Union-Find aspect:

```text
LC 684 — Redundant Connection
LC 721 — Accounts Merge
```

The **millions-of-points + spatial hashing follow-up** is the part that makes this considerably more Google-style than simply asking LC 547.
 
 */