package Amazon;

public class MinimumTravelTimeBetweenFulfillmentStationsOnARing {

    public static void main(String[] args) {

        // ------------------------------------------------
        // Test Version 1: Equal travel time between stations
        // ------------------------------------------------

        int n1 = 10;
        int[] deliveries1 = {2, 9, 5};

        System.out.println("Version 1 - Equal edge cost");
        System.out.print("Test 1: " + minTravelTime(n1, deliveries1));
        System.out.println("  Expected: 9");

        // 0 -> 1 = 1
        // 1 -> 9 = min(8, 2) = 2
        // 9 -> 0 = 1
        // Total = 4
        int[] deliveries2 = {1, 9, 0};

        System.out.print("Test 2: " + minTravelTime(10, deliveries2));
        System.out.println("  Expected: 4");

        // No deliveries
        int[] deliveries3 = {};

        System.out.print("Test 3: " + minTravelTime(10, deliveries3));
        System.out.println("  Expected: 0");


        // ------------------------------------------------
        // Test Version 2: Different travel time per edge
        // ------------------------------------------------

        System.out.println("\nVersion 2 - Weighted edge cost");

        int[] travelTime = {2, 4, 1, 3, 5};

        // 0 -> 1 = 2
        // 1 -> 4:
        //     path1 = 4 + 1 + 3 = 8
        //     path2 = 2 + 5 = 7
        //     take 7
        // 4 -> 2 = 3 + 1 = 4
        // Total = 2 + 7 + 4 = 13
        int[] weightedDeliveries1 = {1, 4, 2};

        System.out.print(
            "Test 1: " +
            minTravelTimeWithWeightedEdges(travelTime, weightedDeliveries1)
        );
        System.out.println("  Expected: 13");


        // 0 -> 4:
        // direct through edge 4 -> 0 costs 5
        // other direction = 2 + 4 + 1 + 3 = 10
        // take 5
        int[] weightedDeliveries2 = {4};

        System.out.print(
            "Test 2: " +
            minTravelTimeWithWeightedEdges(travelTime, weightedDeliveries2)
        );
        System.out.println("  Expected: 5");


        // Same station repeatedly:
        // 0 -> 0 -> 0
        // Total = 0
        int[] weightedDeliveries3 = {0, 0};

        System.out.print(
            "Test 3: " +
            minTravelTimeWithWeightedEdges(travelTime, weightedDeliveries3)
        );
        System.out.println("  Expected: 0");
    }
	
	
	// Solution start here...
	
	/*
	 The key is that diff is not really “forward distance.” It is the distance 
	 along one of the two arcs between the stations.

		For:
		
		n = 10
		current = 2
		next = 9
		
		the stations on the ring are:
		
		0 - 1 - 2 - 3 - 4 - 5 - 6 - 7 - 8 - 9
		|                                   |
		+-----------------------------------+
		
		Math.abs(2 - 9) gives:
		
		diff = 7
		
		That corresponds to this path:
		
		2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9
		
		There are 7 edges.
		
		Now here's the important observation:
		
		The entire circle has exactly n edges.
		
		For n = 10, there are 10 edges total:
		
		0-1
		1-2
		2-3
		3-4
		4-5
		5-6
		6-7
		7-8
		8-9
		9-0
		
		If one route from 2 to 9 uses 7 of those edges, then the other route 
		must use all the remaining edges:
		
		10 - 7 = 3
		
		Those remaining 3 edges are:
		
		2 -> 1 -> 0 -> 9
		
		So:
		
		int diff = Math.abs(current - next);   // one arc
		int other = n - diff;                  // remaining arc
		
		That's why n - diff works.
		
		Another example

		Suppose:
		
		n = 10
		current = 2
		next = 5
		
		Then:
		
		diff = |2 - 5| = 3
		
		One path is:
		
		2 -> 3 -> 4 -> 5
		
		The whole circle has 10 edges, so the opposite route has:
		
		10 - 3 = 7
		
		And indeed:
		
		2 -> 1 -> 0 -> 9 -> 8 -> 7 -> 6 -> 5
		
		is 7 edges.
		
		So the relationship is always:
		
		path1 + path2 = n
		
		Therefore:
		
		path2 = n - path1
		
		And finally:
		
		Math.min(diff, n - diff)
		
		just chooses the shorter of the two arcs.
		
		The subtle thing to remember for the interview is: abs(a - b) is not 
		“clockwise distance”; it is the linear/index distance. On the ring, 
		that gives one arc, and n - diff gives the complementary arc.
		
	* */
	
	
	// Time:  O(m)
	// Space: O(1)
    
