package leetcode;

public class BinaryTreePruning {

	//https://www.geeksforgeeks.org/remove-all-nodes-which-lie-on-a-path-having-sum-less-than-k/
	//https://www.youtube.com/watch?v=77LJc56bwnE&list=PLU_sdQYzUj2keVENTP0a5rdykRSgg9Wp-&index=34
	
	
	public static void main(String[] args) {

	}
	
	public TreeNode pruneTree(TreeNode root) {
		
		if(root == null) return null;
		
		helper(root);
		return root;
		
	}
	
	//https://leetcode.com/problems/binary-tree-pruning/
	/**
	 * Problem Statement: 
	 * We are given the head node root of a binary tree, 
	 * where additionally every node's value is either a 0 or a 1.
	 * Return the same tree where every subtree (of the given tree) not containing a 1 has been removed.
	 */
	
	public boolean helper(TreeNode node) {
        if(node == null) return false;
        
        boolean right_has_1 = helper(node.right);
        boolean left_has_1 = helper(node.left);
        
        if(!right_has_1) {
            node.right = null;
        } 
        if(!left_has_1) {
            node.left = null;
        }
        
        return node.data == 1 || right_has_1 || left_has_1;
    } 


	private static class TreeNode {
		TreeNode left, right;
		int data;
		
		TreeNode(int data) {
			this.data = data;
		}
		
	}

}
