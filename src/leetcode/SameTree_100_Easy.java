package leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class SameTree_100_Easy {

	/* https://leetcode.com/problems/same-tree/
	 Given two binary trees, write a function to check if they are the same or not.

		Two binary trees are considered the same if they are structurally identical and the nodes have the same value.
		
		Example 1:
		
		Input:     1         1
		          / \       / \
		         2   3     2   3
		
		        [1,2,3],   [1,2,3]
		
		Output: true
		Example 2:
		
		Input:     1         1
		          /           \
		         2             2
		
		        [1,2],     [1,null,2]
		
		Output: false
		Example 3:
		
		Input:     1         1
		          / \       / \
		         2   1     1   2
		
		        [1,2,1],   [1,1,2]
		
		Output: false 
	 * */
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	//--BFS
	
	//--https://leetcode.com/problems/same-tree/solution/
	public boolean isSameTree(TreeNode p, TreeNode q) {
        Queue<TreeNode> queue = new LinkedList<>();
        if(p == null && q == null) return true;
        if(p == null || q == null) return false;
        
        if(p != null && q != null) {
            queue.offer(p);
            queue.offer(q);            
        }
        
        while(!queue.isEmpty()) {
            TreeNode first  = queue.poll();
            TreeNode second = queue.poll();
            if(first == null && second == null) continue;
            if(first == null || second == null) return false;
            if(first.data != second.data) return false;
            
            //--add left in queue
            queue.offer(first.left);
            queue.offer(second.left);
            
            //--add right in queue
            queue.offer(first.right);
            queue.offer(second.right);
        }
        return true;    
    }

}
