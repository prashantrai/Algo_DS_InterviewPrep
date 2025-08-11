package LinkedIn;

import java.util.*;

public class UserConnectionPath_BidirectionalBFS {
	
	// -------------- TESTS --------------
    public static void main(String[] args) {
        Map<String, List<String>> graph = new HashMap<>();
        graph.put("Bob",     Arrays.asList("Alice", "David", "Carl"));
        graph.put("Alice",   Arrays.asList("Bob", "Franck", "Eve"));
        graph.put("Franck",  Arrays.asList("Alice", "George"));
        graph.put("David",   Arrays.asList("Bob", "Helen"));
        graph.put("George",  Arrays.asList("Franck"));
        graph.put("Eve",     Arrays.asList("Alice", "Franklin"));
        graph.put("Carl",    Arrays.asList("Bob"));
        graph.put("Helen",   Arrays.asList("David"));
        graph.put("Franklin",Arrays.asList("Eve"));

        // direct celebrities both highly connected
        System.out.println(findConnectionPathBiBFS(graph, "Bob", "George"));
        // Bob → Alice → Franck → George

        // symmetric path
        System.out.println(findConnectionPathBiBFS(graph, "George", "Bob"));
        // George → Franck → Alice → Bob

        // trivial case
        System.out.println(findConnectionPathBiBFS(graph, "Bob", "Bob"));
        // [Bob]

        // no path
        System.out.println(findConnectionPathBiBFS(graph, "Bob", "Zoe"));
        // []
    }
    
	
	/* This solution is for follow-up question. Where the UserConnectionPath.java
	 * has been extended/update to support Bidirectional BFS. 
	 * 
	 * Wherever new code was added for the bi-directional logic, 
	 * we’ll see a // step 2: comment.
	 * 
	 * Question: Given a graph of users, return the connection_path 
	 * between them. Bob -> Alice -> Franck.. connection length is 2. 
	 * 
	 * Follw-up (Look at step 2 comment): 
     * Follow up: What is Bob has multiple users or bob is a celeb and Frank is also a celeb. 
     * The idea was to start with bi-directional BFS.
     * 
     * High-Level Steps
	   - Initialize two frontiers (frontierStart, frontierEnd), two visited maps (parentsStart, parentsEnd), and two visited sets.
	   - Alternate expanding the smaller frontier by one layer (to keep work balanced).
	   - Stop as soon as a meeting node is found in both visited sets.
	   - Reconstruct the path by walking parents from both sides to the meeting node.
	   
	 * Time & Space Complexity
		- Time: In the worst case, each side explores roughly 
			O((N + E)/2), so still O(N + E).

		- Space: We store two visited sets, two parent maps, 
			and two frontiers ⇒ O(N + E) overall.
     */
    public static List<String> findConnectionPathBiBFS(
        Map<String, List<String>> graph,
        String start, String end ) {
    	
        // Edge cases
        if (!graph.containsKey(start) || !graph.containsKey(end)) return Collections.emptyList();
        if (start.equals(end)) return Collections.singletonList(start);

        // step 2: Initialize frontiers for both directions
        Set<String> frontierStart = new HashSet<>();
        Set<String> frontierEnd   = new HashSet<>();
        frontierStart.add(start);
        frontierEnd.add(end);

        // step 2: Parent maps to reconstruct paths
        Map<String, String> parentsStart = new HashMap<>();
        Map<String, String> parentsEnd   = new HashMap<>();
        parentsStart.put(start, null);
        parentsEnd.put(end, null);

        // step 2: Visited sets
        Set<String> visitedStart = new HashSet<>();
        Set<String> visitedEnd   = new HashSet<>();
        visitedStart.add(start);
        visitedEnd.add(end);

        // step 2: The meeting node (when frontiers touch)
        String meetingNode = null;

        // step 2: Alternate BFS until one frontier is empty or we meet
        while (!frontierStart.isEmpty() && !frontierEnd.isEmpty()) {
            // Always expand the smaller frontier for efficiency
            if (frontierStart.size() <= frontierEnd.size()) {
                meetingNode = expandFrontier(
                    graph, frontierStart, visitedStart, visitedEnd, parentsStart);
            } else {
                meetingNode = expandFrontier(
                    graph, frontierEnd, visitedEnd, visitedStart, parentsEnd);
            }
            if (meetingNode != null) break;
        }

        // No connection found
        if (meetingNode == null) return Collections.emptyList();

        // step 2: Reconstruct path from start→meeting and meeting→end
        return buildBiPath(meetingNode, parentsStart, parentsEnd);
    }

    /**
     * Expands one layer of the given frontier. If a neighbor is already visited
     * by the opposite search, returns the meeting node.
     */
    private static String expandFrontier(
        Map<String, List<String>> graph,
        Set<String> frontier,
        Set<String> visitedThisSide,
        Set<String> visitedOtherSide,
        Map<String, String> parentsThisSide
    ) {
        Set<String> nextFrontier = new HashSet<>();
        for (String u : frontier) {
            for (String v : graph.getOrDefault(u, Collections.emptyList())) {
                if (visitedThisSide.contains(v)) continue;
                // Mark parent
                parentsThisSide.put(v, u);
                // If the other side has seen v, we’ve met!
                if (visitedOtherSide.contains(v)) {
                    return v;
                }
                visitedThisSide.add(v);
                nextFrontier.add(v);
            }
        }
        frontier.clear();
        frontier.addAll(nextFrontier);
        return null;
    }

    /**
     * Builds the full path when two BFS’s meet at `meetingNode`.
     */
    private static List<String> buildBiPath(
        String meetingNode,
        Map<String, String> parentsStart,
        Map<String, String> parentsEnd
    ) {
        LinkedList<String> path = new LinkedList<>();

        // Walk from meeting node back to start
        String cur = meetingNode;
        while (cur != null) {
            path.addFirst(cur);
            cur = parentsStart.get(cur);
        }

        // Walk from meeting node back to end (skip meetingNode to avoid duplication)
        cur = parentsEnd.get(meetingNode);
        while (cur != null) {
            path.addLast(cur);
            cur = parentsEnd.get(cur);
        }

        return path;
    }

    
}

