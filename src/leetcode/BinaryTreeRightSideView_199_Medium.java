package leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class BinaryTreeRightSideView_199_Medium {

	public static void main(String[] args) {
		TreeNode node_4 = new TreeNode(4);
		TreeNode node_5 = new TreeNode(5);
		
		TreeNode node_2 = new TreeNode(2, null, node_5);
		TreeNode node_3 = new TreeNode(3, null, node_4);

		TreeNode root = new TreeNode(1, node_2, node_3);
		
		System.out.println("Expected: [1,3,4], Actual: " + rightSideView(root));
	}

	
	public static List<Integer> rightSideView(TreeNode root) {
        List<Integer> rightSide = new ArrayList<>();
        
        if(root == null) return rightSide;
        
        Queue<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        
        while(!q.isEmpty()) {
            int size = q.size();
            
            for(int i=0; i<size; i++) {
                TreeNode curr = q.poll();
                
                //last node in the queue will be visible from the righside, i.e. there no node to block the view. So, add this in result list
                if(i == size-1) {
                    rightSide.add(curr.val);              
                }
                
                if(curr.left != null) q.offer(curr.left);
                if(curr.right != null) q.offer(curr.right);
            } 
        }
        return rightSide;        
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
		@Override
		public String toString() {
			//return "[" + val + ", " + left + ", " + right + "]";
			return ""+val;
		}
	}
}
