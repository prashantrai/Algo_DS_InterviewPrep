package test.practice.amazon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListOfDepthsDemo {

	public static void main(String[] args) {

		TreeNode tree =  new TreeNode(12);
		tree.insertInOrder(7);
		tree.insertInOrder(8);
		tree.insertInOrder(6);
		tree.insertInOrder(4);
		tree.insertInOrder(16);
		tree.insertInOrder(13);
		tree.insertInOrder(15);
		
		
		System.out.println(">>> " + createLevelLinkedList(tree));
		
	}
	
	public static List<LinkedList<TreeNode>> createLevelLinkedList(TreeNode root) {
		
		ArrayList<LinkedList<TreeNode>> result = new ArrayList<>();
		
		LinkedList<TreeNode> current = new LinkedList<TreeNode>();
		
		if(root != null)
			current.add(root);
		
		while (current.size() > 0) {
			
			result.add(current);
			LinkedList<TreeNode> parent = current;
			current = new LinkedList<TreeNode>();
			
			for(TreeNode node : parent) {

				if(node.left != null) {
					current.add(node.left);
				}
				if(node.right != null) {
					current.add(node.right);
				}
			}
		}
		
		return result;
		
	}

}
