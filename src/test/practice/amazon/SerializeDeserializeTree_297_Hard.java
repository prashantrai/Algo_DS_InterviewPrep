package test.practice.amazon;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class SerializeDeserializeTree_297_Hard {

	/***
	 * https://leetcode.com/problems/serialize-and-deserialize-binary-tree/ 
	 * https://leetcode.com/problems/serialize-and-deserialize-binary-tree/discuss/502539/Java-(Level-order)
	 * https://leetcode.com/problems/serialize-and-deserialize-binary-tree/discuss/502021/Share-My-Level-Order-Traversal-Java-Solution
	 * My submisson:: https://leetcode.com/problems/serialize-and-deserialize-binary-tree/submissions/
	 * 
	 * You may serialize the following tree:

			    1
			   / \
			  2   3
			     / \
			    4   5

		as "[1,2,3,null,null,4,5]"
	 * 
	 */
	
	public static void main(String[] args) {
		TreeNode tree =  new TreeNode(20);
		tree.insertInOrder(10);
		tree.insertInOrder(25);
		tree.insertInOrder(5);
		tree.insertInOrder(15);
		tree.insertInOrder(24);
		tree.insertInOrder(28);
		
		
		String serilaized = serialize(tree); 
		System.out.println("After serialization:   ["+serilaized+"]");
		
		TreeNode node = deserialize(serilaized); //--looks good
		
		//--to test: re-serializing tree after desrailize, if the result is same then Tree constructed correctly
		System.out.println("After deserialization: ["+ serialize(node)+"]"); 

	}
	
	//--bfs - level order traversal
	public static String serialize (TreeNode root) {
		if(root == null) 
			return "";
		
		Queue<TreeNode> q = new LinkedList<>();
		StringBuilder sb = new StringBuilder();
		q.offer(root);
		
		while(!q.isEmpty()) {
			TreeNode node = q.poll();
			if(node == null) {
				sb.append("null");
			} else {
				sb.append(node.data);
				q.offer(node.left);
				q.offer(node.right);
			}
			sb.append(",");
		}
		
		return sb.toString().substring(0, sb.length()-1); //--removing comma at the end using substring
		
	}
	
	public static TreeNode deserialize (String serilaized) {
		if(serilaized == null || serilaized.length() == 0) {
			return null;
		}
		
		String[] arr = serilaized.split(",");
		
		if(arr[0].equals("null")) return null;
		
		Queue<TreeNode> q = new LinkedList<>();
		TreeNode root = new TreeNode(Integer.parseInt(arr[0]));
		q.offer(root);
		int idx = 1;
		
		while (!q.isEmpty() && idx < arr.length) {
			TreeNode node = q.poll();
			String s = arr[idx];
			
			if(!s.equals("null")) {
				TreeNode leftNode = new TreeNode(Integer.parseInt(s));
				node.left = leftNode;
				q.offer(leftNode);
			}
			
			idx++;
			
			if(idx > arr.length) 
				break;
			
			s = arr[idx];
			
			if(!s.equals("null")) {
				TreeNode rightNode = new TreeNode(Integer.parseInt(s));
				node.right = rightNode;
				q.offer(rightNode);
			}
			idx++;
		}
		
		return root;
		
	} 

}