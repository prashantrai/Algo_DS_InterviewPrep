package Gusto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TimeBasedKeyValueStore_981_Medium {

	public static void main(String[] args) {
		TimeMap kv = new TimeMap();
		kv.set("foo", "bar", 1); // store the key "foo" and value "bar" along with timestamp = 1   
		kv.set("foo", "bar2", 4);   

		System.out.println(kv.get("foo", 1));  // output "bar"
		System.out.println(kv.get("foo", 3)); // output "bar" since there is no value corresponding to foo at timestamp 3 and timestamp 2, then the only value is at timestamp 1 ie "bar"
		
		System.out.println(kv.get("foo", 4)); // output "bar2"
		System.out.println(kv.get("foo", 5)); //output "bar2"
		
		TimeMap2 kv2 = new TimeMap2();
		kv2.set("foo", "bar", 1); // store the key "foo" and value "bar" along with timestamp = 1   
		kv2.set("foo", "bar2", 4);   

		System.out.println(kv2.get("foo", 1));  // output "bar"
		System.out.println(kv2.get("foo", 3)); // output "bar" since there is no value corresponding to foo at timestamp 3 and timestamp 2, then the only value is at timestamp 1 ie "bar"
		
		System.out.println(kv2.get("foo", 4)); // output "bar2"
		System.out.println(kv2.get("foo", 5)); //output "bar2"

	}

	/*Complexity Analysis (for TreeMap solution)

	Time Complexity: O(1) for each set operation, 
					and O(logN) for each get operation, where N is the 
					number of entries in the TimeMap.

	Space Complexity: O(N).
	*/
	static class TimeMap {
	    
	    Map<String, TreeMap<Integer, String>> map;

	    /** Initialize your data structure here. */
	    public TimeMap() {
	        map = new HashMap<>();
	    }
	    
	    public void set(String key, String value, int timestamp) {
	        /*if (!map.containsKey(key)) {
	          map.put(key, new TreeMap<Integer, String>());
	        }*/
	    	map.putIfAbsent(key, new TreeMap<Integer, String>());
	        map.get(key).put(timestamp, value);        
	    }
	    
	    public String get(String key, int timestamp) {
	        if(!map.containsKey(key)) 
	            return ""; //null;
	        
	        TreeMap<Integer, String> tMap = map.get(key);
	        /*
	        floorKey() returns the greatest key less than or equal to the given key, or null if there is no such key.
	        */
	        Integer t = tMap.floorKey(timestamp);
	        return t == null ? "" : tMap.get(t);
	    }
	}
	
	//--Solution2 
	//--Same time complexity as above solution
	
	static class TimeMap2 {
	    Map<String, List<Pair<Integer, String>>> M;

	    public TimeMap2() {
	        M = new HashMap();
	    }

	    public void set(String key, String value, int timestamp) {
	        if (!M.containsKey(key))
	            M.put(key, new ArrayList<Pair<Integer, String>>());

	        M.get(key).add(new Pair(timestamp, value));
	    }

	    public String get(String key, int timestamp) {
	        if (!M.containsKey(key)) return "";

	        List<Pair<Integer, String>> A = M.get(key);
	        int i = Collections.binarySearch(A, 
	        		new Pair<Integer, String>(timestamp, "{"),
	                (a, b) -> Integer.compare(a.getKey(), b.getKey()));

	        if (i >= 0)
	            return A.get(i).getValue();
	        else if (i == -1)
	            return "";
	        else
	            return A.get(-i-2).getValue();
	    }
	    
	    class Pair<I, S> {
	    	I timestamp;
	    	S value;
	    	public Pair(I t, S s) {
	    		this.timestamp = t;
	    		this.value = s;
	    	}
	    	public I getKey() {
	    		return this.timestamp;
	    	}
	    	public S getValue() {
	    		return this.value;
	    	} 
	    }
	}
	
}

/* Question: 

Create a timebased key-value store class TimeMap, that supports two operations.

1. set(string key, string value, int timestamp)

Stores the key and value, along with the given timestamp.
2. get(string key, int timestamp)

Returns a value such that set(key, value, timestamp_prev) was called previously, with timestamp_prev <= timestamp.
If there are multiple such values, it returns the one with the largest timestamp_prev.
If there are no values, it returns the empty string ("").
 

Example 1:

Input: inputs = ["TimeMap","set","get","get","set","get","get"], inputs = [[],["foo","bar",1],["foo",1],["foo",3],["foo","bar2",4],["foo",4],["foo",5]]
Output: [null,null,"bar","bar",null,"bar2","bar2"]
Explanation:   
TimeMap kv;   
kv.set("foo", "bar", 1); // store the key "foo" and value "bar" along with timestamp = 1   
kv.get("foo", 1);  // output "bar"   
kv.get("foo", 3); // output "bar" since there is no value corresponding to foo at timestamp 3 and timestamp 2, then the only value is at timestamp 1 ie "bar"   
kv.set("foo", "bar2", 4);   
kv.get("foo", 4); // output "bar2"   
kv.get("foo", 5); //output "bar2"   

Example 2:

Input: inputs = ["TimeMap","set","set","get","get","get","get","get"], inputs = [[],["love","high",10],["love","low",20],["love",5],["love",10],["love",15],["love",20],["love",25]]
Output: [null,null,null,"","high","high","low","low"]
 
 * */
