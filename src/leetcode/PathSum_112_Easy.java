package leetcode;

public class PathSum_112_Easy {

	//--https://www.youtube.com/watch?v=Hg82DzMemMI
	//--https://leetcode.com/problems/path-sum/
	
	/**
	 * Definition for a binary tree node.
	 */ 
	
	class TreeNode {
	      int val;
	      TreeNode left;
	      TreeNode right;
	      TreeNode(int x) { val = x; }
	}
	 
	public static void main(String[] args) {

	}
	
	//--working solution: see leetcode link
	public boolean hasPathSum(TreeNode root, int sum) {
        if(root == null) 
            return false;
        
        sum = sum - root.val;
        
        if(root.left == null && root.right == null && sum == 0)                 
        	return true;
        
        return hasPathSum(root.left, sum) ||
                            hasPathSum(root.right, sum);
        
    }
	
}
