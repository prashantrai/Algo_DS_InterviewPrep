package TikTok;

import java.util.ArrayList;
import java.util.List;

public class NumberOfGoodLeafNodesPairs_1530_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/* refer: https://leetcode.com/problems/number-of-good-leaf-nodes-pairs/discuss/1372128/Java-DFS-Solution-with-Explanation.
	 * 
	 * Another approach with array instead of List: https://leetcode.com/problems/number-of-good-leaf-nodes-pairs/discuss/755793/Java-DFS/630505

	For Explaination: https://www.youtube.com/watch?v=4T2EjBpalpM&ab_channel=ChhaviBansal

	Time: O(N * l * r)
	Space: O(N)

	*/
	
	static int count;

	public static int countPairs(TreeNode root, int distance) {
		dfs(root, distance);
		return count;
	}

	private static List<Integer> dfs(TreeNode root, int d) {
		if (root == null)
			return new ArrayList<Integer>();

		// leaf found
		if (root.left == null && root.right == null) {
			List<Integer> sublist = new ArrayList<>();
			sublist.add(1);
			return sublist;
		}

		List<Integer> left = dfs(root.left, d);
		List<Integer> right = dfs(root.right, d);

		// find all nodes satisfying distance
		for (int l : left) {
			for (int r : right) {
				if (l + r <= d)
					count++; // pair found
			}
		}

		// return left and right node distances
		// +1 while returning, becuase the parent node will receive distance + 1
		List<Integer> ans = new ArrayList<>();
		for (int l : left) {
			ans.add(l + 1);
		}
		for (int r : right) {
			ans.add(r + 1);
		}

		return ans;
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
    }

}
