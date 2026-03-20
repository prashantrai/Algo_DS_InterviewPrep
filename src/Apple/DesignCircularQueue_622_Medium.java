package Apple;

import java.util.concurrent.locks.ReentrantLock;

public class DesignCircularQueue_622_Medium {
	
	public static void main(String[] args) {
        // Test 1: Example from problem statement
        System.out.println("Test 1: Basic operations");
        MyCircularQueue q1 = new MyCircularQueue(3);
        System.out.println(q1.enQueue(1)); // true
        System.out.println(q1.enQueue(2)); // true
        System.out.println(q1.enQueue(3)); // true
        System.out.println(q1.enQueue(4)); // false (full)
        System.out.println(q1.Rear());     // 3
        System.out.println(q1.isFull());   // true
        System.out.println(q1.deQueue());  // true
        System.out.println(q1.enQueue(4)); // true
        System.out.println(q1.Rear());     // 4
        System.out.println();

        // Test 2: Operations on empty queue
        System.out.println("Test 2: Empty queue behavior");
        MyCircularQueue q2 = new MyCircularQueue(2);
        System.out.println(q2.deQueue());  // false
        System.out.println(q2.Front());    // -1
        System.out.println(q2.Rear());     // -1
        System.out.println(q2.isEmpty());  // true
        System.out.println();

        // Test 3: Capacity = 1 edge case
        System.out.println("Test 3: Capacity = 1");
        MyCircularQueue q3 = new MyCircularQueue(1);
        System.out.println(q3.enQueue(10)); // true
        System.out.println(q3.enQueue(20)); // false (already full)
        System.out.println(q3.Front());     // 10
        System.out.println(q3.Rear());      // 10
        System.out.println(q3.isFull());    // true
        System.out.println(q3.deQueue());   // true
        System.out.println(q3.isEmpty());   // true
        System.out.println(q3.Front());     // -1
        System.out.println();

        // Test 4: Wrap-around behavior
        System.out.println("Test 4: Wrap-around");
        MyCircularQueue q4 = new MyCircularQueue(3);
        System.out.println(q4.enQueue(1)); // true
        System.out.println(q4.enQueue(2)); // true
        System.out.println(q4.enQueue(3)); // true (queue is now full)
        System.out.println(q4.deQueue());  // true (remove 1)
        System.out.println(q4.enQueue(4)); // true (should go to index 0)
        System.out.println(q4.Front());    // 2
        System.out.println(q4.Rear());     // 4
        System.out.println(q4.deQueue());  // true (remove 2)
        System.out.println(q4.deQueue());  // true (remove 3)
        System.out.println(q4.deQueue());  // true (remove 4)
        System.out.println(q4.deQueue());  // false (now empty)
    }
	
	
	
	/* Follow-up: point out a potential defect about the implementation and the solution.
	 * 
	 * This time, it is not about the space or time complexity, but concurrency. 
	 * Our circular queue is NOT thread-safe. One could end up with corrupting 
	 * our data structure in a multi-threaded environment. 
	 * 
	 * Enqueue is not thread-safe and can introduce race conditions. 
	 * 
	 * */
	
	// Time: All operations are O(1) time 
	// Space: O(k) for the internal array.
	static class MyCircularQueue {
	
	    private int[] data;
	    private int front;
	    private int rear;
	    private int size;
	    private int capacity;
	
	    public MyCircularQueue(int k) {
	        data = new int[k];
	        capacity = k;
	    }
	
	    public boolean enQueue(int value) {
	        if (isFull())
	            return false;
	
	        // Put the value at the free slot
	        data[rear] = value;
	
	        // That slot is now used, so the next free slot is one step ahead
	        rear = (rear + 1) % capacity; // rear+1, because rear starts from 0
	        size++;
	
	        return true;
	    }
	
	    public boolean deQueue() {
	        if (isEmpty())
	            return false;
	
	        /*
	            Think of front as a pointer to “where the current first element lives” 
	            in the array.
	            When we deQueue, we are removing that first element.
	            Instead of physically shifting all elements left in the array, 
	            we just say:
	            “The new front is the next position in the circle.”
	            That “next position” is front + 1 (with    wrap-around).                                                                                                   */
	        // We remove the front element (logically).
	        // The new front is the next element, one step ahead:
	        front = (front + 1) % capacity;
	        size--;
	
	        return true;
	    }
	
	    public int Front() {
	        if (isEmpty())
	            return -1;
	
	        return data[front];
	    }
	
