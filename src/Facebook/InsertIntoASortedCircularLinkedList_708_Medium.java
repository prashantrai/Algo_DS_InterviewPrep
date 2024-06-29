package Facebook;

public class InsertIntoASortedCircularLinkedList_708_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	/* Leetcode Solution:
    
    Time Complexity: O(N) where N is the size of list. 
    In the worst case, we would iterate through the entire list.

    Space Complexity: O(1). It is a constant space solution.
    */
    
    public static Node insert_2(Node head, int insertVal) {
        // if start is null, create a node pointing to itself and return
        if(head == null) {
            Node node = new Node(insertVal, null);
            node.next = node;
            return node;
        }
        
        Node prev = head;
        Node curr = head.next;
        boolean toInsert = false;
        
        do {
            // Case 1: when insertVal is betweem prev and curr node
            if(prev.val <= insertVal && insertVal <= curr.val) {
                toInsert = true;
                
            } 
            else if (prev.val > curr.val) { 
                // Case 2: when insertVal is either less than the 
                // min value or greater than the max value. 
                // In either case, the new node should be added right 
                // after the tail node (i.e. the node with the max value).
                if(insertVal >= prev.val || insertVal <= curr.val) {
                    toInsert = true;
                }
            }
            if(toInsert) {
                prev.next = new Node(insertVal, curr);
                return head;
            }
            
            // move pointers to next node
            prev = curr;
            curr = curr.next;
            
        } while (prev != head);
        
        // Case 3: whem list has duplicates/uniform values, i.e., 3->3->3->3
        // just to add the new node after any node in the list
        prev.next = new Node(insertVal, curr);
        return head;
        
    }
	
	
	/* Another approach [https://leetcode.com/problems/insert-into-a-sorted-circular-linked-list/discuss/149374/Java-5ms-One-Pass-and-Two-Pass-Traverse-With-Detailed-Comments-and-Edge-cases!/221648]
	 * 
	 * Time Complexity: O(N) where N is the size of list. 
	 * In the worst case, we would iterate through the entire list.
	 * 
	 * Space Complexity: O(1). It is a constant space solution.
	 * */
	
	public Node insert2(Node head, int insertVal) {
        
		Node r = new Node(insertVal, null);
		// if start is null, create a node pointing to itself and return
        if(head == null) {
            r.next = r;
            return r;
        }
        
        Node n = head;
        
        /* 3 cases::
        case 1: insertVal is between 2 nodes 
        	e.g. 1->2->4, insert 3
        	condition: insertVal >= n.val && insertVal <= n.next.val
    
        case 2: insertVal is >= largest node value or <= smallest node value 
        	e.g. 1->2->4, insert 0 or 1->2->4, insert 5
        	condition: n.next.val < n.val && (insertVal >= n.val || insertVal <= n.next.val)
    
        case 3: all the nodes in the tree have same value
        	e.g. 1->1->1, insert 2
        	condition: n.next == head 
         */
        while(true){
            if((insertVal >= n.val && insertVal <= n.next.val)
               || (n.next.val < n.val && (insertVal >= n.val || insertVal <= n.next.val))
               || n.next == head){
                r.next = n.next;
                n.next = r;
                break;
            }
            n = n.next;
        }
        return head;
	}
	
	
	
    
    
    private static class Node {
        public int val;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _next) {
            val = _val;
            next = _next;
        }
    }
	
}
