package ctci.ch2.linkedlist;

import java.util.Stack;

public class PalindromListDemo {

	public static void main(String[] args) {
		
		LinkedList list = new LinkedList();
		list.add(0);
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(2);
		list.add(1);
		list.add(0); 
		System.out.println("list="+list);
		
		System.out.println(">>> RESULT :: "+isPalindrom(list));
		System.out.println(">>> RESULT :: "+isPalindorm_WithIerativeApproach(list.head));
	}
	
	
	
	public static boolean isPalindromeREC(LinkedList.Node head) {
		
		int length = lenght(head);
		
		Result result = isPalindromeRecursive(head, length);
		
		return result.result;
		
	}
	
	//--Recursive approach
	//-- 0 1 2 3  (4)  3 2 1 0
	public static Result isPalindromeRecursive(LinkedList.Node head, int length) {
		
		if(length <= 0 || head == null) {
			return new Result(head, true);
		}
		
		if(length == 1) {
			return new Result(head.next, true);
		}
		
		Result res = isPalindromeRecursive(head.next, length-2);
		
		if(!res.result || res.node == null) {
			return res;
		}
		
		res.result = res.node.data == head.data;
		res.node  = res.node.next;
		
		return res;
	} 
	
	//--Iterative approach
	public static boolean isPalindorm_WithIerativeApproach(LinkedList.Node node) {
		
		// abcdedcba
		
		Stack<Integer> stack = new Stack<Integer>();
		
		//-Iterate List using runner approach and push data into stack till half of the list
		LinkedList.Node slow = node;
		LinkedList.Node fast = node;
		
		while(fast != null && fast.next != null) {
			
			stack.push(slow.data);

			slow = slow.next;
			fast = fast.next.next;

		}
		
		//--if size of list is odd
		if(fast != null) {
			slow = slow.next;
		}

		while(slow != null) {
			int value = stack.pop().intValue();
			if(slow.data == value) 
				slow = slow.next;
			else 
				return false;
		}
		
		return true;
	}
	

	//--Reverse and compare	
	public static boolean isPalindrom(LinkedList list) {
		
		LinkedList.Node node = reverseAndClone(list.head);
		
		System.out.println(printList(node));
		
		return isEqual(list.head, node);
	}

	public static boolean isEqual(LinkedList.Node l1, LinkedList.Node l2) {
		
		while (l1 != null || l2 != null) {
			
			if(l1.data != l2.data) {
				return false;
			}
			
			l1 = l1.next;
			l2 = l2.next;
		}
		return l1 == null || l2 == null;
	}
	
	
	public static LinkedList.Node reverseAndClone(LinkedList.Node node) {
		
		LinkedList.Node head = null;
		while(node != null) {
			LinkedList.Node n = new LinkedList().new Node();
			n.data = node.data;
			n.next = head;
			head = n;
			node = node.next;
			
		}
		return head;
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

	public static int lenght(LinkedList.Node node) {
		
		int size=0;
		
		while(node != null) {
			size++;
			node = node.next;
		}
		return size;
	}
	
	
}


class Result {
	LinkedList.Node node;
	boolean result;
	Result(LinkedList.Node node, boolean result) {
		this.node = node;
		this.result = result;
	}
}



