package Google;

import java.util.*;

public class MinimumViolationsRoadRoute {

	public static void main(String[] args) {
        int n = 4;

        int[][] roads = {
            {0, 1},
            {2, 1},
            {2, 3}
        };

        int source = 0;
        int destination = 3;

        System.out.println(
            minViolations(n, roads, source, destination)
        );

        // Expected: 1
    }

	
	static class Edge {
		int to;
		int cost;
		Edge(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
	}
	
	
	/* Interview script:  You can explain the approach in roughly 30–40 seconds:
	
	“For every preferred road u -> v, I'll create a zero-cost edge from u to v and 
	a cost-one edge from v to u. 
	
	Then the problem becomes finding the minimum-cost path where all edge weights 
	are either zero or one. 
	
	Because of that restriction, instead of regular Dijkstra with a priority queue, 
	I can use 0-1 BFS with a deque. When I relax a zero-cost edge I push the node to 
	the front, and for a cost-one edge I push it to the back. This gives O(V + E) 
	time and O(V + E) space.”

	One-line recognition pattern: 
	Shortest path + edge costs are only 0 and 1 → think 0-1 BFS.
	  
	 * */
	
	
	/*
	Time: O(V + E), Let: V = number of cities,  E = number of roads

	Each original road becomes two graph edges:
	u -> v
	v -> u
	
	So adjacency-list construction takes: O(V + E)
	
	In 0-1 BFS, vertices/edges are processed through relaxation in linear total work:
	O(V + E)
	
	Space: O(V + E), 
		Adjacency list: O(V + E),  Distance array: O(V),  Deque: O(V)
	 * */
	
	public static int minViolations( int n, int[][] roads, int source, int destination) {
		
		// Build the graph.
        // For every preferred road u -> v:
        // u -> v has cost 0 because we follow the preferred direction.
        // v -> u has cost 1 because we travel against it.
		List<List<Edge>> graph = new ArrayList<>();
		for(int i=0; i<n; i++) {
			graph.add(new ArrayList<>());
		}
		
		// fill graph/adjacency list
		for(int[] road : roads) {
			int u = road[0];  // from/src
			int v = road[1];  // dest/to
			graph.get(u).add(new Edge(v, 0));
			graph.get(v).add(new Edge(u, 1));
		}
		
		// dist[i] = minimum violations needed to reach city i.
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        
        // Follow-up 1: Track the previous node used to reach each city optimally.
        int[] parent = new int[n];
        Arrays.fill(parent, -1); // -1 means no parent has been assigned yet.
        

        dist[source] = 0;
        
        // 0-1 BFS uses a deque instead of a priority queue.
        Deque<Integer> dq = new ArrayDeque<>();
        dq.offerFirst(source);
        
        while (!dq.isEmpty()) {
        	int current = dq.pollFirst();
        	
        	for(Edge edge : graph.get(current)) {
        		int next = edge.to;
        		int newCost = dist[current] + edge.cost;
        		
        		// For each neighbor, check whether reaching it through 
        		// the current node improves its minimum known cost 
        		// (a cheaper way to reach the neighbor). 
        		// If yes, update its best-known cost.
        		/*  For example, suppose:
						dist[current] = 2
						edge cost     = 1
						dist[next]    = 5
					
					Going through current would cost: 2 + 1 = 3
					Since: 3 < 5
					
					we found a better route, so we update: dist[next] = 3
					That is called relaxing the edge.
        		 * */
        		
        		if(newCost < dist[next]) {
        			dist[next] = newCost;
        			
        			// Follow-up 1: Remember how we reached next on this better path.
                    parent[next] = current; // record the parent
        			
        			// Cost 0 means this node should be processed ASAP.
        			if(newCost == 0) {
        				dq.offerFirst(next);
        			} else {
        				// Cost 1 means lower priority, so put at the back.
        				dq.offerLast(next);
        			}
        		}
        	}
        }
		
        // Follow-up 1 ; print path to min destination
        getPath(parent, source, destination);
        
        return dist[destination];
	}
	
	
	/* Follow-up 1
	 * “parent[] stores links, not the full path. Starting from the destination, 
	 * I follow those links backward to the source, which gives the route 
	 * in reverse order, so I reverse it once at the end.”
	 */
	/* For the path: 0 -> 1 -> 2 -> 3
		
		we have:
		parent[0] = -1
		parent[1] = 0
		parent[2] = 1
		parent[3] = 2
		
		Think of it as:
		"How did I reach node 3?" -> from 2
		"How did I reach node 2?" -> from 1
		"How did I reach node 1?" -> from 0
		
		So if the destination is 3, parent[3] only gives you: 2
		It does not directly give you: [0, 1, 2, 3]
		
		You have to follow the chain:
		3
		↓ parent[3]
		2
		↓ parent[2]
		1
		↓ parent[1]
		0
		
		That produces: 3 -> 2 -> 1 -> 0  which is backward because we started at the destination.
	 * */
	
