package test.practice.amazon;


public class MinimalTreeDemo {

	public static void main(String[] args) {
		
		int[] arr = {1,2,3,4,5,6,7,8,9,10};
		System.out.println(createMinimalBST(arr));

		
		TreeNode tree =  new TreeNode(12);
		tree.insertInOrder(7);
		tree.insertInOrder(8);
		tree.insertInOrder(6);
		tree.insertInOrder(4);
		tree.insertInOrder(16);
		tree.insertInOrder(13);
		tree.insertInOrder(15);
		
		//System.out.println(tree.find(13));
	}
	
	
	public static TreeNode createMinimalBST(int[] arr) {
		
		return createMinimalBST(arr, 0, arr.length-1);
		
	}
	
	private static TreeNode createMinimalBST(int[] arr, int low, int high) {
		
		if(low > high) return null;
		
		int mid = (low+high)/2;
		
		TreeNode node = new TreeNode(arr[mid]);
		
		node.left = createMinimalBST(arr, low, mid-1);
		node.right = createMinimalBST(arr, mid+1, high);
		
		return node;
	}
	
	

}


