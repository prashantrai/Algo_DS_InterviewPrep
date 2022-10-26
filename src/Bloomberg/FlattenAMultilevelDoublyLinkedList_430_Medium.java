package Bloomberg;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class FlattenAMultilevelDoublyLinkedList_430_Medium {

	public static void main(String[] args) {
		Node n2 = new Node(2);
		Node n3 = new Node(3);
		
		Node head = new Node(1, null, n2, n3); //Node(val, prev, next, child)
		
		n2.prev = head;
		
		Node result_Head = flatten(head);
		
		System.out.println("Expected: 1<->3<->2, Actual: ");
		print(result_Head);
	}

	public static Node flatten(Node head) {

		// return flatten_iterative(head);
		// return flatten_Iterative_LeetPremium(head);
		return flatten_recursive(head);
	}

	// DFS by Iteration
	// Time and space: O(N)
	private static Node flatten_iterative(Node head) {

		Node curr = head;
		Stack<Node> stk = new Stack<>();

		while (curr != null) {
			if (curr.child != null) {
				if (curr.next != null)
					stk.push(curr.next);

				// Now we have to make child node to next node of curr
				// 1. make child node next poitner of curr from child pointer
				curr.next = curr.child;
				// 2. update child prev to point to curr next from curr child
				curr.child.prev = curr;
				// remove child pointer by marking it as null
				curr.child = null;
			}

			/*
			 * When curr next is null we have element in Stack then pop it from stack and
			 * make it next of curr.
			 * 
			 * When this happens? Say if we found a child node then before we iterate we
			 * push the curr's next node to stack as that has to become next of tail node of
			 * child. So, after adding in stack we iterate child till end where child.next
			 * is null then we pop from stack and make that as next element of child's tail
			 * node
			 */
			if (curr.next == null && !stk.isEmpty()) {
				// Node temp = stk.pop();
				curr.next = stk.pop();
				curr.next.prev = curr;
			}

			curr = curr.next;
		}
		return head;
	}

	// Time and space: O(N)
	static Node tail = null;

	public static Node flatten_recursive(Node head) {

		if (head == null)
			return null;

		// we always maintain a tail pointer which will be node till where we have
		// already iterated/visited

		// now, current node (passed as head) point to previous i.e. tail
		head.prev = tail;
		tail = head; // make current node as tail

		Node nextNode = head.next; // store the next pointer before iterating child
		head.next = flatten(head.child);
		head.child = null; // remove child link

		//after child visit done explore/iterate for the nextNode and add to tail
		tail.next = flatten(nextNode);  

		return head;

	}

	// Leetcode Premium Solution
	// Time and space: O(N)
	public static Node flatten_Iterative_LeetPremium(Node head) {
		if (head == null)
			return head;

		Node pseudoHead = new Node(0, null, head, null);
		Node curr, prev = pseudoHead;

		Deque<Node> stack = new ArrayDeque<>();
		stack.push(head);

		while (!stack.isEmpty()) {
			curr = stack.pop();
			prev.next = curr;
			curr.prev = prev;

			if (curr.next != null)
				stack.push(curr.next);
			if (curr.child != null) {
				stack.push(curr.child);
				// don't forget to remove all child pointers.
				curr.child = null;
			}
			prev = curr;
		}
		// detach the pseudo node from the result
		pseudoHead.next.prev = null;
		return pseudoHead.next;
	}
	
	private static class Node {
	    public int val;
	    public Node prev;
	    public Node next;
	    public Node child;
	    
	    public Node(int val) { this.val = val; }
	    public Node(int _val, Node _prev, Node _next, Node _child) {
	        val = _val;
	        prev = _prev;
	        next = _next;
	        child = _child;
	    }
	}
	
	
	private static void print(Node node) {
		while(node != null) {
			System.out.print(node.val);
			if(node.next != null)
				System.out.print("<->");
			
			node = node.next;
		}
	}

}