	static void getPath(int[] parent, int source, int destination) {
		List<Integer> path = new ArrayList<>();

		int current = destination;

		while (current != -1) {
		    path.add(current);

		    if (current == source) {
		        break;
		    }

		    current = parent[current];
		}
		
		Collections.reverse(path);
		System.out.println("Path: " + path);
	} 
	
	
	
	/** Follow-up 2 
	Suppose traversing a road backward no longer always costs 1.
	For every preferred road: u -> v
	you are given: reverseCost
	such that:
	u -> v           cost 0
	v -> u           cost reverseCost
	
	where: reverseCost >= 0
	
	For example:
	0 -> 1, reverseCost = 5
	1 -> 2, reverseCost = 2
	
	Return the minimum total violation cost.
	
	Key Discussion
	Now edge weights are no longer restricted to: 0 or 1
	
	Therefore: 0-1 BFS no longer applies.
	Use: Dijkstra's algorithm
	
	Complexity
	O((V + E) log V) 
	 * */
	
	/* Interview script
	“The original solution used 0-1 BFS because every edge cost was 
	exactly 0 or 1. In this follow-up, reverse costs can be any 
	non-negative value, so a deque can no longer maintain nodes in 
	increasing total-cost order. 
	
	I’ll keep the same weighted graph representation, but use Dijkstra 
	with a min-heap. dist[v] stores the cheapest known cost to reach v, 
	and whenever going through the current node gives a cheaper cost, 
	I update dist[v] and push the new state into the heap.”
	 * */
	
	/*
	 The main change is: 0-1 BFS is no longer valid because reverse costs 
	 can be 2, 5, 10, etc. We need a priority queue so that we always process 
	 the currently cheapest node first. That is Dijkstra’s algorithm.
	 * */
	// Time Complexity O((V + E) log V), As we are using Dijkstra's algorithm
	private static class MinimumViolationCost_Followup3 {

	    static class Edge {
	        int to;
	        int cost;
	        Edge(int to, int cost) {
	            this.to = to;
	            this.cost = cost;
	        }
	    }
	    static class State {
	        int node;
	        long cost;

	        State(int node, long cost) {
	            this.node = node;
	            this.cost = cost;
	        }
	    }

	    public static long minViolationCost(
	            int n, int[][] roads, int source, int destination) {

	        List<List<Edge>> graph = new ArrayList<>();

	        for (int i = 0; i < n; i++) {
	            graph.add(new ArrayList<>());
	        }

	        // roads[i] = {u, v, reverseCost}
	        /*Example: int[][] roads = {
	            {0, 1, 5},
	            {2, 1, 2},
	            {2, 3, 4} }; 
	         * */
	        for (int[] road : roads) {
	            int u = road[0];
	            int v = road[1];
	            int reverseCost = road[2];

	            // Preferred direction costs 0.
	            graph.get(u).add(new Edge(v, 0));

	            // Traveling backward costs reverseCost.
	            graph.get(v).add(new Edge(u, reverseCost));
	        }

	        long[] dist = new long[n];
	        Arrays.fill(dist, Long.MAX_VALUE);

	        dist[source] = 0;

	        // Always process the node with the smallest known cost.
	        PriorityQueue<State> minHeap =
	                new PriorityQueue<>(
	                        (a, b) -> Long.compare(a.cost, b.cost));

	        minHeap.offer(new State(source, 0));

	        while (!minHeap.isEmpty()) {

	            State current = minHeap.poll();

	            int node = current.node;
	            long currentCost = current.cost;

	            // Skip stale entries.
	            if (currentCost != dist[node]) {
	                continue;
	            }

	            if (node == destination) {
	                return currentCost;
	            }

	            for (Edge edge : graph.get(node)) {

	                int next = edge.to;
	                long newCost = currentCost + edge.cost;

	                // Relax the edge:
	                // update if we found a cheaper route to next.
	                if (newCost < dist[next]) {

	                    dist[next] = newCost;

	                    minHeap.offer(
	                            new State(next, newCost));
	                }
	            }
	        }

	        return dist[destination];
	    }
	    
