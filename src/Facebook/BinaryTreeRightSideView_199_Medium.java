package Facebook;

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

		// Right side view
		System.out.println("Expected: [1,3,4], Actual: " + rightSideView(root));

		// Left side view
		System.out.println("Expected: [1,2,5], Actual: " + leftSideView(root));
		
		// Both Left and Right side view
		System.out.println("Expected: [[1,2,5], [1,3,4]], Actual: " + leftAndRightSideView(root));
	}
	

	/*
    Time: O(N), since we need to visit each node
   
    Space: O(D), to keep the queues, where D is a tree diameter. 
    Let's use the last level to estimate the queue size. This level 
    could contain up to N/2 tree nodes in the case of complete binary tree.
    */
    
    // BFS
    public static List<Integer> rightSideView(TreeNode root) {
        List<Integer> rightSide = new ArrayList<>();
        
        if(root == null) return rightSide;
        
        Queue<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        
        while(!q.isEmpty()) {
            int size = q.size();
            
            for(int i=0; i<size; i++) {
                TreeNode curr = q.poll();
                
                // if it's the rightmost element: last node 
                // in the queue will be visible from the righside, 
                // i.e., there no node to block the view. So, 
                // add this in result list
                if(i == size-1) {
                    rightSide.add(curr.val);              
                }
                
                if(curr.left != null) q.offer(curr.left);
                if(curr.right != null) q.offer(curr.right);
            } 
        }
        return rightSide;        
    }

    // Left side view
    public static List<Integer> leftSideView(TreeNode root) {
        List<Integer> leftSide = new ArrayList<>();
        
        if(root == null) return leftSide;
        
        Queue<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        
        while(!q.isEmpty()) {
            int size = q.size();
            
            for(int i = 0; i < size; i++) {
                TreeNode curr = q.poll();
                
                // if it's the leftmost element: first node 
                // in the queue will be visible from the left side, 
                // i.e., there no node to block the view. So, 
                // add this in result list
                if(i == 0) {
                    leftSide.add(curr.val);
                }
                
                if(curr.left != null) q.offer(curr.left);
                if(curr.right != null) q.offer(curr.right);
            } 
        }
        return leftSide;
    }
    
    // Left and Right view
    public static List<List<Integer>> leftAndRightSideView(TreeNode root) {
        List<Integer> leftSide = new ArrayList<>();
        List<Integer> rightSide = new ArrayList<>();
        
        // or add to a hashmap with key as name like "leftView/righView"
        List<List<Integer>> res = new ArrayList<>();
        res.add(leftSide);
        res.add(rightSide);
        
        if(root == null) return res;
        
        Queue<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        
        while(!q.isEmpty()) {
            int size = q.size();
            
            for(int i = 0; i < size; i++) {
                TreeNode curr = q.poll();
                
                // if it's the leftmost element: first node 
                // in the queue will be visible from the left side, 
                // i.e., there no node to block the view. So, 
                // add this in result list
                if(i == 0) {
                	// left side view
                    leftSide.add(curr.val);
                    
                }
                
                if(i == size-1) {
                	// right side view
                	rightSide.add(curr.val);
                }
                
                if(curr.left != null) q.offer(curr.left);
                if(curr.right != null) q.offer(curr.right);
            } 
        }
        return res;
    }
    
    
    
	private static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode() {}

		TreeNode(int val) {
			this.val = val;
		}

		TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}

		@Override
		public String toString() {
			// return "[" + val + ", " + left + ", " + right + "]";
			return "" + val;
		}
	}

}
