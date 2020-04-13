package test.lyft;

public class LargestBSTSubtree_333_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	/**
	 * Definition for a binary tree node.
	 * public class TreeNode {
	 *     int val;
	 *     TreeNode left;
	 *     TreeNode right;
	 *     TreeNode(int x) { val = x; }
	 * }
	 */

	//https://leetcode.com/problems/largest-bst-subtree/
	
	//https://leetcode.com/problems/largest-bst-subtree/discuss/78891/Share-my-O(n)-Java-code-with-brief-explanation-and-comments
	
	public int largestBSTSubtree(TreeNode root) {
        if(root == null) return 0;
        
        Result res = traverse(root);
        return max;
        
    }
    
    int max = 0;
    
    private Result traverse(TreeNode root) {
        if (root == null) { 
            return new Result(0, Integer.MAX_VALUE, Integer.MIN_VALUE); 
        }
       
        Result left = traverse(root.left);
        Result right = traverse(root.right);
        
        if (left.size == -1 || right.size == -1 
            || root.val <= left.upper || root.val >= right.lower) {
            
            return new Result(-1, 0, 0);
        }
        
        int size = left.size + 1 + right.size;
        
        max = Math.max(size, max);
        
        return new Result(size, 
                          Math.min(left.lower, root.val), 
                          Math.max(right.upper, root.val));
    }
    
    
    static class Result {
        int size;
        int lower;
        int upper;
        
        public Result(int size, int lower, int upper) {
            this.size = size;
            this.lower = lower;
            this.upper = upper;
            
        }
    }

}
