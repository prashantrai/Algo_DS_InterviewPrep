package test.practice.ebay;

import java.util.Random;


public class RandomNodeDemo {

	public static void main(String[] args) {

		//TreeNode tree =  new TreeNode(12);
		MyTree tree = new MyTree();
		tree.insertInOrder(7);
		tree.insertInOrder(8);
		tree.insertInOrder(6);
		tree.insertInOrder(4);
		tree.insertInOrder(16);
		tree.insertInOrder(13);
		tree.insertInOrder(15);
		
		System.out.println(tree.getRandomNode());
	}

}


class MyTree {
	
	MyTreeNode root;
	
	public int size() {
		return root != null ? root.size() : 0;
	}
	
	public MyTreeNode getRandomNode() {
		if(root == null) {
			return null;
		} 
		
		Random random = new Random();
		int index = random.nextInt(root.size);
		
		return root.getIthNode(index);
	
	}
	
	public void insertInOrder(int value) {
		if(root == null) {
			root = new MyTreeNode(value);
		} else {
			root.insertInOrder(value);
		}
	}
} 

class MyTreeNode {
	
	int data;
	MyTreeNode left;
	MyTreeNode right;
	MyTreeNode parent;
	int size;
	
	public MyTreeNode(int data) {
		this.data = data;
		this.size = 1;
	}
	
	public MyTreeNode getIthNode(int index) {
		
		int leftSize = left == null ? 0 : left.size();
		
		if(index < leftSize) {
			return left.getIthNode(index);
		} else if(index == leftSize) {
			return this;
		} else {  
			//--skipping over leftSize+1 nodes, so subtract them
			return right.getIthNode(index - (leftSize+1));
		}
	}
	
	public MyTreeNode getRandomNode() {
		
		int leftSize = left != null  ? left.size() : 0;
		
		Random random = new Random();
		int index = random.nextInt(size); //--this is a recursive call and calling random every time can be expensive 
		
		if(index < leftSize) {
			return left.getRandomNode();
		} else if (index == leftSize) {
			return this;
		} else {
			return right.getRandomNode();
		}
	
	}
	
	public void insertInOrder(int d) {
		
		if(d <= data) {
			if (left == null) {
				setLeftNode(new MyTreeNode(d));
			} else {
				left.insertInOrder(d);
			}
		} else {
			if(right == null) {
				setRightNode(new MyTreeNode(d));
			}
			else {
				right.insertInOrder(d);
			}
		}
		size++;
	}
	
	public MyTreeNode find (int d) {
		
		if(this.data == d) {
			return this;
		}
		
		else if(this.data <= d) {
			return left != null ? left.find(d) : null;
		} else if(this.data > d) {
			return right != null ? right.find(d) : null;
		}
		
		return null;
	}
	
	public int size() { return size; }
	
	public void setLeftNode(MyTreeNode node) {
		this.left = node;
		if(left != null) {
			left.parent = this;
		}
	}
	
	public void setRightNode(MyTreeNode node) {
		
		this.right = node;
		if(right != null) {
			right.parent = this;
		}
	}
	
	public String toString() {
		return ""+data;
	}
}