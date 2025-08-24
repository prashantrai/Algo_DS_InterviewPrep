package LinkedIn;
import java.util.*;

public class BiDirBfs_LinkedInShortestPath {
    
	public static void main(String[] args) {
        // Graph builder
        Map<String, List<String>> g = new HashMap<>();
        g.put("A", List.of("B", "E"));
        g.put("B", List.of("A", "C", "F"));
        g.put("C", List.of("B", "D"));
        g.put("D", List.of("C", "G"));
        g.put("E", List.of("A", "F"));
        g.put("F", List.of("B", "E", "G"));
        g.put("G", List.of("D", "F", "H"));
        g.put("H", List.of("G"));

        // 1. Simple path
        System.out.println("distance 1 (A->D): " + findConnectionDistance(g, "A", "D"));
        System.out.println("Test 1 (A->D): " + shortestPath(g, "A", "D")); // shortest: [A, B, C, D]

        // 2. Same source and target
        System.out.println("distance 2 (A->A): " + findConnectionDistance(g, "A", "A")); // [A]
        System.out.println("Test 2 (A->A): " + shortestPath(g, "A", "A")); // [A]

        // 3. Disconnected nodes
        Map<String, List<String>> g2 = new HashMap<>();
        g2.put("X", List.of("Y"));
        g2.put("Y", List.of("X"));
        g2.put("Z", List.of("W"));
        g2.put("W", List.of("Z"));
        System.out.println("distance 3 (X->W): " + findConnectionDistance(g2, "X", "W")); // []
        System.out.println("Test 3 (X->W): " + shortestPath(g2, "X", "W")); // []

        // 4. Multiple shortest paths, picks one
        // Here A->B->F->G->H or A->E->F->G->H are both length 4
        System.out.println("distance 4 (A->H): " + findConnectionDistance(g, "A", "H"));	// [A, B, F, G, H]
        System.out.println("Test 4 (A->H): " + shortestPath(g, "A", "H"));	// [A, B, F, G, H]

        // 5. Graph with cycles
        g.put("H", List.of("G", "C")); // Adds cycle C->H->G->D->C
        System.out.println("distance 5 (A->H with cycle): " + findConnectionDistance(g, "A", "H")); // [A, B, C, H]
        System.out.println("Test 5 (A->H with cycle): " + shortestPath(g, "A", "H")); // [A, B, C, H]

        // 6. Node not present in graph
        System.out.println("distance 6 (A->Z missing node): " + findConnectionDistance(g, "A", "Z")); // []
        System.out.println("Test 6 (A->Z missing node): " + shortestPath(g, "A", "Z")); // []

        // 7. Single edge graph
        Map<String, List<String>> g3 = new HashMap<>();
        g3.put("P", List.of("Q"));
        g3.put("Q", List.of("P"));
        System.out.println("distance 7 (P->Q): " + findConnectionDistance(g3, "P", "Q")); // [P, Q]
        System.out.println("Test 7 (P->Q): " + shortestPath(g3, "P", "Q")); // [P, Q]
    }
	/* Question: 
	 	Find Linkedin connection distance: Shortest path between LinkedIn connections. 
		An adjacency list is given, a source and a target. 
		Find the connection distance between source and target. 
		Follow-up: Print the path as well.
	 * */
	/* Alog/Steps:
	Initialize:
	- Two queues (q1 from source, q2 from target).
	- Two visited maps (visited1, visited2) storing:
		The parent of each node (for path reconstruction).
	- Two distance maps (dist1, dist2) storing:
		Distance from source or target.
	
	Expand alternately:
	- Always pick the queue with fewer nodes to expand first.
	- For each neighbor:
		If already visited by the other side, we found a meeting point.
		Otherwise, add it to the queue, mark visited, and record parent + distance.
	
	Meeting point:
	- Once we find a node visited by both sides, we can reconstruct the path:
		Walk back using visited1 from source to meeting point.
		Walk back using visited2 from target to meeting point.
		Merge them to form the full path.

	Distance: Total distance = dist1[meetingNode] + dist2[meetingNode].
	
	🔹 Interview Script (What to Say)
	
	I’ll solve this using Bi-directional BFS.
	
	A normal BFS from source could be very expensive if the graph is large, 
	since LinkedIn has millions of users. With Bi-directional BFS, I start 
	from both source and target and expand outwards. When the searches meet, 
	I’ve found the shortest path.
	
	To optimize further, at each step I always expand the smaller frontier—this 
	ensures we explore fewer nodes overall.
	
	When a node is seen from both sides, I’ll reconstruct the path and 
	return both the path and the distance.
	
	This approach is efficient because the search space grows exponentially 
	in BFS, and halving the depth reduces nodes visited from O(b^d) to O(b^(d/2)), 
	where b is branching factor and d is depth. 
	 * */

