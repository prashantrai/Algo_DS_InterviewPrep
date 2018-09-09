package test.practice.misc;

import java.util.HashSet;
import java.util.LinkedList;

public class TreeNodeDemo {

	public static void main(String[] args) {

		TreeNode tree =  new TreeNode(12);
		tree.insertInOrder(7);
		tree.insertInOrder(8);
		tree.insertInOrder(6);
		tree.insertInOrder(4);
		tree.insertInOrder(16);
		tree.insertInOrder(13);
		tree.insertInOrder(15);
		
		System.out.println(tree.find(113));
	}
	
	//--Using BFS
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

class TreeNode {
	
	public int data;
	public TreeNode left, right, parent;
	private int size = 0;
	
	public TreeNode(int d) {
		this.data = d;
		this.size = 1;
	}
	
	public void insertInOrder(int d) {
		
		if(d <= data) {
			if(left == null) {
				setLeftNode(new TreeNode(d));
			} else {
				left.insertInOrder(d);
			}
		} else {
			if(right == null) {
				setRightNode(new TreeNode(d));
			} else {
				right.insertInOrder(d);
			}
		}
		size++;
	}
	
	public int size() {
		return size;
	}
	
	public TreeNode find(int d) {
		
		if(data == d) return this;
		
		if(d <= data) {
			return left != null ? left.find(d) : null;
		} else if(d > data) {
			return right != null ? right.find(d) : null;
		}
		return null;
	}
	

	
	public void setLeftNode(TreeNode left) {
		this.left = left;
		if(left != null) { 
			left.parent =  this;
		}
		
 	}	
	public void setRightNode(TreeNode right) {
		this.right = right;
		
		if(right != null) {
			right.parent = this;
		}
	}	
	
	public String toString() {
		return ""+data;
	}
	
}
