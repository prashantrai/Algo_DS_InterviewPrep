package Vmware;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class AllNodesDistanceKInBinaryTree_863_Medium {

	public static void main(String[] args) {
		
		TreeNode node_0 = new TreeNode(0);
		
		//TreeNode node_44 = new TreeNode(44);
		//TreeNode node_4 = new TreeNode(4, node_44, null);
		TreeNode node_4 = new TreeNode(4);
		
		TreeNode node_6 = new TreeNode(6);

		//TreeNode node_77 = new TreeNode(77);
		//TreeNode node_7 = new TreeNode(7, null, node_77);
		TreeNode node_7 = new TreeNode(7);
		
		TreeNode node_8 = new TreeNode(8);
		
		TreeNode node_2 = new TreeNode(2, node_7, node_4);
		TreeNode node_5 = new TreeNode(5, node_6, node_2);
		
		TreeNode node_1 = new TreeNode(1, node_0, node_8);
		
		TreeNode root = new TreeNode(3, node_5, node_1);
		
		int k = 2;
		TreeNode target = node_5;
		
		System.out.println("Expected: [7,4,1], Actual: " + distanceK(root, target, k));
		
	}

	
	/*
    1. This of it as a graph where you have to find all the neighbouring node
    K distance from the source/target node.
    
    2. Because it's a binary tree and we have parent nodes for source/target
    node. We have to fetch the parent for all the nodes (we can also change the TreeNode data structure here but it's not an option here). That's why we have 
    parentMap being populated in dfs().
    
    3. Once parentMap is created then start BFS from the target node till dist == K
    
    4. Add values in Q. When dist == K Q will have all the nodes from target at distance 
    dist. Copy the value from Q to result List and return.
    
    */
    //Time: O(N)
    //Space: O(N)
	// reference: https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/solution/438039
	
    static Map<TreeNode, TreeNode> parentMap; //store node and it's parent
    public static List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        parentMap = new HashMap<>();
        dfs(root, root);
        
        Queue<TreeNode> q = new ArrayDeque<>();
        q.offer(target);
        
        Set<TreeNode> seen = new HashSet<>();
        seen.add(target);
        
        int dist = 0;
        while(!q.isEmpty() && dist < k) {
            int size = q.size();
            dist++;
            for(int i=0; i<size; i++) {
                TreeNode node = q.poll();
                
                // add left child to q
                if(node.left != null && !seen.contains(node.left)) {
                    q.offer(node.left);
                    seen.add(node.left);
                }
                
                // add right child to q
                if(node.right != null && !seen.contains(node.right)) {
                    q.offer(node.right);
                    seen.add(node.right);
                }
                
                //check and get current node's parent and add that to q
                TreeNode parent = parentMap.get(node);
                if(!seen.contains(parent)) {
                    q.offer(parent);
                    seen.add(parent);
                }
            }
        }
        
        /*
        List<Integer> ans = new ArrayList<>();
        for (TreeNode n: queue)
            ans.add(n.val);
        return ans;
        */
        return q.stream()
                .map(n->n.val)
                .collect(Collectors.toList());
        
        
    }
    
    /*
    Here we mapping each Node with it's parent
    */
    private static void dfs(TreeNode node, TreeNode parent) {
        if(node != null) {
            parentMap.put(node, parent);
            dfs(node.left, node);
            dfs(node.right, node);
        }
    }
	
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
		@Override
		public String toString() {
			//return "[" + val + ", " + left + ", " + right + "]";
			return ""+val;
		}
	}
}
