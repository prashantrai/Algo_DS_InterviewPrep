package leetcode;

import java.util.PriorityQueue;
import java.util.Random;

public class KthLargestElementInAnArray_215_Medium {

	public static void main(String[] args) {
		int[] nums = {3,2,1,5,6,4};
		int k=2;
		System.out.println("1. "+findKthLargest(nums, k));
		
	}

	
	//--Copied from- https://leetcode.com/problems/kth-largest-element-in-an-array/discuss/60294/Solution-explained
	public static int findKthLargest(int[] nums, int k) {

        k = nums.length - k;
        int lo = 0;
        int hi = nums.length - 1;
        while (lo < hi) {
            final int j = partition(nums, lo, hi);
            if(j < k) {
                lo = j + 1;
            } else if (j > k) {
                hi = j - 1;
            } else {
                break;
            }
        }
        return nums[k];
    }

    private static int partition(int[] a, int lo, int hi) {

        int i = lo;
        int j = hi + 1;
        while(true) {
            while(i < hi && less(a[++i], a[lo]));
            while(j > lo && less(a[lo], a[--j]));
            if(i >= j) {
                break;
            }
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    private static void exch(int[] a, int i, int j) {
        final int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static boolean less(int v, int w) {
        return v < w;
    }
	
	
	// --Copied from Leet code article - Premium

	/*
	 * Complexity Analysis
	 * 
	 * Time complexity : O(Nlogk). Space complexity : O(k) to store the heap
	 * elements.
	 */
	class Solution_1 {
		public int findKthLargest(int[] nums, int k) {
			// init heap 'the smallest element first'
			PriorityQueue<Integer> heap = new PriorityQueue<Integer>((n1, n2) -> n1 - n2);

			// keep k largest elements in the heap
			for (int n : nums) {
				heap.add(n);
				if (heap.size() > k)
					heap.poll();
			}

			// output
			return heap.poll();
		}
	}

	// --Quick select algo

	/*
	 * Complexity Analysis
	 * 
	 * Time complexity : O(N) in the average case, O(N^2) in the worst case. Space
	 * complexity : O(1)
	 */

	class Solution_2 {
		int[] nums;

		public void swap(int a, int b) {
			int tmp = this.nums[a];
			this.nums[a] = this.nums[b];
			this.nums[b] = tmp;
		}

		public int partition(int left, int right, int pivot_index) {
			int pivot = this.nums[pivot_index];
			// 1. move pivot to end
			swap(pivot_index, right);
			int store_index = left;

			// 2. move all smaller elements to the left
			for (int i = left; i <= right; i++) {
				if (this.nums[i] < pivot) {
					swap(store_index, i);
					store_index++;
				}
			}

			// 3. move pivot to its final place
			swap(store_index, right);

			return store_index;
		}

		public int quickselect(int left, int right, int k_smallest) {
			/*
			 * Returns the k-th smallest element of list within left..right.
			 */

			if (left == right) // If the list contains only one element,
				return this.nums[left]; // return that element

			// select a random pivot_index
			Random random_num = new Random();
			int pivot_index = left + random_num.nextInt(right - left);

			pivot_index = partition(left, right, pivot_index);

			// the pivot is on (N - k)th smallest position
			if (k_smallest == pivot_index)
				return this.nums[k_smallest];
			// go left side
			else if (k_smallest < pivot_index)
				return quickselect(left, pivot_index - 1, k_smallest);
			// go right side
			return quickselect(pivot_index + 1, right, k_smallest);
		}

		public int findKthLargest(int[] nums, int k) {
			this.nums = nums;
			int size = nums.length;
			// kth largest is (N - k)th smallest
			return quickselect(0, size - 1, size - k);
		}
	}

}
