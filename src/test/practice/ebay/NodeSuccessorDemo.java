package test.practice.ebay;



public class NodeSuccessorDemo {

	public static void main(String[] args) {

		TreeNode tree =  new TreeNode(16);
		tree.insertInOrder(7);
		tree.insertInOrder(8);
		tree.insertInOrder(6);
		tree.insertInOrder(4);
//		tree.insertInOrder(16);
//		tree.insertInOrder(13);
//		tree.insertInOrder(15);
//		tree.insertInOrder(17);
//		tree.insertInOrder(18);
		
		System.out.println(">>> "+inOrderSuccessor(tree.find(8)));
		System.out.println(">>> "+inOrderSuccessor_2 (tree.find(8)));
		
	}

	private static TreeNode inOrderSuccessor(TreeNode node) {
		
		if(node == null)
			return null;
		
		if(node.right != null) {
			return getLeftMostNode(node.right);
		} else {
			
			TreeNode n = node;
			TreeNode x = n.parent;
			
			while(x != null && x.left != n) {
				n = x;
				x = n.parent;
			}
			return x;
		}
		
	}
	
	public static TreeNode getLeftMostNode(TreeNode node) {
		
		if(node == null) 
			return null;
		
		while(node.left != null) {
			node  = node.left;
		}
		
		return node;
	}

	private static TreeNode inOrderSuccessor_2 (TreeNode node) {
		
		if(node == null) return null;
		
		if(node.right != null) {
			//--to traverse right subtree
			return getLeftMostNode(node.right);
		} else {

			TreeNode n = node;
			TreeNode x = node.parent;
			
			while(x != null && x.left != n) {
				n = x;
				x = n.parent;
			}
			return x;
		}
		
	}
	
	public TreeNode getLeftMostNode_2 (TreeNode node) {
		
		while (node != null && node.left != null) {
			node = node.left;
		}
		
		return node;
	}
	
	
}
