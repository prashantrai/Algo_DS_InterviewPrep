package test.practice.misc;

import java.util.ArrayList;

public class SerializeDeserializeTree {

	public static void main(String[] args) {
		TreeNode tree =  new TreeNode(20);
		tree.insertInOrder(10);
		tree.insertInOrder(25);
		tree.insertInOrder(5);
		tree.insertInOrder(15);
		tree.insertInOrder(24);
		tree.insertInOrder(28);
		
		ArrayList<String> list = new ArrayList<String>(); 
		
		serialize(tree, list);  //--looks good
		System.out.println("After serialization:   "+list);
		
		TreeNode node = deserialize(list); //--looks good
		
		//--to test: re-serializing tree after desrailize, if the result is same then Tree constructed correctly
		list.clear();
		serialize(node, list);
		
		System.out.println("After deserialization: "+list ); 

	}
	
	public static void serialize (TreeNode node, ArrayList<String> list) {
		
		if(node == null) { 
			list.add("#");
			return;
		}
		
		//--PreOrder (NLR)
		list.add(""+node.data);
		serialize(node.left, list);
		serialize(node.right, list);
		
	}
	
	static int idx;  //--global
	
	public static TreeNode deserialize (ArrayList<String> list) {
		
		if(list == null || list.size() == 0 || idx > list.size() || list.get(idx).equals("#")) {
			return null;
		}

		int data = Integer.parseInt(list.get(idx));
		TreeNode node = new TreeNode(data);
		idx = idx + 1;
		
		node.left = deserialize(list);
		idx = idx + 1;
		
		node.right = deserialize(list);

		return node;
		
	} 

}
