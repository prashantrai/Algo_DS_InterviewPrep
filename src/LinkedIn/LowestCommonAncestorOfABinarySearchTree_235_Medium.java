package LinkedIn;


public class LowestCommonAncestorOfABinarySearchTree_235_Medium {

	public static void main(String[] args){
        // Build example tree:
        //         6
        //       /   \
        //      2     8
        //     / \   / \
        //    0   4 7   9
        //       / \
        //      3   5
		TreeNode n6 = createNode(6), n2 = createNode(2), n8 = createNode(8), 
				n0 = createNode(0), n4 = createNode(4),
				n7 = createNode(7), n9 = createNode(9), 
				n3 = createNode(3), n5 = createNode(5);
        n6.left=n2; n6.right=n8;
        n2.left=n0; n2.right=n4;
        n8.left=n7; n8.right=n9;
        n4.left=n3; n4.right=n5;

        System.out.println(lowestCommonAncestor(n6, n2, n8).val); // 6
        System.out.println(lowestCommonAncestor(n6, n2, n4).val); // 2
        System.out.println(lowestCommonAncestor(n6, n4, n4).val); // 4
    }
	
	static TreeNode createNode(int v){ 
		return new TreeNode(v); 
	} 
	private static class TreeNode {
		int val;
		TreeNode left,  right;
		TreeNode(int x) { val = x; }
	}
	
	
	/*
    Time: O(h) where h is tree height (balanced: O(log n), skewed: O(n))
    Space: O(1) iterative (no recursion stack)
     */
    // iterative
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

        // Ensure p.val <= q.val to simplify comparisons (optional)
    	// You don’t have to check separately p.val < root.val && q.val < root.val.
    	// Just one bound check (b < root.val or a > root.val).
        int  minNode = Math.min(p.val, q.val);
        int maxNode = Math.max(p.val, q.val);

        while (root != null) {
            if(maxNode < root.val) {
                // Both targets are strictly in the left subtree
                root = root.left;

            } else if(minNode > root.val) {
                // Both targets are strictly in the right subtree
                root = root.right;

            } else {
                // a <= root.val <= b  -> split happens here (or one equals root)
                return root; // root is LCA
            }
        }
        return null;
    }

    /*
    Time Complexity: O(N), where N is the number of nodes in the BST. 
        In the worst case we might be visiting all the nodes of the BST.

    Space Complexity: O(N). This is because the maximum amount of space 
        utilized by the recursion stack would be N since the height of a 
        skewed BST could be N.
     */
    // recursion
    public static TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null) return root;

        if(p.val < root.val && q.val < root.val) 
            return lowestCommonAncestor(root.left, p, q);
        
        if(p.val > root.val && q.val > root.val) 
            return lowestCommonAncestor(root.right, p, q);

        return root;
    }
    

}
