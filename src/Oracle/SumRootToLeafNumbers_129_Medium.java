package Oracle;

public class SumRootToLeafNumbers_129_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	/* Interview Script:: 
	    “Each root-to-leaf path forms a number.”
	    “While doing DFS, I carry the number formed so far.”
	    “At each node, I update it as current * 10 + node.val.”
	    “When I reach a leaf, I return that full number.”
	    “The answer is the sum of values returned by left and right subtrees.”
	    “This visits each node once, so time is O(n) and recursion stack is O(h).”
    */

    /* 
    Time: O(n), Each node is visited once.
    Space: O(h), Recursive call stack, where h is tree height.
    */
    public int sumNumbers(TreeNode root) {
        return dfs(root, 0);
    }

    // DFS builds the number formed from root to current node
    private int dfs(TreeNode node, int curNum) {
        if (node == null) {
            return 0;
        }

        curNum = curNum * 10 + node.val;

        // If this is a leaf, this path contributes one complete number
        if(node.left == null && node.right == null) {
            return curNum;
        }

        return dfs(node.left, curNum) + dfs(node.right, curNum);
    }
	
	
	/*
	 * Recursive pre-order 
	 * 
	 * Time: O(n), since we have to visit each node 
	 * Space: O(h), to keep the recursion tree stack, 
	 * where h is height of the tree.
	 */
	int rootToLeaf = 0;

	public int sumNumbers2(TreeNode root) {
		preOrder(root, 0);
		return rootToLeaf;
	}

	private void preOrder(TreeNode r, int currNum) {
		if (r == null)
			return;

		currNum = currNum * 10 + r.val;

		// if it's a leaf, update rootToLeaf sum
		if (r.left == null & r.right == null) {
			rootToLeaf += currNum;
		}

		preOrder(r.left, currNum);
		preOrder(r.right, currNum);
	}

	
	
	// another approach
	public int sumNumbers3(TreeNode root) {
		return sum(root, 0);
	}

	private int sum(TreeNode n, int s){
		if (n == null) return 0;
		if (n.right == null && n.left == null) 
			return s*10 + n.val;
		
		return sum(n.left, s*10 + n.val) + sum(n.right, s*10 + n.val);
	}
	
	
	private static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode() {}

		TreeNode(int val) {
			this.val = val;
		}

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
