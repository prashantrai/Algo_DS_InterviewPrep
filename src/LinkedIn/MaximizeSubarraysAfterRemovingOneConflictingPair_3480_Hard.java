package LinkedIn;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MaximizeSubarraysAfterRemovingOneConflictingPair_3480_Hard {

	// Test cases
    public static void main(String[] args) {

        int[][] cp1 = {{2, 3}, {1, 4}};
        System.out.println(maxSubarrays(4, cp1)); // Output: 9

        int[][] cp2 = {{1, 2}, {2, 5}, {3, 5}};
        System.out.println(maxSubarrays(5, cp2)); // Output: 12
    }
	
	
	/* Getting TLE
	 * 
	 Time: O(k × n)
		k = number of conflicting pairs (up to 10⁴)
		For each removal, we run a sliding window over n elements

	 Space: O(n + k)
		For conflict map and frequency map
	 * */
	public static long maxSubarrays(int n, int[][] conflictingPairs) {
        int maxValidSubArr = 0;

        // Try removing each conflicting pair one by one
        for(int i=0; i<conflictingPairs.length; i++) {

            // Step 1: Build conflict map after removing pair i
            Map<Integer, Set<Integer>> conflictMap = new HashMap<>();

            for(int j=0; j<conflictingPairs.length; j++) {
                if (i == j) continue; // skip the pair we're "removing"
                int a = conflictingPairs[j][0];
                int b = conflictingPairs[j][1];
                
                // when map contains add the value to set, 
                // if doesn't add key and new Set and then add the value
                conflictMap.computeIfAbsent(a, k -> new HashSet<>()).add(b); 
                conflictMap.computeIfAbsent(b, k -> new HashSet<>()).add(a); 
            }

            // Step 2: Use sliding window to count valid subarrays
            int left = 0;
            int validCount = 0;

            Map<Integer, Integer> freqMap = new HashMap<>();

            for(int right = 1; right<=n; right++) {
                // Add current number to window
                freqMap.put(right, freqMap.getOrDefault(right, 0) + 1);

                // Check and move left if any conflict exists
                while(hasConflict(freqMap, conflictMap)) {
                    int remove = left + 1;
                    freqMap.put(remove, freqMap.get(remove) - 1);
                    if(freqMap.get(remove) == 0) {
                        freqMap.remove(remove);
                    }
                    left++;
                }
                // All subarrays ending at 'right' and starting 
                // from [left..right]
                validCount += (right - left);
            }
            maxValidSubArr = Math.max(maxValidSubArr, validCount);
        }
        return maxValidSubArr;
    }

    // Helper to check if there's any conflicting pair in current window
    private static boolean hasConflict(Map<Integer, Integer> freqMap, Map<Integer, Set<Integer>> conflictMap) {
        for (int num : freqMap.keySet()) {
            if (!conflictMap.containsKey(num)) 
            	continue;
            
            for (int conflict : conflictMap.get(num)) {
                if (freqMap.containsKey(conflict)) return true;
            }
        }
        return false;
    }

}
