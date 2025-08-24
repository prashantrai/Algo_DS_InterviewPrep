package LinkedIn;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;

public class MaxStack_716_Hard {
	
	public static void main(String[] args) {
		MaxStack_716_Hard stack = new MaxStack_716_Hard();

	    stack.push(5);
	    stack.push(1);
	    stack.push(5);
	    System.out.println(stack.top());      // 5
	    System.out.println(stack.popMax());   // 5
	    System.out.println(stack.top());      // 1
	    System.out.println(stack.peekMax());  // 5
	    System.out.println(stack.pop());      // 1
	    System.out.println(stack.top());      // 5

	    // Edge case: push duplicate max
	    stack.push(3);
	    stack.push(3);
	    System.out.println(stack.popMax());   // 3 (top-most one)
	    System.out.println(stack.peekMax());  // 3
	}


    /*
    Time Complexity: 
    | Operation | Time Complexity |
    | --------- | --------------- |
    | push      | O(log n)        |
    | pop       | O(1)            |
    | top       | O(1)            |
    | peekMax   | O(log n)        |
    | popMax    | O(log n)        |

    Space Complexity: O(n) for storing all nodes and map entries.

    */

    private static class Node {
        int val;
        Node prev, next;
        Node(int val) {
            this.val = val;
        }
    }

    Node head, tail; // DoublyLinkedList (DLL) head and tail
    
    /* Why TreeMap? 
       The problem requires:
		- Fast access to the maximum value in the stack → peekMax()
		
		- TreeMapstores keys in sorted order.
		- supports lastKey() → returns the maximum key in O(log n).
		- It supports efficient removal of elements as well.
		
		This makes TreeMap ideal for finding and removing max values quickly.
		
		Example: 
		DLL (stack order from bottom to top): head <-> 5 <-> 1 <-> 5 <-> tail
		TreeMap: {
		  1: [Node1],     // list of nodes with value 1
		  5: [Node2, Node3]  // two different nodes with value 5
		}
		
		peekMax() → map.lastKey() → 5 
	*/
    
    TreeMap<Integer, List<Node>> map; // Value to the list of DLL nodes

    public MaxStack_716_Hard() {
        head = new Node(0); // dummy head
        tail = new Node(0); // dummy tail
        head.next = tail;
        tail.prev = head;
        map = new TreeMap<>();
    }

    private void addNode(Node node) {
        Node prev = tail.prev;
        // link prev node to new node
        prev.next = node;
        node.prev = prev;
        // link new node with tail
        node.next = tail;
        tail.prev = node;
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    
    public void push(int x) {
        Node node = new Node(x);
        addNode(node);

        /* If map doesn't contain key then create/put a new key (x)
        in map with value as empty array list and then add node to 
        the arrayLiist. */
        map.computeIfAbsent(x, k -> new ArrayList<>()).add(node);

        /* OR, wihtout lambda
        if(!map.containsKey(k)) {
            map.put(x, new ArrayList<>());
        }
        map.get(x).add(node);
        */

    }
    
    public int pop() {
        // get the node to be popped
        Node node = tail.prev;
        removeNode(node);

        // remove from List
        List<Node> list = map.get(node.val);
        list.remove(list.size()-1); // remove last inserted node
        
        // if list is empty, remove from map
        if(list.isEmpty()) 
            map.remove(node.val);

        return node.val;

    }
    
    public int top() {
        return tail.prev.val;
    }
    
    public int peekMax() {
        // Returns largest key currently in the TreeMap.
        // Time: O(log n) — because TreeMap is a Red-Black tree.
        return map.lastKey(); 
    }
    
    public int popMax() {
        int max = map.lastKey();
        List<Node> list = map.get(max);
        // remove last inserted node from list
        Node node = list.remove(list.size()-1);
        if (list.isEmpty()) {
            map.remove(max);
        }
        removeNode(node);
        
        return max;
    }
    
    
    /** Thread-safe MaxStack code */
    
    // It's an extension of above solution. Lines add to support
    // concurrency have comment as "concurrency"
    
    class MaxStack_ThreadSafe {
        private class Node {
            int val;
            Node prev, next;
            Node(int val) { this.val = val; }
        }

        private Node head, tail;
        private TreeMap<Integer, List<Node>> map;
        
//        private final ReentrantLock lock = new ReentrantLock(); // concurrency - creates single lock
        
        // concurrency - individual locks to protect both DLL and MAP
        private final ReentrantLock dllLock = new ReentrantLock(); // protects DLL
        private final ReentrantLock mapLock = new ReentrantLock(); // protects TreeMap
        
        /* Why Consistent Lock Order?
			If Thread A locks dllLock then waits for mapLock, 
			and Thread B locks mapLock then waits for dllLock → deadlock.
    	
    		To prevent this:
    		We always acquire locks in the same order:
    		dllLock → then mapLock.
         */


        public MaxStack_ThreadSafe() {
            head = new Node(0);
            tail = new Node(0);
            head.next = tail;
            tail.prev = head;
            map = new TreeMap<>();
        }

        private void addNode(Node node) {
            Node prev = tail.prev;
            prev.next = node;
            node.prev = prev;
            node.next = tail;
            tail.prev = node;
        }

        private void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        public void push(int x) {
            //lock.lock(); //concurrency: Acquire lock before modifying shared data
        	dllLock.lock(); //concurrency: -- dll lock first
            mapLock.lock(); //concurrency: 
            try {
                Node node = new Node(x);
                addNode(node);
                map.computeIfAbsent(x, k -> new ArrayList<>()).add(node);
            } finally {
                //lock.unlock(); //concurrency: Always release lock
            	mapLock.unlock(); // release mapLock first
                dllLock.unlock();
            }
        }

        public int pop() {
            //lock.lock(); // concurrency
        	 dllLock.lock(); // concurrency -- dll lock first
             mapLock.lock(); // concurrency:
            try {
                Node node = tail.prev;
                removeNode(node);
                List<Node> list = map.get(node.val);
                list.remove(list.size() - 1);
                if (list.isEmpty()) map.remove(node.val);
                return node.val;
            } finally {
                //lock.unlock(); // concurrency
            	mapLock.unlock(); // release mapLock first
                dllLock.unlock();
            }
        }

        public int top() {
        	//lock.lock(); // concurrency
       	 	dllLock.lock(); // concurrency
            try {
                return tail.prev.val;
            } finally {
            	//lock.unlock(); // concurrency
                dllLock.unlock();
            }
        }

        public int peekMax() {
        	//lock.lock(); // concurrency
            mapLock.lock(); // concurrency
            try {
                return map.lastKey();
            } finally {
            	//lock.unlock(); // concurrency
            	mapLock.unlock();
            }
        }

        public int popMax() {
        	// Must lock both in same order as push/pop to prevent deadlock
        	//lock.lock(); // concurrency
       	 	dllLock.lock(); // concurrency -- dll lock first
            mapLock.lock(); // concurrency
            try {
                int max = map.lastKey();
                List<Node> list = map.get(max);
                Node node = list.remove(list.size() - 1);
                if (list.isEmpty()) map.remove(max);
                removeNode(node);
                return max;
            } finally {
            	//lock.unlock(); // concurrency
            	mapLock.unlock(); // release mapLock first
                dllLock.unlock();
            }
        }
    }
    
    
}