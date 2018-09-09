package ctci.ch4.trees.and.graphs;

public class NodeSuccessorDemo {

	public static void main(String[] args) {

		TreeNode tree =  new TreeNode(12);
		tree.insertInOrder(7);
		tree.insertInOrder(8);
		tree.insertInOrder(6);
		tree.insertInOrder(4);
		tree.insertInOrder(16);
//		tree.insertInOrder(13);
//		tree.insertInOrder(15);
//		tree.insertInOrder(17);
//		tree.insertInOrder(18);
		
		System.out.println(">>> "+inOrderSuccessor(tree.find(8)));
		
	}
	
	
	public static TreeNode inOrderSuccessor(TreeNode n) {
		
		if(n == null) return null;
		
		if(n.right != null) {
			
			return getLeftMostChild(n.right);
			
		} else {
			//--if n is the right child or n.right is NULL
			TreeNode q = n;
			TreeNode x = q.parent;
			
			//--move up until we are left in lieu of right
			while( x!= null && x.left != q) {
				
				q = x;
				x = x.parent;
			}
			return x;
		}
		
	}

	private static TreeNode getLeftMostChild(TreeNode node) {
		if(node == null) return null;
		
		while(node.left != null) {
			node = node.left;
		}
		
		return node;
	}
	
}
