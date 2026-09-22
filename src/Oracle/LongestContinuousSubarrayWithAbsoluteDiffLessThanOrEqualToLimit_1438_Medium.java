package Oracle;

import java.util.*;

public class LongestContinuousSubarrayWithAbsoluteDiffLessThanOrEqualToLimit_1438_Medium {

	public static void main(String[] args) {
        int[] nums1 = {8, 2, 4, 7};
        int limit1 = 4;
        int expected1 = 2;
        int actual1 = longestSubarray(nums1, limit1);
        System.out.println("Test 1: nums=" + Arrays.toString(nums1) + ", limit=" + limit1 +
                ", expected=" + expected1 + ", actual=" + actual1 +
                (actual1 == expected1 ? " ✅" : " ❌"));

        int[] nums2 = {10, 1, 2, 4, 7, 2};
        int limit2 = 5;
        int expected2 = 4;
        int actual2 = longestSubarray(nums2, limit2);
        System.out.println("Test 2: nums=" + Arrays.toString(nums2) + ", limit=" + limit2 +
                ", expected=" + expected2 + ", actual=" + actual2 +
                (actual2 == expected2 ? " ✅" : " ❌"));

        int[] nums3 = {4, 2, 2, 2, 4, 4, 2, 2};
        int limit3 = 0;
        int expected3 = 3;
        int actual3 = longestSubarray(nums3, limit3);
        System.out.println("Test 3: nums=" + Arrays.toString(nums3) + ", limit=" + limit3 +
                ", expected=" + expected3 + ", actual=" + actual3 +
                (actual3 == expected3 ? " ✅" : " ❌"));

        int[] nums4 = {5};
        int limit4 = 10;
        int expected4 = 1;
        int actual4 = longestSubarray(nums4, limit4);
        System.out.println("Test 4: nums=" + Arrays.toString(nums4) + ", limit=" + limit4 +
                ", expected=" + expected4 + ", actual=" + actual4 +
                (actual4 == expected4 ? " ✅" : " ❌"));

        int[] nums5 = {3, 3, 3, 3};
        int limit5 = 0;
        int expected5 = 4;
        int actual5 = longestSubarray(nums5, limit5);
        System.out.println("Test 5: nums=" + Arrays.toString(nums5) + ", limit=" + limit5 +
                ", expected=" + expected5 + ", actual=" + actual5 +
                (actual5 == expected5 ? " ✅" : " ❌"));

        int[] nums6 = {1, 10};
        int limit6 = 3;
        int expected6 = 1;
        int actual6 = longestSubarray(nums6, limit6);
        System.out.println("Test 6: nums=" + Arrays.toString(nums6) + ", limit=" + limit6 +
                ", expected=" + expected6 + ", actual=" + actual6 +
                (actual6 == expected6 ? " ✅" : " ❌"));

        int[] nums7 = {1, 2, 3, 4, 5};
        int limit7 = 10;
        int expected7 = 5;
        int actual7 = longestSubarray(nums7, limit7);
        System.out.println("Test 7: nums=" + Arrays.toString(nums7) + ", limit=" + limit7 +
                ", expected=" + expected7 + ", actual=" + actual7 +
                (actual7 == expected7 ? " ✅" : " ❌"));

        int[] nums8 = {};
        int limit8 = 5;
        int expected8 = 0;
        int actual8 = longestSubarray(nums8, limit8);
        System.out.println("Test 8: nums=" + Arrays.toString(nums8) + ", limit=" + limit8 +
                ", expected=" + expected8 + ", actual=" + actual8 +
                (actual8 == expected8 ? " ✅" : " ❌"));
    }
	
	/* Thought Process

	    First thought:
	    - Use a sliding window.
	    - But for every window, we need the current minimum and maximum values.
	    - Scanning the entire window each time would make the solution O(n^2).
	
	    Optimization:
	    - Use TreeMap to maintain min and max values in O(log n).
	    - This gives O(n log n) complexity.
	
	    Better approach:
	    - Use two monotonic deques to maintain minimum and maximum.
	    - Max deque stores values in decreasing order.
	            -> Front always contains the current maximum.
	
	    - Min deque stores values in increasing order.
	            -> Front always contains the current minimum.
	
	    - Each element is added and removed from the deques at most once.
	    - This gives an optimal O(n) solution.
	*/
	
