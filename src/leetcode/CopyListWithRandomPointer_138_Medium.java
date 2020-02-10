package leetcode;

import java.util.HashMap;
import java.util.Map;

public class CopyListWithRandomPointer_138_Medium {

	// --https://leetcode.com/problems/copy-list-with-random-pointer/
	// --https://leetcode.com/problems/copy-list-with-random-pointer/discuss/501188/Java-100-(such-a-confusing-question)
	//--https://leetcode.com/problems/copy-list-with-random-pointer/discuss/502920/Very-simple-solution.-Faster-than-100-of-Java-online-solutions.
	
	//--My solution: https://leetcode.com/problems/copy-list-with-random-pointer/discuss/504289/Java-Solution%3A-Inspired-by-the-earlier-post-just-simplified-the-steps-little

	public static void main(String[] args) {
		Node n1 = new Node(7);
		Node n2 = new Node(13);
		Node n3 = new Node(11);
		Node n4 = new Node(10);
		Node n5 = new Node(1);

		n1.next = n2;
		n1.random = null;

		n2.next = n3;
		n2.random = n1;

		n3.next = n4;
		n3.random = n5;

		n4.next = n5;
		n4.random = n3;

		n5.next = null;
		n5.random = n1;
		
		//--print input list
		Node temp = n1;
		while (temp != null) {
			System.out.print(temp);
			if(temp.next != null) {
				System.out.print(" -> ");
			}
			temp = temp.next;
		}
		System.out.println("");
		Node head = copyRandomList(n1);
		
		//--print cloned list
		while (head != null) {
			System.out.print(head);
			if(head.next != null) {
				System.out.print(" -> ");
			}
			head = head.next;
		}

	}
	
	/**
	 *  Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
	 *	Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]
	 * 
	 */
	
	public static Node copyRandomList (Node head) {
		
		if (head == null) {
			return null;
		}
		
		Map<Node, Node> map= new HashMap<>();
		
		Node curr = head;
		Node res = new Node(curr.val, null, null);
		
		
		/* Pass 1: Just Iterate the List and map all the node to their copy of new nodes 
		 * i.e. only value, next and random will be null for new nodes 
		 * */ 
		while (curr != null) {
			Node node = new Node(curr.val, null, null);
			map.put(curr, node);
			curr = curr.next;
		}
		
		curr = head;
		Node new_head;

		//--Pass 2 : interweave the new nodes by iterating the list again and accessing new nodes from map 
		while (curr != null) {
			new_head = map.get(curr);
			Node next = map.get(curr.next);
			Node random = map.get(curr.random);
			
			new_head.next = next;
			new_head.random = random; 
		
			new_head = new_head.next;
			curr = curr.next;
		}
		res = map.get(head);
		return res;
	}

	private static class Node {
		int val;
		Node next;
		Node random;

		public Node(int val) {
			this.val = val;
			this.next = null;
			this.random = null;
		}
		public Node(int val, Node next, Node random) {
			this.val = val;
			this.next = next;
			this.random = random;
		}
		
		public String toString() {
			return "[" + val 
					+",next:"+ (next != null ? ""+next.val : "null")
					+",random:"+ (random != null ? ""+random.val : "null")
					+ "]";
		}
	
		
	}

}
