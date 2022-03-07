package doordash;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MinimumDegreeOfConnectedTrioinGraph_1761_Hard {

	public static void main(String[] args) {
        int[][] input = new int[][]{{1, 2}, {1, 3}, {3, 2}, {4, 1}, {5, 2}, {3, 6}};
        int n = 6;
        System.out.println("Expected: 3, Actual: " + minTrioDegree(n, input));
        //	Using arrays - This solution is faster than the above one
        System.out.println("Expected: 3, Actual: " + minTrioDegree2(n, input));
        
        int[][] input2 = {{1,3},{4,1},{4,3},{2,5},{5,6},{6,7},{7,5},{2,6}};
        n = 7;
        System.out.println("Expected: 0, Actual: " + minTrioDegree(n, input2));
        // Using arrays - This solution is faster than the above one
        System.out.println("Expected: 0, Actual: " + minTrioDegree2(n, input2));
        
    }
	
	
	/* Reference: https://medium.com/algorithm-and-datastructure/minimum-degree-of-a-connected-trio-in-a-graph-461bec572061
	 * 
	 * Trio: when connected nodes form a triangle. Like below,
	 * 
	 *              A
	 *            /	  \
	 *            B - C 
	 * 
	 * Here, degree is ZERO as there is no edge going outside of this connected to any node in Trio.
	 *              A
	 *            /	  \
	 *            B - C- 
	 * 
	 * Here, degree is ONE as there is one edge connected to C and also connecting something else
	 * outside of Trio.
	 * 
	 * How to calculate degree: 
	 * 	degree=total number of edges in the three nodes - 6
	 *  
	 *  What is 6: If Trio degree is ZERO then it will have 6 edges i.e. count for edge from each connected node in Trio
	 *  Example: When degree is ZERO then total edges are,
	 *  (total number of edges from A) 
	 *  	+ (total number of edges from B) 
	 *  	+ (total number of edges from C) = 6
	 *  
	 *  Example: in above where degree is ONE
	 *   degree = (total number of edges from A) 
	 *   			+ (total number of edges from B) 
	 *   			+ (total number of edges from C) - 6
	 *   degree = 2+2+3 - 7
	 *   degree = 1
	 *   
	 * 
	 */
	
	//Time: O(n^2) - now not sure if this is correct
	//Space: O(n^2)
	//NOTE: When I used HashSet instead of Set it was giving TLE on Leetcode
	// So updates from Map<Integer, Set<Integer>> to Map<Integer, HashSet<Integer>>
	
    public static int minTrioDegree(int n, int[][] edges) {
        Map<Integer, HashSet<Integer>> graph = new HashMap<>(); //adjecency matrix
        for(int[] e : edges) {
            graph.putIfAbsent(e[0], new HashSet<Integer>());
            graph.putIfAbsent(e[1], new HashSet<Integer>());
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
        }
        /* OR LIKE BELOW - 2 LINER APPROACH
        for (int[] e : edges) {
            graph.compute(e[0], (k,v) -> v == null ? new HashSet<Integer>() : v).add(e[1]);
            graph.compute(e[1], (k,v) -> v == null ? new HashSet<Integer>() : v).add(e[0]);
        }
        */

        int degree = Integer.MAX_VALUE;
        for(int[] e : edges) {
        	HashSet<Integer> x = graph.get(e[0]);
        	HashSet<Integer> y = graph.get(e[1]);
            
            for(int num : x) {
                if(y.contains(num)) { // found trio
                    // degree = (total edges from x) 
                    //        + (total edges from y) + (total edges from num) - 6
                    // why 6? : If Trio degree is ZERO then it will have 6 edges 
                    // i.e. count for edge from each connected node in Trio
                    int total = x.size() + y.size() + graph.get(num).size() - 6;
                    degree = Math.min(degree, total);
                }
            }
        }
        return degree == Integer.MAX_VALUE ? -1 : degree;
    }
	
    
    //Using arrays - This solution is faster than the above one
    // https://leetcode.com/problems/minimum-degree-of-a-connected-trio-in-a-graph/discuss/1686998/JAVA-Simple-Solution-%3A-Faster-than-96.
    // Time: O(N^3)
    //Space: O(n^2), adjacency matrix
    public static int minTrioDegree2(int n, int[][] edges) {
    	// adjacency matrix
    	boolean[][] graph = new boolean[n+1][n+1]; // if we keeping it n (not n+1) then while marking edge value in array use edge-1 for right index 
    	
    	//to store inDegrees to a node(NOTE: here inDegree and outDegree are same because it is Undirected graph)
    	int[] indegree = new int[n+1];
    	
    	for(int[] e : edges) {
    		int e0 = e[0];
    		int e1 = e[1];
    		graph[e0][e1] = true;
    		graph[e1][e0] = true;
    		
    		indegree[e0]++;
    		indegree[e1]++;
    	}
    	
    	int degree = Integer.MAX_VALUE;
        for(int i=1; i<=n; i++) {
            for(int j=i+1; j<=n; j++) {
                if(graph[i][j]) {
                    for(int k=j+1; k<=n; k++) {
                        if(graph[i][k] && graph[j][k]) {
                        	degree = Math.min(degree, indegree[i] + indegree[j] + indegree[k] - 6);
                        }
                    }
                }
            }
        }
        
        
        return degree == Integer.MAX_VALUE ? -1 : degree;
    }
}
