package ctci.ch2.linkedlist;

public class SumListDemo {

	public static void main(String[] args) {
		
		
		LinkedList l1 = new LinkedList();
		l1.add(7);
		l1.add(1);
		l1.add(6);
		/*l1.add(4);
		l1.add(3);
		l1.add(2);
		l1.add(1);*/
		
		System.out.println("l1 = "+l1);
		
		LinkedList l2 = new LinkedList();
		/*l2.add(7);
		l2.add(6);
		l2.add(5);*/
		l2.add(5);
		l2.add(9);
		l2.add(2);
		
		System.out.println("l2 = "+l2);
		
		LinkedList.Node result = null;
		//result = sumListsInReverseOrder(l1.head, l2.head, 0);
		
		//Printing linked list
		String sResult = "";
		while(result != null) {
			if(result.next != null)
				sResult += result.data + " -> ";
			else 
				sResult += result.data;
			
			result = result.next;
		}
		System.out.println("Result: "+ sResult);
		
		
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
		
		printList(resultList);
	}
	
	
	public static LinkedList.Node sumListsInReverseOrder(LinkedList.Node l1, LinkedList.Node l2, int carry) {
		
		if(l1 == null && l2 == null && carry ==0) { 
			return null;
		}
		
		LinkedList.Node result = new LinkedList().new Node();
		
		int value = carry;
		
		if(l1 != null)
			value += l1.data;
		
		if(l2 != null)
			value += l2.data;
		
		carry = value / 10;
		value =  value % 10;
		
		result.data = value;
		
		if(l1 != null || l2 != null) {
			LinkedList.Node more = sumListsInReverseOrder( l1 == null ? null : l1.next, 
											l2 == null ? null : l2.next, 
											carry);
			
			result.next = more;
		}
		return result;
		
	}	
	

	
	/*
	  1->2->3->4
	  0->5->6->7
	 
	 PartialSum: 
	 	sum = 
	 	carry = 
	 
	 * */
	public static LinkedList.Node sumListsInForwardOrder(LinkedList.Node l1, LinkedList.Node l2) {
		
		int l1_length = length(l1);
		int l2_length = length(l2);

		System.out.println("l1 length= "+l1_length);
		System.out.println("l2 length= "+l2_length);
		
		//padding the smallest list with zero at front
		if(l1_length < l2_length) {
			l1 = padList(l1, l2_length - l1_length);
			printList(l2);
		} else {
			printList(l1);
			l2 = padList(l2, l1_length - l2_length);
			
		}

		//PartialSum sum = sumListsInForwardOrder(l1.next, l2.next, carry);
		
		/*
		  1->2->3->4
		  0->5->6->7
		 */
		PartialSum sum = addListsHelper(l1, l2); 
		
		if(sum.carry == 0) {
			return sum.sum;
		} else {
			LinkedList.Node node = new LinkedList().new Node();
			node.data = sum.carry;
			node.next = sum.sum;
					
			return node;
		}
	}
	
	
	public static PartialSum addListsHelper(LinkedList.Node l1, LinkedList.Node l2) {
		
		if(l1 == null && l2 == null) {
			PartialSum sum = new PartialSum();
			return sum;
		}

		PartialSum sum = addListsHelper(l1.next, l2.next);
		
		//--add node values
		int value = sum.carry + l1.data + l2.data;
		
		sum = insertBefore(sum, value%10);
		sum.carry = value/10;
				
		return sum;
	}
		
	public static PartialSum insertBefore(PartialSum ps, int value) {
		LinkedList.Node head = new LinkedList().new Node();
		head.data = value;
		head.next = ps.sum;
		
		ps.sum=head;
		
		return ps;
	}
	
	//-- pad list with zero at front
	public static LinkedList.Node padList(LinkedList.Node head, int d) {
		
		for (int i=0; i<d; i++) {
			LinkedList.Node node =  new LinkedList().new Node();
			node.data = 0;
			node.next = head;
			head = node;
		}
		
		System.out.println("padList called :: head=>");
		printList(head);
		
		return head;
	}
	
	//--compute list size
	public static int length (LinkedList.Node node) {
		int count = 0;
		
		while(node != null) {
			count++;
			node = node.next;
		}
		return count;
	}
	
	
	public static void printList(LinkedList.Node node) {
		String sNode = "";
		while(node != null) {
			if(node.next != null)
				sNode += node.data + " -> ";
			else 
				sNode += node.data;
			
			node = node.next;
			
		}
		System.out.println(sNode);
	}

}

class PartialSum {
	public LinkedList.Node sum;
	public int carry;
}