package doordash;

import java.util.Arrays;

public class BinaryTreeMaximumPathSum_124_Hard {

	public static void main(String[] args) {
		TreeNode node_9 = new TreeNode(9);
		TreeNode node_15 = new TreeNode(15);
		TreeNode node_7  = new TreeNode(7);
		
		TreeNode node_20  = new TreeNode(20, node_15, node_7);
		TreeNode root  = new TreeNode(-10, node_9, node_20);
		
		System.out.println("Expected: 42, Actual: " + maxPathSum(root));
		
	}

	/*
	Explanation: https://leetcode.com/problems/binary-tree-maximum-path-sum/discuss/603423/Python-Recursion-stack-thinking-process-diagram 
	  
	Algo (LC Premium Solution): 
		1. Initiate max_sum as the smallest possible integer and call max_gain(node = root).
		2. Implement max_gain(node) with a check to continue the old path/to start a new path:
			- Base case : if node is null, the max gain is 0.
			- Call max_gain recursively for the node children to compute max gain from the left 
				and right subtrees : left_gain = max(max_gain(node.left), 0) and
			- right_gain = max(max_gain(node.right), 0).
			- Now check to continue the old path or to start a new path. To start a new path 
				would cost price_newpath = node.val + left_gain + right_gain. Update max_sum 
				if it's better to start a new path.
			- For the recursion return the max gain the node and one/zero of its subtrees could 
				add to the current path : node.val + max(left_gain, right_gain).
	 
	 
    Time: O(N)
    Space: O(H), where H is tree height
    */
	public static int maxPathSum(TreeNode root) {
        maxGain(root);
        System.out.println("Max Path: " + Arrays.toString(maxPath));
        return maxSum;
    }
    
	static int maxSum = Integer.MIN_VALUE;
	static int[] maxPath = new int[3]; //print the nodes of max path
    
    private static int maxGain(TreeNode root) {
        if(root == null) return 0;
        
        // max sum on the left and right sub-trees of node
        int leftSum  = Math.max(maxGain(root.left), 0);
        int rightSum = Math.max(maxGain(root.right), 0);
        
        int currSum = root.val + leftSum + rightSum;
        
        //This is just to print the max path and wasn't a part of requirement but could 
        // be a follow-up question in interview
        if(maxSum < currSum && root != null) {
        	maxPath[0] = root.left != null ? root.left.val : Integer.MIN_VALUE;
        	maxPath[1] = root.val;
        	maxPath[2] = root.right != null ? root.right.val : Integer.MIN_VALUE;
        }
        
        maxSum = Math.max(currSum, maxSum);
        
        return root.val + Math.max(leftSum, rightSum);
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
