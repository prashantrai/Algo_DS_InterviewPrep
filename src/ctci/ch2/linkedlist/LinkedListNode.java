package ctci.ch2.linkedlist;


public class LinkedListNode {

	public LinkedListNode next;
	public int data;
	
	public LinkedListNode(){}
	public LinkedListNode(int data) {
		this.data = data;
	}
	
	public void setNext(LinkedListNode n) {
		next = n;
	}

}