    // Version 1: Every adjacent move costs 1
	public static int minTravelTime(int n, int[] deliveries) {
		
		int totalTime = 0;
		int current = 0;
		
		for(int next : deliveries) {
			
			/* Interview script
			“For every delivery, I have two possible paths because the stations 
			form a circle. I calculate the direct difference between the station 
			numbers. Then the distance in the opposite direction is the total 
			number of stations minus that difference. I take whichever is smaller 
			and add it to the total.”
			
			“On a ring, if one path has length diff, the other path must have 
			length n - diff.”
			 * */
				
			int diff = Math.abs(current - next);
	        totalTime += Math.min(diff, n - diff);
	        current = next;
		}
		
		return totalTime;
		
	}
	
	
	/* The important progression to remember (for both Original and Weighted version)

		This is a nice follow-up because you can show the interviewer how you derived it:
		
		Original:
		one path = number of edges
		other path = n - number of edges
		
		
		Weighted:
		one path = sum of edge costs
		other path = total ring cost - one path
		
		
		Need fast range sum
		        ↓
		Prefix sum 
	 
	 * */
	
	/* IDEA: 
	Prefix sum is the key optimization

	Build:
	
	prefix[i]
	
	representing the total travel cost from station 0 up to station i.
	
	For:
	
	travelTime = [2, 4, 1, 3, 5]
	
	build:
	
	prefix[0] = 0
	prefix[1] = 2
	prefix[2] = 6
	prefix[3] = 7
	prefix[4] = 10
	prefix[5] = 15
	
	Think of it as:
	
	station:     0    1    2    3    4
	             |----|----|----|----|---- back to 0
	edge cost:      2    4    1    3    5
	
	
	prefix:
	             0    2    6    7   10   15
	
	The total cost of the entire ring is:
	
	totalRingCost = prefix[n] = 15
	
	Now we can calculate one path between any two stations in O(1).
	
	4. Example: 1 -> 4
	
	The cost along increasing station numbers is:
	
	1 -> 2 -> 3 -> 4
	
	Using prefix sums:
	
	prefix[4] - prefix[1]
	= 10 - 2
	= 8
	
	So:
	
	path1 = 8
	
	And just like the original problem, the other path is simply:
	
	totalRingCost - path1
	
	Therefore:
	
	15 - 8 = 7
	
	So:
	
	min(8, 7) = 7
	
	Notice how this is exactly the weighted version of:
	
	Math.min(diff, n - diff)
	
	Original:
	
	entire circle length = n
	one path = diff
	other path = n - diff
	
	Weighted version:
	
	entire circle cost = totalCost
	one path = pathCost
	other path = totalCost - pathCost
	
	That's the connection I would emphasize in the interview.
	 * */
	
	
	/* Weighted edges → prefix sums
	 The same two-path idea still applies, but now I can't use the difference 
	 between station indices because edges have different weights. 
	 
	 A straightforward solution would be to walk around the ring and sum the 
	 edge costs for both directions.”
	 
	 But this can take O(n) per delivery, and with With m deliveries: O(m * n)
	 
	 
	 # Prefix sum is the key optimization
	 
	 “The ring still gives me exactly two paths between any pair of stations. 
	 The difference from the original problem is that I can't use the number 
	 of edges anymore because every edge has a different cost.

	 So I'll pre-process the edge costs using a prefix sum. Then, for any 
	 two stations a and b, I can calculate the cost of the path between 
	 the smaller and larger station index in O(1) using the prefix array.

	 Since I also know the total cost of going around the entire circle, 
	 the cost of the other path is simply totalCost - pathCost.

	 I'll take the smaller of those two costs for every delivery transition.”
	 * */
	
	// Time:  O(n + m),  n = number of stations, m = number of deliveries
	// Space: O(n)
	
	// Version 2: Different travel time for every edge
	public static long minTravelTimeWithWeightedEdges(int[] travelTime, int[] deliveries) {
		
		int n = travelTime.length;
		
		// prefixSum[i] = cost to travel from station 0 to station i
		int[] prefixSum = new int[n+1];
		
		for(int i=0; i<n; i++) {
			prefixSum[i+1] = prefixSum[i] + travelTime[i];
		}
		
		int totalRingCost = prefixSum[n];
		int totalTime = 0;
		
		int current = 0;
		
		for(int next : deliveries) {
			
			/* When current > next?
				For example: 4 -> 1
				
				Don't make this more complicated than necessary.
				
				Take:
				left = Math.min(current, next);
				right = Math.max(current, next);
				
				This will give us the left and right postion to start with
			 */
			int left = Math.min(current, next);
			int right = Math.max(current, next);
			
			// Cost along one side of the ring.
			long path1 = prefixSum[right] - prefixSum[left];
			
			// The other side contains all remaining edges.
			long path2 = totalRingCost - path1;
			
			totalTime += Math.min(path1, path2);
			
			current = next;
		}
		
		return totalTime;	
		
	}
	
	/* Dry run: 

		Suppose:
		travelTime = [2, 4, 1, 3, 5]
		deliveries = [1, 4, 2]
		
		Prefix:
		prefix = [0, 2, 6, 7, 10, 15]
		totalRingCost = 15
		
		Start:
		current = 0
		0 -> 1
		path1 = prefix[1] - prefix[0]
		      = 2
		
		path2 = 15 - 2
		      = 13
		
		take 2
		
		Total:  2
		
		1 -> 4
			path1 = prefix[4] - prefix[1]
			      = 10 - 2 = 8
		
			path2 = 15 - 8 = 7
			
			take 7
		
		Total: 2 + 7 = 9
		
		4 -> 2
		We use:
			left = 2
			right = 4
		
		Then:
			path1 = prefix[4] - prefix[2]
		     	  = 10 - 6 = 4
		
		This represents:
			2 -> 3 -> 4
			1 + 3 = 4
		
		Other way:
			path2 = 15 - 4 = 11
		
		Take: 4
		
		Final: 2 + 7 + 4 = 13
	 
	 * */
	

}

