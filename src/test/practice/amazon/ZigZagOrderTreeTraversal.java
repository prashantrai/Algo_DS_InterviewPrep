package test.practice.amazon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class ZigZagOrderTreeTraversal {

	public static void main(String[] args) {
		TreeNode tree = new TreeNode(20);
		tree.insertInOrder(10);
		tree.insertInOrder(25);
		tree.insertInOrder(5);
		tree.insertInOrder(15);
		tree.insertInOrder(24);
		tree.insertInOrder(28);

		traverseZigZagOrder(tree);

		System.out.println("\nResult:: " + zigzagLevelOrder(tree));
	}

	// --https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/submissions/
	// --
	//-- https://medium.com/@harycane/binary-tree-zigzag-level-order-traversal-5b96a3e1b767

	/**
	 * Just a modified version of level order traversal
	 * 
	 * Complexity Analysis: 
	 * Since each node is visited at least once, the Time
	 * complexity is T O(n). And since the Queue can contain all the leaf nodes in
	 * the worst case, the Space complexity is also S O(n).
	 */
	public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {

		List<List<Integer>> res = new ArrayList<>();
		if (root == null)
			return res;

		LinkedList<TreeNode> q = new LinkedList<>();
		q.addLast(root);
		boolean leftToRight = false; // --Declare this to decide which side to traverse from
		while (!q.isEmpty()) {
			int size = q.size();
			List<Integer> list = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				TreeNode curr = q.removeFirst();

				if (leftToRight) {
					list.add(0, curr.data);
				} else {
					list.add(curr.data);
				}

				if (curr.left != null)
					q.addLast(curr.left);
				if (curr.right != null)
					q.addLast(curr.right);

			}
			leftToRight = !leftToRight;
			res.add(list);
		}
		return res;

	}

	
	//--https://www.geeksforgeeks.org/zigzag-tree-traversal/
	public static void traverseZigZagOrder(TreeNode root) {

		if (root == null)
			return;

		Stack<TreeNode> currLevel = new Stack<TreeNode>();
		Stack<TreeNode> nextLevel = new Stack<TreeNode>();
		Stack<TreeNode> temp;

		boolean leftToRight = true; // -- Direction of traversal

		currLevel.push(root);

		while (!currLevel.isEmpty()) {

			TreeNode node = currLevel.pop();
			if (node != null)
				System.out.print(node.data + " ");

			if (!leftToRight) {
				addElements(nextLevel, node.right);
				addElements(nextLevel, node.left);
			} else {
				addElements(nextLevel, node.left);
				addElements(nextLevel, node.right);
			}

			if (currLevel.isEmpty()) {
				temp = currLevel;
				currLevel = nextLevel;
				nextLevel = temp;

				leftToRight = !leftToRight;
			}

		}
	}

	private static void addElements(Stack<TreeNode> nodes, TreeNode node) {

		if (node != null) {
			nodes.push(node);
		}
	}

}
