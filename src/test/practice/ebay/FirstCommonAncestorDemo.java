package test.practice.ebay;


public class FirstCommonAncestorDemo {

	public static void main(String[] args) {

		/*TreeNode tree =  new TreeNode(12);
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
		TreeNode node_2 = getCommonAncestor_2(tree, p, q);*/
		
		TreeNode tree =  new TreeNode(3);
		tree.insertInOrder(1);
		tree.insertInOrder(5);
		tree.insertInOrder(8);
		
		TreeNode p = tree.find(5);
		TreeNode q = tree.find(8);
		TreeNode node = getCommonAncestor(tree, p, q);
		TreeNode node_2 = getCommonAncestor_2(tree, p, q);
		
		System.out.println(node);
		System.out.println(node_2);
		
	}
	
	public static TreeNode getCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
		
		Result res = commonAncestorHelper(root, p, q);
		if(res.isAncestor) {
			return res.node;
		}
		return null;
	}
	
	public static Result commonAncestorHelper(TreeNode root, TreeNode p, TreeNode q) {
		if(root == null) {
			return new Result(root, false);
		}
		
		if(p == root && q == root) {
			return new Result(root, true);
		}
		
		Result resLeft = commonAncestorHelper(root.left, p, q);
		if(resLeft.isAncestor) {
			return resLeft;
		}
		
		Result resRight = commonAncestorHelper(root.right, p, q);
		if(resRight.isAncestor) {
			return resRight;
		}
		
		if(resLeft.node != null && resRight.node != null) {
			return new Result(root, true);
			
		} else if(root == p || root == q) { //--when one of the node is part of subtree
			
			boolean isAncestor = resLeft.node != null || resRight.node != null;
			return new Result(root, isAncestor);
		} else {
			return new Result(resLeft.node != null ? resLeft.node : resRight.node, false);
		}

	}

	private static class Result {
		TreeNode node;
		boolean isAncestor;
		public Result(TreeNode node, boolean isAncestor) {
			this.node = node;
			this.isAncestor = isAncestor;
		}
	}
	
	public static TreeNode getCommonAncestor_2 (TreeNode root, TreeNode p, TreeNode q) {
		
		Result res = commonAncestorHelper_2(root, p, q);
		
		return res.isAncestor ? res.node : null;
	}
	
	public static Result commonAncestorHelper_2 (TreeNode root, TreeNode p, TreeNode q) {
		
		if(root == null) return new Result(null, false);
		
		if(root == p && root == q) {
			return new Result(root, true);
		}
		
		Result rx = commonAncestorHelper_2(root.left, p, q);
		if(rx.isAncestor) {
			return rx;
		}
		
		Result ry = commonAncestorHelper_2(root.right, p, q);
		if(ry.isAncestor) {
			return ry;
		}
		
		if(rx.node != null && ry.node != null) {
			return new Result(root, true);
		}
		else if(root == p || root == q) {
			boolean isAncestor = rx.node != null || ry.node != null;
			return new Result(root, isAncestor);
			
		} else {
			return new Result( rx.node != null ? rx.node : ry.node, false);
		}
		
	}
}


