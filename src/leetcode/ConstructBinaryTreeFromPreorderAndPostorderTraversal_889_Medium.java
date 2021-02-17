package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

public class ConstructBinaryTreeFromPreorderAndPostorderTraversal_889_Medium {

	public static void main(String[] args) {

		int[] pre = {1,2,4,5,3,6,7}; 
		int[] post = {4,5,2,6,7,3,1};
//		TreeNode root = constructFromPrePost(pre, post);
		TreeNode root2 = constructFromPrePost2(pre, post);
	}

	
	/* Reference: https://leetcode.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/discuss/161268/C%2B%2BJavaPython-One-Pass-Real-O(N)
	 Time and space : O(N)
   */
   
   private static  int preIndex = 0, postIndex = 0;
   public static TreeNode constructFromPrePost(int[] pre, int[] post) {
       TreeNode root = new TreeNode(pre[preIndex++]);
      /* if pre value doesn't match the post value then call recursively 
       * if matched means that node has been covered already from pre and it's already part of the tree
       */
       if(root.val != post[postIndex]) { 
           root.left = constructFromPrePost(pre, post);
       }
       if(root.val != post[postIndex]) {
           root.right = constructFromPrePost(pre, post);
       }
       postIndex++;
       
       return root;
   }
	
	
	// Reference: https://leetcode.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/discuss/161268/C%2B%2BJavaPython-One-Pass-Real-O(N)
	// Iterative
	// Time and space : O(N)
   /*
    * For Some detail explanation refer below (but not really required): 
    * https://leetcode.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/discuss/821268/C-Construct-tree-using-preorder-traversal-first
    * */
   
    public static TreeNode constructFromPrePost2(int[] pre, int[] post) {
        
        Deque<TreeNode> q = new ArrayDeque<>();
        q.offer(new TreeNode(pre[0])); //root node
        
        int j = 0; //pointer for post[]
        for(int i=1; i<pre.length; i++) {
            TreeNode node = new TreeNode(pre[i]);
            while(q.getLast().val == post[j]) {
                q.pollLast();
                j++;
            }
            
            if(q.getLast().left == null) {
                q.getLast().left = node;
            } else {
                q.getLast().right = node;
            }
            q.offer(node);
        }
        
        return q.getFirst();
    }
    
    /**
	 * Definition for a binary tree node.
	 */
	  private static class TreeNode {
	     int val;
	     TreeNode left;
	     TreeNode right;
	     TreeNode() {}
	     TreeNode(int val) { this.val = val; }
	     TreeNode(int val, TreeNode left, TreeNode right) {
	    	 this.val = val;
	         this.left = left;
	         this.right = right;
	     }
	  }
}
