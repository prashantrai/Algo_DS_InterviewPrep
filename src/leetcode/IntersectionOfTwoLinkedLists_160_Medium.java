package leetcode;

public class IntersectionOfTwoLinkedLists_160_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/*
    Time complexity : O(N+M)
    Space complexity : O(1).
    
    Two Pointers: 
    Start by assigin each pointer to a node and traverse
    As soon as they hit the end (i.e. null), switch the node 
    i.e. nodeA started with headA and once it reaches the end 
    reset nodeA by assigning nodeB and then continue the traversing.
    Follow the similar for nodeB once it hit the end/null.
    
    Why this works? 
    both list will have different lenght (not equal) and one will hit the end before other, and, by swtching/restting the nodeA and nodeB we are actually making them visit the same lenght.
    
    e.g. if starting from headA, lenghtA=5 and from headB, lengthB=3
    then nodeB reaches end first and by resetting and assgining headA nodeB
    will travese 5 more nodes distance + 3 nodes distance = 8
    and, then same will be for nodeA which will traverse 
    3 more nodes distance + 5 nodes distance (after resetting to headB) = 8
    
    And, because after swtich, both pointers traverse the same distance they will met the end together i.e. inersect.
    
    */
    
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
     
        ListNode nodeA = headA;
        ListNode nodeB = headB;
        
        while (nodeA != nodeB) {
            nodeA = nodeA == null ? headB : nodeA.next;
            nodeB = nodeB == null ? headA : nodeB.next;
        }
        
        return nodeA;
        
        // Note: In the case lists do not intersect, the pointers for A and B
        // will still line up in the 2nd iteration, just that here won't be
        // a common node down the list and both will reach their respective ends
        // at the same time. So nodeA will be NULL in that case.
    }
    
    //Definition for singly-linked list.
  	 private static class ListNode {
  		  int val;
  	      ListNode next;
  	      ListNode(int val) { this.val = val; }
  	      
  	 }

}
