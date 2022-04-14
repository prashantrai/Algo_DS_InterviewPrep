package lintcode;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class CloneBinaryTree_375_Medium_LintCode {

	public static void main(String[] args) {

	}
	
	// Working
	// https://www.lintcode.com/problem/375/solution/54204
	
	public TreeNode cloneTreeDFS(TreeNode root) {
	
		if(root == null) return root;
		
		TreeNode newRoot = new TreeNode(root.val);
		if(root.left != null) {
			newRoot.left = cloneTreeDFS(root.left);
		}
		if(root.right != null) {
			newRoot.right = cloneTreeDFS(root.right);
		}
		
		return newRoot;
	}
	
	// Working
	public TreeNode cloneTreeBFS(TreeNode root) {
		if(root == null) return root;
		
		Queue<TreeNode> q = new ArrayDeque<>();
		q.offer(root);
		Map<TreeNode, TreeNode> map = new HashMap<>();
		map.put(root, new TreeNode(root.val));
		
		while(!q.isEmpty()) {
			TreeNode curr = q.poll();
			if(curr.left != null && !map.containsKey(curr.left)) {
				map.put(curr.left, new TreeNode(curr.left.val));
				map.get(curr).left = map.get(curr.left);
				q.offer(curr.left);
			}
			if(curr.right != null && !map.containsKey(curr.right)) {
				map.put(curr.right, new TreeNode(curr.right.val));
				map.get(curr).right = map.get(curr.right);
				q.offer(curr.right);
			}
		}
		
		return map.get(root);
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
