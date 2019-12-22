package ctci.ch2.linkedlist;

import java.util.HashSet;
import java.util.Set;

public class RemoveDups {

	public static void main(String[] args) {

		LinkedList list = new LinkedList();
		fillList(list);
		
//		removeDupsWithExtraBuffer(list);
		removeDupsWithExtraBuffer_2(list);
		
//		removeDupsWithNoExtraBuffer(list);
		System.out.println(">> Dupes removed : "+ list);
		
	}

	
	
	
	
	// O(n^2)
	public static void removeDupsWithNoExtraBuffer_2(LinkedList list) {
		
		LinkedList.Node currNode = list.head;
		
		while (currNode != null) {
			LinkedList.Node runner = currNode;
			 while (runner.next != null) {
				 if(currNode.data == runner.next.data) {
					 runner.next = runner.next.next;
				 } else {
					 runner = runner.next;
				 }
			 }
			currNode = currNode.next;
		}
	}
	/* 1, 2, 2, 3, 3, 4, 5 
	 * 
	 * 1, 2, 3, 4, 2, 5
	 * 
	 * 1, 2, 3, 2, 2, 4
	 * 
	 * currNode = 2
	 * prevNode = 2
	 * runnerNode = 2
	 * 
	 * */
	public static void removeDupsWithNoExtraBuffer(LinkedList list) {
		
		LinkedList.Node currNode = list.head;
		
		while(currNode != null) {
			
			LinkedList.Node runnerNode = currNode;
			LinkedList.Node prevNode = currNode;
			
			while(runnerNode != null && runnerNode.next != null) {
				if(currNode.data == runnerNode.next.data) {
					
					if(currNode.data != prevNode.next.data)
						prevNode = runnerNode;
					
					runnerNode = runnerNode.next.next;
					
					if(runnerNode != null && currNode.data == runnerNode.data) {
						runnerNode = prevNode.next;
					} else {
						prevNode.next = runnerNode;
					}
					
				} else {
					runnerNode = runnerNode.next;  
				}
			}
			currNode = currNode.next;
		}
	}
	
	
	
	public static void removeDupsWithExtraBuffer_2(LinkedList list) {
		
		LinkedList.Node prevNode = null;
		LinkedList.Node currNode = list.head;
		
		Set<Integer> buffer = new HashSet<Integer>();
		
		while(currNode != null) {
			if(buffer.contains(currNode.data)) {
				prevNode.next = currNode.next;
			} else {
				prevNode = currNode;
				buffer.add(currNode.data);
			}
			currNode = currNode.next;
		}
		
	}
	
	public static void removeDupsWithExtraBuffer(LinkedList list) {
		
		 Set<Integer> buffer = new HashSet<Integer>();
		
		 LinkedList.Node prevNode = list.head;
		 LinkedList.Node currNode = prevNode;
		 
		 if(currNode.next == null) {
			 return;
		 } 
		 
		 while(currNode.next != null) {
			 
			 if(buffer.contains(currNode.data)) {
				 currNode = currNode.next;
				 prevNode.next = currNode;
			 } else {
				 buffer.add(currNode.data);
				 prevNode = currNode;
				 currNode = currNode.next;
			 }
			 
			 if(currNode.next == null && buffer.contains(currNode.data)) {
				 prevNode.next=null;
			 }
			 
			 /*if(buffer.contains(currNode.data)) {
				 prevNode = currNode.next;
				 
			 } else {
				 buffer.add(currNode.data);
				 prevNode = currNode;	
			 }*/
			 
		 }
		 
		 
		 System.out.println("result :: list : "+list);
	}
	
	
	
	public static void fillList(LinkedList list) {
		list.add(5);
		list.add(2);
		list.add(13);
		list.add(3);
		list.add(4);
		list.add(12);
		list.add(12);
		list.add(4);
		list.add(1);
		list.add(2);
		list.add(4);
		list.add(5);
		list.add(5);
		list.add(1);
		
		//list.remove(0);
		
		System.out.println(list);
	}
	
}


class LinkedList_2 {
	
	Node head;
	Node tail;
	
	public LinkedList_2() {
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
	
	class Node {
		
		int data;
		Node next;
		
		public Node(int d) {
			this.data = d;
			this.next = null;
		}
	}
	
}
