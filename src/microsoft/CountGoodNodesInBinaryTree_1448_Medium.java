package microsoft;

public class CountGoodNodesInBinaryTree_1448_Medium {

	public static void main(String[] args) {
		TreeNode root = new TreeNode(3, new TreeNode(1), new TreeNode(4)); 
		root.left.left = new TreeNode(3);
		root.right.left = new TreeNode(1);
		root.right.right = new TreeNode(5);
		
		goodNodes(root);
		System.out.println("Expected: 4, Actual: " + count);
		System.out.println("Expected: 4, Actual: " + goodNodes2(root));
	}

	
	/* 	Time O(N)
		Space O(height)
	 */

	static int count;
	public static int goodNodes(TreeNode root) {
        if(root == null) return 0;
        helper(root, Integer.MIN_VALUE);
        return count;
    }
	//pre-order
	public static void helper(TreeNode root, int max) {
		if(root == null) {
			return;
		}
		if(root.val >= max) 
			count++;
		
		max = Math.max(max, root.val);
		helper(root.left, max);
		helper(root.right, max);
	}

	
	/* 	Time O(N)
		Space O(height)
	*/

	public static int goodNodes2(TreeNode root) {
        if(root == null) return 0;
        return helper2(root, Integer.MIN_VALUE);
    }

	//pre-order
	public static int helper2(TreeNode root, int max) {
		if(root == null) return 0;
		
		//int result = 0;
		//if(root.val >= max) result++;
		int result = root.val >= max ? 1 : 0;
		
		max = Math.max(max, root.val);
		result += helper2(root.left, max);
		result += helper2(root.right, max);
		
		return result;
	}

	
	/**
	 * Definition for a binary tree node.
	 */
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
