package leetcode;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class CloneBinaryTreeWithRandomPointer_1485_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public NodeCopy copyRandomBinaryTree(Node root) {
		Map<Node, NodeCopy> map = new HashMap<>();
		return helperDFS(root, map);
		// return helperBFS(root, map);
	}

	/*
	 * Time complexity: O(n) - we run on the entire tree once. Our map acts as
	 * memoization to prevent redoing work and avoid cycle 
	 * 
	 * Space copmlexity: O(n) -
	 * We're creating a hashmap to save all nodes in the tree.
	 */
	private static NodeCopy helperDFS(Node root, Map<Node, NodeCopy> map) {
		if (root == null)
			return null;

		if (map.containsKey(root))
			return map.get(root);

		NodeCopy copyNode = new NodeCopy(root.val);
		map.put(root, copyNode);

		copyNode.left = helperDFS(root.left, map);
		copyNode.right = helperDFS(root.right, map);
		copyNode.random = helperDFS(root.random, map);

		return copyNode;
	}

	private static NodeCopy helperBFS(Node root, Map<Node, NodeCopy> map) {
		if (root == null)
			return null;

		// Map<Node, NodeCopy> map = new HashMap<>();
		Queue<Node> q = new ArrayDeque<>();
		q.offer(root);
		map.put(root, new NodeCopy(root.val));

		while (!q.isEmpty()) {
			Node curr = q.poll();
			if (curr.left != null) {
				if (!map.containsKey(curr.left)) {
					map.put(curr.left, new NodeCopy(curr.left.val));
				}
				map.get(curr).left = map.get(curr.left);
				q.offer(curr.left);
			}
			if (curr.right != null) {
				if (!map.containsKey(curr.right)) {
					map.put(curr.right, new NodeCopy(curr.right.val));
				}
				map.get(curr).right = map.get(curr.right);
				q.offer(curr.right);
			}
			if (curr.random != null) {
				if (!map.containsKey(curr.random)) {
					map.put(curr.random, new NodeCopy(curr.random.val));
				}
				map.get(curr).random = map.get(curr.random);
			}
		}
		return map.get(root);
	}
	
	
	private static class Node {
		int val;
		Node left;
		Node right;
		Node random;

		Node() {}
		Node(int val) {
			this.val = val;
		}
		Node(int val, Node left, Node right, Node random) {
			this.val = val;
			this.left = left;
			this.right = right;
			this.random = random;
		}
	}
	
	private static class NodeCopy {
		int val;
		NodeCopy left;
		NodeCopy right;
		NodeCopy random;

		NodeCopy() {}
		NodeCopy(int val) {
			this.val = val;
		}
		NodeCopy(int val, NodeCopy left, NodeCopy right, NodeCopy random) {
			this.val = val;
			this.left = left;
			this.right = right;
			this.random = random;
		}
	}

}
