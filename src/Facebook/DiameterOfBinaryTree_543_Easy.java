package Facebook;

public class DiameterOfBinaryTree_543_Easy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/*
	Time Complexity: O(N). 
	This is because in our recursion 
	function longestPath, we only enter and exit from each 
	node once. We know this because each node is entered from 
	its parent, and in a tree, nodes only have one parent.
	
	Space Complexity: O(N). 
	The space complexity depends on 
	the size of our implicit call stack during our DFS, 
	which relates to the height of the tree. In the worst case, 
	the tree is skewed so the height of the tree is O(N). 
	If the tree is balanced, it'd be O(log N).
	 */
	

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
			return "[" + val + ", " + left + ", " + right + "]";
		}
	}
	
}
