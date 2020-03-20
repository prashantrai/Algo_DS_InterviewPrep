package leetcode;

import java.util.HashMap;
import java.util.Map;

public class HouseRobber_337_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	/**
	 * Definition for a binary tree node.
	 * public class TreeNode {
	 *     int val;
	 *     TreeNode left;
	 *     TreeNode right;
	 *     TreeNode(int x) { val = x; }
	 * }
	 */
	
	/*
	 * https://leetcode.com/problems/house-robber-iii/
	 * 
	 * https://leetcode.com/problems/house-robber-iii/discuss/480679/Java-DP-with-explanation
	 * 
	 * EXPLAINATION: https://leetcode.com/problems/house-robber-iii/discuss/79330/Step-by-step-tackling-of-the-problem
	 * 
	 * ANOTHER APPROACHES: 
	 * https://leetcode.com/problems/house-robber-iii/discuss/479024/O(N)-Bottom-UP-DFS-simple-%2B-easy-to-understand
	 * */
	public static int rob(TreeNode root) {

		return helper(root, new HashMap<TreeNode, Integer>());

	}

	public static int helper(TreeNode root, Map<TreeNode, Integer> map) {

		if (root == null)
			return 0;

		if (map.containsKey(root))
			return map.get(root);

		int result = 0;
		if (root.left != null) {
			result += helper(root.left.left, map) + helper(root.left.right, map);
		}

		if (root.right != null) {
			result += helper(root.right.left, map) + helper(root.right.right, map);
		}

		result = Math.max(result + root.data, helper(root.left, map) + helper(root.right, map));

		map.put(root, result);

		return result;

	}

}
