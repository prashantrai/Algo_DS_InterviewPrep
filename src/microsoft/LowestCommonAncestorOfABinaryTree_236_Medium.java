package microsoft;

public class LowestCommonAncestorOfABinaryTree_236_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/*
	 * https://www.youtube.com/watch?v=uKhLoNaG9LI 
	 * 
	 * Taken from one of the comment posted in article : https://leetcode.com/articles/lowest-common-ancestor-of-a-binary-tree/
	 * 
	 * Time and Space: O(n)
	 * */
	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

		if (root == null)
			return null;
		if (root == p || root == q)
			return root;

		TreeNode left = lowestCommonAncestor(root.left, p, q);
		TreeNode right = lowestCommonAncestor(root.right, p, q);

		if (left != null && right != null)
			return root;

		return left != null ? left : right;
	}

	private static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

}
