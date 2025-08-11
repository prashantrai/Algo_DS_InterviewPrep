package LinkedIn;
import java.util.*;

public class BiDirBfs_Generic {

    /**
     * Generic Bi-directional BFS for finding the shortest path between two nodes in an unweighted graph.
     * Works for:
     * Q1: PathBetween2Users
     * Q2: ConnectionDegreeAndPathFinder
     * Q3: LinkedInConnectionsShortestPath
     */
    public static List<String> biDirBfsPath(Map<String, List<String>> graph, String start, String end) {
		if (start == null || end == null)
			return Collections.emptyList();
		if (!graph.containsKey(start) || !graph.containsKey(end))
			return Collections.emptyList();
		if (start.equals(end))
			return List.of(start);

        Queue<String> q1 = new LinkedList<>(), q2 = new LinkedList<>();
        Map<String, String> p1 = new HashMap<>(), p2 = new HashMap<>();
        Set<String> v1 = new HashSet<>(), v2 = new HashSet<>();

        q1.add(start); v1.add(start); p1.put(start, null);
        q2.add(end);   v2.add(end);   p2.put(end, null);

        while (!q1.isEmpty() && !q2.isEmpty()) {
            // Celebrity optimization — expand smaller frontier first (Q1 follow-up)
            if (q1.size() > q2.size()) {
                Queue<String> tmpQ = q1; 
                q1 = q2; 
                q2 = tmpQ;
                
                Set<String> tmpV = v1;   
                v1 = v2;   
                v2 = tmpV;
                
                Map<String, String> tmpP = p1; 
                p1 = p2; 
                p2 = tmpP;
            }
            
            String meet = expandOneLevel(graph, q1, v1, v2, p1);
            
            if (meet != null) 
            	return buildPath(meet, p1, p2);
        }
        return Collections.emptyList(); // No connection
    }

    /** Expands one BFS level and returns meeting node if found. */
	private static String expandOneLevel(Map<String, List<String>> graph, 
			Queue<String> q, Set<String> mine,
			Set<String> other, Map<String, String> prev) {
		
        int size = q.size();
        for (int i = 0; i < size; i++) {
            String cur = q.poll();
            for (String nei : graph.getOrDefault(cur, Collections.emptyList())) {
                if (mine.contains(nei)) continue; // Already visited on this side
                
                prev.put(nei, cur);
                
                if (other.contains(nei)) return nei; // Found meeting point
                
                mine.add(nei);
                q.add(nei);
            }
        }
        return null;
    }

    /** Builds full path from start to end given meeting point. */
	private static List<String> buildPath(String meet, Map<String, String> p1, Map<String, String> p2) {
		LinkedList<String> path = new LinkedList<>();
		
		for (String cur = meet; cur != null; cur = p1.get(cur))
			path.addFirst(cur);
		
		for (String cur = p2.get(meet); cur != null; cur = p2.get(cur))
			path.addLast(cur);
		
		return path;
	}

    // ===== Q1: PathBetween2Users =====
    public static List<String> findConnectionPath_Q1(Map<String, List<String>> graph, String start, String end) {
        return biDirBfsPath(graph, start, end);
    }

    // ===== Q2: ConnectionDegreeAndPathFinder =====
    public static int minDegree_Q2(Map<String, List<String>> graph, String start, String end) {
        List<String> path = biDirBfsPath(graph, start, end);
        return path.isEmpty() ? -1 : path.size() - 1;
    }

    // ===== Q3: LinkedInConnectionsShortestPath =====
    public static int connectionDistance_Q3(Map<String, List<String>> graph, String start, String end) {
        List<String> path = biDirBfsPath(graph, start, end);
        return path.isEmpty() ? -1 : path.size() - 1;
    }

    
    
    // ================== TEST CASES ==================
    
    public static void main(String[] args) {
        runTests_Q1();
        runTests_Q2();
        runTests_Q3();
    }

    /** Q1 Test cases: PathBetween2Users (+ follow-up) */
    public static void runTests_Q1() {
        System.out.println("===== Q1 Tests: PathBetween2Users =====");
        Map<String, List<String>> g = new HashMap<>();
        g.put("A", new ArrayList<>(List.of("B", "C")));
        g.put("B", new ArrayList<>(List.of("A", "D", "E")));
        g.put("C", new ArrayList<>(List.of("A", "F")));
        g.put("D", new ArrayList<>(List.of("B")));
        g.put("E", new ArrayList<>(List.of("B", "F")));
        g.put("F", new ArrayList<>(List.of("C", "E", "G")));
        g.put("G", new ArrayList<>(List.of("F")));
        g.put("H", new ArrayList<>(List.of())); // Isolated node

        // Basic case
        System.out.println(findConnectionPath_Q1(g, "A", "G")); // Expect: A → C → F → G

        // Same node
        System.out.println(findConnectionPath_Q1(g, "A", "A")); // Expect: [A]

        // No connection
        System.out.println(findConnectionPath_Q1(g, "A", "H")); // Expect: []

        // Follow-up: unbalanced celebrity node
        g.put("X", new ArrayList<>()); 
        for (char ch = 'A'; ch <= 'G'; ch++) {
            g.get("X").add(String.valueOf(ch));
            g.get(String.valueOf(ch)).add("X");
        }
        System.out.println(findConnectionPath_Q1(g, "D", "F")); // Should be short due to celebrity optimization
    }

    /** Q2 Test cases: ConnectionDegreeAndPathFinder (+ follow-up: path) */
    public static void runTests_Q2() {
        System.out.println("===== Q2 Tests: ConnectionDegreeAndPathFinder =====");
        Map<String, List<String>> g = new HashMap<>();
        g.put("Bob", List.of("Alice", "Tom"));
        g.put("Alice", List.of("Bob", "Frank", "Mary"));
        g.put("Frank", List.of("Alice", "Mary"));
        g.put("Mary", List.of("Frank", "Alice", "Tom"));
        g.put("Tom", List.of("Mary", "Bob", "Eve"));
        g.put("Eve", List.of("Tom"));
        g.put("Zoe", List.of()); // Isolated

        // Normal path degree
        System.out.println("Degree: " + minDegree_Q2(g, "Bob", "Eve")); // Expect: 2

        // No connection
        System.out.println("Degree: " + minDegree_Q2(g, "Bob", "Zoe")); // Expect: -1

        // Follow-up: path
        System.out.println("Path: " + findConnectionPath_Q1(g, "Bob", "Eve")); // Expect: Bob → Tom → Eve
    }

    /** Q3 Test cases: LinkedInConnectionsShortestPath (+ follow-up: print path) */
    public static void runTests_Q3() {
        System.out.println("===== Q3 Tests: LinkedInConnectionsShortestPath =====");
        Map<String, List<String>> g = new HashMap<>();
        g.put("1", List.of("2", "3"));
        g.put("2", List.of("1", "4"));
        g.put("3", List.of("1", "5"));
        g.put("4", List.of("2", "5"));
        g.put("5", List.of("3", "4", "6"));
        g.put("6", List.of("5"));

        // Distance
        System.out.println("Distance: " + connectionDistance_Q3(g, "1", "6")); // Expect: 3

        // Path (Follow-up)
        System.out.println("Path: " + findConnectionPath_Q1(g, "1", "6")); // Expect: 1 → 3 → 5 → 6

        // Edge: same node
        System.out.println("Distance: " + connectionDistance_Q3(g, "1", "1")); // Expect: 0

        // No connection
        g.put("X", List.of());
        System.out.println("Distance: " + connectionDistance_Q3(g, "1", "X")); // Expect: -1
    }

    
    
}
