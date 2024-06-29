package Facebook;

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
  	Queue<Integer> pq = new PriorityQueue<>((a, b) -> (b - a));

 * */

/*
 * Time: O(log N), 
        - insertion and deletion from the heap takes O(log n).
        - max there 3 offer() + 2 poll() = 5, 
            that gives us O(5*logn) + O(1) for finding median
        
                 
 * Space: O(N)
 */

public class FindMedianFromDataStream_295_Hard {
	
	public static void main(String[] args) {
		
		FindMedianFromDataStream_295_Hard mf = new FindMedianFromDataStream_295_Hard();
		
		int[] arr = {8,9,7,6,14,12,15,13,10,11};
		
		for(int i : arr) {
			mf.addNum(i);
		}
		
		System.out.println(mf.findMedian());
	}

	
	PriorityQueue<Integer> maxHeap;// lower half
	PriorityQueue<Integer> minHeap;// higher half
	
	public FindMedianFromDataStream_295_Hard() {
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