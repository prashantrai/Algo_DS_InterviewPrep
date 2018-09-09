package ctci.ch4.trees.and.graphs;

public class FirstCommonAncestorDemo {

	public static void main(String[] args) {

		TreeNode tree =  new TreeNode(12);
		tree.insertInOrder(7);
		tree.insertInOrder(8);
		tree.insertInOrder(6);
		tree.insertInOrder(4);
		tree.insertInOrder(16);
		tree.insertInOrder(13);
		tree.insertInOrder(15);
		
		TreeNode p = tree.find(4);
		TreeNode q = tree.find(15);
		TreeNode node = getCommonAncestor(p, q);
		
		System.out.println(node);
		
	}
	
	public static TreeNode getCommonAncestor(TreeNode p, TreeNode q) {
		
		if(p == null || q == null) 
			return null;
	
		int delta = depth(p) - depth(q);
		
		TreeNode first = delta > 0 ? q : p; //--get the shallow node first
		TreeNode second = delta > 0 ? p : q; //--get the deeper node
		
		//--move deeper node up to level with shallow node
		second = goUpBy(second, Math.abs(delta));

		
		//--Loop will break when first and second will point to same node i.e. ancestor
		while (first != second && first != null && second != null) {
			first = first.parent;
			second = second.parent;
		}
		
		return first == null || second == null ? null : first; 
		
	}

	public static TreeNode goUpBy(TreeNode node, int delta) {
		while(delta > 0 && node != null) {
			node = node.parent;
			delta--;
		}
		return node;
	}
	
	public static int depth(TreeNode node) {
		int depth=0;
		while(node != null) {
			node = node.parent;
			depth++;
		}
		return depth;
	}
}
