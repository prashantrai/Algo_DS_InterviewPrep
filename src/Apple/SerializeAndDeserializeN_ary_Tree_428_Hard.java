package Apple;

import java.util.ArrayList;
import java.util.List;

public class SerializeAndDeserializeN_ary_Tree_428_Hard {

	public static void main(String[] args) {
        Codec codec = new Codec();

        // ---------- Test 1: Empty tree ----------
        Node root1 = null;
        runTest(codec, root1, "Empty tree");

        // ---------- Test 2: Single node ----------
        Node root2 = new Node(42, new ArrayList<>());
        runTest(codec, root2, "Single node (42)");

        // ---------- Test 3: Example tree ----------
        //      1
        //    / | \
        //   2  3  4
        //      |
        //      5
        Node n1 = new Node(1, new ArrayList<>());
        Node n2 = new Node(2, new ArrayList<>());
        Node n3 = new Node(3, new ArrayList<>());
        Node n4 = new Node(4, new ArrayList<>());
        Node n5 = new Node(5, new ArrayList<>());
        n3.children.add(n5);
        n1.children.add(n2);
        n1.children.add(n3);
        n1.children.add(n4);
        
       Node root3 = n1;
        
        runTest(codec, root3, "Example tree");

        // ---------- Test 4: Root with many children ----------
        Node root4 = new Node(10, new ArrayList<>());
        for (int i = 1; i <= 5; i++) {
            root4.children.add(new Node(i, new ArrayList<>()));
        }
        runTest(codec, root4, "Root with 5 leaf children");

        // ---------- Test 5: Deep (chain) tree ----------
        Node root5 = buildChainTree(6); // 1 -> 2 -> 3 -> 4 -> 5 -> 6
        runTest(codec, root5, "Deep chain of length 6");
    }

    // Helper to run a single test: serialize, deserialize, and compare
    private static void runTest(Codec codec, Node root, String testName) {
        System.out.println("=== " + testName + " ===");
        String serialized = codec.serialize(root);
        System.out.println("Serialized: \"" + serialized + "\"");

        Node deserialized = codec.deserialize(serialized);
        boolean same = sameTree(root, deserialized);
        System.out.println("Deserialization correct? " + same);
        System.out.println();
    }



	/* 
	 * Approach:
	 * --------
	 * We use PREORDER traversal and, for each node, we record:
	 *   1) its value
	 *   2) the number of its children
	 *
	 * Example tree:
	 *           1
	 *        /  |  \
	 *       2   3   4
	 *          /
	 *         5
	 *
	 * Preorder with child counts becomes:
	 *   1,3,  2,0,  3,1,  5,0,  4,0
	 *
	 * We serialize this as a string of integers separated by spaces:
	 *   "1 3 2 0 3 1 5 0 4 0"
	 *
	 * To deserialize, we read tokens from left to right:
	 *   - First integer: node value
	 *   - Second integer: number of children
	 *   - Then recursively build each child
	 *
	 * Because we serialize and deserialize using the same preorder structure,
	 * we can reconstruct the original N-ary tree.
	 *
	 * Time Complexity:
	 *   - serialize:   O(n), we visit each node once
	 *   - deserialize: O(n), we process each token once and create each node once
	 *
	 * Space Complexity:
	 *   - O(n) for the output string / tokens and for storing the tree
	 *   - O(h) recursion stack, where h is tree height (<= 1000 by constraints)
	 */
	
	/* Approach in simple terms:: 
	Below is a simple and efficient Java solution using a very intuitive idea:
	- Use preorder traversal.
	- For each node, store:
		- its value
		- the number of its children
	- Then recursively do the same for all its children. 
	 * */

	
	private static class Node {
	    public int val;
	    public List<Node> children;
	
	    public Node() {
	        children = new ArrayList<>();
	    }
	
	    public Node(int _val) {
	        val = _val;
	        children = new ArrayList<>();
	    }
	
	    public Node(int _val, List<Node> _children) {
	        val = _val;
	        children = _children;
	    }
	}
	
	
	
