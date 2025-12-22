package Oracle;

import java.util.List;
import java.util.Stack;


public class KthSmallestElement_In_a_BST_230_Medium {

	public static void main(String[] args) {
		KthSmallestElement_In_a_BST_230_Medium sol = new KthSmallestElement_In_a_BST_230_Medium();

        // Test Case 1: [3,1,4,null,2] → k=1 → expected: 1
        //       3
        //      / \
        //     1   4
        //      \
        //       2
        TreeNode root1 = new TreeNode(
            3,
            new TreeNode(1, null, new TreeNode(2)), // left subtree
            new TreeNode(4)                         // right subtree
        );
        System.out.println("Test 1: " + sol.kthSmallest(root1, 1)); // 1

        // Test Case 2: [5,3,6,2,4,null,null,1] → k=3 → expected: 3
        //         5
        //        / \
        //       3   6
        //      / \
        //     2   4
        //    /
        //   1
        TreeNode root2 = new TreeNode(
            5,
            new TreeNode(
                3,
                new TreeNode(2, new TreeNode(1), null),
                new TreeNode(4)
            ),
            new TreeNode(6)
        );
        System.out.println("Test 2: " + sol.kthSmallest(root2, 3)); // 3

        // Test Case 3: Single node
        TreeNode root3 = new TreeNode(1);
        System.out.println("Test 3: " + sol.kthSmallest(root3, 1)); // 1

        // Test Case 4: [2,1,3] → k=2 → expected: 2
        TreeNode root4 = new TreeNode(2, new TreeNode(1), new TreeNode(3));
        System.out.println("Test 4: " + sol.kthSmallest(root4, 2)); // 2
    }
	
	/* Interview Script for Time and Space complexity:: 
	 
	Solution performs an in-order traversal of the BST, which naturally visits nodes in ascending order. To optimize, I stop the traversal as soon as I find the k-th smallest element by using a boolean return flag that propagates upward through the recursion stack.

	In terms of time complexity:
	In the worst case, I need to descend from the root to the leftmost node — that’s O(H), 
	where H is the height of the tree. Then, I visit k nodes in sorted order until I reach 
	the k-th one. So the total time is O(H + k).
	
	For a balanced BST, H is O(log N), so it becomes O(log N + k). In a skewed tree 
	(like a linked list), H = N, so worst-case time is O(N) — but only if k is large 
	(e.g., k = N). For small k, it’s much faster than full traversal.
	
	For space complexity:
	The space is dominated by the recursion call stack. At any point, the maximum depth 
	of recursion is the height of the tree, so O(H) space. Again, that’s O(log N) for 
	balanced trees and O(N) for skewed ones.
	 * */
	
	/* 
    1. Perform inorder on the tree and store each node value in a List
    2. Return (k-1)th value from the list.\
    */
    // Time and space: O(N)
	
	static int count = 0;
    static int result = Integer.MIN_VALUE;
    
    public int kthSmallest(TreeNode root, int k) {
        inorder(root, k);
        return result;
        /* 
        // Working solution without List, i.e., count as we go and 
        // return as soon as we reach the k-1th element.
        List<Integer> nums = new ArrayList<>();
        inorder2(root, k);
        return result;
        */
    }
    private static boolean inorder(TreeNode node, int k) {
        if (node == null) return false;
        if (inorder(node.left, k)) return true;  // ← If left found it, STOP
        count++;
        if (count == k) {
            result = node.val;
            return true; // ← Signal "found!"
        }
        return inorder(node.right, k); // ← Only go right if not found
    }
    
    // New for Kth Largest
    private static boolean inorderReverse(TreeNode node, int k) {
        if (node == null) return false;
        // New for Kth Largest → traverse RIGHT first
        if (inorderReverse(node.right, k)) return true;

        count++;
        if (count == k) {
            result = node.val;
            return true; // stop traversal
        }
        // New for Kth Largest → traverse LEFT last
        return inorderReverse(node.left, k);
    }

    // approach 2 - using ArrayList
    // Not very efficient as it iterates the entire Tree
    private void inorder2(TreeNode root, List<Integer> nums) {
        if(root != null) {
            inorder2(root.left, nums);
            nums.add(root.val);
            inorder2(root.right, nums);
        }
    }
    
    
    /*
    Time Complexity: O(H + k)
		H: Height of the tree — we push all leftmost nodes onto the stack initially 
		(while (curr != null) loop).
		k: We pop exactly k nodes from the stack before returning.
		Once k == 0, we return immediately — no extra work.
		
		Balanced BST: O(log N + k)
		Skewed tree: O(N + k) → but since k ≤ N, worst-case is O(N)
	
	Space Complexity: O(H)
		The stack stores at most H nodes (the current path from root to leftmost node).
		No recursion stack — only the explicit Stack<TreeNode>.
		
		Balanced BST: O(log N)
		Skewed tree: O(N)
		✅ Same as recursive version, but avoids function call overhead.
     * */

    // Iterative approach
    public int kthSmallest_Iter(TreeNode root, int k) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        
        while (current != null || !stack.isEmpty()) {
            // Go to the leftmost node
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            
            // Process the node
            current = stack.pop();
            if (--k == 0) 
            	return current.val; // Stop immediately!
            
            // Move to the right subtree
            current = current.right;
        }
        return -1; // Never reached if k is valid
    } 
    
    // New method name for Kth Largest
    public int kthLargest_Iter(TreeNode root, int k) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        while (current != null || !stack.isEmpty()) {
            // New for Kth Largest → move to RIGHTMOST
            while (current != null) {
                stack.push(current);
                current = current.right; // New for Kth Largest
            }
            current = stack.pop();
            if (--k == 0)
                return current.val; // Found kth largest
            
            // New for Kth Largest → go LEFT
            current = current.left;
        }
        return -1; // if k is invalid
    }
    
    // TreeNode
    private static class TreeNode {
		int val;
		TreeNode left, right;
		TreeNode() {}
		TreeNode(int val) { this.val = val; }
		TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
		@Override
		public String toString() {
			// return "[" + val + ", " + left + ", " + right + "]";
			return "" + val;
		}
	}

}
