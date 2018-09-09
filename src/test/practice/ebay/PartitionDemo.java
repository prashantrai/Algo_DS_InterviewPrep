package test.practice.ebay;

import ctci.ch2.linkedlist.LinkedList;

public class PartitionDemo {

	public static void main(String[] args) {

		LinkedList list = new LinkedList();
		list.add(3);
		list.add(5);
		list.add(8);
		list.add(5);
		list.add(10);
		list.add(2);
		list.add(1);

//		LinkedList.Node node = partition(list, 5);
		
		LinkedList.Node node = partition_2(list.head, 5);
		//LinkedList.Node node = partition_3(list.head, 5);
		//list.head = node;

		while(node != null) {
			System.out.print(node.data + " -> ");
			node = node.next;
		}
		
	}
	
	
	public static LinkedList.Node partition(LinkedList list, int n) {
		
		LinkedList.Node node = list.head;

		LinkedList left = new LinkedList();
		LinkedList.Node leftHead = left.head;
		LinkedList.Node leftTail = left.tail;

		LinkedList right = new LinkedList();
		LinkedList.Node rightHead = right.head;
		LinkedList.Node rightTail = right.tail;

		while (node != null) {
			
			if(node.data < n) {
				left.add(node.data);
			} else {
				right.add(node.data);
			}
			node = node.next;
		}
		
		System.out.println("beforePartitionList = " + left);
		System.out.println("afterPartitionList = " + right);
		
		leftHead = left.head;
		leftTail = left.tail;
		rightHead = right.head;
		leftTail.next=rightHead;

		return leftHead;
	}

	public static LinkedList.Node partition_2(LinkedList.Node node, int n) {
		LinkedList.Node head = node;
		LinkedList.Node tail = node;
		
		while (node != null) {
			LinkedList.Node next = node.next;  //--get the next pointer, as in next step it changing and loop will go infinite
			
			if(node.data < n) {
				
				node.next = head;
				head = node;
				
			} else {
				tail.next = node;
				tail = node;
			}
			node = next;
//			node = node.next; ///will not work as node position has changed
		}
		tail.next = null;
		
		return head;
	}
 	
	
	static class LinkedListNode {
		int data;
		LinkedListNode next;
	}

}




