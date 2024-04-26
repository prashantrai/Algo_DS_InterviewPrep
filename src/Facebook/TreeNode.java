package Facebook;

class TreeNode {
	
	TreeNode left, right, parent;
	int data;
	int size=0;
	
	public TreeNode(int data) {
		this.data =  data;
		this.size = 1;
	} 
	
	//--insert inorder
	public void  insertInOrder(int d) {
		
		if(d <= data) {
			if(this.left == null) {
				setLeft(new TreeNode(d));
			} else {
				left.insertInOrder(d);
			}
		} else {
			if(this.right == null) {
				setRight(new TreeNode(d));
			} else {
				right.insertInOrder(d);
			}
		}
		size++;
	}
	
	public int size() { return size; }
	
	public TreeNode find(int d) {
		
		if(this.data == d) { 
			return this;
		} else if(d < this.data) {
			return left != null ? left.find(d) : null;
		} else {
			return right != null ? right.find(d) : null;
		}
	}
	
	//--set left
	public void setLeft(TreeNode node) {
		this.left = node;
		
		if(left != null) {
			left.parent = this;
		}
	}
	
	//--set right
	public void setRight(TreeNode node) {
		this.right = node;
		
		if(right != null) {
			right.parent = this;
		}
	}
	
	public String toString() {
		return ""+data;
	}
	
}