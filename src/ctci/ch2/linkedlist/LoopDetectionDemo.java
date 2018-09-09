package ctci.ch2.linkedlist;

public class LoopDetectionDemo {

	public static void main(String[] args) {

		LinkedList l1 = new LinkedList();
		l1.add(11);
		l1.add(12);
		l1.add(1);
		l1.add(2);
		l1.add(3);
		l1.add(4);
		l1.add(5);
		l1.add(6);
		l1.add(7);
		l1.add(8);
		l1.add(9);
		l1.add(10);
		l1.add(11);
		
		//--Create intersecting Linked Lists
		createLoop(l1.head);
		
		//System.out.println("l1: "+l1);
		
		detectLoop(l1.head);
		
//		LinkedList.Node node = findIntersection(l1.head, l2.head);
		/*if (node != null)
			System.out.println(">>> RESULT : " + node.data);
		else 
			System.err.println(">>> RESULT : No intersection found");*/
		
	}
	
	public static LinkedList.Node detectLoop(LinkedList.Node head) {

		LinkedList.Node slowPointer = head;
		LinkedList.Node fastPointer = head;
		
		while (fastPointer != null && fastPointer.next != null) {
			
			slowPointer = slowPointer.next;
			fastPointer = fastPointer.next.next;
			
			if(slowPointer == fastPointer) {
				System.out.println(">> Collision at " + slowPointer.data);
				break;
			}
		}
		
		/**
		 * find the start of the loop
		 * 
		 * Move slowPointer to the head and keep the fastPointer at same location
		 * Now move both pointer by one node at a time
		 * Location they will meet will be the start of loop 
		 * */
		
		slowPointer = head;
		
		while(fastPointer != null && fastPointer.next != null) {
			
			slowPointer = slowPointer.next;
			fastPointer = fastPointer.next;
			
			if(slowPointer == fastPointer) {
				System.out.println(">> Lopp starts at " + slowPointer.data);
				break;
			}
			
		}
		
		
		return slowPointer;
	}
	
	
	
	public static void createLoop(LinkedList.Node node) {
		
		LinkedList.Node node4 = null;
		while(node != null) {
			
			if(node.data == 4) {
				node4 = node;
			}
			
			if(node.next == null) {
				node.next = node4;
				break;
			}
			node = node.next;
		}
	}

	

}
