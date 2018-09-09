package ctci.ch4.trees.and.graphs;

public class FirstCommonAncestorDemo_4 {

	/*
	 * When one of the node is not in the given tree
	 * */
	
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
		TreeNode q = tree.find(15);
		TreeNode node = getCommonAncestor(tree, p, q);
		System.out.println(node);

	}
	
	public static TreeNode getCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
		
		Result rs = ancestorHelper(root, p, q); 
		if(rs.isAncestor) return rs.node;
		
		return null;
		
	}
	
	public static Result ancestorHelper(TreeNode root, TreeNode p, TreeNode q) {
		
		if(root == null){
			return new Result(null, false);
		}
		
		if(root == p && root == q) {
			return new Result(root, true);
		}
		
		Result rx =  ancestorHelper(root.left, p, q);
		if(rx.isAncestor) {
			return rx;
		}
		
		Result ry =  ancestorHelper(root.right, p, q);
		if(ry.isAncestor) {
			return ry;
		}
		
		//-- if rx and ry both are not null, then they are in different sub-tree and root will be the ancestor
		if(rx.node != null && ry.node != null) {
			return new Result(root, true);
		}
		else if(root == p || root == q) {
			
			/*
			 * e.g. When p is root and ry is not null that means p is the ancestor
			 *      OR 
			 *      When q is root and rx is not null that means q is the ancestor 
			 * */
			
			boolean isAncestor = rx.node != null || ry.node != null;
			
			return new Result(root, isAncestor);
		} else {
			
			return new Result(rx.node != null ? rx.node : ry.node, false);
		}
		
	}

}

class Result {
	TreeNode node;
	boolean isAncestor;
	
	public Result(TreeNode node, boolean isAncestor) {
		
		this.node = node;
		this.isAncestor = isAncestor;
	}
}