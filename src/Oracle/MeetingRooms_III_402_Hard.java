package Oracle;

import java.util.Arrays;
import java.util.PriorityQueue;

public class MeetingRooms_III_402_Hard {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/*
    Step-by-Step Algorithm (Simple Terms)

        1. Sort meetings by start time
        2. Initialize:
        - freeRooms: min heap of room numbers
        - busyRooms: min heap of (endTime, room)
        - count[room]: number of meetings per room

        3. For each meeting:
        - Free all rooms whose meetings ended before current start
        - If a free room exists:
            -- Assign it directly
        - Else:
            -- Delay the meeting to the earliest finishing room
        - Update meeting count
        4. Return room with highest meeting count


    | Component        | Complexity |
    | ---------------- | ---------- |
    | Sorting meetings | O(m log m) |
    | Heap operations  | O(m log n) |
    | Total Time       | O(m log n) |
    | Space            | O(n)       |

     */

    public int mostBooked(int n, int[][] meetings) {

        // Sort meetings by start time
        Arrays.sort(meetings, (a,b) -> Integer.compare(a[0], b[0]));
        // Arrays.sort(meetings, (a,b) -> a[0] - b[0]);

        // Min heap of free rooms (room number)
        PriorityQueue<Integer> freeRooms_minHeap = new PriorityQueue<>();

        // Min heap of busy rooms: [endTime, roomNumber]
        PriorityQueue<long[]> busyRooms_minHeap = new PriorityQueue<>(
            (a, b) -> Long.compare(a[0], b[0]) != 0 ? // compare by end time
                                Long.compare(a[0], b[0])
                                : Long.compare(a[1], b[1]) // tie → room number
        );
        
        // Initialize free rooms
        for(int i=0; i<n; i++) {
            freeRooms_minHeap.offer(i);
        }

        int[] meetingCount = new int[n];

        for(int[] meeting : meetings) {
            int start = meeting[0];
            int end = meeting[1]; 

            // Free rooms that have completed meetings
            while (!busyRooms_minHeap.isEmpty() 
                        && busyRooms_minHeap.peek()[0] <= start) {
                // add room number
                freeRooms_minHeap.offer((int) busyRooms_minHeap.poll()[1]); 
            }

            if(!freeRooms_minHeap.isEmpty()) {
                int room = freeRooms_minHeap.poll();
                meetingCount[room]++;
                busyRooms_minHeap.offer(new long[]{end, room});
            }
            else { // delay when no room available
                long[] earliest = busyRooms_minHeap.poll();
                long duration = end - start;
                long newEnd = earliest[0] + duration;
                int room = (int) earliest[1];
                meetingCount[room]++;
                busyRooms_minHeap.offer(new long[]{newEnd, room});
            }
        }

        // Find room with maximum meetings
        int result = 0;
        for(int i=1; i<n; i++) {
            if(meetingCount[i] > meetingCount[result]) {
                result = i;
            }
        }
        return result;
    }

}
