package test.practice.amazon;

public class BoundaryTreeTraversal_AntiClockWise {

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
	}
	
	/*
	 * https://github.com/grandyang/leetcode/issues/545
	 * https://www.cnblogs.com/grandyang/p/6833459.html
	 * https://leetcode.com/problems/boundary-of-binary-tree/discuss/101294/Java-C%2B%2B-Clean-Code-(1-Pass-perorder-postorder-hybrid)
	 * https://leetcode.com/problems/boundary-of-binary-tree/discuss/101280/Java(12ms)-left-boundary-left-leaves-right-leaves-right-boundary
	 * 
	 */
	
	/*
	   Print the edge nodes (boundary) of a binary tree in an anti-clockwise direction.
	    - The leftmost nodes starting at root
	    - Then the leaves left to right
	    - Finally the rightmost nodes from the bottom up


	        __A__
	       /     \
	      B       C
	     / \     / \
	    D   E   F   G
	   / \         / \
	  H   I       J   K


	  Output sample for a filled tree: A,B,D,H,I,E, F,J,K,G,C
	  Not all input trees will be filled.

	1. Print left side top -down
	    then left side form left - right
	2. Right - bottom -up

	*/
	
	
	public static void printBoundary(TreeNode root) {
		
		if(root == null) 
			return;
		
		System.out.println(root.data);
		traverseLeft(root.left);
		traverseRight(root.right);
		
		
	}
	
	static void traverseLeft(TreeNode node) {
		System.out.println(node);
		if(node.left != null)
			printBoundary(node.left);
		else if (node.right != null)
			printBoundary(node.right);
	}

	static void traverseRight(TreeNode node) {
		System.out.println(node);
		if(node.right != null)
			printBoundary(node.right);
		else if (node.left != null)
			printBoundary(node.left);
	} 
	
	/*void printTree(TreeNode root) {
	    
	    if(root == null) return;
	    
	    System.out.println(root.data);
	    //iterate left
	    leftTree(node.left);
	    rightTree(node.right);
	}*/

	//--B,D,H,I,E
	/*void leftTree(TreeNode node) {
	    if(node == null) return;
	    if(node.left != null ) {
	        System.out.println(node.data);
	        leftTree(node.left);
	    } else if (node.right != null) {
	        System.out.println(node.data);
	        leftTree(node.right);
	    }
	}*/

	//--F,J,K,G,C
	//--bottom up
	/*void rightTree (TreeNode node) {
	    if(node == null) return;
	    if(node.right != null) {
	        rightTree (node.right);
	        System.out.println(node.data);
	    } else if (node.left != null) {
	        rightTree (node.leftt);
	        System.out.println(node.data);
	    }
	}*/
	
	
	/**
	 * https://leetcode.com/problems/boundary-of-binary-tree/discuss/101294/Java-C%2B%2B-Clean-Code-(1-Pass-perorder-postorder-hybrid)
	 * 
	 *  node.left is left bound if node is left bound;
		node.right could also be left bound if node is left bound && node has no right child;
		Same applys for right bound;
		if node is left bound, add it before 2 child - pre order;
		if node is right bound, add it after 2 child - post order;
		A leaf node that is neither left or right bound belongs to the bottom line;

	 * */
	
	public class Solution {
	    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
	        List<Integer> res = new ArrayList<Integer>();
	        if (root != null) {
	            res.add(root.val);
	            getBounds(root.left, res, true, false);
	            getBounds(root.right, res, false, true);
	        }
	        return res;
	    }

	    private void getBounds(TreeNode node, List<Integer> res, boolean lb, boolean rb) {
	        if (node == null) return;
	        if (lb) res.add(node.val);
	        if (!lb && !rb && node.left == null && node.right == null) res.add(node.val);
	        getBounds(node.left, res, lb, rb && node.right == null);
	        getBounds(node.right, res, lb && node.left == null, rb);
	        if (rb) res.add(node.val);
	    }
	}


}
