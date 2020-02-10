package leetcode;

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
	
	public static void inOrder(TreeNode node) {
		
		if(node == null) return;
		
		inOrder(node.left);
		visit(node);
		inOrder(node.right);
	}
	
	public static void postOrder(TreeNode node){
		
		if(node != null) {
			postOrder(node.left);
			postOrder(node.right);
			visit(node);
		}
	}
	
	public static void preOrder(TreeNode node) {
		
		if(node != null) {
			visit(node);
			preOrder(node.left);
			preOrder(node.right);
		}
	}
	
	public static void visit(TreeNode node) {
		System.out.println(node.data);
	}
	
	public String toString() {
		return ""+data;
	}
	
}
