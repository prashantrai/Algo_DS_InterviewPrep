package LinkedIn;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTreeUpsideDown_156_Medium {

	public static void main(String[] args) {
        // Test Case 1
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        root1.left.left = new TreeNode(4);
        root1.left.right = new TreeNode(5);

        System.out.println("Original Tree (Test Case 1):");
        printTree(root1);

        // Iterative Approach
        TreeNode upsideDownIterative = upsideDownBinaryTree(root1);
        System.out.println("\nUpside Down Tree (Iterative):");
        printTree(upsideDownIterative);

        // Reset the tree for recursive approach
        root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        root1.left.left = new TreeNode(4);
        root1.left.right = new TreeNode(5);

        // Recursive Approach
        TreeNode upsideDownRecursive = upsideDownBinaryTree_recursive(root1);
        System.out.println("\nUpside Down Tree (Recursive):");
        printTree(upsideDownRecursive);

        // Test Case 2: Single Node
        TreeNode root2 = new TreeNode(1);
        System.out.println("\nOriginal Tree (Test Case 2 - Single Node):");
        printTree(root2);

        upsideDownIterative = upsideDownBinaryTree(root2);
        System.out.println("\nUpside Down Tree (Iterative):");
        printTree(upsideDownIterative);

        upsideDownRecursive = upsideDownBinaryTree_recursive(root2);
        System.out.println("\nUpside Down Tree (Recursive):");
        printTree(upsideDownRecursive);

        // Test Case 3: Empty Tree
        TreeNode root3 = null;
        System.out.println("\nOriginal Tree (Test Case 3 - Empty Tree):");
        printTree(root3);

        upsideDownIterative = upsideDownBinaryTree(root3);
        System.out.println("\nUpside Down Tree (Iterative):");
        printTree(upsideDownIterative);

        upsideDownRecursive = upsideDownBinaryTree_recursive(root3);
        System.out.println("\nUpside Down Tree (Recursive):");
        printTree(upsideDownRecursive);
    }

    // Helper method to print the tree level by level
    public static void printTree(TreeNode root) {
        if (root == null) {
            System.out.println("[]");
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                if (node == null) {
                    sb.append("null, ");
                    continue;
                }
                sb.append(node.val).append(", ");
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }

        // Remove the trailing ", " and close the bracket
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("]");
        System.out.println(sb.toString());
    }
	
	/* 
    Time: O(N), where N is the number of nodes in the tree. 
                Each node is visited once.
    Space: O(1) for iterative, O(N) for recursive (due to recursion stack).

    Iterative Approach:
    - Traverse down the left subtree, keeping track of the current node, its parent, and its right sibling.
    - At each step, restructure the tree by making the current node's left child point to its right sibling and the right child point to its parent.

    - Continue until all nodes are processed.
    */

    // Iterative approach
    private static TreeNode upsideDownBinaryTree(TreeNode root) {
        TreeNode curr = root;
        TreeNode parent = null;
        TreeNode rightSibling = null;

        while (curr != null) {
            // Save the left child before modifying it
           TreeNode left = curr.left;

            // Reassign the current node's left and right children
           curr.left = rightSibling;
           rightSibling = curr.right;

            // Save the right child for the next iteration
            curr.right = parent;
           
            // Move to the left child for the next iteration
           parent = curr;
           curr = left;
        }

        return parent;
    }

    /*
    Time: O(N), where N is the number of nodes in the tree. 
                Each node is visited once.
    Space: O(N) for recursive (due to recursion stack).
    */
    // Recursive approach
    private static TreeNode upsideDownBinaryTree_recursive(TreeNode root) {
        if (root == null || root.left == null) {
            return root;
        }

        // Recursively process the left subtree until we 
        // reach the leftmost node, which will become the new root.
        TreeNode newRoot = upsideDownBinaryTree(root.left);

        // Restructuring the tree
        root.left.left = root.right; // Original right becomes left child
        root.left.right = root; // Original parent becomes right child
        
        // avoid cycle
        root.left = null;
        root.right = null;

        return newRoot;

    }
    
    
    static class TreeNode {
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
