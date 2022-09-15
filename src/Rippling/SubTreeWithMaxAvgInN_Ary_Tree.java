package Rippling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubTreeWithMaxAvgInN_Ary_Tree {

	public static void main(String[] args) {
		TreeNode t_11 = new TreeNode(11);
		TreeNode t_2 = new TreeNode(2);
		TreeNode t_3 = new TreeNode(3);
		TreeNode t_15 = new TreeNode(15);
		TreeNode t_8 = new TreeNode(8);
		
		TreeNode t_12 = new TreeNode(12, Arrays.asList(t_11, t_2, t_3));
		TreeNode t_18 = new TreeNode(18, Arrays.asList(t_15, t_8));

		TreeNode root = new TreeNode(20, Arrays.asList(t_12, t_18));
		
		TreeNode res = maximumAverageSubtree(root);
		
		System.out.println("res: " + res.val);
		
		
	}
	
	/*
	 * Time: O(N), where N is the number of nodes in the tree. This is because we visit each 
	 * 				and every node only once, as we do in post-order traversal.
	 * 
	 * Space: O(N), because we will create N states for each of the nodes in the tree. 
	 * 			Also, in cases where we have a skewed tree, we will implicitly maintain 
	 * 			a recursion stack of size N, hence space complexity from this will also be O(N).
	 * */
	
	static double max = Integer.MIN_VALUE;
	static TreeNode maxNode = null;

	public static TreeNode maximumAverageSubtree(TreeNode root) {
	    if (root == null) return null;
	    helper(root);
	    return maxNode;
	}

	private static double[] helper(TreeNode root) {
	    if (root == null) 
	    	return new double[] {0, 0};

	    double curTotal = root.val;
	    double count = 1;
	    for (TreeNode child : root.children) {
	        double[] cur = helper(child);
	        curTotal += cur[0];
	        count += cur[1];
	    }        
	    double avg = curTotal / count;
	    if (count > 1 && avg > max) { //taking "at least 1 child" into account
	        max = avg;
	        maxNode = root;
	    }
	    return new double[] {curTotal, count};
	}

	private static class TreeNode {
		int val;
		List<TreeNode> children;
		TreeNode() {}
		TreeNode(int val) { 
			this.val = val;
			children = new ArrayList<>();
		}
		TreeNode(int val, List<TreeNode> children) {
			 this.val = val;
			 this.children = children;
		}
	}
}

/*
 * https://aaronice.gitbook.io/lintcode/trees/n-ary-tree-postorder-traversal
 * 
 * similar: https://leetcode.com/problems/maximum-average-subtree/

https://leetcode.com/discuss/interview-question/349617

Given an N-ary tree, find the subtree with the maximum average. Return the root of the subtree.
A subtree of a tree is the node which have at least 1 child plus all its descendants. 
The average value of a subtree is the sum of its values, divided by the number of nodes.

Example 1:

Input:
		 20
	   /   \
	 12     18
  /  |  \   / \
11   2   3 15  8

Output: 18
Explanation:
There are 3 nodes which have children in this tree:
12 => (11 + 2 + 3 + 12) / 4 = 7
18 => (18 + 15 + 8) / 3 = 13.67
20 => (12 + 11 + 2 + 3 + 18 + 15 + 8 + 20) / 8 = 11.125

18 has the maximum average so output 18.
  
  
 * */
