package Amazon;


public class CountUniValuedSubtreesInABinaryTree {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	
	/*
	 Key Observation
		The only information we need from each child is:

		Is your entire subtree uni-valued?

		If both children answer Yes, and their values (if they exist) match the current 
		node's value, then the current subtree is also uni-valued. This bottom-up dependency 
		makes Postorder DFS the ideal approach. 
		
	Algorithm
		Traverse the tree using Postorder DFS.
		
		A null node is considered a valid uni-valued subtree.
		
		Recursively check whether the left and right subtrees are uni-valued.
		
		If either subtree is not uni-valued, return false.
		
		If the left child exists and its value differs from the current node, return false.
		
		If the right child exists and its value differs from the current node, return false.
		
		Otherwise, the current subtree is uni-valued:
		- Increment the answer.
		- Return true.
		
	 */
	
	
	/*  Time Complexity: O(n)
			Every node is visited exactly once.
		
		Space Complexity: O(h)
			h is the height of the tree due to the recursion stack.
			Worst case: O(n) for a skewed tree.
			Best/Average case: O(log n) for a balanced tree. 
	 */
	
	private static int count = 0;

    public static int countUnivalSubtrees(TreeNode root) {
        dfs(root);
        return count;
    }

	private static boolean dfs(TreeNode node) {

        if (node == null) {
            return true;
        }

        boolean left = dfs(node.left);
        boolean right = dfs(node.right);

        if (!left || !right) {
            return false;
        }

        if (node.left != null && node.left.val != node.val) {
            return false;
        }

        if (node.right != null && node.right.val != node.val) {
            return false;
        }

        count++;
        return true;
    }
	
	private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val) { this.val = val; }
    }

}



/*
Intuition
A subtree is uni-valued if every node inside it has the same value.

Notice that whether the current subtree is uni-valued depends on its left and right subtrees. 
Therefore, we cannot decide this before processing the children.

This naturally suggests a Postorder DFS (Left → Right → Node).

For every node, we recursively determine whether its left and right subtrees are uni-valued. 
If they are, and the values of the existing children match the current node's value, then the 
current subtree is also uni-valued.

Whenever we find such a subtree, we increment our answer.


Algorithm
Traverse the tree using Postorder DFS.

A null node is considered a valid uni-valued subtree.

Recursively check whether the left and right subtrees are uni-valued.

If either subtree is not uni-valued, return false.

If the left child exists and its value differs from the current node, return false.

If the right child exists and its value differs from the current node, return false.

Otherwise, the current subtree is uni-valued:

Increment the answer.
Return true.
Dry Run
        5
       / \
      1   5
     / \   \
    5   5   5
Step 1
Visit the leftmost leaf.

5
Uni-valued
Count = 1
Step 2
Visit the right leaf of node 1.

5
Uni-valued
Count = 2
Step 3
Process node 1.

    1
   / \
  5   5
Although both children are uni-valued,

5 != 1
Hence this subtree is not uni-valued.

Step 4
Visit the leaf on the right.

5
Uni-valued
Count = 3
Step 5
Process

  5
   \
    5
Both values are equal.

Uni-valued
Count = 4
Step 6
Process the root.

      5
     / \
    1   5
Left child has value 1, so the whole tree is not uni-valued.

Answer = 4
 
 
 
 */