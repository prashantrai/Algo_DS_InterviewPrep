package Box;

import java.util.PriorityQueue;
import java.util.Queue;

public class KthLargestElementInAStream_703_Easy {

	public static void main(String[] args) {

		int k = 3;
		int[] nums = { 4, 5, 8, 2 };
		KthLargest obj = new KthLargest(k, nums);
		System.out.println("Expected: 4, Actual: " + obj.add(3));
		System.out.println("Expected: 5, Actual: " + obj.add(5));
		System.out.println("Expected: 5, Actual: " + obj.add(10));
		System.out.println("Expected: 8, Actual: " + obj.add(9));
		System.out.println("Expected: 8, Actual: " + obj.add(4));

	}

	/*
	 * Time complexity: O(N⋅log(N) + M⋅log(k))
	 * 
	 * Space: O(N)
	 */

	private static class KthLargest {
		private Queue<Integer> minHeap;
		private int k;

		public KthLargest(int k, int[] nums) {
			this.minHeap = new PriorityQueue<>();
			this.k = k;

			for (int num : nums)
				add(num);
		}

		public int add(int val) {
			minHeap.offer(val);

			if (minHeap.size() > k)
				minHeap.poll();

			return minHeap.peek();
		}
	}

}
