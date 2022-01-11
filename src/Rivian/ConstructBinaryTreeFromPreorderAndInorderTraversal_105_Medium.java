package Rivian;

import java.util.HashMap;
import java.util.Map;

public class ConstructBinaryTreeFromPreorderAndInorderTraversal_105_Medium {

	public static void main(String[] args) {

		int[] pre = {3,9,20,15,7}; 
		int[] in = {9,3,15,20,7};
		TreeNode root = buildTree(pre, in);
	}
	
	/*
	 * Reference : https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/discuss/34543/Simple-O(n)-without-map
	 * 
	 * preorder: [1, 2, 4, 5, 3, 6]
	 * inorder: [4, 2, 5, 1, 6, 3]
	 * 
	 * Algo: Just recurse on the full remaining arrays and stop when you come across the 
	 * 1 (i.e. root element in above example arr) in inorder. That's what my above solution does. 
	 * 
	 * Each recursive call gets told where to stop, and it tells its sub calls where to stop. 
	 * It gives its own root value as stopper to its left sub call and its parent`s stopper as 
	 * stopper to its right sub call.
	 * 
	 * 
	 * Time: O(N)
	 * Space : O(N) [s every element of inorder is visited and there are N elements in inorder, hence O(N)]
	 * 
	 * */
	
	private static int preIdx = 0, inIdx = 0;
    public static TreeNode buildTree(int[] preorder, int[] inorder) {
       return helper(preorder, inorder, Integer.MIN_VALUE);
    }
    
    private static TreeNode helper(int[] pre, int[] in, int stop) {
        if(preIdx == pre.length) return null;
        
        if(in[inIdx] == stop) {
            inIdx++;
            return null;
        }
        
        TreeNode root = new TreeNode(pre[preIdx++]);
        root.left  = helper(pre, in, root.val);
        root.right = helper(pre, in, stop);
        
        return root;
    }
	
    
    /*
     * Reference : https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/discuss/34538/My-Accepted-Java-Solution
     * Time and Space : O(N)
     */
    // using map
    int pre_idx = 0;
    public TreeNode buildTree2(int[] preorder, int[] inorder) {
        Map<Integer, Integer> map = new HashMap<>();
        int idx = 0;
        for(int num : inorder) {
            map.put(num, idx++);
        }
        
       return helper2(preorder, inorder, 0, inorder.length-1, map);
    }
    
    private TreeNode helper2(int[] pre, int[] in, int start, int end, Map<Integer, Integer> map) {
        if(start > end) {
            return null;
        }
        
        TreeNode root = new TreeNode(pre[pre_idx++]);
        int index = map.get(root.val);
        root.left  = helper2(pre, in, start, index-1, map);
        root.right = helper2(pre, in, index+1, end, map);
        
        return root;
    }
    
    
    
	/**
	 * Definition for a binary tree node.
	 */
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
