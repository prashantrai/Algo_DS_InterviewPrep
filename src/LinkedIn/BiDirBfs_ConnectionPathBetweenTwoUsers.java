package LinkedIn;
import java.util.*;

public class BiDirBfs_ConnectionPathBetweenTwoUsers {
	
	// Driver
    public static void main(String[] args) {
        Map<String, List<String>> graph = new HashMap<>();
        graph.put("Bob", Arrays.asList("Alice", "Charlie"));
        graph.put("Alice", Arrays.asList("Bob", "Frank", "David"));
        graph.put("Frank", Arrays.asList("Alice"));
        graph.put("Charlie", Arrays.asList("Bob", "David"));
        graph.put("David", Arrays.asList("Alice", "Charlie", "Eve"));
        graph.put("Eve", Arrays.asList("David"));

        // Simple case
        System.out.println(findConnectionPath(graph, "Bob", "Frank")); // Bob -> Alice -> Frank

        // Edge case: start == end
        System.out.println(findConnectionPath(graph, "Bob", "Bob")); // [Bob]

        // Complex case: Celebrity node
        graph.get("Alice").addAll(Arrays.asList("X", "Y", "Z", "M", "N", "O", "P")); // follow-up
        graph.put("X", Arrays.asList("Alice"));
        graph.put("Y", Arrays.asList("Alice"));
        graph.put("Z", Arrays.asList("Alice"));
        graph.put("M", Arrays.asList("Alice"));
        graph.put("N", Arrays.asList("Alice"));
        graph.put("O", Arrays.asList("Alice"));
        graph.put("P", Arrays.asList("Alice"));

        System.out.println(findConnectionPath(graph, "X", "P")); // X -> Alice -> P
    }
    
    /*
     * Problem Statement:
		Given a graph of users, return the connection path between two users.
		Example:
		
		Bob -> Alice -> Frank  
		Connection length = 2
		
		Follow-up Scenario:
		What if Bob has multiple users (multiple direct connections) or Bob 
		is a celebrity and Frank is also a celebrity? 	
     * 
     * Step-by-Step Algorithm (Bi-directional BFS)
		Goal: Find the shortest connection path between two users 
		in a social graph.
		
		Core Idea: Bi-directional BFS
		
		Steps:
		1. Initialize Two Queues — one starting from userA
		 (source) and another from userB (target).
		
		2. Visited Maps — Maintain two maps:
		
		- visitedFromA → keeps track of visited nodes 
			from userA’s side along with the parent for 
			path reconstruction.
		
		- visitedFromB → keeps track of visited nodes 
			from userB’s side along with the parent.
		
		3. BFS Alternating —
		
		- Always expand the smaller frontier first 
			(to minimize branching).
		
		- For each neighbor of the current node, 
			if it is already in the other visited map, 
			we found a meeting point.
		
		4. Path Reconstruction —
		
		- Trace back from the meeting point to userA 
			using visitedFromA.
		
		- Trace forward from the meeting point to userB
		 	using visitedFromB.
		
		- Combine to form the final path.
		
		5. Stop Early — As soon as a meeting point is found, 
			stop BFS — this guarantees the shortest path.
		
		
	  * Follow-up Handling: 
		Multiple direct connections: 
			No change — BFS naturally handles branching.
		
		Celebrity nodes (very high-degree): To avoid 
		performance blowup, expand the smaller queue first 
		(if size(queueA) > size(queueB) swap), minimizing 
		visits to huge adjacency lists.
		
		
		Time Complexity:
			Worst case: O(V + E)
			Average case (bi-directional advantage): O(b^(d/2)), 
				where b = branching factor, d = shortest path length.

		Space Complexity: O(V) for visited maps + queues.
		
     */
    
    // Finds the shortest connection path between two users
    public static List<String> findConnectionPath(Map<String, List<String>> graph, 
    		String start, String end) {
        
    	if (!graph.containsKey(start) || !graph.containsKey(end)) 
        	return Collections.emptyList();
        
        if (start.equals(end)) return Arrays.asList(start);

        Queue<String> qA = new LinkedList<>(), qB = new LinkedList<>();
        
        /* TIP: We actually do have a visited-node mechanism in the solution 
         * — it’s just hidden inside the parentA and parentB maps.
         * 
         * Here, instead of a separate visited set, we store each visited 
         * node in parentA (for BFS from start) or parentB (for BFS from end) 
         * as soon as we encounter it.

		   This has two purposes:
			Tracks visitation — If parentA.containsKey(node) is true, 
			that means we’ve already visited it from side A.

			Stores the parent for path reconstruction later.
         * */
        
        Map<String, String> parentA = new HashMap<>(); 
        Map<String, String> parentB = new HashMap<>();

        qA.add(start); parentA.put(start, null);
        qB.add(end);   parentB.put(end, null);

        while (!qA.isEmpty() && !qB.isEmpty()) {
            // Always expand the smaller queue first // follow-up
            if (qA.size() > qB.size()) {
                Queue<String> tmpQ = qA; 
                qA = qB; 
                qB = tmpQ;
                
                Map<String, String> tmpP = parentA; 
                parentA = parentB; 
                parentB = tmpP;
            }
            int size = qA.size();
            for (int i = 0; i < size; i++) {
                String node = qA.poll();
                
                for (String neighbor : graph.getOrDefault(node, Collections.emptyList())) {
                   
                	if (parentA.containsKey(neighbor)) 
                    	continue; // already visited from this side
                    
                    parentA.put(neighbor, node);

                    if (parentB.containsKey(neighbor)) {
                        return buildPath(neighbor, parentA, parentB);
                    }
                    qA.add(neighbor);
                }
            }
        }
        return Collections.emptyList(); // no connection
    }

    // Builds the path from start to end via meeting point
	private static List<String> buildPath(String meet, Map<String, String> parentA, 
			Map<String, String> parentB) {
		
        LinkedList<String> path = new LinkedList<>();
        String cur = meet;
        
        while (cur != null) { // trace back to start
            path.addFirst(cur);
            cur = parentA.get(cur);
        }
        
        cur = parentB.get(meet);
        while (cur != null) { // trace forward to end
            path.addLast(cur);
            cur = parentB.get(cur);
        }
        return path;
    }

    
}
