package ctci.ch2.linkedlist;

public class ReverseLinkedList {

	public static void main(String[] args) {
		
		LinkedList list = new LinkedList();
		list.add(0);
		list.add(1);
		list.add(2);
		list.add(3);
		
		//--Practice only - Reverse a linked list
		//System.out.println(">>> RESULT :: Recursive :: isPalindrome: " + isPalindromeREC(list.head));
		//System.out.println(">>##>>>"+reverse(list.head));
		
		LinkedList.Node node = reverseREC(list.head);
		while(node != null) {
			System.out.print(node.data + "-> ");
			node = node.next;
		}
		System.out.println(">>[REC]>>>"+reverseREC(list.head));
		
		
	}

	public static LinkedList.Node reverseREC(LinkedList.Node node) {
		
		if(node == null || node.next == null) return node;
		
		LinkedList.Node n = reverseREC(node.next);
		node.next.next = node;
		node.next = null;
		return n;
		
	}
	
	public static LinkedList.Node reverse(LinkedList.Node node) {
		
		if(node == null) return null;
		
		LinkedList.Node prev = null;
		LinkedList.Node curr = node;
		LinkedList.Node next = null;
		
		while(curr != null) {
			next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
		}
		node = prev;
		while(node != null) {
			System.out.print(node.data + "-> ");
			node = node.next;
		}
		
		return prev;
	}
	
}
