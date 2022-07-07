package Box;


public class UnivaluedBinaryTree_965_Easy {

	public static void main(String[] args) {

		TreeNode n1 = new TreeNode(1, null, null);
		TreeNode n2 = new TreeNode(1, null, null);
		TreeNode n3 = new TreeNode(1, null, null);
		
		TreeNode n4 = new TreeNode(1, n1, n2);
		TreeNode n5 = new TreeNode(1, null, n3);
		
		
		TreeNode root = new TreeNode(1, n4, n5);
		
		System.out.println("Expected: true, Actual: " + isUnivalTree(root));
		System.out.println("Expected: true, Actual: " + isUnivalTree2(root));
		
	}

	/*
	 * Time and Space: O(N)
	 */

	public static boolean isUnivalTree(TreeNode root) {
		boolean left = root.left == null || 
				(root.val == root.left.val && isUnivalTree(root.left));

		boolean right = root.right == null || 
				(root.val == root.right.val && isUnivalTree(root.right));

		return left && right;
	}
	
	
	// Approach 2
	public static boolean isUnivalTree2(TreeNode root) {
        return dfs(root, root.val);
    }
    private static boolean dfs(TreeNode n, int v) {
        if (n == null) { return true; }
        if (n.val != v) { return false; }
        return dfs(n.left, v) && dfs(n.right, v);
    } 
	

	// Tree Node
	private static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode() {
		}

		TreeNode(int val) {
			this.val = val;
		}

		TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}

}
