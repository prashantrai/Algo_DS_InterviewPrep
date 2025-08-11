package LinkedIn;
import java.util.*;

public class BiDirBfs_ConnectionDegreeAndPathFinder {
	
    public static void main(String[] args) {
        Map<String, List<String>> g = new HashMap<>();
        g.put("A", List.of("B", "C"));
        g.put("B", List.of("A", "D"));
        g.put("C", List.of("A", "E"));
        g.put("D", List.of("B", "F"));
        g.put("E", List.of("C", "F"));
        g.put("F", List.of("D", "E", "G"));
        g.put("G", List.of("F"));

        System.out.println("===== Simple connected =====");
        System.out.println("Path A->G: " + findConnectionPath(g, "A", "G")); // [A, B, D, F, G]
        System.out.println("Degree A->G: " + minDegreeConnection(g, "A", "G")); // 4

        System.out.println("\n===== Direct connection =====");
        System.out.println("Path A->B: " + findConnectionPath(g, "A", "B")); // [A, B]
        System.out.println("Degree A->B: " + minDegreeConnection(g, "A", "B")); // 1

        System.out.println("\n===== Same node =====");
        System.out.println("Path A->A: " + findConnectionPath(g, "A", "A")); // [A]
        System.out.println("Degree A->A: " + minDegreeConnection(g, "A", "A")); // 0

        System.out.println("\n===== Disconnected nodes =====");
        g.put("X", List.of("Y")); // isolated component
        g.put("Y", List.of("X"));
        System.out.println("Path A->X: " + findConnectionPath(g, "A", "X")); // []
        System.out.println("Degree A->X: " + minDegreeConnection(g, "A", "X")); // -1

        System.out.println("\n===== Node not in graph =====");
        System.out.println("Path A->Z: " + findConnectionPath(g, "A", "Z")); // []
        System.out.println("Degree A->Z: " + minDegreeConnection(g, "A", "Z")); // -1

        System.out.println("\n===== Graph with cycle =====");
        Map<String, List<String>> cycleGraph = new HashMap<>();
        cycleGraph.put("1", List.of("2", "3"));
        cycleGraph.put("2", List.of("1", "3"));
        cycleGraph.put("3", List.of("1", "2", "4"));
        cycleGraph.put("4", List.of("3", "5"));
        cycleGraph.put("5", List.of("4"));
        System.out.println("Path 1->5: " + findConnectionPath(cycleGraph, "1", "5")); // [1, 3, 4, 5]
        System.out.println("Degree 1->5: " + minDegreeConnection(cycleGraph, "1", "5")); // 3

        System.out.println("\n===== Multiple shortest paths =====");
        // Two equally short routes from S to T: S-A-T and S-B-T
        Map<String, List<String>> multiPath = new HashMap<>();
        multiPath.put("S", List.of("A", "B"));
        multiPath.put("A", List.of("S", "T"));
        multiPath.put("B", List.of("S", "T"));
        multiPath.put("T", List.of("A", "B"));
        System.out.println("Path S->T: " + findConnectionPath(multiPath, "S", "T")); // could be [S, A, T] or [S, B, T]
        System.out.println("Degree S->T: " + minDegreeConnection(multiPath, "S", "T")); // 2

        System.out.println("\n===== Large chain graph =====");
        // Linear chain: N1-N2-N3-...-N10
        Map<String, List<String>> chain = new HashMap<>();
        for (int i = 1; i <= 10; i++) chain.put("N" + i, new ArrayList<>());
        for (int i = 1; i < 10; i++) {
            chain.get("N" + i).add("N" + (i + 1));
            chain.get("N" + (i + 1)).add("N" + i);
        }
        System.out.println("Path N1->N10: " + findConnectionPath(chain, "N1", "N10"));
        System.out.println("Degree N1->N10: " + minDegreeConnection(chain, "N1", "N10")); // 9
    }


    /*
     * Question: 
     * 	You have given users and its direct connections. 
		For a given two users S and P, find minimum degree of connection. 
		Degree of directly connected users is 1.
	
		Follow up: Find the path
      
	 * Algorithm (step-by-step)
		1. If start == end return trivially.
		
		2. Do a bidirectional BFS from both start and end.
		
		3. Keep two visited sets and two prev maps (one per side) so we can 
			reconstruct the path when the searches meet.
		
		4. On each loop expand the smaller frontier (keeps work balanced).
		
		5. If a neighbor is already visited by the other side → we found the meeting node.
		
		6. Reconstruct the full path by walking prev from meeting node to start and from 
			meeting node toward end.
	
	* Time & space complexity
		Time: O(N + E) worst-case (N = nodes, E = edges). 
			In practice bidirectional BFS visits much fewer nodes.
		
		Space: O(N) for the two visited sets, prev maps and queues.
     */
  