	    public int Rear() {
	        if (isEmpty())
	            return -1;
	
	        /*
	        Why (rear - 1)?
	            Remember:
	            rear = next free slot, because we move the rear to next 
	            free slot (i.e. where next element can be added) when we 'enQueue'
	            So the last element is exactly one step before rear (i.e. 
	            a location where we added last value).
	        
	        Why add capacity?
	            Problem: what if rear = 0?
	            capacity = 5, rear = 0
	            Last element index should be 4 (wrap around to the end).
	            But rear - 1 = -1 → invalid index.
	        
	            Mathematically, in modular arithmetic:
	            -1 mod 5 ≡ 4
	            But in Java: -1 % 5 == -1, still negative.
	        
	            So we adjust before taking %:
	            (rear - 1 + capacity) % capacity
	            
	            Example with rear = 0, capacity = 5:
	            (0 - 1 + 5) % 5 = 4 % 5 = 4
	        
	            So: 
	            rear - 1 → go one step back.
	            + capacity → avoid negative.
	            % capacity → keep result in range [0, capacity - 1].
	        */
	        int lastIndex =  (rear - 1 + capacity) % capacity;
	
	        return data[lastIndex];
	    }
	
	    public boolean isEmpty() {
	        return size == 0;
	    }
	
	    public boolean isFull() {
	        return size == capacity;
	    }
	}
	
	
	
	/** Thread-safe version 
	 * 
	 * Interview-script: 
	 * The original circular queue is not thread-safe: multiple threads 
	 * can interleave updates to front, rear, and size, corrupting the data structure.
	 * To fix this, I’d add a ReentrantLock and wrap each method in a 
	 * lock.lock()/try/finally/lock.unlock() pattern. That makes each 
	 * operation atomic and also provides the necessary memory visibility 
	 * guarantees.
	 * 
	 * In real production code, if I just need a bounded, thread-safe queue, 
	 * I’d usually use ArrayBlockingQueue from java.util.concurrent, which 
	 * is a well-tested circular buffer with internal locking.
	 * 
	 * */

	class MyCircularQueue_ThreadSafe {
	    private final int[] data;
	    private int front;
	    private int rear;
	    private int size;
	    private final int capacity;

	    // Single lock to protect the whole queue
	    private final ReentrantLock lock = new ReentrantLock();

	    public MyCircularQueue_ThreadSafe(int k) {
	        this.capacity = k;
	        this.data = new int[k];
	        this.front = 0;
	        this.rear = 0;
	        this.size = 0;
	    }

	    public boolean enQueue(int value) {
	        lock.lock();
	        try {
	            if (isFullInternal()) {
	                return false;
	            }
	            data[rear] = value;
	            rear = (rear + 1) % capacity;
	            size++;
	            return true;
	        } finally {
	            lock.unlock();
	        }
	    }

	    public boolean deQueue() {
	        lock.lock();
	        try {
	            if (isEmptyInternal()) {
	                return false;
	            }
	            front = (front + 1) % capacity;
	            size--;
	            return true;
	        } finally {
	            lock.unlock();
	        }
	    }

	    public int Front() {
	        lock.lock();
	        try {
	            if (isEmptyInternal()) {
	                return -1;
	            }
	            return data[front];
	        } finally {
	            lock.unlock();
	        }
	    }

	    public int Rear() {
	        lock.lock();
	        try {
	            if (isEmptyInternal()) {
	                return -1;
	            }
	            int lastIndex = (rear - 1 + capacity) % capacity;
	            return data[lastIndex];
	        } finally {
	            lock.unlock();
	        }
	    }

	    public boolean isEmpty() {
	        lock.lock();
	        try {
	            return isEmptyInternal();
	        } finally {
	            lock.unlock();
	        }
	    }

	    public boolean isFull() {
	        lock.lock();
	        try {
	            return isFullInternal();
	        } finally {
	            lock.unlock();
	        }
	    }

	    // Helpers, only called while holding the lock
	    private boolean isEmptyInternal() {
	        return size == 0;
	    }

	    private boolean isFullInternal() {
	        return size == capacity;
	    }
	}
	
	
	
	/** LinkedList Version */
	
	static class MyCircularQueue_LinkedList {

	    private static class Node {
	        int val;
	        Node next;
	        Node(int val) {
	            this.val = val;
	        }
	    }

	    private Node head;      // front of the queue
	    private Node tail;      // rear of the queue (last element)
	    private int size;       // current number of elements
	    private final int capacity; // maximum allowed size

	    public MyCircularQueue_LinkedList(int k) {
	        this.capacity = k;
	        this.head = null;
	        this.tail = null;
	        this.size = 0;
	    }

	    public boolean enQueue(int value) {
	        if (isFull()) {
	            return false;
	        }
	        Node newNode = new Node(value);
	        if (isEmpty()) {
	            head = tail = newNode;
	        } else {
	            tail.next = newNode;
	            tail = newNode;
	        }
	        size++;
	        return true;
	    }

	    public boolean deQueue() {
	        if (isEmpty()) {
	            return false;
	        }
	        head = head.next;
	        if (head == null) {
	            // queue is now empty
	            tail = null;
	        }
	        size--;
	        return true;
	    }

	    public int Front() {
	        if (isEmpty()) {
	            return -1;
	        }
	        return head.val;
	    }

	    public int Rear() {
	        if (isEmpty()) {
	            return -1;
	        }
	        return tail.val;
	    }

	    public boolean isEmpty() {
	        return size == 0;
	    }

	    public boolean isFull() {
	        return size == capacity;
	    }
	}
	
}
