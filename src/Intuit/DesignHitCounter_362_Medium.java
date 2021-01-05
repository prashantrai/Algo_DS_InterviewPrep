package Intuit;

import java.util.LinkedList;
import java.util.Queue;

public class DesignHitCounter_362_Medium {

	//https://leetcode.com/problems/design-hit-counter/
	//https://leetcode.com/discuss/interview-question/178662/Design-a-Hit-Counter/
	
	/*
	 	362. Design Hit Counter
		
		Design a hit counter which counts the number of hits received in the past 5 minutes.
		Each function accepts a timestamp parameter (in seconds granularity) and you may assume 
		that calls are being made to the system in chronological order (i.e. the timestamp is monotonically increasing). 
		You may assume that the earliest timestamp starts at 1.
		It is possible that several hits arrive roughly at the same time.
		
		Example:
		HitCounter counter = new HitCounter();
		
		// hit at timestamp 1.
		counter.hit(1);
		
		// hit at timestamp 2.
		counter.hit(2);
		
		// hit at timestamp 3.
		counter.hit(3);
		
		// get hits at timestamp 4, should return 3.
		counter.getHits(4);
		
		// hit at timestamp 300.
		counter.hit(300);
		
		// get hits at timestamp 300, should return 4.
		counter.getHits(300);
		
		// get hits at timestamp 301, should return 3.
		counter.getHits(301); 
		
		Follow up:
		What if the number of hits per second could be very large? Does your design scale? 
	 * */
	
	public static void main(String[] args) {
		HitCounter counter = new HitCounter();

		// hit at timestamp 1.
		counter.hit(1);

		// hit at timestamp 2.
		counter.hit(2);

		// hit at timestamp 3.
		counter.hit(3);

		// get hits at timestamp 4, should return 3.
		counter.getHits(4);

		// hit at timestamp 300.
		counter.hit(300);

		// get hits at timestamp 300, should return 4.
		counter.getHits(300);

		// get hits at timestamp 301, should return 3.
		counter.getHits(301); 
		
		
		/* Input Sequence: ["HitCounter","hit","hit","hit","getHits","hit","getHits","getHits"]
				           [   [],        [1],  [2],  [3],   [4],    [300],  [300],    [301]]
				
				Expected
				[null,null,null,null,3,null,4,3]
		*/
	}
	
	

	static class HitCounter {
	    private Queue<Integer> q_timestamps;
	    
	    /** Initialize your data structure here. */
	    public HitCounter() {
	        q_timestamps = new LinkedList<>();
	    }
	    
	    /** Record a hit.
	        @param timestamp - The current timestamp (in seconds granularity). */
	    public void hit(int timestamp) {
	        q_timestamps.offer(timestamp);                                 
	    }
	    
	    /** Return the number of hits in the past 5 minutes.
	        @param timestamp - The current timestamp (in seconds granularity). */
	    public int getHits(int timestamp) {
	        while (!q_timestamps.isEmpty() && timestamp- q_timestamps.peek() >= 300){
	            q_timestamps.poll();
	        }
	        return q_timestamps.size();
	    }
	    
	    /* alternatives: Wihout removing from the queue
	     	public int getHits(int timestamp) {
        
		        int total = 0;
		        for (int i = 0; i < 300; i++) {
		            if (timestamp - timestamps[i] < 300) {
		                total += counts[i];        
		            }
		        }
		        
		        return total;
		    }
	      
	     * */
	}

	/**
	 * Your HitCounter object will be instantiated and called as such:
	 * HitCounter obj = new HitCounter();
	 * obj.hit(timestamp);
	 * int param_2 = obj.getHits(timestamp);
	 */

}

/*
 * Question: 362. Design Hit Counter-Medium Design a hit counter which counts
 * the number of hits received in the past 5 minutes.
 * 
 * Each function accepts a timestamp parameter (in seconds granularity) and you
 * may assume that calls are being made to the system in chronological order
 * (ie, the timestamp is monotonically increasing). You may assume that the
 * earliest timestamp starts at 1.
 * 
 * It is possible that several hits arrive roughly at the same time.
 * 
 * Example:
 * 
 * HitCounter counter = new HitCounter();
 * 
 * // hit at timestamp 1. counter.hit(1);
 * 
 * // hit at timestamp 2. counter.hit(2);
 * 
 * // hit at timestamp 3. counter.hit(3);
 * 
 * // get hits at timestamp 4, should return 3. counter.getHits(4);
 * 
 * // hit at timestamp 300. counter.hit(300);
 * 
 * // get hits at timestamp 300, should return 4. counter.getHits(300);
 * 
 * // get hits at timestamp 301, should return 3. counter.getHits(301); Follow
 * up: What if the number of hits per second could be very large? Does your
 * design scale?
 */