	private static class Codec {
	
	    // Encodes an n-ary tree to a single string.
	    public String serialize(Node root) {
	        if (root == null) {
	            // Empty tree -> empty string
	            return "";
	        }
	        StringBuilder sb = new StringBuilder();
	        serializeHelper(root, sb);
	        // Remove trailing space
	        return sb.toString().trim();
	    }
	
	    // Helper method to serialize using preorder traversal
	    private void serializeHelper(Node node, StringBuilder sb) {
	        if (node == null) return;
	
	        // 1) Append node value
	        sb.append(node.val).append(" ");
	
	        // 2) Append number of children
	        int childCount = (node.children == null) ? 0 : node.children.size();
	        sb.append(childCount).append(" ");
	
	        // 3) Recursively serialize each child
	        if (node.children != null) {
	            for (Node child : node.children) {
	                serializeHelper(child, sb);
	            }
	        }
	    }
	
	    // Decodes your encoded data to tree.
	    public Node deserialize(String data) {
	        if (data == null || data.isEmpty()) {
	            // Empty string -> empty tree
	            return null;
	        }
	
	        // Split string into tokens (each token is an integer in String form)
	        String[] tokens = data.split(" ");
	
	        // Use an index array so we can modify index inside recursion
	        int[] index = new int[1]; // index[0] = 0 initially
	        return deserializeHelper(tokens, index);
	    }
	
	    // Helper method to deserialize using preorder structure
	    private Node deserializeHelper(String[] tokens, int[] index) {
	        // Safety check
	        if (index[0] >= tokens.length) return null;
	
	        // 1) Read node value
	        int val = Integer.parseInt(tokens[index[0]++]);
	
	        // 2) Read number of children
	        int childCount = Integer.parseInt(tokens[index[0]++]);
	
	        // 3) Create node with empty children list
	        Node node = new Node(val, new ArrayList<>());
	
	        // 4) Recursively build each child and add to children list
	        for (int i = 0; i < childCount; i++) {
	            Node child = deserializeHelper(tokens, index);
	            node.children.add(child);
	        }
	
	        return node;
	    }
	}
	
	/*
	 * Time Complexity:
	 *   - serialize:   O(n), traverses each node exactly once
	 *   - deserialize: O(n), reads each token and creates each node once
	 *
	 * Space Complexity:
	 *   - O(n) for:
	 *       * serialized string / tokens
	 *       * the reconstructed tree
	 *   - O(h) for recursion stack, where h is the tree height (<= 1000)
	 */
	
	
	// Called from main method
    // Build the example tree:
    //      1
    //    / | \
    //   2  3  4
    //      |
    //      5
    private static Node buildExampleTree() {
        Node n1 = new Node(1, new ArrayList<>());
        Node n2 = new Node(2, new ArrayList<>());
        Node n3 = new Node(3, new ArrayList<>());
        Node n4 = new Node(4, new ArrayList<>());
        Node n5 = new Node(5, new ArrayList<>());

        n3.children.add(n5);
        n1.children.add(n2);
        n1.children.add(n3);
        n1.children.add(n4);

        return n1;
    }

    // Build a deep chain: 1 -> 2 -> 3 -> ... -> n
    private static Node buildChainTree(int n) {
        if (n <= 0) return null;
        Node root = new Node(1, new ArrayList<>());
        Node current = root;
        for (int i = 2; i <= n; i++) {
            Node child = new Node(i, new ArrayList<>());
            current.children.add(child);
            current = child;
        }
        return root;
    }

    // Compare two trees (value and structure)
    private static boolean sameTree(Node a, Node b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        if (a.val != b.val) return false;

        int sizeA = (a.children == null) ? 0 : a.children.size();
        int sizeB = (b.children == null) ? 0 : b.children.size();
        if (sizeA != sizeB) return false;

        for (int i = 0; i < sizeA; i++) {
            if (!sameTree(a.children.get(i), b.children.get(i))) {
                return false;
            }
        }
        return true;
    }
	
}