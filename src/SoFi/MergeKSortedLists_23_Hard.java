package SoFi;

import java.util.PriorityQueue;

public class MergeKSortedLists_23_Hard {

	public static void main(String[] args) {

		//Input:: [[1,4,5],[1,3,4],[2,6]]
		//Expected:: [1,1,2,3,4,4,5,6]
		
		ListNode l1 = new ListNode(1);
		l1.next = new ListNode(4);
		l1.next.next = new ListNode(5);
		
		ListNode l2 = new ListNode(1);
		l2.next = new ListNode(3);
		l2.next.next = new ListNode(4);
		
		ListNode l3 = new ListNode(2);
		l3.next = new ListNode(6);
		
		ListNode[] lists = {l1, l2, l3};
		
		ListNode res = mergeKLists(lists);
		System.out.println(res);
		
	    while(res != null) {
	    	System.out.print(res.val+", ");
	    	
	    	res = res.next;
	    }
		
	}
	
	/* Better space complexity O(1) compare to PriorityQueue 
    solutions which has O(N) space complexity.
 
	 > Merge with Divide And Conquer
	 
	 Time:   O(N logK), where N is the total number of nodes 
	         and K is the number of lists.
	         
	         - interval starts at 1 and doubles each time until it 
	         reaches or exceeds K. The number of iterations required 
	         to do this is (logK), because the interval grows exponentially.
	         
	         - In each merging round, we perform K/2, K/4, ... merge operations.
	         - In each round, we are merging N nodes, and we 
	             have logK rounds of merging.
	         - Thus, the total time complexity is O(NlogK)
	         
	 Space: O(1), for the merge process 
	         (excluding the space for the input and output lists).
	 
	 */
	 
	 public static ListNode mergeKLists(ListNode[] lists) {
	     if (lists == null || lists.length == 0) 
	         return null;
	     int interval = 1;
	     while (interval < lists.length) {
	         for (int i = 0; i + interval < lists.length; i += interval * 2) {
	             lists[i] = merge2Lists(lists[i], lists[i + interval]);
	         }
	         interval *= 2;
	     }
	     return lists[0];
	 }
	
	 // Same solution/method as problem "Leetcode 21 - Merge 2 Sorted Lists"
	 // https://leetcode.com/problems/merge-two-sorted-lists/
	 private static ListNode merge2Lists(ListNode l1, ListNode l2) {
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
	
	
	// Implement min and max heap with priority queue: https://jindongpu.wordpress.com/2015/10/20/implement-max-heap-and-min-heap-using-priorityqueue-in-java/
	
	// https://leetcode.com/problems/merge-k-sorted-lists/
	// https://www.youtube.com/watch?v=zLcNwcR6yO4
	
	/**
	Complexity Analysis:: 

	Time complexity : O(Nlogk) where k is the number of linked lists.

	The comparison cost will be reduced to O(logk) for every pop 
	and insertion to priority queue. But finding the node with 
	the smallest value just costs O(1) time.
	
	There are N nodes in the final linked list.

	Space complexity :  O(n)

	O(n) Creating a new linked list costs O(n) space.
	
	O(k) The code above present applies in-place method 
	which cost O(1) space. And the priority queue 
	(often implemented with heaps) costs O(k) 
	space (it's far less than N in most situations).
	*/
	
	 
	public static ListNode mergeKLists2(ListNode[] lists) {
	    
	    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
	    //or  PriorityQueue<ListNode> minHeap 
	    //   = new PriorityQueue<ListNode>(lists.length, (a,b)-> a.val-b.val);
	    
	    
	    for(ListNode list : lists) {
	        while(list != null) {
	            minHeap.add(list.val);
	            list = list.next;
	        }
	    }
	    
	    ListNode dummy = new ListNode(-1);
	    
	    // point head to dummy then move dummy with
        // every new element/value
	    ListNode head = dummy;
	    
	    while(!minHeap.isEmpty()) {
	        head.next = new ListNode(minHeap.remove());
	        head = head.next;
	    }
	    
	    return dummy.next;
	    
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
