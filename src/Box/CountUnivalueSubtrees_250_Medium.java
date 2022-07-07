package Box;

public class CountUnivalueSubtrees_250_Medium {

	public static void main(String[] args) {
		TreeNode n5_5 = new TreeNode(5, null, null);
		TreeNode n4_5 = new TreeNode(5, null, null);
		TreeNode n3_5 = new TreeNode(5, null, null);

		TreeNode n2_5 = new TreeNode(5, null, n3_5);
		TreeNode n_1 = new TreeNode(1, n5_5, n4_5);
		
		TreeNode root = new TreeNode(5, n_1, n2_5);
		
		System.out.println("1. Expected: 4, Actual: " + countUnivalSubtrees(root));
		System.out.println("2. Expected: 4, Actual: " + countUnivalSubtrees2(root));
	}

	
	/* DFS with Parent value
	 * Time and Space: O(N)
	 * */
	static int count2;
	public static int countUnivalSubtrees2(TreeNode root) {
		helper2(root, 0);
		return count2;
    }
	
	
	private static boolean helper2(TreeNode node, int val) {
		if(node == null) {
			return true;
		}
		
		// check if node.left and node.right are univalue subtrees of value node.val
        // note that || short circuits but | does not - both sides of the or get evaluated 
		// with | so we explore all possible routes
		
		if(!helper2(node.left, node.val) | !helper2(node.right, node.val)) {
			return false;
		}
		
		// if it passed the last step then this a valid subtree - increment
        count2++;
        
        // at this point we know that this node is a univalue subtree of value node.val
        // pass a boolean indicating if this is a valid subtree for the parent node
        
        return node.val == val;
	}


	/* DFS
	 * Time and Space: O(N)
	 * */
	
	static int count;
	public static int countUnivalSubtrees(TreeNode root) {
        //int[] count = new int[1];
        //helper(root, count);
        //return count[0];
		helper(root);
		return count;
    }
	
	//private static void helper(TreeNode node, int[] count) {
	private static boolean helper(TreeNode node) {
		if(node == null) {
			return true;
		}
		
		boolean left = helper(node.left);
		boolean right = helper(node.right);
		
		if(left && right) {
			if(node.left != null && node.val != node.left.val) {
				return false;
			}  
			if(node.right != null && node.val != node.right.val) {
				return false;
			}  
			
			count++;
			return true;
		}
		return false;
	}

	// Tree Node
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
	}
}
