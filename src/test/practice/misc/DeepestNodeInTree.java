package test.practice.misc;

import java.util.LinkedList;

public class DeepestNodeInTree {

	public static void main(String[] args) {
		TreeNode tree =  new TreeNode(20);
		tree.insertInOrder(10);
		tree.insertInOrder(25);
		tree.insertInOrder(5);
		tree.insertInOrder(15);
		tree.insertInOrder(24);
		tree.insertInOrder(28);
		tree.insertInOrder(3);
		
		deepestNode(tree);

	}
	
	//--Working Solution : Find the deepest node in a Tree : Level order traversal 
	//-- Runtime O(n), Space O(n)
	
	public static void deepestNode(TreeNode root) {
		
		if(root == null || root.left == null || root.right == null) return;// root;
		
		LinkedList<TreeNode> q = new LinkedList<>();
		//ArrayList<Integer> levelOrderTraverse = new ArrayList<>();
		int deepestNode = -1;
		
		q.addLast(root);
		
		while (!q.isEmpty()) {
			
			TreeNode node =  q.removeFirst();
			//levelOrderTraverse.add(node.data);
			deepestNode = node.data;
			
			if(node.left != null) {
				q.addLast(node.left);
			}
			if(node.right != null) {
				q.addLast(node.right);
			}
			
		}
		
		System.out.println("Deepest Node: " + deepestNode);
		
	}

}
