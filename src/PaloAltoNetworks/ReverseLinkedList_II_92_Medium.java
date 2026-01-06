package PaloAltoNetworks;

public class ReverseLinkedList_II_92_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/* Time Complexity: O(n) where n is the number of nodes in the list. We traverse the list once.
	Space Complexity: O(1) as we only use a constant amount of extra space.*/

	    public ListNode reverseBetween(ListNode head, int left, int right) {
	        // Edge case: no reversal needed
	        if (left == right) {
	            return head;
	        }

	        // Create a dummy node to handle case when left = 1
	        ListNode dummy = new ListNode(0);
	        dummy.next = head;

	        // Find the connection point (node before position 'left')
	        ListNode con = dummy;
	        for (int i = 1; i < left; i++) {
	            con = con.next;
	        }

	        // The node at position 'left' will become the tail of reversed portion
	        ListNode tail = con.next;
	        ListNode prev = null;
	        ListNode cur = con.next;

	        // Reverse the sublist from position 'left' to 'right'
	         for (int i = left; i <= right; i++) {
	            ListNode nextTemp = cur.next;
	            cur.next = prev;
	            prev = cur;
	            cur = nextTemp;
	        }

	        // Reconnect the reversed portion
	        // con.next should point to the new head of reversed portion 
	        // (which is 'prev')
	        con.next = prev;
	        // tail.next should point to the remaining part of the list 
	        // (which is 'cur')
	        tail.next = cur;
	        
	        return dummy.next;

	    }

}
