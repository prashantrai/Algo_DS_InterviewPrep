package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class DoorDash_PhoneScreen {

	public static void main(String[] args) {

		List<int[]> input = new ArrayList<>();
		input.add(new int[] { 3, 20 });
		input.add(new int[] { -2, 0 });
		input.add(new int[] { 0, 2 });

		input.add(new int[] { 16, 17 });
		input.add(new int[] { 19, 23 });
		input.add(new int[] { 30, 40 });
		input.add(new int[] { 27, 33 });
		
//		Collections.sort(input, (a, b)->(a[0] - b[0]));
//		
//		for(int[] i : input) 
//			System.out.println("input:: " + Arrays.toString(i));

		// meetings_list: [3,20], [-2, 0], [0,2], [16,17], [19,23], [30,40], [27, 33]
		
		// Expected: [-5, -2], [23,27]
		int start_time= -5;
		int end_time= 50;
		int min_duration= 2;
		List<int[]> res = freeTime(input, start_time, end_time, min_duration);
		print(1, "[-5, -2], [23,27]", res);

		// Expected: Empty list
		start_time = -5;
		end_time = 50;
		min_duration = 6;
		res = freeTime(input, start_time, end_time, min_duration);
		print(2, "Empty list", res);
		
		// Expected: Empty list
		start_time = 24;
		end_time = 27;
		min_duration = 2;
		res = freeTime(input, start_time, end_time, min_duration);
		print(3, "Empty list", res);

		// Expected: [-5, -2], [23,27]
		start_time = -5;
		end_time = 27;
		min_duration = 2;
		res = freeTime(input, start_time, end_time, min_duration);
		print(4, "[-5, -2], [23,27]", res);
		
	}

	// Working as expected
	public static List<int[]> freeTime(List<int[]> meetingList, int start_time, int end_time, int min_duration) {
		List<int[]> res = new ArrayList<>();

		// sanity check
		if (meetingList == null || meetingList.isEmpty())
			return res;

		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (a[0] - b[0]));
		for (int[] interval : meetingList) {
			pq.add(interval);
		}

		int[] prev = pq.poll();

		if (start_time < prev[0] && (prev[0] - start_time) >= min_duration) {
			res.add(new int[] { start_time, prev[0] });
		}

		while (!pq.isEmpty()) {
			int[] curr = pq.poll();

			if (prev[1] <= curr[0]) {
				int strt = prev[1];
				int end = curr[0];

				if ((end - strt) >= min_duration 
						&& strt >= start_time 
						&& end <= end_time) {
					
					res.add(new int[] { strt, end });
				}
				prev = curr;
			} else {
				prev = prev[1] < curr[1] ? curr : prev; // switching max val
			}
		}

		return res;

	}
	
	private static void print(int testCaseNum, String expected, List<int[]> res) {
		System.out.print(testCaseNum + ". Expected: " + expected + ", Actual:: ");
		for (int[] arr : res) {
			System.out.print(Arrays.toString(arr) + " ");
		}
		System.out.println("");
	}
}


/*
 Question: 
 // Google Calendar, Outlook, iCal has been banned from your company! So an intrepid engineer has decided to roll their own implementation. Unfortunately one major missing feature is the ability to find out what time slots are free for a particular individual.

// Given a list of time blocks where a particular person is already booked/busy, a start and end time to search between, a minimum duration to search for, find all the blocks of time that a person is free for a potential meeting that will last the aforementioned duration.

// Given: start_time, end_time, duration, meetings_list -> suggested_meeting_times

// Let's assume we abstract the representation of times as simple integers, so a valid time is any valid integer supported by your environment. Here is an example input:

// meetings_list: [3,20], [-2, 0], [0,2], [16,17], [19,23], [30,40], [27, 33]
// start_time: -5
// end_time: 27
// min_duration: 2

// expected answer:
// free_time: [-5, -2], [23,27]
// Feel free to represent the meetings however you would like, i.e. List of Lists, Lists of Objects etc. 
 * */
