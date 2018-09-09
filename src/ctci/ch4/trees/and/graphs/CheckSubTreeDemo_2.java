package ctci.ch4.trees.and.graphs;

public class CheckSubTreeDemo_2 {

	public static void main(String[] args) {

		TreeNode t1 =  new TreeNode(4);
		t1.insertInOrder(2);
		t1.insertInOrder(1);
		t1.insertInOrder(3);
		t1.insertInOrder(6);
		t1.insertInOrder(7);
		t1.insertInOrder(5);
		
		TreeNode t2 =  new TreeNode(6);
		t2.insertInOrder(7);
		t2.insertInOrder(5);
		
		System.out.println(">>>Result:: "+containsTree(t1, t2));
	}
	
	public static boolean containsTree(TreeNode t1, TreeNode t2) {
		
		if(t2 == null) 
			return true;
		
		return subTree(t1, t2);
		
	}
	
	public static boolean subTree(TreeNode r1, TreeNode r2) {
		
		if(r1 == null) { 
			return false;
			
		} else if(r1.data == r2.data && matchTree(r1, r2)) {
			return true;
		}
		
		return subTree(r1.left, r2) || subTree(r1.right, r2);
		
	}
	
	public static boolean matchTree(TreeNode t1, TreeNode t2) {
		
		if(t1 == null && t2 == null) { //--nothing left in subtree, we reached end
			return true;
		} else if(t1 == null || t2 == null) {
			return false; //-- we reached the end of either of node and no match found
		} else if (t1.data != t2.data) {
			return false;
		} else {
			return matchTree(t1.left, t2.left) && matchTree(t1.right, t2.right);
		}
	} 

}