    /* 
     * Dry run — two examples
		
		Graph (visual):
		A — B — D — F — G
		| \
		C — E — — —
		
		Example 1: A -> G
			Start frontier: {A}; End frontier: {G}.
			
			Expand A → discover B,C. No intersection.
			
			Expand G → discover F. No intersection.
			
			Expand smaller frontier (G side still small) 
				→ expand F → discover D,E (prev[F]=G). No intersection yet.
			
			Expand A side → expand B → discover D. D is already 
				discovered from F (other side) → meeting node = D.
			
			Reconstruct:
				start-side chain: D -> B -> A → reversed -> [A,B,D]
				end-side chain from D: F -> G → append -> [A,B,D,F,G]
			
			Path length = 5 nodes → degree (edges) = 5 - 1 = 4.
		
		
		Example 2: A -> B
		A neighbors B → B is immediately found on start side expansion; path [A,B], degree 1.
      
     * */
    
    
    // Minimum degree (edges) = path.size() - 1, or -1 if no path
    public static int minDegreeConnection(Map<String, List<String>> graph, String start, String end) {
        List<String> path = findConnectionPath(graph, start, end);
        return path.isEmpty() ? -1 : path.size() - 1;
    }

    // Follow-up: return actual path from start to end (inclusive). If no path, return empty list.
    public static List<String> findConnectionPath(Map<String, List<String>> graph, String start, String end) {
        if (start == null || end == null) return Collections.emptyList();
        if (start.equals(end)) return List.of(start);

        // Two frontiers, two visited sets and two predecessor maps (for path reconstruction).
        Queue<String> q1 = new LinkedList<>(); 
        Queue<String> q2 = new LinkedList<>();
        
        // visited
        Set<String> v1 = new HashSet<>(); 
        Set<String> v2 = new HashSet<>();
        
        /* These two maps store parent pointers — essentially, 
		"where did I come from?" during BFS traversal.
		
		parentFromBegin(p1) → Maps each visited node (string) 
			from the begin side to the node we came from.
		
		parentFromEnd(p2) → Maps each visited node from the end 
			side to the node we came from.
			
		By storing a parent pointer, we can reconstruct the 
			path after BFS finishes by walking backwards from the 
			meeting point to each source. */
        
        Map<String, String> p1 = new HashMap<>();
        Map<String, String> p2 = new HashMap<>();

        q1.offer(start); v1.add(start); p1.put(start, null);
        q2.offer(end);   v2.add(end);   p2.put(end, null);

        while (!q1.isEmpty() && !q2.isEmpty()) {
            // Expand the smaller frontier for efficiency
            String meet = (q1.size() <= q2.size())
                    ? expand(graph, q1, v1, v2, p1)
                    : expand(graph, q2, v2, v1, p2);
            if (meet != null) return buildPath(p1, p2, meet);
        }
        return Collections.emptyList(); // no connection
    }
    
    // Expand one level from `q`, return meeting node if this expansion hits other side
    /*
      	q — the queue/frontier for the side we're expanding.
		mine — visited set for this side.
		other — visited set for the opposite side.
		prev — predecessor map for this side (node → parent toward this side's root).
		
		Inner mechanics:
			int sz = q.size(); — capture current frontier size. 
				This ensures we expand one level only (the nodes 
				currently in q), preserving BFS layering.
			
			Loop sz times:
			
				cur = q.poll() — current node at this layer.
				
				For each neighbor nei of cur:
					If mine.contains(nei) → skip (already visited on 
					this side).
					
					prev.put(nei, cur) — store the parent pointer: 
						nei was reached from cur (this is toward this 
						side’s root).
					
					If other.contains(nei) → this node was already visited
					 by the opposite side earlier, so we found a meeting 
					 node; immediately return nei.
					
					Otherwise add nei to mine and q.offer(nei) so it will
					 be expanded in future iterations.
			
			If loop ends without meeting other side, return null.
		
     */
    private static String expand(Map<String, List<String>> graph, Queue<String> q,
                                 Set<String> mine, Set<String> other, Map<String, String> prev) {
        int sz = q.size();
        for (int i = 0; i < sz; i++) {
            String cur = q.poll();
            for (String nei : graph.getOrDefault(cur, Collections.emptyList())) {
                if (mine.contains(nei)) 
                	continue;     		// already seen this side
                
                prev.put(nei, cur);     // record predecessor (toward root of this side)
                
                if (other.contains(nei)) 
                	return nei;  	// meeting point
                
                mine.add(nei);
                q.offer(nei);
            }
        }
        return null;
    }
    
    /*
     Left part (start → ... → meet):
		- Start at cur = meet.
		
		- Repeatedly do left.addFirst(cur); 
			cur = prevStart.get(cur); until cur == null.
		
		- Because prevStart maps a node to its parent 
			toward the start root, adding each node with 
			addFirst reverses the order and produces 
			[start, ..., meet].

	  Right part (meet → ... → end):
		- Start from cur = prevEnd.get(meet) — note: 
				prevEnd maps nodes toward the end root. 
				prevEnd.get(meet) is the neighbor of meet on 
				the path toward end (or null if meet == end).
			
		- Repeatedly append cur with left.addLast(cur); 
			cur = prevEnd.get(cur) until cur == null. 
			This walks from the node immediately after 
			meet to end, in order.


     * */
    
    // Build full path from start -> ... -> meet -> ... -> end
    private static List<String> buildPath(Map<String, String> prevStart, Map<String, String> prevEnd, String meet) {
        LinkedList<String> left = new LinkedList<>();
        for (String cur = meet; cur != null; cur = prevStart.get(cur)) { 
        	left.addFirst(cur);
        }
        // append nodes after meet toward end (prevEnd maps node -> parent toward end)
        for (String cur = prevEnd.get(meet); cur != null; cur = prevEnd.get(cur)) {
        	left.addLast(cur);
        }
        return left;
    }
    
}
