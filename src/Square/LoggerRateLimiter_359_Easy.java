package Square;

import java.util.HashMap;
import java.util.Map;

public class LoggerRateLimiter_359_Easy {

	public static void main(String[] args) {

		Logger logger = new Logger();

		// logging string "foo" at timestamp 1
		boolean isPrinted = logger.shouldPrintMessage(1, "foo"); // returns true;
		System.out.println("Expected: true, Actual: " + isPrinted);

		// logging string "bar" at timestamp 2
		isPrinted = logger.shouldPrintMessage(2, "bar"); // returns true;
		System.out.println("Expected: true, Actual: " + isPrinted);

		// logging string "foo" at timestamp 3
		isPrinted = logger.shouldPrintMessage(3, "foo"); // returns false;
		System.out.println("Expected: false, Actual: " + isPrinted);

		// logging string "bar" at timestamp 8
		isPrinted = logger.shouldPrintMessage(8, "bar"); // returns false;
		System.out.println("Expected: false, Actual: " + isPrinted);

		// logging string "foo" at timestamp 10
		isPrinted = logger.shouldPrintMessage(10, "foo"); // returns false;
		System.out.println("Expected: false, Actual: " + isPrinted);

		// logging string "foo" at timestamp 11
		isPrinted = logger.shouldPrintMessage(11, "foo"); // returns true;
		System.out.println("Expected: true, Actual: " + isPrinted);
	}

	// https://leetcode.com/problems/logger-rate-limiter/

	/*
	 * Question:: 359-Logger Rate Limiter-Easy Design a logger system that receive
	 * stream of messages along with its timestamps, each message should be printed
	 * if and only if it is not printed in the last 10 seconds.
	 * 
	 * Given a message and a timestamp (in seconds granularity), return true if the
	 * message should be printed in the given timestamp, otherwise returns false.
	 * 
	 * It is possible that several messages arrive roughly at the same time.
	 */

	/*
	 * Complexity::
	 * 
	 * Time Complexity: O(1). The lookup and update of the hashtable takes a
	 * constant time.
	 * 
	 * Space Complexity: O(M) where M is the size of all incoming messages. Over the
	 * time, the hashmap would have an entry for each unique message that has
	 * appeared.
	 */

	static class Logger {

		Map<String, Integer> map;

		/** Initialize your data structure here. */
		public Logger() {
			map = new HashMap<>();
		}

		/**
		 * Returns true if the message should be printed in the given timestamp,
		 * otherwise returns false. If this method returns false, the message will not
		 * be printed. The timestamp is in seconds granularity.
		 */
		public boolean shouldPrintMessage(int timestamp, String message) {

			if (!map.containsKey(message)) {
				map.put(message, timestamp);
				return true;
			}

			Integer oldTimestamp = map.get(message);
			if (timestamp - oldTimestamp >= 10) {
				map.put(message, timestamp);
				return true;
			}

			return false;
		}
	}

	/**
	 * Your Logger object will be instantiated and called as such: Logger obj = new
	 * Logger(); boolean param_1 = obj.shouldPrintMessage(timestamp,message);
	 */

}
