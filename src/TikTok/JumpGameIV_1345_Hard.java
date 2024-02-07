package TikTok;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class JumpGameIV_1345_Hard {

	public static void main(String[] args) {

		int[] arr = { 100, -23, -23, 404, 100, 23, 23, 23, 3, 404 };
		System.out.println("Expected: 3, Actual: " + minJumps(arr));

		int[] arr1 = { 7, 6, 9, 6, 9, 6, 9, 7 };
		System.out.println("Expected: 1, Actual: " + minJumps(arr1));

	}

	/*
	 * Refer:
	 * https://www.youtube.com/watch?v=JYbU8RH1OSQ&ab_channel=AlgorithmsMadeEasy
	 * https://leetcode.com/problems/jump-game-iv/discuss/1690813/Best-explanation-
	 * EVER-possible-for-this-question
	 * 
	 *
	 * Algo: Use BFS. 1. Create Map of value (as key) and list of indexes as value.
	 * 2. Now use BFS and initialize a Q with 0 index (starting point) 3. Initialize
	 * a variable 'step=0' 4. Iterate Q (based on current Q size) 5. Poll from the Q
	 * and for each polled index from Q a. jump to j-1 (should not be out of array
	 * and exists in map) b. jump to j+1 and exists in map if this is the last
	 * index, then return step as result
	 * 
	 * c. Read the value from Input array and check if it exists in the map i.e. if
	 * this elements hasn't been processed (because we'll be removing elements from
	 * map after it has been processed) d. Use the value to get the value from Map,
	 * this will return list of indexes where that value exists in the input array).
	 * e. Iterate the list (returned in above step) i. if it's not current index and
	 * it's the last index then return the step value as result. ii. i. if it's not
	 * current index and it's NOT the last index then add in Q and continue. f.
	 * Remove this value (as it's been processed) from the MAP. g. Finally return
	 * the step.
	 * 
	 * 
	 * 
	 * 
	 * 
	 * Time complexity: O(N) since we will visit every node at most once. Space:
	 * O(N)
	 */

	public static int minJumps(int[] arr) {
		int n = arr.length;
		if (n == 1)
			return 0;

		// craeted map holding integer & list
		Map<Integer, List<Integer>> map = new HashMap<>();
		for (int i = 0; i < n; i++) {
			// so, using this function it will check if arr[i] is present or not,
			// if it's not present it would create a new arraylist
			// and if it's already present we will add index in it
			map.computeIfAbsent(arr[i], v -> new ArrayList<Integer>()).add(i);
		}

		int step = 0; // intial step is 0
		Queue<Integer> q = new ArrayDeque<>();
		q.offer(0); // first index as we'll start from 0th index

		while (!q.isEmpty()) {
			step++;
			int size = q.size();

			for (int i = 0; i < size; i++) {

				int j = q.poll(); // getting element from queue

				// so, here we will perform 3 steps

				// jump to j-1
				if (j - 1 >= 0 && map.containsKey(arr[j - 1])) {
					q.offer(j - 1);
				}

				// jump to j+1
				if (j + 1 < n && map.containsKey(arr[j + 1])) {
					// if we are at last index return
					if (j + 1 == n - 1)
						return step;

					q.offer(j + 1);
				}

				// jump to k where arr[j] == arr[k]
				if (map.containsKey(arr[j])) { // if this elements hasn't been processed
					for (int k : map.get(arr[j])) { // so, we will iterate over each k
						if (k != j) { // not the same index/position
							if (k == n - 1)
								return step;

							q.offer(k);
						}
					}
				}
				map.remove(arr[j]); // removing from map
			}
		}

		return step;
	}

}
