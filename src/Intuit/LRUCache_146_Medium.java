package Intuit;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LRUCache_146_Medium {

	public static void main(String[] args) {

		LRUCache lru = new LRUCache(5);
		lru.put(1, 11);
		lru.put(2, 22);
		lru.put(3, 33);
		lru.put(4, 44);
		lru.put(5, 55);
		lru.put(6, 66); // [100, 66, 44, 33, 22]

		lru.put(5, 100);

		//--print
		lru.printList();
	}
	
}


// This is as fast as leetcode premium solution - in interview use Leetcode solution (provided below) and avoid this

class LRUCache {
    
    private int capacity;
    private HashMap<Integer, Node> map;
    private Node head;
    private Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>(capacity);
    }
    
    public int get(int key) {
        Node node = map.get(key);
        if (node == null) {
            return -1;
        }
        removeNode(node);
        addNode(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            removeNode(node);
            addNode(node);
            node.value = value;
        } else {
            if (map.size() >= capacity) {
                map.remove(tail.key);
                removeNode(tail);
            }
            Node node = new Node();
            node.key = key;
            node.value = value;
            addNode(node);
            map.put(key, node);
        }
    }
    
    private void addNode(Node node) {
        if (head == null) {
            head = node;
            tail = node;
            node.next = node;
            node.prev = node;
        } else {
            node.next = head;
            node.prev = tail;
            head.prev = node;
            tail.next = node;
            head = node;
        }
    }
    
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
    
    public void printList() {
		Node node = head;
		
		while (node != null) {
			System.out.print(node.value);
			if(node.next != null)
				System.out.print(" <-> ");
			node = node.next;
		}
	}
    
    private class Node {
        int key;
        int value;
        Node next;
        Node prev;
    }
}


// Leet code premium solution 
class LRUCache3 {

	  private Map<Integer, DLinkedNode> cache = new HashMap<>();
	  private int size;
	  private int capacity;
	  private DLinkedNode head, tail;

	  public LRUCache3(int capacity) {
	    this.size = 0;
	    this.capacity = capacity;

	    head = new DLinkedNode();
	    // head.prev = null;

	    tail = new DLinkedNode();
	    // tail.next = null;

	    head.next = tail;
	    tail.prev = head;
	  }

	  public int get(int key) {
	    DLinkedNode node = cache.get(key);
	    if (node == null) return -1;

	    // move the accessed node to the head;
	    moveToHead(node);

	    return node.value;
	  }

	  public void put(int key, int value) {
	    DLinkedNode node = cache.get(key);

	    if(node == null) {
	      DLinkedNode newNode = new DLinkedNode();
	      newNode.key = key;
	      newNode.value = value;

	      cache.put(key, newNode);
	      addNode(newNode);

	      ++size;

	      if(size > capacity) {
	        // pop the tail
	        DLinkedNode tail = popTail();
	        cache.remove(tail.key);
	        --size;
	      }
	    } else {
	      // update the value.
	      node.value = value;
	      moveToHead(node);
	    }
	  }
	  
	  // debugging only
	  public void printList() {
		  DLinkedNode node = head;
			
			while (node != null) {
				System.out.print(node.value);
				if(node.next != null)
					System.out.print(" <-> ");
				node = node.next;
			}
	  }	
	  
	  class DLinkedNode {
	    int key;
	    int value;
	    DLinkedNode prev;
	    DLinkedNode next;
	  }

	  private void addNode(DLinkedNode node) {
	    /**
	     * Always add the new node right after head.
	     */
	    node.prev = head;
	    node.next = head.next;

	    head.next.prev = node;
	    head.next = node;
	  }

	  private void removeNode(DLinkedNode node){
	    /**
	     * Remove an existing node from the linked list.
	     */
	    DLinkedNode prev = node.prev;
	    DLinkedNode next = node.next;

	    prev.next = next;
	    next.prev = prev;
	  }

	  private void moveToHead(DLinkedNode node){
	    /**
	     * Move certain node in between to the head.
	     */
	    removeNode(node);
	    addNode(node);
	  }

	  private DLinkedNode popTail() {
	    /**
	     * Pop the current tail.
	     */
	    DLinkedNode res = tail.prev;
	    removeNode(res);
	    return res;
	  }
	  
	  
	}

	/**
	 * Your LRUCache object will be instantiated and called as such:
	 * LRUCache obj = new LRUCache(capacity);
	 * int param_1 = obj.get(key);
	 * obj.put(key,value);
	 */




/*
 * Working but slow when submitted on leetcode - time 143ms compare to above which is 13ms 
 * 
 * Using Map and LinkedList as Doubly LinkedList
 * */
class LRUCache2 {
	
	private Map<Integer, Node> cache;
	private LinkedList<Node> list;  // DoublyLinkedList
//	private Deque<Node> list;  // we should ignore array deque here as deletion will be expensive
	private int capacity;
	
	public LRUCache2(int capacity) {
		cache = new HashMap<>();
		list = new LinkedList<>();
		this.capacity = capacity;
	}
	
	public void put(int key, int value) {
		if(cache.containsKey(key)) {
			Node node = cache.get(key);
			node.v = value;
			list.remove(node); // remove from current position
			list.addFirst(node); // add to first position in queue as this is recently accessed
		} else { // if new
			if(cache.size() >= capacity) {
				Node oldestNode = list.getLast();
				cache.remove(oldestNode.k);
				list.remove(oldestNode);
			}
			Node node = new Node(key, value);
			list.addFirst(node);
			cache.put(key, node);
			
		}
	} 
	
	public int get(int key) {
		if(cache.containsKey(key)) {
			Node node = cache.get(key);
			list.remove(node); // remove from current position
			list.addFirst(node); // add to first position in queue as this is recently accessed
			
			return node.v; // return value
		} 
		return -1;	
	}
	
	//debugging only
	public void printList() {
		System.out.println(list);
	}
	
	private class Node {
		int k, v;
		Node(int k, int v) {
			this.k = k;
			this.v = v;
		}
		@Override
		public String toString() {
			return "Node [k=" + k + ", v=" + v + "]";
		}
	} 
	
}
