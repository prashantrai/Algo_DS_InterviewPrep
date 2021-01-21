package leetcode;

import java.util.ArrayList;
import java.util.List;

public class BinaryTreePaths_257_Easy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	//https://leetcode.com/problems/binary-tree-paths/
	
	
	// Working
	// Time O(N), Space: O(n)
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<>();
        if(root == null) return paths;
    
        dfs(root, "", paths);
        
        return paths;
    }
    
    private void dfs(TreeNode node, String currPath, List<String> paths) {
        currPath += node.val;
        if(node.left == null && node.right == null) {
            paths.add(currPath);
            return;
        }
        
        if(node.left != null) 
            dfs(node.left, currPath + "->", paths);
        if(node.right != null) 
            dfs(node.right, currPath + "->", paths);
        
    }
	
	// Working
	// Time O(N), Space: O(n)
    public static List<String> binaryTreePaths2(TreeNode root) {
        List<String> result = new ArrayList<>();
        if(root == null) return result;
        
        String currPath = Integer.toString(root.val);
        if(root.left == null && root.right == null)  result.add(currPath);
        if(root.left != null) dfs(root.left, currPath, result);
        if(root.right != null) dfs(root.right, currPath, result);
        
        return result;
    }
    
    private static void dfs2(TreeNode node, String currPath, List<String> result) {
        currPath += "->" + node.val;
        
        if(node.left == null && node.right == null) {
            result.add(currPath);
            return;
        }
        
        if(node.left != null) dfs(node.left, currPath, result);
        if(node.right != null) dfs(node.right, currPath, result);
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
