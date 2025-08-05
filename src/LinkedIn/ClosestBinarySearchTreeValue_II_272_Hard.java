package LinkedIn;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class ClosestBinarySearchTreeValue_II_272_Hard {

	public static void main(String[] args) {

	}
	
	
	/* Approach: binary search
    Time: O(N)
    Space: O(n), Both arr and the recursion call stack use O(n) space.
    */
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        List<Integer> list = new ArrayList<>();
        inorder(root, list); //dfs
        
        int left = 0;
        int right = list.size() - k;

        while (left < right) {
            int mid = left + (right - left)/2;

            if(Math.abs(target - list.get(mid+k)) < Math.abs(target - list.get(mid))) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return list.subList(left, left+k);
    }

    // inorder traversal will add value in list in sorted order
    private void inorder(TreeNode node, List<Integer> list) {
        if (node == null) return;
        
        inorder(node.left, list);
        list.add(node.val);
        inorder(node.right, list);
    }
    

    /* Approach: Build The Window With Deque 
    Time: O(N)
    Space: O(n+k), We use O(n) space for the recursion call stack 
    and O(k) space for queue.
    */

    public List<Integer> closestKValues_Dqueue(TreeNode root, double target, int k) {
        Deque<Integer> dq = new ArrayDeque<>();
        dfs(root, dq, target, k);
        return new ArrayList<>(dq);
    }

    private void dfs(TreeNode node, Deque<Integer> dq,  double target, int k) {

        if(node == null) return;
        dfs(node.left, dq, target, k);
        dq.add(node.val);
        if(dq.size() > k) {
            if (Math.abs(dq.peekFirst() - target) <= Math.abs(dq.peekLast() - target)) {
                dq.removeLast();
            } else {
                dq.removeFirst();
            }
        }

        dfs(node.right, dq, target, k);

    }
    
    /* Approach: Inorder + Sliding
    1. Inorder Traversal: We first perform an inorder traversal of the BST 
    to get all node values in sorted order. This takes O(N) time 
    and O(N) space.

    2. Sliding Window: Using two pointers (left and right), we adjust the 
    window size to include only the k closest values to the target. The 
    comparison is based on the absolute difference from the target.

    3. Result Extraction: The sublist from left to right gives the k 
    closest values.

    Time and Sapce: O(N)
    */
    public List<Integer> closestKValues_TwoPointers(TreeNode root, double target, int k) {
        List<Integer> sortedValues = new ArrayList<>();
        // Step 1: Get sorted list via inorder traversal
        inorderTraversal(root, sortedValues);

        int left = 0, right = sortedValues.size()-1;
        // Step 2: Use sliding window to find k closest elements
        while (right - left + 1 > k) {
            if (Math.abs(sortedValues.get(left) - target) 
                > Math.abs(sortedValues.get(right) - target)) {
                left++;
            } else {
                right--;
            }
        }
        // Step 3: Return the sublist from left to right
        return sortedValues.subList(left, right+1);

    }

    private void inorderTraversal(TreeNode node, List<Integer> sortedValues) {
        if(node == null) return;
        inorderTraversal(node.left, sortedValues);
        sortedValues.add(node.val);
        inorderTraversal(node.right, sortedValues);

    }
    
    /* Approach: Using maxHeap/PriorityQueue
    
    Time: O(n log k), A heap operation's cost is a function of the size
     of the heap. We are limiting the size of our heap to k, so 
     heap operations will cost O(logk).
     We visit each node once. At each node, we perform up to two heap
     operations. Therefore, we perform a maximum of 2n heap operations,
     giving us a time complexity of O(n⋅logk).

     Space: O(k+n), We need O(n) space for the recursion call stack, 
     and O(k) space for the heap.
    */
    public List<Integer> closestKValues_PQ(TreeNode root, double target, int k) {
        
        /*
        The heap is initialized with a custom comparator that orders 
        elements based on their absolute difference from the target. 
        The comparator returns -1 if a has a larger difference than b, 
        which means a will have higher priority (max-heap).
        */
        Queue<Integer> maxHeap = new PriorityQueue<>((a, b)->Math.abs(a - target) > Math.abs(b - target) ? -1 : 1);

        dfs(root, maxHeap, k);

        return new ArrayList<>(maxHeap);
    }

    private void dfs(TreeNode node, Queue<Integer> maxHeap, int k) {
        if(node == null) return;

        maxHeap.add(node.val);
        if(maxHeap.size() > k) maxHeap.remove();

        dfs(node.left, maxHeap, k);
        dfs(node.right, maxHeap, k);
    }
    
    
    
    // TreeNode
    static class TreeNode {
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
