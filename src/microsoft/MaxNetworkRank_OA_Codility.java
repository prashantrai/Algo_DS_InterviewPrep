package microsoft;

import java.util.Arrays;
import java.util.List;

public class MaxNetworkRank_OA_Codility {

	public static void main(String[] args) {
		
		List<Integer> starts = Arrays.asList(1, 2, 3, 3);
		List<Integer> ends = Arrays.asList(2, 3, 1, 4);
		int numOfCities = starts.size();
		int res = maxNetworkRank(starts, ends, numOfCities);
        System.out.println("Expected: 4, Actual: " + res);
	}
	
	/* https://algo.monster/problems/max_network_rank
	 
	  An infrastructure consisting of n cities from l to n, and m bidirectional roads between them are given. 
	  Roads do not intersect apart from at their start and end points (they can pass through underground tunnels 
	  to avoid collisions). For each pair of cities directly connected by a road, let’s define their network 
	  rank as the total number of roads that are connected to either of the two cities.
		
	  Write a function, given two arrays starts, ends consisting of m integers each and an integer n, 
	  where starts[i] and ends[i] are cities at the two ends of the i-th road, returns the maximal network 
	  rank in the whole infrastructure.
		
		Example:
		  Input:
			starts = [1, 2, 3, 3]
			ends = [2, 3, 1, 4]
			n = 4
		  Output:
			4
		
		Explanation:
			The chosen cities may be 2 and 3, and the four roads connected to them are: 
			(2,1), (2,3), (3,1), (3,4).
	  
	 * */
	
	
	
	public static int maxNetworkRank(List<Integer> starts, List<Integer> ends, int n) {
        int[] edgeCount = new int[n];
        int m = starts.size();
        int maxRank = Integer.MIN_VALUE;
        for (int i = 0; i < m; i++) {
            edgeCount[starts.get(i) - 1]++;
            edgeCount[ends.get(i) - 1]++;
        }
        for (int i = 0; i < m; i++) {
            int rank = edgeCount[starts.get(i) - 1] + edgeCount[ends.get(i) - 1] - 1;
            if (rank > maxRank) {
                maxRank = rank;
            }
        }
        return maxRank;
    }

}
