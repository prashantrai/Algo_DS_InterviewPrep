package Amazon;

import java.util.Map;
import java.util.TreeMap;

public class MaximumConcurrentOrAggregateVideoBandwidth {

	public static void main(String[] args) {

        // Test 1: Given example
        int[][] streams1 = {
                {1, 5, 10},
                {2, 7, 20},
                {4, 6, 15}
        };
        System.out.println("Test 1: " + maxConcurrentVideoBandwidth(streams1)
                        + " | Expected: 45" );

        // Test 2: Streams touch but do not overlap
        // [1,5) ends exactly when [5,8) starts
        int[][] streams2 = {
                {1, 5, 10},
                {5, 8, 20}
        };
        System.out.println(
                "Test 2: " + maxConcurrentVideoBandwidth(streams2)
                        + " | Expected: 20"
        );

        // Test 3: All streams overlap
        int[][] streams3 = {
                {1, 10, 10},
                {2, 9, 20},
                {3, 8, 30}
        };
        System.out.println(
                "Test 3: " + maxConcurrentVideoBandwidth(streams3)
                        + " | Expected: 60"
        );

        // Test 4: No streams overlap
        int[][] streams4 = {
                {1, 2, 10},
                {3, 4, 20},
                {5, 6, 30}
        };
        System.out.println(
                "Test 4: " + maxConcurrentVideoBandwidth(streams4)
                        + " | Expected: 30"
        );

        // Test 5: Multiple events happen at the same timestamp
        int[][] streams5 = {
                {1, 5, 10},
                {2, 5, 20},
                {5, 8, 40},
                {5, 10, 5}
        };
        // Before time 5: 10 + 20 = 30
        // At time 5: first two end, last two start => 40 + 5 = 45
        System.out.println(
                "Test 5: " + maxConcurrentVideoBandwidth(streams5)
                        + " | Expected: 45"
        );

        // Test 6: Single stream
        int[][] streams6 = {
                {100, 200, 50}
        };
        System.out.println(
                "Test 6: " + maxConcurrentVideoBandwidth(streams6)
                        + " | Expected: 50"
        );

        // Test 7: Empty input
        int[][] streams7 = {};
        System.out.println(
                "Test 7: " + maxConcurrentVideoBandwidth(streams7)
                        + " | Expected: 0"
        );
    }
	
	// sweep-line algorithm/approach
	private static int maxConcurrentVideoBandwidth(int[][] streams) {
	    if (streams == null || streams.length == 0) {
	        return 0;
	    }

	    Map<Integer, Integer> map = new TreeMap<>();

	    // Store bandwidth CHANGE at each timestamp.
	    for (int[] stream : streams) {
	        int startTime = stream[0];
	        int endTime = stream[1];
	        int bitrate = stream[2];

	        map.put(startTime, map.getOrDefault(startTime, 0) + bitrate);
	        map.put(endTime, map.getOrDefault(endTime, 0) - bitrate);
	    }

	    int currentBandwidth = 0;
	    int maxBandwidth = 0;

	    // Sweep timestamps in sorted order.
	    for (int delta : map.values()) {
	        currentBandwidth += delta;
	        maxBandwidth = Math.max(maxBandwidth, currentBandwidth);
	    }

	    printTimeRangeForMaxBitrate(map); // Follow-up #2: Return the Time Range
	    
	    return maxBandwidth;
	}
	
	/* Follow-up #2: Return the Time Range
	Instead of returning only the maximum bitrate, return the interval or intervals 
	during which that maximum bandwidth was required.
	
	For the example:
	Maximum bitrate = 45
	Interval = [4, 5)
	
	This requires remembering the timestamps between consecutive sweep-line events.
	 */
	
	/* Interview Script: 
	 "In the original solution, the running sum gives me the bandwidth after processing 
	 each event. For the follow-up, I realize that bandwidth remains unchanged between 
	 two consecutive event timestamps. So while sweeping, I look at [previousTime, currentTime). 
	 The current running bandwidth is exactly the bandwidth used throughout that interval. 
	 I compare it against the maximum and store that interval whenever I find a new maximum."
	 
	 * */
	static void printTimeRangeForMaxBitrate(Map<Integer, Integer> map) {
		
		int currentBandwidth = 0;
	    int maxBandwidth = 0;
	    
	    int maxStart = -1;
	    int maxEnd = -1;
	    
	    Integer prevTime = null;
		
		for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
			int currTime = entry.getKey(); 
			int bitrate = entry.getValue(); 
			
			// Bandwidth from previousTime to currentTime
	        // was the bandwidth calculated at previousTime.
			if(prevTime != null) {
				if(currentBandwidth > maxBandwidth) {
					maxBandwidth = currentBandwidth;
					maxStart = prevTime;
					maxEnd = currTime;
				}
			}
			
			// Events at currentTime affect [currentTime, nextTime)
			currentBandwidth += bitrate;
			
			prevTime = currTime;
		}
		
