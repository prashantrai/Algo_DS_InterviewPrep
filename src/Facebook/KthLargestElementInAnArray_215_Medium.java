package Facebook;

import java.util.PriorityQueue;
import java.util.Queue;

public class KthLargestElementInAnArray_215_Medium {

	public static void main(String[] args) {
		int[] nums = {3,2,1,5,6,4}; 
		int k = 2;
		System.out.println("Expected: 5,  Actual: " + findKthLargest(nums, k));
		
		int[] nums2 = {3,2,3,1,2,4,5,5,6}; 
		k = 4;
		System.out.println("Expected: 4,  Actual: " + findKthLargest(nums2, k));
	}
	
	/*
    Time: O(N log K)
    Space: O(K)
    */
    
    public static int findKthLargest(int[] nums, int k) {
        Queue<Integer> minHeap = new PriorityQueue<>();
        
        for(int n : nums) {
            
            // 1. add to heap
            minHeap.offer(n); 
            
            // 2. check the size and poll() if greater than k
            if(minHeap.size() > k) 
                minHeap.poll();
        }
        
        return minHeap.peek();
    }

}
