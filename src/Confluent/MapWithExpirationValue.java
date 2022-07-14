package Confluent;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

public class MapWithExpirationValue {

	public static void main(String[] args) {
		
		TimeMapWithExpiry_With_DLL timeMap_DLL = new TimeMapWithExpiry_With_DLL();
		
		//LocalDate.now().plusDays(1);
		//LocalTime.now().plusMinutes(10).ge
		long millis = Instant.now().plusSeconds(60).toEpochMilli();
		
		timeMap_DLL.put("a", 100, 200);
		timeMap_DLL.put("b", 103, 0);
		timeMap_DLL.put("c", 307, 500);
		timeMap_DLL.put("d", 409, 100);
		timeMap_DLL.put("e", 553, millis);
		
		System.out.println(timeMap_DLL.get("a"));
		System.out.println(timeMap_DLL.get("b"));
		System.out.println(timeMap_DLL.get("c"));
		System.out.println(timeMap_DLL.get("d"));
		System.out.println(timeMap_DLL.get("e"));

		
		System.out.println(timeMap_DLL.remove("c"));
		System.out.println(timeMap_DLL.get("c"));
		System.out.println(timeMap_DLL.get("a"));
		
		System.out.println(timeMap_DLL.getAvg()); // working
		
		
		//**** NOTE ****
		// Only when asked otherwise don't bring this up
		repeatedTimerTask(timeMap_DLL); 
		
		/*
		 * For an easy test update the code to hard code the millis with long values
		 * and then pass diffrent value while invoking each method to varify
		 * 
		 * e.g: Let's say currnet millis is 500 then add values with 500 as expiration, with greater than 500
		 * and less than. Then call other methods like get() for any key. If the value is 500 or big for the input
		 * key we should be getting the result else -1
		 * 
		 */
		
	}
	
	//**** NOTE ****
	// Only when asked otherwise don't bring this up
	// below method will call clean repeatedly method after a fix period till we stop
	public static void repeatedTimerTask(TimeMapWithExpiry_With_DLL map) {
		
		TimerTask repeatedTask = new TimerTask() {
	        public void run() {
	        	map.clean();
	            System.out.println("Task performed on " 
	            + LocalTime.now().getMinute() + ":" + LocalTime.now().getSecond());
	        }
	    };
	    
	    Timer timer = new Timer("Timer");
	    long delay = 500L;
	    long period = 1000L; // period of successive task of execution
	    timer.scheduleAtFixedRate(repeatedTask, delay, period);
		
	}

	/**
	 * Windowed Average. You are given a set of key, value pairs. 
	 *  a. Each key, value expires after k millisec. 
	 *  b. I can ask you to get me a specific key. 
	 *  c. Also, I can ask you to return me the average.
	 * 
	 * For ex: if <"foo", 100> is saved at t = 1 and time expiry is 3ms then after 3
	 * ms get("foo") should return key not found.
	 */

}

/*
 * With DoublyLinkedList (DLL)
 * 
 * add: O(1), when not in sorted order i.e. adding at the end
 * get: O(1), we are accessing directly from Map
 * remove: O(1), we have node store in Map and we can directly update using prev and next to remove
 * clean: O(N), have to iterate entire list to find and remove node
 * 
 * Have to create custom DLL as Java DLL remove(obj) takes O(N)
 * 
 */

class TimeMapWithExpiry_With_DLL {
	Map<String, Node> map;
	//Node dll;	//DoublyLinkedList
	Node head, tail;
	int total;
	
	public TimeMapWithExpiry_With_DLL() {
    	map = new HashMap<>();
    }
	
	// add/put
	// Time: O(1) for insertion and lookup.
	public void put(String k, int v, long lifeTimeMillis) {
		
		if(!map.containsKey(k)) {
			Node node = new Node(k, v, lifeTimeMillis);
			addNode(node);
            map.put(k, node);
            total += v;
            
		} else { // Just replace the Node's old value with new one along with time
			Node node = map.get(k);
			total -= node.value; // first substract the old value
            
			node.value = v;
			total += v; // add new value
            node.expiresOn = System.currentTimeMillis() + lifeTimeMillis;
            
            //Note: if this needs to be like LRUCache then refer LRUCache_146_Medium.java
		}
		
	}
	
	// O(1)
	private void addNode(Node node) {
        if (head == null) {
            head = node;
            tail = node;
            node.next = node;
            node.prev = node;
        } else { // add as last node
        	tail.next = node;
        	node.prev = tail;
        	node.next = head;
            head.prev = node;
            tail = node; // updating tail to new node
        }
    }
	
	// O(1)
	public Integer get(String key) {
			
		if(!map.containsKey(key)) 
			return -1;
		
		Node node = map.get(key);
		// if it's expired delete it from Map and DLL and return null
		if(node.expiresOn < System.currentTimeMillis()) {
			removeNode(node);
			total -= node.value;
			map.remove(key);
			return -1;
		}

		return node.value;
	}
	
	// O(1)
	public boolean remove(String key) {
		if(!map.containsKey(key)) 
			return false;
		
		Node node = map.get(key);
		removeNode(node);	// call local removeNode()
		total -= node.value;	// update total
		map.remove(key);	// remove from map
		
		return true;
		
	}
	
