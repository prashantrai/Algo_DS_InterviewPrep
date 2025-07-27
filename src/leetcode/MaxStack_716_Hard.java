package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


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

}

