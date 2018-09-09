package test.practice.ebay;


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
		
		System.out.println(tree.find(13));
	}

}

class TreeNode {
	
	int data;
	TreeNode left, right, parent;
	int size;
	
	public TreeNode() {}
	public TreeNode(int data) { 
		this.data = data;
		size = 1;
	}
	
	public void insertInOrder(int data) {
		
		if(data <= this.data) {
			if(left == null) {
				setLeftNode(new TreeNode(data));
			} else {
				left.insertInOrder(data);
			}
		} else {
			if(right == null) {
				setRightNode(new TreeNode(data));
			} else {
				right.insertInOrder(data);
			}
		}
		size++;
	}
	
	public int size() {
		return size;
	}
	
	public TreeNode find(int v) {
		
		if(v == data) {
			return this;
		}
		
		if(v <= data) {
			return left != null ? left.find(v) : null;
		} else {
			return right!= null ? right.find(v) : null;
		}
		
	}
	
	private TreeNode setRightNode(TreeNode node) {
		right = node;
		if(right != null) {
			right.parent = this;
		}
		return right;
 	}
	
	private TreeNode setLeftNode(TreeNode node) {
		left = node;
		
		if(left != null) {
			left.parent = this;
		}
		
		return left;
	}
	
	public String toString() {
		return ""+data;
	}
	
}

