package Oracle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class TopKFrequentElements_347_Medium {

	public static void main(String[] args) {
		/*
		 * Example 1: Input: nums = [1,1,1,2,2,3], k = 2 Output: [1,2]
		 * 
		 * Example 2: Input: nums = [1], k = 1 Output: [1]
		 */
		int[] nums1 = { 1, 1, 1, 2, 2, 3 };
		int k1 = 2;
		int[] res1 = topKFrequent(nums1, k1);
		System.out.println("Expected: [1, 2], Actual: " + Arrays.toString(res1));

		int[] nums2 = { 1 };
		int k2 = 1;
		int[] res2 = topKFrequent(nums2, k2);
		System.out.println("Expected: [1], Actual: " + Arrays.toString(res2));
	}

	/*
	 * Complexity Analysis
	 * 
	 * Time complexity : O(Nlog(k)). The complexity of Counter method is O(N). To
	 * build a heap and output list takes O(Nlog(k)). Hence
	 * the overall complexity of the algorithm is (N + N \log(k)) = O(N+Nlog(k))=O(Nlog(k)).
	 * 
	 * Space complexity : O(N) to store the hash map.
	 */
	public static int[] topKFrequent(int[] nums, int k) {

		Map<Integer, Integer> map = new HashMap<>();

		//--build frequency table
		for (int num : nums) {
			map.put(num, map.getOrDefault(num, 0) + 1);
		}

		// comparator to order element by their frequency - increasing
		PriorityQueue<Integer> heap = new PriorityQueue<>((n1, n2) -> map.get(n1) - map.get(n2));

		// keep k top elements
		for (int n : map.keySet()) {
			heap.add(n);
			if (heap.size() > k) {
				heap.poll();
			}
		}

		int[] res = new int[heap.size()];
		for (int i = res.length - 1; i >= 0; i--) { // reverse order
			if (!heap.isEmpty()) {
				res[i] = heap.poll();
			}
		}
		return res;
	}

	
	/* Bucket Sort : Runtime: O(N)
	 * 
	 * https://leetcode.com/problems/top-k-frequent-elements/discuss/81602/Java-O(n)-Solution-Bucket-Sort
	 * */
	public static List<Integer> topKFrequent2(int[] nums, int k) {
        // freq map
        Map<Integer, Integer> freq = new HashMap<Integer, Integer>();
        for (int n : nums) {
            freq.put(n, freq.getOrDefault(n, 0) + 1);
        }
        // bucket sort on freq
        List<Integer>[] bucket = new List[nums.length + 1];
        for (int i = 0; i < bucket.length; i++) bucket[i] = new ArrayList();
        for (int key : freq.keySet()) {
            bucket[freq.get(key)].add(key);
        }
        // gather result
        List<Integer> res = new ArrayList();
        for (int i = bucket.length - 1; i >= 0; i--) {
            res.addAll(bucket[i]);
            if (res.size() >= k) break;
        }
        return res;
    }
	
}
