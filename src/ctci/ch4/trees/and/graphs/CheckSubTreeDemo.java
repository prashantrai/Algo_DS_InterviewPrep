package ctci.ch4.trees.and.graphs;

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
	}
	
	public static boolean containsTree(TreeNode t1, TreeNode t2) {
		
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		
		getOrderedString(t1, sb1);
		getOrderedString(t2, sb2);
		
		return sb1.indexOf(sb2.toString()) != -1;
	}
	
	public static void getOrderedString(TreeNode node, StringBuilder sb) {
		
		if(node == null) {
			sb.append("X");
			return;
		}
		
		sb.append(node.data + " ");
		getOrderedString(node.left, sb);
		getOrderedString(node.right, sb);
		
		System.out.println(sb);
	}

}
