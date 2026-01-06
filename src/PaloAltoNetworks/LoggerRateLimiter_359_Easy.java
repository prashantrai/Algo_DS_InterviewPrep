package PaloAltoNetworks;

import java.util.HashMap;
import java.util.Map;

public class LoggerRateLimiter_359_Easy {

	public static void main(String[] args) {
		Logger logger = new Logger();
		logger.shouldPrintMessage(1, "foo");  // return true, next allowed timestamp for "foo" is 1 + 10 = 11
		logger.shouldPrintMessage(2, "bar");  // return true, next allowed timestamp for "bar" is 2 + 10 = 12
		logger.shouldPrintMessage(3, "foo");  // 3 < 11, return false
		logger.shouldPrintMessage(8, "bar");  // 8 < 12, return false
		logger.shouldPrintMessage(10, "foo"); // 10 < 11, return false
		logger.shouldPrintMessage(11, "foo"); // 11 >= 11, return true, next allowed timestamp for "foo" is 11 + 10 = 21
	}
	
	/* 
    Time: O(1)
    Space: O(M), where M is the no of unique messages
    */

	static class Logger {
	    Map<String, Integer> msgDict;
	
	    public Logger() {
	        msgDict = new HashMap<>();
	    }
	    
	    public boolean shouldPrintMessage(int timestamp, String message) {
	        if(!msgDict.containsKey(message)) {
	            msgDict.put(message, timestamp);
	            return true;
	        }
	
	        int oldTimestamp = msgDict.get(message);
	        if(timestamp - oldTimestamp >= 10) {
	            msgDict.put(message, timestamp);
	            return true;
	        }
	
	        return false;
	
	    }
	}
	
	/*
    Potential Issues with above solution:
    Memory leak: The HashMap keeps growing indefinitely as new 
    messages are added, never removing old entries
    
    Memory inefficiency: Messages that haven't been seen in 10+ 
    seconds are still stored but serve no purpose
    
    Below is Alternative Approach with Lazy Cleanup Strategy

    Time: O(1)
    Space: O(M), only becomes an issue with very large numbers of 
    unique messages over long periods.
    */

	static class Logger2 {
	    private Map<String, Integer> msgDict;
	    private int lastCleanupTime = 0;
	    private static final int CLEANUP_INTERVAL = 100; // Cleanup every 100 calls
	    
	    public Logger2() {
	        msgDict = new HashMap<>();
	    }
	    
	    public boolean shouldPrintMessage(int timestamp, String message) {
	        // Periodic cleanup to remove old messages
	        if (timestamp - lastCleanupTime >= CLEANUP_INTERVAL) {
	            cleanupOldMessages(timestamp);
	            lastCleanupTime = timestamp;
	        }
	        
	        if (!msgDict.containsKey(message)) {
	            msgDict.put(message, timestamp);
	            return true;
	        }
	        
	        int oldTimestamp = msgDict.get(message);
	        if (timestamp - oldTimestamp >= 10) {
	            msgDict.put(message, timestamp);
	            return true;
	        }
	        
	        return false;
	    }
	    
	    private void cleanupOldMessages(int currentTime) {
	        // Remove messages that are older than 10 seconds
	        msgDict.entrySet().removeIf(entry -> currentTime - entry.getValue() >= 10);
	    }
	}

}