		System.out.println("Time Range for max bitrate: [" + maxStart +", "+maxEnd+"]");
	}
	

}

/** Follow-up 3
 ## Follow-up: What if there are 10 billion streams and input does not fit in memory?

	### How I should think
	
	The original sweep-line idea is still correct.
	
	The problem is this part:
	
	```java
	TreeMap<Integer, Integer> events
	```
	
	With 10 billion streams, we can generate up to 20 billion events:
	
	```text
	(startTime, +bitrate)
	(endTime,   -bitrate)
	```
	
	We cannot keep all of them in memory.
	
	The key observation is:
	
	> The sweep itself only needs events in sorted timestamp order and O(1) state.
	> The part that does not scale is storing/sorting all events in memory.
	
	So I would keep the same sweep-line algorithm, but replace in-memory sorting with 
	**external sorting**.
	
	---
	
	## Interview Script
	
	> "The current TreeMap solution will not work for 10 billion streams because it may 
	  need to store up to 20 billion start/end events in memory.
	>
	> However, the sweep itself is streaming-friendly. Once events are available in timestamp 
	order, I only need the current bandwidth and maximum bandwidth.
	>
	> So I would use an external sort. I would read as many streams as fit in memory, 
	convert them into start and end delta events, sort that chunk by timestamp, and 
	write the sorted chunk to disk. I repeat this until the entire input is processed.
	>
	> Then I perform a k-way merge of those sorted files using a min-heap. As events 
	come out in global timestamp order, I aggregate all deltas having the same timestamp, 
	update the running bandwidth, and track the maximum.
	>
	> This preserves the original sweep-line logic while keeping memory bounded."
	
	---
	
	## Step 1: Process Input in Chunks
	
	For every stream:
	
	```text
	[start, end, bitrate]
	
	start -> +bitrate
	end   -> -bitrate
	```
	
	Example:
	
	```text
	[1,5,10] -> (1,+10), (5,-10)
	[2,7,20] -> (2,+20), (7,-20)
	```
	
	Suppose memory can hold 1 million streams.
	
	Process one chunk:
	
	```text
	read chunk
	      ↓
	convert to events
	      ↓
	sort by timestamp
	      ↓
	write sorted run to disk
	```
	
	Repeat for all 10 billion streams.
	
	Result:
	
	```text
	chunk1.sorted
	chunk2.sorted
	chunk3.sorted
	...
	```
	
	---
	
	## Step 2: K-way Merge
	
	Each file is individually sorted.
	
	Example:
	
	```text
	File 1: (1,+10), (5,-10)
	File 2: (2,+20), (7,-20)
	File 3: (4,+15), (6,-15)
	```
	
	Keep the next event from every file in a min-heap:
	
	```text
	(1,+10)
	(2,+20)
	(4,+15)
	```
	
	Remove the smallest timestamp and then read the next event from that same file.
	
	This produces the events globally in timestamp order without loading all files into memory.
	
	---
	
	## Important: Aggregate Same Timestamps
	
	If multiple events occur at the same timestamp:
	
	```text
	(5,-10)
	(5,-20)
	(5,+40)
	```
	
	combine them first:
	
	```text
	5 -> +10
	```
	
	Then:
	
	```java
	currentBandwidth += delta;
	maxBandwidth = Math.max(maxBandwidth, currentBandwidth);
	```
	
	This cleanly preserves the `[startTime, endTime)` requirement.
	
	---
	
	## Complexity
	
	Let:
	
	```text
	N = number of streams
	K = number of sorted chunks
	```
	
	There are `2N` events.
	
	External sorting:
	
	```text
	O(N log N)
	```
	
	K-way merge:
	
	```text
	O(N log K)
	```
	
	Memory:
	
	```text
	O(M + K)
	```
	
	where `M` is the chunk size that fits in memory.
	
	The important point:
	
	> Memory is bounded and does not grow with all 10 billion records.
	
	---
	
	## Optimization to Mention
	
	Before choosing external sorting, I should ask:
	
	> "Is the timestamp range bounded and reasonably small?"
	
	For example, if timestamps are only:
	
	```text
	0 ... 86,400
	```
	
	I can directly aggregate:
	
	```text
	delta[start] += bitrate
	delta[end]   -= bitrate
	```
	
	and then prefix-sum over timestamps.
	
	Complexity becomes:
	
	```text
	O(N + T)
	```
	
	where `T` is the timestamp range.
	
	This avoids sorting completely.
	
	So my decision is:
	
	```text
	Small bounded timestamp range
	        ↓
	Array / bucket aggregation
	
	Large or unbounded timestamp range
	        ↓
	External sort + k-way merge
	```
	
	---
	
	## Key Line to Remember
	
	> "When the input no longer fits in memory, I first identify which part of 
	my existing algorithm actually requires all the data. Here the sweep is already 
	streaming; storing and sorting the events is the bottleneck. So I keep the 
	sweep-line logic and replace the in-memory sort with external sorting."

  
 * */



