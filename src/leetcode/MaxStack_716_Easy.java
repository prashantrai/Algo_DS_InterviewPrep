package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.TreeMap;

public class MaxStack_716_Easy {

	public static void main(String[] args) {

		MaxStack stack = new MaxStack();
		stack.push(5); 
		stack.push(1);
		stack.push(5);
		System.out.println(stack.top()); //-> 5
		System.out.println(stack.popMax()); //-> 5
		System.out.println(stack.top()); //-> 1
		System.out.println(stack.peekMax()); //-> 5
		System.out.println(stack.pop()); //-> 1
		System.out.println(stack.top()); //-> 5
	}

	/*
	 * Complexity Analysis
	 * 
	 * Time Complexity: O(logN) for all operations except peek which is
	 * O(1), where N is the number of operations performed. Most operations
	 * involving TreeMap are O(logN).
	 * 
	 * Space Complexity: O(N), the size of the data structures used.
	 */

	static class MaxStack {
		TreeMap<Integer, List<Node>> map;
		DoublyLinkedList dll;

		public MaxStack() {
			map = new TreeMap<>();
			dll = new DoublyLinkedList();
		}

		public void push(int val) {
			Node node = dll.add(val);
			if (!map.containsKey(val)) {
				map.put(val, new ArrayList<Node>());
			}
			map.get(val).add(node);
		}

		public int pop() {
			int val = dll.pop();
			List<Node> al = map.get(val);
			al.remove(al.size() - 1);
			if (al.isEmpty()) {
				map.remove(val);
			}
			return val;
		}

		public int top() {
			return dll.peek();
		}

		public int peekMax() {
			return map.lastKey();
		}

		public int popMax() {
			int max = peekMax();
			List<Node> al = map.get(max);
			Node node = al.get(al.size() - 1);
			al.remove(al.size() - 1);

			dll.unlink(node);

			if (al.isEmpty()) {
				map.remove(max);
			}
			return max;
		}
	}

	static class DoublyLinkedList {

		Node head;
		Node tail;

		public DoublyLinkedList() {
			head = new Node(0);
			tail = new Node(0);
			head.next = tail;
			tail.prev = head;
		}

		public Node add(int val) {
			Node node = new Node(val);
			node.next = tail;
			node.prev = tail.prev;
			tail.prev.next = node;
			tail.prev = node;

			return node;
		}

		public int peek() {
			return tail.prev.val;
		}

		public int pop() {
			return unlink(tail.prev).val;
		}

		public Node unlink(Node node) {
			node.prev.next = node.next;
			node.next.prev = node.prev;

			return node;
		}
	}

	static class Node {
		int val;
		Node prev, next;

		public Node(int val) {
			this.val = val;
		}
	}

}

//--Leet Code: Another Easy solution but time Complexity changes for this 

/*
 * Complexity Analysis
 * 
 * Time Complexity: O(N) for the popMax operation, and O(1)O(1) for the other
 * operations, where N is the number of operations performed.
 * 
 * Space Complexity: O(N), the maximum size of the stack.
 */

class MaxStack_2 {
	Stack<Integer> stack;
	Stack<Integer> maxStack;

	public MaxStack_2() {
		stack = new Stack();
		maxStack = new Stack();
	}

	public void push(int x) {
		int max = maxStack.isEmpty() ? x : maxStack.peek();
		maxStack.push(max > x ? max : x);
		stack.push(x);
	}

	public int pop() {
		maxStack.pop();
		return stack.pop();
	}

	public int top() {
		return stack.peek();
	}

	public int peekMax() {
		return maxStack.peek();
	}

	public int popMax() {
		int max = peekMax();
		Stack<Integer> buffer = new Stack();
		while (top() != max)
			buffer.push(pop());
		pop();
		while (!buffer.isEmpty())
			push(buffer.pop());
		return max;
	}
}
