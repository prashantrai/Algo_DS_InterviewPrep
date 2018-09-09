package test.practice.ebay;

import ctci.ch2.linkedlist.LinkedList;

public class IntersectionListDemo {

	public static void main(String[] args) {

		LinkedList l1 = new LinkedList();
		l1.add(1);
		l1.add(2);
		l1.add(3);
		l1.add(4);
		l1.add(5);
		l1.add(6);
		l1.add(7);
		
		LinkedList l2 = new LinkedList();
		l2.add(11);
		l2.add(12);
		//l2.add(13);
		
		//--Create intersecting Linked Lists
		createIntersectingNode(l1.head, l2.head);
		
		System.out.println("l1: "+l1);
		System.out.println("l2: "+l2);
		
		LinkedList.Node node = findIntersection(l1.head, l2.head);
		if (node != null)
			System.out.println(">>> RESULT : " + node.data);
		else 
			System.err.println(">>> RESULT : No intersection found");
		
	}

	public static LinkedList.Node findIntersection(LinkedList.Node l1, LinkedList.Node l2) {
		
		//Get length and check if they have the same tail
		Result l1_res = getTailAndLength(l1);
		Result l2_res = getTailAndLength(l2);
		
		if(l1_res.tail != l2_res.tail) {
			return null;
		}
		
		LinkedList.Node shorter = l1_res.size > l2_res.size ? l2 : l1; 
		LinkedList.Node longer  = l1_res.size > l2_res.size ? l1 : l2;

		longer = moveForwardByDiff(longer, Math.abs(l1_res.size - l2_res.size));
		
		while (longer != null) {
			
			if(longer == shorter) {
				return longer;
			}
			
			shorter = shorter.next;
			longer = longer.next;
		}
		
		return null;
	}
	
	public static LinkedList.Node moveForwardByDiff(LinkedList.Node node, int diff) {
		
		for (int i=0;i<diff;i++) {
			node = node.next;
		}
		return node;
	}
	
	public static Result getTailAndLength(LinkedList.Node node) {
		
		int size = 0;
		
		while (node != null) {
			size++;
			node = node.next;
		}
		
		return new Result(node, size);
		
	}
	
	public static void createIntersectingNode(LinkedList.Node node1, LinkedList.Node node2) {
		
		while(node2.next != null) {
			node2 = node2.next;
		}
		
		while (node1 != null) {
			
			if(node1.data == 5) {
				node2.next = node1;
				break;
			}
			node1 = node1.next;
		}
		
	}

	static class Result {
		LinkedList.Node tail;
		int size;
		
		public Result(LinkedList.Node tail, int size) {
			this.tail = tail;
			this.size = size;
		}
		
	}
	
}


