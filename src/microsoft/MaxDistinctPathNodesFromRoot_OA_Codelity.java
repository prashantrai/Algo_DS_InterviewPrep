package microsoft;

import java.util.HashSet;
import java.util.Set;

public class MaxDistinctPathNodesFromRoot_OA_Codelity {

	public static void main(String[] args) {
		
		Node root = new Node(1);
	    root.left = new Node(2);
	    root.right = new Node(3);
	    root.left.left = new Node(4);
	    root.left.right = new Node(5);
	    root.right.left = new Node(6);
	    root.right.right = new Node(7);
	    root.right.left.right = new Node(8);
	    root.right.right.right = new Node(9);
	    
	    int res = traverse(root, new HashSet<Integer>());
	    System.out.println("Expected: 4, Actual: " + res);

	}
	/*
	Find the number of nodes on the longest distinct path that starts at the root in a binary tree.
	Question: https://leetcode.com/discuss/interview-question/1065005/Max-Number-of-Distinct-Notes-along-a-Root-Node-Path/850343
	
	Also screen shot saved in GoogleDrive: LeetCode_Premium/Online Assessments/Microsoft_Online Test _Codelity/5922756e-0683-4863-8050-0e74d2b01160_1613291010.857781.png
	*/
	
	private static int traverse(Node root, Set<Integer> mSet) {
		if(root == null || mSet.contains(root.data)) 
			return mSet.size();
		
		// add root to set
		mSet.add(root.data);
		
		int l = traverse(root.left, mSet);
		int r = traverse(root.right, mSet);
		
		//Backtrack and remove the element from the set
        mSet.remove(root.data);

        //return the max path
        return Math.max(l, r);
	}
	
	
	private static class Node {
		int data;
		Node left, right;
		public Node() {}
		public Node(int data) { this.data = data; }
	}
	
}
