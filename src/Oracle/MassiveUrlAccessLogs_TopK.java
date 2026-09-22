package Oracle;

import java.util.*;

public class MassiveUrlAccessLogs_TopK {

	public static void main(String[] args) {

		long hour = 60L * 60 * 1000;

		// Use a fixed current time so tests are deterministic.
		long now = 100L * hour;

		List<Log> logs = Arrays.asList(

				new Log("/home", now - 1 * hour), new Log("/home", now - 2 * hour), new Log("/home", now - 3 * hour),

				new Log("/product", now - 4 * hour), new Log("/product", now - 5 * hour),

				new Log("/search", now - 6 * hour),

				// Older than 24 hours.
				new Log("/old", now - 30 * hour), new Log("/old", now - 40 * hour), new Log("/old", now - 50 * hour),
				new Log("/old", now - 60 * hour));

		Result result = findTopK(logs, 2, now);

		System.out.println("Test 1");

		System.out.println("Expected global: [/old=4, /home=3]");
		System.out.println("Actual global:   " + result.globalTopK);

		System.out.println();

		System.out.println("Expected last 24h: [/home=3, /product=2]");
		System.out.println("Actual last 24h:   " + result.last24HoursTopK);

		System.out.println("\n--------------------\n");

		List<Log> logs2 = Arrays.asList(

				new Log("/a", now - 1 * hour), new Log("/a", now - 2 * hour),

				new Log("/b", now - 3 * hour), new Log("/b", now - 30 * hour), new Log("/b", now - 40 * hour),

				new Log("/c", now - 5 * hour));

		Result result2 = findTopK(logs2, 2, now);

		System.out.println("Test 2");

		System.out.println("Expected global: [/b=3, /a=2]");
		System.out.println("Actual global:   " + result2.globalTopK);

		System.out.println();

		System.out.println("Expected last 24h: [/a=2, /b=1] or [/a=2, /c=1]");
		System.out.println("Actual last 24h:   " + result2.last24HoursTopK);
	}

	/*
	 * Interview Explanation Before Coding
	 * 
	 * 
	 * "I'll separate the problem into frequency counting and Top K selection. I'll
	 * scan the logs once and maintain two hash maps. The first map stores the
	 * global access count for every URL. The second stores counts only for accesses
	 * whose timestamp falls within the last 24 hours relative to the supplied
	 * current time.
	 * 
	 * Once I have the two frequency maps, I don't need to sort every URL. I'll
	 * maintain a min-heap of size K. For each URL and count, I add it to the heap,
	 * and if the heap grows beyond K, I remove the smallest count. At the end, the
	 * heap contains exactly the K most frequent URLs.
	 * 
	 * This makes the counting pass O(N), and Top K extraction O(U log K), where U
	 * is the number of distinct URLs.
	 * 
	 * For this initial version, I'm assuming the distinct URL counts fit on one
	 * machine. If they don't, I'd handle that through partitioning and distributed
	 * aggregation, which I would treat as the scaling follow-up."
	 */

	/*
	 * Complxity:
	 * 
	 * Time: O(N log K), Counting all logs: O(N) Getting global Top K: O(Ug log K)
	 * Getting 24-hour Top K: O(U24 log K) So total time is: O(N + Ug log K + U24
	 * log K)
	 * 
	 * Space: O(Ug + U24 + K), The maps dominate, so overall O(Ug + U24)
	 */

	/*
	 * Step-by-Step Algorithm
	 * 
	 * Suppose: currentTime = now
	 * 
	 * First calculate: long cutoff = currentTime - 24 hours;
	 * 
	 * Step 1 Create: Map<String, Integer> globalCount Map<String, Integer>
	 * recentCount
	 * 
	 * Step 2 Process every log.
	 * 
	 * Always update: globalCount If: timestamp >= cutoff also update: recentCount
	 * 
	 * Step 3 Find global Top K using: getTopK(globalCount, k)
	 * 
	 * Step 4 Find recent Top K using: getTopK(recentCount, k)
	 * 
	 * Step 5 Inside getTopK() maintain a min-heap ordered by: smallest count first
	 * 
	 * If heap size exceeds K: poll()
	 * 
	 * At the end, reverse the heap result so that the caller receives: largest
	 * frequency first
	 */

	static class Log {
		String url;
		long timestamp;

		Log(String url, long timestamp) {
			this.url = url;
			this.timestamp = timestamp;
		}
	}

	static class UrlCount {
		String url;
		int count;

		UrlCount(String url, int count) {
			this.url = url;
			this.count = count;
		}

		@Override
		public String toString() {
			return url + "=" + count;
		}
	}

	static class Result {
		List<UrlCount> globalTopK;
		List<UrlCount> last24HoursTopK;

		Result(List<UrlCount> globalTopK, List<UrlCount> last24HoursTopK) {

			this.globalTopK = globalTopK;
			this.last24HoursTopK = last24HoursTopK;
		}
	}

