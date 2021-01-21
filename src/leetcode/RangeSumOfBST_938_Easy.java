package leetcode;

import java.util.Stack;

public class RangeSumOfBST_938_Easy {

	public static void main(String[] args) {
		
	}
	
	
	
	/* https://leetcode.com/problems/range-sum-of-bst/
	 * https://leetcode.com/problems/range-sum-of-bst/discuss/192019/JavaPython-3-3-similar-recursive-and-1-iterative-methods-w-comment-and-analysis.
   	 *
     *  Time: O(n), space: O(h), where n is the number of total nodes, h is the height of the tree.
     */
    public static int rangeSumBST(TreeNode root, int L, int R) {
        if (root == null) return 0; // base case.
        
        if(root.val < L) return rangeSumBST(root.right, L, R);
        if(root.val > R) return rangeSumBST(root.left, L, R);
        
        return root.val + rangeSumBST(root.right, L, R) + rangeSumBST(root.left, L, R);
    }
    
    public static int rangeSumBST2(TreeNode root, int low, int high) {
        if(root == null) return 0;
        
        int n = 0;
        
        // inbound
        if(root.val >= low && root.val <= high) n = root.val;
        
        n += rangeSumBST(root.left, low, high);
        n += rangeSumBST(root.right, low, high);
        
        return n;
    }
    
    public static int rangeSumBST3(TreeNode root, int low, int high) {
        if(root == null) return 0;
        
        int sum = 0;
        Stack<TreeNode> stk = new Stack<>();
        stk.push(root);
        
        while(!stk.isEmpty()) {
            TreeNode node = stk.pop();
            
            if(node == null) continue;
            
            if(node.val > low) stk.push(node.left); // left is possible candidate
            if(node.val < high) stk.push(node.right); // right is possible candidate
            
            if(node.val >= low && node.val <= high) {
                sum += node.val;
            }
            
        }
        
        return sum;
    }
    
    
    /**
     * Definition for a binary tree node.
     */
      private class TreeNode {
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