	    public static void main(String[] args) {
	        int n = 4;

	        int[][] roads = {
	            {0, 1, 5},
	            {2, 1, 2},
	            {2, 3, 4} };

	        int source = 0;
	        int destination = 3;

	        System.out.println("Expected: 2, Actual: "+ 
	        			minViolationCost(n, roads, source, destination));

	    }
	    
	}
	
	
}


/* C7. Minimum-Violations Road Route | Priority: EXTREMELY HIGH
		[Similar Leetcode: LC 1368 (conceptually very close), LC 2290 is also related]
		
	HINT: Once you build the graph this way, the question "minimum violations 
	from source to destination" becomes just "shortest path from source to 
	destination" in a weighted graph where all weights are 0 or 1.
	
	Each preferred road becomes a zero-cost forward edge and a cost-one backward edge, 
	so this is a shortest-path problem with only 0/1 weights, which makes 0-1 BFS with 
	a deque the optimal solution.

	That's it — it's a shortest-path problem in disguise.
	

	
  * Problem Statement
	
	You are given a road network represented as a directed graph with n cities numbered from 0 to n - 1.
	
	Each road has a preferred direction:
	
	u -> v
	
	However, the road may also be traveled in the opposite direction.
	
	Travel cost is defined as:
	
	u -> v : cost 0   // follow the preferred direction
	v -> u : cost 1   // travel against the preferred direction
	
	Given:
	
	n
	roads
	source
	destination
	
	return the minimum number of road-direction violations required to travel from source to destination.
	
	You may assume that the destination is reachable if road directions are ignored.
	
	Method Signature
	public static int minViolations(
	        int n,
	        int[][] roads,
	        int source,
	        int destination)
	
	Each entry:
	
	roads[i] = {u, v}
	
	means the preferred direction is:
	
	u -> v
	Example 1
	n = 4
	
	roads = [
	    [0,1],
	    [1,2],
	    [2,3]
	]
	
	source = 0
	destination = 3
	
	Graph:
	
	0 -> 1 -> 2 -> 3
	
	We can follow all preferred directions.
	
	0 -> 1 -> 2 -> 3
	
	violations = 0
	Output
	0
	Example 2
	n = 4
	
	roads = [
	    [0,1],
	    [2,1],
	    [2,3]
	]
	
	source = 0
	destination = 3
	
	Preferred roads:
	
	0 -> 1
	2 -> 1
	2 -> 3
	
	To reach 3:
	
	0 -> 1
	1 -> 2   // against preferred direction 2 -> 1
	2 -> 3
	
	Only one road is traversed backward.
	
	Output
	1
	Example 3
	n = 5
	
	roads = [
	    [1,0],
	    [1,2],
	    [3,2],
	    [3,4]
	]
	
	source = 0
	destination = 4
	
	One possible route:
	
	0 -> 1 -> 2 -> 3 -> 4
	
	Costs:
	
	0 -> 1    cost 1   // road prefers 1 -> 0
	1 -> 2    cost 0
	2 -> 3    cost 1   // road prefers 3 -> 2
	3 -> 4    cost 0
	
	Total:
	
	2
	Output
	2
	Example 4 — Choose Better Route
	n = 5
	
	roads = [
	    [0,1],
	    [2,1],
	    [2,4],
	    [0,3],
	    [3,4]
	]
	
	source = 0
	destination = 4
	
	Route 1:
	
	0 -> 1 -> 2 -> 4
	
	0 -> 1    0
	1 -> 2    1
	2 -> 4    0
	
	cost = 1
	
	Route 2:
	
	0 -> 3 -> 4
	
	cost = 0
	
	Therefore:
	
	Output
	0
	
	This example is useful because it makes clear that we are looking for the 
	minimum total violations, not the fewest number of edges.
	
	Key Observation
	
	Convert every preferred road:
	
	u -> v
	
	into two weighted edges:
	
	u -> v   weight 0
	v -> u   weight 1
	
	Now the problem becomes:
	
	Find the shortest path from source to destination in a graph where every 
	edge weight is either 0 or 1.
	
	That is exactly the use case for:
	
	0-1 BFS
	
	Using a deque:
	
	weight 0 -> addFirst()
	weight 1 -> addLast()
	Expected Complexity
	
	For:
	
	V = number of cities
	E = number of roads
	
	0-1 BFS runs in:
	
	Time:  O(V + E)
	Space: O(V + E)
	
	A normal Dijkstra solution is also correct:
	
	O((V + E) log V)
	
	but 0-1 BFS is preferable because edge weights are restricted to 0 and 1.
	
	Closest LeetCode Problems
	
	The closest matches are: 
	
	LC 1368 — Minimum Cost to Make at Least One Valid Path in a Grid
	
	Very similar concept:
	
	preferred direction = cost 0
	different direction = cost 1
	
	and naturally solved with 0-1 BFS.
	
	Also related:
	
	LC 2290 — Minimum Obstacle Removal to Reach Corner
	
	Another classic 0-1 BFS problem:
	
	normal move   -> cost 0
	remove obstacle -> cost 1
	
	There is no exact graph-based LeetCode equivalent, but 1368 is conceptually very close.
	
	Follow-up 1 — Return the Actual Route
	Priority: VERY HIGH
	
	Instead of returning only:
	
	minimum number of violations
	
	return the actual sequence of cities used by one optimal route.
	
	For example:
	
	roads = [
	    [0,1],
	    [2,1],
	    [2,3]
	]
	
	source = 0
	destination = 3
	
	Return something like:
	
	violations = 1
	path = [0,1,2,3]
	Expected Modification
	
	Maintain:
	
	parent[next] = current;
	
	whenever the shortest distance to next improves.
	
	After 0-1 BFS finishes, reconstruct:
	
	destination -> ... -> source
	
	and reverse it.
	
	What Google is testing
	
	Whether you can extend shortest-path logic without changing the core algorithm.
	
  
  * 
  * 
  * Follow-up 2 — Different Violation Costs
	Priority: VERY HIGH
	
	Suppose traversing a road backward no longer always costs 1.
	
	For every preferred road:
	
	u -> v
	
	you are given:
	
	reverseCost
	
	such that:
	
	u -> v           cost 0
	v -> u           cost reverseCost
	
	where:
	
	reverseCost >= 0
	
	For example:
	
	0 -> 1, reverseCost = 5
	1 -> 2, reverseCost = 2
	
	Return the minimum total violation cost.
	
	Key Discussion
	
	Now edge weights are no longer restricted to:
	
	0 or 1
	
	Therefore:
	
	0-1 BFS no longer applies.
	
	Use:
	
	Dijkstra's algorithm
	Complexity
	O((V + E) log V)
	
	This is a very natural interviewer follow-up because it tests whether you understand 
	why 0-1 BFS works rather than memorizing it.
	
  
  * 
  * Follow-up 3 — Minimum Violations, Then Minimum Roads
	Priority: HIGH
	
	If multiple routes have the same minimum number of violations, return the route using 
	the fewest roads.
	
	For example:
	
	Route A:
	violations = 1
	edges = 7
	
	Route B:
	violations = 1
	edges = 4
	
	Route B should win.
	
	Expected Discussion
	
	The optimization criteria become lexicographic:
	
	1. minimize violations
	2. minimize number of edges
	
	Possible state distance:
	
	(violations, edges)
	
	Compare routes using:
	
	smaller violations first
	then smaller edge count
	
	A Dijkstra-style priority queue over this pair is a clean solution.
	
	This tests whether you can evolve a single-cost shortest-path problem into a 
	multi-criteria shortest path problem.
	
	Follow-up 4 — Many Source/Destination Queries
	Priority: MEDIUM-HIGH
	
	Suppose the road network is fixed, but you receive millions of queries:
	
	(source1, destination1)
	(source2, destination2)
	...
	
	Each query asks for the minimum number of violations.
	
	Running:
	
	0-1 BFS
	
	from scratch for every query may become expensive.
	
	Discussion Points
	
	The answer depends heavily on graph size and query volume.
	
	For relatively small graphs, you could precompute minimum violation costs between all pairs.
	
	For example:
	
	run 0-1 BFS from every node
	
	giving approximately:
	
	O(V * (V + E))
	
	preprocessing and:
	
	O(1)
	
	per query.
	
	For very large sparse graphs, storing:
	
	O(V²)
	
	answers may be impractical, so alternatives include caching results for frequently 
	queried sources or running 0-1 BFS on demand.
	
	This is a good Google-style follow-up because it shifts the discussion from 
	single-query algorithms to system/workload trade-offs.
	
	Interview one-liner
	
	“Each preferred road becomes a zero-cost forward edge and a cost-one backward edge, 
	so this is a shortest-path problem with only 0/1 weights, which makes 0-1 BFS with 
	a deque the optimal solution.”
	 
 */