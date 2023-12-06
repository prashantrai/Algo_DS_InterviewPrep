package Snowflake;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphValidTree_261_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/*
    For the graph to be a valid tree, it must have exactly n - 1 edges. Any less, and it can't 
    possibly be fully connected. Any more, and it has to contain cycles. Additionally, if the graph 
    is fully connected and contains exactly n - 1 edges, it can't possibly contain a cycle, and 
    therefore must be a tree!

    Going by this definition, our algorithm needs to do the following:

    Check whether or not there are n - 1 edges. If there's not, then return false.
    Check whether or not the graph is fully connected. Return true if it is, false if otherwise.
    */
    
    /*
    Time: O(N)
    Space: O(N)
    */
    
    // BFS - taken from solutions
    public boolean validTree(int n, int[][] edges) {
     
        if(edges.length != n-1) return false;
        
        // Make the adjacency list.
        List<List<Integer>> adjList = new ArrayList<>();
        for(int i=0; i<n; i++) {
           adjList.add(new ArrayList<>());
        }
        for(int[] edge : edges) {
            adjList.get(edge[0]).add(edge[1]);
            adjList.get(edge[1]).add(edge[0]);
        }
        
        Set<Integer> seen = new HashSet<>();
        Deque<Integer> q = new ArrayDeque<>(); // or LinkedList
        q.offer(0);
        seen.add(0);
        
        while(!q.isEmpty()) {
            int node = q.poll();
            for(int neighbour : adjList.get(node)) {
                if(seen.contains(neighbour))
                    continue;
                
                seen.add(neighbour);
                q.offer(neighbour);
            }
        }
        return seen.size() == n;
    }

}
