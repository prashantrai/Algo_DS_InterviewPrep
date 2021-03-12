package leetcode;

import java.util.ArrayDeque;
import java.util.Queue;

public class CheckIfAStringIsAValidSequenceFromRootToLeavesPathInBinaryTree_1430_Medium {

	public static void main(String[] args) {
		TreeNode root = new TreeNode(0);
		root.left = new TreeNode(1);
			root.left.left = new TreeNode(0);
			root.left.right = new TreeNode(1);
			
			root.left.left.right = new TreeNode(1);
			root.left.right.left = new TreeNode(0);
			root.left.right.right = new TreeNode(0);
			
		root.right = new TreeNode(0);
			root.right.left = new TreeNode(0);
			
		int[] input_arr = {0,1,0,1};
		
		boolean isValid = isValidSequence(root, input_arr);
		System.out.println("Expected: true, Actual: " + isValid);
		
		System.out.println("Expected: true, Actual: " + isValidSequence_BFS(root, input_arr));
	}

	/* LeetCode 1430. Check If a String Is a Valid Sequence from Root to Leaves Path in a Binary Tree	
	 * 
	 * Given a binary tree where each path going from the root to any leaf form a valid sequence, 
	 * 	check if a given string is a valid sequence in such binary tree.
		We get the given string from the concatenation of an array of integers arr and the concatenation 
		of all values of the nodes along a path results in a sequence in the given binary tree.
		
		See Here For Complete Question: https://leetcode.ca/all/1430.html
	 *  https://www.youtube.com/watch?v=23oR5ipWwk8&ab_channel=KnowledgeCenter
	 */
	
	
	//--DFS
	public static boolean isValidSequence(TreeNode root, int[] arr) {
		if(root == null || arr == null || arr.length == 0) return false;
		
		return dfs(root, arr, 0);
	}
	
	private static boolean dfs(TreeNode root, int[] arr, int idx) {
		if(root.val != arr[idx]) 
			return false;
		
		if(idx == arr.length-1)
			return root.left == null && root.right == null;
		
		return ( (root.left != null && dfs(root.left, arr, idx+1))
				|| (root.right != null && dfs(root.right, arr, idx+1)) );
	}
	
	//--BFS : https://leetcode.ca/2019-10-30-1430-Check-If-a-String-Is-a-Valid-Sequence-from-Root-to-Leaves-Path-in-a-Binary-Tree/
	public static boolean isValidSequence_BFS(TreeNode root, int[] arr) {
		if(root == null || arr == null || arr.length == 0) return false;
		
		int length = arr.length;
		Queue<TreeNode> q = new ArrayDeque<>();
		q.offer(root);
		int idx = 0;
		
		while(!q.isEmpty() && idx < length) {
			int num = arr[idx];
			int size = q.size();
			
			for(int i=0; i<size; i++) {
				TreeNode node = q.poll();
				
				if(node.val != num) 
					continue;
				
				TreeNode left = node.left;
				TreeNode right = node.right;
				
				if(left != null) q.offer(left);
				if(right != null) q.offer(right);
				
				//at leaf node
				if(idx == length - 1 && left == null && right == null) 
					return true;
			}
			idx++;
		}
		
		return false;
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
