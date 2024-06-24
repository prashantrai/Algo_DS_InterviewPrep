package leetcode;

/*
 * https://leetcode.com/discuss/interview-question/algorithms/125088/facebook-phone-screen-convert-a-bst-into-a-doubly-linked-list
 * http://tech-queries.blogspot.com/2011/08/convert-binary-tree-in-circular-doubly.html
 * 
 * With Solution : https://segmentfault.com/a/1190000016762566
 * 
 * http://shibaili.blogspot.com/2018/08/convert-binary-search-tree-to-sorted.html
 * */

/**
 * This is in-place conversion Complete Problem :
 * https://www.lintcode.com/problem/convert-binary-search-tree-to-sorted-doubly-linked-list/description
 * 
 * Convert a BST to a sorted circular doubly-linked list in-place. Think of the
 * left and right pointers as synonymous to the previous and next pointers in a
 * doubly-linked list.
 */

// Solution reference : https://www.geeksforgeeks.org/convert-given-binary-tree-doubly-linked-list-set-3/

/*
 * Time Complexity: The above program does a simple inorder traversal, so time
 * complexity is O(n) where n is the number of nodes in given binary tree.
 * 
 * Complexity Analysis: [Leetcode premium]
 * 
 * Time complexity : O(N) since each node is processed exactly once.
 * 
 * Space complexity : O(N). We have to keep a recursion stack of
 * the size of the tree height, which is O(logN) for the best
 * case of completely balanced tree and O(N) for the worst case of
 * completely unbalanced tree.
 * 
 */

public class ConvertBinarySearchTreeToSortedDoublyLinkedList_426_Medium {

	public static void main(String[] args) {

		TreeNode tree = new TreeNode(12);
		tree.insertInOrder(7);
		tree.insertInOrder(8);
		tree.insertInOrder(6);
		tree.insertInOrder(4);
		tree.insertInOrder(16);
		tree.insertInOrder(13);
		tree.insertInOrder(15);

		TreeNode head = convertBinarySearchTreeToSortedDoublyCicularLinkedList(tree);
		printListForward(head);
		printListReverse(tail);
	}

	
	/*
	 * Complexity Analysis: [Leetcode premium]
	 * 
	 * Time complexity : O(N) since each node is processed exactly once.
	 * 
	 * Space complexity : O(N). We have to keep a recursion stack of
	 * the size of the tree height, which is O(logN) for the best
	 * case of completely balanced tree and O(N) for the worst case of
	 * completely unbalanced tree.
	 * 
	 */
	
	//https://leetcode.com/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/submissions/
	
	TreeNode _head;
	TreeNode _tail;
    public TreeNode treeToDoublyList(TreeNode root) {
        if(root == null) return null;
        
        helper(root);
        
        //make it circular //---moved this code to helper
        //head.left = tail; 
        //tail.right = head;
            
        return _head;
    }
    
    public void helper (TreeNode node) {
        if(node == null) 
            return;
        
        helper(node.left);
        
        if(head == null) {
            head = node;
        } else {
            node.left = tail;
            tail.right = node;            
        }
        tail = node;
        
        helper(node.right);

        //make it circular
        head.left = tail;
        tail.right = head;
    }
	

    
    /* Another approach */
	private static TreeNode prev = null;
	private static TreeNode head = null;
	private static TreeNode tail = null;

	private static TreeNode convertBinarySearchTreeToSortedDoublyCicularLinkedList(TreeNode root) {

		if (root == null)
			return null;

		// --traverse left
		convertBinarySearchTreeToSortedDoublyCicularLinkedList(root.left);

		if (prev == null) {
			head = root;
		} else {

			/*
			 * point the left to prev (think of left pointer of tree as prev in doubly
			 * linked list) and prev.right to curr node (think of right as a next pointer of
			 * doubly linked list)
			 */

			root.left = prev;
			prev.right = root;

		}
		prev = root;
		tail = root; // --make it circular

		// --traverse right
		convertBinarySearchTreeToSortedDoublyCicularLinkedList(root.right);
		tail.right = head;
		return head;
	}

	// --print list forward from head
	public static void printListForward(TreeNode node) {
		while (node != tail) {
			System.out.print(node.data + "->");
			node = node.right;
		}
		if (node == tail) {
			System.out.println(node.data);
		}
	}

	// --print list backward form tail
	public static void printListReverse(TreeNode node) {
		while (node != head) {
			System.out.print(node.data + "->");
			node = node.left;
		}
		if (node == head) {
			System.out.println(node.data);
		}
	}

	
	

}
