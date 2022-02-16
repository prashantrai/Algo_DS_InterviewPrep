package Vmware;

import java.util.PriorityQueue;

public class FurthestBuildingYouCanReach_1642_Medium {

	public static void main(String[] args) {

	}

	// https://leetcode.com/problems/furthest-building-you-can-reach/discuss/918515/JavaC%2B%2BPython-Priority-Queue
	
	/*

		Explanation
			Heap heap store k height difference that we need to use ladders.
			Each move, if the height difference d > 0,
			we push d into the priority queue pq.
			If the size of queue exceed ladders,
			it means we have to use bricks for one move.
			Then we pop out the smallest difference, and reduce bricks.
			If bricks < 0, we can't make this move, then we return current index i.
			If we can reach the last building, we return A.length - 1.
			
			
			Complexity
			Time O(NlogK)
			Space O(K)
	
		*/
	
	 public int furthestBuilding(int[] A, int bricks, int ladders) {
	        PriorityQueue<Integer> pq = new PriorityQueue<>();
	        for (int i = 0; i < A.length - 1; i++) {
	            int d = A[i + 1] - A[i];
	            if (d > 0)
	                pq.add(d);
	            if (pq.size() > ladders)
	                bricks -= pq.poll();
	            if (bricks < 0)
	                return i;
	        }
	        return A.length - 1;
	    }
	
}
