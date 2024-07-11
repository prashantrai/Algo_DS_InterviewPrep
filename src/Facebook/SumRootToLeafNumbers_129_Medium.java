package Facebook;

public class SumRootToLeafNumbers_129_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/*
	 * Recursive pre-order 
	 * 
	 * Time: O(n), since we have to visit each node 
	 * Space: O(h), to keep the recursion tree stack, 
	 * where h is height of the tree.
	 */
	int rootToLeaf = 0;

	public int sumNumbers(TreeNode root) {
		preOrder(root, 0);
		return rootToLeaf;
	}

	private void preOrder(TreeNode r, int currNum) {
		if (r == null)
			return;

		currNum = currNum * 10 + r.val;

		// if it's a leaf, update rootToLeaf sum
		if (r.left == null & r.right == null) {
			rootToLeaf += currNum;
		}

		preOrder(r.left, currNum);
		preOrder(r.right, currNum);
	}

	
	
	// another approach
	public int sumNumbers2(TreeNode root) {
		return sum(root, 0);
	}

	private int sum(TreeNode n, int s){
		if (n == null) return 0;
		if (n.right == null && n.left == null) 
			return s*10 + n.val;
		
		return sum(n.left, s*10 + n.val) + sum(n.right, s*10 + n.val);
	}
	
	
	private static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode() {}

		TreeNode(int val) {
			this.val = val;
		}

		TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}

		@Override
		public String toString() {
			// return "[" + val + ", " + left + ", " + right + "]";
			return "" + val;
		}
	}
	
	
}
