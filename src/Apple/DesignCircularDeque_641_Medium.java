package Apple;


public class DesignCircularDeque_641_Medium {
	
	public static void main(String[] args) {
        // ---------- Test 1: insertLast on empty ----------
        System.out.println("=== Test 1: insertLast on empty ===");
        MyCircularDeque dq1 = new MyCircularDeque(3);
        System.out.println(dq1.insertLast(1));   // true
        System.out.println(dq1.getFront());      // expect 1
        System.out.println(dq1.getRear());       // expect 1

        // ---------- Test 2: insertFront on empty ----------
        System.out.println("\n=== Test 2: insertFront on empty ===");
        MyCircularDeque dq2 = new MyCircularDeque(3);
        System.out.println(dq2.insertFront(5));  // true
        System.out.println(dq2.getFront());      // expect 5
        System.out.println(dq2.getRear());       // expect 5

        // ---------- Test 3: fill to full, then wrap-around ----------
        System.out.println("\n=== Test 3: fill, delete, wrap-around ===");
        MyCircularDeque dq3 = new MyCircularDeque(3);
        dq3.insertLast(1);
        dq3.insertLast(2);
        dq3.insertLast(3);
        System.out.println(dq3.isFull());        // expect true
        System.out.println(dq3.getFront());      // expect 1
        System.out.println(dq3.getRear());       // expect 3

        System.out.println(dq3.deleteFront());   // true, remove 1
        System.out.println(dq3.insertLast(4));   // true, wrap-around
        System.out.println(dq3.getFront());      // expect 2
        System.out.println(dq3.getRear());       // expect 4

        // ---------- Test 4: capacity 1 deque ----------
        System.out.println("\n=== Test 4: capacity 1 ===");
        MyCircularDeque dq4 = new MyCircularDeque(1);
        System.out.println(dq4.isEmpty());       // expect true
        System.out.println(dq4.insertLast(10));  // true
        System.out.println(dq4.isFull());        // expect true
        System.out.println(dq4.getFront());      // expect 10
        System.out.println(dq4.insertFront(20)); // expect false (full)
        System.out.println(dq4.deleteLast());    // true
        System.out.println(dq4.isEmpty());       // expect true

        // ---------- Test 5: delete on empty ----------
        System.out.println("\n=== Test 5: delete on empty ===");
        MyCircularDeque dq5 = new MyCircularDeque(2);
        System.out.println(dq5.deleteFront());   // expect false
        System.out.println(dq5.deleteLast());    // expect false
    }
	
	
	// Time: All operations are O(1) time 
	// Space: O(k) for the internal array.
	static class MyCircularDeque {

	    int[] data;
	    int front;
	    int rear;
	    int size;
	    int capacity;

	    public MyCircularDeque(int k) {
	        data = new int[k];
		    capacity = k;
	    }
	    
	    public boolean insertFront(int value) {
	        if(isFull()) return false;
	        if(isEmpty()) {
	            front = rear = 0;
	        } else {
	            // In an array, “before” means one index lower, 
	            // that's why (front - 1).
	            front = (front - 1 + capacity) % capacity;
	        }
	        data[front] = value;
	        size++;
	        return true;
	    }
	    
	    public boolean insertLast(int value) {
	        if(isFull()) return false;

	        if(isEmpty()) {
	            front = rear = 0;
	        } else {
	            rear = (rear + 1) % capacity;
	        }
	        data[rear] = value;
	        size++;
	        return true;
	    }
	    
	    public boolean deleteFront() {
	        if (isEmpty())
		        return false;
	        
	        front = (front + 1) % capacity;
	        size--;
	        return true;
	    }
	    
	    public boolean deleteLast() {
	        if (isEmpty())
		        return false;
	        
	        rear = (rear - 1 + capacity) % capacity;
	        size--;

	        return true;
	    }
	    
	    public int getFront() {
	        if(isEmpty()) return - 1;
	        return data[front];
	    }
	    
	    public int getRear() {
	        if(isEmpty()) return - 1;
	        return data[rear];
	    }
	    
	    public boolean isEmpty() {
	        return size == 0;
	    }
	    
	    public boolean isFull() {
	        return size == capacity;
	    }
	}
	
	// Using LinkedList
	// Time: All operations are O(1) time 
	// Space: O(k) .
	static class MyCircularDeque_UsingLinkedList {

	    private static class Node {
	        int val;
	        Node prev, next;
	        Node(int v) { val = v; }
	    }

	    private final int capacity;
	    private int size;
	    private final Node head; // dummy head
	    private final Node tail; // dummy tail

	    public MyCircularDeque_UsingLinkedList(int k) {
	        this.capacity = k;
	        this.size = 0;
	        head = new Node(-1);
	        tail = new Node(-1);
	        head.next = tail;
	        tail.prev = head;
	    }
	    
	    public boolean insertFront(int value) {
	        if (isFull()) return false;
	        Node node = new Node(value);
	        Node first = head.next;

	        head.next = node;
	        node.prev = head;
	        node.next = first;
	        first.prev = node;

	        size++;
	        return true;
	    }
	    
	    public boolean insertLast(int value) {
	        if (isFull()) return false;
	        Node node = new Node(value);
	        Node last = tail.prev;

	        last.next = node;
	        node.prev = last;
	        node.next = tail;
	        tail.prev = node;

	        size++;
	        return true;
	    }
	    
	    public boolean deleteFront() {
	        if (isEmpty()) return false;
	        Node first = head.next;
	        Node second = first.next;

	        head.next = second;
	        second.prev = head;

	        size--;
	        return true;
	    }
	    
	    public boolean deleteLast() {
	        if (isEmpty()) return false;
	        Node last = tail.prev;
	        Node secondLast = last.prev;

	        secondLast.next = tail;
	        tail.prev = secondLast;

	        size--;
	        return true;
	    }
	    
	    public int getFront() {
	        if (isEmpty()) return -1;
	        return head.next.val;
	    }
	    
	    public int getRear() {
	        if (isEmpty()) return -1;
	        return tail.prev.val;
	    }
	    
	    public boolean isEmpty() {
	        return size == 0;
	    }
	    
	    public boolean isFull() {
	        return size == capacity;
	    }
	}
	
}
