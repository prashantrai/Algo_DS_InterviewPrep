package Oracle;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class SlidingWindowMaximum_239_Hard {

	// Note: main is the end of the method
	
	
	/* Interview Script
    “Brute force is O(nk), too slow.”
    “I need a structure that keeps track of the maximum while the window moves.”
    “I’ll use a decreasing deque of indices.”
    “The front always holds the maximum of the current window.”
    “Before inserting a new element, I remove smaller elements from the back since they can never be maximum again.”
    “I also remove expired indices from the front.”
    “Each index is pushed and popped at most once, so total time is O(n).”
	*/
	
	/* Algo: 
	    For each index i:
	    - Remove indices from the front if they are outside the window
	    (index <= i - k)
	    - Remove indices from the back while their values are smaller than or equal to nums[i]
	    - Add current index i to the deque
	    - Once the first full window is formed (i >= k - 1), answer is nums[dq.peekFirst()]
	*/

	// Time O(n), Space O(k)
	public static int[] maxSlidingWindow(int[] nums, int k) {
	    if(nums == null || nums.length == 0) return new int[0];
	    
	    int n = nums.length;
	
	    // Edge case: if window size is 1, every element is an answer
	    if(k == 1) {
	        return Arrays.copyOf(nums, n);
	    }
	
	    int[] result = new int[n-k+1];
	
	    // We are maintaining a monotonic Deque (maintaining element in sorted 
	    // order (in this problem strcitly decreasing order)//
	    // Front of this dq will always have he max of the current window
	    Deque<Integer> dq = new ArrayDeque<>();
	    int resIndx = 0;
	
	    for(int i=0; i<n; i++) {
	        // 1. Remove the indices that are out of current soling window
	        if(!dq.isEmpty() && dq.peekFirst() < i-k+1) {
	            dq.pollFirst();
	        }
	        
	        // 2. maintain dereasing order, 
	        // remove smaller element from the back
	        
	        // Why remove from the back?
	        // When a new element nums[i] comes: if it is bigger 
	        // than the last element in deque, that smaller element 
	        // is useless because: it can never become maximum while this 
	        // bigger element is still in the window, if it is equal, 
	        // we also remove it because the new one stays in the window 
	        //longer, so the old equal value is also useless
	        while (!dq.isEmpty() && nums[dq.peekLast()] < nums[i] ) {
	            dq.pollLast();
	        }
	        
	        // 3) Add current element index to the back
	        dq.offerLast(i);
	
	        // 4) Start recording answers once the first full window is formed
	        // Front of the queue is always the max element of the window
	        if(i >= k-1) {
	            result[resIndx++] = nums[dq.peekFirst()];
	        }
	    }
	    return result;
	}
	
	
	
	// ---------------- Local testing helpers ----------------
	
	private static void runTest(int[] nums, int k, int[] expected) {
        int[] actual = maxSlidingWindow(nums, k);
        System.out.println("nums     = " + Arrays.toString(nums));
        System.out.println("k        = " + k);
        System.out.println("expected = " + Arrays.toString(expected));
        System.out.println("actual   = " + Arrays.toString(actual));
        System.out.println(Arrays.equals(actual, expected) ? "PASS" : "FAIL");
        System.out.println("-------------------------------------------");
    }

    public static void main(String[] args) {
        // Example test - nums, k, expected
        runTest(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3,
                new int[]{3, 3, 5, 5, 6, 7});

        // Single element
        runTest(new int[]{1}, 1,
                new int[]{1});

        // k = 1
        runTest(new int[]{4, 2, 12, 3}, 1,
                new int[]{4, 2, 12, 3});

        // k = n
        runTest(new int[]{2, 1, 5, 3, 4}, 5,
                new int[]{5});

        // All same elements
        runTest(new int[]{2, 2, 2, 2}, 2,
                new int[]{2, 2, 2});

        // Strictly increasing
        runTest(new int[]{1, 2, 3, 4, 5}, 3,
                new int[]{3, 4, 5});

        // Strictly decreasing
        runTest(new int[]{9, 8, 7, 6, 5}, 2,
                new int[]{9, 8, 7, 6});

        // Negative values
        runTest(new int[]{-4, -2, -5, -1, -7}, 2,
                new int[]{-2, -2, -1, -1});

        // Duplicates and mixed values
        runTest(new int[]{7, 2, 4, 4, 6, 5}, 3,
                new int[]{7, 4, 6, 6});
    }

}