	/* Interview Explanation Before Coding
	
	    I will use a sliding window with two monotonic deques.
	
	    1. maxDeque:
	        - Maintains indices in decreasing order of values.
	        - The front always represents the maximum value in the current window.
	
	    2. minDeque:
	        - Maintains indices in increasing order of values.
	        - The front always represents the minimum value in the current window.
	
	    As I expand the window using the right pointer:
	    - Add the new element to both deques.
	    - Remove elements from the back that violate the monotonic property.
	
	    If:
	            maxDeque.front - minDeque.front > limit
	
	    the window is invalid:
	    - Move the left pointer forward.
	    - Remove the outgoing index from the front of either deque if it matches.
	
	    Since every index is inserted and removed at most once from each deque,
	    the overall complexity is O(n).
	*/
	
	/* Complexity Analysis
	
	    Let:
	    n = size of input array
	
	    Time Complexity: O(n)
	
	    - Each element is added to maxDeque once and removed once.
	    - Each element is added to minDeque once and removed once.
	    - All deque operations are O(1) amortized.
	
	    Space Complexity: O(n)
	
	    - In the worst case, both deques together can store up to n indices.
	*/
	
	/* Step-by-Step Algorithm
	
	    1. Create two monotonic deques:
	
	        maxDeque:
	        - Stores indices.
	        - Values are maintained in decreasing order.
	
	        minDeque:
	        - Stores indices.
	        - Values are maintained in increasing order.
	
	
	    2. Initialize:
	
	        left = 0
	        answer = 0
	
	
	    3. Iterate right pointer from 0 to n - 1.
	
	
	    4. Add current index into maxDeque:
	
	        - Remove indices from the back while their values
	            are smaller than the current value.
	
	        - Add current index.
	
	
	    5. Add current index into minDeque:
	
	        - Remove indices from the back while their values
	            are larger than the current value.
	
	        - Add current index.
	
	
	    6. While the current window is invalid:
	
	        maxDeque.front value - minDeque.front value > limit
	
	        - If left index is at the front of maxDeque:
	                remove it.
	
	        - If left index is at the front of minDeque:
	                remove it.
	
	        - Increment left pointer.
	
	
	    7. Update maximum window length:
	
	        answer = Math.max(answer, right - left + 1);
	
	
	    8. Return answer.
	*/
	
	public static int longestSubarray(int[] nums, int limit) {
	    
	    Deque<Integer> maxDQ = new LinkedList<>();
	    Deque<Integer> minDQ = new LinkedList<>();
	
	    int left = 0; // left pointer
	    int maxLength = 0;
	
	    for(int right = 0; right < nums.length; right++) {
	
	        // Maintain increasing deque (minimum)
	        while (!minDQ.isEmpty() && minDQ.peekLast() > nums[right]) {
	            minDQ.pollLast();
	        }
	        minDQ.offerLast(nums[right]);
	
	        // Maintain decreasing deque (maximum)
	        while (!maxDQ.isEmpty() && maxDQ.peekLast() < nums[right]) {
	            maxDQ.pollLast();
	        }
	        maxDQ.offerLast(nums[right]);
	
	        // Check if the current window exceeds the limit
	        // Shrink window until valid
	        while(maxDQ.peekFirst() - minDQ.peekFirst() > limit) {
	            // Remove the elements that are out of the current window
	            if(maxDQ.peekFirst() == nums[left]) {
	                maxDQ.pollFirst();
	            }
	            if(minDQ.peekFirst() == nums[left]) {
	                minDQ.pollFirst();
	            }
	            left++;
	        }
	        
	        /* If a subarray starts at index left and ends at index right, 
            its length is: right - left + 1
            The +1 is needed because both left and right are part of the window.
            */
	        maxLength = Math.max(maxLength, right - left + 1);
	    }
	
	    return maxLength;
	}
	
}
