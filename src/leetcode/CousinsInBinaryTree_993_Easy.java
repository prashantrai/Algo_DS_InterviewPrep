package leetcode;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

public class CousinsInBinaryTree_993_Easy {

	public static void main(String[] args) {
		
		// https://leetcode.com/problems/cousins-in-binary-tree/
		//Input: root = [1,2,3,null,4,null,5], x = 5, y = 4
		//Expected Output: true
		
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		
		root.left.right  = new TreeNode(4);
		root.right.right = new TreeNode(5);
		
		root.right.right.left = new TreeNode(7);
		root.right.right.left.right = new TreeNode(8);
		
		root.right.right.right = new TreeNode(6);
		
		
		boolean res = isCousins(root, 4, 5);
//		boolean res = isCousins(root, 8, 6);
		System.out.println(res);
		
		/*
		 * [1,3,2,null,6,5,4,null,7,10,null,8,null,null,null,11,13,9,15,12,14]
			14
			9
		 */
		
	}
	
	// https://leetcode.com/problems/cousins-in-binary-tree/discuss/489453/My-Java-Solution-BFS
	// Working - BFS
	//-- Time : O(n) Space:  O(n)
	public static boolean isCousins(TreeNode root, int x, int y) {
        if(root == null) {
            return false;
        }
        
        Queue<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        
        int x_depth = -1;
        int y_depth = -2;
        int level = 0;
        
        while(!q.isEmpty()) {
        	int size = q.size();

        	for(int i=0; i<size; i++) {
        		
        		TreeNode curr = q.poll();
        		
        		//same parent
        		if(curr.left != null && curr.right != null) {
        			if(curr.left.val == x && curr.right.val == y)
        				return false;
        			
        			if(curr.left.val == y && curr.right.val == x)
        				return false;
        		}
        		
        		if(curr.val == x) 
        			x_depth = level;
        		
        		else if(curr.val == y) 
        			y_depth = level;
        		
        		if(curr.left != null)
        			q.offer(curr.left);
        		
        		if(curr.right != null)
        			q.offer(curr.right);
        		
        	}
        	level++;
        }
        return x_depth == y_depth;
	}
	
	//not passing all tests on Leetcode
	public static boolean isCousins3(TreeNode root, int x, int y) {
        if(root == null) {
            return false;
        }
        
        Queue<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        
        boolean x_Found = false;
    	boolean y_Found = false;
    	
    	TreeNode x_Parent = null;
        TreeNode y_Parent = null;
        
        while(!q.isEmpty()) {
        	
        	int size = q.size();
        	for(int i=0; i<size; i++) {
        		
        		TreeNode curr = q.poll();
        		
        		if(curr.left != null && (curr.left.val == x || curr.left.val == y)) {
        			if(curr.left.val == x) {
        				x_Parent = curr;
            			x_Found = true;
        			} else {
           				y_Parent = curr;
               			y_Found = true;
        			}
        			
        		}
        		
        		if(curr.right != null && (curr.right.val == x || curr.right.val == y)) {
        			if(curr.right.val == x) {
        				x_Parent = curr;
            			x_Found = true;
        			} else {
           				y_Parent = curr;
               			y_Found = true;
        			}
        		}
        		
        		if(x_Found && y_Found) {
        			if(x_Parent.val != y_Parent.val) 
        				return true;
        		}
        		else if(x_Found || y_Found) break;
        		
        		if(curr.left != null)
        			q.offer(curr.left);
        		
        		if(curr.right != null)
        			q.offer(curr.right);
        		
        	}
        }
        
        return false;
        
	}
	private static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

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
			//return "TreeNode [val=" + val + ", left=" + left + ", right=" + right + "]";
			return ""+val;
		}
		
		
	}
	
	//--Leetcode Article solution - Premium
	/**
	 * Definition for a binary tree node.
	 * public class TreeNode {
	 *     int val;
	 *     TreeNode left;
	 *     TreeNode right;
	 *     TreeNode(int x) { val = x; }
	 * }
	 */
	class Solution {

	    public boolean isCousins(TreeNode root, int x, int y) {

	        // Queue for BFS
	        Queue <TreeNode> queue = new LinkedList <> ();
	        queue.add(root);

	        while (!queue.isEmpty()) {
	            boolean siblings = false;
	            boolean cousins = false;

	            int nodesAtDepth = queue.size();

	            for (int i = 0; i < nodesAtDepth; i++) {
	                // FIFO
	                TreeNode node = queue.remove();

	                // Encountered the marker.
	                // Siblings should be set to false as we are crossing the boundary.
	                if (node == null) {
	                    siblings = false;
	                } else {
	                    if (node.val == x || node.val == y) {
	                        // Set both the siblings and cousins flag to true
	                        // for a potential first sibling/cousin found.
	                        if (!cousins) {
	                            siblings = cousins = true;
	                        } else {
	                            // If the siblings flag is still true this means we are still
	                            // within the siblings boundary and hence the nodes are not cousins.
	                            return !siblings;
	                        }
	                    }

	                    if (node.left != null) queue.add(node.left);
	                    if (node.right != null) queue.add(node.right);
	                    // Adding the null marker for the siblings
	                    queue.add(null);
	                }
	            }
	            // After the end of a level if `cousins` is set to true
	            // This means we found only one node at this level
	            if (cousins) return false;
	        }
	        return false;
	    }
	}
	

}
