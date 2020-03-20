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
