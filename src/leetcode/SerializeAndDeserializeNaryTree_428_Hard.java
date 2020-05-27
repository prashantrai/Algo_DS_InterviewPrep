package leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SerializeAndDeserializeNaryTree_428_Hard {

	public static void main(String[] args) {
		
		Node root = new Node(1, new ArrayList<Node>());
		Node node3 = new Node(3, new ArrayList<Node>());
		Node node2 = new Node(2, new ArrayList<Node>());
		Node node4 = new Node(4, new ArrayList<Node>());
		Node node5 = new Node(5, new ArrayList<Node>());
		Node node6 = new Node(6, new ArrayList<Node>());

		root.addChildren(node3, node2, node4); //varargs
		node3.addChildren(node5, node6);	//varargs
		
		//--loooks good
		Codec codec = new Codec();
		Node node = codec.deserialize(codec.serialize(root));
		System.out.println("RES:: "+node);
		/*Node [val=1, 
		 * 	   children=[
		 * 				Node [val=3, 
		 * 					  children=[
		 * 								Node [val=5, children=[]], 
		 * 								Node [val=6, children=[]]]
		 * 				], 
		 * 				Node [val=2, children=[]], 
		 * 				Node [val=4, children=[]]
		 * 			]
		 * 		]
		 */
		
	}
	
	/* It's a Leetcode premium question, so not accessible 
	 * 
	 * Question: https://www.lintcode.com/problem/serialize-and-deserialize-n-ary-tree/description
	 * 
	 * http://shibaili.blogspot.com/2018/11/428-serialize-and-deserialize-n-ary-tree.html
	 * https://www.cnblogs.com/Dylan-Java-NYC/p/11056027.html
	 * 
	 * 3-ary::
	 					1
	 				  / | \
	 				 3  2  4
	 				/ \
	               5   6
	  
	  		Input：{1,3,2,4#2#3,5,6#4#5#6}
			Output：{1,3,2,4#2#3,5,6#4#5#6}
	 */
	
	/* Time Complexity: serialize, O(n). deserialize, O(n).
	 * Space: O(n) 
	 */
	
	private static class Codec {
		
		// Encodes a tree to a single string.
		public static String serialize(Node root) {
			if(root == null) return "";
			
			Deque<Node> dq = new ArrayDeque<>();
			dq.offer(root);
			
			StringBuilder sb = new StringBuilder();
			sb.append(root.val);
			sb.append(",#,"); // adding comma as delemeter to split durng deserialization 
			
			while (!dq.isEmpty()) {
				Node node = dq.poll();
				
				for(Node child : node.children) {
					sb.append(child.val);
					sb.append(",");
					dq.offer(child);
				}
				sb.append("#,");
			}
			
			System.out.println("serialize:: "+ sb);
			return sb.toString();
		}
		
		// Decodes your encoded data to tree.
		public static Node deserialize(String data) {
			if (data.length() == 0) return null;
	        String[] s = data.split(",");
	        
	        Deque<Node> dq = new ArrayDeque<>();
	        // create root node
	        Node root = new Node(Integer.parseInt(s[0]), new ArrayList<Node>());
	        dq.offer(root);
	        
	        int i = 1; // count for string array
	        
	        while (!dq.isEmpty()) {
	        	Node node = dq.poll();
	        	i++; // i == 0 is root and i == 1 is '#', that's why increment by one to get the child
	        	while (!s[i].equals("#")) {
	        		Node child = new Node(Integer.parseInt(s[i]), new ArrayList<Node>());
	        		node.children.add(child);
	        		dq.offer(child);
	        		i++;
	        	}
	        }
	        return root;
		}
	}// Codec closed
	
	private static class Node {
		int val;
		List<Node> children;

		public Node(int val, List<Node> children) {			
			this.val = val;
			this.children = children;
		}
		
		public void addChildren(Node ...nodes) {
			for(Node child : nodes) {
				children.add(child);
			}
		}

		@Override
		public String toString() {
			return "Node [val=" + val + ", children=" + children + "]";
		}
	}

}
