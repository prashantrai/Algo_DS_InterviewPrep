package ctci.ch4.trees.and.graphs;

public class MinimalTreeDemo {

	public static void main(String[] args) {

		int[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		System.out.println(createMinimalBST(arr));

		TreeNode_2 tree = new TreeNode_2(12);
		tree.insertInOrder(7);
		tree.insertInOrder(8);
		tree.insertInOrder(6);
		tree.insertInOrder(4);
		tree.insertInOrder(16);
		tree.insertInOrder(13);
		tree.insertInOrder(15);

		// System.out.println(tree.find(13));
	}

	/*
	 * Complexity Analysis
	 * 
	 * Time Complexity: The time complexity comes down to just O(N) now since we
	 * convert the linked list to an array initially and then we convert the array
	 * into a BST. Accessing the middle element now takes O(1) time and hence
	 * the time complexity comes down. 
	 * 
	 * Space Complexity: No extra space used so it's O(1)
	 */

	public static TreeNode_2 createMinimalBST(int[] arr) {
		return createMinimalBST(arr, 0, arr.length - 1);
	}

	public static TreeNode_2 createMinimalBST(int[] arr, int start, int end) {

		if (start > end)
			return null;

		int mid = (start + end) / 2;

		TreeNode_2 node = new TreeNode_2(arr[mid]);

		// --left subtree
		node.left = createMinimalBST(arr, start, mid - 1);
		// --right subtree
		node.right = createMinimalBST(arr, mid + 1, end);

		return node;

	}

}

class TreeNode_2 {

	TreeNode_2 left, right, parent;
	int data;
	int size = 0;

	public TreeNode_2(int data) {
		this.data = data;
		this.size = 1;
	}

	// --insert inorder
	public void insertInOrder(int d) {

		if (d <= data) {
			if (this.left == null) {
				setLeft(new TreeNode_2(d));
			} else {
				left.insertInOrder(d);
			}
		} else {
			if (this.right == null) {
				setRight(new TreeNode_2(d));
			} else {
				right.insertInOrder(d);
			}
		}
		size++;
	}

	public int size() {
		return size;
	}

	public TreeNode_2 find(int d) {

		if (this.data == d) {
			return this;
		} else if (d < this.data) {
			return left != null ? left.find(d) : null;
		} else {
			return right != null ? right.find(d) : null;
		}
	}

	// --set left
	public void setLeft(TreeNode_2 node) {
		this.left = node;

		if (left != null) {
			left.parent = this;
		}
	}

	// --set right
	public void setRight(TreeNode_2 node) {
		this.right = node;

		if (right != null) {
			right.parent = this;
		}
	}

	public String toString() {
		return "" + data;
	}

}
