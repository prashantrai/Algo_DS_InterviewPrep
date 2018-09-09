package test.practice.misc;

import java.util.HashSet;
import java.util.LinkedList;

public class FlipABinaryTreeDemo {

	public static void main(String[] args) {
		TreeNode tree =  new TreeNode(20);
		tree.insertInOrder(10);
		tree.insertInOrder(25);
		tree.insertInOrder(5);
		tree.insertInOrder(15);
		tree.insertInOrder(24);
		tree.insertInOrder(28);
		
		System.out.println("Before :: ");
		levelOrderTraversal(tree);
		
		System.out.println("\nAfter :: ");
		TreeNode flippedNode = flipBinaryTree(tree); 
		levelOrderTraversal(flippedNode);
		

	}
	
	
	/*
	 * After one swap root will have 2 parent pointing to it (which will later be organized to 1 after the completion of call).
	 * 
	 * For Example: Consider a tree,
	 * 						 1
	 * 					2			3
	 * 				4		5	 6      7
	 * 
	 * Stage 1 : When 2 is the root in a recursive call and after first swap 4 will have 5 as left child and
	 * 2 as a right child and we set Left and Right child as NULL for the root (i.e. for 2 in this case) .
	 *  
	 * Stage 2: After  above 2 will have no child nodes but 2 parent nodes i.e. new Parent 4 and previous parent 1
	 * 
	 * */
	
	public static TreeNode flipBinaryTree (TreeNode root) {
		
		if(root == null) return root;
		
		if(root.left == null && root.right == null) return root;
		
		TreeNode flippedNode = flipBinaryTree (root.left);
		root.left.left = root.right;  //--right sibling becomes left child
		root.left.right = root;  	//--Root becomes right child
		root.left  = null;
		root.right = null;
		
		return flippedNode;
	}
	
	//--BFS
	public static void levelOrderTraversal (TreeNode node) {
		
		if (node == null) return;
		
		LinkedList<TreeNode> q = new LinkedList<TreeNode>();
		q.addLast(node);

		HashSet<TreeNode> visited = new HashSet<TreeNode>();
		
		StringBuilder res = new StringBuilder();
		
		while (!q.isEmpty()) {
			
			TreeNode currNode = q.removeFirst();
			
			if(visited.contains(currNode)) 
				continue;
			
			if(currNode == null) continue;
			
			visited.add(currNode);
			res.append(currNode.data + " ");
			q.addLast(currNode.left);
			q.addLast(currNode.right);
			
		}
		System.out.println();
		System.out.print(res);
		
	}
	

}
