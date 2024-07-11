package Facebook;

import java.util.HashMap;
import java.util.Map;

import leetcode.CopyListWithRandomPointer_138_Medium.Node;

public class CopyListWithRandomPointer_138_Medium {

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

		// --print input list
		Node temp = n1;
		while (temp != null) {
			System.out.print(temp);
			if (temp.next != null) {
				System.out.print(" -> ");
			}
			temp = temp.next;
		}
		System.out.println("");
		Node head = copyRandomList(n1);

		// --print cloned list
		while (head != null) {
			System.out.print(head);
			if (head.next != null) {
				System.out.print(" -> ");
			}
			head = head.next;
		}

	}

	// Time: O(n)
    // Space: O(n)
    public static Node copyRandomList(Node head) {
        if(head == null) return head;
        Map<Node, Node> map = new HashMap<>();
        Node curr = head;
        
        while(curr != null) {
            map.put(curr, new Node(curr.val));
            curr = curr.next;
        }
        
        curr = head;
        while(curr != null) {
            map.get(curr).next = map.get(curr.next);
            map.get(curr).random = map.get(curr.random);
            curr = curr.next;
        }
        
        return map.get(head);
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
			return "[" + val + ",next:" + (next != null ? "" + next.val : "null") + ",random:"
					+ (random != null ? "" + random.val : "null") + "]";
		}

	}
	
}
