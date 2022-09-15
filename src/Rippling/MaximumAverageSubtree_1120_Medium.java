package Rippling;


public class MaximumAverageSubtree_1120_Medium {

	public static void main(String[] args) {

	}
	
	/*
	 * Time: O(N), where N is the number of nodes in the tree. This is because we visit each 
	 * 				and every node only once, as we do in post-order traversal.
	 * 
	 * Space: O(N), because we will create N states for each of the nodes in the tree. 
	 * 			Also, in cases where we have a skewed tree, we will implicitly maintain 
	 * 			a recursion stack of size N, hence space complexity from this will also be O(N).
	 * */
	
	public double maximumAverageSubtree(TreeNode root) {
        dfs(root);
        return res;
    }
    
    double res = Integer.MIN_VALUE;
    private int dfs(TreeNode root){
        if(root == null)
            return 0;
        int left = dfs(root.left);
        int right = dfs(root.right);
        int number = left + right + 1;
        if(left != 0)
            root.val += root.left.val;
        if(right != 0)
            root.val += root.right.val;
        res = Math.max(res, (double)(root.val) / number);
        return number;
    }
    
    
    private int[] dfs2(TreeNode root) {
        if (root == null) {
            return new int[] {0, 0};
        }
        int[] l = dfs(root.left);
        int[] r = dfs(root.right);
        int sum = l[0] + r[0] + root.val;
        int count = l[1] + r[1] + 1;
        res = Math.max(1.0 * sum / count, res);
        return new int[] {sum, count};
    }
	

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
}
