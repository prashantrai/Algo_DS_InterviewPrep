package LinkedIn;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class All_O_one_Data_Structure_432_Hard {

	public static void main(String[] args) {

		AllOne obj = new AllOne();
		obj.inc("hello");
		obj.inc("hello");
		System.out.println(obj.getMaxKey()); // "hello"
		System.out.println(obj.getMinKey()); // "hello"
		obj.inc("leet");
		System.out.println(obj.getMinKey()); // "leet"
		obj.dec("leet");
		System.out.println(obj.getMinKey()); // "hello"
	}
	
	// Time: O(1), Space: (N)
	
	static class AllOne { // static added just use here from main

	    class Node {
	        int count; //freq
	        Set<String> keys;
	        Node prev, next;

	        Node(int count) {
	            this.count = count;
	            // HashSet works too but LinkedHashSet gives order guarantee 
	            // while reading and a good option to use in Interview
	            this.keys = new LinkedHashSet<>(); 
	        }
	    }

	    // Dummy head and tail to simplify insert/remove
	    private Node head, tail;
	    private Map<String, Node> map; // key -> Node

	    public AllOne() {
	        head = new Node(0);
	        tail = new Node(0);
	        head.next = tail;
	        tail.prev = head;
	        map = new HashMap<>();

	    }
	    
	    /*
	     If key is new:
	        - Check if a bucket with count = 1 exists.
	        - If not, create a new one after head.
	        - Add key to that bucket.
	        - Update keyToNode.
	    If key exists:
	        - Move it to the next bucket with count + 1.
	        - Create it if it doesn’t exist.
	        - Remove key from current bucket.
	        - Delete current bucket if it's empty.
	        - Update keyToNode.
	     */
	    public void inc(String key) {
	        if(!map.containsKey(key)) {
	            // Insert key with count 1
	            if(head.next.count != 1) {
	                //insertNodeAfter head
	                insertNodeAfter(head, new Node(1));
	            } 
	            // Update new added node's Set by adding key 
	            head.next.keys.add(key);
	            // add to map
	            map.put(key, head.next);
	        }
	        else {
	            Node cur = map.get(key);
	            Node next = cur.next;
	            if(next == tail || next.count != cur.count + 1) {
	                next = new Node(cur.count + 1);
	                insertNodeAfter(cur, next); // insert after current
	            }
	        
	            next.keys.add(key);
	            map.put(key, next);

	            // Now, remove key from current node
	            cur.keys.remove(key);
	            if(cur.keys.isEmpty()) {
	                removeNode(cur);
	            }
	        }
	    }
	    
	    /* 
	    1. If key not present → return.
	    2. If key’s count = 1:
	        - Remove key from current bucket.
	        - Remove key from keyToNode.
	    3. Else:
	        - Move key to previous bucket with count - 1.
	        - Create if it doesn’t exist.
	        - Remove key from current bucket.
	    4. Delete current bucket if it's empty.
	    */
	    public void dec(String key) {
	        if(!map.containsKey(key)) return;

	        Node cur = map.get(key);
	        // when count is 1, remove/delete key
	        if(cur.count == 1) {
	            // Remove key from structures completely
	            //cur.keys.remove(key); // MUST remove from current bucket
	            map.remove(key); // Remove from map
	        } else {
	            // Move key to previous bucket
	            Node prev = cur.prev;
	            if(prev == head || prev.count != cur.count - 1) {
	                prev = new Node(cur.count - 1);
	                insertNodeAfter(cur.prev, prev);
	            }
	            prev.keys.add(key);
	            map.put(key, prev);

	            //cur.keys.remove(key); // Remove from current bucket
	        }
	        cur.keys.remove(key); // MUST Remove from current bucket
	        if(cur.keys.isEmpty()) { 
	            removeNode(cur);
	        }
	    }
	    
	    public String getMaxKey() {
	        if(tail.prev != head && !tail.prev.keys.isEmpty()) {
	            return tail.prev.keys.iterator().next();
	        } 
	        return "";
	    }
	    
	    public String getMinKey() {
	        if(head.next != null && !head.next.keys.isEmpty()) {
	            return head.next.keys.iterator().next();
	        }
	        return "";
	    }

	    // Utility to insert node after given node
	    private void insertNodeAfter(Node prev, Node node) {
	        node.next = prev.next;
	        node.prev = prev;
	        
	        // keep below code in same sequance
	        // changig order of both lines will curropt the node/list mapping
	        prev.next.prev = node;
	        prev.next = node;
	    }

	    private void removeNode(Node node) {
	        node.prev.next = node.next;
	        node.next.prev = node.prev;
	    }

	}

	/**
	 * Your AllOne object will be instantiated and called as such:
	 * AllOne obj = new AllOne();
	 * obj.inc(key);
	 * obj.dec(key);
	 * String param_3 = obj.getMaxKey();
	 * String param_4 = obj.getMinKey();
	 */

}

