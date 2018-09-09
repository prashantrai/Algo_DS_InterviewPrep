package test.practice.ebay;


public class ValidateBSTDemo {
	
	private static int index;

	public static void main(String[] args) {

		TreeNode tree =  new TreeNode(12);
		tree.left = new TreeNode(23);
		tree.insertInOrder(7);
		tree.insertInOrder(8);
		tree.insertInOrder(6);
		tree.insertInOrder(4);
		//tree.left = new TreeNode(23);
		tree.insertInOrder(16);
		tree.insertInOrder(13);
		tree.insertInOrder(15);
		tree.insertInOrder(17);
		tree.insertInOrder(18);
		
//		System.out.println(">>> "+validateBST(tree));
//		System.out.println(">>> "+validateBST_2(tree));
		System.out.println(">>> "+validateBST_3(tree, null, null));
		System.out.println(">>> "+validateBST_4(tree, null, null));
		
	}
	
	//--With in-order traversal
	public static boolean validateBST(TreeNode root) {
		
		if(root == null) return false;
		
		int arr[] = new int[root.size];
		
		inOrder(root, arr);
		
		for(int i=1; i<arr.length; i++) {
			if(arr[i-1] > arr[i]) return false;
		} 
		return true;
	}
	
	public static void inOrder(TreeNode node , int[] arr) {
		
		if(node != null) {
			inOrder(node.left, arr);
			arr[index++] = node.data;
			inOrder(node.right, arr);
		}
	}
	
	
	//--compare on the go
	public static Integer lastPrinted;
	public static boolean validateBST_2(TreeNode node) {
		
		if(node == null) return true;
		boolean left = validateBST_2(node.left);
		
		if(!left) return false;
		
		if(lastPrinted != null && lastPrinted >= node.data) {
			return false;
		}
		lastPrinted = node.data;
		boolean right = validateBST_2(node.right);
		
		if(!right) return false;
		
		
		return true;
	}
	
	
	public static boolean validateBST_3(TreeNode node, Integer min, Integer max) {
		
		if(node == null) return true;
		
		if(min != null && node.data < min || max != null && node.data > max) {
			return false;
		}
		
		if(!validateBST_3(node.left, min, node.data) || !validateBST_3(node.right, node.data, max)) {
			return false;
		}
		
		return true;
	}
	
	
	public static boolean validateBST_4(TreeNode node, Integer min, Integer max) {
		
		if(node == null) return true;
		
		if(min != null && node.data < min) { //--for right child validation
			return false;
		}
		
		if(max != null && node.data > max) { //--for left child validation
			return false;
		}
		
		if(!validateBST_4(node.left, min, node.data) || !validateBST_4(node.right, node.data, max)) {
			return false;
		}
		return true;
		
	}
	
}
