package Oracle;

import java.util.Arrays;
import java.util.PriorityQueue;

public class MeetingRoomsII_253_Medium {

	// Main method with test cases
    public static void main(String[] args) {
    	MeetingRoomsII_253_Medium solution = new MeetingRoomsII_253_Medium();
        
        // Test Case 1: Example from problem statement
        int[][] intervals1 = {{0, 30}, {5, 10}, {15, 20}};
        System.out.println("Test Case 1: " + Arrays.deepToString(intervals1));
        System.out.println("Expected: 2, Got: " + solution.minMeetingRooms(intervals1));
        System.out.println();
        
        // Test Case 2: Example from problem statement
        int[][] intervals2 = {{7, 10}, {2, 4}};
        System.out.println("Test Case 2: " + Arrays.deepToString(intervals2));
        System.out.println("Expected: 1, Got: " + solution.minMeetingRooms(intervals2));
        System.out.println();
        
        // Test Case 3: All meetings overlap
        int[][] intervals3 = {{1, 5}, {2, 6}, {3, 7}, {4, 8}};
        System.out.println("Test Case 3: " + Arrays.deepToString(intervals3));
        System.out.println("Expected: 4, Got: " + solution.minMeetingRooms(intervals3));
        System.out.println();
        
        // Test Case 4: No overlapping meetings
        int[][] intervals4 = {{1, 2}, {3, 4}, {5, 6}, {7, 8}};
        System.out.println("Test Case 4: " + Arrays.deepToString(intervals4));
        System.out.println("Expected: 1, Got: " + solution.minMeetingRooms(intervals4));
        System.out.println();
        
        // Test Case 5: Empty input
        int[][] intervals5 = {};
        System.out.println("Test Case 5: " + Arrays.deepToString(intervals5));
        System.out.println("Expected: 0, Got: " + solution.minMeetingRooms(intervals5));
        System.out.println();
        
        // Test Case 6: Single meeting
        int[][] intervals6 = {{1, 10}};
        System.out.println("Test Case 6: " + Arrays.deepToString(intervals6));
        System.out.println("Expected: 1, Got: " + solution.minMeetingRooms(intervals6));
        System.out.println();
        
        // Test Case 7: Back-to-back meetings (should reuse room)
        int[][] intervals7 = {{1, 3}, {3, 5}, {5, 7}};
        System.out.println("Test Case 7: " + Arrays.deepToString(intervals7));
        System.out.println("Expected: 1, Got: " + solution.minMeetingRooms(intervals7));
        System.out.println();
        
        // Test Case 8: Complex overlapping scenario
        int[][] intervals8 = {{13, 15}, {1, 13}, {6, 9}, {3, 7}, {8, 10}, {2, 4}};
        System.out.println("Test Case 8: " + Arrays.deepToString(intervals8));
        System.out.println("Expected: 3, Got: " + solution.minMeetingRooms(intervals8));
        System.out.println();
    }
    
    
	public int minMeetingRooms(int[][] intervals) {
        return minMeetingRooms_PQ(intervals);
        // return minMeetingRooms_UsingArray(intervals);
    }

    /**
     * Alternative approach using Min-Heap (Priority Queue)
     * 
     * Time Complexity: O(N log N) - sorting + heap operations
     * Space Complexity: O(N) - for the priority queue
     */
    public int minMeetingRooms_PQ(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        
        // Sort intervals by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        // Min-heap to store end times of meetings currently using rooms
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        for (int[] interval : intervals) {
            // If heap is not empty and current meeting starts after 
            // the earliest ending meeting, reuse that room
            if (!minHeap.isEmpty() && interval[0] >= minHeap.peek()) {
                minHeap.poll(); // Remove the finished meeting
            }
            // Add current meeting's end time to heap
            minHeap.offer(interval[1]);
        }
        
        // Size of heap represents minimum rooms needed
        return minHeap.size();
    }

    /**
     * Finds the minimum number of conference rooms required for given meeting intervals.
     * 
     * Approach: Chronological Ordering with Two Pointers
     * - Separate start and end times into different arrays
     * - Sort both arrays independently
     * - Use two pointers to traverse through start and end times
     * - Track the number of rooms needed at any point in time
     * 
     * Time Complexity: O(N log N) - due to sorting both arrays
     * Space Complexity: O(N) - for storing separate start and end time arrays
     * 
     * @param intervals array of meeting intervals where intervals[i] = [start_i, end_i]
     * @return minimum number of conference rooms required
     */
    public int minMeetingRooms_UsingArray(int[][] intervals) {
        // Handle edge case
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        
        // Create separate arrays for start times and end times
        int[] startTimes = new int[intervals.length];
        int[] endTimes = new int[intervals.length];
        
        // Extract start and end times from intervals
        for (int i = 0; i < intervals.length; i++) {
            startTimes[i] = intervals[i][0];
            endTimes[i] = intervals[i][1];
        }
        
        // Sort both arrays independently
        Arrays.sort(startTimes);
        Arrays.sort(endTimes);
        
        // Initialize pointers and room counter
        int startPointer = 0;  // pointer for start times array
        int endPointer = 0;    // pointer for end times array
        int roomsNeeded = 0;   // current number of rooms needed
        
        // Process all meetings by their start times
        while (startPointer < intervals.length) {
            // If current meeting starts before the earliest ending meeting finishes,
            // we need a new room
            if (startTimes[startPointer] < endTimes[endPointer]) {
                roomsNeeded++;
            } else {
                // Current meeting starts after or when a meeting ends,
                // so we can reuse that room (move end pointer forward)
                endPointer++;
            }
            // Always move to next meeting start time
            startPointer++;
        }
        
        return roomsNeeded;
    }

}
