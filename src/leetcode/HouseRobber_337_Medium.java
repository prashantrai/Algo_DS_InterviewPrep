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
	 * 
	 * 
	 * Reference: https://www.youtube.com/watch?v=FRP5l83XoZo&ab_channel=AlgorithmsMadeEasy
	 *
     * Time and space :: O(n)
	 * 
	 * */
	
	
	
	Map<TreeNode, Integer> memo = new HashMap<>(); //memoization
    public int rob(TreeNode root) {
        if(root == null) return 0;

        if(memo.containsKey(root)) 
            return memo.get(root);
        
        int sum = root.data;
        
        if(root.left != null) {
            sum += rob(root.left.left);
            sum += rob(root.left.right);
        }
        if(root.right != null) {
            sum += rob(root.right.left);
            sum += rob(root.right.right);
        }
        
        // starting sum from 2nd level e.g. in Expample 1 from node value 2
        int nextSum = rob(root.left) + rob(root.right);
        
        int result = Math.max(sum, nextSum);
        
        memo.put(root, result);
        
        return result;
    }
    
    
	//-- 2nd approach - exactly similar to above only change the map is in signature
	public static int rob2(TreeNode root) {
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
