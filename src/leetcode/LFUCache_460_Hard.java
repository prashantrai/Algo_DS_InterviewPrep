package leetcode;

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
	
	/*
	 * https://leetcode.com/problems/lfu-cache/
	 * https://medium.com/algorithm-and-datastructure/lfu-cache-in-o-1-in-java-4bac0892bdb3
	 * 
	 * Time Comlexity for both get() and put() is O(1) 
	 * */
	
	static class LFUCache {
	    private Map<Integer, Integer> vals; // data : key and value
	    private Map<Integer, Integer> counts; // counter : key and count
	    private Map<Integer, LinkedHashSet<Integer>> lists; //Counter and item list
	    private int capacity;
	    private int min;
	    
	    public LFUCache(int capacity) {
	        vals   = new HashMap<>();
	        counts = new HashMap<>();
	        lists  = new HashMap<>();
	        lists.put(1, new LinkedHashSet<Integer>());
	        this.capacity = capacity;
	        min = -1;
	    }
	    
	    public int get(int key) {
	        
	        if(!vals.containsKey(key)) {
	            return -1;
	        }
	        
	        int count = counts.get(key);
	        counts.put(key, count+1); // update current count
	        
	        // remove the element from the counter to linkedhashset
	        lists.get(count).remove(key);
	        
	         // when current min does not have any data, next one would be the min
	        if(count == min && lists.get(count).size() == 0) {
	            min++;
	        }
	        
	        if(!lists.containsKey(count+1)) {
	            lists.put(count+1, new LinkedHashSet<Integer>());
	        }
	        lists.get(count+1).add(key);
	        
	        return vals.get(key);
	    }
	    
	    public void put(int key, int value) {
	        if(capacity <= 0 ) return;
	        
	        // If key does exist, we are returning from here
	        if (vals.containsKey(key)) {
	            vals.put(key, value);
	            get(key); // this will update values every where
	            return;
	        }
	        
	        if(vals.size() >= capacity) { // evict the less frequently used key from everywhere
	            int evict = lists.get(min).iterator().next();
	            lists.get(min).remove(evict);
	            vals.remove(evict);
	            counts.remove(evict);
	        }
	        
	        // If the key is new, insert the value and current min should be 1 of course
	        vals.put(key, value);
	        counts.put(key, 1);
	        min = 1;
	        lists.get(1).add(key);

	    }
	}

	/**
	 * Your LFUCache object will be instantiated and called as such:
	 * LFUCache obj = new LFUCache(capacity);
	 * int param_1 = obj.get(key);
	 * obj.put(key,value);
	 */

}
