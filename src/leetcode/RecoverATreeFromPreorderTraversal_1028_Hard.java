package leetcode;

import java.util.Stack;

public class RecoverATreeFromPreorderTraversal_1028_Hard {

	public static void main(String[] args) {
		String S = "1-2--3--4-5--6--7";
		TreeNode root = recoverFromPreorder(S);
		
		String S2 = "1-401--349---90--88-2";
		TreeNode root2 = recoverFromPreorder(S);
	}
	
	
	/*
	 * https://leetcode.com/problems/recover-a-tree-from-preorder-traversal/
	 * https://leetcode.com/problems/recover-a-tree-from-preorder-traversal/submissions/
	 * 
	 * Complexity:
	 *     	Time O(S), Space O(N)
	 *     
	 *     
	 * https://leetcode.com/problems/recover-a-tree-from-preorder-traversal/discuss/274621/JavaC%2B%2BPython-Iterative-Stack-Solution
	 * 
	 * */
	
	// iterative
    public static TreeNode recoverFromPreorder(String S) {
        
        int val;
        int level;
        Stack<TreeNode> stack = new Stack<>();
        for(int i=0; i<S.length();) {
            
            // Calculate level
            for(level=0; S.charAt(i) == '-'; i++) {
                level++;
            }
            // Calculate value
            int value;
            for(val=0; i<S.length() && S.charAt(i) != '-'; i++) {
                val = val*10 + (S.charAt(i) - '0');
            }
            
            while (stack.size() > level) {
                stack.pop();
            }
            
            TreeNode node = new TreeNode(val);
            if(!stack.isEmpty()) {
                if(stack.peek().left == null) {
                    stack.peek().left = node;
                } else {
                    stack.peek().right = node;
                }
            }
            stack.push(node);
        }
        
        while (stack.size() > 1) stack.pop();
        
        return stack.pop();
    }

}
