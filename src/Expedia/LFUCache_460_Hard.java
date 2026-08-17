package Expedia;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class LFUCache_460_Hard {

	public static void main(String[] args) {
		int capacity = 2;
		LFUCache cache = new LFUCache(capacity);
		cache.put(1, 1);
		cache.put(2, 2);
		cache.get(1);       // returns 1
		cache.put(3, 3);    // evicts key 2
		cache.get(2);       // returns -1 (not found)
		cache.get(3);       // returns 3.
		cache.put(4, 4);    // evicts key 1.
		cache.get(1);       // returns -1 (not found)
		cache.get(3);       // returns 3
		cache.get(4);       // returns 4
		
		//Expected : [null,null,null,1,null,-1,3,null,-1,3,4]
	}
	
	/* Interview script:

	- “I’ll implement this cache using an LFU policy, and when multiple 
		keys have the same frequency, I’ll break ties by LRU.”
	
	- “To get O(1) average time** for both `get` and `put`, I’ll maintain 
	 	**three maps** plus one variable for the current minimum frequency.”
	 
	- “The first map, `vals`, stores key -> value, so I can return or update 
		the cached value directly.”
	- “The second map, `counts`, stores "key -> frequency", so I always 
		know how many times each key has been used.”
	
	- “The third map, `lists`, stores "frequency -> set of keys". This groups 
		keys by usage count. I’ll use a `LinkedHashSet` so that within the same 
		frequency bucket, I preserve insertion order, which lets me evict 
		the "Least Recently Used" key among ties.”
	
	- “I’ll also maintain `min`, which tracks the **smallest frequency currently 
		present** in the cache. That allows eviction in O(1), because I can 
		immediately look at the lowest-frequency bucket.”
		
	- “On `get`, I return the value and move the key from frequency `f` to `f+1`. 
		On `put`, if the cache is full, I evict from the `min` bucket first.”

	Natural spoken version for interviews:: 
	
	 * */
	
	/* 
	Time: O(1) for both get() and put()
	Space: O(capacity)
	*/
	static class LFUCache {
		private Map<Integer, Integer> vals; // Stores the actual cache value.
	    private Map<Integer, Integer> counts; // Stores how many times each key was used.

	    // Groups keys by frequency.
	    // LinkedHashSet keeps insertion order, so within the same frequency, 
	    // the oldest key is the LRU one.
	    private Map<Integer, LinkedHashSet<Integer>> lists;
	    private int capacity;
	    
	    // min stores the smallest frequency currently present in the cache.
	    // It helps us evict in O(1) by directly looking at the lowest-frequency bucket: lists.get(min).
	    // We update min when that bucket becomes empty, or reset it to 1 when inserting a new key.
	    // Tracks the smallest frequency among all keys currently in the cache.
	    private int min;
	    
	    public LFUCache(int capacity) {
	        this.capacity = capacity;
	        this.min = 0; // Start with 0 instead of -1 for clarity
	        
	        vals = new HashMap<>();
	        counts = new HashMap<>();
	        lists = new HashMap<>();
	    }
	    
	    public int get(int key) {
	        if (!vals.containsKey(key)) {
	            return -1;
	        }
	        
	        int count = counts.get(key);
	        // Update count
	        counts.put(key, count + 1);
	        
	        // When we access a key via get(), its frequency increases by 1. 
	        // We need to move it from its old frequency bucket to the new one. 
	        // If we don't remove it from the old bucket, the key would exist 
	        // in multiple frequency lists simultaneously, which breaks our 
	        // data structure.
	        // Remove from current frequency list
	        lists.get(count).remove(key);
	        
	        // Clean up empty frequency buckets
	        if (lists.get(count).isEmpty()) {
	            lists.remove(count);
	            // Update min if we removed the last element at minimum frequency
	            if (count == min) {
	                min = count + 1;
	            }
	        }
	        
	        // Add to new frequency list
	        lists.computeIfAbsent(count + 1, k -> new LinkedHashSet<>()).add(key);
	        
	        return vals.get(key);
	    }
	    
	    public void put(int key, int value) {
	        if (capacity <= 0) {
	            return;
	        }
	        
	        if (vals.containsKey(key)) {
	            vals.put(key, value);
	            get(key); // Reuse get logic to update frequency
	            return;
	        }
	        
	        // Eviction needed
	        if (vals.size() >= capacity) {
	            // Get the least recently used key at minimum frequency
	        	// Gets the first element from this iterator
	            int evictKey = lists.get(min).iterator().next();
	            lists.get(min).remove(evictKey);
	            
	            // Clean up empty bucket
	            if (lists.get(min).isEmpty()) {
	                lists.remove(min);
	            }
	            
	            vals.remove(evictKey);
	            counts.remove(evictKey);
	        }
	        
	        // Insert new key
	        vals.put(key, value);
	        counts.put(key, 1);
	        lists.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
	        min = 1; // Reset min to 1 since we added a new element
	    }
	}

	/**
	 * Your LFUCache object will be instantiated and called as such:
	 * LFUCache obj = new LFUCache(capacity);
	 * int param_1 = obj.get(key);
	 * obj.put(key,value);
	 */

}
