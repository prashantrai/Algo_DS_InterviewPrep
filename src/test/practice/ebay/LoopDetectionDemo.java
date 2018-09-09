package test.practice.ebay;

import ctci.ch2.linkedlist.LinkedList;
import ctci.ch2.linkedlist.LinkedList.Node;

public class LoopDetectionDemo {

	public static void main(String[] args) {

		LinkedList l1 = new LinkedList();
		l1.add(11);
		l1.add(12);
		l1.add(1);
		l1.add(2);
		l1.add(3);
		l1.add(4);
		l1.add(5);
		l1.add(6);
		l1.add(7);
		l1.add(8);
		l1.add(9);
		l1.add(10);
		l1.add(11);
		
		//--Create intersecting Linked Lists
		createLoop(l1.head);
		
		//System.out.println("l1: "+l1);
		
		LinkedList.Node node = detectLoop(l1.head);
		
//		LinkedList.Node node = findIntersection(l1.head, l2.head);
		if (node != null)
			System.out.println(">>> RESULT : " + node.data);
		else 
			System.err.println(">>> RESULT : No loop detected");
		
	}
	
	private static Node detectLoop(LinkedList.Node head) {
		
		LinkedList.Node slow = head;
		LinkedList.Node fast = head;
		
		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;

			if(slow == fast) {
				//return slow;
				break;
			}
		}
		
		System.out.println(">> collision at : " + slow.data);
		
		//--get start of loop
		fast = head;
		while (head != null) {
			
			fast = fast.next;
			slow = slow.next;

			if(fast == slow) {
				
				System.out.println(">> loop starts at : " + slow.data);
				break;
			}
			
		}
		
		return slow;
	}


	public static void createLoop(LinkedList.Node node) {
		
		LinkedList.Node node4 = null;
		while(node != null) {
			
			if(node.data == 4) {
				node4 = node;
			}
			
			if(node.next == null) {
				node.next = node4;
				break;
			}
			node = node.next;
		}
	}
	

}
