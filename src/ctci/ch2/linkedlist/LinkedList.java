package ctci.ch2.linkedlist;
public class LinkedList {
	
	public Node head;
	public Node tail;
	
	public LinkedList() {
	}
	
	public void add(int d) {
		if(this.head == null) {
			Node node = new Node(d);
			head = node;
			tail = node;
		
		} else {
			if(tail.next == null) {
				tail.next = new Node(d);
				tail = tail.next;
				return;
			}
			while(tail.next != null) {
				if(tail.next == null) {
					tail.next = new Node(d);
					tail = tail.next;
					return;
				} else {
					tail = tail.next;
				}
			}
		}
		
	}
	
	public void remove(int d) {
		
		Node prevNode = null;
		Node currNode = head;
		
		if(currNode == null) {
			System.out.println(">>Empty list....");
			return;
		}
		
		//--First node
		if(currNode.next == null) {
			if(currNode.data == d) {
				currNode = null;
				head = null;
				tail = null;
				System.out.println(">>Deleted node: "+d);
				return;
			} else {
				System.out.println(">>Data not found.");
				return;
			}
		}
		
		prevNode = currNode;

		while (currNode.next != null) {
			if(currNode.data == d) {
				
				if(head.equals(currNode)) { // && prevNode.data == currNode.data) {
					head = currNode.next;
					return;
					
				} else if(currNode.next == null) {
					
					prevNode.next = null;
					tail = prevNode;
				} 
				else {
					prevNode.next = currNode.next;
				}
				
				//break;
			}
			prevNode = currNode;
			currNode = currNode.next;
		}
		
		if(currNode.next == null && currNode.data == d) {
			prevNode.next = null;
			tail = prevNode;
		}
	}
	
	public int size() {
		int count = 0;
		
		LinkedList.Node counterNode = this.head;
		
		while (counterNode != null) {
			count++;
			counterNode = counterNode.next;
		}
		return count;
	}
	
	public String toString() {
		String sList = "[";
		
		if(head == null) {
			sList += "]";
			return sList;
		}
		
		Node tempNode = head;
		while(tempNode.next != null) {
			sList += tempNode.data;
			sList += ", ";
			tempNode = tempNode.next;
		}
		if(tempNode.next == null) {
			sList += tempNode.data;
		}
		
		sList += "]";
		return sList;
	}
	
	public class Node {
		
		public int data;
		public Node next;
		
		Node(){}
		
		Node(int d) {
			this.data = d;
			this.next = null;
		}
	}
}