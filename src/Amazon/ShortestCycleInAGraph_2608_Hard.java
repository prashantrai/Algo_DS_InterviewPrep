package Amazon;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class ShortestCycleInAGraph_2608_Hard {

	public static void main(String[] args) {
	    // 1) Example: triangle + another cycle
	    runTest(
	        7,
	        new int[][]{
	            {0, 1}, {1, 2}, {2, 0},
	            {3, 4}, {4, 5}, {5, 6}, {6, 3}
	        },
	        3
	    );

	    // 2) No cycle
	    runTest(
	        4,
	        new int[][]{
	            {0, 1}, {0, 2}
	        },
	        -1
	    );

	    // 3) Simple triangle
	    runTest(
	        3,
	        new int[][]{
	            {0, 1}, {1, 2}, {2, 0}
	        },
	        3
	    );

	    // 4) Square cycle
	    runTest(
	        4,
	        new int[][]{
	            {0, 1}, {1, 2}, {2, 3}, {3, 0}
	        },
	        4
	    );

	    // 5) Line graph, no cycle
	    runTest(
	        5,
	        new int[][]{
	            {0, 1}, {1, 2}, {2, 3}, {3, 4}
	        },
	        -1
	    );

	    // 6) Multiple cycles, shortest is triangle
	    runTest(
	        6,
	        new int[][]{
	            {0, 1}, {1, 2}, {2, 0},
	            {2, 3}, {3, 4}, {4, 5}, {5, 2}
	        },
	        3
	    );

	    // 7) Disconnected graph, one component has cycle
	    runTest(
	        8,
	        new int[][]{
	            {0, 1}, {1, 2}, {2, 0},
	            {4, 5}, {5, 6}
	        },
	        3
	    );

	    // 8) Minimum size no cycle
	    runTest(
	        2,
	        new int[][]{
	            {0, 1}
	        },
	        -1
	    );

	    // 9) Larger cycle only
	    runTest(
	        5,
	        new int[][]{
	            {0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 0}
	        },
	        5
	    );
	}

	private static void runTest(int n, int[][] edges, int expected) {
	    int actual = findShortestCycle(n, edges);
	    System.out.println("Expected = " + expected + ", Actual = " + actual
	            + (expected == actual ? " ✅" : " ❌"));
	}
	
	/* 
    Idea / Thought process
        The graph is unweighted, and we need the shortest cycle.
        In an unweighted graph, BFS is the best tool for shortest path in terms of number of edges.
        So for each node:
            run BFS,
            track distance from the start,
            track parent to avoid counting the immediate back edge in an undirected graph.
        
        During BFS, if we see an already visited neighbor that is not the parent, then we found a cycle:
            one path comes from the start to current
            another path comes from the start to neighbor
            plus the edge between them
            So cycle length is:
            dist[current] + dist[neighbor] + 1

        Why run BFS from every node?
        Because the shortest cycle can be anywhere in the graph.
    */
    /*
    Interview script
        You can say:

        “Since the graph is unweighted and I need the shortest cycle length, BFS is a natural choice.”
        “I run BFS from every node because the shortest cycle may start anywhere.”
        “During BFS, if I reach a visited neighbor that is not the parent, I found a cycle.”
        “Its length is dist[u] + dist[v] + 1.”
        “I keep the minimum over all BFS runs.”
        “This is simple, clean, and works well for n, m <= 1000.”

    */
    /*
    Step-by-step algorithm
        1. Build adjacency list.
        2. Initialize answer as a large value.
        3. For every node start:
            create dist[] filled with -1
            create parent[] filled with -1
            start BFS from start
        4. While doing BFS:
            if neighbor is unvisited, visit it
            else if neighbor is not the parent of current node, a cycle is found
        5. Keep the minimum cycle length.
        6. If no cycle was found, return -1.
    
    */
    /*
    Complexity
        Time: O(n * (n + m)), BFS from each node
        Space: O(n + m), graph + BFS arrays

        Where: n = number of nodes, m = number of edges
    */

    public static int findShortestCycle(int n, int[][] edges) {
        
        // Build graph (adjacency list)
        List<Integer>[] graph = new ArrayList[n];
        for(int i=0; i<n; i++) {
            graph[i] = new ArrayList<>();
        }
        for(int[] edge : edges) {
            // edges[i] = [ui, vi]
            // denotes an edge between vertex ui and vertex vi.
            int u = edge[0];
            int v = edge[1];
            // connnecting both nodes with each other
            graph[u].add(v);
            graph[v].add(u);
        }
        
        int ans = Integer.MAX_VALUE;

        // Run BFS from every node
        for(int start = 0; start<n; start++) {
            int cycleLen = bfs(start, graph, n);
            ans = Math.min(ans, cycleLen);
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;

    }

    private static int bfs(int start, List<Integer>[] graph, int n) {
        int[] dist = new int[n];
        int[] parent = new int[n];
        Arrays.fill(dist, -1);
        Arrays.fill(parent, -1);

        Queue<Integer> q = new ArrayDeque<>();
        q.offer(start);
        dist[start] = 0;

        int shortest = Integer.MAX_VALUE;

        while (!q.isEmpty()) {
            int curr = q.poll();

            for(int neigh : graph[curr]) {
                // First time visiting this neighbor
                if(dist[neigh] == -1) {
                    dist[neigh] = dist[curr] + 1;
                    parent[neigh] = curr;
                    q.offer(neigh);
                }
                // cycle found
                // Visited already and not the direct parent:
                // this forms a valid cycle in an undirected graph
                else if (parent[curr] != neigh) {
                    int cycleLen = dist[curr] + dist[neigh] + 1;
                    shortest = Math.min(shortest, cycleLen);
                }
            }
        }

        return shortest;
    }

}
