package leetcode;

public class ConstructBinarySearchTreeFromPreorderTraversal_1008_Medium {

	public static void main(String[] args) {
		
		int[] preorder = {8,5,1,7,10,12};
		TreeNode root = bstFromPreorder(preorder);
		
	}
	
	/*
	 * https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/
	 * https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/discuss/252232/JavaC%2B%2BPython-O(N)-Solution
	 * 
	 * Another Nice Approach (not the below sol) : https://www.youtube.com/watch?v=GvPBasOr_mE&ab_channel=KnowledgeCenter 
	 * 
	 * Time : O(N), Space O(N) because of recursive calls
	 * 
	 * */
	
	public static TreeNode bstFromPreorder(int[] preorder) {
		return helper(preorder, Integer.MAX_VALUE, new int[]{0});
	}
	
	
	/*
    Q: Why array 'i' is required for index count and why not use only 'i' as a variable and not as array?
    A: As this is a recursive implementaion having a local var 'i' will not work because when we return from the one of the call (i.e. base condition) it will reset it's value to back ward. We can use only 'i'
    as a global variable to avoid this issue or use of array.
    
    However, using an array would would keep track the of the value as we will passing the reference of the array and while updating the value inside array 'i'.
    */
	
	private static TreeNode helper(int[] pre, int bound, int[] i) {
		// bound here is also to verify when to start add the value as right child
        if(i[0] == pre.length || pre[i[0]] > bound) {  
            return null;
        }
		
		TreeNode root = new TreeNode(pre[i[0]++]);
		
		root.left = helper(pre, root.val, i);
		root.right = helper(pre, bound, i);  // bound here is start node for right child
		
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
