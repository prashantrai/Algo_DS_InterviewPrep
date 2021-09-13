package Reddit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class VerticalOrderTraversalOfABinaryTree_987_Hard {

	public static void main(String[] args) {
		
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		
		root.right.left = new TreeNode(6);
		root.right.right = new TreeNode(7);
		
		List<List<Integer>> res = verticalTraversal(root);
		
		System.out.println("Expected: [[4],[2],[1,5,6],[3],[7]], Actual: "+res);
		
	}
	
	/* https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/
	 * 
	 * Algorithm
		To find the location of every node, we can use a depth-first search. During the search, we will maintain the location (x, y) of the node. As we move from parent to child, the location changes to (x-1, y+1) or (x+1, y+1) depending on if it is a left child or right child. [We use y+1 to make our sorting by decreasing y easier.]
		To report the locations, we sort them by x coordinate, then y coordinate, so that they are in the correct order to be added to our answer.
	
	Time Complexity: O(NlogN), where N is the number of nodes in the given tree.
	Space Complexity: O(N)
	
	
	Reference: 
	https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/discuss/231125/Java-HashMap-and-TreeMap-and-PriorityQueue-Solution
	https://massivealgorithms.blogspot.com/2019/02/leetcode-987-vertical-order-traversal.html#:~:text=Time%20Complexity%3A%20O(Nlo,nodes%20in%20the%20given%20tree.
	https://www.cnblogs.com/Dylan-Java-NYC/p/12014476.html
	*/ 
	
	
	static Map<Integer, TreeMap<Integer, PriorityQueue<Integer>>> map = new HashMap<>();
	static int minX, maxX;
	public static List<List<Integer>> verticalTraversal(TreeNode root) {
		List<List<Integer>> res = new ArrayList<>();
		
		helper(root, 0,0);
		
		for(int i=minX; i<=maxX; i++) {
			List<Integer> level = new ArrayList<>();
			for(int key : map.get(i).keySet()) {
				while (!(map.get(i).get(key)).isEmpty()) {
                    level.add(map.get(i).get(key).poll());
                }
			}
			
			if(!level.isEmpty())
				res.add(level);
		} 
		
		return res;
	}
	
	private static void helper(TreeNode node, int x, int y) {
		if(node == null) return;
		
		// these will help us have the minimum key and maximum key to use it iterate 
        // HashMap (as HashMap is unordered)
		minX = Math.min(minX, x);
		maxX = Math.max(maxX, x);
		
		if(!map.containsKey(x)) {
			map.put(x, new TreeMap<Integer, PriorityQueue<Integer>>());
		}
		if(!map.get(x).containsKey(y)) {
			map.get(x).put(y, new PriorityQueue<Integer>());
		}
		
		map.get(x).get(y).offer(node.val);
		
		helper(node.left, x-1, y+1);
		helper(node.right, x+1, y+1);
	}
	
	
	/**
	 * Definition for a binary tree node.
	 */ 
	static class TreeNode {
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
