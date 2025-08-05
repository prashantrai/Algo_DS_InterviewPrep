package LinkedIn;
import java.util.*;

public class UserConnectionPath {
	
	/* 
	 * Question: Given a graph of users, return the connection_path 
	 * between them. Bob -> Alice -> Franck.. connection length is 2. 
	 * 
	 * Algorithm (BFS - Breadth First Search)
		Why BFS?
		- This is a classic shortest path in unweighted graph problem.
		- BFS ensures the shortest path (in terms of edges) is found first.
		
		Steps
		- Use a queue to perform BFS.
		- Track visited users in a Set to avoid cycles.
		- Track the path taken using a map (parentMap) to reconstruct the path from endUser to startUser.
		- Once endUser is found, use the parentMap to reconstruct the path.
	 
	 * 	Time Complexity: O(N + E)
			N = number of users (nodes)
			E = number of connections (edges)
	
		Space Complexity: O(N) For visited set, parent map, queue.
	 * 
	 * */

    public List<String> findConnectionPath(Map<String, List<String>> graph, String start, String end) {
        // Edge case: start or end missing
        if (!graph.containsKey(start) || !graph.containsKey(end)) return new ArrayList<>();
        
        // Edge case: start == end
        if (start.equals(end)) return Arrays.asList(start);

        Queue<String> queue = new LinkedList<>();
        Map<String, String> parentMap = new HashMap<>();
        Set<String> visited = new HashSet<>();

        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            for (String neighbor : graph.getOrDefault(current, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);  // track parent to build path
                    if (neighbor.equals(end)) {
                        return buildPath(parentMap, start, end);
                    }
                    queue.offer(neighbor);
                }
            }
        }

        // No connection found
        return new ArrayList<>();
    }

    private List<String> buildPath(Map<String, String> parentMap, String start, String end) {
        LinkedList<String> path = new LinkedList<>();
        String current = end;
        while (current != null) {
            path.addFirst(current);
            current = parentMap.get(current);
        }
        return path;
    }

    // ---------- TEST CASES ----------
    public static void main(String[] args) {
        UserConnectionPath solution = new UserConnectionPath();

        Map<String, List<String>> graph = new HashMap<>();
        graph.put("Bob", Arrays.asList("Alice", "David"));
        graph.put("Alice", Arrays.asList("Bob", "Franck"));
        graph.put("Franck", Arrays.asList("Alice", "George"));
        graph.put("David", Arrays.asList("Bob"));
        graph.put("George", Arrays.asList("Franck"));

        System.out.println(solution.findConnectionPath(graph, "Bob", "Franck")); // [Bob, Alice, Franck]
        System.out.println(solution.findConnectionPath(graph, "Bob", "George")); // [Bob, Alice, Franck, George]
        System.out.println(solution.findConnectionPath(graph, "George", "Bob")); // [George, Franck, Alice, Bob]
        System.out.println(solution.findConnectionPath(graph, "Bob", "Bob"));     // [Bob]
        System.out.println(solution.findConnectionPath(graph, "Bob", "Zoe"));     // []
    }
}