	/**
	 * "I'll scan the logs once and build two frequency maps: one for all-time
	 * counts and one only for accesses inside the last 24 hours.
	 *
	 * After counting, I'll use a min-heap of size K so I don't need to sort every
	 * unique URL."
	 */

	public static Result findTopK(List<Log> logs, int k, long currentTime) {

		if (logs == null || logs.isEmpty() || k <= 0) {
			return new Result(new ArrayList<>(), new ArrayList<>());
		}

		Map<String, Integer> globalCount = new HashMap<>();
		Map<String, Integer> recentCount = new HashMap<>();

		long dayMillis = 24L * 60 * 60 * 1000;
		long cutOff = currentTime - dayMillis;

		for (Log log : logs) {
			if (log == null || log.url == null) {
				continue;
			}

			// Every valid access contributes to the global count.
			String key = log.url;
			globalCount.put(key, globalCount.getOrDefault(key, 0) + 1);

			/*
			 * "For the second map, I only count accesses whose timestamp belongs to the
			 * last 24-hour window."
			 */
			if (log.timestamp >= cutOff && log.timestamp <= currentTime) {
				recentCount.put(key, recentCount.getOrDefault(key, 0) + 1);
			}
		}

		List<UrlCount> globalTopK = getTopK(globalCount, k);
		List<UrlCount> recentTopK = getTopK(recentCount, k);

		return new Result(globalTopK, recentTopK);
	}

	/**
	 * "I'll keep only K candidates in a min-heap. The smallest count stays at the
	 * top, so whenever the heap grows beyond K, I remove the smallest candidate."
	 *
	 * Time: O(U log K) Space: O(K)
	 */
	private static List<UrlCount> getTopK(Map<String, Integer> countMap, int k) {

		PriorityQueue<UrlCount> minPQ = new PriorityQueue<>((a, b) -> {

			int cmp = Integer.compare(a.count, b.count);
			if (cmp != 0) {
				return cmp;
			}

			return b.url.compareTo(a.url);
		});

		/*
		 * Another approach PriorityQueue<UrlCount> minPQ2 = new PriorityQueue<>(
		 * Comparator.comparingInt((UrlCount a) -> a.count) .thenComparing(a -> a.url,
		 * Comparator.reverseOrder()) );
		 */
		for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
			UrlCount item = new UrlCount(entry.getKey(), entry.getValue());
			minPQ.offer(item);

			// Keep only the K largest counts seen so far.
			if (minPQ.size() > k) {
				minPQ.poll();
			}
		}

		List<UrlCount> result = new ArrayList<>();

		/*
		 * Heap returns smallest first, so collect and reverse to return highest
		 * frequency first.
		 */
		while (!minPQ.isEmpty()) {
			result.add(minPQ.poll());
		}

		Collections.reverse(result);

