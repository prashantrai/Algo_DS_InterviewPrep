package Bloomberg;

import java.util.Arrays;

public class TwoCityScheduling_1029_Medium {

	public static void main(String[] args) {
		
		int[][] costs = {{10,20},{30,200},{400,50},{30,20}};
		//System.out.println("Expected: 110, Actual: " + twoCitySchedCost(costs));
		System.out.println("Expected: 110, Actual: " + twoCitySchedCost2(costs));
		
		int[][] costs2 = {{259,770},{448,54},{926,667},{184,139},{840,118},{577,469}};
		//System.out.println("Expected: 1859, Actual: " + twoCitySchedCost(costs2));
	}

	/*
	 * Time: O(NlogN) because of sorting of input data.
	 * Space:O(logN), In Java, Arrays.sort() is implemented using a variant 
	 * 					of the Quick Sort algorithm which has a space complexity of O(logn). 
	 */
	
	public static int twoCitySchedCost(int[][] costs) {

		// sort on basis of absolute diff of cities - increasing order
		Arrays.sort(costs, (a1, a2) -> (a1[0] - a1[1]) - (a2[0] - a2[1]));
		
		/* Using comparator - before java 8
		 Arrays.sort(costs, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return (a[1] - a[0]) - (b[1] - b[0]);
            }
        });
		 * */
		
		/* Will result [[30, 200], [10, 20], [30, 20], [400, 50]]
		 * i.e. -170, -10, 10, 350
		 */
		System.out.println(Arrays.deepToString(costs));

		int cost = 0;
		int n = costs.length;
		
		// iterate the array and calculate but taking 0th index value for a for 
		// first half of the array and 1st index value for every 2nd half of the array

		for(int i=0; i<n/2; i++) {
			cost += costs[i][0] 
					 + costs[i+n/2][1]; // starting from 2nd half index for city B
			
		}
		
		return cost;

	}
	
	/* Another Approach: https://leetcode.com/problems/two-city-scheduling/discuss/667786/Java-or-C%2B%2B-or-Python3-or-With-detailed-explanation
	 *
	 * Algo: 
	 * 1. The idea is to send each person to city A. 
	 * 		costs = [[10,20],[30,200],[400,50],[30,20]]
	 * 
	 * 		So, totalCost = 10 + 30 + 400 + 30 = 470
	 * 
	 * 2. Now, Now, we need to send n persons to city B. Which persons do we need to send city B? 
	 * 	  Here, we need to minimize the cost. We have already paid money to go to city A.
	 *	  So, we'll the persons to city B who get more refund so that our cost will be minimized.
	 * 
	 * 	  So, maintain refunds of each person calculate the diff of each pair in the array
	 * 		costs = [[10,20],[30,200],[400,50],[30,20]] 
	 * 			
	 * 		after calculating diff to a new array refund will be
	 * 		refund[i] = cost[i][1] - cost[i][0]
	 * 		
	 * 		refund = [10, 170, -350, -10]
	 * 		Here, refund +ve means we need to pay -ve means we will get refund.
	 * 
	 * 2. Sort the array refund, 
	 * 		refund = [-350, -10, 10, 170]
	 * 
	 * 3. Now, get refund for N persons,
	 * 		totalCost += -350 -10 here we totalCost is 470 and we take the values from refund till N/2  
	 * 		i.e. totalCose = 110 
	 * 
	 * 
	 * 		
	 */
	
	public static int twoCitySchedCost2(int[][] costs) {
        int N = costs.length/2;
        int[] refund = new int[N * 2];
        int minCost = 0, index = 0;
        
        for(int[] cost : costs){
            refund[index++] = cost[1] - cost[0];
            minCost += cost[0];
        }
        
        Arrays.sort(refund);
        
        for(int i = 0; i < N; i++){
            minCost += refund[i];
        }
        
        return minCost;
    }
	
}
