package test.anuj;


public class SymmetricTreeDemo {

	

	public static void main(String[] args) {
		
		TreeNode root = new TreeNode(1);
		//root.left()
	}
	
	
	public static boolean isSymmetric(TreeNode root) {
		
			String sLeft = ""; 
			sLeft = preOrder(root.left, sLeft);
			
			String sRight = ""; 
			sRight = preOrder(root.right, sRight);
		
		
			return sLeft.equals(sRight);
		
	}
	
	public static String preOrder(TreeNode node, String s) {
		
		if(node != null) {
			s += node.data;
			preOrder(node.left, s);
			preOrder(node.right, s);
			
			return s;
		}
		return "";
	}
 
}