	/* Complexity:
		Time: O(V + E) in worst case, but expected runtime is O(b^(d/2)), 
			which is significantly smaller in practice.
		Space: O(V)
	 * Script: 
	   	"We are solving shortest path in an unweighted graph using Bi-Dir BFS. 
	   	We search from both source and target simultaneously. This reduces the 
	   	search space from exponential O(b^d) to O(b^(d/2)). We keep track of 
	   	visited nodes and parent pointers on both sides. When the two searches 
	   	meet, we reconstruct the path and compute the distance. 
	   	The time complexity is O(V + E) in the worst case, but with bi-dir BFS, 
	   	it’s much faster in practice. 
	   	
	   	The space complexity is O(V) for queues, visited sets, and parent maps." 
	 * */
	
	// Minimum degree (edges) = path.size() - 1, or -1 if no path
    public static int findConnectionDistance(Map<String, List<String>> graph, String start, String end) {
        List<String> path = shortestPath(graph, start, end);
        return path.isEmpty() ? -1 : path.size() - 1;
    }
	
	public static List<String> shortestPath(Map<String, List<String>> graph, String src, String dst) {
        
		if (src.equals(dst)) 
			return List.of(src);
        
		if (!graph.containsKey(src) || !graph.containsKey(dst)) 
			return List.of();

        Queue<String> qSrc = new LinkedList<>(), qDst = new LinkedList<>();
        
        Map<String, String> pSrc = new HashMap<>(), pDst = new HashMap<>();
        
        Set<String> vSrc = new HashSet<>(), vDst = new HashSet<>();

        qSrc.add(src); vSrc.add(src); pSrc.put(src, null);
        qDst.add(dst); vDst.add(dst); pDst.put(dst, null);

        while (!qSrc.isEmpty() && !qDst.isEmpty()) {
        	// Always expand the smaller frontier for efficiency
            if (qSrc.size() <= qDst.size()) {
            	String meet = bfsStep(graph, qSrc, vSrc, vDst, pSrc);
            	
            	if (meet != null) 
            		return buildPath(meet, pSrc, pDst);
            } else {

            	String meet = bfsStep(graph, qDst, vDst, vSrc, pDst);
            	if (meet != null) 
            		return buildPath(meet, pSrc, pDst);
            }
        }
        
        return List.of(); // or Collections.emptyList()
    }

	// Expand one BFS frontier
    private static String bfsStep(Map<String, List<String>> graph, Queue<String> q,
                                  Set<String> visThis, Set<String> visOther,
                                  Map<String, String> pThis) {
    	
        for (int sz = q.size(); sz > 0; sz--) {
            String cur = q.poll();
            for (String nei : graph.getOrDefault(cur, List.of())) { // or Collections.emptyList()
               
            	if (visThis.contains(nei)) continue;
                
                pThis.put(nei, cur);
                
                if (visOther.contains(nei)) return nei;
                
                visThis.add(nei);
                q.add(nei);
            }
        }
        return null;
    }

    private static List<String> buildPath(String meet, 
    		Map<String, String> parentSrc, Map<String, String> parentDst) {
    	
        LinkedList<String> path = new LinkedList<>();
        
        String cur = meet;
        while (cur != null) {  // Walk back from meeting point to source
            path.addFirst(cur);
            cur = parentSrc.get(cur);
        }
        cur = parentDst.get(meet); // Skip meet itself on this side
        while (cur != null) {      // Walk forward from meeting point to target
            path.addLast(cur);
            cur = parentDst.get(cur);
        }
        System.out.println("Distance: " + (path.size() - 1));
        
        /* Other approach - works
        for (String x = meet; x != null; x = parentSrc.get(x))  path.addFirst(x);
        for (String x = parentDst.get(meet); x != null; x = parentDst.get(x))  path.addLast(x);
        */
        
        return path;
    }
    
}
