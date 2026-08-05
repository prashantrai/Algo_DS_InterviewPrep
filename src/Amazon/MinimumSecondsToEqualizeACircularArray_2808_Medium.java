package Amazon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinimumSecondsToEqualizeACircularArray_2808_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	/*
		Algo:
	1. Scan the array once. For every value, track:
		- first index it appeared at,
		- prev (last seen) index,
		- maxGap seen so far between consecutive occurrences.
	2. After scanning, close the circle: compute the wrap-around 
		gap n - prev + first for each value and update maxGap.
	3. For each value, candidate answer = maxGap / 2.
	4. Return the minimum candidate answer across all values.
	 */
	
	/* Example Test:
	 	// Example 1
		nums = [1,2,1,2]        -> expected 1
		
		// Example 2
		nums = [2,1,3,3,2]      -> expected 2
		
		// Example 3 (all same)
		nums = [5,5,5,5]        -> expected 0
		
		// Edge case: single element
		nums = [7]              -> expected 0
		
		// Edge case: single occurrence of the best value, rest scattered
		nums = [1,2,3,4,5]      -> expected 2  
		// (every value distinct, n=5, maxGap = n = 5 for each, 5/2 = 2)
		
		// Edge case: two values alternating, odd length
		nums = [1,2,1,2,1]      -> expected 1
		
		// Large gap edge: one value appears once, far apart cluster
		nums = [9,1,1,1,9]      -> check gaps for value 9: indices [0,4], 
		                           gap=4-0=4, wrap = 5-4+0=1, maxGap=4 -> 4/2=2
		                           value 1: indices [1,2,3], gaps=1,1, wrap=5-3+1=3, maxGap=3 -> 3/2=1
		                           answer = 1
	 
	 * */
	
	// Time and Space Complexity: O(n)
    public static int minimumSeconds(List<Integer> nums) {
        int n = nums.size();

        // For each value store: [firstIndex, lastSeenIndex, maxGapSoFar]
        Map<Integer, int[]> info = new HashMap<>();

        for(int i=0; i<n; i++) {
            int val = nums.get(i);
            if(!info.containsKey(val)) {
                info.put(val, new int[]{i, i, 0});
            } else {
                int[] data = info.get(val);
                // distance from previous occurrence, i - lastSeenIndex
                int gap = i - data[1];  

                // update max gap
                data[2] = Math.max(data[2], gap);
                // update last seen index
                data[1] = i;
            }
        }

        int ans = Integer.MAX_VALUE;

        // Close the circle for every value and compute the candidate answer
        for(int[] data : info.values()) {
            int first = data[0];
            int last = data[1];
            int maxGap = data[2];

            // wrap-around gap: from last occurrence back to first occurrence
            int wrapGap = n - last + first;
            maxGap = Math.max(maxGap, wrapGap);

            // time needed using this value = maxGap / 2 (integer division)
            ans = Math.min(ans, maxGap/2);
        }

        return ans;

    }
	
	
	// Revisit this solution, to understand better.
	// Source: https://algo.monster/liteproblems/2808
    // Time and Space Complexity: O(n)
    public int minimumSeconds2(List<Integer> nums) {
        int arraySize = nums.size();

        Map<Integer, List<Integer>> valueToIndicesMap = new HashMap<>();

        int minSeconds = Integer.MAX_VALUE;

        // Build the map: for each value, collect all indices where it appears
        for(int i=0; i<arraySize; i++) {
            valueToIndicesMap.computeIfAbsent(nums.get(i), 
                                                    k -> new ArrayList<>()).add(i);
        }

        // For each unique value in the array, calculate the minimum seconds needed
        for(List<Integer> indices : valueToIndicesMap.values()) {
            int indicesCount = indices.size();

            // Calculate the circular distance from the last index to the first index
            // This handles the wrap-around case in the circular array
            int maxGap = indices.get(0) + arraySize - indices.get(indicesCount - 1);

            // Find the maximum gap between consecutive indices of the same value
            for(int i=1; i<indicesCount; i++) {
                maxGap = Math.max(maxGap, indices.get(i) - indices.get(i-1));
            }

            // The minimum seconds needed is half of the maximum gap (rounded down)
            // This is because elements spread from both directions simultaneously
            minSeconds = Math.min(minSeconds, maxGap / 2);
        }

        return minSeconds;
    }
}
