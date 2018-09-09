package test.practice.yhoo;

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
		
		System.out.println("data found in tree: "+tree.find(13).data);
		
		//--Functional/Working
		System.out.println("Inorder: ");
		tree.inOrder(tree);
		
		//--Functional/Working
		//System.out.println("Post-order: ");
		//TreeNode.postOrder(tree);
		
		//--Functional/Working
		//System.out.println("Pre-order: ");
		//TreeNode.preOrder(tree);
		
		System.out.println("\nFind:: "+(tree.find(13)).data);
		
		
	}

}

class TreeNode {
	
	int data;
	int size;
	TreeNode left, right, parent;
	
	
	public TreeNode(int d) {
		this.data = d;
		size++;
	} 
	
	public void insertInOrder(int d) {
		
		if(d <= this.data) {
			
			if(this.left == null) {
				setLeftNode(new TreeNode(d));
			}else {
				left.insertInOrder(d);
			}
		} else {
			if(this.right == null) {
				setRightNode(new TreeNode(d));
			}else {
				right.insertInOrder(d);
			}
		}
		size++;
	
	}
	
	public TreeNode find (int d) {
		
		if(this.data == d) return this;
		
		if(data >= d) {
			return left != null ? left.find(d) : null;
		} else if(data < d) {
			return right != null ? right.find(d) : null;
		}
		
		return null;
		
	}
	
	public void setLeftNode(TreeNode left) {
		this.left = left;
		
		if(left != null) {
			left.parent = this.left;
		}
		//return left;
	}
	
	public void setRightNode(TreeNode right) {
		this.right=right;
		
		if(right != null) {
			right.parent = this;
		}
		//return right;		
	}
	
	
	public static void inOrder(TreeNode node) {
		
		if (node != null) {
			inOrder(node.left);
			visit(node);
			inOrder(node.right);
		}
	}
	public static void preOrder(TreeNode node) {
		
		if (node != null) {
			visit(node);
			inOrder(node.left);
			inOrder(node.right);
		}
	}
	public static void postOrder(TreeNode node) {
	
		if (node != null) {
			inOrder(node.left);
			inOrder(node.right);
			visit(node);
		}
	}
	
	
	public static void visit(TreeNode node) {
		System.out.print(node.data+" ");
	}
}
