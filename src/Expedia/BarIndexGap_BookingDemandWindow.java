package Expedia;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class BarIndexGap_BookingDemandWindow {

	public static void main(String[] args) {
        // Given examples
        runTest(new int[]{6, 0, 8, 2, 1, 5}, 4);
        runTest(new int[]{9, 8, 1, 0, 1, 9, 4, 0, 4, 1}, 7);

        // Edge cases
        runTest(new int[]{1, 1}, 1);          // smallest valid equal pair
        runTest(new int[]{2, 1}, 0);          // no ramp
        runTest(new int[]{1, 2}, 1);          // direct ramp

        // All equal
        runTest(new int[]{5, 5, 5, 5}, 3);

        // Strictly increasing
        runTest(new int[]{1, 2, 3, 4, 5}, 4);

        // Strictly decreasing
        runTest(new int[]{5, 4, 3, 2, 1}, 0);

        // Duplicates and mixed values
        runTest(new int[]{3, 3, 3, 1, 2, 2, 4}, 6);
        runTest(new int[]{4, 1, 2, 3, 1, 5}, 5);

        // More complex
        runTest(new int[]{8, 1, 0, 2, 1, 5, 7, 3, 6}, 7);
        runTest(new int[]{9, 7, 5, 3, 2, 8, 10, 1, 9}, 8);
    }
    // Simple test helper
    private static void runTest(int[] nums, int expected) {
        int actual = barIndexGap_BookingDemandWindow(nums);
        System.out.println("nums = " + Arrays.toString(nums));
        System.out.println("Expected = " + expected + ", Actual = " + actual
                + (expected == actual ? "  ✅" : "  ❌"));
        System.out.println();
    }
	
	/* Bar Index Gap Problem (LeetCode 962: Max Ramp Width) : Expedia version: "Booking Demand Window"

		Problem Statement:
		You are given an array 'heights' representing the heights of vertical bars over a timeline. 
		Find the maximum possible index gap (j - i) between two bars such that:
		1. i < j
		2. heights[i] ≤ heights[j]
	
		If no such pair exists satisfying the condition, return 0.
	
		Expedia Interview Context:
		This is frequently framed as a "Booking Demand Window" problem, where you must calculate the 
		longest sequence of days between a baseline day 'i' and a future day 'j' where ticket/hotel 
		demand has not dropped below the baseline.
	
		Constraints:
		- 2 ≤ heights.length ≤ 10^5 (Requires an O(N) or O(N log N) solution; O(N²) will fail)
		- 0 ≤ heights[i] ≤ 10^9
	
		Example 1:
		Input: heights = [3, 5, 4, 2]
		Output: 2
		Explanation: The maximum gap is achieved by choosing i = 0 (height 3) and j = 2 (height 4). 
		Since 3 ≤ 4, the gap is j - i = 2 - 0 = 2.
	
		Example 2:
		Input: heights = [1, 2, 3, 4]
		Output: 3
		Explanation: The maximum gap is between the first and last element: i = 0, j = 3. Since 1 ≤ 4, the gap is 3 - 0 = 3.
	
		Example 3:
		Input: heights = [5, 4, 3, 2, 1]
		Output: 0
		Explanation: The array is strictly decreasing. No pair satisfies heights[i] ≤ heights[j] where i < j. Thus, the maximum gap is 0.

	 * */

    /* Time and space complexity
    	Time: O(n), Each index is pushed at most once and popped at most once
    	Space: O(n), For the stack
    */
	
	private static int barIndexGap_BookingDemandWindow(int[] nums) {
		int n = nums.length-1;
		Deque<Integer> stk = new ArrayDeque<>();
		
		int maxWidth = 0;
		
		// Step 1: Build a decreasing stack of candidate left indices.
	    // We keep an index only if its value is smaller than all previous values. 
		for(int i=0; i<n; i++) {
			if(stk.isEmpty() || nums[i] < nums[stk.peek()]) {
				stk.push(i);
			}
		}
		
		// Step 2: Scan from right to left.
	    // If nums[left] <= nums[right], we found a valid ramp.
	    // Since right is moving from the far end, this is the best width
	    // for that left index, so we can pop it.  
		for(int i=n; i>=0; i--) {
			while(!stk.isEmpty() && nums[i] >= nums[stk.peek()]) {
				maxWidth = Math.max(maxWidth, i - stk.pop());
			}
		}
		
		
		return maxWidth;
	}
	
	

}

/*
Expedia Interview Context:
	This is frequently framed as a "Booking Demand Window" problem, where you must 
	calculate the longest sequence of days between a baseline day 'i' and a future 
	day 'j' where ticket/hotel demand has not dropped below the baseline.

*/

/*
 * Bar Index Gap Problem
	
	Problem Statement: 
	
	You are given an array heights representing the heights of vertical bars.
	Find the maximum value of j - i such that:
	i < j
	heights[i] <= heights[j]
	
	Return the maximum possible index gap.
	
	Example 1
	Input: [3,5,4,2]
	Output: 2
	
	Explanation: 
		Choose
		i = 0
		j = 2
	
		3 <= 4
		
		Gap = 2
	
	Example 2
	Input: [1,2,3,4]
	Output: 3
	Explanation: 
		Choose first and last bar.
	
	Example 3
	Input: [5,4,3,2,1]
	Output: 0
	Explanation
		No pair satisfies the condition, so only a gap of 0 (same index) is possible.
	
	Pattern: Prefix minimum array + suffix maximum array + two pointers in O(n) time.
*/

/*
 Bar Index Gap Problem (LeetCode 962: Max Ramp Width Variation)

Problem Statement:
You are given an array 'heights' representing the heights of vertical bars over a timeline. 
Find the maximum possible index gap (j - i) between two bars such that:
1. i < j
2. heights[i] ≤ heights[j]

If no such pair exists satisfying the condition, return 0.

Expedia Interview Context:
This is frequently framed as a "Booking Demand Window" problem, where you must calculate the 
longest sequence of days between a baseline day 'i' and a future day 'j' where ticket/hotel 
demand has not dropped below the baseline.

Constraints:
- 2 ≤ heights.length ≤ 10^5 (Requires an O(N) or O(N log N) solution; O(N²) will fail)
- 0 ≤ heights[i] ≤ 10^9

Example 1:
Input: heights = [3, 5, 4, 2]
Output: 2
Explanation: The maximum gap is achieved by choosing i = 0 (height 3) and j = 2 (height 4). 
Since 3 ≤ 4, the gap is j - i = 2 - 0 = 2.

Example 2:
Input: heights = [1, 2, 3, 4]
Output: 3
Explanation: The maximum gap is between the first and last element: i = 0, j = 3. Since 1 ≤ 4, the gap is 3 - 0 = 3.

Example 3:
Input: heights = [5, 4, 3, 2, 1]
Output: 0
Explanation: The array is strictly decreasing. No pair satisfies heights[i] ≤ heights[j] where i < j. Thus, the maximum gap is 0.

 * */
