package Square;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree_CharacterCount {

    public static void main(String[] args) {
        String input = "aabbbbbcDDD";
        
        // Part 1
        Map<Character, Integer> charCountMap = getCharacterCount(input);
        
        for (Map.Entry<Character, Integer> entry : charCountMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
        
        // Part 2
        TreeNode root = buildBinaryTree(charCountMap);
        System.out.println("Root: (" + root.character + ", " + root.count + ")");
        System.out.println("Left: (" + root.left.character + ", " + root.left.count + ")");
        System.out.println("Right: (" + root.right.character + ", " + root.right.count + ")");
        
        printTree(root, 0);
    }
    
    /* For question see bottom.
     * 
     * Overall: 
     * Time: O(n) for Part 1 and O(klogk) for Part 2.
     * Space: O(k) for both parts
     */
    
    
    // Part 1
    // Time: O(n)
    public static Map<Character, Integer> getCharacterCount(String input) {
        Map<Character, Integer> charCountMap = new HashMap<>();
        
        for (char c : input.toCharArray()) {
            charCountMap.put(c, charCountMap.getOrDefault(c, 0) + 1);
        }
        
        return charCountMap;
    }
    
    // Part 2
    /*
	1. Uses a PriorityQueue (min-heap) to keep track of the nodes with the lowest counts.
	2. Adds each character and its count as a TreeNode to the PriorityQueue.
	3. While there is more than one node in the PriorityQueue, it extracts 
		the two nodes with the lowest counts, creates a new parent node with 
		a count equal to the sum of these two nodes, and adds the new parent 
		node back to the PriorityQueue.
	
	4.The process continues until there is only one node left in the PriorityQueue, 
		which becomes the root of the binary tree.
		
	Time Complexity: O(klogk)
		- Priority Queue: Adding each character and its count takes 
			O(klogk) time, where k is the number of unique characters. 
			
		- Binary tree: Each extraction from the PriorityQueue takes O(logk) time.
			We perform k−1 extractions and insertions to combine nodes (since we combine nodes until only one node is left in the queue).
			Therefore, the time complexity for this step is O(klogk).
     */
    
    public static TreeNode buildBinaryTree(Map<Character, Integer> charCountMap) {
        PriorityQueue<TreeNode> minHeap = new PriorityQueue<>((a, b) -> a.count - b.count);
        
        for (Map.Entry<Character, Integer> entry : charCountMap.entrySet()) {
            minHeap.add(new TreeNode(entry.getKey(), entry.getValue()));
        }
        
        while (minHeap.size() > 1) {
            TreeNode left = minHeap.poll();
            TreeNode right = minHeap.poll();
            
            TreeNode parent = new TreeNode('#', left.count + right.count);
            parent.left = left;
            parent.right = right;
            
            minHeap.add(parent);
        }
        
        return minHeap.poll();
    }
    
    // Part 2: TreeNode class to represent nodes in the binary tree
    static class TreeNode {
        char character;
        int count;
        TreeNode left;
        TreeNode right;
        
        TreeNode(char character, int count) {
            this.character = character;
            this.count = count;
            this.left = null;
            this.right = null;
        }
    }
    
    // Part 2: Method to print the tree (for verification)
    public static void printTree(TreeNode root, int level) {
        if (root == null) return;
        
        printTree(root.right, level + 1);
        
        if (root.character == '#') {
            System.out.println(" ".repeat(level * 4) + "(" + root.character + ", " + root.count + ")");
        } else {
            System.out.println(" ".repeat(level * 4) + "(" + root.character + ", " + root.count + ")");
        }
        
        printTree(root.left, level + 1);
    }

}


/* 
 * https://leetcode.com/company/square/discuss/4082148/square-interview-question!!!-dont-know-the-solution
Question: 
Part 1: Given an arbitrary string, give me a mapping between character and the number of times that character occurs.
"aabbbbbcDDD" => a -> 2, b -> 5, c -> 1, D -> 3
Part 2: convert the map in the first step into a binary tree.
We take the two nodes with the lowest count as the starting points of our tree. (c, 1)
The count of the parent node should be the sum of its children’s counts. The character of the parent node doesn’t matter ("#" is used here).
Our tree now looks like this:
         (#, 3)
        /      
     (a, 2)    (c,1)
We take the next node with the lowest count and add it as a sibling of this tree. Continue in this way for all characters and we get:
(#, 11)
    /   
(b,5)    (#, 6)
         /   \     
    (D, 3)   (#, 3)
             /   
        (a, 2)    (c,1)
Return a pointer to the root of that tree.
 */
