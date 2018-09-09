package ctci.ch4.trees.and.graphs;

public class CheckBalancedDemo {

	public static void main(String[] args) {

		System.out.println((-1 == Integer.MIN_VALUE));
		
		TreeNode tree =  new TreeNode(12);
		tree.insertInOrder(7);
		tree.insertInOrder(8);
		tree.insertInOrder(6);
		tree.insertInOrder(4);
//		tree.insertInOrder(16);
//		tree.insertInOrder(13);
//		tree.insertInOrder(15);
//		tree.insertInOrder(17);
//		tree.insertInOrder(18);
		
		System.out.println(">>> "+isBalanced(tree));
		
	}

	
	private static int checkHeight(TreeNode root) {
		
		if(root == null) return -1;
		
		//check height of left sub-tree
		int leftHight = checkHeight(root.left);
		
		if(leftHight == Integer.MIN_VALUE) {
			return Integer.MIN_VALUE;
		}
		
		System.out.println(">>leftHight = "+leftHight);
		
		//check height of right sub-tree
		int rightHeight = checkHeight(root.right);
		if(rightHeight == Integer.MIN_VALUE) {
			return Integer.MIN_VALUE;
		}
		
		System.out.println(">>rightHeight = "+rightHeight);
		
		int heightDiff = Math.abs(leftHight - rightHeight);
		
		System.out.println(">>heightDiff = "+heightDiff);
		
		if(heightDiff > 1) {
			return Integer.MIN_VALUE;
		
		} else {
			return Math.max(leftHight, rightHeight)+1;
		}
		
		
	}
	
	public static boolean isBalanced(TreeNode root) {
		
		return checkHeight(root) != Integer.MIN_VALUE;
	}
}
