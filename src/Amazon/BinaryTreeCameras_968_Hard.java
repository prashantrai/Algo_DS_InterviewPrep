package Amazon;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTreeCameras_968_Hard {

	public static void main(String[] args) {

        runTest(new Integer[]{0},
                1, "Single node");

        runTest(new Integer[]{0, 0, null, 0, 0},
                1, "Example 1: [0,0,null,0,0]");

        runTest(new Integer[]{0, 0, null, 0, null, 0, null, null, 0},
                2, "Example 2: [0,0,null,0,null,0,null,null,0]");

        runTest(new Integer[]{0, null, 0, null, 0, null, 0},
                2, "Right-skewed chain");

        runTest(new Integer[]{0, 0, 0, 0, 0, 0, 0},
                2, "Full binary tree");
        
        // Custom tree:
        //       1
        //      /
        //     2
        //    / \
        //   3   4
        //    \
        //    14
        //      \
        //      15
        runTest(new Integer[]{1, 2, null, 3, 4, null, 14, null, null, null, 15},
                2, "Custom tree: 1-2-3-4-14-15");
    }

    private static void runTest(Integer[] arr,
                                int expected, String testName) {
    	cameras = 0; ans = 0; // reset camera count before each test
        TreeNode root = buildTree(arr);
        int result = minCameraCover(root);
        System.out.printf("%s -> result = %d, expected = %d%n",
                testName, result, expected);
    }

    /**
     * Build a binary tree from level-order array where null means no node.
     * Example: [0,0,null,0,0] corresponds to:
     *      0
     *     /
     *    0
     *   / \
     *  0   0
     */
    private static TreeNode buildTree(Integer[] arr) {
        if (arr == null || arr.length == 0 || arr[0] == null) return null;

        TreeNode root = new TreeNode(arr[0]);
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int i = 1;

        while (!q.isEmpty() && i < arr.length) {
            TreeNode curr = q.poll();

            // left child
            if (i < arr.length && arr[i] != null) {
                curr.left = new TreeNode(arr[i]);
                q.offer(curr.left);
            }
            i++;

            // right child
            if (i < arr.length && arr[i] != null) {
                curr.right = new TreeNode(arr[i]);
                q.offer(curr.right);
            }
            i++;
        }
        return root;
    }
    
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val) { this.val = val; }
    }
    
	
	/* How to think about this problem:: 
    
    A camera at a node covers:
    its parent
    itself
    its children
    We want minimum cameras.
    This is a tree DP / greedy from bottom to top problem:
    Decide about a node after you know what happens in its children.
    Key idea:
    For each node, we don’t care where exactly all cameras are, just its state:

    Let each node return one of three states to its parent:

    0 = NOT_COVERED (this node is not covered by any camera)
    1 = HAS_CAMERA (this node has a camera)
    2 = COVERED (this node is covered by a child’s camera, but has no camera itself)
    2. Local decisions (core greedy idea)
    For a node x, look at left and right child states:

    If any child is NOT_COVERED (0)
    → x must place a camera to cover them
    → state(x) = HAS_CAMERA (1), cameraCount++

    Else if at least one child HAS_CAMERA (1)
    → x is already covered by that camera
    → state(x) = COVERED (2)

    Else (both children are COVERED (2) or null)
    → x is not covered yet, and no child has a camera
    → state(x) = NOT_COVERED (0) and let parent decide

    At the end, if the root is NOT_COVERED, we need one more camera at root.
	*/
	
	/* Algorithm steps
	    Use a post-order DFS (left → right → node).
	    For null nodes:
	        Return COVERED (2) so you don’t place cameras on leaves just because of nulls.
	    For each real node:
	        Recursively get leftState, rightState.
	        Apply the rules above to compute this node’s state and possibly increment camera count.
	    After DFS on root:
	        If rootState == NOT_COVERED, add one more camera.
	    
	    Return total camera count.
	*/
	
	/* Short interview script (how you’d explain to an interviewer)
	    
	    “I use a post-order DFS and classify each node into 3 states:
	        0: not covered
	        1: has a camera
	        2: covered but no camera.
	
	    For null nodes I return ‘covered’ so they don’t force cameras.”
	    
	    “At each node, after processing children:
	        If any child is not covered, I put a camera here.
	        Else if any child has a camera, this node is covered.
	        Else it’s not covered and I let the parent handle it.”
	    
	    “After DFS, if the root is not covered I add one more camera.”
	    
	    “This is greedy from bottom to top, each node visited once: O(n) time, O(h) space for recursion stack.”
	*/
	
	/* Complexity:: 
	    Time: Each node visited once → O(n)
	    Space:
	    Recursion stack depth O(h) (h = tree height) → O(h)
	    Worst case skewed tree: O(n)
	*/
	
	// States:
	// 0 = NOT_COVERED
	// 1 = HAS_CAMERA
	// 2 = COVERED (no camera here, but covered by child)
	private static final int NOT_COVERED = 0;
	private static final int HAS_CAMERA = 1;
	private static final int COVERED = 2;
	
	private static int cameras = 0;
	
	public static int minCameraCover(TreeNode root) {
	    int rootState = dfs(root);
	
	    // If root is not covered, place one more camera at root
	    if(rootState == NOT_COVERED) {
	        cameras++;
	    }
	    return cameras;
	}
	
	private static int dfs (TreeNode root) {
	    if(root == null) {
	        // Null nodes are treated as covered to avoid extra cameras
	        return COVERED;
	    }
	
	    int leftState = dfs(root.left);
	    int rightState = dfs(root.right);
	
	    // If any child is NOT_COVERED, we must place a camera here
	    if (leftState == NOT_COVERED || rightState == NOT_COVERED) {
	        cameras++;
	        return HAS_CAMERA;
	    }
	
	    // If any child HAS_CAMERA, this node is covered
	    if (leftState == HAS_CAMERA || rightState == HAS_CAMERA) {
	        return COVERED;
	    }
	
	    // Otherwise, both children are COVERED but have no camera,
	    // so this node is currently NOT_COVERED
	    return NOT_COVERED;
	
	}
	
	
	
	/** 
	Intuition:
	Consider a node in the tree.
	It can be covered by its parent, itself, its two children.
	Four options.
	
	Consider the root of the tree.
	It can be covered by left child, or right child, or itself.
	Three options.
	
	Consider one leaf of the tree.
	It can be covered by its parent or by itself.
	Two options.
	
	If we set a camera at the leaf, the camera can cover the leaf and its parent.
	If we set a camera at its parent, the camera can cover the leaf, its parent and its sibling.
	
	We can see that the second plan is always better than the first.
	Now we have only one option, set up camera to all leaves' parent.
	
	Here is our greedy solution:
	- Set cameras on all leaves' parents, thenremove all covered nodes.
	- Repeat step 1 until all nodes are covered.
	
	Algo (dfs): 
	Apply a recusion function dfs.
	Return 0 if it's a leaf.
	Return 1 if it's a parent of a leaf, with a camera on this node.
	Return 2 if it's coverd, without a camera on this node.
	
	For each node,
	if it has a child, which is leaf (node 0), then it needs camera.
	if it has a child, which is the parent of a leaf (node 1), then it's covered.
	
	If it needs camera, then res++ and we return 1.
	If it's covered, we return 2.
	Otherwise, we return 0.
	*/
	
	/* 
	Time Complexity: O(N), where N is the number of nodes in the tree.
	Space Complexity: O(H), where H is the height of the tree.
	*/
	
	// States:
	// 0 = NOT_COVERED
	// 1 = HAS_CAMERA
	// 2 = COVERED (no camera here, but covered by child)
	
	static int ans;
	public static int minCameraCover2(TreeNode root) {
	    return (dfs(root) < 1 ? 1 : 0) + ans;
	}
	
	private static int dfs2 (TreeNode root) {
	    if(root == null) 
	        // Return 2 it's leaf, coverd without any camera.
	        return 2; 
	
	    int left = dfs(root.left);
	    int right = dfs(root.right);
	
	    if(left == 0 || right == 0) {
	        // If any child is NOT COVERED, we must place a camera here
	        ans++;
	        return 1;
	    }
	    return left == 1 || right == 1 ? 2 : 0;
	}

}
