package ctci.ch4.trees.and.graphs;

public class ValidateBSTDemo {

	public static int index;
	
	public static void main(String[] args) {

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
		
		System.out.println(">>> "+checkBST_3(tree));
		
	}

	//--Apporoach 1 : Traverse the tree in-order and store each node value in and array 
	//--then check if all the elemets in the array in sorted order

	public static boolean checkBST(TreeNode root) {
		int[] arr = new int[root.size()];
		
		copyBST(root, arr);
		
		for(int i=1; i<arr.length; i++) {
			if(arr[i-1] >= arr[i]) return false;
		}
		return true;
		
	}
	private static void copyBST(TreeNode root, int[] arr) {
		
		if(root == null) return;
		
		copyBST(root.left, arr);
		
		arr[index] = root.data;
		index++;
		
		copyBST(root.right, arr);
		
	}
	
	
	//--Approach 2: Traverse the tree in-order and compare as you go with last element whether the last element is smaller than the current element or not
	
	public static Integer lastElement = null;
	
	public static boolean checkBST_2(TreeNode root) {
		
		if(root == null) return true;
		
		boolean checkLeft = checkBST_2(root.left);

		if(!checkLeft) {
			return false;
		}
		
		if(lastElement != null && lastElement >= root.data) {
			return false;
		}
		lastElement = root.data;
		
		boolean checkRight = checkBST_2(root.right);
		if(!checkRight) { 
			return false;
		}
		
		return true;
		
	}
	
	
	
	//--Approach 3
	
	public static boolean checkBST_3(TreeNode n) {
		
		return checkBST_3(n, null, null);
	}
	
	public static boolean checkBST_3(TreeNode n, Integer min, Integer max) {
		
		if(n == null) return true;
		
		if(min != null && n.data < min || max != null && n.data > max) {
			return false;
		}
		
		if(!checkBST_3(n.left, min, n.data) || !checkBST_3(n.right, n.data, max)) {
			return false;
		}
		
		return true;
	}
	

}
