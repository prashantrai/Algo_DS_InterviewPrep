package Stripe;

import java.util.HashMap;
import java.util.Map;

public class StoreHours {

	public static void main(String[] args) {
		//System.out.println(calculatePenalty("      ", 1));
		
		//String s = "    ";
		//System.out.println(s.isBlank()); //isBlank is available from JDK 11 and after
		
		int penalty = 0;
		int penalty2 = 0;
		
		// case 1
		String storeLogs = "Y N Y Y N";
		int closeHour = 5;
		penalty = calculatePenalty(storeLogs, closeHour);
		//assertEquals(0, calculatePenalty("Y N Y Y N", 5));
		System.out.println("Expected: 2 (should be 1), Actual: " + penalty);
		penalty2 = calculatePenalty2(storeLogs, closeHour);
		System.out.println("penalty2:: Expected: 2, Actual: " + penalty2);
		
		System.out.println("\n");
		
		// case 2
		storeLogs = "Y N Y Y N";
		closeHour = 4;
		penalty = calculatePenalty(storeLogs, closeHour);
		//assertEquals(1, calculatePenalty("Y N Y Y N", 4));
		System.out.println("Expected: 1 (should be 2), Actual: " + penalty);
		penalty2 = calculatePenalty2(storeLogs, closeHour);
		System.out.println("penalty2:: Expected: 1, Actual: " + penalty2);
		
		System.out.println("\n");
		
		// case 3
		storeLogs = "Y N Y Y N";
		closeHour = 2;  // expected output = 2, coz after closing at 2nd hour there are 2 Y after closing 
		penalty = calculatePenalty(storeLogs, closeHour);
		System.out.println("Expected: 3 (should be 2), Actual: " + penalty);
		penalty2 = calculatePenalty2(storeLogs, closeHour);
		System.out.println("penalty2:: Expected: 3, Actual: " + penalty2);
		
		System.out.println("\n");
		
		// case 4
		storeLogs = "Y N Y Y N";
		closeHour = 7;  // expected output = 2, because 7 > openHours and there 2 N before closing hour  
		penalty = calculatePenalty(storeLogs, closeHour);
		System.out.println("Expected: 2, Actual: " + penalty);
		penalty2 = calculatePenalty2(storeLogs, closeHour);
		System.out.println("penalty2:: Expected: 2, Actual: " + penalty2);
		
		System.out.println("\n");
		
		// case 5
		storeLogs = "N N N N Y";
		closeHour = 1;  // expected output = 1, as there is one Y after closing hour 
		penalty = calculatePenalty(storeLogs, closeHour);
		System.out.println("Expected: 1, Actual: " + penalty);
		penalty2 = calculatePenalty2(storeLogs, closeHour);
		System.out.println("penalty2:: Expected: 1, Actual: " + penalty2);
		
		System.out.println("\n");
		
		// case 6
		storeLogs = "N Y Y Y Y";
		closeHour = 1;  // expected output = 4, because there are 4 Y after closing time  
		penalty = calculatePenalty(storeLogs, closeHour);
		System.out.println("Expected: 4, Actual: " + penalty);
		penalty2 = calculatePenalty2(storeLogs, closeHour);
		System.out.println("penalty2:: Expected: 4, Actual: " + penalty2);
		
		System.out.println("\n");
		
		// case 7 - when invalid input
		storeLogs = " ";
		closeHour = 0;  
		penalty = calculatePenalty(storeLogs, closeHour);
		System.out.println("Expected: 0, Actual: " + penalty);
		penalty2 = calculatePenalty2(storeLogs, closeHour);
		System.out.println("penalty2:: Expected: 0, Actual: " + penalty2);
		
		System.out.println("\n----Best closng hour---");
		
		int bestHour = 0;
		
		// Best closng hour
		storeLogs = "Y Y N N";
		bestHour = getBestHour(storeLogs);
		System.out.println(">>Expected: 3, Actual: " + bestHour);
//		System.out.println("calculateClosingTime:: Expected: 4 (should be 3), Actual: " + calculateClosingTime(storeLogs));
		
		storeLogs = "Y Y Y N N";
		bestHour = getBestHour(storeLogs);
		System.out.println(">>Expected: 4, Actual: " + bestHour);
//		System.out.println("calculateClosingTime:: Expected: 4, Actual: " + calculateClosingTime(storeLogs));
		
		storeLogs = "N Y Y N N";
		bestHour = getBestHour(storeLogs);
		System.out.println(">>Expected: 4, Actual: " + bestHour);
//		System.out.println("calculateClosingTime:: Expected: 4, Actual: " + calculateClosingTime(storeLogs));
		
		storeLogs = "Y Y Y Y N";
		bestHour = getBestHour(storeLogs);
		System.out.println(">>Expected: 5, Actual: " + bestHour);
//		System.out.println("calculateClosingTime:: Expected: 5, Actual: " + calculateClosingTime(storeLogs));
		
		storeLogs = "N N N N Y";
		bestHour = getBestHour(storeLogs);
		System.out.println(">>Expected: 1, Actual: " + bestHour);
//		System.out.println("calculateClosingTime:: Expected: 1, Actual: " + calculateClosingTime(storeLogs));
		
		storeLogs = "N N N N N";
		bestHour = getBestHour(storeLogs);
		System.out.println(">>Expected: 1, Actual: " + bestHour);
//		System.out.println("calculateClosingTime:: Expected: 1, Actual: " + calculateClosingTime(storeLogs));
		
		storeLogs = "Y Y Y Y Y";
		bestHour = getBestHour(storeLogs);
		System.out.println(">>Expected: 5, Actual: " + bestHour);
//		System.out.println("calculateClosingTime:: Expected: 5, Actual: " + calculateClosingTime(storeLogs));
		
		storeLogs = " ";
		bestHour = getBestHour(storeLogs);
		System.out.println(">>Expected: 0, Actual: " + bestHour);
//		System.out.println("calculateClosingTime:: Expected: 0, Actual: " + calculateClosingTime(storeLogs));
		
		//THIRD PART: Given an incomplete log with possibly invalid data , find the best closing time for all logs.
		
		
		/* 3rd part - derived from prev question and not sure if they will ask like this
		   
		   Given a string with multiple server statuses nested together, determine the best time to take 
		   the server offline" example strings were like "BEGIN BEGIN 0 0 1 END BEGIN 0 1 END", 
		   but only for the inner-most BEGIN/END combinations.
		   
		   For the sake of Store hour question we can update the input like below
		   Case 1: "OPEN OPEN Y Y N CLOSE OPEN Y N CLOSE"
		   
		   Approach: we can split the string by space and then use a STACK to push the value till we get CLOSE.
		   When we hit the CLOSE we will start popping the value and until we hit OPEN, prepare the store logs string 
		   (use string builder and reverse it) and then pass the the string
		   to method getBestHour to get the best closing hour with min penalty
		*/
		String nestedServerLogs = "";
		Map<String, Integer> bestHoursMap = null;
		
		nestedServerLogs = "OPEN OPEN Y Y N CLOSE OPEN Y N CLOSE";
		bestHoursMap = getBestHourForNestedStoreLogs(nestedServerLogs);
		System.out.println("bestHoursMap: " + bestHoursMap);
		
		bestHoursMap.clear();
		
		System.out.println("\n-- Invalid input scenario --");
		nestedServerLogs = " ";
		bestHoursMap = getBestHourForNestedStoreLogs(nestedServerLogs);
		System.out.println("bestHoursMap: " + bestHoursMap);
	}
	
