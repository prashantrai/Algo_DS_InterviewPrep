package Uber;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class BusRoutes_815_Hard {

	public static void main(String[] args) {
		
		int[][] routes = {{1,2,7},{3,6,7}};
		int source = 1, target = 6;
		
		System.out.println("Expected: 2, Actual: "+numBusesToDestination(routes, source, target));

	}
	
	/*
	 The first part loop on routes and record stop to routes mapping in to_route.
	 The second part is general bfs. Take a stop from queue and find all connected route.
	 The hashset seen record all visited stops and we won't check a stop for twice.
	 We can also use a hashset to record all visited routes, or just clear a route after visit.
	 
	 1. For each of the bus stop, we maintain all the buses (bus routes) that go through 
	    it. To do that, we use a HashMap, where bus stop number is the key and all the 
	    buses (bus routes) that go through it are added to an Set/ArrayList.
	 
	 
	 */

	// Time: O(n^2); Space: O(n^2)
	public static int numBusesToDestination(int[][] routes, int S, int T) {
		
		int n = routes.length;
		
		// bus stop number is the key and all the 
	    // buses (bus routes) that go through it are added to an Set/ArrayList
		Map<Integer, Set<Integer>> to_route = new HashMap<>();
		
		if (S == T) return 0; 
		
		for(int i=0; i<n; i++) {
			for(int j : routes[i]) {
				if(!to_route.containsKey(j)) {
					to_route.put(j, new HashSet<Integer>());
				}
				to_route.get(j).add(i); 
			}
		}
		
		System.out.println(to_route);
		
		int res = 0;
		
		Queue<Integer> q = new ArrayDeque<>();
		Set<Integer> visited_bus = new HashSet<>();
		
		q.offer(S);
		
		while(!q.isEmpty()) {
			res++;
			int size = q.size();
			for(int i=0; i<size; i++) {
				int currStop = q.poll();
				Set<Integer> buses = to_route.get(currStop);
				
				for(int bus : buses) {
					if(visited_bus.contains(bus)) continue;
					visited_bus.add(bus);
					
					for(int j=0; j<routes[bus].length; j++) {
						if(routes[bus][j] == T) return res;
						q.offer(routes[bus][j]); // add the next stop in q
					} 
					
				}
			}
		}
		
		
		return -1;
	}
}
