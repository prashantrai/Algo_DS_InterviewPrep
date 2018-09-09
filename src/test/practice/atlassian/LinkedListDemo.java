package test.practice.atlassian;

import java.util.Scanner;

public final class LinkedListDemo {

	private static int size;
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		
		int listItem;
		LinkedListNode head = null;
		LinkedListNode tail = null;
		
		for(int i = 0; i<5; i++) {
			listItem = in.nextInt();
            if(i == 0) {
            	head = add(head, tail, listItem);
            	tail = head;
	        }
	        else {
	        	tail = add(head, tail, listItem);
	        }
        }
		
		display(head);
	}

	
	public static LinkedListNode add(LinkedListNode head, LinkedListNode tail, int val) {
		
		if(head == null) {
			head = new LinkedListNode(val);
			tail = head;
		} else {
			tail.next = new LinkedListNode(val);
			tail = tail.next;
		}
		size++;
		return tail;
	}
	
	public static void display(LinkedListNode node) {
		while(node != null) {
			System.out.print(node.val+" -> ");
			node = node.next;
		}
	}
	
	public int size() {
		return size;
	}
	
	public static class LinkedListNode {
		
		int val;
		LinkedListNode next;
		
		public LinkedListNode(int val) {
			this.val = val;
			next = null;
		}
	}
	
}
