package test.practice.ebay;

import ctci.ch2.linkedlist.LinkedList;

public class ReturnKthToLastDemo {

	public static void main(String[] args) {

		LinkedList list = new LinkedList();
		list.add(1);
		list.add(12);
		list.add(3);
		list.add(4);
		list.add(5);
		
		//System.out.println("size = ");
		
		System.out.println(list);
		
		printKthToLastREC(list.head, 4);
		printKthToLast(list.head, 4);
		
	}
	
	public static void printKthToLast(LinkedList.Node node, int n) {
		
		LinkedList.Node curr = node;
		LinkedList.Node fast = node;
		
		for(int i=0; i<n; i++) {
			fast = fast.next;
		}
		
		while(fast != null) {
			curr = curr.next;
			fast = fast.next;
		}
		
		System.out.println("Kth to the last is :: " + curr.data) ;
	}
	
	public static int printKthToLastREC(LinkedList.Node node, int n) {
		
		if(node == null) 
			return 0;
		
		int index = printKthToLastREC(node.next, n) + 1;

		if(index == n) {
			System.out.println("Kth to the last is :: " + node.data) ;
		}
		
		return index;
	}

}
