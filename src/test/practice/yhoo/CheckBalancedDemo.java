package test.practice.yhoo;
public class CheckBalancedDemo {

	public static void main(String[] args) {
		
/*		
				12
				/
			   7
			  / \
			 6   8
			/
		   4
*/
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
		
		System.out.println("1. >>> "+isBalanced(tree));
		System.out.println("2. >>> "+isBalanced2(tree));
		
	}

	
	public static boolean isBalanced2(TreeNode root) {
		if(root == null) return false;
		
		return helper(root) != Integer.MIN_VALUE;
	}
	
	public static int helper(TreeNode root) {
		
		if(root == null) return -1;
		
		//-- traverse left
		int left_height = helper(root.left);
		if(left_height == Integer.MIN_VALUE) return Integer.MIN_VALUE;
		//System.out.println(">>left_height = "+left_height);
		
		//-- traverse right
		int right_height = helper(root.right);
		if(right_height == Integer.MIN_VALUE) return Integer.MIN_VALUE;
		//System.out.println(">>right_height = "+right_height);
		
		int delta = Math.abs(right_height - left_height);
		System.out.println("*>>heightDiff = "+delta);
		
		if(delta > 1) return Integer.MIN_VALUE;
		
		return Math.max(left_height, right_height)+1; //--adding current/root node as 1 height into the sub-tree size
		
	}
	
	private static int checkHeight(TreeNode root) {
		
		if(root == null) return -1;
		
		//check height of left sub-tree
		int leftHight = checkHeight(root.left);
		
		if(leftHight == Integer.MIN_VALUE) {
			return Integer.MIN_VALUE;
		}
		
		//System.out.println(">>leftHight = "+leftHight);
		
		//check height of right sub-tree
		int rightHeight = checkHeight(root.right);
		if(rightHeight == Integer.MIN_VALUE) {
			return Integer.MIN_VALUE;
		}
		
		//System.out.println(">>rightHeight = "+rightHeight);
		
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