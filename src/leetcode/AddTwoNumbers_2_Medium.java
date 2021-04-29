package leetcode;

public class AddTwoNumbers_2_Medium {

	public static void main(String[] args) {

		
		
	}
	
	// Time: O(N), N is length of the longest list
	// Space : 	O(N)
	//https://leetcode.com/problems/add-two-numbers/
	//Looks good
	public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        
        ListNode dummyHead = new ListNode(0);
        ListNode curr = dummyHead;
        
        ListNode p = l1;
        ListNode q = l2;
        
        int carry = 0;
        
        while(p != null || q != null) {
            int x = p != null ? p.val : 0;
            int y = q != null ? q.val : 0;
            
            int sum = carry + x + y;
            carry = sum/10;
            
            curr.next = new ListNode(sum%10);
            curr = curr.next;
            
            if(p != null) p = p.next;
            if(q != null) q = q.next; 
        }
        
        if(carry > 0) {
            curr.next = new ListNode(carry);
        }
        
        return dummyHead.next;
    }
	
	/**
	 * Definition for singly-linked list.
	 */
	 private static class ListNode {
	      int val;
	      ListNode next;
	      ListNode() {}
	      ListNode(int val) { this.val = val; }
	      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
	 }
	 

}
