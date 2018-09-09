package ctci.ch4.trees.and.graphs;

import java.util.ArrayList;
import java.util.LinkedList;

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
		
		System.out.println(tree.find(13).data);
		
		System.out.println(">>> " + createLevelLinkedList(tree));
		System.out.println(">>> " + createLevelLinkedList_2(tree));
		
	}
	
	
	//--Approach 2: using BFS 
	public static ArrayList<LinkedList<TreeNode>> createLevelLinkedList_2(TreeNode root) {
		
		ArrayList<LinkedList<TreeNode>> result = new ArrayList<LinkedList<TreeNode>>();
		LinkedList<TreeNode> current = new LinkedList<TreeNode>();
		
		if(root != null) {
			current.add(root);
		}
		
		while (current.size() > 0) {
			result.add(current);
			LinkedList<TreeNode> parents = current;
			current = new LinkedList<TreeNode>();
			
			for(TreeNode parent : parents) {
				
				if(parent.left != null) {
					current.add(parent.left);
				} 
				if(parent.right != null) {
					current.add(parent.right);
				}
			}
		}
		
		return result;
		
	} 
	
	
	public static ArrayList<LinkedList<TreeNode>> createLevelLinkedList(TreeNode root) {
		
		int level = 0;
		ArrayList<LinkedList<TreeNode>> lists = new ArrayList<LinkedList<TreeNode>>();
		lists = createLevelLinkedList(root, lists, level);
		
		return lists;
	}


	//--Approach 1 :: pre-order tree traversal approach
	private static ArrayList<LinkedList<TreeNode>> createLevelLinkedList(TreeNode root, ArrayList<LinkedList<TreeNode>> lists, int level) {
		
		if(root == null) {
			return null;
		}
		
		LinkedList<TreeNode> list = null;
		
		if(lists.size() == level) {
			list =  new LinkedList<TreeNode>();
			lists.add(list);
		} else {
			list = lists.get(level);
		} 
		list.add(root);
		
		createLevelLinkedList(root.left, lists, level+1);
		createLevelLinkedList(root.right, lists, level+1);
		
		return lists;
	}
	

}

class TreeNode_3 {
	
	TreeNode_3 left, right, parent;
	int size=0;
	int data;
	
	public TreeNode_3(int data) {
		this.data = data;
		this.size = 1;
	}
	
	public void insertInOrder(int d) {
		
		if(d <= this.data) {
			if(this.left == null) {
				setLeftNode(new TreeNode_3(d));
			} else {
				left.insertInOrder(d);
			}
			
		} else {
			if(this.right == null) {
				setRightNode(new TreeNode_3(d));
			} else {
				right.insertInOrder(d);
			}
		}
		size++;
	}
	
	public TreeNode_3 find(int d) {
		
		if(this.data == d) {
			return this;
		} else if (this.data >= d) {
			return left != null ? left.find(d) : null;
		} else {
			return right != null ? right.find(d) : null;
		}
		
	}
	
	public int size() { return this.size; }
	
	public void setLeftNode(TreeNode_3 node) {
		this.left = node;
		
		if(left != null)
			node.parent = this;
	}
	
	public void setRightNode(TreeNode_3 node) {
		this.right = node;
		if(right != null)
			node.parent = this;
	}
	
}
