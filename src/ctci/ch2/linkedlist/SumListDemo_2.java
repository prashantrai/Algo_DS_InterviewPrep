package ctci.ch2.linkedlist;

public class SumListDemo_2 {

	public static void main(String[] args) {
		
		//--Sum Lists in Forward Order
		LinkedList l3 = new LinkedList();
		l3.add(1);
		l3.add(2);
		l3.add(3);
		l3.add(4);
		
		LinkedList l4 = new LinkedList();
		l4.add(5);
		l4.add(6);
		l4.add(7);
		
		System.out.println("l3 = "+l3);
		System.out.println("l4 = "+l4);
		
		LinkedList.Node resultList = sumListsInForwardOrder(l3.head, l4.head);
		
		System.out.println(">>Result: "+printList(resultList));
	}
	
	
	public static LinkedList.Node sumListsInForwardOrder (LinkedList.Node l1, LinkedList.Node l2) {
		
		/*
		  1->2->3->4
		  0->5->6->7
		  ----------
		  1->8->0->1
		  
		 */

		int l1_length = length(l1);
		int l2_length = length(l2);
		
		if(l1_length < l2_length) {
			l1 = padList(l1, l2_length - l1_length);
		} else {
			l2 = padList(l2, l1_length - l2_length);
		}
		
		PartialSum_2 sum = addListHelper(l1, l2);
		
		if(sum.carry != 0) {
			LinkedList.Node node = new LinkedList().new Node();
			node.data = sum.carry;
			node.next = sum.sum;
			sum.sum = node;
			return sum.sum;
		} else {
			return sum.sum;
		}
		
	}
	
	public static PartialSum_2 addListHelper(LinkedList.Node l1, LinkedList.Node l2) {
		
		if(l1 == null || l2 == null) {
			PartialSum_2 sum = new PartialSum_2();
			return sum;		
		}
		
		PartialSum_2 sum = addListHelper(l1.next, l2.next);
		
		int value = sum.carry + l1.data + l2.data;
		sum = insertBefore(sum, value % 10);
		sum.carry = value/10;
		
		return sum;
	}
	
	public static PartialSum_2 insertBefore(PartialSum_2 sum, int value) {
		
		LinkedList.Node node = new LinkedList().new Node();
		node.data = value;
		node.next = sum.sum;
		sum.sum = node;
		return sum;
	} 
	
	public static LinkedList.Node padList(LinkedList.Node head, int d) {
		
		for(int i=0; i<d; i++) {
			LinkedList.Node node = new LinkedList().new Node();
			node.data = 0;
			node.next = head;
			head = node;
		}
		return head;
		
	}
	
	public static int length(LinkedList.Node l) {
		int count = 0;
		while (l != null) {
			count++;
			l = l.next;
		}
		return count;
	}
	
	public static String printList(LinkedList.Node head) {
		
		String sList = "";
		while(head != null) {
			if(head.next != null)
				sList += head.data + " -> ";
			else 
				sList += head.data;
			
			head = head.next;
		}
		return sList;
	}

}

class PartialSum_2 {
	LinkedList.Node sum;
	int carry;
} 