package Coupang;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


public class LRUCache_146_Medium {

	public static void main(String[] args) {
		LRUCache lru = new LRUCache(5);
//		LRUCache2 lru = new LRUCache2(5); // built in collection - leetcode solution
//		LRUCache3 lru = new LRUCache3(5); // Similar to main solution - leetcode solutions
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

class LRUCache {
    
    // Time: O(1) for both put and get
    // Space: O(capacity)
    
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
		
		Set<Integer> seen  = new HashSet<>();
		while (node != null) {
			if(seen.contains(node.value)) 
				break; // this is a doubly linked list and if we don't break it will go in infinite loop
			
			seen.add(node.value);
			System.out.print(node.value);
			
//			if(node.next != null)
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





//similar to above - Leetcode solution
class ListNode {
    int key;
    int val;
    ListNode next;
    ListNode prev;

    public ListNode(int key, int val) {
        this.key = key;
        this.val = val;
    }
}

class LRUCache3 {
    int capacity;
    Map<Integer, ListNode> dic;
    ListNode head;
    ListNode tail;
    
    public LRUCache3(int capacity) {
        this.capacity = capacity;
        dic = new HashMap<>();
        head = new ListNode(-1, -1);
        tail = new ListNode(-1, -1);
        head.next = tail;
        tail.prev = head;
    }
    
    public int get(int key) {
        if (!dic.containsKey(key)) {
            return -1;
        }
        
        ListNode node = dic.get(key);
        remove(node);
        add(node);
        return node.val;
    }
    
    public void put(int key, int value) {
        if (dic.containsKey(key)) {
            ListNode oldNode = dic.get(key);
            remove(oldNode);
        }
        
        ListNode node = new ListNode(key, value);
        dic.put(key, node);
        add(node);
        
        if (dic.size() > capacity) {
            ListNode nodeToDelete = head.next;
            remove(nodeToDelete);
            dic.remove(nodeToDelete.key);
        }
    }
    
    public void add(ListNode node) {
        ListNode previousEnd = tail.prev;
        previousEnd.next = node;
        node.prev = previousEnd;
        node.next = tail;
        tail.prev = node;
    }
    
    public void remove(ListNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */



// Using Built in collection - Leetcode solutions

class LRUCache2 {
    int capacity;
    LinkedHashMap<Integer, Integer> dic;
    
    public LRUCache2(int capacity) {
        this.capacity = capacity;
        dic = new LinkedHashMap<Integer, Integer>(5, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > capacity;
            }
        };
    }
    
    public int get(int key) {
        return dic.getOrDefault(key, -1);
    }
    
    public void put(int key, int value) {
        dic.put(key, value);
    }
    
    public void printList() {
		System.out.println(dic);
	}

}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */


