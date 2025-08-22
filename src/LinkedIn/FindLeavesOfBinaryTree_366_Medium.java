package LinkedIn;

import java.util.ArrayList;
import java.util.List;

public class FindLeavesOfBinaryTree_366_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/*
    The key is to find the height of each node. Here the definition of height is:
    The height of a node is the number of edges from the node to the deepest leaf.

    The height of a node is also the its index in the result list (res). For 
    example, leaves, whose heights are 0, are stored in res[0]. Once we find the 
    height of a node, we can put it directly into the result.
     */
    /*
    Time Complexity: O(N), Assuming N is the total number of nodes in the binary 
    tree, traversing the tree takes O(N) time and storing all the pairs at the 
    correct position also takes O(N) time. Hence overall time complexity of this 
    approach is O(N).

    Space Complexity: O(N), the space used by the recursion call stack.
    */
    public List<List<Integer>> findLeaves(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        getHeight(root, res);
        return res;
    }
    private int getHeight(TreeNode node, List<List<Integer>> res) {
        if(node == null) return -1;

        // first calculate the height of the left and right children
        int leftHeight = getHeight(node.left, res);
        int rightHeight = getHeight(node.right, res);

        int curHeight = Math.max(leftHeight, rightHeight) + 1;

        // res size is same as curHeight then current level is covered, 
        // move to next level, add a new list in the res for next level.
        if(curHeight == res.size()) {
            res.add(new ArrayList<>());
        }

        res.get(curHeight).add(node.val);

        return curHeight;

    }
    
    
	static public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode() {}
		TreeNode(int val) {
			this.val = val;
		}

		TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}

}
