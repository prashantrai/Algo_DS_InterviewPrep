package Facebook;

import java.util.HashSet;
import java.util.Set;

public class LowestCommonAncestorOfABinaryTree_iii_1650_Medium {

	public static void main(String[] args) {
		
		Node node_5 = new Node(5);
		Node node_1 = new Node(1);
		Node node_6 = new Node(6);
		Node node_2 = new Node(2);
		Node node_0 = new Node(0);
		Node node_8 = new Node(8);
		Node node_7 = new Node(7);
		Node node_4 = new Node(4);
		
		Node root = new Node(3, node_5, node_1);
		
		node_5.left = node_6;
		node_5.right = node_2;
		node_5.parent = root;
		
		node_1.left = node_0;
		node_1.right = node_8;
		node_1.parent = root;
		
		node_2.left = node_7;
		node_2.right = node_4;
		node_2.parent = node_5;
		
		
		node_0.parent = node_1;
		node_8.parent = node_1;
		node_6.parent = node_5;
		node_7.parent = node_2;
		node_4.parent = node_2;
		
		
		Node p = node_5, q = node_1;
		Node res = lowestCommonAncestor(p, q);
		System.out.println("Expected: 3, Actual: " + res.val);
	}
	
	
	/*
    Similar solution as https://leetcode.com/problems/intersection-of-two-linked-lists/
    Also look here: https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-iii/discuss/932914/Java-in-6-lines
    
    Time: O(N+M)
    Space: O(1)
    
    Two Pointer Approach: 
    Start by assigning each pointer to a node and traverse. 
    As soon as they hit the end (i.e. null), switch the node 
    i.e. ptr1 started with "p" and once it reaches the end 
    reset ptr1 by assigning "q" and then continue the traversing.
    Follow the similar for ptr2 once it hit the end/null.
    
    > Why this works? 
    both side of tree will have different length (not equal) and one will hit 
    the end before other, and, by swtching/restting the ptr1 and ptr2 we are 
    actually making them visit the same lenght.
    
    e.g. if starting from ptr1, lenghtA=5 and from ptr2, lengthB=3
    then ptr1 reaches end first and by resetting and assgining "p" to ptr2
    will travese 5 more nodes distance + 3 nodes distance = 8
    and, then same will be for ptr1 which will traverse 3 more nodes 
    distance + 5 nodes distance (after resetting to ptr2) = 8
    
    And, because after swtich, both pointers traverse the same distance 
    they will meet the end together i.e. inersect.
    
    
    */
    public static Node lowestCommonAncestor(Node p, Node q) {
        Node ptr1 = p;
        Node ptr2 = q;
        
        while(ptr1 != ptr2) {
            // reset position if null, set 2nd node to p1
            ptr1 = ptr1 == null ? q : ptr1.parent;
            
            // reset position if null, set 1st node to p2
            ptr2 = ptr2 == null ? p : ptr2.parent;
        }
        return ptr1;
    }
	
	
	/*
	 * Time: O(N)
	 * Space: O(N)
	 * 
	 * Similar question: leetcode.LowestCommonAncestorOfABinaryTree_236_Medium
	 * 
	 */
	
	public static Node lowestCommonAncestor2(Node p, Node q) {
        Set<Node> pPath = new HashSet<>();
        Node parent = p;
        
        while(parent!=null) {
            pPath.add(parent); 
            parent = parent.parent;
        }
        
        parent = q;
        while(parent!=null) {
            if(pPath.contains(parent))
                return parent;
            parent = parent.parent;
        }
        return null;        
    }
	
	// Definition for a Node.
	static  class Node {
	    public int val;
	    public Node left;
	    public Node right;
	    public Node parent;
	    public Node(int v) {
	    	this.val = v;
	    }
	    public Node(int v, Node left, Node right) {
	    	this.val = v;
	    	this.left = left;
	    	this.right = right;
	    }
	};

}
