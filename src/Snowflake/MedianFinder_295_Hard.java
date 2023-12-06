package Snowflake;

import java.util.Collections;
import java.util.PriorityQueue;

//https://www.programcreek.com/2015/01/leetcode-find-median-from-data-stream-java/

// https://jindongpu.wordpress.com/2015/10/20/implement-max-heap-and-min-heap-using-priorityqueue-in-java/

/*
 * Below are more/other approaches to create MaxHeap and 
 * slightly better to prevent possible Integer overflows.
 * 
 	Queue<Integer> pq = new PriorityQueue<>((a, b) -> Integer.compare(b, a);
	or
	Queue<Integer> pq = new PriorityQueue<>((a, b) -> b.compareTo(a));
	or
	Queue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
	
  other than above another simpler approach is,
	WARNING: below can lead to overflow in some cases, read here: https://leetcode.com/problems/sliding-window-median/discuss/96352/Never-create-max-heap-in-java-like-this...
  	
  	Queue<Integer> pq = new PriorityQueue<>((a, b) -> (b - a));

 * */

/*
 * Time: O(log N)
 * Space: O(N)
 */

public class MedianFinder_295_Hard {
	PriorityQueue<Integer> maxHeap;// lower half
	PriorityQueue<Integer> minHeap;// higher half
	
	public static void main(String[] args) {
		
		MedianFinder_295_Hard mf = new MedianFinder_295_Hard();
		
		int[] arr = {8,9,7,6,14,12,15,13,10,11};
		
		for(int i : arr) {
			mf.addNum(i);
		}
		
		System.out.println(mf.findMedian());
	}

	public MedianFinder_295_Hard() {
		maxHeap = new PriorityQueue<Integer>(Collections.reverseOrder());
		minHeap = new PriorityQueue<Integer>();
	}
	
	/*
    Add lower half in maxHeap: will give us the middle number (max value in lower half)
    Add higher half in minHeap: will give us the the middle number (min value in lower half)
    */

	// Adds a number into the data structure.
	public void addNum(int num) {
		maxHeap.offer(num); //-- Adding first in max to have the smallest value on top then poll the smallest and add to minHeap
		minHeap.offer(maxHeap.poll());
		if (maxHeap.size() < minHeap.size()) {
			maxHeap.offer(minHeap.poll());
		}
	}

	// Returns the median of current data stream
	public double findMedian() {
		if (maxHeap.size() == minHeap.size()) {
			return (double) (maxHeap.peek() + (minHeap.peek())) / 2;
		} else {
			return maxHeap.peek();
		}
	}
}