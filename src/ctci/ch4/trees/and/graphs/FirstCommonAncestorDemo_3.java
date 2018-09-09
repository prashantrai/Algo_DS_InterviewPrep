package ctci.ch4.trees.and.graphs;

public class FirstCommonAncestorDemo_3 {

	public static void main(String[] args) {

		TreeNode tree = new TreeNode(12);
		tree.insertInOrder(7);
		tree.insertInOrder(8);
		tree.insertInOrder(6);
		tree.insertInOrder(4);
		tree.insertInOrder(16);
		tree.insertInOrder(13);
		tree.insertInOrder(15);

		TreeNode p = tree.find(4);
		TreeNode q = tree.find(8);
		TreeNode node = getCommonAncestor(tree, p, q);
		System.out.println(node);

	}

	public static TreeNode getCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
		
		if(!covers(root, p) || !covers(root, q)) { //--check if one node is not in tree
			return null;
		}
		
		return commonAncestorHelper(root, p, q);
	}
	
	public static TreeNode commonAncestorHelper(TreeNode root, TreeNode p, TreeNode q) {
		
		boolean pOnLeftSide = covers(root.left, p);
		boolean qOnLeftSide = covers(root.left, q);
		
		if(pOnLeftSide != qOnLeftSide) {
			return root;  //--node are not on the same side, returning root as the root will be the ancestor
		}
		
		TreeNode childSide = pOnLeftSide ? root.left : root.right;
		
		return commonAncestorHelper(childSide, p, q);
		
	}
	
	public static boolean covers(TreeNode root, TreeNode node) {
		if(root == null) {
			return false;
		}
		if(root == node) return true;
		return covers(root.left, node) || covers(root.right, node);
	}
	
}