	// part 1
	public static int calculatePenalty(String storeLogs, int closeHour) {
		//sanity check
		if(!isValidInput(storeLogs, closeHour)) { // ask if ZERO is a valid hour
			return 0; 
		}
				
		storeLogs = storeLogs.trim();
		String[] logsArr = storeLogs.split(" ");
		int len = logsArr.length;
		int penalty = 0;
		//closeHour = closeHour-1;
		
		/*
		 * Scenario 1: 
		During interview confirm, if the closeHour has value 'N' and then NO penalty
		use below code otherwise use scenario 2 code 
		*/
		for(int i=0; i<len; i++) {
			if(i+1 < closeHour && "N".equals(logsArr[i])) 
				penalty = penalty + 1; //penalty++;
			else if(i+1 >= closeHour && "Y".equals(logsArr[i]))
				penalty = penalty + 1; //penalty++;
		}
		
		/*
		 * Scenario 2: 
		During interview confirm, if the closeHour has value 'N' and then ADD penalty
		use below code
		
		 PS: Only change between schenario 1 and 2 is about condition between i+1 and closeHour
		*/
		/*for(int i=0; i<len; i++) {
			if(i+1 <= closeHour && "N".equals(logsArr[i])) 
				penalty = penalty + 1; //penalty++;
			else if(i+1 > closeHour && "Y".equals(logsArr[i]))
				penalty = penalty + 1; //penalty++;
		}*/
			
		
		return penalty;
	}
	
	
	public static int getBestHour(String storeLogs) {
		
		if(!isValidInput(storeLogs, 0)) { // ask if ZERO is a valid hour
			return 0; 
		}
		
		Integer min = Integer.MAX_VALUE;
		storeLogs = storeLogs.trim();
		String[] logsArr = storeLogs.split(" ");
		int len = logsArr.length;
		int bestHour = 0;
		
		for (int i = 0; i < len; i++) {
			int penalty = calculatePenalty(storeLogs, i+1); 

			if (penalty < min) {
				min = penalty;
				bestHour = i+1;
			}
		}
		
		// from leetcode
		/*for (int i = 0; i <= storeLogs.length(); i++) {
			int penalty = calculatePenalty(storeLogs, i); 

			if (penalty < min) {
				min = penalty;
				bestHour = i;
			}
		}*/

		return bestHour;
		
	}
	
