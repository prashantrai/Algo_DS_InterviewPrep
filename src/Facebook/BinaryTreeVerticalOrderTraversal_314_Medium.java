package Facebook;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class BinaryTreeVerticalOrderTraversal_314_Medium {

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		
		root.right.left = new TreeNode(6);
		root.right.right = new TreeNode(7);
		
		List<List<Integer>> res = verticalOrder(root);
		System.out.println("1. Expected: [[4],[2],[1,5,6],[3],[7]], Actual: "+res);
		
		res = verticalOrder2(root);
		System.out.println("2. Expected: [[4],[2],[1,5,6],[3],[7]], Actual: "+res);

		
		root = new TreeNode(3);
		root.left = new TreeNode(9);
		root.right = new TreeNode(8);
		
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(0);
		
		root.right.left = new TreeNode(1);
		root.right.right = new TreeNode(7);
		
		res = verticalOrder(root);
		System.out.println("3. Expected: [[4],[9],[3,0,1],[8],[7]], Actual: "+res);
		
		res = verticalOrder2(root);
		System.out.println("4. Expected: [[4],[9],[3,0,1],[8],[7]], Actual: "+res);

	}
	
	/* It's a premium question
	 * For Complete Question: 
	 * https://leetcode.ca/all/314.html OR https://tenderleo.gitbooks.io/leetcode-solutions-/content/GoogleMedium/314.html 
	 * 
	 * Company:
	 * Adobe Amazon Bloomberg ByteDance Databricks Expedia Facebook Google Mathworks Microsoft Oracle Snapchat
	 * 
	 * Question: 
	 * Given a binary tree, return the vertical order traversal of its nodes' values. (ie, from top to bottom, column by column).
	 * If two nodes are in the same row and column, the order should be from left to right.
						
		Input: [3,9,8,4,0,1,7]

		     3
		    /\
		   /  \
		   9   8
		  /\  /\
		 /  \/  \
		 4  01   7
		
		Output:
		
		[
		  [4],
		  [9],
		  [3,0,1],  // not in sorted order and that's the difference between this and 987 problem
		  [8],
		  [7]
		]

	 * 
	 * */
	
	
	
	/* Better solution for interview. This is BFS
	 * 
	 * Traverse the Tree (in BFS) and add to a HashMap where 
	 * key will be level and value will be list of nodes at that level.
	 * 
	 * Keep track of max and min level to search the map and print the result. 
	 * 
	    Time: O(N), N is no of nodes, as we are using HashMap (not TreeMap) 
	                which gives us O(1) insertion but we iterating 
	                the to prepare result
	                
	    Space: O(N)
	 */
	public static List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if(root == null) return res;
	
        Map<Integer, List<Integer>> map = new HashMap<>();

        Queue<TreeNode> nodes  = new ArrayDeque<>();
        Queue<Integer> levels = new ArrayDeque<>();
        
        nodes.offer(root);
        
        // for level as we go left we'll subtract '1' from the peek value 
        // and for right we will add '1'
        levels.offer(0); // for first level i.e. root
        
        int min = 0;
        int max = 0;
        
        while (!nodes.isEmpty()) {
        	TreeNode front = nodes.poll();
        	int value = levels.poll();
        	
        	min = Math.min(min, value);
        	max = Math.max(max, value);
        	
        	if(!map.containsKey(value)) {
        		map.put(value, new ArrayList<Integer>());
        	}
        	map.get(value).add(front.val);
        	
        	if(front.left != null) {
        		nodes.offer(front.left);
        		levels.offer(value - 1);
        	}
        	if(front.right != null) {
        		nodes.offer(front.right);
        		levels.offer(value + 1);
        	}
        }

        for(int i=min; i<=max; i++) {
        	if(map.containsKey(i))
        		res.add(map.get(i));
        }
        
        return res;
	}
	
	
	// Time Complexity: N LogN, bacause of Tree Map as adding in TreeMap for n element is NlogN
	public static List<List<Integer>> verticalOrder2(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if(root == null) return res;
	
        Map<Integer, List<Integer>> map = new TreeMap<>();

        Queue<TreeNode> nodes  = new ArrayDeque<>();
        Queue<Integer> levels = new ArrayDeque<>();
        
        nodes.offer(root);
        
        // for level as we go left we'll subtract '1' from the peek value and for right we will add '1'
        levels.offer(0); // for first level i.e. root
        
        while (!nodes.isEmpty()) {
        	TreeNode front = nodes.poll();
        	int value = levels.poll();
        	
        	if(!map.containsKey(value)) {
        		map.put(value, new ArrayList<Integer>());
        	}
        	map.get(value).add(front.val);
        	
        	if(front.left != null) {
        		nodes.offer(front.left);
        		levels.offer(value - 1);
        	}
        	if(front.right != null) {
        		nodes.offer(front.right);
        		levels.offer(value + 1);
        	}
        }
        res.addAll(map.values());
        
        return res;
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
