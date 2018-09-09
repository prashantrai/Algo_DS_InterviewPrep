package test.practice.misc;

import java.util.LinkedList;

public class LevelOrderTreeTraversal {

	public static void main(String[] args) {
		TreeNode tree =  new TreeNode(12);
		tree.insertInOrder(7);
		tree.insertInOrder(8);
		tree.insertInOrder(6);
		tree.insertInOrder(4);
		tree.insertInOrder(16);
		tree.insertInOrder(13);
		tree.insertInOrder(15);
		tree.insertInOrder(17);
		tree.insertInOrder(18);
		
		printLeverOrder(tree);

	}
	
	//--BFS approach to traverse level by level
	public static void printLeverOrder (TreeNode root) {
		
		if(root == null) return;
		
		LinkedList<TreeNode> q = new LinkedList<TreeNode>();
		q.add(root);
		
		while (!q.isEmpty()) {
			
			TreeNode node = q.removeFirst();
			
			if(node != null) {
				//--print current node
				System.out.print(node.data + " ");
				
				//--add left and right children to queue
				q.addLast(node.left);
				q.addLast(node.right);
			}
			
		}
	}

}
