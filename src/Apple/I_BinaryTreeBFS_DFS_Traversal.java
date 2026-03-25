package Apple;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class I_BinaryTreeBFS_DFS_Traversal {

	public static void main(String[] args) {
        // Construct the tree from Example 1: [1,2,3,4,5,6,7]
        /*
              1
             / \
            2   3
           / \ / \
          4  5 6  7
        */
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);

        BfsDfsTraversal solution = new BfsDfsTraversal();

        // Test BFS
        System.out.println("BFS: " + solution.bfs(root));

        // Test DFS Recursive
        System.out.println("DFS Recursive: " + solution.dfsRecursive(root));

        // Test DFS Iterative
        System.out.println("DFS Iterative: " + solution.dfsIterative(root));
        
        // Test Edge Case: Empty Tree
        System.out.println("\n--- Edge Case: Empty Tree ---");
        TreeNode emptyRoot = null;
        System.out.println("BFS (Empty): " + solution.bfs(emptyRoot));
        System.out.println("DFS Recursive (Empty): " + solution.dfsRecursive(emptyRoot));
        System.out.println("DFS Iterative (Empty): " + solution.dfsIterative(emptyRoot));
    }
	

	/*
	Time & Space Complexity:: 
	
	Time Complexity: O(N) for all three methods.
	We visit every node in the tree exactly once to add it to the list.
	
	Space Complexity: O(N) for all three methods.
	
	BFS: In the worst case (a complete binary tree), 
		the queue holds roughly N/2 nodes (the last level).
	DFS Recursive: In the worst case (a skewed tree), 
		the recursion stack depth is N.
	DFS Iterative: In the worst case (a skewed tree), 
		the stack holds N nodes.
	 * */
	
	// Definition for a binary tree node.
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

	private static class BfsDfsTraversal {

	    // 1. Breadth-First Search (BFS) - Level Order
	    public List<Integer> bfs(TreeNode root) {
	        List<Integer> result = new ArrayList<>();
	        if (root == null) return result;

	        Queue<TreeNode> queue = new LinkedList<>();
	        queue.offer(root);

	        while (!queue.isEmpty()) {
	            TreeNode current = queue.poll();
	            result.add(current.val);

	            if (current.left != null) queue.offer(current.left);
	            if (current.right != null) queue.offer(current.right);
	        }
	        return result;
	    }

	    // 2. Depth-First Search (DFS) - Recursive Preorder
	    public List<Integer> dfsRecursive(TreeNode root) {
	        List<Integer> result = new ArrayList<>();
	        preorderHelper(root, result);
	        return result;
	    }

	    private void preorderHelper(TreeNode node, List<Integer> result) {
	        if (node == null) return;

	        result.add(node.val);           // Root
	        preorderHelper(node.left, result);  // Left
	        preorderHelper(node.right, result); // Right
	    }

	    // 3. Depth-First Search (DFS) - Iterative Preorder
	    public List<Integer> dfsIterative(TreeNode root) {
	        List<Integer> result = new ArrayList<>();
	        if (root == null) return result;

	        Stack<TreeNode> stack = new Stack<>();
	        stack.push(root);

	        while (!stack.isEmpty()) {
	            TreeNode current = stack.pop();
	            result.add(current.val);

	            // Push Right first so Left is processed first (LIFO)
	            if (current.right != null) stack.push(current.right);
	            if (current.left != null) stack.push(current.left);
	        }
	        return result;
	    }
	}

}
