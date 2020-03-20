package leetcode;

public class InvertBinaryTree_226_Easy {

	
	/**
	 * Definition for a binary tree node.
	 * public class TreeNode {
	 *     int val;
	 *     TreeNode left;
	 *     TreeNode right;
	 *     TreeNode(int x) { val = x; }
	 * }
	 */
	public static void main(String[] args) {

		
		//--refer test.practice.misc.MirrorImageOfBinaryTree
	}
	
	//--https://www.educative.io/edpresso/how-to-invert-a-binary-tree
	public TreeNode invertTree(TreeNode root) {
        
        if(root == null) return root;
        
        if(root.left == null && root.right == null) return root;
        invertTree(root.left);
        invertTree(root.right);
        
        swap(root);
        
        return root;
    }
    
    public static void swap(TreeNode node) {
		TreeNode temp = node.left;
		node.left = node.right;
		node.right = temp;
	}
    
    
    public TreeNode invertTree2(TreeNode root) {
        
        if(root == null) 
        	return root;
        
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        
        root.left = right;
        root.right = left;
        
        return root;
    }

}
