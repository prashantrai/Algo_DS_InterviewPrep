package LinkedIn;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TreeMerger {

	// Test & Dry Run
    public static void main(String[] args) {
        TreeNode t1 = new TreeNode("Root", 10);
        TreeNode a1 = new TreeNode("A", 2);
        a1.addChild(new TreeNode("L", 2));
        TreeNode b1 = new TreeNode("B", 2);
        b1.addChild(new TreeNode("L", 2));
        TreeNode m1 = new TreeNode("M", 6);
        t1.addChild(a1);
        t1.addChild(b1);
        t1.addChild(m1);

        TreeNode t2 = new TreeNode("Root", 13);
        TreeNode a2 = new TreeNode("A", 4);
        a2.addChild(new TreeNode("L", 4));
        TreeNode b2 = new TreeNode("B", 3);
        b2.addChild(new TreeNode("L", 0)); // Leaf with 0 value
        TreeNode d2 = new TreeNode("D", 6);
        TreeNode m2 = new TreeNode("M", 6);
        d2.addChild(m2);
        t2.addChild(a2);
        t2.addChild(b2);
        t2.addChild(d2);

        TreeNode merged = merge(t1, t2);

        System.out.println("Merged Tree:");
        merged.print("");
    }
    
    
    /*
     Algorithm: Step-by-Step
		
	 1. Create a TreeNode class with:
		- String key
		- int value
		- Map<String, TreeNode> children to map unique 
			child keys to child nodes (unordered).
		
	 2. Recursive Merge Function:
		- If one of the nodes is null, return the other.
		- Create a new merged node with:
		   -- key = same as input nodes.
		   -- value = sum of the values of the two input nodes.
		
		- For all keys in both children maps:
		  -- Recursively merge child nodes.
		
	 3. Update Node Value:
		- After merging children, recalculate the merged node’s value as:
		  -- own value = sum of all merged children's 
		     values + original value from inputs.
     * */
    
    
//    Time Complexity: O(n + m) — Each node is visited once.
//
//    Space Complexity: O(n + m) — Space for merged tree.

	public static TreeNode merge(TreeNode t1, TreeNode t2) {
		if(t1 == null) return cloneTree(t2);
		if(t2 == null) return cloneTree(t1);
		
		
		// Both t1 and t2 represent the same logical node 
		// in their respective trees.
		// Therefore, t1.key == t2.key.
		if(!t1.key.equals(t2.key)) {
			throw new IllegalArgumentException("Mismatched keys: " + t1.key + " vs " + t2.key);
		}
		
		TreeNode merged = new TreeNode(t1.key, 0);
		
		// Union of child keys
		Set<String> allKeys = new HashSet<>();
		allKeys.addAll(t1.children.keySet());
		allKeys.addAll(t2.children.keySet());
		
		for(String key : allKeys) {
			TreeNode child1 = t1.children.get(key);
			TreeNode child2 = t2.children.get(key);
			TreeNode mergedChild = merge(child1, child2);
			merged.addChild(mergedChild);
		}
		
		// Set correct value = own value + sum of all children
		
		// with Lambda - works
		// int childrenSum = merged.children.values().stream().mapToInt(c -> c.value).sum();
		
		int childrenSum = 0;
		for (TreeNode child : merged.children.values()) {
		    childrenSum += child.value;
		}
		
		int selfValue = getSelfValue(t1) + getSelfValue(t2);
		
		merged.value = selfValue + childrenSum;
		
		return merged;
		
	}
	
	// This method helps you extract the node's own value (excluding its children's values).
	/*
	 Here’s how the values are structured:
		"A" has: value = 6
			A's One child "L" with value = 4

		So we expect "A"’s self value = 6 - 4 = 2
	 * */
	private static int getSelfValue(TreeNode node) {
		if(node == null) return 0;
		
		// with Lambda - works
		// int childSum = node.children.values().stream().mapToInt(c -> c.value).sum();
		
		int childSum = 0;
	    for (TreeNode child : node.children.values()) {
	        childSum += child.value;
	    }
		
		return node.value - childSum;
	}
	
	// This method deep-copies a tree node and all its children recursively.
	/*
	 What’s the problem with not cloning?
		Let’s say we're merging t1 and t2, and return t2 directly (without cloning).
		Later, if you modify the merged tree:
		You will accidentally modify t2, because both the merged tree and t2 share the same objects in memory.
		That breaks data isolation and introduces side effects.
	 * */
	private static TreeNode cloneTree(TreeNode node) {
		if(node == null) return null;
		
		TreeNode newNode = new TreeNode(node.key, node.value);
		for(TreeNode child : node.children.values()) {
			newNode.addChild(cloneTree(child));
		}
		
		return newNode;
	}
	
	
	static class TreeNode {
		String key;
		int value;
		Map<String, TreeNode> children;
		
		TreeNode(String key, int value) {
			this.key = key;
			this.value = value;
			children = new HashMap<>();
		}
		
		void addChild(TreeNode child) {
			children.put(child.key, child);
		}
		
		// Utility to print tree for debugging
	    void print(String indent) {
	        System.out.println(indent + key + ":" + value);
	        for (TreeNode child : children.values()) {
	            child.print(indent + "  ");
	        }
	    }
		
	} 
	
	
}

/*
 Tree has keys and values, values represent sum of this and child node values
When merging - absent branches should be created, and values for existing branches are summed
Keys are unique among child nodes of a single parent
Child nodes could be stored in any order
// #     Root:10
// #     /  |  \
// #    A:2 B:2 M:6 
// #     |  |
// #    L:2 L:2

// # Target tree T

// #      Root:13
// #     /  |   \
// #    A:4 B:3 D:6
// #    /        \
// #.  L:4        M:6

// # Result tree RT

// #          Root:23
// #      /     |   \    \
// #    A:6    B:5   D:6  M:6 
// #    /       |     |
// #   L:6     L:2   M:6 
 * */
