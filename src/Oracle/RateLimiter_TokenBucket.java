package Oracle;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RateLimiter_TokenBucket {

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
	
	
	/*
	The sliding window solution stores every request timestamp. While it is accurate, 
	it uses O(maxRequests) memory per user.

	A Token Bucket is more memory efficient.
	
	Instead of storing every request, each user stores only:
	- Current number of tokens
	- Last refill timestamp
	
	The bucket has a fixed capacity equal to maxRequests.
	Initially, the bucket is full.
	
	Every second, tokens are added back at a fixed rate until the bucket is full again.
	Whenever a request arrives:
	
	- Refill the bucket based on elapsed time.
	- If at least one token is available:
		Consume one token.
		Allow the request.
	- Otherwise reject it.
	
	This requires only O(1) memory per user.
	 */
	
	/* Interview Script: 
	 Instead of storing every request timestamp, I'll use a Token Bucket. 
	 Each user has a bucket with a fixed capacity equal to the maximum allowed requests. 
	 Tokens are replenished over time at a constant rate, and every request consumes 
	 one token. 
	 If a token is available, the request is allowed; otherwise, it's rejected. 
	 This approach processes each request in O(1) time while using only O(1) memory per user, 
	 making it much more scalable for millions of users.
	 * */


	// Time: O(1)
	// Space: O(N), for N user, Only one bucket object is stored per user.
	
	// *** Solution Starts Here ***
	
	final int capacity;
	final double refillRate; // tokens added per second

	final ConcurrentMap<String, Bucket> buckets;

	static class Bucket {
		double tokens;
		long lastRefillTime;

		Bucket(double tokens, long lastRefillTime) {
			this.tokens = tokens;
			this.lastRefillTime = lastRefillTime;
		}
	}

	/* How's refill rate calculated? Why 10 / 5 = 2 tokens/second?
	Suppose the requirement is: 10 requests every 5 seconds
	We want the bucket to recover 10 tokens over 5 seconds, so:
	
	refillRate = maxRequests / windowSeconds
	           = 10 / 5 = 2 tokens/second
	
	This ensures that after 5 seconds of no traffic, the bucket is completely full again.
	 * */
	
	public RateLimiter_TokenBucket(int maxRequests, int windowSeconds) {
	        this.capacity = maxRequests;
	        this.refillRate = (double) maxRequests / windowSeconds;
	        this.buckets = new ConcurrentHashMap<>();
	    }

	public boolean allowRequest(String userId, long timestamp) {

		Bucket bucket = buckets.computeIfAbsent(userId, k -> new Bucket(capacity, timestamp));

		synchronized (bucket) {

			// Refill tokens based on elapsed time
			long elapsed = timestamp - bucket.lastRefillTime;

			if (elapsed > 0) {
				bucket.tokens = Math.min(capacity, bucket.tokens + elapsed * refillRate);

				bucket.lastRefillTime = timestamp;
			}

			// No token available
			if (bucket.tokens < 1.0) {
				return false;
			}

			// Consume one token
			bucket.tokens--;

			return true;
		}
	}

}
