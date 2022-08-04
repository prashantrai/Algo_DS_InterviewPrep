package Stripe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StoreHours {
	public static void main(String[] args) {
		// 1st part
		testComputePenalty();
		// 2nd part
		testFindBestClosingTime();
		// 3rd part
		List<Integer> res1 = get_best_closing_times("BEGIN Y Y END \nBEGIN N N END");
		System.out.println("Expected: [2, 0], Actual: " + res1);

		List<Integer> res2 = get_best_closing_times("BEGIN BEGIN \nBEGIN N N BEGIN Y Y\n END N N END");
		System.out.println("Expected: [2], Actual: " + res2);
		
		
		//4th part 
		//- Logs are coming in stream one by one in a batch with each stream of data i.e. not passed at once
		
	}
	
	// 1st Part
	public static int compute_penalty(String logs, int time) {
		if (logs == null || logs.length() == 0 || time < 0) {
			return 0;
		}
		
		logs = " " + logs + " ";
		int penalty = 0;
		int closingHour = time * 2;
		char[] arr = logs.toCharArray();

		for (int i = 0; i < arr.length; i++) {
			if (i <= closingHour && arr[i] == 'N')
				penalty++;
			if (i >= closingHour && arr[i] == 'Y')
				penalty++;
		}
		return penalty;
	}
	
	// 2nd Part - Working
	static List<Integer> get_best_closing_times(String logs) {
		List<Integer> res = new ArrayList<>();
		logs = logs.replace("\n", "");	

		List<String> list = getValidLogList(logs);

		for (String log : list) {
			int time = find_best_closing_time(log);
			res.add(time);
		}
		return res;
	}

	// 3rd Part - Working
	/*
	 * ## Examples get_best_closing_times("BEGIN Y Y END \nBEGIN N N END") should
	 * return an array: [2, 0]
	 * get_best_closing_times("BEGIN BEGIN \nBEGIN N N BEGIN Y Y\n END N N END")
	 * should return an array: [2]
	 */

	static List<String> getValidLogList(String logs) {

		List<String> list = new ArrayList<>();
		StringBuilder sb = new StringBuilder();

		String[] arr = logs.split(" ");
		boolean seenBegin = false;

		for (String s : arr) {
			
			if (sb.length() == 0 && "BEGIN".equals(s)) {
				seenBegin = true;
				continue;
			}
			
			if (sb.length() > 0 && "BEGIN".equals(s)) {
				seenBegin = true;
				sb.setLength(0);
				continue;
			}

			if(seenBegin) {
				if (!"END".equals(s)) {
					sb.append(" ");
					sb.append(s);
				} else if ("END".equals(s)) {
					sb.deleteCharAt(0);
					list.add(sb.toString());
					seenBegin = false;
					sb.setLength(0);
				}
			}
		}

		return list;

	}

	static int find_best_closing_time(String logs) {
		if (logs == null || logs.length() == 0) {
			return 0;
		}

		int min = Integer.MAX_VALUE;
		int bestHour = 0;
		int totalLogHours = logs.length() + 2;

		for (int i = 0; i < totalLogHours; i++) {
			int penalty = compute_penalty(logs, i);
			if (penalty < min) {
				min = penalty;
				bestHour = i;
			}
		}

		return bestHour;
	}

	

	public static <T> void assertEquals(T expected, T actual) {
		if (expected == null && actual == null || actual != null && actual.equals(expected)) {
			System.out.println("PASSED");
		} else {
			throw new AssertionError("Expected:\n  " + expected + "\nActual:\n  " + actual + "\n");
		}
	}

	// You can't autobox int[] to Integer[]
	// So we just provide the int[] implementation because that's all we need,
	// really
	public static void assertArrayEquals(int[] expected, int[] actual) {
		if (!Arrays.equals(actual, expected)) {
			throw new AssertionError(
					"Expected:\n  " + Arrays.toString(expected) + "\nActual:\n  " + Arrays.toString(actual) + "\n");
		} else {
			System.out.println("PASSED");
		}
	}

	public static void testComputePenalty() {
		//
		System.out.println("compute_penalty");
		assertEquals(3, compute_penalty("Y Y Y N N N N", 0));
		assertEquals(4, compute_penalty("Y Y Y N N N N", 7));
		assertEquals(0, compute_penalty("Y Y Y N N N N", 3));
		assertEquals(0, compute_penalty("", 0));
		assertEquals(1, compute_penalty("Y N Y N N N N", 3));
	}

	public static void testFindBestClosingTime() {
		System.out.println("find_best_closing_time");
		assertEquals(3, find_best_closing_time("Y Y Y N N N N"));
		assertEquals(0, find_best_closing_time(""));
		assertEquals(1, find_best_closing_time("Y"));
		assertEquals(0, find_best_closing_time("N N N N"));
		assertEquals(4, find_best_closing_time("Y Y Y Y"));
		assertEquals(5, find_best_closing_time("N Y Y Y Y N N N Y N N Y Y N N N N Y Y N N Y N N N"));
		assertEquals(0, find_best_closing_time("N N N N N Y Y Y N N N N Y Y Y N N N Y N Y Y N Y N"));
		assertEquals(25, find_best_closing_time("Y Y N N N Y Y N Y Y N N N Y Y N N Y Y Y N Y N Y Y"));
	}

}

/**
Given a String like "Y N Y Y N" where Y denotes if there were any customers at a given hour 
and N denotes no customers at a given hour. There is a penalty for a hour where it is open 
and you have a N (i.e. no customer), and if you close at a certain hour and it turns out 
the hour had customers i.e. Y. 

 hour   :  1  2  3  4  5
 log    :  Y  N  Y  Y  N
close_at:  1  2  3  4  5   // close at 5th hour, no penalty
					   ^

 hour   :  1  2  3  4  5
 log    :  Y  N  Y  Y  N
close_at:  1  2  3  4  5   // close at 4th hour, there was a Y and you closed, so you would have a penalty
					^

					   
So, for the above string if you had closed at the 5th hour you would have no penalty. 
However, if you closed at the 4th hour there was a Y and you closed, so you would have a penalty. 
Given a String and an hour, calculate the penalty.

Second part: Given a string, return the best hour to close for that string i.e., with minimum penalty. 
It does not matter which hour you return. You can return any of the hours with minimum penalty.

*/