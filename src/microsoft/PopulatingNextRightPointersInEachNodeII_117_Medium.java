package microsoft;

import java.util.ArrayDeque;
import java.util.Deque;

public class PopulatingNextRightPointersInEachNodeII_117_Medium {

	public static void main(String[] args) {
		Node root = new Node(1);
		root.left = new Node(2);
		root.left.left = new Node(4);
		root.left.right = new Node(5);
		
		root.right = new Node(3);
		root.right.right = new Node(7);
		
		connect(root);

	}
	
	/*
	 * Given a binary tree Populate each next pointer to point to its next right node. 
	 * If there is no next right node, the next pointer should be set to NULL. 
	 * Initially, all next pointers are set to NULL. 
	 */
	
	// Refer below link for complete problem and description
	//https://leetcode.com/problems/populating-next-right-pointers-in-each-node-ii/
	//https://leetcode.com/problems/populating-next-right-pointers-in-each-node-ii/discuss/37828/o1-space-on-complexity-iterative-solution
	
	//O(n) time complexity : we are touching each node
	//O(1) space complexity
    public static Node connect(Node root) {
        if(root == null) return null;
        
        Node curr = root;
        Node head = null;
        Node prev = null;
        
        while (curr != null) {
            while (curr != null) {
                
                if(curr.left != null) {
                    if(prev != null) {
                        prev.next = curr.left;
                    } else {
                        head = curr.left;
                    }
                    prev = curr.left;
                }
                
                if(curr.right != null) {
                    if(prev != null) {
                        prev.next = curr.right;
                    } else {
                        head = curr.right;
                    }
                    prev = curr.right;
                }
                curr = curr.next;
            }
            curr = head;
            head = null;
            prev = null;            
        }
        
        return root;
    }
    
    // BFS: Space is not constant
    public static Node connect2(Node root) {
        if(root == null) return null;
        Deque<Node> dq = new ArrayDeque<>();
        dq.offer(root);
        Deque<Node> dq2 = new ArrayDeque<>();
        while (!dq.isEmpty()) {
            
            int size = dq.size();
            for (int i=0; i<size; i++) {
                Node curr = dq.poll();
                if(!dq.isEmpty()) {
                    curr.next = dq.peek();
                }
                if(curr.left != null)
                    dq2.offer(curr.left);
                if(curr.right != null)
                    dq2.offer(curr.right);
            }
            dq.addAll(dq2);
            dq2.clear();
            
        }
        return root;
    }
    
    
    // Definition for a Node.
    private static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}
        
        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    };

}
