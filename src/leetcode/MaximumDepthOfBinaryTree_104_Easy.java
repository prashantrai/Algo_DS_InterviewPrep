package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

public class MaximumDepthOfBinaryTree_104_Easy {

	public static void main(String[] args) {

		TreeNode root = new TreeNode(3);
		root.left = new TreeNode(9);
		root.right = new TreeNode(20);
		root.right.left = new TreeNode(15);
		root.right.right = new TreeNode(7);
		
		System.out.println("Expected: 3, Actual: "+maxDepth(root));
		
	}
	
	/*
		 3
	    / \
	   9  20
	     /  \
	   15   7
	   
	   return its depth = 3.
	*/
	
	public static int maxDepth(TreeNode root) {
	     
        if(root == null) return 0;
        
        int depth = 0;
        
        Deque<TreeNode> dq = new ArrayDeque();
        dq.offer(root);
        
        while(!dq.isEmpty()) {
            depth++;
            int size = dq.size();
                
            for(int i=0; i<size; i++) {
                TreeNode node = dq.poll();
                if(node.left != null) dq.offer(node.left);
                if(node.right != null) dq.offer(node.right);
            }
        }
        
        return depth;
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
