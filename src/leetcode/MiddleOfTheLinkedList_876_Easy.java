package leetcode;


public class MiddleOfTheLinkedList_876_Easy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	// Time: O(N)
    // Space: O(1)
    public ListNode middleNode(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    
    //Definition for singly-linked list.
  	 private static class ListNode {
  		  int val;
  	      ListNode next;
  	      ListNode() {}
  	      ListNode(int val) { this.val = val; }
  	      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  	      
  	 }

}
