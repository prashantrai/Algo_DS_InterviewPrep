package test.practice.ebay;

public class MinimalTreeDemo {

	public static void main(String[] args) {
		
		int[] arr = {1,2,3,4,5,6,7,8,9,10};
		System.out.println(createMinimalBST(arr, 0, arr.length-1));
		System.out.println(createMinimalBST2(arr, 0, arr.length-1));
		
		TreeNode node = createMinimalBST3(arr, 0, arr.length-1);
		System.out.println(node);
		
	}

	private static TreeNode createMinimalBST(int[] arr, int start, int end) {

		if(end < start) {
			return null;
		}
		
		int mid = (start+end)/2;
		TreeNode node = new TreeNode(arr[mid]);
		
		node.left = createMinimalBST(arr, start, mid-1);
		node.right = createMinimalBST(arr, mid+1, end);
		return node;
	}
	
	
	private static TreeNode createMinimalBST2(int[] arr, int start, int end) {
		
		if(arr == null) {
			return null;
		}
		
		if(start > end) {
			return null;
		}
		
		int mid = (start+end)/2;

		TreeNode root = new TreeNode(arr[mid]);
		root.left = createMinimalBST2(arr, start, mid-1);
		root.right = createMinimalBST2(arr, mid+1, end);
		
		return root;
		
	}
	

	private static TreeNode createMinimalBST3 (int[] arr, int start, int end) {
		if(arr == null || arr.length == 0) return null;
		
		if(start > end) return null;
		
		int mid = (start + end) / 2;
		
		TreeNode root = new TreeNode(arr[mid]);
		
		root.left = createMinimalBST3(arr, start, mid-1);
		root.right = createMinimalBST3(arr, mid+1, end);
		
		return root;
		
	}

}
