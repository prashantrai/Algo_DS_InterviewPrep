package Oracle;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RateLimiter {

	public static void main(String[] args) {

        // 5 requests per 10 seconds
        RateLimiter limiter = new RateLimiter(5, 10);

        System.out.println("=== Initial Burst ===");
        System.out.println(limiter.allowRequest("user1", 0)); // true
        System.out.println(limiter.allowRequest("user1", 0)); // true
        System.out.println(limiter.allowRequest("user1", 0)); // true
        System.out.println(limiter.allowRequest("user1", 0)); // true
        System.out.println(limiter.allowRequest("user1", 0)); // true
        System.out.println(limiter.allowRequest("user1", 0)); // false

        System.out.println();

        System.out.println("=== After 2 Seconds ===");
        System.out.println(limiter.allowRequest("user1", 2)); // true
        System.out.println(limiter.allowRequest("user1", 2)); // false

        System.out.println();

        System.out.println("=== After 6 Seconds ===");
        System.out.println(limiter.allowRequest("user1", 6)); // true
        System.out.println(limiter.allowRequest("user1", 6)); // true
        System.out.println(limiter.allowRequest("user1", 6)); // false

        System.out.println();

        System.out.println("=== Different User ===");
        System.out.println(limiter.allowRequest("user2", 0)); // true
        System.out.println(limiter.allowRequest("user2", 0)); // true
        System.out.println(limiter.allowRequest("user2", 0)); // true
        System.out.println(limiter.allowRequest("user2", 0)); // true
        System.out.println(limiter.allowRequest("user2", 0)); // true
        System.out.println(limiter.allowRequest("user2", 0)); // false
    }

	// use below to test for concurrent requests
	private static void main_concurrent(String[] args) throws Exception {

        RateLimiter limiter = new RateLimiter(5, 10);

        int threadCount = 20;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        // Ensures all threads start at approximately the same time
        CountDownLatch startLatch = new CountDownLatch(1);

        // Wait for all threads to finish
        CountDownLatch finishLatch = new CountDownLatch(threadCount);

        for (int i = 1; i <= threadCount; i++) {

            final int requestId = i;

            executor.submit(() -> {

                try {
                    startLatch.await();

                    boolean allowed =
                            limiter.allowRequest("user1", 0);

                    System.out.println(
                            "Request " + requestId +
                            " : " + allowed);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    finishLatch.countDown();
                }
            });
        }

        // Release all threads simultaneously
        startLatch.countDown();

        finishLatch.await();

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }
	
	
	// *** Solution Starts Here ***
	// Time: O(1)
	// Space: O(N × maxRequests), For N users, If a user can 
	// have at most maxRequests valid timestamps
	
	int maxRequests;
	int windowSeconds; 
	
	// userId -> request timestamps
	private final Map<String, Deque<Long>> requests;
	
	// Add for thread-safety
	// private final ConcurrentMap<String, Deque<Long>> requests; // Thread-safety

	
	public RateLimiter(int maxRequests, int windowSeconds) {
		this.maxRequests = maxRequests;
		this.windowSeconds = windowSeconds;
		this.requests = new HashMap<>();
//		this.requests = new ConcurrentHashMap<>(); // Thread-safety
	}

	boolean allowRequest(String userId, long timestamp) {
		
		// Create a queue for a new user
		// Atomically creates only one queue per user, even if 
		// multiple threads create it simultaneously.
        Deque<Long> q = requests.computeIfAbsent(userId,
                k -> new ArrayDeque<>());
		
        // Lock only this user's queue
        //synchronized (queue) { // Thread-safety

            // Remove expired timestamps
            while (!q.isEmpty()
                    && timestamp - q.peekFirst() >= windowSeconds) {
                q.pollFirst();
            }

            // Reject if limit reached
            if (q.size() >= maxRequests) {
                return false;
            }

            // Allow request
            q.offerLast(timestamp);
            return true;
        //} // Thread-safety
        
	}
	
}

/*
	Design an in-memory API rate limiter.
	
	Requirements:
	
	class RateLimiter {
		RateLimiter(int maxRequests, int windowSeconds)
	
		boolean allowRequest(String userId, long timestamp)
	}
	
	Example:
		maxRequests = 3
		window = 10 seconds
	
	Requests:
		user1 timestamp=1
		user1 timestamp=3
		user1 timestamp=5
		user1 timestamp=8
	
		allowed
	
		user1 timestamp=9
	
		rejected
	
	
	Follow-ups:
	
 *	Multiple users 
		- Already supported using: Map<String, Deque<Long>>
		Each user has it's own queue.
		
 *	Thread safety
		Possible approaches:
		- Use a ConcurrentHashMap<String, Deque<Long>>.
		- Synchronize per user's queue (e.g., synchronized (queue)), 
			so different users don't block each other.
		- Alternatively, use a lock per user (such as ReentrantLock) 
			for finer-grained concurrency.
	
 *		 implementation
		Instead of storing every timestamp:

			Store per user:
				tokens
				lastRefillTime
			
			tokens
			lastRefillTime
			
			For every request:
			
			1. Refill tokens based on elapsed time.
			2. If at least one token is available:
				consume one
				allow
			3. Otherwise reject.
			
			Advantages:
				O(1) space per user.
				Supports burst traffic up to the bucket capacity.
				Commonly used in production systems.
	
	
	
 *	Distributed rate limiter using Redis
		When multiple application servers are running:
		- Store request counts or timestamps in Redis.
		- Use Redis sorted sets (ZSET) or counters with expiration for sliding windows.
		- Execute the check, cleanup, and update atomically using a Lua script or Redis 
			transactions to prevent race conditions.
		- Since all servers share Redis, the rate limit is enforced consistently across instances.
	
	
 *	Handling millions of users
 	For very large scale:
	- Store only active users; inactive users naturally disappear as their queues become empty.
	- Remove empty queues from the map to reclaim memory.
	- Use a distributed cache like Redis when multiple application servers need a shared limit.
	- For timestamp-based sliding windows, keep only timestamps within the current window; 
		for even lower memory, consider algorithms like Token Bucket or Fixed/Sliding Window 
		Counters that use O(1) state per user instead of storing every request timestamp.
 
 
 * */
