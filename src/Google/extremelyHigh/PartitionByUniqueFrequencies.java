package Google.extremelyHigh;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PartitionByUniqueFrequencies {

	public static void main(String[] args) {
		
		// These tests are for follow-up question/part only. Main problem is very simple so, no test.

	    // ------------------------------------------------------------
	    // Test 1: Normal case - multiple valid groups
	    // Expected groups:
	    // [1,2,3]
	    // [2,3,4]
	    // [6,7,8]
	    // Expected: true
	    // ------------------------------------------------------------
	    int[] arr1 = {1, 2, 3, 6, 2, 3, 4, 7, 8};

	    System.out.println("Test 1");
	    System.out.println("Expected: true");
	    System.out.println("Actual  : "
	            + uniqueOccurrences_PartitionIntoConsecutiveGroups(arr1, 3));
	    System.out.println();


	    // ------------------------------------------------------------
	    // Test 2: Important trace case - input is NOT sorted
	    //
	    // Frequencies:
	    // 1 -> 1
	    // 2 -> 2
	    // 3 -> 2
	    // 4 -> 1
	    //
	    // First n = 3:
	    //
	    // start = 3
	    // 2 exists -> start = 2
	    // 1 exists -> start = 1
	    //
	    // Build:
	    // [1,2,3]
	    //
	    // Remaining:
	    // 1 -> 0
	    // 2 -> 1
	    // 3 -> 1
	    // 4 -> 1
	    //
	    // start becomes 2
	    // Build:
	    // [2,3,4]
	    //
	    // Expected: true
	    //
	    // This is probably the BEST test to manually dry-run
	    // during the interview.
	    // ------------------------------------------------------------
	    int[] arr2 = {3, 2, 1, 2, 3, 4};

	    System.out.println("Test 2 - Best Dry Run");
	    System.out.println("Expected: true");
	    System.out.println("Actual  : "
	            + uniqueOccurrences_PartitionIntoConsecutiveGroups(arr2, 3));
	    System.out.println();


	    // ------------------------------------------------------------
	    // Test 3: Missing value inside required consecutive group
	    //
	    // Possible first group:
	    // [1,2,3]
	    //
	    // Remaining:
	    // [2,4,4]
	    //
	    // Starting at 2 would require:
	    // [2,3,4]
	    //
	    // But no 3 remains.
	    //
	    // Expected: false
	    // ------------------------------------------------------------
	    int[] arr3 = {1, 2, 2, 3, 4, 4};

	    System.out.println("Test 3");
	    System.out.println("Expected: false");
	    System.out.println("Actual  : "
	            + uniqueOccurrences_PartitionIntoConsecutiveGroups(arr3, 3));
	    System.out.println();


	    // ------------------------------------------------------------
	    // Test 4: Length not divisible by groupSize
	    //
	    // 4 elements cannot be divided into groups of 3.
	    //
	    // Expected: false
	    // ------------------------------------------------------------
	    int[] arr4 = {1, 2, 3, 4};

	    System.out.println("Test 4");
	    System.out.println("Expected: false");
	    System.out.println("Actual  : "
	            + uniqueOccurrences_PartitionIntoConsecutiveGroups(arr4, 3));
	    System.out.println();


	    // ------------------------------------------------------------
	    // Test 5: Duplicate values require repeatedly creating
	    // groups from the same starting value.
	    //
	    // Frequencies:
	    // 1 -> 2
	    // 2 -> 2
	    // 3 -> 2
	    //
	    // Groups:
	    // [1,2,3]
	    // [1,2,3]
	    //
	    // This specifically exercises:
	    //
	    // while (freqMap.getOrDefault(start, 0) > 0)
	    //
	    // Expected: true
	    // ------------------------------------------------------------
	    int[] arr5 = {1, 1, 2, 2, 3, 3};

	    System.out.println("Test 5");
	    System.out.println("Expected: true");
	    System.out.println("Actual  : "
	            + uniqueOccurrences_PartitionIntoConsecutiveGroups(arr5, 3));
	    System.out.println();


	    // ------------------------------------------------------------
	    // Test 6: Single group
	    //
	    // Group:
	    // [5,6,7]
	    //
	    // Expected: true
	    // ------------------------------------------------------------
	    int[] arr6 = {7, 5, 6};

	    System.out.println("Test 6");
	    System.out.println("Expected: true");
	    System.out.println("Actual  : "
	            + uniqueOccurrences_PartitionIntoConsecutiveGroups(arr6, 3));
	    System.out.println();


	    // ------------------------------------------------------------
	    // Test 7: groupSize = 1
	    //
	    // Every individual element forms its own valid group.
	    //
	    // Expected: true
	    // ------------------------------------------------------------
	    int[] arr7 = {5, 2, 9, 2};

	    System.out.println("Test 7");
	    System.out.println("Expected: true");
	    System.out.println("Actual  : "
	            + uniqueOccurrences_PartitionIntoConsecutiveGroups(arr7, 1));
	}

	/* Interview script
	"Since all copies of a value have to stay together, each partition's 
	size is exactly that value's frequency. So I first build a frequency map. 
	Then I iterate over those frequencies and put them into a HashSet. 
	If a frequency is already in the set, two partitions would have the 
	same size, so I return false. Otherwise, all frequencies are unique."
	*/
	// Similar to LC 1207
	// Time and Space: O(n)
	public static boolean uniqueOccurrences(int[] arr) {
        
        Set<Integer> seen = new HashSet<>();
        Map<Integer, Integer> frqMap = new HashMap<>();
        for(int n : arr) {
            frqMap.put(n, frqMap.getOrDefault(n, 0)+1);
        }
        for(int n : frqMap.values()) {
            if (seen.contains(n)) return false;
            seen.add(n);
        }
        return true;
    }
	
	
	/** Follow-up: Partition into Consecutive Groups */
	
	/* Now change the partitioning rule.
	
	You are given an integer array `nums` and an integer `groupSize`.
	
	Partition all elements of `nums` into groups such that:
	
	1. Every group contains exactly `groupSize` elements.
	2. The values inside each group must be consecutive integers.
	3. Each required occurrence must come from an actual occurrence in `nums`.
	4. Every element in `nums` must belong to exactly one group.
	
	Return `true` if such a partition is possible; otherwise, return `false`.
	
	The order of elements in the original array does not matter.
	Example 1: 
	Input: nums = [1,2,3,6,2,3,4,7,8]
	groupSize = 3
	
	Possible partition:
	[1,2,3]
	[2,3,4]
	[6,7,8]
	
	Output: true
	
	Every group contains exactly `3` consecutive integers.
	 * */
	
	
	// Time: O(n), Space: O(n)
	public static boolean uniqueOccurrences_PartitionIntoConsecutiveGroups(int[] arr, int groupSize) {
		if(arr.length % groupSize != 0) return false;
		
		Map<Integer, Integer> frqMap = new HashMap<>();
		for(int n : arr) {
			frqMap.put(n, frqMap.getOrDefault(n, 0)+1);
		}
		
		for(int n : arr) {
			// as the array is not sorted, we have to find the start
            // position of the group while decrementing the value of startCard
            // Find the start of the potential sequence
			int start = n;
			while(frqMap.getOrDefault(start-1, 0) > 0) start--;
			
			while(start <= n) {
				while(frqMap.getOrDefault(start, 0) > 0) {
					for(int i=start; i<start+groupSize; i++) {
						
						if(frqMap.getOrDefault(i, 0) == 0) return false;
						
						frqMap.put(i, frqMap.get(i)-1);
					}
				}
				start++;
			}
		}
		return true;
	}
	
	// Time: O(nlogn), Space: O(n)
	public static boolean uniqueOccurrences_PartitionIntoConsecutiveGroups2(int[] arr, int groupSize) {
		
		if(arr.length % groupSize != 0) return false;
		
		Map<Integer, Integer> frqMap = new HashMap<>();
		for(int n : arr) {
			frqMap.put(n, frqMap.getOrDefault(n, 0)+1);
		}
		
		Arrays.sort(arr);
		for(int n : arr) {
			if(frqMap.get(n) > 0) {
				for(int i=n; i<n+groupSize; i++) {
					if(frqMap.getOrDefault(i, 0) == 0) return false;
					frqMap.put(i, frqMap.get(i) - 1);
				}
			}
		}
		
		return true;
	}
	
	
}


