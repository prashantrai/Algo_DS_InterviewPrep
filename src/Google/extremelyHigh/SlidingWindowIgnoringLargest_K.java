package Google.extremelyHigh;

import java.util.Arrays;
import java.util.TreeMap;

public class SlidingWindowIgnoringLargest_K {

	public static void main(String[] args) {
		
		SlidingWindowIgnoringLargest_K sol = new SlidingWindowIgnoringLargest_K();
		
		run(new SlidingWindowIgnoringLargest_K(), 
				new int[] { 1, 5, 2, 4, 3 }, // nums
				3, // w
				1, // k
				new double[] { 1.5, 3.0, 2.5 }); // expected

		run(new SlidingWindowIgnoringLargest_K(),
				new int[] { 1, 8, 3, 6, 2 }, 4, 2, new double[] { 2.0, 2.5 });

		run(new SlidingWindowIgnoringLargest_K(),
				new int[] { 5, 5, 1, 2 }, 3, 1, new double[] { 3.0, 1.5 });

		run(new SlidingWindowIgnoringLargest_K(),
				new int[] { 2, 4, 6, 8 }, 2, 0, new double[] { 3.0, 5.0, 7.0 });

		run(new SlidingWindowIgnoringLargest_K(),
				new int[] { 7, 2, 9, 4 }, 3, 2, new double[] { 2.0, 2.0 });
	}

	private static void run(SlidingWindowIgnoringLargest_K sol, 
			int[] nums, int w, int k, double[] expected) {
		double[] actual = sol.avgIgnoringLargestK(nums, w, k);
		System.out.println("Expected: " + Arrays.toString(expected));
		System.out.println("Actual:   " + Arrays.toString(actual));
		System.out.println();
	}
	
	/* Why TreeMap?
		We need to repeatedly perform:
			insert value
			remove value
			get smallest value
			get largest value
		
		And duplicates are allowed.
		
		For example: [5, 5, 1]
		A normal: TreeSet<Integer>
		does not work because it would store only one 5.
		
		Instead, use: TreeMap<Integer, Integer>
		where:
			key   = number
			value = frequency
		
		Example: [1, 5, 5]
		becomes:
		1 -> 1
		5 -> 2
		
		This gives us an ordered multiset.
	 * */
	
	/*Why We Cannot Just Use Two Heaps?

		A natural first thought is:
			max heap for kept elements
			min heap for ignored elements
		
		The problem is the sliding window requires arbitrary 
		deletion of the outgoing element.
		
		For example: window = [1, 8, 3, 6]
		When 1 leaves, it may not be at the top of its heap.
		
		Java's PriorityQueue.remove(value) is: O(w) not O(log w).
		
		We could implement lazy deletion with extra maps, but that 
		becomes significantly more complicated.
	*/
	
	
	/* Interview Explanation Before Coding
	 * 
	 * I want to avoid sorting every window independently because that would cost
	 * O(n * w log w). Since consecutive windows differ by only one outgoing and one
	 * incoming element, I'll maintain the current window dynamically.
	 * 
	 * I'll divide the window into two ordered multisets. small contains the
	 * smallest w-k elements and large contains the largest k elements. I'll also
	 * maintain the running sum of small.
	 * 
	 * Then the answer for each full window is simply sumSmall / (w-k).
	 * 
	 * Since duplicates are possible, I'll use TreeMap as a multiset by storing
	 * value to frequency. Every insertion, deletion, and rebalance operation costs
	 * O(log w), so the overall complexity will be O(n log w).
	 */
	
	/* Step-by-Step Algorithm
	 * 
	 * For every number:
	 * 
	 * Step 1 Insert the incoming number into either small or large.
	 * 
	 * Step 2 If the window is now larger than w, remove the outgoing element.
	 * 
	 * The outgoing index is: i - w Step 3
	 * 
	 * Rebalance the two multisets so: smallSize <= w-k
	 * 
	 * and ultimately for a full window:
	 * 	smallSize = w-k largeSize = k 
	 * 
	 * Step 4 Once we have processed at least w elements:
	 * 	average = (double) sumSmall / (w-k)
	 * 
	 * Store it.
	 */
	
