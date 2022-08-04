package Stripe;

import java.util.ArrayList;
import java.util.List;

public class AnalyzeServerProcessUptimeLog {

	public static void main(String[] args) {

//		int res1 = compute_penalty("0 0 1 0", 0);// should return 3
//		int res2 = compute_penalty("0 0 1 0", 4);// should return 1
//		int res2 = compute_penalty("0 0 1 0", 4);// should return 1
//		
//		System.out.println("res1: "+res1);
//		System.out.println("res2: "+res2);
		testComputePenalty();
		testFindBestClosingTime();
		
		// 3rd part
		List<Integer> res1 = get_best_closing_times("BEGIN 0 0 END \nBEGIN 1 1 END");
		System.out.println("Expected: [2, 0], Actual: " + res1);

		List<Integer> res2 = get_best_closing_times("BEGIN BEGIN \\nBEGIN 1 1 BEGIN 0 0\\n END 1 1 END");
		System.out.println("Expected: [2], Actual: " + res2);
		
		
		//4th part 
		//- Logs are coming in stream one by one in a batch with each stream of data i.e. not passed at once
		
	}
	
	public static <T> void assertEquals(T expected, T actual) {
	    if (expected == null && actual == null || actual != null && actual.equals(expected)) {
	      System.out.println("PASSED");
	    } else {
	      throw new AssertionError("Expected:\n  " + expected + "\nActual:\n  " + actual + "\n");
	    }
	  }
	  
	public static void testComputePenalty() {
		//
		System.out.println("compute_penalty");
		assertEquals(3, compute_penalty("0 0 0 1 1 1 1", 0));
		assertEquals(4, compute_penalty("0 0 0 1 1 1 1", 7));
		
		assertEquals(0, compute_penalty("0 0 0 1 1 1 1", 3));
		assertEquals(1, compute_penalty("0 1 0 1 1 1 1", 3));
		
		assertEquals(2, compute_penalty("0 1 0 1 1 0 1", 3));
		
		assertEquals(0, compute_penalty("", 0));
	}
	public static void testFindBestClosingTime() {
		System.out.println("find_best_closing_time");
		assertEquals(3, find_best_closing_time("0 0 0 1 1 1 1"));
		assertEquals(0, find_best_closing_time(""));
		assertEquals(1, find_best_closing_time("0"));
		assertEquals(0, find_best_closing_time("1 1 1 1"));
		assertEquals(4, find_best_closing_time("0 0 0 0"));
		assertEquals(5, find_best_closing_time("1 0 0 0 0 1 1 1 0 1 1 0 0 1 1 1 1 0 0 1 1 0 1 1 1"));
		assertEquals(0, find_best_closing_time("1 1 1 1 1 0 0 0 1 1 1 1 0 0 0 1 1 1 0 1 0 0 1 0 1"));
		assertEquals(25, find_best_closing_time("0 0 1 1 1 0 0 1 0 0 1 1 1 0 0 1 1 0 0 0 1 0 1 0 0"));
	}

	// 1st Part
	public static int compute_penalty(String logs, int time) {
		if (logs == null || logs.length() == 0 || time < 0) {
			return 0;
		}
		
		logs = " " + logs + " ";
		int penalty = 0;
		int removingHour = time * 2;
		char[] arr = logs.toCharArray();

		for (int i = 0; i < arr.length; i++) {
			if (i <= removingHour && arr[i] == '1')
				penalty++;
			if (i >= removingHour && arr[i] == '0')
				penalty++;
		}
		return penalty;
	}
	
	// 2nd Part - Working
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
	
	// 3rd Part - Working
	/* Given invalid log list with nested logs
	 *  
	 ## Examples get_best_closing_times("BEGIN 0 0 END \nBEGIN 1 1 END") should
	 * return an array: [2, 0]
	 * get_best_closing_times("BEGIN BEGIN \nBEGIN 1 1 BEGIN 0 0\n END 1 1 END")
	 * should return an array: [2]
	 */
	static List<Integer> get_best_closing_times(String logs) {
		List<Integer> res = new ArrayList<>();
		logs = logs.replace("\n", "");	// remove \n

		List<String> list = getValidLogList(logs); // find the valid log i.e. between a valid BEGIN and END

		for (String log : list) {
			int time = find_best_closing_time(log);
			res.add(time);
		}
		return res;
	}
	
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
	
}



/*
 Throughout this interview, we'll write code to analyze a simple server process uptime log.
  These logs are
much simplified, and are just strings of space separated 0's and 1's. The log is a string of binary
digits (e.g. "0 0 1 0"). Each digit corresponds to 1 hour of the server running:
 
"1" = <crashed>, "down" // server process crashed during the hour
"0" = <didn't crash>, "up" // server process did not crash during the hour
 
 EXAMPLE: A server with log "0 0 1 0" ran for 4 hours and its process crashed during hour #3
 
   hour: |1|2|3|4|
   log : |0|0|1|0|
              ^
              |
             down during hour #3
We can *permanently remove* a server at the beginning of any hour during its operation. A server
is on the network until it is removed. Note that a server stays POWERED ON after removal, it's
just not on the network.
 
  EXAMPLE: Remove a server with log "0 0 1 0"
 
    hour :  | 1 | 2 | 3 | 4 |
    log  :  | 0 | 0 | 1 | 0 |
remove_at:  0   1   2   3   4   // remove_at being `x` means "server removed before hour `x+1`"
            ^               ^
            |               |
     before hour #1         after hour #4
 
We'd like to understand the best times to remove a server. So let's introduce an aggregate metric
called a "penalty" for removing a server at a bad time.
 
We define our penalty like this:
+1 penalty for each DOWN hour when a server is on the network
+1 penalty for each UP hour after a server has been removed
 
Further Examples:
 
EXAMPLE:
 
   hour :   1 2 3 4     // total penalty = 3  (3 server-up hours after remove)
   log  :   0 0 1 0
           ^
           |
         remove_at = 0
 
   hour :   1 2 3 4     // total penalty = 1  (1 server-down hour before remove)
   log  :   0 0 1 0
                   ^
                   |
                 remove_at = 4
 
Note that for a server log of length `n` hours, the remove_at variable can range
from 0, meaning "before the first hour" to n, meaning "after the final hour".
 
1a)
Write a function: compute_penalty, that computes the total penalty, given
 
a server log (as a string) AND
a time at which we removed the server from the network (call that  variable remove_at)
 
In addition to writing this function, you should use tests to demonstrate that it's correct.
 
## Examples
 
compute_penalty("0 0 1 0", 0) should return 3
compute_penalty("0 0 1 0", 4) should return 1


ABOVE QUESTION HAS BELOW FOLLOWUPS: 

1a) "Given a string of server-statuses ("1 0 0 1") and a time that the server was taken offline, 
determine how many statuses the server was off by. 0 indicates the server is running, 1 indicates 
the server is offline"

1b) "Given the previous, determine when the best time would have been to take the server offline. "

2a) "Ok given a string with multiple server statuses nested together, 
determine the best time to take the server offline" example strings were like 
"BEGIN BEGIN 0 0 1 END BEGIN 0 1 END", but only for the inner-most BEGIN/END combinations."


 * */
 