package leetcode;

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
	
	// Implement min and max heap with priority queue: https://jindongpu.wordpress.com/2015/10/20/implement-max-heap-and-min-heap-using-priorityqueue-in-java/
	
	// https://leetcode.com/problems/merge-k-sorted-lists/
	// https://www.youtube.com/watch?v=zLcNwcR6yO4
	
	/**
	Complexity Analysis:: 

	Time complexity : O(Nlogk) where k is the number of linked lists.

	The comparison cost will be reduced to O(logk) for every pop and insertion to priority queue. But finding the node with the smallest value just costs O(1) time.
	There are N nodes in the final linked list.

	Space complexity :

	O(n) Creating a new linked list costs O(n) space.
	O(k) The code above present applies in-place method which cost O(1) space. And the priority queue (often implemented with heaps) costs O(k) space (it's far less than N in most situations).
	*/
	
	 
	public static ListNode mergeKLists(ListNode[] lists) {
	    
	    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
	    for(ListNode list : lists) {
	        while(list != null) {
	            minHeap.add(list.val);
	            list = list.next;
	        }
	    }
	    
	    ListNode dummy = new ListNode(-1);
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
