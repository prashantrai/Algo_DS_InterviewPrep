	package Intuit;

import java.util.Stack;

public class PalindromeLinkedList_234_Easy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static boolean isPalindrome(ListNode head) {
        Stack<Integer> stk = new Stack<>();
        ListNode slow = head;
        ListNode fast = head;
        
        while(fast != null && fast.next != null) {
            stk.push(slow.val);
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // when list size is odd
        if(fast != null) slow = slow.next;
            
        // now fast is at the end of the list
        // we move slow pointer by one and compare with poped value from stack
        while(slow != null) {
            if(slow.val == stk.pop()) {
                slow = slow.next;
            } else 
                return false;
        }
        return true;        
    }
	
	private static class ListNode {
		int val;
		ListNode next;
		ListNode() {}
		ListNode(int val) { this.val = val; }
		ListNode(int val, ListNode next) { this.val = val; this.next = next; }
	}

}
