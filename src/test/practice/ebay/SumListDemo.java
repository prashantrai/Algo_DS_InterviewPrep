package test.practice.ebay;

public class SumListDemo {

	public static void main(String[] args) {
		
		
		LinkedList l1 = new LinkedList();
		l1.add(7);
		l1.add(1);
		l1.add(6);
		l1.add(4);
		/*l1.add(3);
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
		result = sumListsInReverseOrder(l1.head, l2.head, 0);
		
		//Printing linked list
		printList(result);
		
		
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
		
		System.out.println("--->");
		printList(resultList);
	}
	
	
	public static LinkedList.Node sumListsInReverseOrder(LinkedList.Node l1, LinkedList.Node l2, int carry) {
		
		if(l1 == null && l2 == null && carry == 0) 
			return null;
		
		LinkedList.Node result =  new LinkedList().new Node();
		
		int value = carry;
		
		if(l1 != null) {
			value += l1.data;
		}
		if(l2 != null) {
			value += l2.data;
		}
		
		result.data = value % 10;
		
		if(l1 != null && l2 != null) {
			
			LinkedList.Node more  = sumListsInReverseOrder(l1.next, l2.next, value < 10 ? 0 : 1);
			
			result.next = more;
		}
		
		return result;
	}
	
	public static LinkedList.Node sumListsInForwardOrder(LinkedList.Node l1, LinkedList.Node l2) {
		
		if(l1 == null && l2 == null) 
			return null;
		
		
		/* Padding
		 *  1. Get the lenght of both list
		 *  2. Pad the list smallest in size with 0 equal to size diff between both the list
		 * 
		 * */
		int l1_size = size(l1);
		int l2_size = size(l2);
		
		if(l1_size < l2_size) {
			l1 = padList(l1, l2_size - l1_size);
		}
		if(l2_size < l1_size) {
			l2 = padList(l2, l1_size - l2_size);
		}
		
		printList(l1);
		printList(l2);
		
		PartialSum sum = addListHelper(l1, l2);
		System.out.println("----");
		printList(sum.sum);
		
		if(sum.carry == 0) {
			return sum.sum;
		} else {
			LinkedList.Node node = new LinkedList().new Node();
			node.data = sum.carry;
			node.next = sum.sum;
			sum.sum = node;
			return node;
		}
	}
	
	public static PartialSum addListHelper (LinkedList.Node l1, LinkedList.Node l2) {
		
		if(l1 == null && l2 == null) 
			return new PartialSum();
		
		PartialSum sum = addListHelper(l1.next, l2.next);

	    int sumVal = sum.carry +  l1.data + l2.data;
	    int carry = sumVal > 9 ? 1 : 0;
	    
	    insertBefore(sum, sumVal%10);		
		sum.carry = carry;
		
		return sum;
	}
	
	public static PartialSum insertBefore(PartialSum sum, int val) { 
		
		LinkedList.Node node = new LinkedList().new Node();
		node.data = val;
		node.next = sum.sum;
		sum.sum = node;
		
		return sum;
	}
	
	public static LinkedList.Node padList(LinkedList.Node node, int padSize) {
		
		LinkedList.Node pad = null;
		for(int i=0; i<padSize; i++) {
			pad = new LinkedList().new Node();
			pad.data = 0;
			pad.next = node;
			node = pad;
		}
		System.out.print("**** Padded List:: ");
		printList(node);
		System.out.println();
		
		return node;
	}
	
	public static int size(LinkedList.Node node) {
		
		int size = 0;
		while (node != null) {
			size++;
			node = node.next;
		}
		return size;
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
	LinkedList.Node sum;
	int carry;
	
}
