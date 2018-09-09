package ctci.ch4.trees.and.graphs;

//--Solution 2
public class FirstCommonAncestorDemo_2 {
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
		
		if(!covers(root, p) || !covers(root, q)) {
			return null;
			
		} else if(covers(p, q)) {
			return p;
		} else if(covers(q, p)) {
			return q;
		}
		
		TreeNode sibling = getSibling(p);
		TreeNode parent = p.parent;
		
		while(!covers(sibling, q)) {
			
			sibling = getSibling(parent);
			parent = parent.parent;
		}
		
		return parent;
	}
	
	public static TreeNode getSibling(TreeNode node) {
		
		if(node == null || node.parent == null)  //--check node.parent against null to check if the node is only element in the list
			return node;
		
		TreeNode parent = node.parent;
		
		return parent.left == node ? parent.right : parent.left;
		
		
	}
	
	public static boolean covers(TreeNode root, TreeNode node) {
		if(root == null) 
			return false;
		
		if(root == node) return true;
		
		return covers(root.left, node) || covers(root.right, node);
		
	}
}
