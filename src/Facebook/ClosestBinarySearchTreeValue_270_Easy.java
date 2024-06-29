package Facebook;

public class ClosestBinarySearchTreeValue_270_Easy {

	public static void main(String[] args) {

		TreeNode root = new TreeNode(4);
		root.left = new TreeNode(2);
		root.right = new TreeNode(5);

		root.left.left = new TreeNode(1);
		root.left.right = new TreeNode(3);
		
		int res = closestValue(root, 3.714286);
		System.out.println("Expected: 4, Actual: " + res);
		
		res = closestValue(root, 4.5);
		System.out.println("Expected: 4, Actual: " + res);
		
		res = closestValue(root, 3.5);
		System.out.println("Expected: 3, Actual: " + res);

	}

	/* Leetcode Solution: 
	 * 
	 * Time complexity : O(H) since here one goes from root down to a leaf.
	 * Space complexity : O(1)
	 * */
	public static int closestValue(TreeNode root, double target) {
		int val = 0;
		int closest = root.val;
		
		while(root != null) {
			val = root.val;
			
			
			// '==' condition is to handle the use case when difference is same
			// after substracting val and closests from the target value
			// e.g. 0.5 is the one of the difference when the target is 4.5 or 3.5
			
			closest = Math.abs(val - target) < Math.abs(closest - target)
					|| ( Math.abs(val - target) == Math.abs(closest - target)
						&& val < closest ) ? val : closest;
			
			root = target < root.val ? root.left : root.right;
		}
		return closest;
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
