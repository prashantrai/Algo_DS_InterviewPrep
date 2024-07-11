package Facebook;

import Facebook.SumRootToLeafNumbers_129_Medium.TreeNode;

public class CountNodesEqualToAverageOfSubtree_2265_Medium {

	public static void main(String[] args) {

	}
	
	// Time: O(N), We need to iterate over each 
    //          node in the binary tree only once
    // Space: O(N), Recursion requires some stack space, and the maximum 
    // number of active stack calls would be equal to N (one for each node).
    
    int res = 0;
    public int averageOfSubtree(TreeNode root) {
        dfs(root);
        return res;
    }
    
    private int[] dfs(TreeNode node) {
        if(node == null) {
            return new int[] {0,0};
        }
        
        int[] left = dfs(node.left);
        int[] right = dfs(node.right);
        
        int currSum = left[0] + right[0] + node.val;
        int currCount = left[1] + right[1] + 1;
        
        if(currSum / currCount == node.val) {
            res++;
        }
            
        return new int[] {currSum, currCount};
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
