package test.practice.misc;

public class MirrorImageOfBinaryTree {

	public static void main(String[] args) {
		TreeNode tree =  new TreeNode(20);
		tree.insertInOrder(10);
		tree.insertInOrder(25);
		tree.insertInOrder(5);
		tree.insertInOrder(15);
		tree.insertInOrder(24);
		tree.insertInOrder(28);
		
		System.out.println("Before :: ");
		FlipABinaryTreeDemo.levelOrderTraversal(tree);
		
		System.out.println("\nAfter :: ");
		TreeNode mirrorRoot = mirrorImageOfBinaryTree(tree); 
		FlipABinaryTreeDemo.levelOrderTraversal(mirrorRoot);
		

	}
	
	/**
	 * See GeeksforGeeks for more details
	 * In mirror image : Left child will be right and right will left
	 * */

	//--Looks good
	public static TreeNode mirrorImageOfBinaryTree(TreeNode root) {

		if(root == null) return root;
		
		if(root.left == null && root.right == null) return root;
		
		mirrorImageOfBinaryTree(root.left);
		mirrorImageOfBinaryTree(root.right);
		swap(root);
		
		return root;
		
	}
	
	public static void swap(TreeNode node) {
		TreeNode temp = node.left;
		node.left = node.right;
		node.right = temp;
	}

}
