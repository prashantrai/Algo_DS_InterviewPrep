package test.practice.ebay;

import java.util.LinkedList;

public class ListOfDepthsDemo {

	public static void main(String[] args) {

		TreeNode tree = new TreeNode(12);
		tree.insertInOrder(7);
		tree.insertInOrder(8);
		tree.insertInOrder(6);
		tree.insertInOrder(4);
		tree.insertInOrder(16);
		tree.insertInOrder(13);
		tree.insertInOrder(15);

		System.out.println(tree.find(13).data);

		System.out.println(">>> " + createLevelLinkedList(tree));
		System.out.println(">>> " + createLevelLinkedListBFS(tree));
		System.out.println(">>> " + createLevelLinkedListBFS_2(tree));

	}
	
	//--BFS Approach
	public static LinkedList<LinkedList<TreeNode>> createLevelLinkedListBFS(TreeNode root) {
		
		if(root == null) return null;
		
		LinkedList<LinkedList<TreeNode>> lists = new LinkedList<LinkedList<TreeNode>>();
		
		LinkedList<TreeNode> current = new LinkedList<TreeNode>();
		current.add(root);
		
		while(current.size() > 0) {
			
			lists.add(current);
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
		return lists;
	}
	

	public static LinkedList<LinkedList<TreeNode>> createLevelLinkedList(TreeNode root) {
		LinkedList<LinkedList<TreeNode>> lists = new LinkedList<LinkedList<TreeNode>>();
		return createLevelLinkedList(root, 0, lists);
	}
	
	public static LinkedList<LinkedList<TreeNode>> createLevelLinkedList(TreeNode root, int level,
			LinkedList<LinkedList<TreeNode>> lists) {

		if(root == null) return null;
		
		LinkedList<TreeNode> list;
		if (lists.size() == level) {
			list = new LinkedList<TreeNode>();
			lists.add(list);
		} else {
			list = lists.get(level);
		}
		
		list.add(root);
		createLevelLinkedList(root.left, level+1, lists);
		createLevelLinkedList(root.right, level+1, lists);
		return lists;
	}
	
	
	public static LinkedList<LinkedList<TreeNode>> createLevelLinkedListBFS_2 (TreeNode root) {
		
		LinkedList<LinkedList<TreeNode>> result = new LinkedList<LinkedList<TreeNode>>();
		
		if(root == null) {
			return result;
		}
		
		LinkedList<TreeNode> current = new LinkedList<TreeNode>();
		current.add(root);
		
		LinkedList<TreeNode> parents = null;
		
		while (current.size() > 0) {
			
			result.add(current);
			
			parents = current;
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

}
