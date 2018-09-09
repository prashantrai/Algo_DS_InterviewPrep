package test.practice.ebay;

import ctci.ch2.linkedlist.LinkedList;

public class DeleteMidNodeInListDemo {

	public static void main(String[] args) {


		LinkedList list = new LinkedList();
		list.add(1);
		list.add(12);
		list.add(3);
		list.add(4);
		list.add(5);
		
		LinkedList.Node node = list.head;

		/*We iterate till any node (here till 3) because
		 *  In problem we have access to the node to be deleted only (i.e. no head access)
		 * 
		 */
		while(node.data != 3) {  
			node = node.next;
		}
		
		deleteMidNode(node);
		
		System.out.println("list:: "+list);
		
	}
	
	
	public static boolean deleteMidNode (LinkedList.Node node) {
		
		if(node == null || node.next == null) 
			return false;
		
		node.data = node.next.data;
		node.next = node.next.next;
		
		return true;
		
	}

}
