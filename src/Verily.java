import java.util.ArrayDeque;
import java.util.Queue;

public class Verily {

	public static void main(String[] args) {

		TreeNode n_4 = new TreeNode(4, null, null);
		TreeNode n_5 = new TreeNode(5, null, null);
		TreeNode n_6 = new TreeNode(6, null, null);
		TreeNode n_7 = new TreeNode(7, null, null);

//		TreeNode n_2 = new TreeNode(2, n_4, n_5);
//		TreeNode n_3 = new TreeNode(3, n_6, n_7);

		TreeNode n_2 = new TreeNode(2, n_4, null);
		TreeNode n_3 = new TreeNode(3, n_6, null);

		TreeNode root = new TreeNode(1, n_2, n_3);

		System.out.println("isPerfect: " + is_Perfect(root));

		// clone tree
		TreeNode newRoot = cloneTreeDFS(root);
		System.out.println("isPerfect: " + is_Perfect(newRoot));

		System.out.println("Build start");
		TreeNode root_perfect = buildPerfectBinaryTree(newRoot);
		System.out.println("Build end");

		System.out.println(">>isPerfect: " + is_Perfect(root_perfect));
	}

	/*
	 * Implement a method generatePerfectTree() which should take root as input of
	 * an existing binary tree and return a new PERFECT binary tree. We should not
	 * modify the input i.e. in order to generate perfect tree we should deep copy
	 * input Tree and then make that perfect.
	 * 
	 * In order to make tree perfectly balanced you should add dummy node (say node
	 * with value 0)
	 * 
	 * 
	 */

	// Working
	// build perfect binary tree
	public static TreeNode buildPerfectBinaryTree(TreeNode root) {

		Queue<TreeNode> q = new ArrayDeque<>();
		q.offer(root);
		int depth = depth(root);
		System.out.println("current depth: " + depth);

		int d = 1;
		while (!q.isEmpty()) {
			TreeNode curr = q.poll();

			if (curr.left == null) {
				curr.left = new TreeNode(0);
			}
			if (curr.right == null) {
				curr.right = new TreeNode(0);
			}

			q.offer(curr.left);
			q.offer(curr.right);

			System.out.println("d = " + d);

			if (d == depth)
				break;

			d++;
		}

		return root;
	}

	// clone a binary tree
	public static TreeNode cloneTreeDFS(TreeNode root) {

		if (root == null)
			return root;

		TreeNode newRoot = new TreeNode(root.val);
		if (root.left != null) {
			newRoot.left = cloneTreeDFS(root.left);
		}
		if (root.right != null) {
			newRoot.right = cloneTreeDFS(root.right);
		}

		return newRoot;
	}

	// Check if binary tree is perfect or not
	static boolean is_Perfect(TreeNode root) {
		int d = depth(root);
		return is_perfect(root, d, 0);
	}

	
	
	// Calculate the depth
	static int depth(TreeNode root) {
		// Root being null means tree doesn't exist.
		if (root == null)
			return 0;

		// Get the depth of the left and right subtree
		// using recursion.
		int leftDepth = depth(root.left);
		int rightDepth = depth(root.right);

		// Choose the larger one and add the root to it.
		if (leftDepth > rightDepth)
			return (leftDepth + 1);
		else
			return (rightDepth + 1);
	}
	
	// this one only calculated based on the left node
	static int depth2(TreeNode node) {
		int d = 0;
		while (node != null) {
			d++;
			node = node.left;
		}
		return d;
	}

	// Check if the tree is perfect binary tree
	static boolean is_perfect(TreeNode root, int d, int level) {

		// Check if the tree is empty
		if (root == null)
			return true;

		// If for children
		if (root.left == null && root.right == null)
			return (d == level + 1);

		if (root.left == null || root.right == null)
			return false;

		return is_perfect(root.left, d, level + 1) && is_perfect(root.right, d, level + 1);
	}

	// Tree Node
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

//Checking if a binary tree is a perfect binary tree in Java

//https://www.programiz.com/dsa/perfect-binary-tree

class PerfectBinaryTree {

	static class Node {
		int key;
		Node left, right;
	}

	// Calculate the depth
	static int depth(Node node) {
		int d = 0;
		while (node != null) {
			d++;
			node = node.left;
		}
		return d;
	}

	// Check if the tree is perfect binary tree
	static boolean is_perfect(Node root, int d, int level) {

		// Check if the tree is empty
		if (root == null)
			return true;

		// If for children
		if (root.left == null && root.right == null)
			return (d == level + 1);

		if (root.left == null || root.right == null)
			return false;

		return is_perfect(root.left, d, level + 1) && is_perfect(root.right, d, level + 1);
	}

	// Wrapper function
	static boolean is_Perfect(Node root) {
		int d = depth(root);
		return is_perfect(root, d, 0);
	}

	// Create a new node
	static Node newNode(int k) {
		Node node = new Node();
		node.key = k;
		node.right = null;
		node.left = null;
		return node;
	}

	/*
	 * public static void main(String args[]) { Node root = null; root = newNode(1);
	 * root.left = newNode(2); root.right = newNode(3); root.left.left = newNode(4);
	 * root.left.right = newNode(5);
	 * 
	 * if (is_Perfect(root) == true)
	 * System.out.println("The tree is a perfect binary tree"); else
	 * System.out.println("The tree is not a perfect binary tree"); }
	 */
}
