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
	
	
	
	/* 
	 * Question: 
	 	Find Linkedin connection distance: Shortest path between LinkedIn connections. 
		An adjacency list is given, a source and a target. 
		Find the connection distance between source and target. 
	
		Follow-up: Print the path as well.
	 * 
	 * Complexity:
		Time: O(V + E) worst-case
		Space: O(V)
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

        Queue<String> q1 = new LinkedList<>(), q2 = new LinkedList<>();
        Map<String, String> p1 = new HashMap<>(), p2 = new HashMap<>();
        Set<String> v1 = new HashSet<>(), v2 = new HashSet<>();

        q1.add(src); v1.add(src); p1.put(src, null);
        q2.add(dst); v2.add(dst); p2.put(dst, null);

        while (!q1.isEmpty() && !q2.isEmpty()) {
            String meet = bfsStep(graph, q1, v1, v2, p1, p2);
            if (meet != null) return buildPath(meet, p1, p2);

            meet = bfsStep(graph, q2, v2, v1, p2, p1);
            if (meet != null) return buildPath(meet, p1, p2);
        }
        return List.of();
    }

    private static String bfsStep(Map<String, List<String>> g, Queue<String> q,
                                  Set<String> visThis, Set<String> visOther,
                                  Map<String, String> pThis, Map<String, String> pOther) {
    	
        for (int sz = q.size(); sz > 0; sz--) {
            String cur = q.poll();
            for (String nei : g.getOrDefault(cur, List.of())) {
               
            	if (visThis.contains(nei)) continue;
                
                pThis.put(nei, cur);
                
                if (visOther.contains(nei)) return nei;
                
                visThis.add(nei);
                q.add(nei);
            }
        }
        return null;
    }

    private static List<String> buildPath(String meet, Map<String, String> p1, Map<String, String> p2) {
        LinkedList<String> path = new LinkedList<>();
        
        for (String x = meet; x != null; x = p1.get(x)) 
        	path.addFirst(x);
        
        for (String x = p2.get(meet); x != null; x = p2.get(x)) 
        	path.addLast(x);
        
        return path;
    }
    
}