/*
 Maximum Concurrent Video Bandwidth / Maximum Aggregate Bitrate
	Problem Statement
	
	You are given a collection of video streams. Each stream is represented as:
	
	[startTime, endTime, bitrate]
	
	A stream consumes bitrate units of bandwidth continuously during the interval:
	
	[startTime, endTime)
	
	The endTime is exclusive, meaning a stream ending at time t does not overlap with another stream starting at time t.
	
	Return the maximum total bitrate being consumed simultaneously at any point in time.
	
	Example
	Input:
	
	streams = [
	    [1, 5, 10],
	    [2, 7, 20],
	    [4, 6, 15]
	]
	
	Output:
	45
	Explanation
	
	The bandwidth usage changes as follows:
	
	[1,2)  -> 10
	[2,4)  -> 10 + 20 = 30
	[4,5)  -> 10 + 20 + 15 = 45
	[5,6)  -> 20 + 15 = 35
	[6,7)  -> 20
	
	Therefore:
	
	maximum bandwidth = 45
	Expected Approach
	
	This is a sweep-line / event aggregation problem.
	
	For each stream:
	
	(startTime, +bitrate)
	(endTime,   -bitrate)
	
	Sort events by time and maintain a running bandwidth.
	
	An important detail is [startTime, endTime). If one stream ends exactly when another starts, they do not overlap.
	
	For example:
	
	[1, 5, 10]
	[5, 8, 20]
	
	The answer is:
	
	20
	
	not 30.
	
	A particularly clean implementation is to aggregate deltas occurring 
	at the same timestamp before updating the maximum.
	
	
	
	* Follow-up #1: Huge Input
	
	The input contains 10 billion video streams and cannot fit in memory. 
	How would you modify your solution?
	
	This is probably the most important follow-up from the source you found.
	
	The basic in-memory solution requires sorting 2N events:
	
	O(N log N)
	
	but storing all events is no longer possible.
	
	A strong expected discussion is external sorting:
	
	Read a manageable chunk of records from disk.
	Convert streams into start/end events.
	Sort the chunk in memory.
	Write the sorted events back to temporary files.
	Perform a k-way merge over those sorted files.
	While merging, maintain the running bitrate and maximum.
	
	You do not need to load all 10 billion events into memory.
	
	This gives approximately:
	
	Memory: O(chunk size + number of files)
	Disk:   O(N)
	Time:   dominated by external sorting
	
	This follow-up tests more than algorithms. It tests whether you can move from 
	an in-memory coding problem to a scalable data-processing design.
	
	
	
	* Follow-up #2: Return the Time Range
	
	Instead of returning only the maximum bitrate, return the interval or intervals 
	during which that maximum bandwidth was required.
	
	For the example:
	Maximum bitrate = 45
	Interval = [4, 5)
	
	This requires remembering the timestamps between consecutive sweep-line events.
	
	
	
	* Follow-up #3: Streams Arrive Continuously
	
	What if streams arrive continuously and you need to answer this query repeatedly?
	
	Now the interviewer may be moving toward a streaming/system-design discussion.
	
	Possible directions include:
	
	time buckets
	ordered maps
	segment trees
	distributed stream processing
	windowed aggregation
	
	depending on the requirements.
 */