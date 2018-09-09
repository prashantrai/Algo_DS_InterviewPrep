package test.linkedlist;

public class LinkedListDemo {

	public static void main(String[] args) {
		
		LinkedList l = new LinkedList();
		l.add("A");
		l.add("AA"); 
		l.add("BB");
		l.add("BBBB");
		
		System.out.println(l.get(3));
	}

}


class LinkedList {
	
	Node head;
	Node tail;
	
	public LinkedList() {}

	public void add(Object v) {
		
		Node newNode = crateNode(v);

		//-If first node
		if(head == null) {
			head = newNode;
			tail = head;
		} else {
			tail.next = newNode;
			tail = newNode;
		}
		
	}
	
	public Object get(int index) {
		int count = 0;
		//System.out.println("->"+node);
		Node node = head;
		
		while(node != null) {
			System.out.println("->cout="+count +", index="+index);
			if(count == index) {
				System.out.println(">> Element found at index "+index+"");
				return node.data;
			}
			node = node.next;
			++count;
		}
		
		System.out.println(">> Element not found.");
		return null;
	}
	
	public void createCircularList() {
		tail.next = head;
	}
	
	public Node crateNode(Object v) {
		Node node =  new Node();
		node.data = v;
		return node;
	}
	
	public Node head() {
		return tail;
	}
	
	public Node tail() {
		return tail;
	}
	
	public String toString() {
		
		String data = "[ ";
		while (head != null) {
			data += (String) head.data;
			if(head.next != null) {
				data += " -> ";
			}
			head = head.next;
		}
		
		return data+"]";
		
	}
	
	class Node{
		
		Object data;
		Node next;
		
		public Node() {
			
		}
		
	}
	
}


