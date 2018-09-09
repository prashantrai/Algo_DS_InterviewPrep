package test.practice.ebay;

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
		System.out.println(">>> RESULT :: "+isPalindromInterative(list.head));
		System.out.println(">>> RESULT :: Recursive :: isPalindrome: " + isPalindromRecursive(list.head));
		
	}
	
	public static boolean isPalindromRecursive(LinkedList.Node node) {

		int length = lenght(node);
		
		Result res = isPalindromREC(node, length);
		return res.result;
	}
	
	public static Result isPalindromREC(LinkedList.Node node, int length) {
		
		if(node == null || length <=0) {

			return new Result(node, true);
		}
		
		if(length ==1) { //--for odd length
			return new Result(node.next, true);
		}
		
		Result res = isPalindromREC(node.next, length-2);
		
		if(!res.result || res.node == null) {
			return res;
		}
		
		res.result = res.node.data == node.data;
		res.node = res.node.next;
		
		return res;
		
	}
	
	public static int lenght(LinkedList.Node node) {
		
		int size=0;
		
		while(node != null) {
			size++;
			node = node.next;
		}
		return size;
	}
	
	
	//--With fast pointer and slow pointer approach and Stack
	public static boolean isPalindromInterative(LinkedList.Node node) {
		
		LinkedList.Node fast = node;
		LinkedList.Node slow = node;
		
		Stack<Integer> stack = new Stack<Integer>();
		
		while (fast != null && fast.next != null) {
			
			stack.push(slow.data);
			slow = slow.next;
			fast = fast.next.next;
		}
		
		//--if lenght is odd
		if(fast != null) {
			slow = slow.next;
		}
		
		while(slow != null) {
			int v = stack.pop();
			if(v != slow.data) {
				return false;
			}
			slow = slow.next;
		}
		return true;
	}

	public static boolean isPalindrom(LinkedList list) {
		
		//reverse and clone
		LinkedList.Node node  = reverseAndClone(list.head);
		
		//compare
		return isEqual(node, list.head);
	}
	
	public static LinkedList.Node reverseAndClone(LinkedList.Node node) {
		LinkedList.Node head = null;
		
		while(node != null) {
			LinkedList.Node n = new LinkedList().new Node(node.data);
			n.next = head;
			head = n;
			node = node.next;
		}
		return head;
		
	}
	
	public static boolean isEqual(LinkedList.Node l1, LinkedList.Node l2) {
		
		while(l1 != null || l2 != null) {
			
			if(l1.data != l2.data) {
				return false;
			}
			
			l1 = l1.next;
			l2 = l2.next;
		}
		return true;
	}

	static class Result {
		LinkedList.Node node;
		boolean result;
		
		public Result(LinkedList.Node node, boolean result) {
			this.node = node;
			this.result = result;
		}
	}

}


