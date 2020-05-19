package leetcode;

import java.util.Stack;

public class BinarySearchTreeIterator_173_Medium {

	public static void main(String[] args) {
		
		TreeNode root = new TreeNode(7);
		root.left = new TreeNode(3);
		root.right = new TreeNode(15);
		root.right.left = new TreeNode(9);
		root.right.right = new TreeNode(20);
		
		
		BSTIterator iterator = new BSTIterator(root);
		iterator.next();    // return 3
		iterator.next();    // return 7
		iterator.hasNext(); // return true
		iterator.next();    // return 9
		iterator.hasNext(); // return true
		iterator.next();    // return 15
		iterator.hasNext(); // return true
		iterator.next();    // return 20
		iterator.hasNext(); // return false
		
	}
	
	//https://leetcode.com/problems/binary-search-tree-iterator/

	static class BSTIterator {

		private Stack<TreeNode> stk;

		public BSTIterator(TreeNode root) {
			stk = new Stack<>();
			fillStack(root);
		}

		/** @return the next smallest number */
		public int next() {
			TreeNode node = stk.pop();
			fillStack(node.right);

			return node.val;
		}

		/** @return whether we have a next smallest number */
		public boolean hasNext() {
			return !stk.isEmpty();
		}

		private void fillStack(TreeNode node) {
			while (node != null) {
				stk.push(node);
				node = node.left;
			}
		}
	}

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
