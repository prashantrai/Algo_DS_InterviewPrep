package leetcode;

import java.util.ArrayList;
import java.util.List;

public class DiameterOf_N_AryTree_1522_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	// Time:  O(N)
    // Space: O(N), because of recursion

	static int res;

	public static int diameter(Node root) {
		getHeight(root);
		return res;
	}

	// with only one max
	// https://leetcode.com/problems/diameter-of-n-ary-tree/discuss/750352/Java-Simpler-and-Cleaner-DFS-recursion/1061025
	public static int getHeight(Node root) {
		if (root == null)
			return 0;

		int curMax = 0;
		for (Node child : root.children) {
			int height = getHeight(child);
			res = Math.max(res, curMax + height);
			curMax = Math.max(curMax, height);
		}
		return curMax + 1;
	}

	
	// https://leetcode.com/problems/diameter-of-n-ary-tree/discuss/750352/Java-Simpler-and-Cleaner-DFS-recursion
	public static int diameter2(Node root) {
		if (root == null)
			return 0;
		getHeight2(root);
		return res;
	}

	private static int getHeight2(Node root) {
		if (root == null)
			return 0;

		// to fetch the longest path between any two nodes we need to keep track of 2
		// heights and add and update the res with max between res and max1 + max2
		int max1 = 0;
		int max2 = 0;

		for (Node child : root.children) {
			int currHeight = getHeight(child);
			if (currHeight > max1) {
				// save max1 height to max2 and update max1, why? because we have to track
				// height between 2 nodes so if max1 has a new one then we save the curr
				// max1 to max2 and calculate the total max height i.e. diameter

				max2 = max1;
				max1 = currHeight;
			} else if (currHeight > max2) {
				max2 = currHeight;
			}
		}

		res = Math.max(res, max1 + max2); // max diameter so far

		return max1 + 1;
	}

	// Definition for a Node.
	private static class Node {
		public int val;
		public List<Node> children;

		public Node() {
			children = new ArrayList<Node>();
		}

		public Node(int _val) {
			val = _val;
			children = new ArrayList<Node>();
		}

		public Node(int _val, ArrayList<Node> _children) {
			val = _val;
			children = _children;
		}
	};

}