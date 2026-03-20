package Apple;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LoadBalancer_Strip {
	public static void main(String[] args) {
		List<String> serverIds = Arrays.asList("a", "b", "c");
		 
		// This implementation assumes ties are broken in ascending order ("a" before "b"), but you can use any consistent tiebreaking system
	
		// 1st part -- working
		/*
		LoadBalancer lb = new LoadBalancer(serverIds);
		System.out.println(lb.route(2)); // returns a
		System.out.println(lb.route(1)); // returns b
		System.out.println(lb.route(1)); // returns c
		System.out.println(lb.route(1)); // returns b
		*/
		// 2nd part -- working
		try {

			LoadBalancer lb = new LoadBalancer(serverIds);
			System.out.println("Expected: a, Actual: " + lb.route(2, 100)); // returns a
			System.out.println("Expected: b, Actual: " + lb.route(1, 500)); // returns b
			System.out.println("Expected: c, Actual: " + lb.route(1, 500)); // returns c
			

			Thread.sleep(100);

			//lb.route(1, 100); // returns "a" since the request with Weight 2 has expired.
			System.out.println("Expected: a, Actual: " + lb.route(1, 100)); // returns a
		} catch (InterruptedException e) {
			     e.printStackTrace();
		}
	}
	
}

/////

// Note: This implementation assumes ties are broken in ascending order ("a" before "b"), 
// but you can use any consistent tiebreaking system
///* A Load Balancer accepts requests and sends a request
// to one of a set of associated servers

//            ________________
//       --> |               | ----> [ Machine 1 ]
//       --> |               |
//requests --> |     Load      | ----> [ Machine . ]
//       --> |   Balancer    |
//       --> |               | ----> [ Machine N ]
//           -----------------
//*/

//// TASK: Implement a load balancer that sends each request to the least utilized server.
//// Server load is equal to the total weight of all the requests it has processed so far.
// If 2 servers has the same load then one with above in Name/Sequence should be assighed with the load
//  e.g. among servers a,b,c,d if b and c has same load and a new request comes then that should
//     be routed to b

/*class LoadBalancer {

	Map<String, Integer> map;
	
	public LoadBalancer(List<String> serverIds) {
	  // Constructed with an iterable of user defined server IDs.
	  map = new TreeMap<>();
	  for(String id : serverIds)
	    map.put(id, 0);
	}
	
	public String route(int weight) {
	  // Takes a request's weight (load) and returns the server's ID the request should be sent to.
	  int minLoad = Integer.MAX_VALUE;
	  String nextServer = "";
	
	  for(String s : map.keySet()) {
	    if(map.get(s) < minLoad) {
	      minLoad = map.get(s);
	      nextServer = s;
	   }
	   
	  }
	
	  //update load
	  map.put(nextServer, map.get(nextServer)+weight);
	
	  return nextServer;
	}
}
*/


/* 2 Part
For this part, requests have an additional numeric property ttl (Time To Live)
that represents how long in milliseconds until the request's load / weight expires.
In the previous part, we assumed that the "weight" of a request
would exist on the server forever.
This time, we take a more realistic view
and assume that after some time has passed, 
we can assume a request's weight will expire on the server
and can no longer be considered.

Task: Implement a load balancer that sends each request to the least utilized server.
A server's utilization is the total weight of all requests
currently being processed
that haven't expired.
*/

class LoadBalancer {
	Map<String, List<Load>> map;
	Map<String, Integer> currentWeightMap; 
	
	public LoadBalancer(List<String> serverIds) {
	 map = new TreeMap<>();
	 currentWeightMap = new TreeMap<>();
	 for(String id : serverIds) {
	   map.put(id, new LinkedList<Load>());  //LinkedList because we have to remove the expired load
	   currentWeightMap.put(id, 0);
	 }
	
	}
	
	public String route(int weight, long ttl) {
		 int minWeight = Integer.MAX_VALUE;
		 String nextServer = "";
		
		 for(String s : map.keySet()) {
		     if(currentWeightMap.get(s) < minWeight) {
		       minWeight = map.get(s).size();
		       nextServer = s;
		     }
		 }
		
		 // Evict the expired Weight and update the weight in currentWeightMap
		 evictExpiredLoad(map, currentWeightMap);
		 
		 // create a new Loas and add to map
		 Load load = new Load(weight, ttl);
		 map.get(nextServer).add(load);
		 
		 // Update currentWeightMap by adding new weight to specific server
		 currentWeightMap.put(nextServer, currentWeightMap.get(nextServer)+weight);
		
		 
		 return nextServer;
	}
	
	private static void evictExpiredLoad(Map<String, List<Load>> map, Map<String, Integer> currentWeightMap) {
		
		for(String server : map.keySet()) {
			List<Load> loads = map.get(server);
			
			for(Load load : loads) {
				if(load.hasExpired()) {
					//update currentWeightMap: upate weight for the server
					currentWeightMap.put(server, currentWeightMap.get(server) - load.weight);
					loads.remove(load);
				}
			}
		}
		
	}

	static class Load {
		 int weight;
		 long timeInMillis;
		 long lifeTime;
		
		 public Load(int load, long lifeTime) {
		   this.weight = weight;
		   this.lifeTime = lifeTime;
		   this.timeInMillis = System.currentTimeMillis();
		 }
		
		 public boolean hasExpired() {
		   return System.currentTimeMillis() - timeInMillis > lifeTime;
		 }
	}

}


