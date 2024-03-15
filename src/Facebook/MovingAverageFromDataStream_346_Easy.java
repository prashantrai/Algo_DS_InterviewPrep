package Facebook;

import java.util.ArrayDeque;
import java.util.Queue;

public class MovingAverageFromDataStream_346_Easy {

	public static void main(String[] args) {
		
		MovingAverage movingAverage = new MovingAverage(3);
		
		double avg = movingAverage.next(1); // return 1.0 = 1 / 1
		System.out.println("Expected: 1.0  Actual: " + avg);
		
		avg = movingAverage.next(10); // return 5.5 = (1 + 10) / 2
		System.out.println("Expected: 5.5  Actual: " + avg);
		
		avg = movingAverage.next(3); // return 4.66667 = (1 + 10 + 3) / 3
		System.out.println("Expected: 4.66667  Actual: " + avg);
		
		avg = movingAverage.next(5); // return 6.0 = (10 + 3 + 5) / 3
		System.out.println("Expected: 6.0  Actual: " + avg);
	}
	
	/* 
	 * https://leetcode.com/problems/moving-average-from-data-stream/discuss/81491/Java-O(1)-time-solution./85924
	 * 
	 * Time: O(1), adding and removing from queue/window is O(1)
	 * Space: O(N),  where N is the size of the moving window.
     */
	
	static class MovingAverage {

	    int size;
	    double sum;
	    Queue<Integer> q;
	    /** Initialize your data structure here. */
	    public MovingAverage(int size) {
	        this.size = size;
	        sum = 0;
	        q = new ArrayDeque<>();
	    }
	    
	    public double next(int val) {
	        if(q.size() == size) {
	            // remove oldest from the q/window and substract from sum
	            sum = sum - q.poll();
	        }
	        // add a new element to the window
	        q.offer(val);
	        sum += val;
	        
	        return sum/q.size();
	    }
	}

	/**
	 * Your MovingAverage object will be instantiated and called as such:
	 * MovingAverage obj = new MovingAverage(size);
	 * double param_1 = obj.next(val);
	 */

}
