package ctci.ch2.linkedlist;

public class ReurnKthToLastDemo {

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

	
	public static void printKthToLast(LinkedList.Node head, int k) {
		
		LinkedList.Node p1 = head;
		LinkedList.Node p2 = head;
		
		for(int i=0; i<k; i++) {
			if(p1 == null) return;
			
			p1 = p1.next;
		}
		
		while (p1 != null) {
			p1 = p1.next;
			p2 = p2.next;
		}
		
		System.out.println(">>> "+k + "th to last node is " + p2.data);
	}
	
	public static int printKthToLastREC(LinkedList.Node head, int k) {
		
		if(head == null) return 0;
//		System.out.println("head.data = "+head.data + ", k="+k);
		
		int index = printKthToLastREC(head.next, k) + 1;
		System.out.println("index = "+index);
		
		if(index == k) {
			System.out.println(k + "th to last node is " + head.data);
		}
		
		return index;
	}
	
	
	
	
	
	


}
