package leetcode;

import java.util.ArrayList;
import java.util.List;

public class BoundaryOfBinaryTree_545_Medium {

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.left.right.left = new TreeNode(7);
		root.left.right.right = new TreeNode(8);
		
		root.right = new TreeNode(3);
		root.right.left = new TreeNode(6);
		root.right.left.left = new TreeNode(9);
		root.right.left.right = new TreeNode(10);
		
		//--new
		//root.right.right = new TreeNode(20);
		//root.right.right.left = new TreeNode(21);
		
		boundaryOfBinaryTree(root);
		System.out.println(res);
	}
	
	/*	
		Input:
		    ____1_____
		   /          \
		  2            3
		 / \          / 
		4   5        6   
		   / \      / \
		  7   8    9  10  
		       
		Ouput:
		[1,2,4,7,8,9,10,6,3]
	
		Explanation:
		The left boundary are node 1,2,4. (4 is the left-most node according to definition)
		The leaves are node 4,7,8,9,10.
		The right boundary are node 1,3,6,10. (10 is the right-most node).
		So order them in anti-clockwise without duplicate nodes we have [1,2,4,7,8,9,10,6,3].
		
	*/	
	
	
	// https://leetcode.com/problems/boundary-of-binary-tree/discuss/101280/Java(12ms)-left-boundary-left-leaves-right-leaves-right-boundary
	// Time complexity: O(n)
	
	
	static List<Integer> res = new ArrayList<>();
    
    public static List<Integer> boundaryOfBinaryTree(TreeNode root) {
        if(root == null) return res;
        
        res.add(root.val);
        
        leftBoundary(root.left);
        leaves(root.left);
        leaves(root.right);        
        rightBoundary(root.right);
        
        return res;
    }
    
    public static void leftBoundary(TreeNode root) {
        if(root == null) return;
        if(root.left == null && root.right == null) return;
        
        res.add(root.val);
        
        if(root.left != null) 
            leftBoundary (root.left);
        else 
            leftBoundary (root.right);
    }
    
    public static void rightBoundary(TreeNode root) {
        
        if(root == null) return;
        if(root.left == null && root.right == null) return;
        
        if(root.right != null) 
            rightBoundary (root.right);
        else 
            rightBoundary (root.left);
        
        res.add(root.val); // add after child visit(reverse)
    }
    
    public static void leaves(TreeNode root) {
        if(root == null) return;
        
        //leaf found
        if(root.left == null && root.right == null) {
            res.add(root.val);
            return;
        }
        leaves(root.left);
        leaves(root.right);
    } 
	
	
	
	private static class TreeNode {
		 int val;
		 TreeNode left;
		 TreeNode right;
		 TreeNode() {}
		 TreeNode(int val) { this.val = val; }
		 TreeNode(int val, TreeNode left, TreeNode right) {
			 this.val = val;
			 this.left = left;
			 this.right = right;
		 }
	}

}
