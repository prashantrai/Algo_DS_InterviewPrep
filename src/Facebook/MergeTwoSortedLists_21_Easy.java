package Facebook;


public class MergeTwoSortedLists_21_Easy {

	public static void main(String[] args) {
		//Input:: [[1,4,5],[1,3,4],[2,6]]
		//Expected:: [1,1,2,3,4,4,5,6]
		
		ListNode ln = new ListNode(1);
		
		ListNode l1 = new ListNode(1);
		l1.next = new ListNode(2);
		l1.next.next = new ListNode(4);
		
		ListNode l2 = new ListNode(1);
		l2.next = new ListNode(3);
		l2.next.next = new ListNode(4);
		
		ListNode res = mergeTwoLists(l1, l2);
		
		System.out.println("Expected: [1,1,2,3,4,4]\nActual:");
		
	    while(res != null) {
	    	System.out.print(res.val+", ");
	    	
	    	res = res.next;
	    }

	}

	/*
    Time: O(n+m) where n and m are the size of each list 
    i.e. overall complexity is linear
    
    Space: O(1)
    
    */
    
	public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        
		ListNode head = new ListNode(-1); 
        ListNode current = head;
        
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                current.next = l1;
                l1 = l1.next;
            } else {
                current.next = l2;
                l2 = l2.next;
            }
            current = current.next;
        }
        current.next = (l1 != null) ? l1 : l2;
        return head.next;
    }
	
	
	
    
    public static class ListNode {
    	int val;
     	ListNode next;
     	ListNode() {}
     	ListNode(int val) { this.val = val; }
     	ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     }
	
}
