package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class KthSmallestElementIn_A_BST {

	public static void main(String[] args) {

	}

	// https://leetcode.com/problems/kth-smallest-element-in-a-bst/
	// https://leetcode.com/problems/kth-smallest-element-in-a-bst/solution/
	// Time and space: O(N)
    public static int kthSmallest(TreeNode root, int k) {

        List<Integer> nums = inorder(root, new ArrayList<Integer>());
        return nums.get(k-1);
        
        /* Working solution
        inorder2(root, k);
        return result;
        */
        /* Working solution
        int[] c = {0, 0}; // We can also use a class say Result with 2 var count and result
        inorder3(root, k, c);
        return c[1];
        */
    }
    
    private static List<Integer> inorder(TreeNode root, List<Integer> nums) {
        if(root != null) {
            inorder(root.left, nums);
            nums.add(root.val);
            inorder(root.right, nums);
        }
        return nums;
    }
    
    // iterative
    /*Complexity Analysis
     *  Time complexity: O(H+k), where H is a tree height. This complexity is defined by the stack, 
     *     which contains at least H+k elements, since before starting to pop out one has to go down 
     *     to a leaf. This results in O(log N + k) for the balanced tree and O(N+k) for completely 
     *     unbalanced tree with all the nodes in the left subtree.
     *	
     *	Space complexity: O(H) to keep the stack, where H is a tree height. 
     *		That makes O(N) in the worst case of the skewed tree, and O(logN) in the average 
     *		case of the balanced tree.
    */
    public static int kthSmallest_Iterative(TreeNode root, int k) {
        LinkedList<TreeNode> stack = new LinkedList<TreeNode>();

        while (true) {
          while (root != null) {
            stack.add(root);
            root = root.left;
          }
          root = stack.removeLast();
          if (--k == 0) return root.val;
          root = root.right;
        }
      }
    
    
    // with no array list
    // Time and space : O(n) (space will be O(N) as this is recursive)
    int count = 0;
    int result = Integer.MIN_VALUE;
    private void inorder2(TreeNode root, int k) {
        if(root == null) return;
        
        inorder2(root.left, k);
        count++;
        if(count == k) result = root.val;
        inorder2(root.right, k);
    }
    
    // apprach 3
    private void inorder3(TreeNode root, int k, int[] c) {
        if(root == null) return;
        
        inorder3(root.left, k, c);
        c[0]++;
        if(c[0] == k) c[1] = root.val;
        inorder3(root.right, k, c);
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
