package LinkedIn;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LowestCommonAncestorOfABinaryTree_236_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/* How this problem is different from 
	 * 1650 (https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-iii/)?
    
     * Here TreeNode doesn't have parent pointer and that's why we starting from root
     * and moving left and right.
	 * 
	 * 
	 * 
	 * https://www.youtube.com/watch?v=uKhLoNaG9LI 
	 * 
	 * Taken from one of the comment posted in article : https://leetcode.com/articles/lowest-common-ancestor-of-a-binary-tree/
	 * 
	 * Time and Space: O(n)
	 * */
	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

		if (root == null)
			return null;
		if (root == p || root == q)
			return root;

		TreeNode left = lowestCommonAncestor(root.left, p, q);
		TreeNode right = lowestCommonAncestor(root.right, p, q);

		if (left != null && right != null)
			return root;

		return left != null ? left : right;
	}

	private static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}
	
	
	// Iterative
	
	/*
	Time Complexity : O(N), where N is the number of nodes in the binary tree. 
		In the worst case we might be visiting all the nodes of the binary tree.

	Space Complexity : O(N). In the worst case space utilized by the stack, the 
		parent pointer dictionary and the ancestor set, would be N each, since 
		the height of a skewed binary tree could be N.
	 */
	
	public TreeNode lowestCommonAncestor_Iter(TreeNode root, TreeNode p, TreeNode q) {

        // Stack for tree traversal
        Deque<TreeNode> stack = new ArrayDeque<>();

        // HashMap for parent pointers
        Map<TreeNode, TreeNode> parent = new HashMap<>();

        parent.put(root, null);
        stack.push(root);

        // Iterate until we find both the nodes p and q
        while (!parent.containsKey(p) || !parent.containsKey(q)) {

            TreeNode node = stack.pop();

            // While traversing the tree, keep saving the parent pointers.
            if (node.left != null) {
                parent.put(node.left, node);
                stack.push(node.left);
            }
            if (node.right != null) {
                parent.put(node.right, node);
                stack.push(node.right);
            }
        }

        // Ancestors set() for node p.
        Set<TreeNode> ancestors = new HashSet<>();

        // Process all ancestors for node p using parent pointers.
        while (p != null) {
            ancestors.add(p);
            p = parent.get(p);
        }

        // The first ancestor of q which appears in
        // p's ancestor set() is their lowest common ancestor.
        while (!ancestors.contains(q))
            q = parent.get(q);
        return q;
    }

}
