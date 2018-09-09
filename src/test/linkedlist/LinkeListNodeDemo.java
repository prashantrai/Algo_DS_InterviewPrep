package test.linkedlist;

public class LinkeListNodeDemo {

	public static void main(String[] args) {

	}

}

class LinkedListNode {
	
	public LinkedListNode prev, next, last;
	int data;
	
	public LinkedListNode(){}
	public LinkedListNode(int data) {
		this.data = data;
	}
	public LinkedListNode(int data, LinkedListNode prev, LinkedListNode next) {
		this.data =data;
		this.prev = prev;
		this.next = next;
	}
	
	public void setNext(LinkedListNode node){
		this.next = node;
		
		if(this == last) {
			last = node;
		}
		if(node != null && node.prev != this) {
			node.setPrevious(this);
		}
	}
	
	public void setPrevious(LinkedListNode node) {
		this.prev = node;
		
		if(node != null && node.prev != this) {
			node.setNext(this);
		}
	}
	
}
