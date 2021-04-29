package Intuit;

import java.util.Arrays;
import java.util.PriorityQueue;

public class MaximumPerformanceOfATeam_1383_Hard {

	public static void main(String[] args) {
		
		int n = 6; 
		int[] speed = {2,10,3,1,5,8}; 
		int[] efficiency = {5,4,3,9,7,2}; 
		int k = 2;

		int res = maxPerformance(n, speed, efficiency, k);
		System.out.println("Expected: 60, Actual: " + res);
		
		n = 6; 
		k = 3;
		res = maxPerformance(n, speed, efficiency, k);
		System.out.println("Expected: 68, Actual: " + res);
		
		n = 6; 
		k = 4;
		res = maxPerformance(n, speed, efficiency, k);
		System.out.println("Expected: 72, Actual: " + res);
		
	}
	
	/*
	1. Sort efficiency with descending order. Because, afterwards, when we iterate whole engineers, 
		every round, when calculating the current performance, minimum efficiency is the efficiency 
		of the new incoming engineer.
	2. Maintain a pq to track of the minimum speed in the group. If size of group is == K, 
		kick the engineer with minimum speed out (since efficiency is fixed by new coming engineer, 
		the only thing matters now is sum of speed).
	3. Calculate/Update performance.
	
    Time: O(NlogN) for sorting
    Time: O(NlogK) for priority queue
    Space: O(N)
    
    Reference: 
    	https://leetcode.com/problems/maximum-performance-of-a-team/discuss/539687/JavaC%2B%2BPython-Priority-Queue
    	https://leetcode.com/problems/maximum-performance-of-a-team/discuss/539680/Java-Detailed-Explanation-PriorityQueue-O(NlogN)
    */
	public static int maxPerformance(int n, int[] speed, int[] efficiency, int k) {
		int MOD = (int) 1e9 + 7;
		
		int[][] efficiency_speed = new int[n][2]; // engineers
		
		for(int i=0; i<n; i++) {
			efficiency_speed[i] = new int[] {efficiency[i], speed[i]};
		}
		
		//printArray(efficiency_speed); //debug
		
        // O(NlogN) for sorting
		Arrays.sort(efficiency_speed, (a, b)->(b[0] - a[0])); //sort by efficiency in decreasing order
		
		//System.out.println("----");
		//printArray(efficiency_speed); //debug
		
		PriorityQueue<Integer> pq = new PriorityQueue<>(k, (a, b)-> (a-b)); // pq to track of the minimum speed in the group

		long res = 0; 
		long totalSpeed = 0;
		
		for(int[] es : efficiency_speed) {
			pq.add(es[1]); // add speed; O(NlogK) for priority queue
			totalSpeed += es[1];
			//totalSpeed = (totalSpeed + es[1]);
			
			if(pq.size() > k) totalSpeed -= pq.poll();
			
			res = Math.max(res, (totalSpeed * 	es[0]));	// min efficiency is the efficiency of new engineer
		}
		
		return (int)(res % MOD);
	}
	
	static void printArray(int[][] arr) {
		
		for(int[] a : arr) {
			System.out.println(Arrays.toString(a));
		}
	}

}
