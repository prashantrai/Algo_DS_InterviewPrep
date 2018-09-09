package test.practice.amazon;
public class CheckBalancedDemo {

	public static void main(String[] args) {

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

	public static boolean isBalanced(TreeNode root) {
		
		return checkHeight(root) != Integer.MIN_VALUE;
	}
	
	
	private static int checkHeight(TreeNode root) {
		
		if(root == null) { 
			return -1;
		}
		
		int leftHeight = checkHeight(root.left);
		
		if(leftHeight == Integer.MIN_VALUE) return Integer.MIN_VALUE;
		
		int rightHeight = checkHeight(root.right);
		if(rightHeight == Integer.MIN_VALUE) return Integer.MIN_VALUE;
		
		int diff = Math.abs(leftHeight - rightHeight);
		
		if(diff > 1) {
			return Integer.MIN_VALUE;
		}
		else {
			int height = Math.max(rightHeight, leftHeight);
			height = height + 1;
			return height;
		}
	}
	
	
}