	 /* Complexity Analysis Before Coding
	 * 
	 * Let: n = nums.length w = window size
	 * 
	 * For every element, we perform a constant number of:
	 * 
	 * 	TreeMap insertions 
	 * 	TreeMap deletions 
	 * 	TreeMap firstKey() 
	 * 	TreeMap lastKey()
	 * 
	 * Insertion/removal costs: O(log w)
	 * 
	 * Each element enters the window once and leaves once.
	 * 
	 * Therefore:
	 * 	Insertion work: O(n log w) 
	 * 	Removal work: O(n log w) 
	 * 	Rebalancing: O(n log w)
	 * 
	 * More explicitly:
	 * 
	 * 	O(n log w) + O(n log w) + O(n log w) 
	 * 	= O(3n log w) = O(n log w)
	 * 
	 * 
	 * Space: small + large contain at most w elements
	 * 
	 * Therefore: Space = O(w)
	 */
	
	
	// NOTE: This solution already covers follow-up 1, Follow-up 2 (Handle Duplicate Values Correctly0 by utilizing TreeMap.
	
	// Solution starts...	
	/* Mental model stays very simple:
		lo = smallest w-k values
		hi = largest k values
		
		sum = sum(lo)
		
		Then only remember:  add, remove, balance
		and the two balancing rules:
		
		lo too big
		    lo.lastKey() -> hi
		
		lo too small
    	hi.firstKey() -> lo
	 * */
	
	
	
	// Smallest w-k values.
	TreeMap<Integer, Integer> lo = new TreeMap<>();
	
	// Largest k values.
	TreeMap<Integer, Integer> hi = new TreeMap<>();
	
	int loSize = 0;
	int keep;			// Number of values contributing to average.
	int sum = 0;	// Sum of values in lo
	
	public double[] avgIgnoringLargestK(int[] a, int w, int k) {
		keep = w - k;
		
		double[] ans = new double[a.length - w + 1];
		
		for(int i=0; i<a.length; i++) {
			add(a[i]);
			
			// Remove outgoing value.
			if(i >= w) {
				remove(a[i - w]);
			}
			
			// Current window is complete.
			if(i >= w - 1) {
				/* Why is the result index i - w + 1?
				When i is the ending index of the current window, 
				i - w + 1 is the starting index of that window.
				 
				Example: 
				For: w = 3
				the first complete window happens when: i = 2
				Then: i - w + 1 = 2 - 3 + 1 = 0
				so, we write: ans[0]
				
				Next window: i = 3, gives: 3 - 3 + 1 = 1
				so: ans[1]
				 * */
				ans[i - w  + 1] = (double) sum / keep;
			}
		}
		return ans;
	}
	
	private void add(int x) {
		if(lo.isEmpty() || x <= lo.lastKey()) {
			put(lo, x);
			loSize++;
			sum += x;
		}
		else {
			put(hi, x);
		}
		balance();
	}
	
	private void remove(int x) {
		if(lo.containsKey(x)) {
			delete(lo, x);
			loSize--;
			sum -= x;
		}
		else {
			delete(hi, x);
		}
		balance();
	}

	private void balance() {
		// lo has too many values.
		while(loSize > keep) {
			int x = lo.lastKey();	// gives the largest value.
			
			delete(lo, x);
			loSize--;
			sum -= x;
			
			put(hi, x);
		}
		
		// lo has too few values.
		while(lo.size() < keep && !hi.isEmpty()) {
			int x = hi.firstKey(); // gives the smallest value
			delete(hi, x);
			
			put(lo, x);
			loSize++;
			sum += x;
		}
	}
	
	private void delete(TreeMap<Integer, Integer> map, int x) {
		int count = map.get(x);
		
		if(count == 1) {
			map.remove(x);
		} else {
			map.put(x, count - 1);
		}
	}
	