	// O(1)
	private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        if (head == node) {
            head = node.next;
        }
        if (tail == node) {
            tail = node.prev;
        }
    }
	
	
	// clean expired values
	// Time: O(n), have to iterate the each node in the list
	public void clean() {
		Node node = head;
		
		while((node = node.next) != null) {
			if(node.expiresOn < System.currentTimeMillis())
				remove(node.key);
			
			//node = node.next; // this is also valid just update loop as while(node != null) with this
			
		}
	}
	
	public double getAvg() { 
		
		clean(); // first delete all the expired keys and then calculate average
		
		return (double) total/map.size();
	}
	
	//Node - DoublyLinkedList
	private static class Node {
		String key;
		int value;
		long expiresOn;
		Node prev; 
		Node next;
	
		public Node(String key, int value, long lifeTimeMillis) {
			this.key = key;
			this.value = value;
			this.expiresOn = System.currentTimeMillis() + lifeTimeMillis;
		}

	}
	
}


////////////////////

/* Good candidate for interview only when requirement of add and get is NOT O(1)
 * 
 * with TreeMap: Using TreeMap provides guaranteed log(n) time cost for the 
 * containsKey, get, put and remove operations
 * 
 */
class TimeMapWithExpiry_With_TreeMap {
	Map<String, Integer> map;
	TreeMap<Integer, Long> treeMap;
	int total;
	
	public TimeMapWithExpiry_With_TreeMap() {
    	map = new HashMap<>();
    	treeMap = new TreeMap<>((l1, l2) -> Long.compare(l1, l2)); // sort by time in ascending 
    	treeMap = new TreeMap<>(); // default sorted i.e. by key
    }
	
	// add/put
	// TreeMap has complexity of O(logN) for insertion and lookup.
	public void put(String k, int v, long lifeTimeMillis) {

		// add new or replace if it's already exist
		map.put(k, v);
		long expiresOn = System.currentTimeMillis() + lifeTimeMillis;
		treeMap.put(v, expiresOn);
		
		total += v;
		
	}
	
	// O(log N)
	public Integer get(String key) {
		
		if(!map.containsKey(key)) 
			return null;
		
		int v = map.get(key); //O(1)

		// if it's expired delete it from Map and TreeMap and return null
		if(treeMap.get(v) < System.currentTimeMillis()) {
			remove(key);
			return null;
		}
		
		return v;
	} 
	
	public double getAvg() { 
		return total/map.size();
	}
	
	// delete/remove a node
	// O(log N)
	public boolean remove(String key) {
		if(!map.containsKey(key)) 
			return false;
		
		removeAndUpdateTotal(key);
		
		return true;
	}

	// O(log N)
	private void removeAndUpdateTotal(String key) {
		int v = map.get(key);
		treeMap.remove(v); // O(Log N) => Here it beats the PriorityQueue (PQ), coz PQ remove(obj) cost O(N) 
		map.remove(key); // O(1)
		total -= v;
	}

	// clean expired values
	// Time: Remove in TreeMap takes O(log n) but 
	//       O(n * log n) in worst case here when we are deleting the entire TreeMap
	public void clean() {
		
		Set<String> keySet = map.keySet();
		
		for(String key : keySet) {
			
			long v = map.get(key);
			
			if(treeMap.get(v) < System.currentTimeMillis()) {
				remove(key);
			}
		}
	}
	
	
}


// with PriorityQueue
// Use TreeMap solution as remove(obj) for PQ takes O(N) but in TreeMap O(log n)
class TimeMapWithExpiry_With_PQ {

	Map<String, Node> map;
	// Node node;
	PriorityQueue<Node> minHeap;
	int total;

	public TimeMapWithExpiry_With_PQ() {
    	map = new HashMap<>();
    	minHeap = new PriorityQueue<>((n1, n2) -> Long.compare(n1.expiresOn, n2.expiresOn));
    }

	// add/put
	public void put(String k, int v, long lifeTimeMillis) {
		Node node = new Node(k, v, lifeTimeMillis); // create a new Node with new value 
		
		total += v;
		// add new or replace if it's already exist
		map.put(k, node);
	}

	// Time: O(N)
	public Integer get(String k) {
		
		if(!map.containsKey(k)) 
			return null;
		
		Node node = map.get(k);

		// if it's expired delete it from Map and PriorityQueue and return null
		if(node.expiresOn < System.currentTimeMillis()) {
			remove(k);
			return null;
		}
		
		return node.value;
	}

	public double getAvg() { 
		return total/map.size();
	}
	
	// delete/remove a node
	public boolean remove(String k) {
		if(!map.containsKey(k)) 
			return false;
		
		Node node = map.get(k);
		
		minHeap.remove(node); // O(N) + O(Log N)  => O(N)
		map.remove(node.key); // O(1)
		total -= node.value;
		
		return true;
	}

	// clean expired values
	// Time: O(n * log n) in worst case i.e. when we are polling entire heap 
	public void clean() {
		while(!minHeap.isEmpty()) {
			Node top = minHeap.peek();
			
			if(top.expiresOn < System.currentTimeMillis()) {
				minHeap.poll();	// O(log n) : O(1) for polling but then arranging entire tree takes O(log n)
				map.remove(top.key); // O(1)
				total -= top.value;
			}
		}
	}
	
	
	//Node
	private static class Node {
		public String key; // this will help us in deleting the node from HashMap
		public int value;
		public long expiresOn; // in milliseconds: addedOn + expirationTime

		public Node() {
		}

		public Node(String k, int value, long lifeTimeMillis) {
			this.key = k;
			this.value = value;
			this.expiresOn = System.currentTimeMillis() + lifeTimeMillis;
		}
	}
}
