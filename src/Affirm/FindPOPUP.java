package Affirm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class FindPOPUP {

	public static void main(String[] args) {
		
		// TreeNode(String name, boolean hidden, List<TreeNode> children, TreeNode parent) 
		
		TreeNode root = new TreeNode("ROOT", false, new ArrayList<TreeNode>(), null);
		
		// 2nd level
		TreeNode B = new TreeNode("B", false, new ArrayList<TreeNode>(), root);
		TreeNode C = new TreeNode("C", false, new ArrayList<TreeNode>(), root);
		TreeNode D = new TreeNode("D", false, new ArrayList<TreeNode>(), root);
		
		// 3rd level
		TreeNode F = new TreeNode("F", false, new ArrayList<TreeNode>(), B);
		TreeNode G = new TreeNode("G", false, new ArrayList<TreeNode>(), B);
		TreeNode POPUP = new TreeNode("POPUP", true, new ArrayList<TreeNode>(), D);
		TreeNode I = new TreeNode("I", false, new ArrayList<TreeNode>(), D);
		TreeNode J = new TreeNode("J", false, new ArrayList<TreeNode>(), D);
		TreeNode K = new TreeNode("K", true, new ArrayList<TreeNode>(), D);

		// 4th level
		TreeNode N = new TreeNode("N", false, new ArrayList<TreeNode>(), POPUP);
		TreeNode O = new TreeNode("O", false, new ArrayList<TreeNode>(), POPUP);
		TreeNode P = new TreeNode("P", true,  new ArrayList<TreeNode>(), POPUP);
		TreeNode Z = new TreeNode("Z", false, new ArrayList<TreeNode>(), J);
		TreeNode Y = new TreeNode("Y", false, new ArrayList<TreeNode>(), J);
		
		root.children.add(B);
		root.children.add(C); // leaf node, no children
		root.children.add(D);
		
		B.children.add(F);
		B.children.add(G);
		
		D.children.add(POPUP);
		D.children.add(I);	// leaf node, no children
		D.children.add(J);
		D.children.add(K);	// leaf node, no children
		
		POPUP.children.add(N);
		POPUP.children.add(O);
		POPUP.children.add(P);
		
		J.children.add(Z); // leaf node, no children
		J.children.add(Y); // leaf node, no children
		
		TreeNode popupNode = findPOPUP(root);
		System.out.println("popupNode:: " + popupNode);
		
		// a. Find POPUP, make all the sibling of POPUP to hidden
		List<String> result = seekAndHide(popupNode);
		System.out.println("Expected: I, J, K  Actual: "+ result);
		
		// b. Find out POPUP's parent, make all the sibling of parent to hidden
		result = seekAndHide(popupNode.parent);
		System.out.println("Expected: B, C  Actual: "+ result);
		
		/* In the output diagram (see bottom of this file) after case 'a' and 'b' POPUP 
		 * is not hidden, that's why this method.
		 * */
		makePOPUPVisible(popupNode);

	}

	/* Find element/node name POPUP in a given tree (n-ary) and
	 * a. make all the sibling of POPUP node to hidden (in diagram hidden are in parenthesis)
	 * 		e.g. in diagram "After openPopup called" ::  POPUP's siblings I, J, K are marked 
	 *           as hidden and show inside parenthesis
	 *           
	 * b. Find POPUP's parent, make all the sibling of parent to hidden
	 * 		e.g. in diagram "After openPopup called" ::  Siblings of POPUP's parent B and C are marked hidden 
	 * 			an shown inside parenthesis in diagram.
	 *  
	 *  Initial State:
	 *
	 *              ROOT
	 *          /     |    \
	 *         /      |     \
	 *       B       C        D
	 *    /   |            /  | \  \
	 *   /    |           /   |  \  \
	 * F      G      (POPUP)  I  J  (K)
	 *              /   |   \   / \
	 *             /    |    \  Z  Y
	 *            N     O    (P)
	 * 
	 * 
	 * Algorithm: 
	 * 1. Create a data structure for Node and keep parent link as well in that
	 * 2. implement findPopup() and return POPUP node 
	 * 3. implement seekAndHideSiblings(Node node): this method will find all the siblings of input node 
	 *    and hide them 
	 * 
	 * 
	 * Complexity Overall: 
	 * 	Time: O(N) + O(V + E) i.e. O(N)
	 * 	Space: O(1) + O(N) i.e. O(N) 
	 * 
	 * 
	 * */

	// Time: O(N) 
	// Space: O(1), not count result list as that's just for debuggin purpose and won't be in actual code
	private static List<String> seekAndHide(TreeNode node) {
		TreeNode parent = node.parent;
		
		//this is just to store result and print and can be ignored in interview
		List<String> result = new ArrayList<>(); 
		
		for(TreeNode child: parent.children) {
			String childName = child.name; 
			if(!childName.equals(node.name)) {
				child.hidden = true;
				result.add(childName);
			}
		}
		return result;
	}
	
	// Time: O(V + E) 
	// Space : O(N)
	private static TreeNode findPOPUP(TreeNode root) { // BFS
		
		Set<String> visited = new HashSet<>();
		Queue<TreeNode> q = new ArrayDeque<>();
		
		q.offer(root);
		
		while(!q.isEmpty()) {
			TreeNode node = q.poll();
			
			if(visited.contains(node.name)) 
				continue;
			
			visited.add(node.name);
			
			if("POPUP".equals(node.name)) {
				return node;
			}
			
			for(TreeNode child: node.children) {
				q.offer(child);
			}
		}
		return null;
	}
	
	private static void makePOPUPVisible(TreeNode node) { //un-hide
		if(node.name.equals("POPUP") && node.hidden) {
			node.hidden = false;
		}
	}
	
	private static class TreeNode {
        String name;
        boolean hidden;
        List<TreeNode> children;
        TreeNode parent;

        public TreeNode() {}
        public TreeNode(String name, boolean hidden, List<TreeNode> children, TreeNode parent) {
            this.hidden = hidden;
            this.name = name;
            this.children = children;
            this.parent = parent;
        }
        
        /** NOTE: mention/implement below only when asked otherwise don't waste time */
        public int hashCode() {
			return name.hashCode()*3
					+ name.hashCode()*5;
		}
		public boolean equals(Object o) {
			TreeNode p = (TreeNode) o;
			return this.name == p.name;
		}
		public String toString() {
			return "(name=" + name 
					+", hidden=" + hidden 
					// ERROR: below is causing StackOverFlow as it's trying to print the entire Tree
					//+ ", children=" + children    
					+ ", parent=" + parent +")";
		}
    } 
	
	
}


/*https://leetcode.com/discuss/interview-question/1530392/affirm-phone-find-popup

	a. Find POPUP, make all the sibling of POPUP to hidden
	b. Find out POPUP's parent, make all the sibling of parent to hidden

	// Initial State:

	//              ROOT
	//          /     |    \
	//         /      |     \
	//       B       C        D
	//    /   |            /  | \  \
	//   /    |           /   |  \  \
	// F      G      (POPUP)  I  J  (K)
	//              /   |   \   / \
	//             /    |    \  Z  Y
	//            N     O    (P)
	
	
	// After openPopup called:
	
	//              ROOT
	//          /     |    \
	//         /      |     \
	//      (B)      (C)      D
	//    /   |            /  | \   \
	//   /    |           /   |  \   \
	// F      G       POPUP  (I)  (J) (K)
	//              /   |   \
	//             /    |    \
	//            N     O     (P)
	
	private static class DomNode {
			DomNode parent;
	        String id;
	        boolean hidden;
	        List<DomNode> children;
	
	        public DomNode(String id, boolean hidden, List<DomNode> children) {
	            this.hidden = hidden;
	            this.id = id;
	            this.children = children;
	        }
	    }
	    
*/