/* # Partition by Unique Frequencies

	### Priority: EXTREMELY HIGH
	
	## Problem
	
	You are given an integer array `nums`.
	
	You must partition the array into groups such that:
	
	1. All occurrences of the same integer must belong to the same group.
	2. A group cannot contain occurrences of two different integer values.
	3. Every group must have a **unique size**. In other words, no two groups may 
		contain the same number of elements.
	
	Return `true` if such a partition is possible; otherwise, return `false`.
	
	Because every occurrence of a value must stay together and each group contains only 
	one distinct value, the size of each group is simply the frequency of that value in `nums`.
	
	### Example 1
	
	```text
	Input:
	nums = [1, 2,2, 3,3,3, 4,4,4,4]
	
	Frequencies:
	1 -> 1
	2 -> 2
	3 -> 3
	4 -> 4
	
	Group sizes:
	[1, 2, 3, 4]
	
	Output:
	true
	```
	
	All values occur a different number of times.
	
	---
	
	### Example 2
	
	```text
	Input:
	nums = [1,2,3]
	
	Frequencies:
	1 -> 1
	2 -> 1
	3 -> 1
	
	Group sizes:
	[1, 1, 1]
	
	Output:
	false
	```
	
	Multiple groups would have size `1`.
	
	---
	
	### Example 3
	
	```text
	Input:
	nums = [5,5, 7,7,7, 9]
	
	Frequencies:
	5 -> 2
	7 -> 3
	9 -> 1
	
	Group sizes:
	[2, 3, 1]
	
	Output:
	true
	```
	
	All frequencies are distinct.
	
	---
	
	### Example 4
	
	```text
	Input:
	nums = [1,1, 2,2, 3,3,3]
	
	Frequencies:
	1 -> 2
	2 -> 2
	3 -> 3
	
	Group sizes:
	[2, 2, 3]
	
	Output:
	false
	```
	
	Values `1` and `2` both occur twice.
	
	---
	
	### Example 5
	
	```text
	Input:
	nums = [8,8,8,8]
	
	Frequencies:
	8 -> 4
	
	Output:
	true
	```
	
	There is only one group, so its size is trivially unique.
	
	---
	
	## Constraints
	
	```text
	1 <= nums.length <= 100000
	-10^9 <= nums[i] <= 10^9
	```
	
	## Expected Complexity
	
	A solution should ideally run in:
	
	```text
	Time:  O(n)
	Space: O(k)
	```
	
	where `k` is the number of distinct values.
	
	## Related LeetCode
	
	Closest match:
	
	**LC 1207 — Unique Number of Occurrences**
	
	The core observation is identical: determine whether every distinct value has a unique frequency.
	
	
	
	# Follow-up: Partition into Consecutive Groups
	
	Now change the partitioning rule.
	
	You are given an integer array `nums` and an integer `groupSize`.
	
	Partition all elements of `nums` into groups such that:
	
	1. Every group contains exactly `groupSize` elements.
	2. The values inside each group must be consecutive integers.
	3. Each required occurrence must come from an actual occurrence in `nums`.
	4. Every element in `nums` must belong to exactly one group.
	
	Return `true` if such a partition is possible; otherwise, return `false`.
	
	The order of elements in the original array does not matter.
	
	---
	
	### Example 1
	
	```text
	Input:
	nums = [1,2,3,6,2,3,4,7,8]
	groupSize = 3
	
	Possible partition:
	
	[1,2,3]
	[2,3,4]
	[6,7,8]
	
	Output:
	true
	```
	
	Every group contains exactly `3` consecutive integers.
	
	---
	
	### Example 2
	
	```text
	Input:
	nums = [1,2,3,4]
	groupSize = 3
	
	Output:
	false
	```
	
	There are `4` elements, which cannot be divided into groups of size `3`.
	
	---
	
	### Example 3
	
	```text
	Input:
	nums = [1,2,3,4,5,6]
	groupSize = 3
	
	Possible partition:
	
	[1,2,3]
	[4,5,6]
	
	Output:
	true
	```
	
	---
	
	### Example 4
	
	```text
	Input:
	nums = [1,2,2,3,3,4]
	groupSize = 3
	
	Possible partition:
	
	[1,2,3]
	[2,3,4]
	
	Output:
	true
	```
	
	Duplicate values are allowed as long as there are enough copies to construct all required groups.
	
	---
	
	### Example 5
	
	```text
	Input:
	nums = [1,2,2,3,4,4]
	groupSize = 3
	
	Output:
	false
	```
	
	One possible first group is:
	
	[1,2,3]
	
	Remaining:
	
	[2,4,4]
	
	These cannot form three consecutive integers.
	
	---
	
	### Example 6
	
	```text
	Input:
	nums = [5,6,7,7,8,9]
	groupSize = 3
	
	Possible partition:
	
	[5,6,7]
	[7,8,9]
	
	Output:
	true
	```
	
	---
	
	### Example 7
	
	```text
	Input:
	nums = [1,2,3,3,4,5,5,6,7]
	groupSize = 3
	
	Possible partition:
	
	[1,2,3]
	[3,4,5]
	[5,6,7]
	
	Output:
	true
	```
	
	---
	
	## Constraints
	
	```text
	1 <= nums.length <= 100000
	1 <= groupSize <= nums.length
	-10^9 <= nums[i] <= 10^9
	```
	
	## Expected Complexity
	
	A good solution should typically run in approximately:
	
	```text
	O(n log n)
	```
	
	using sorting or an ordered frequency map.
	
	## Related LeetCode
	
	Closest matches:
	
	**LC 846 — Hand of Straights**
	
	**LC 1296 — Divide Array in Sets of K Consecutive Numbers**
	
	Both problems require partitioning the entire multiset into equal-size groups of consecutive integers.

 
 */