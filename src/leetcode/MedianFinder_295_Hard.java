package leetcode;

import java.util.Collections;
import java.util.PriorityQueue;

//https://www.programcreek.com/2015/01/leetcode-find-median-from-data-stream-java/

// https://jindongpu.wordpress.com/2015/10/20/implement-max-heap-and-min-heap-using-priorityqueue-in-java/

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

	// Adds a number into the data structure.
	public void addNum(int num) {
		maxHeap.offer(num); //-- Adding first in max to have the samllest value on top then poll the smallest and add to minHeap
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