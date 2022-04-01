package leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class NetworkDelayTime_743_Medium {

	public static void main(String[] args) {

		int[][] times = {{2,1,1},{2,3,1},{3,4,1}}; 
		int n = 4, k = 2;
		
		int res = networkDelayTime(times, n, k);
		System.out.println("Expected: 2, Actual: " + res);
	}
	
	
	/* Dijkstra: refer : https://leetcode.com/problems/network-delay-time/discuss/210698/Java-Djikstrabfs-Concise-and-very-easy-to-understand
	 * 
	 * Dijkstra is like BFS, just use PriorityQueue in stead of normal Queue and add the 
	 * distance with next node when you move to next node.
	 * 
	 * Time: O(E log N), The maximum number of vertices added to the priority 
	 * 		queue is E. Thus, push and pop operations on the priority queue 
	 * 		take O(logE) time. 
	 * 
	 * 		The value of E can be at most N * (N−1). Therefore, 
	 * 		O(logE) is equivalent to O(logN^2) 
	 * 		which in turn equivalent to O(2*logN). 
	 * 		
	 * 		Hence, the time complexity for priority queue operations equals O(logN).
	 * 
	 * Space: O(N), map, set and priorityQueue
	 * 
	 * */
	
	// Dijkstra/BFS
	public static int networkDelayTime(int[][] times, int n, int k) {
		
		Map<Integer, Map<Integer, Integer>> adjMap = getAdjacencyMatrix(times);
		
		Set<Integer> visited = new HashSet<>();
		
		//(distance, node) into pq - minHeap
        Queue<int[]> pq = new PriorityQueue<>((a, b) -> (a[0] - b[0])); // sort by distance - increasing

        pq.offer(new int[] {0, k}); // k is src and distance from src to src is 0
        
        int res = 0;
        int nodesCount = n; // total unvisited nodes
        
        while(!pq.isEmpty()) {
        	int[] cur = pq.poll();
        	int curDist = cur[0];
        	int curNode = cur[1];
        	
        	if(visited.contains(curNode)) continue;
        	visited.add(curNode);
        	
        	res = curDist;
        	
        	nodesCount--; // we visited a node
        	
        	if(adjMap.containsKey(curNode)) {
        		Set<Integer> keySet = adjMap.get(curNode).keySet(); // add all neighbors in the pq 
        		for(int next : keySet) {
        			int dist = adjMap.get(curNode).get(next); // distance
        			pq.offer(new int[] {curDist + dist, next}); // Dijkstra: add the current distance, i.e. total distance to reach to next node
        		}
        	}
        }
        
        return nodesCount == 0 ? res : -1;
        
    }
	
	/* 
	 * Map<Integer, Map<Integer, Integer>> to store the source node, target node 
	 * and the distance between them.
	 */
	private static Map<Integer, Map<Integer, Integer>> getAdjacencyMatrix(int[][] times) {
		Map<Integer, Map<Integer, Integer>> adjMap = new HashMap<>();
		
		/* times[i] = {source, destination, distance} */
		for(int[] time : times) {
			int src = time[0];
			int dest = time[1];
			int distance = time[2];
			
			// store the source node as key. The value is another map of the neighbor nodes and distance.
			adjMap.putIfAbsent(src, new HashMap<>());
			adjMap.get(src).put(dest, distance);
		}
		return adjMap;
	}

}
