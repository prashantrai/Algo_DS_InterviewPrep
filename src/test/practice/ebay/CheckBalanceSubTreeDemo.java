package test.practice.ebay;


public class CheckBalanceSubTreeDemo {
	public static void main(String[] args) {


		System.out.println((-1 == Integer.MIN_VALUE));
		
		TreeNode tree =  new TreeNode(12);
		tree.insertInOrder(7);
		tree.insertInOrder(8);
		tree.insertInOrder(6);
		tree.insertInOrder(4);
		tree.insertInOrder(16);
		tree.insertInOrder(13);
		tree.insertInOrder(15);
		tree.insertInOrder(17);
		tree.insertInOrder(18);
		
		System.out.println(">>> "+isBalanced(tree));
		System.out.println("####>>> "+isBalanced_2(tree));
		
	
	}

	public static boolean isBalanced(TreeNode root) {
		
		return getHeight(root) == Integer.MIN_VALUE ? false : true;
	}
	
	
	public static Integer getHeight(TreeNode root) {
		
		if(root == null) return -1;
		
		int leftHeight = getHeight(root.left);
		System.out.println(">>leftHeight:: "+leftHeight);
		if(leftHeight == Integer.MIN_VALUE) return Integer.MIN_VALUE;
		
		int rightHeight = getHeight(root.right);
		System.out.println(">>rightHeight:: "+rightHeight);
		if(rightHeight == Integer.MIN_VALUE) return Integer.MIN_VALUE;
		
		System.out.println("-->>leftHeight:: "+leftHeight);
		System.out.println("-->>rightHeight:: "+rightHeight);
		
		int heightDiff = Math.abs(leftHeight -  rightHeight);
		
		if(heightDiff > 1) return Integer.MIN_VALUE;
		
		return Math.max(leftHeight, rightHeight) + 1;
		
	}
	
	
	public static boolean isBalanced_2(TreeNode root) {
		return getHeight_2(root) != Integer.MIN_VALUE;
	}
	
	public static int getHeight_2(TreeNode root) {
		
		if(root == null) return Integer.MIN_VALUE;
		
		int leftHeight = getHeight(root.left);
		if(leftHeight == Integer.MIN_VALUE) return Integer.MIN_VALUE;
		
		int rightHeight = getHeight(root.right);
		if(rightHeight == Integer.MIN_VALUE) return Integer.MIN_VALUE;
		
		int diff = Math.abs(rightHeight - leftHeight);
		
		if(diff > 1) return Integer.MIN_VALUE;

		int result = Math.max(rightHeight, leftHeight)+1; 
		
		System.out.println(result);
		
		return result;
	}
	
}