/** Minimum Travel Time Between Fulfillment Stations on a Ring
 * 
 Problem Statement

	Amazon operates n fulfillment stations arranged in a circle.
	
	The stations are numbered:
	
	0, 1, 2, ..., n - 1
	
	Moving between two adjacent stations takes 1 unit of time.
	
	Because the stations form a circle:
	
	0 is adjacent to 1 and n - 1
	
	You initially start at station 0.
	
	You are given an integer array:
	
	deliveries
	
	where deliveries[i] represents the next fulfillment station you must visit.
	
	The stations must be visited in exactly the given order.
	
	Return the minimum total travel time required to complete all deliveries.
	
	For every pair of stations, you may travel either clockwise or counterclockwise.
	
	Example
	Input:
	n = 10
	deliveries = [2, 9, 5]
	
	
	Output:
	9
	Explanation
	
	Start at station:
	
	0
	0 → 2
	
	Clockwise:
	
	2
	
	Counterclockwise:
	
	8
	
	Minimum:
	
	2
	2 → 9
	
	Direct difference:
	
	|2 - 9| = 7
	
	Going the other way around the circle:
	
	10 - 7 = 3
	
	Minimum:
	
	3
	9 → 5
	|9 - 5| = 4
	
	
	10 - 4 = 6
	
	Minimum:
	
	4
	
	Therefore:
	
	2 + 3 + 4 = 9
	Key Formula
	
	For stations a and b:
	
	diff = abs(a - b)
	
	
	distance = min(diff, n - diff)
	
	Then process:
	
	0 -> deliveries[0]
	deliveries[0] -> deliveries[1]
	...
	Expected Complexity
	Time:  O(m)
	Space: O(1)
	
	where m = deliveries.length.
	
	This is likely intended as a relatively straightforward coding question, 
	with the follow-ups carrying more weight for a senior-level candidate.
	
	
	
	* Follow-up 1: Different Travel Times Between Stations [Weighted edges → prefix sums]
	
		Suppose traveling between adjacent fulfillment stations no longer always costs 1.
		
		You are given:
		
		travelTime[i]
		
		where travelTime[i] is the time required to travel between:
		
		station i
		and
		station (i + 1) % n
		
		Return the minimum total travel time required to visit all delivery stations in order.
		
		Example
		n = 5
		
		
		travelTime = [2, 4, 1, 3, 5]
		
		This means:
		
		0 --2-- 1
		1 --4-- 2
		2 --1-- 3
		3 --3-- 4
		4 --5-- 0
		
		For every move a -> b, determine whether clockwise or counterclockwise travel is cheaper.
		
		Expected Approach
		
		Build prefix sums over edge costs.
		
		For example:
		
		prefix[i] =
		total cost of edges before i
		
		Then clockwise distance between two stations can be obtained in O(1).
		
		If:
		
		totalCost = sum(travelTime)
		
		and one direction costs:
		
		d
		
		then the other direction costs:
		
		totalCost - d
		
		Therefore:
		
		min(d, totalCost - d)
		
		Each delivery can still be processed in constant time after preprocessing.
		
		Complexity:
		
		Preprocessing: O(n)
		Deliveries:    O(m)
		
		
		Total:         O(n + m)
		Space:         O(n)
		
		This is a very realistic senior-engineer follow-up because it tests whether you 
		recognize that the original arithmetic formula was relying on uniform edge weights. 
	
	
	
	* Follow-up 2: Deliveries Can Be Visited in Any Order

		What if the stations no longer have to be visited in the specified order? Find the 
		minimum travel time required to visit every requested station.
		
		This is a fundamentally different problem.
		
		You can no longer simply sum pairwise distances. The interviewer is now testing 
		whether you recognize that changing the ordering constraint changes the algorithm entirely.
		
		On a circle, there are useful special properties that can be exploited rather 
		than treating this as a generic Traveling Salesman Problem.
		
	* Follow-up 3: Some Connections Are Closed
	
		Some edges between adjacent stations may temporarily be unavailable. Determine 
		whether all deliveries can still be completed, and if so return the minimum travel time.
		
		On a simple cycle, removing edges can turn the graph into paths or disconnect it.
		
		If the interviewer generalizes beyond a ring, this naturally turns into a shortest-path 
		problem.
		
	
	* Follow-up 4: Arbitrary Transportation Network
	
		Instead of stations arranged in a circle, stations form a general graph with weighted roads. 
		Visit the requested stations in the given order with minimum travel time.
		
		Now for each:
		
		deliveries[i] -> deliveries[i + 1]
		
		you need shortest-path distance.
		
		Depending on constraints:
		
		Dijkstra
		precomputation
		multi-source techniques
		all-pairs shortest paths
		
		could become relevant.
	
 * */
