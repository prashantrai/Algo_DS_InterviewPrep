package Google;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PartitionByUniqueFrequencies {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

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