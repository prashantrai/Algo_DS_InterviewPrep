package test.practice.misc;

import java.util.Stack;

public class ZigZagOrderTreeTraversal {

	public static void main(String[] args) {
		TreeNode tree =  new TreeNode(20);
		tree.insertInOrder(10);
		tree.insertInOrder(25);
		tree.insertInOrder(5);
		tree.insertInOrder(15);
		tree.insertInOrder(24);
		tree.insertInOrder(28);
		
		traverseZigZagOrder(tree);

	}
	
	public static void traverseZigZagOrder (TreeNode root) {
		
		if(root == null) return;
		
		Stack<TreeNode> currLevel = new Stack<TreeNode>();
		Stack<TreeNode> nextLevel = new Stack<TreeNode>();
		Stack<TreeNode> temp;
		
		boolean leftToRight = true; //-- Direction of traversal
		
		currLevel.push(root);
		
		while(!currLevel.isEmpty()) {
			
			TreeNode node = currLevel.pop();
			if(node != null)
				System.out.print(node.data + " ");
			
			if(!leftToRight) {
				addElements(nextLevel, node.right);
				addElements(nextLevel, node.left);
			} else {
				addElements(nextLevel, node.left);
				addElements(nextLevel, node.right);
			}
			
			if(currLevel.isEmpty()) {
				temp = currLevel;
				currLevel = nextLevel;
				nextLevel = temp;
				
				leftToRight = !leftToRight;
			}
			
		}
	}
	
	private static void addElements(Stack<TreeNode> nodes, TreeNode node) {
		
		if(node != null) {
			nodes.push(node);
		}
	}

}
