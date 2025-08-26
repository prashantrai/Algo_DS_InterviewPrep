package LinkedIn;

public class DeleteNodeIn_A_BST_450_Medium {

	
	// Simple main for quick manual tests
	public static void main(String[] args) {

        TreeNode root = buildSample();
        System.out.print("Original inorder: ");
        inorder(root); System.out.println();

        root = deleteNode(root, 3);
        System.out.print("After delete 3: ");
        inorder(root); System.out.println();

        root = deleteNode(root, 5);
        System.out.print("After delete 5: ");
        inorder(root); System.out.println();

        root = deleteNode(root, 7);
        System.out.print("After delete 7: ");
        inorder(root); System.out.println();
    }
	
    

	// Basic TreeNode
    public static class TreeNode {
        int val; TreeNode left, right;
        TreeNode(int v) { val = v; }
    }
    
    /** Iterative */
    
    /* Alog: 
	1. Search for the node to delete and its parent
		Start from the root and move down using BST rules:
		- If key < node.val → go left.
		- If key > node.val → go right.
		
		Keep track of the parent pointer along the way.
		If node not found → return original root.
	
	2. Handle deletion cases
		Case 1: Node has no children (leaf)
		- Just set parent’s left/right to null.
		
		Case 2: Node has one child
		- Replace node with its single child in the parent.
		
		Case 3: Node has two children
		- Find the inorder successor (smallest node in right subtree).
		- Copy successor’s value into node-to-delete.
		- Now delete the successor (which is guaranteed to have at 
			most one child) using similar logic.
	
	3. Edge case: deleting the root
		- If parent is null (deleting root), handle separately:
			--Replace root with child or adjust with successor logic.
     * */
    
    /* Complexity: 
	Time Complexity:
		Search for node: O(h)
		Find inorder successor: O(h)
		Overall: O(h) (h = tree height).
		Balanced tree → O(log n), Skewed → O(n).
		
	Space Complexity:
		No recursion stack, just variables → O(1) extra space.
     * */
    
    public static TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return null;

        TreeNode parent = null; 
        TreeNode curr = root;

        // 1. Find the node to delete and its parent
        while (curr != null && curr.val != key) {
            parent = curr;
            if (key < curr.val) 
            	curr = curr.left;
            else 
            	curr = curr.right;
        }
        if (curr == null) 
        	return root; // key not found

        // 2. If node has two children
        if (curr.left != null && curr.right != null) {
            // Find inorder successor (min in right subtree)
            TreeNode succParent = curr;
            TreeNode succ = curr.right;
            while (succ.left != null) {
                succParent = succ;
                succ = succ.left;
            }
            // Copy successor's value
            curr.val = succ.val;
            // Now delete successor instead
            curr = succ;
            parent = succParent;
        }

        // 3. Now curr has at most one child
        TreeNode child = (curr.left != null) ? curr.left : curr.right;

        if (parent == null) {
            // deleting root
            return child;
        } else if (parent.left == curr) {
            parent.left = child;
        } else {
            parent.right = child;
        }

        return root;
    }

    // Helper: inorder traversal for testing
    public static void inorder(TreeNode root) {
        if (root == null) return;
        inorder(root.left);
        System.out.print(root.val + " ");
        inorder(root.right);
    }
    
    
    /** Recursive */
    
    /* Algo: 
	1. Search for the node with value key using BST property:
		If key < node.val, go left.
		If key > node.val, go right.
		If equal, we found the node to delete.

	2. When deletion node found there are 3 cases:
		- Leaf (no children): return null (remove it).
		- One child: return that child (replace node with its child).
		- Two children: find the inorder successor (minimum node in 
			right subtree), copy its value into current node, 
			then recursively delete that successor from the right subtree.
	
	3. Return the (possibly updated) subtree root up the recursion.
     */
    
    /* Complexity: 
	Time: O(h) where h is the tree height. In average balanced BST, 
		h = O(log n); worst-case (skewed) h = O(n).

	Space: O(h) due to recursion stack. Can be reduced to O(1) with 
		an iterative approach.
     * */
    
    public static TreeNode deleteNode_REC(TreeNode root, int key) {
    	if (root == null) return null;
    	
    	if (key < root.val) {
            // key is in left subtree
            root.left = deleteNode_REC(root.left, key); // recursive call
        } else if (key > root.val) {
            // key is in right subtree
            root.right = deleteNode_REC(root.right, key); // recursive call
            
        } else {
        	// found node to delete
            if (root.left == null) {
                // no left child -> replace with right (could be null)
                return root.right;
            } else if (root.right == null) {
                // no right child -> replace with left
                return root.left;
            } else {
            	// if two children then find inorder successor
            	// i.e. min in right subtree
            	TreeNode succ = findMin(root.right);
            	
            	// copy successor value , i.e., 
            	// replace successor with the node to be deleted
                root.val = succ.val;  
                
                // then, delete the successor node from right subtree
                root.right = deleteNode_REC(root.right, succ.val);
                
            }
        }
    	
    	return root;
    	
    } // deleteNode closed
    
    // Helper: find minimum node in a (non-null) subtree
    private static TreeNode findMin(TreeNode node) {
        while (node.left != null) 
        	node = node.left;
        return node;
    }
    
    /** For Tesing Only */
    
    // ----- Utilities for testing -----
    // In-order traversal (returns CSV string)
    public static String inorder2(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        inorderHelper(root, sb);
        return sb.length() == 0 ? "" : sb.toString();
    }
    private static void inorderHelper(TreeNode node, StringBuilder sb) {
        if (node == null) return;
        inorderHelper(node.left, sb);
        if (sb.length() > 0) sb.append(",");
        sb.append(node.val);
        inorderHelper(node.right, sb);
    }

    // Example builder for the sample tree: [5,3,6,2,4,null,7]
    public static TreeNode buildSample() {
        TreeNode n5 = new TreeNode(5);
        TreeNode n3 = new TreeNode(3);
        TreeNode n6 = new TreeNode(6);
        TreeNode n2 = new TreeNode(2);
        TreeNode n4 = new TreeNode(4);
        TreeNode n7 = new TreeNode(7);
        n5.left = n3; n5.right = n6;
        n3.left = n2; n3.right = n4;
        n6.right = n7;
        return n5;
    }
    /* To test recursive
    public static void main_REC(String[] args) {

        // Test 1: example delete 3
        TreeNode root1 = buildSample();
        System.out.println("Before (inorder): " + inorder(root1));
        root1 = deleteNode(root1, 3);
        System.out.println("After delete 3 (inorder): " + inorder(root1));

        // Test 2: delete root 5
        TreeNode root2 = buildSample();
        root2 = deleteNode(root2, 5);
        System.out.println("After delete 5 (inorder): " + inorder(root2));

        // Test 3: key not present
        TreeNode root3 = buildSample();
        root3 = deleteNode(root3, 0);
        System.out.println("Delete 0 (not present) (inorder): " + inorder(root3));

        // Test 4: empty tree
        TreeNode root4 = null;
        root4 = deleteNode(root4, 1);
        System.out.println("Empty tree delete (inorder): " + inorder(root4));

        // Test 5: delete a leaf (7)
        TreeNode root5 = buildSample();
        root5 = deleteNode(root5, 7);
        System.out.println("After delete 7 (inorder): " + inorder(root5));
    }
    */
    
}
