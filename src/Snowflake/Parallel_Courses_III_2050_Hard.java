package Snowflake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Parallel_Courses_III_2050_Hard {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	/*
    Time: O(n+e)
        It costs O(e) to build graph and O(n) to initialize maxTime, 
        queue, and indegree.
        
        Each node is pushed and popped to queue once, costing O(n). 
        We have a for loop inside the while loop, but this for loop is 
        iterating over edges. Because we only visit each node once, 
        each edge in the input can only be iterated over once as well. 
        This means all for loop iterations across the algorithm will cost O(e).
    
    Space: O(n+e)
        graph takes O(n+e) space, the queue can take up to O(n) space, 
        maxTime and indegree both take O(n) space
    */
    
    public int minimumTime(int n, int[][] relations, int[] time) {
       
        // A graph from relations. For convenience, 
        // we will change the nodes to be 0-indexed.
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for(int i=0; i<n; i++) {
            graph.put(i, new ArrayList<>());
        }
        
        // An array indegree of length n, representing the indegree of each node.
        int[] indegree = new int[n];
        
        for(int[] edge : relations) {
            int x = edge[0] - 1 ;
            int y = edge[1] - 1 ;
            graph.get(x).add(y);
            indegree[y]++;
        }
        
        // An array maxTime of length n, representing 
        // the maximum value of all paths ending at certain nodes.
        int[] maxTime = new int[n];
        
        Queue<Integer> q = new LinkedList<>();
        
        // For all nodes with indegree[node] = 0, 
        // push them to the queue and initialize maxTime[node] = time[node].
        for(int node = 0; node < n; node++) {
            if(indegree[node] == 0) {
                q.add(node);
                maxTime[node] = time[node];
            }
        }
        
        while(!q.isEmpty()) {
            int node = q.poll();
            
            // Iterate over graph[node]. For each neighbor
            for(int neighbor : graph.get(node)) {
                
                // Update maxTime[neighbor] with maxTime[node] + time[neighbor] 
                // if it is larger.
                // Decrement indegree[neighbor].
                // If indegree[neighbor] == 0, push neighbor to queue.
                
                maxTime[neighbor] 
                    = Math.max(maxTime[neighbor], maxTime[node] + time[neighbor]);
                
                indegree[neighbor]--;
                if(indegree[neighbor] == 0) {
                    q.add(neighbor);
                }
            }
        }
        int ans = 0;
        for(int node=0; node<n; node++) {
            ans = Math.max(ans, maxTime[node]);
        }
        return ans;
    }

}
