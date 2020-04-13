package leetcode;

import java.util.ArrayList;
import java.util.List;

public class ConvertSortedListToBinarySearchTree_109_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/*
	 * https://leetcode.com/problems/convert-sorted-list-to-binary-search-tree/
	 * 
	 * Complexity Analysis: 
	 *  
	 * Time Complexity: The time complexity comes down to just O(N) now since we
	 * convert the linked list to an array initially and then we convert the array
	 * into a BST. Accessing the middle element now takes O(1) time and hence
	 * the time complexity comes down. 
	 * 
	 * Space Complexity: Since we used extra space
	 * to bring down the time complexity, the space complexity now goes up to
	 * O(N) as opposed to just O(logN) in the previous solution. This
	 * is due to the array we construct initially.
	 * 
	 */

	// --Workging
	public TreeNode sortedListToBST(ListNode head) {

		List<Integer> list = new ArrayList<>();
		while (head != null) {
			list.add(head.val);
			head = head.next;
		}

		TreeNode root = helper(list, 0, list.size() - 1);

		return root;
	}

	public TreeNode helper(List<Integer> list, int start, int end) {

		if (start > end)
			return null;

		int mid = (start + end) / 2;
		TreeNode node = new TreeNode(list.get(mid));

		node.left = helper(list, start, mid - 1);
		node.right = helper(list, mid + 1, end);

		return node;
	}

}
