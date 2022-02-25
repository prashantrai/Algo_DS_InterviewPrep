package leetcode;

public class DiameterOfBinaryTree_543_Easy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	static int diameter;
    public static int diameterOfBinaryTree(TreeNode root) {
        dfs(root);
        return diameter;
    }
    
    private static int dfs(TreeNode node) {
        if(node == null) return 0;
        
        // recursively find the longest path in
        // both left child and right child
        int leftPath  = dfs(node.left);
        int rightPath = dfs(node.right);
        
        // update the diameter if left_path plus right_path is larger
        diameter = Math.max(diameter, leftPath+rightPath);
        
         // return the longest one between left_path and right_path;
        // remember to add 1 for the path connecting the node and its parent
        
        return Math.max(leftPath, rightPath) + 1;
    }
	
}
