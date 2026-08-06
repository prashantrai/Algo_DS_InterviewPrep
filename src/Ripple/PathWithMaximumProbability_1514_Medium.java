package Ripple;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class PathWithMaximumProbability_1514_Medium {

	private static void runTest(
            int n,
            int[][] edges,
            double[] succProb,
            int start,
            int end,
            double expected) {

        double actual = maxProbability(n, edges, succProb, start, end);
        boolean pass = Math.abs(actual - expected) <= 1e-5;

        System.out.printf(
                "Expected: %.5f, Actual: %.5f -> %s%n",
                expected, actual, pass ? "PASS" : "FAIL");
    }

    public static void main(String[] args) {

        // Normal case: indirect path better
        runTest(3,
                new int[][]{{0, 1}, {1, 2}, {0, 2}},
                new double[]{0.5, 0.5, 0.2},
                0, 2,
                0.25);

        // Normal case: direct path better
        runTest(3,
                new int[][]{{0, 1}, {1, 2}, {0, 2}},
                new double[]{0.5, 0.5, 0.3},
                0, 2,
                0.3);

        // Edge case: no path
        runTest(3,
                new int[][]{{0, 1}},
                new double[]{0.5},
                0, 2,
                0.0);

        // Edge case: single edge with probability 1
        runTest(2,
                new int[][]{{0, 1}},
                new double[]{1.0},
                0, 1,
                1.0);

        // Edge case: single edge with probability 0
        runTest(2,
                new int[][]{{0, 1}},
                new double[]{0.0},
                0, 1,
                0.0);

        // Tricky case: multi-hop beats direct
        runTest(4,
                new int[][]{{0, 1}, {1, 3}, {0, 2}, {2, 3}, {0, 3}},
                new double[]{0.9, 0.5, 0.8, 0.8, 0.3},
                0, 3,
                0.64);

        // Tricky case: cycle in graph
        runTest(3,
                new int[][]{{0, 1}, {1, 2}, {0, 2}},
                new double[]{1.0, 0.5, 0.4},
                0, 2,
                0.5);
    }
	
	
	/*  Time and Space Complexity
    Time: O((V + E) log V)
        Building the graph is O(E), and heap operations are O(log V).
    Space: O(V + E)
        For the adjacency list, best array, and priority queue.
	*/
	
	/* Node 0 needs to know where can I go?
	    and with what probability?
	
	    So one `Edge` object stores
	    to = 1 and probability = 0.5
	*/
	static class Edge {
	    int to;
	    double prob;
	    Edge(int to, double prob) { this.to = to; this.prob = prob; }
	}
	
	/*  "A possible path we are currently exploring.""
	    State does NOT represent the graph.
	    It represents
	    "A possible path we are currently exploring."
	
	    Suppose we've reached node 2 with probability 0.35
	
	    We push
	    State: node = 2, probability = 0.35 into the heap.
	    Later, maybe another path reaches node 2 with 0.41
	    
	    Now we push
	    State: node = 2, probability = 0.41 */
	static class State {
	    int node;
	    double prob;
	    State(int node, double prob) {this.node = node; this.prob = prob;}
	}
	
	public static double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
	    
	    // build adjacnecy list/graph
	    List<List<Edge>> graph = new ArrayList<>();
	    for(int i=0; i<n; i++) {
	        graph.add(new ArrayList<>());
	    } 
	
	    // populate adjacency list
	    for(int i=0; i<edges.length; i++) {
	        int u = edges[i][0];
	        int v = edges[i][1];
	        double prob = succProb[i];
	
	        graph.get(u).add(new Edge(v, prob));
	        graph.get(v).add(new Edge(u, prob));
	    }
	
	    // best[i] = best probability found so far to reach node i
	    double[] best = new double[n];
	    best[start] = 1.0;
	
	    PriorityQueue<State> maxHeap = new PriorityQueue<>((a, b) -> 
	    Double.compare(b.prob, a.prob));
	
	    maxHeap.offer(new State(start, 1));
	
	    while (!maxHeap.isEmpty()) {
	        State curr = maxHeap.poll();
	
	        if(curr.node == end) { 
	            return curr.prob;
	        }
	
	        // Ignore stale entry
	        if(curr.prob < best[curr.node]) continue;
	
	        // Explore Neighbors
	        /* 
	            Neighbors of node 0, 1 (0.5) and 2 (0.2)
	            For node 1 Current probability 1.0
	            Edge probability 0.5
	            So, newProbability = 1.0 × 0.5 = 0.5
	        */
	        for(Edge edge : graph.get(curr.node)) {
	            double newProb = curr.prob * edge.prob;
	
	            // Is this (newProb) better than what I already know?
	            if(newProb <= best[edge.to]) continue;
	
	            best[edge.to] = newProb;
	            maxHeap.offer(new State(edge.to, newProb));
	        }
	
	    }
	    return 0.0;
	}

}
