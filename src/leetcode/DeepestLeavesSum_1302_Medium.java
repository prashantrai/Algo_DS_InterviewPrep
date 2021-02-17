package leetcode;

import java.util.ArrayDeque;
import java.util.Queue;

public class DeepestLeavesSum_1302_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static int deepestLeavesSum(TreeNode root) {
        Queue<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        int sum = 0;
        while(!q.isEmpty()) {
            sum = 0;    // reset for each level
            int size = q.size();
            for(int i=0; i<size; i++) {
                TreeNode node = q.poll();
                sum += node.val;
                if(node.left != null) q.offer(node.left);
                if(node.right != null) q.offer(node.right);
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