	private void put(TreeMap<Integer, Integer> map, int x) {
		map.put(x, map.getOrDefault(x, 0) + 1);
	}
	
}

/* C9. Sliding Window Average Ignoring Largest K | Priority: EXTREMELY HIGH
	Problem: 
	You are given an integer array nums, a window size w, and an integer k.
	
	For every contiguous subarray of exactly w elements:
	
	Ignore the largest k elements in that window.
	Compute the average of the remaining w - k elements.
	Return the averages for all windows from left to right.
	
	If duplicate values occur among the largest elements, each occurrence is treated independently.
	
	Assume:
	
	1 <= w <= nums.length
	0 <= k < w
	
	Return an array of double.
	
	Example 1
	nums = [1, 5, 2, 4, 3]
	w = 3
	k = 1
	
	Windows:[1, 5, 2]
	
	Largest 1 value: [5]
	
	Remaining: [1, 2]
	Average: (1 + 2) / 2 = 1.5
	
	Windows: [5, 2, 4]
	Largest 1 value: [5]
	
	Remaining: [2, 4]
	Average: (2 + 4) / 2 = 3.0
	
	Windows: [2, 4, 3]
	Largest 1 value: [4]
	Remaining: [2, 3]
	
	Average: (2 + 3) / 2 = 2.5
	
	Output: [1.5, 3.0, 2.5]
	
	
	Example 2 — Remove More Than One Largest Value
	nums = [1, 8, 3, 6, 2]
	w = 4
	k = 2
	
	First window: [1, 8, 3, 6]
	Largest 2: [8, 6]
	Remaining: [1, 3]
	
	Average: 2.0
	
	Second window: [8, 3, 6, 2]
	
	Largest 2: [8, 6]
	
	Remaining: [3, 2]
	
	Average: 2.5
	
	Output: [2.0, 2.5]
	
	
	Example 3 — Duplicate Values
	nums = [5, 5, 1, 2]
	w = 3
	k = 1
	
	First window:
	
	[5, 5, 1]
	
	Only one occurrence of 5 is ignored.
	
	remaining = [5, 1]
	
	average = 3.0
	
	Second window:
	
	[5, 1, 2]
	
	ignore = [5]
	
	remaining = [1, 2]
	
	average = 1.5
	
	Output:
	
	[3.0, 1.5]
	
	This example is important because the data structure must correctly handle duplicate numbers.
	
	
	Example 4: 
	k = 0
	nums = [2, 4, 6, 8]
	w = 2
	k = 0
	
	Nothing is removed.
	
	[2, 4] -> 3.0
	[4, 6] -> 5.0
	[6, 8] -> 7.0
	
	Output:
	
	[3.0, 5.0, 7.0]
	
	
	Example 5 — Keep Only the Smallest Element
	nums = [7, 2, 9, 4]
	w = 3
	k = 2
	
	Since:
	
	w - k = 1
	
	only the smallest value remains.
	
	[7, 2, 9] -> ignore 9,7 -> average = 2.0
	[2, 9, 4] -> ignore 9,4 -> average = 2.0
	
	Output: [2.0, 2.0]
	
	
	Expected Complexity
	
	Target:
		Time:  O(n log w)
		Space: O(w)
	
	Sorting every window independently would cost approximately:
	
	O((n - w + 1) * w log w)
	
	which is not the intended solution.
	
	The key challenge is maintaining the window dynamically as:
	
	remove nums[i - w]
	add    nums[i]
	
	while efficiently tracking both:
	
	largest k elements
	
	and
	
	sum of the remaining w - k elements
	
	Closest LeetCode Problems: 
	
	LC 1825 — Finding MK Average
	
	This is the closest conceptual problem.
	
	It requires maintaining different ordered partitions of a 
	sliding window while supporting insertion, deletion, and running sums.
	
	LC 480 — Sliding Window Median
	
	Also closely related because it requires maintaining ordered elements 
	while values continuously enter and leave a sliding window.
	
	The exact problem is not a direct copy of either one.
	
	
 * 	FOLLOW-UPS ***
 *
 *	Follow-up 1 — Maintain O(n log w) Without Sorting Every Window
	Priority: EXTREMELY HIGH
	
	The interviewer now asks:
	
	Sorting every window independently is too expensive. Can you process the entire array in O(n log w)?
	
	Design the data structure so that when the window moves you can efficiently:
	
	remove outgoing value
	add incoming value
	find the largest k values
	compute the sum of everything else
	
	A strong direction is maintaining two ordered multisets:
	
	keep     = smallest w-k elements
	ignored  = largest k elements
	
	Maintain:
	
	sumKeep
	
	so that the answer for each window becomes:
	
	sumKeep / (w - k)
	
	After every insertion or deletion, rebalance the two groups.
	
	This is the most important follow-up because it turns the problem from straightforward window processing into the intended data-structure problem.
	
 *	
 *	Follow-up 2 — Handle Duplicate Values Correctly
	Priority: VERY HIGH
	
	Suppose:
	
	nums = [5, 5, 5, 1]
	w = 3
	k = 2
	
	The data structure must distinguish multiple occurrences of the same value.
	
	For example:
	
	window = [5, 5, 1]
	
	Ignoring the largest two elements means removing both occurrences of 5.
	
	The interviewer may ask:
	
	How does your implementation support duplicate elements if you're using an ordered set?
	
	A normal TreeSet<Integer> is insufficient because duplicates collapse into one value.
	
	Possible approaches include:
	
	TreeMap<Integer, Integer>
	
	where the value stores frequency, or storing:
	
	(value, uniqueIndex)
	
	so each array occurrence is independently represented.
	
 *	
 *
 *	Follow-up 3 — Return the Sum Instead of the Average
	Priority: HIGH
	
	Change the API so that for every window you return:
	
	sum of the values after ignoring the largest k
	
	Example:
	
	nums = [1, 5, 2, 4, 3]
	w = 3
	k = 1
	
	Output:
	
	[3, 6, 5]
	
	because:
	
	[1,5,2] -> 1 + 2 = 3
	[5,2,4] -> 2 + 4 = 6
	[2,4,3] -> 2 + 3 = 5
	
	If the original solution already maintains sumKeep, this follow-up should require almost no architectural change.
	
	The interviewer is testing whether the chosen data structure maintains useful aggregate information rather than recomputing the result every time.
	
 *	
 *	
 *	Follow-up 4 — Ignore Both the Smallest K1 and Largest K2 Values
	Priority: MEDIUM-HIGH
	
	For every window:
	
	ignore the smallest k1 elements
	ignore the largest k2 elements
	average everything in the middle
	
	Example:
	
	window = [1, 2, 4, 7, 10]
	k1 = 1
	k2 = 2
	
	Ignore:
	
	smallest -> [1]
	
	largest -> [10, 7]
	
	Remaining:
	
	[2, 4]
	
	Average:
	
	3.0
	
	Now the window can naturally be divided into three ordered groups:
	
	low
	middle
	high
	
	Maintain:
	
	sumMiddle
	
	This makes the problem even closer to LC 1825 — Finding MK Average.
	
 *	
 *	
 *	Follow-up 5 — k Changes for Every Query
	Priority: MEDIUM
	
	Suppose the window remains the same, but you receive queries such as:
	
	averageIgnoringLargest(1)
	averageIgnoringLargest(3)
	averageIgnoringLargest(5)
	
	The interviewer asks:
	
	Your current solution is optimized for a fixed k. What changes if k is dynamic?
	
	The fixed two-part partition:
	
	smallest w-k
	largest k
	
	is no longer sufficient because the partition boundary changes with each query.
	
	This leads to discussion of data structures supporting:
	
	order statistics
	prefix sums by rank
	
	such as an augmented balanced BST, Fenwick tree with coordinate compression when values are bounded or known in advance, or another order-statistics structure.
	
	The goal is less about coding the entire structure and more about recognizing that dynamic k changes the fundamental data-structure requirement.
	 
 */