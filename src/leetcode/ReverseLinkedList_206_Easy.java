package leetcode;

public class ReverseLinkedList_206_Easy {

	public static void main(String[] args) {
		
		//Input: 1->2->3->4->5->NULL
		//Output: 5->4->3->2->1->NULL
		ListNode node = new ListNode(1);
		node.next = new ListNode(2);
		node.next.next = new ListNode(3);
		node.next.next.next = new ListNode(4);
		node.next.next.next.next = new ListNode(5);
		
		ListNode res = reverseList(node); // iterative
		//ListNode res = reverseList_recursive(node);

		while (res.next != null) {
			System.out.print(res.val + "->");
			res = res.next;
		}
		System.out.println(res.val);
		

	}
	
	/**
	 * Definition for singly-linked list.
	 * public class ListNode {
	 *     int val;
	 *     ListNode next;
	 *     ListNode() {}
	 *     ListNode(int val) { this.val = val; }
	 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
	 * }
	 */
	
	public static ListNode reverseList(ListNode head) {
	     
        if(head == null || head.next == null) 
            return head;
        
        ListNode prev = null;
        ListNode curr = head;
        
        while (curr != null) {
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;            
            curr = temp;
        }
        
        
        return prev;
    }
    
    //--Recursive
    public static ListNode reverseList_recursive(ListNode head) {
     
        if(head == null || head.next == null) return head;
        
        ListNode node = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        
        return node;
    }
    
    
    
    private static class ListNode {
	   	 int val;
	   	 ListNode next;
	   	 ListNode() {}
	   	 ListNode(int val) { this.val = val; }
	   	 ListNode(int val, ListNode next) { this.val = val; this.next = next; }
   	}

}
