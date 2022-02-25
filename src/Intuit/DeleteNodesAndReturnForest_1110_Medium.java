package Intuit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class DeleteNodesAndReturnForest_1110_Medium {

	public static void main(String[] args) {
		TreeNode node_4 = new TreeNode(4);
		TreeNode node_5 = new TreeNode(5);
		TreeNode node_6 = new TreeNode(6);
		TreeNode node_7 = new TreeNode(7);
		
		TreeNode node_2 = new TreeNode(2, node_4, node_5);
		TreeNode node_3 = new TreeNode(3, node_6, node_7);
		
		TreeNode root = new TreeNode(1, node_2, node_3);
		
		int[] to_delete = {3, 5};
		
		List<TreeNode> res = delNodes(root, to_delete);
		System.out.println("Expected: [[1,2,null,4],[6],[7]], Actual: " + res);
		
	}
	
	/* Reference:  
	 * https://leetcode.com/problems/delete-nodes-and-return-forest/discuss/328853/JavaC%2B%2BPython-Recursion-Solution
	 * https://leetcode.com/problems/delete-nodes-and-return-forest/discuss/328860/Simple-Java-Sol
	 
	 Complexity: 
		Time O(N)
		Space O(H + N), where H is the height of tree.
	 */

	// https://leetcode.com/problems/delete-nodes-and-return-forest/discuss/328860/Simple-Java-Sol
	
	public static List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
		List<TreeNode> res = new ArrayList<>();
		//copy all to_delete array element to a Set 
		Set<Integer> toDeleteSet = Arrays.stream(to_delete).boxed().collect(Collectors.toSet()); 
		
		if(!toDeleteSet.contains(root.val)) {
			res.add(root);
		}
		
		helper(root, res, toDeleteSet);
		return res;
    }
	
	private static TreeNode helper(TreeNode node, List<TreeNode> res, Set<Integer> set) {
		if(node == null) return null;
		
		node.left = helper(node.left, res, set);
		node.right = helper(node.right, res, set);
		
		if(set.contains(node.val)) {
			if(node.left != null) res.add(node.left);
			if(node.right != null) res.add(node.right);
			return null;
		}
		
		return node;
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
		@Override
		public String toString() {
			return "[" + val + ", " + left + ", " + right + "]";
		}
	}
}
