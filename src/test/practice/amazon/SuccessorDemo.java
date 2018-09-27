package test.practice.amazon;


public class SuccessorDemo {
	
	
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
	
	
	public static TreeNode inOrderSuccessor(TreeNode node)  {
		
		if(node == null) return null;
		
		if(node.right != null) {
			return findLeftMostNode(node.right);
		} else  {
			TreeNode q = node;
			TreeNode x = q.parent;
			
			while (x!=null  && x.left != q) {
				q = x;
				x = q.parent;		
				
			}
			return x;
		}
	}
	
	private static TreeNode findLeftMostNode(TreeNode node) {
		
		while (node.left != null) {
			node = node.left;
		}
		 return node;
		
		
	}

}