		return result;
	}

	/** Follow-up 1 : What if one machine cannot hold all URL counts? */
	/*
	 * I'd shard the aggregation by URL using something like hash(url) % P, so all
	 * accesses for the same URL go to the same worker. Each worker maintains counts
	 * only for its assigned URLs and computes its local Top K with the same
	 * min-heap approach. Then a global aggregator takes the P local Top K lists, so
	 * at most P times K candidates, and runs another size-K heap to produce the
	 * global Top K.
	 * 
	 * This reduces count storage from O(U) on one machine to roughly O(U/P) per
	 * worker. The main issue I'd address next is skew, where one very hot URL can
	 * overload a partition.
	 * 
	 * Visually:
	 * 
	 * Incoming Logs | v Hash by URL | +----------+----------+ | | | v v v Worker 1
	 * Worker 2 Worker 3 URL counts URL counts URL counts | | | Top K Top K Top K \
	 * | / \ | / +--------v--------+ Global Aggregator | v Global Top K
	 *
	 * 
	 * Step by step design: 1. Receive access logs.
	 * 
	 * 2. Hash each URL: partition = hash(url) % P
	 * 
	 * 3. Route all events for that URL to the same partition.
	 * 
	 * 4. Each worker maintains: Map<URL, Count>
	 * 
	 * 5. Each worker periodically calculates: Local Top K
	 * 
	 * 6. Send Local Top K to an aggregator.
	 * 
	 * 7. Aggregator runs another size-K min-heap across all local candidates.
	 * 
	 * 8. Return Global Top K.
	 */

	/** Follow-up 2 : How do you partition? */
	/*
	 * I would partition by URL because URL is the aggregation key. I would hash the
	 * URL and map it to one of P partitions, for example hash(url) % P. That
	 * ensures every event for the same URL always reaches the same partition, so
	 * one worker owns the complete count for that URL.
	 * 
	 * This distributes the count map across workers and avoids having partial
	 * counts for the same URL on multiple machines.
	 * 
	 * Assuming the hash function distributes URLs reasonably well, each worker
	 * should own roughly one P-th of the distinct URLs and one P-th of the traffic.
	 * 
	 * The main issue with this strategy is skew. Even if URLs are evenly
	 * distributed by count of keys, a single extremely popular URL can send a
	 * disproportionate amount of traffic to one partition. I would handle that
	 * separately as the hot-key follow-up.
	 * 
	 * NOTE: This approach doesn't solve Hot URL Issue yet: Because our current
	 * design: partition = hash(url) % P All requests for the same URL go to the
	 * same partition.
	 */

	/** Follow-up 3 : How do you merge Top K results from multiple machines? */
	// Refer Follow-up 1 answers

	/** Follow-up 4 : What happens if one URL becomes extremely hot? */

	/*
	 * A very hot URL creates a hot-key problem because URL-based partitioning sends
	 * all of its traffic to one worker. Adding more workers alone won't help
	 * because that URL still hashes to one partition."
	 * 
	 * For normal URLs I'd keep single-key partitioning, but for a detected hot URL
	 * I'd split it into multiple logical shards using (url, shardId). Those shards
	 * can be routed to different workers, so the load is spread out.
	 * 
	 * Each worker maintains a partial count, and downstream I sum all shards back
	 * into the original URL before computing the final Top K. That preserves exact
	 * counts while avoiding one overloaded partition. The tradeoff is an additional
	 * aggregation step for hot keys.
	 * 
	 * 
	 * # How Do We Detect a Hot URL?
	 * 
	 * 	Possible signals: 
	 * 		requests/sec for one key 
	 * 		queue depth 
	 * 		partition CPU 
	 * 		partition throughput
	 * 		percentage of total traffic from one URL
	 * 
	 * 	For example: if URL traffic > threshold: mark URL as hot
	 * 
	 * Basic Design: 
	 * 		Incoming Event
			      |
			      v
			Is URL hot?
			   /      \
			 no        yes
			 |          |
			 v          v
			hash(url)   choose shardId
			 |          |
			 v          v
			one       hash(url, shardId)
			partition     |
			              v
			         multiple partitions
	 * 
	 * 
	 * Impact on Top K: 
	 * A hot URL may appear as several partial entries. 
	 * We must not treat those as four different URLs in Top K.
	 * Before computing final Top K, combine them, then compare 
	 * against other URLs.
	 * 
	 * A Useful Optimization: Two-Level Aggregation
	 * If traffic is extremely large, we don't necessarily want 
	 * every event reaching a global reducer.
	 * 
	 * Each shard can first aggregate locally (say /viral#3 = 1,000,000), 
	 * then periodically emit only the delta (say /viral#3 += 50,000)
	 */

	/** Follow-up 5 : How would Count-Min Sketch help? */
	/** Follow-up 6 : Late-arriving logs? */
	
	/*
	 * Interview Script
	 * 
	 * You can say:
	 * 
	 * For late-arriving logs, I would base the rolling 24-hour window on event time
	 * rather than processing time. The timestamp inside the log determines which
	 * time bucket the event belongs to.
	 * 
	 * I would maintain event-time buckets, for example one-minute buckets. If a
	 * late event arrives for an older minute, I update that older bucket and adjust
	 * the current rolling count.
	 * 
	 * To avoid waiting forever for delayed events, I would define an allowed
	 * lateness or Watermark. Events arriving within that lateness are incorporated
	 * normally. For events arriving beyond it, the system needs an explicit policy:
	 * either update and correct previously emitted results, drop them, or send them
	 * to a separate late-event stream.
	 * 
	 * For an exact analytics requirement, I would prefer accepting late events as
	 * long as the relevant bucket is still retained, because then we can correct
	 * the rolling Top K.
	 * 
	 * 		Incoming log
			    |
			    v
			Read eventTime
			    |
			    v
			Choose event-time bucket
			    |
			    +----> bucket still active
			    |         |
			    |         v
			    |      update count
			    |
			    +----> too late
			              |
			              v
			        late-event policy
	 *
	 * Watermark in Simple Terms : we believe events before this event-time point 
	 * are mostly complete, so we can finalize or emit results.
	 * Example:
	 * 	max event time seen = 12:00
	 * 	allowed lateness = 5 min
	 * 	watermark = 11:55
	 */
	
	
	/** Follow-up 7 : What if events arrive out of order? */
	/*
	 * Out order could mean that stream events are not sorted (event time), and it's
	 * related to lateness. These streams are just not in order but they belongs to
	 * the same/current processing window.
	 * 
	 * Interview Script
	 * 
	 * If events arrive out of order, I would stop relying on arrival order
	 * completely. Each event carries an event timestamp, and I use that timestamp
	 * to place it into the correct time bucket.
	 * 
	 * So if a 10:02 event arrives after a 10:05 event, it simply updates the 10:02
	 * bucket. Expiration is based on event time, not insertion order.
	 * 
	 * A watermark with allowed lateness tells me when older buckets are safe to
	 * finalize or expire. This lets the rolling 24-hour window stay correct without
	 * globally sorting the stream.
	 * 
	 */
	

	boolean temp; // not in use
}

/*
 * Massive URL Access Logs / Top K Given a massive volume of URL access logs and
 * limited memory, find: 1. The global Top K most frequently accessed URLs. 2.
 * The Top K URLs with the highest access count during the past 24 hours.
 */