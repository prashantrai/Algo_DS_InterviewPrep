package Ripple;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class CheapestFlightsWithinKStops_787_Medium {

	public static void main(String[] args) {

        // Normal case
        int[][] flights1 = {
                {0,1,100},
                {1,2,100},
                {0,2,500}
        };

        System.out.println(
                findCheapestPrice(
                        3,
                        flights1,
                        0,
                        2,
                        1)); // 200

        // No stops allowed.
        System.out.println(
                findCheapestPrice(
                        3,
                        flights1,
                        0,
                        2,
                        0)); // 500

        // Unreachable.
        int[][] flights2 = {
                {0,1,100},
                {1,0,100}
        };

        System.out.println(
                findCheapestPrice(
                        3,
                        flights2,
                        0,
                        2,
                        1)); // -1

        // Source equals destination.
        System.out.println(
                findCheapestPrice(
                        3,
                        flights1,
                        0,
                        0,
                        2)); // 0
    }
	
	
	 /* Complexity Analysis:
	    Let:
	    V = number of cities (n)
	    E = number of flights (flights.length)
	    
	    Time Complexity: O(k * E)
	    We perform (k + 1) iterations. In each iteration, we scan every 
	    flight (edge) exactly once. Since (k + 1) scales linearly with k, 
	    this simplifies to O(k * E).
	    
	    Space Complexity: O(V)
	    We maintain two distance arrays: 'dist[]' and 'nextDist[]', each 
	    requiring O(V) space. No additional data structures proportional 
	    to the number of flights (E) are created.
	*/
	
	/* Interview Script
	    "I use a modified Bellman-Ford algorithm. In Bellman-Ford, after 
	    one relaxation of all edges we know the cheapest cost using at most 
	    one edge, after two relaxations we know the cheapest cost using at 
	    most two edges, and so on. Since k stops means at most k + 1 flights, 
	    I perform exactly k + 1 relaxation rounds. In each round I update a 
	    temporary distance array instead of the original one, so every relaxation 
	    only uses distances computed in the previous round. This prevents chaining 
	    multiple flights within the same iteration and correctly enforces the stop limit."
	
	
	    Why don't we need a visited array?
	
	    Bellman-Ford is fundamentally different from BFS or Dijkstra.
	
	    It repeatedly relaxes every edge, regardless of whether a node has been 
	    seen before. The dist array already captures the best known cost after 
	    each allowed number of edges, so a visited array would actually prevent 
	    legitimate improvements discovered in later iterations.
	*/

	/* Algorithm (Modified Bellman-Ford)
	    *
	    * 1. dist[i] stores the minimum cost to reach city i.
	    *
	    * 2. Initially, only the source city is reachable.
	    *      dist[src] = 0
	    *      all other cities = INF
	    *
	    * 3. A path with k stops can use at most (k + 1) flights (edges),
	    *    so perform exactly (k + 1) relaxation rounds.
	    *
	    * 4. In each round:
	    *      - Create a copy of the current distance array (nextDist).
	    *      - Iterate through every flight (from -> to, cost).
	    *      - If 'from' is reachable, try relaxing the edge:
	    *            nextDist[to] = min(nextDist[to], dist[from] + cost)
	    *
	    * 5. IMPORTANT:
	    *      Always update nextDist, NOT dist.
	    *      This ensures every round uses only the distances computed
	    *      in the previous round, preventing multiple flights from
	    *      being taken in the same iteration.
	    *
	    * 6. After processing all flights, assign:
	    *      dist = nextDist
	    *
	    * 7. After (k + 1) rounds, dist[dst] contains the cheapest cost
	    *    within the allowed number of stops.
	    *
	    * Time  : O((k + 1) * E)
	    * Space : O(V)
	*/

	public static int findCheapestPrice(int n, int[][] flights,
	                             int src, int dst, int k) {
	
	    // dist[i] = minimum cost to reach city i
	    int[] dist = new int[n];
	
	    // Initially, every city is unreachable.
	    Arrays.fill(dist, Integer.MAX_VALUE);
	
	    // Cost to reach the source city is 0.
	    dist[src] = 0;
	
	    // We perform exactly (k + 1) relaxations.
	    // Why?
	    // k stops = k + 1 flights (edges).
	    for (int i = 0; i <= k; i++) {
	
	        // Create a copy of the current distances.
	        // This prevents using updated values from the same iteration,
	        // which would incorrectly allow multiple flights in one round.
	        int[] nextDist = Arrays.copyOf(dist, n);
	
	        // Try relaxing every flight (edge).
	        for (int[] flight : flights) {
	
	            // Extract flight information.
	            int from = flight[0];
	            int to = flight[1];
	            int price = flight[2];
	
	            // Skip if the source city of this flight
	            // has not been reached yet.
	            if (dist[from] == Integer.MAX_VALUE) {
	                continue;
	            }
	
	            // Cost to reach the destination city
	            // through this flight.
	            int newCost = dist[from] + price;
	
	            // Update only if we found a cheaper route.
	            if (newCost < nextDist[to]) {
	                nextDist[to] = newCost;
	            }
	        }
	
	        // Finish this relaxation round.
	        // These costs become the starting point
	        // for the next allowed flight.
	        dist = nextDist;
	    }
	
	    // Destination was never reached.
	    if (dist[dst] == Integer.MAX_VALUE) {
	        return -1;
	    }
	
	    // Cheapest price within k stops.
	    return dist[dst];
	}


	/* Approach 2: BFS with level order traversal 
	 
    Time Complexity :: 
    • Building graph: O(E) to add all edges. 
    • BFS traversal: O((k+1) * E) because it runs for at most k+1$levels and checks every edge at each level.

    Space Complexity :: 
    • Adjacency list: O(E) to store all flight edges. 
    • Queue: O(E) to hold elements during the search. 
    • Cost array: O(V) to track the cost for each vertex. 
    • Overall space: O(V + E) total memory usage. 
    */

    // Level order traversal
	public static int findCheapestPrice_BFS(int n, int[][] flights, int src, int dst, int k) {
        
        // Build adjacency list.
        List<List<Edge>> graph = new ArrayList<>();

        // Create empty list for every city.
        for(int i=0; i<n; i++) {
            graph.add(new ArrayList<>());
        }

        // Add every flight to the adjacemcy list.
        // flights[i] = [fromi, toi, pricei]
        for(int[] flight : flights) {
            int from = flight[0];
            int to = flight[1];
            int cost = flight[2];
            Edge edge = new Edge(to, cost);
            graph.get(from).add(edge);
        }
        
        // Store minimum cost to reach every city.
        int[] minCost = new int[n];

        // Initialize all cities as unreachable.
        Arrays.fill(minCost, Integer.MAX_VALUE);

        // Cost to reach source is zero.
        minCost[src] = 0;
        
        // BFS queue.
        Queue<State> q = new ArrayDeque<>();

        // Start from source.
        q.offer(new State(src, 0));

        // Number of flights taken so far.
        int level = 0;

        // Process at most k + 1 flight levels.
        // We are performing a level order traversal
        while (!q.isEmpty() && level <= k) {

            // Number of nodes in current level.
            int size = q.size();

            // Copy current costs so updates don't affect this level.
            int[] nextCost = Arrays.copyOf(minCost, n);

            while(size-- > 0 ) {
                // Remove one state.
                State current = q.poll();
                int currCost = current.cost;
                int currCity = current.city;
                
                // Skip if outdated.
                if(currCost > minCost[currCity]) {
                    continue;
                }

                // Visit neighbors.
                for(Edge edge : graph.get(currCity)) {
                    // Compute new cost.
                    int newCost = currCost + edge.cost;

                    // Found cheaper path.
                    if(newCost <  nextCost[edge.to]) {

                        // Update cost.
                        nextCost[edge.to] = newCost;

                        // Continue exploring.
                        q.offer(new State(edge.to, newCost));

                    }
                }
                
            }
            // Save updated costs after finishing this level.
            minCost = nextCost;

            // One more flight processed. Move to next level.
            level++;
        }

        // Return answer.
        return minCost[dst] == Integer.MAX_VALUE ? -1 : minCost[dst];

    }

    // Represents one outgoing flight.
    static class Edge {
        int to;
        int cost;
        Edge (int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    // Represents one state in the BFS queue.
    static class State {
        int city;
        int cost;
        State (int city, int cost) {
            this.city = city;
            this.cost = cost;
        }
    }

}


/*
Approach:: Level Order Traversal

Idea
Build an adjacency list.

Perform a BFS where:
    Each BFS level represents one additional flight taken.
    We process at most k + 1 levels.
    Store:
        current city
        current cost

Maintain an array: minCost[city], which stores the cheapest cost found so far.

Whenever we find a cheaper cost to a neighboring city, update it and push it into the queue.

Since we only process k+1 flight levels, we automatically satisfy the stop constraint.

Why this works?
Normally BFS is for unweighted graphs.
Here, we're not using BFS to find the shortest path by edge count.

Instead,
BFS controls how many flights we've used.
minCost[] ensures we only continue exploring cheaper routes.

Because every level equals one flight, we never exceed k stops.

Interview Script:: 
"Since there's a stop constraint, regular Dijkstra isn't sufficient because the cheapest path may exceed the allowed stops. Instead, I process the graph level by level, where each level corresponds to taking one more flight. I keep track of the cheapest cost to reach each city and only continue exploring if I improve that cost. Processing only k+1 levels guarantees the stop limit."



Step-by-Step Algorithm (Interview Explanation)
- Build an adjacency list.
- Create a queue.
- Push (src, 0) into the queue.
- Initialize minCost[src] = 0 and every other city as infinity (Integer.MAX_VALUE).

- Repeat while:
    queue not empty
    levels ≤ k+1

- For every node in the current level:
    Pop city and current cost.
    Visit every outgoing flight.
    Compute new cost.
    If cheaper than previous:
        update minCost
        push into queue.

- After processing k+1 levels:
    return answer if reachable
    otherwise return -1.

*/
