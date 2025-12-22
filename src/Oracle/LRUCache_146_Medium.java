package Oracle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


public class LRUCache_146_Medium {

	public static void main(String[] args) {
		LRUCache lru = new LRUCache(5);
        lru.put(1, 11);
        lru.put(2, 22);
        lru.put(3, 33);
        lru.put(4, 44);
        lru.put(5, 55);
        lru.printList(); 
        lru.put(6, 66); // evicts 1
        lru.printList(); 

        lru.put(5, 100); // update 5
        lru.printList(); 

        LRUCache cache = new LRUCache(1);
        cache.put(1, 1);
        cache.put(2, 2); // evicts key 1
        System.out.println(cache.get(1)); // prints -1
        cache.printList(); 
		
	}
	
	private static class Node {
		private int key, value;
		private Node prev, next;
		public Node() {}
		public Node(int k, int v) {
			this.key = k;
			this.value = v;
		}
	}

	//Time: O(1) for both put and get
	// Space: O(capacity)

	private static class LRUCache {
		private int capacity;
		private Map<Integer, Node> map;
		private Node head; // dummy head
		private Node tail; // dumm tail
		
		
		public LRUCache(int capacity) {
			this.capacity = capacity;
			map = new HashMap<>();
			// Initialize dummy nodes
			head = new Node();
			tail = new Node();
			head.next = tail;
			tail.prev = head;
		}
		
		public int get(int key) {
	        Node node = map.get(key);
	        if(node == null) return -1;
	        moveToHead(node);
	        return node.value;
	    }
	    
	    public void put(int key, int value) {
	        Node node = map.get(key);
	        if(node != null) {
	        	node.value = value;
	        	moveToHead(node);
	        } 
	        else {
	        	if(map.size() >= capacity) {
	        		Node last = tail.prev;
	        		removeNode(last);
	        		map.remove(last.key);
	        	}
	        	Node newNode = new Node(key, value);
	        	addNode(newNode);
	        	map.put(key, newNode);
	        }
	    }
	    
	    private void moveToHead(Node node) {
	    	removeNode(node);
	    	addNode(node);
	    }
	    
	    private void removeNode(Node node) {
	    	node.prev.next = node.next;
	    	node.next.prev = node.prev;
	    }
	    
	    private void addNode(Node node) {
	    	node.prev = head;
	    	node.next = head.next;
	    	head.next.prev = node;	// update the old first node's prev
	    	head.next = node;	// Then update head's next
	    }
	    
	    
	    // print list - do not use in interview
		public void printList() {
		    if (map.isEmpty()) {
		        System.out.println("Cache is empty");
		        return;
		    }
		    
		    Node current = head.next; // skip dummy head
		    System.out.print("LRU Cache (most recent → least recent): ");
		    while (current != tail) { // stop before dummy tail
		        System.out.print("[" + current.key + "=" + current.value + "]");
		        if (current.next != tail) {
		            System.out.print(" -> ");
		        }
		        current = current.next;
		    }
		    System.out.println();
		}
	    
	}
	

	
	
}