	/* part 3 - below implementation is driven from statement (look in main method for statement) 
	
	 Actual THIRD PART would be: 
	 	Given an incomplete log with possibly invalid data , find the best closing time for all logs.
	 * 
	 */
	public static Map<String, Integer> getBestHourForNestedStoreLogs(String nestedServerLogs) {
		
		Map<String, Integer> res = new HashMap<>();
		
		if(!isValidInput(nestedServerLogs, 0)) { // ask if ZERO is a valid hour
			//return res;
			throw new IllegalArgumentException("Invalid input: nestedServerLogs can't empty or null");
		}
		
		String[] logsArr = nestedServerLogs.split(" ");
		//Stack<String> stk = new Stack<>();
		StringBuilder sb = new StringBuilder();
		
		// OPEN OPEN Y Y N CLOSE OPEN 0 1 CLOSE
		for(String s : logsArr) {
			if("OPEN".equals(s)) 
				continue;
			
			if(sb.length() > 0 && !"CLOSE".equals(s))
				sb.append(" ");
			
			if(!"CLOSE".equals(s))
				sb.append(s);
			
			if("CLOSE".equals(s)) {
				int bestHour = getBestHour(sb.toString());
				res.put(sb.toString(), bestHour);
				sb.setLength(0);
			}
			
		}
		
		return res;
	}
	
	// validate input param
	public static boolean isValidInput(String storeLogs, int closeHour) {
		if(storeLogs == null || storeLogs.trim().isEmpty() || closeHour < 0) { // ask if ZERO is a valid hour
			return false; 
		}
		return true;
	}
		
	public static <T> void assertEquals(T expected, T actual) {
		if (expected == null && actual == null || actual != null && actual.equals(expected)) {
			System.out.println("PASSED:: Expected:" + expected + ", Actual: " + actual);
		} else {
			throw new AssertionError("Expected:\n  " + expected + "\nActual:\n  " + actual + "\n");
	    }
	}
	
	
	// -- From Leet code 
	
	// from leetcode : https://leetcode.com/discuss/interview-experience/1574697/stripe-phone-screen-nov-2021-reject
	public static int calculatePenalty2(String storeLog, int closeIndex) {
		int penalty = 0;
		storeLog = storeLog.replace(" ", "");
		int len = storeLog.length();

			if (storeLog.length() == 0) {
				return 0;
			}

			if (closeIndex == 0) {
				for (int i = 0; i < len; i++) {
					char ch = storeLog.charAt(i);
					if (ch == 'Y') {
						penalty = penalty + 1;
					}
				}
			} else {

				for (int i = 0; i < len; i++) {

					char ch = storeLog.charAt(i);
					if (i + 1 <= closeIndex) {
						if (ch == 'N') {
							penalty = penalty + 1;
						}
					} else {

						if (ch == 'Y') {
							penalty = penalty + 1;
						}
					}
				}
			} // end of else

			return penalty;
		}
	
	// from leetcode
	public static int calculateClosingTime(String storeLog) {
	//public static int getBestHour(String storeLog) {
		Integer min = Integer.MAX_VALUE;
		int bestCloseTime = 0;
		// Y Y N N ==>4
		if (storeLog.isEmpty() || storeLog.equals(" ")) {
			return 0;
		}

		for (int i = 0; i <= storeLog.length(); i++) {
			int penalty = calculatePenalty2(storeLog, i); 

			if (penalty < min) {
				min = penalty;
				bestCloseTime = i;
			}
		}

		return bestCloseTime;
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