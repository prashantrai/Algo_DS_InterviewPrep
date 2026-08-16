package Expedia;

import java.util.PriorityQueue;

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

public class FindMedianFromDataStream_295_Hard {
	
	public static void main(String[] args) {
		
		FindMedianFromDataStream_295_Hard mf = new FindMedianFromDataStream_295_Hard();
		
		int[] arr = {8,9,7,6,14,12,15,13,10,11};
		
		for(int i : arr) {
			mf.addNum(i);
		}
		
		System.out.println(mf.findMedian());
	}

	
	/* 
    - Maintain minHeap (for right side of the partition of data) and maxHeap (for the left side). 
    - keep the most value in maxHeap and keep the balancing the number of element wit minHeap
    - Add the value first in maxHeap, that will push the max value in the to top then pop and add that to minHeap
    - if at any point the size of both is not equal then pop from the minHeap and add to maxHeap
    */
	/*
	 * Time: O(log N), 
	        - insertion and deletion from the heap takes O(log n).
	        - max there 3 offer() + 2 poll() = 5, 
	            that gives us O(5*logn) + O(1) for finding median
	        
	                 
	 * Space: O(N)
	 */
	
	
	PriorityQueue<Integer> maxPQ;// lower half
	PriorityQueue<Integer> minPQ;// higher half
	
	public FindMedianFromDataStream_295_Hard() {
		maxPQ = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
		minPQ = new PriorityQueue<>((a, b) -> Integer.compare(a, b));
	}

	// Works 
	// Adds a number into the data structure.
	public void addNum2(int num) {
		maxPQ.offer(num); //-- Adding first in max to have the samllest value on top then poll the smallest and add to minHeap
		minPQ.offer(maxPQ.poll());
		if (maxPQ.size() < minPQ.size()) {
			maxPQ.offer(minPQ.poll());
		}
	}
	
	// with fewer heap oprations
	public void addNum(int num) {
        if (maxPQ.isEmpty() || num <= maxPQ.peek()) {
            maxPQ.offer(num);
        } else {
            minPQ.offer(num);
        }

        // Rebalance heaps
        // Why minPQ.size()+1 ? 
        // When the total number of elements is odd, 
        // one heap must hold the extra element
        // We choose maxPQ to hold that extra element, so findMedian() is easy:
        // if sizes equal → median is average of both tops
        // otherwise → median is maxPQ.peek()
        // maxPQ is allowed to be larger by 1
        if (maxPQ.size() > minPQ.size() + 1) {
            minPQ.offer(maxPQ.poll());
        } else if (minPQ.size() > maxPQ.size()) {
            maxPQ.offer(minPQ.poll());
        }
    }

	// Returns the median of current data stream
	public double findMedian() {
		if (maxPQ.size() == minPQ.size()) {
			return (double) (maxPQ.peek() + (minPQ.peek())) / 2.0;
		} else {
			return maxPQ.peek();
		}
	}
}