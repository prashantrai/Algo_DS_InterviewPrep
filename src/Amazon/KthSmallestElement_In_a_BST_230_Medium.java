package Amazon;

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
	 
	Solution performs an in-order traversal of the BST, 
	which naturally visits nodes in ascending order. 
	To optimize, I stop the traversal as soon as I find the k-th smallest 
	element by using a boolean return flag that propagates upward through 
	the recursion stack.

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
    // Time and space: O(N)
	
    public int kthSmallest(TreeNode root, int k) {
        inorder_DFS(root, k); // working
        // return result;

        return inorder_Iter(root, k); // working
    }
    
    int count = 0;
    int result = -1;
    // Since inorder traversal of a BST is sorted, the kth visited node is the answer. 
    // Using an iterative stack lets us stop early and use only O(h) space.
    private void inorder_DFS(TreeNode node, int k) {
        if(node == null) return;

        inorder_DFS(node.left, k);

        count++;
        if(count == k) {
            result = node.val;
            return;
        }

        inorder_DFS(node.right, k);
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
    private int inorder_Iter(TreeNode root, int k) {
        Stack<TreeNode> stk = new Stack<>();
        TreeNode cur = root;
        // Standard iterative inorder traversal
        while (cur != null || !stk.isEmpty()) {
            
            // Go as left as possible
            while (cur != null) {
                stk.push(cur);
                cur = cur.left;    
            }

            // Now have added the left most the stack
            // start processing them now and update k
            // Visit current smallest unvisited node
            cur = stk.pop();

            // Decrease k; if this is the kth node, return it
            if(--k == 0) {
                return cur.val;
            }
            // Now process right subtree
            cur = cur.right;
        }
        return -1;
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

    /** Follow-up:  If the BST is modified often (i.e., we can do insert and delete operations) 
     * and you need to find the kth smallest frequently, how would you optimize? */
    
    /* Interview Script::
		For frequent queries, I’d store subtree size in every node. Then left.size tells 
		me how many smaller elements exist before the current node. I can navigate directly 
		to the kth element in O(h) instead of doing inorder traversal every time.
		
		The key is that insert and delete must keep this size field updated. During 
		insertion or deletion, only nodes on the search path are affected, so we recompute 
		their subtree sizes while returning back up the recursion. With a balanced BST, 
		insert, delete, and kth-smallest all become O(log n).
     * */
    
    
    /* 	Time  = O(h)
		Space = O(1)
		
		And if the BST is balanced:
		kthSmallest → O(log n)
		insert      → O(log n)
		delete      → O(log n)
     * */
    
    // Follow-up: new - kthSmallest with O(H) traversal (single path)
    
    /* The important logic is:
     	leftSize = number of nodes smaller than current
		k <= leftSize
		    -> go left
		
		k == leftSize + 1
		    -> current is answer
		
		k > leftSize + 1
		    -> go right
		       skip left subtree + current node
     * */
    
    public int kthSmallest(AugTreeNode root, int k) {
    	AugTreeNode curr = root;

        while (curr != null) {
            int leftSize = curr.left == null ? 0 : curr.left.size;

            // Current node is the kth smallest.
            if (k == leftSize + 1) {
                return curr.val;
            }

            // kth smallest is completely in the left subtree.
            if (k <= leftSize) {
                curr = curr.left;
            } else {
                // Skip left subtree + current node.
                k -= leftSize + 1;
                curr = curr.right;
            }
        }
        return -1;
    }
    
    // Follow-up: new - Augmented Node (for system design)
    static class AugTreeNode {
    	int val;
    	int size;       // Number of nodes in this subtree
    	AugTreeNode left, right;
		AugTreeNode(int val) {
		    this.val = val;
		    this.size = 1;
		}
		private int size(AugTreeNode node) {
		    return node == null ? 0 : node.size;
		}
		
		// may not needed during the interview
		// normal BST insertion, only change is `root.size = 1 + size(root.left) + size(root.right);`
		public AugTreeNode insert(AugTreeNode root, int val) {
		    if (root == null) {
		        return new AugTreeNode(val);
		    }

		    if (val < root.val) {
		        root.left = insert(root.left, val);
		    } else if (val > root.val) {
		        root.right = insert(root.right, val);
		    }

		    // Recalculate subtree size.
		    root.size = 1 + size(root.left) + size(root.right);

		    return root;
		}
		public AugTreeNode delete(AugTreeNode root, int val) {
		    if (root == null) {
		        return null;
		    }
		    if (val < root.val) {
		        root.left = delete(root.left, val);
		    } else if (val > root.val) {
		        root.right = delete(root.right, val);
		    } else {
		        // Case 1 and 2:
		        // zero or one child
		        if (root.left == null) {
		            return root.right;
		        }
		        if (root.right == null) {
		            return root.left;
		        }

		        // Case 3:
		        // two children
		        // Replace with inorder successor.
		        AugTreeNode successor = findMin(root.right);
		        root.val = successor.val;

		        // Delete successor from right subtree.
		        root.right = delete(root.right, successor.val);
		    }

		    // Recalculate subtree size after deletion.
		    root.size = 1 + size(root.left) + size(root.right);

		    return root;
		}
		private AugTreeNode findMin(AugTreeNode node) {
		    while (node.left != null) {
		        node = node.left;
		    }
		    return node;
		}
    }
    
    
    
}
