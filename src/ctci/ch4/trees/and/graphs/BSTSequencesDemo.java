package ctci.ch4.trees.and.graphs;

import java.util.ArrayList;
import java.util.LinkedList;


public class BSTSequencesDemo {

	public static void main(String[] args) {

		TreeNode tree =  new TreeNode(2);
		tree.insertInOrder(1);
		tree.insertInOrder(3);

		
		/*TreeNode tree =  new TreeNode(12);
		tree.insertInOrder(7);
		tree.insertInOrder(8);
		tree.insertInOrder(6);
		tree.insertInOrder(4);
		tree.insertInOrder(16);
		tree.insertInOrder(13);
		tree.insertInOrder(15);
		tree.insertInOrder(17);
		tree.insertInOrder(18);*/
		
		System.out.println(">>>Result:: "+allSequeces(tree));
	}
	
	
	public static ArrayList<LinkedList<Integer>> allSequeces(TreeNode node) {
		
		ArrayList<LinkedList<Integer>> result = new ArrayList<LinkedList<Integer>>();
		if(node == null) {
			result.add(new LinkedList<Integer>());
			return result;
		}
		
		LinkedList<Integer> prefix =  new LinkedList<Integer>();
		prefix.add(node.data);
		
		//--recurse through left and right sub trees
		ArrayList<LinkedList<Integer>> leftSeq  = allSequeces(node.left);
		ArrayList<LinkedList<Integer>> rightSeq = allSequeces(node.right);
		
		
		for(LinkedList<Integer> left : leftSeq) {
			for(LinkedList<Integer> right : rightSeq) {
				ArrayList<LinkedList<Integer>> wavedList = new ArrayList<LinkedList<Integer>>();
//				LinkedList<Integer> wavedList = new LinkedList<Integer>();

				System.out.println("left=> "+left);
				System.out.println("right=> "+right);
				//--wave the lists
				result = waved(left, right, wavedList, prefix);
			
			}
		}
		return result;
	}


	private static ArrayList<LinkedList<Integer>> waved(LinkedList<Integer> left, LinkedList<Integer> right, ArrayList<LinkedList<Integer>> results,
			LinkedList<Integer> prefix) {
		
		if(left.size() == 0 || right.size() == 0) {
		
			//--clone the prefix
			LinkedList<Integer> result = (LinkedList<Integer>) prefix.clone();
			result.addAll(left);
			result.addAll(right);
			results.add(result);
			return results;
		}
		
		//--recurse through left list
		int headFirst = left.removeFirst();  //--removing first element from list to add that as a part of prefix to create a resultlist for left elements
		prefix.addLast(headFirst);
		results = waved(left, right, results, prefix);
		
		//--revert the elements to their previous position
		left.addFirst(headFirst);
		prefix.removeLast();
		
		//--recurse through right list
		int headSecond = right.removeFirst();
		prefix.addLast(headSecond);
		results = waved(left, right, results, prefix);
		
		//--revert the elements to their previous position
		right.addFirst(headSecond);
		prefix.removeLast();
		
		
		return results;
		
	}
	
	

}
