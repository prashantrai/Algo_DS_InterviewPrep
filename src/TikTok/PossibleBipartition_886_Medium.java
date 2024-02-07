package TikTok;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class PossibleBipartition_886_Medium {

	public static void main(String[] args) {

		int[][] dislikes = {{1,2},{1,3},{2,4}};
		int n = 4; 
		System.out.println("Expected: true Actual: " + possibleBipartition(n, dislikes));
		
		int[][] dislikes2 = {{1,2},{1,3},{2,3}};
		n = 3; 
		System.out.println("Expected: false Actual: " + possibleBipartition(n, dislikes2));
	}
	
	/*
    For explanation/algo : 
    https://www.youtube.com/watch?v=0ACfAqs8mm0&ab_channel=Techdose
    
    Complexity Analysis::
        Let E be the size of dislikes and N be the number of people.

        Time complexity: O(N+E)

        Each node is only queued once, which takes O(1) time for each node. 
        We also iterate over the edges of every node once (since we only 
        visit each node once, we won't iterate over a node's edges multiple times), 
        which adds O(E) time.
        
        We also need O(E) to initialize the adjacency list and O(N) 
        to initialize the color array.

        
        Space complexity: O(N+E)

        In the worst case, the queue can grow to a size linear with N.
        We also require O(E) and O(N) space for the adjacency list and 
        the color array respectively.
    */
    
    
    public static boolean possibleBipartition(int n, int[][] dislikes) {
        
        // create adjacency list of all connected nodes
        Map<Integer, List<Integer>> adj = new HashMap<>();
        for(int[] edge : dislikes) {
            int a = edge[0];
            int b = edge[1];
            adj.computeIfAbsent(a, value -> new ArrayList<Integer>()).add(b);
            adj.computeIfAbsent(b, value -> new ArrayList<Integer>()).add(a);
        }
        
        // We'll use graph cloring strategy to color visited nodes/elements
        int[] color = new int[n+1];
        Arrays.fill(color, -1); // 0 stands for red and 1 for blue
        
        for(int i=1; i<=n; i++) {
            if(color[i] == -1) {
                // for each pending component, run BFS
                if(!bfs(i, adj, color)) {
                    // Return false, if there is conflict in the component.
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private static boolean bfs(int source, Map<Integer, List<Integer>> adj, int[] color) {
        
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(source);
        color[source] = 0; // Start with marking source as `RED`.
        
        while(!q.isEmpty()) {
            int node = q.poll();
            
            if(!adj.containsKey(node))
                continue;
            
            for(int neighbor : adj.get(node)) {
                
                // if same color, means unbalanced graph/elements
                // and solution is not possible
                if(color[neighbor] == color[node])
                    return false;
                if(color[neighbor] == -1) {
                    color[neighbor] = 1 - color[node];
                    q.add(neighbor);
                }
            }
        }
        
        return true;
    }

}
