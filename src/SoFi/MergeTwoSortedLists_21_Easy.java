package SoFi;

public class MergeTwoSortedLists_21_Easy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/*
    Time: O(n+m) where n and m are the size of each list i.e. overall complexity is linear
    Space: O(1)
    
    */
    
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode preHead = new ListNode(-1);
        ListNode prev = preHead;
        
        while(l1 != null && l2 != null) {
            if(l1.val <= l2.val) {
                prev.next = l1;
                l1 = l1.next;
            } else {
                prev.next = l2;
                l2 = l2.next;
            }
            prev = prev.next;
        }
        // At least one of l1 and l2 can still have nodes at this point, so connect
        // the non-null list to the end of the merged list.
        prev.next = l1 == null ? l2 : l1;
        
        return preHead.next;
    }
    public class ListNode {
    	int val;
     	ListNode next;
     	ListNode() {}
     	ListNode(int val) { this.val = val; }
     	ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     }
	
}
