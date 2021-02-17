package leetcode;

import java.util.ArrayList;
import java.util.List;

public class PathSum2_113_Medium {

	class TreeNode {
	      int val;
	      TreeNode left;
	      TreeNode right;
	      TreeNode(int x) { val = x; }
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	//--LeetCode Medium
	//https://www.youtube.com/watch?v=3B5gnrwRmOA
	//--https://leetcode.com/problems/path-sum-ii/submissions/
	
	//--A good resource for many problems and their solutions: 
	//--https://ttzztt.gitbooks.io/lc/content/path-sum-ii.html
	
	
	/*
	 * Complexity : [https://medium.com/@lenchen/leetcode-113-path-sum-ii-af368b7687d3]
	 * Because each node will be iterated once, it takes O(n) time if n
	 * denotes to counts of nodes in this tree. 
	 * 
	 * For space complexity, the calls in
	 * calling stack may reach h if h denotes to the length of the deepest path in
	 * the tree. So it’s space complexity is O(h). Or you can say O(logn) because h
	 * is bounded by logn.
	 */

	public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> paths = new ArrayList<>();
        
        helper(root, sum, new ArrayList<Integer>(), paths);
        
        
        return paths;
        
    }
    public void helper(TreeNode root, int sum, List<Integer> path, List<List<Integer>> paths) {
        
        if(root == null) {
            return;            
        }
        
        path.add(root.val);
        if(sum == root.val && root.left == null && root.right == null) {
            paths.add(path);
            return;
        }
        
        sum = sum - root.val;
        helper(root.left, sum, new ArrayList<Integer>(path), paths); //--new list for each call
        helper(root.right, sum, new ArrayList<Integer>(path), paths);
    }
	
}
