package leetcode;

import java.util.HashMap;
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

//https://leetcode.com/problems/lru-cache/submissions/
//-https://leetcode.com/problems/lru-cache/
//-- https://leetcode.com/problems/lru-cache/discuss/502050/Clean-Java-O(1)-Solution-using-HashMap-%2B-linked-list-with-no-dummy-nodes
class Node {
    int key;
    int value;
    Node next;
    Node prev;
}

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
}



//--https://leetcode.com/problems/lru-cache/discuss/494345/Java-Hash-Map-%2B-Doubly-Linked-List-Clearer-Version-of-Official-Solution
class LRUCache_Official_leetcode {
	private Map<Integer, DLinkedNode> cache;
	private DLinkedList list = new DLinkedList();
	private int size;
	private int capacity;

	public LRUCache_Official_leetcode(int capacity) {
		cache = new HashMap<Integer, DLinkedNode>();
		size = 0;
		this.capacity = capacity;
	}

	public int get(int key) {
		DLinkedNode node = cache.get(key);
		if (node == null)
			return -1;
		list.moveToHead(node);
		return node.value;
	}

	public void put(int key, int value) {
		DLinkedNode node = cache.get(key);
		if (node == null) {
			DLinkedNode newNode = new DLinkedNode(key, value);
			cache.put(key, newNode);
			list.addToHead(newNode);
			size++;
			if (size > capacity) {
				DLinkedNode popped = list.popTail();
				cache.remove(popped.key);
				size--;
			}
		} else {
			node.value = value;
			list.moveToHead(node);
		}
	}
	
	public void printList() {
		DLinkedNode node = list.head;
		
		while (node != null) {
			System.out.print(node.value);
			if(node.next != null)
				System.out.print(" <-> ");
			node = node.next;
		}
	}
}

class DLinkedNode {
	int key;
	int value;
	DLinkedNode prev;
	DLinkedNode next;

	DLinkedNode() {
	}

	DLinkedNode(int key, int value) {
		this.key = key;
		this.value = value;
	}
}

class DLinkedList {
	DLinkedNode head;
	DLinkedNode tail;

	public DLinkedList() {
		head = new DLinkedNode();
		tail = new DLinkedNode();
		head.prev = null;
		tail.next = null;
		head.next = tail;
		tail.prev = head;
	}

	public void moveToHead(DLinkedNode node) {
		removeNode(node);
		addToHead(node);
	}

	public void addToHead(DLinkedNode node) {
		node.prev = head;
		node.next = head.next;
		head.next.prev = node;
		head.next = node;
	}

	public void removeNode(DLinkedNode node) {
		DLinkedNode prev = node.prev;
		DLinkedNode next = node.next;
		prev.next = next;
		next.prev = prev;
	}

	public DLinkedNode popTail() {
		DLinkedNode popped = tail.prev;
		removeNode(popped);
		return popped;
	}
} 