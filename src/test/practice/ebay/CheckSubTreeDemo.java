package test.practice.ebay;


public class CheckSubTreeDemo {

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
		System.out.println(">>>Result:: "+containsTree_2(t1, t2));
		System.out.println(">>>Result:: "+containsTree_3(t1, t2));
	}
	
	public static boolean containsTree (TreeNode t1, TreeNode t2) {
		
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();

		getOrderedString(t1, sb1);
		getOrderedString(t2, sb2);
		
		System.out.println("sb1= "+sb1);
		System.out.println("sb2= "+sb2);
		
		return sb1.indexOf(sb2.toString()) != -1;
	}
	
	public static void getOrderedString(TreeNode node, StringBuilder sb) {
		
		if(node == null) {
			sb.append("X");
			return;
		}
		
		sb.append(node.data);
		getOrderedString(node.left, sb);
		getOrderedString(node.right, sb);
		
	}
	
	
	
	public static boolean containsTree_2 (TreeNode t1, TreeNode t2) {
		if(t1 == null) return false;
		
		if(t1.data == t2.data && matchTree(t1, t2)) {
			return true;
		}
		return matchTree(t1.left, t2) || matchTree(t1.right, t2); 
	}
	
	public static boolean matchTree (TreeNode t1, TreeNode t2) {

		if(t1 == null && t2 == null) {
			return true;
			
		}else if(t1 == null || t2 == null) {
			return false;
			
		} else if(t1.data != t2.data) {
			return false;
		}
		
		return matchTree(t1.left, t2.left) && matchTree(t1.right, t2.right);
	}


	public static boolean containsTree_3 (TreeNode r1, TreeNode r2) {
		
		if(r1 == null) return false;
		if(r2 == null) return true;
		
		if(r1.data == r2.data && matchTree_2(r1, r2)) {
			return true;
		}
		return matchTree(r1.left, r2) || matchTree(r1.right, r2);
	}
	
	public static boolean matchTree_2(TreeNode r1, TreeNode r2) {
		if(r1 == null && r2 == null) return true;
		
		if(r1 == null || r2 == null) return false;
		
		if(r1.data != r2.data) return false;
		
		else {
			return matchTree_2(r1.left, r2.left) && matchTree_2(r1.right, r2.right);
		}
	}

}
