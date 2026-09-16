package Google.extremelyHigh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrefixPathsInAnUndirectedCharacterGraph_FollwUp2_MultipleTargetString {

	
	
	/** Follow-up 2: Multiple target strings */
	/*Interview script
		“For a small number of words I would run the same DFS 
		independently because it's simpler. If we have thousands 
		of target strings with shared prefixes, I'd store them in 
		a Trie and traverse the graph and Trie simultaneously so 
		shared prefixes are explored once.”
		
		A naive solution runs this DFS independently for every word.
		If there are many target strings, build a Trie.
		
		Then instead of:
		
		Graph DFS state =
		(node, wordIndex)
		
		we have:
		
		Graph DFS state =
		(node, trieNode)
		
		Each graph character advances the Trie.
		
		Mental model
		Single word:
		Graph + word pointer
		
		Many words:
		Graph + Trie pointer
	 * */
	/*
		5. Thought Process
			A straightforward approach would be to run the original DFS once for every target string.
			
			If there are K words, that means repeatedly exploring the same graph prefixes. For words such as:
			
			amazon
			amazing
			amazed
			
			we repeatedly explore graph paths beginning with:
			
			a → m → a
			
			The common work suggests storing all words in a Trie.
			
			Then during graph DFS, instead of asking:
			
			Does neighbor.ch == word[index + 1]?
			
			I ask:
			
			Does currentTrieNode have neighbor.ch as a child?
			
			If not, no target word can continue through that neighbor, so I immediately prune that branch.
			
			The graph visited array is still necessary because paths must remain simple.
		*/	
	/*
	 * 6. Interview Explanation Before Coding
	 * 
	 * This is the explanation I would give before touching the code:
	 * 
	 * “For one word, my DFS state was the graph node plus an index into that word.
	 * With many words, repeatedly running that DFS would duplicate a lot of work
	 * when the words share prefixes.
	 * 
	 * So I’ll first insert all target words into a Trie. Then I’ll DFS through the
	 * graph and Trie simultaneously.
	 * 
	 * From the Trie root, every graph vertex whose character exists as a root child
	 * can start a search. Once I move to that graph vertex, I also move to the
	 * corresponding Trie node.
	 * 
	 * Every successful transition into a Trie node means the current graph path
	 * matches a prefix of at least one target word, so I count that path.
	 * 
	 * To extend it, I inspect the graph neighbors. A neighbor is useful only if it
	 * hasn’t already been used in the current path and the current Trie node has
	 * that neighbor’s character as a child.
	 * 
	 * I’ll use a visited array with backtracking so vertices cannot repeat within
	 * one path but can still participate in other paths.
	 * 
	 * The Trie is useful because shared target prefixes are explored together
	 * instead of once per target word.”
	 */
	
	/* Complexity:
	 * Time:  
			Trie construction: Every character is inserted once:
				Time:  O(S)
				Space: O(S)
				
			DFS
				O(V × Δ^(L - 1)), Δ is delta which denotes degree of graph, 
				degree = max number neighbor for a node in the given graph. 
				NOTE: Read main solution 'PrefixPathsInAnUndirectedCharacterGraph' 
				for full explanation on this.
				
			Overall: O(S + V × Δ^(L - 1))
			
		Space: O(S + V + L)
			Trie: O(S)
			Visited: O(V)
			Recursion stack: O(L)

		
		Interview script: 
		“Building the Trie costs O(S), where S is the total number of 
		target characters. The graph search can still be exponential 
		because we're enumerating simple paths, bounded by approximately 
		O(V times delta to the L minus 1), where delta is maximum degree 
		and L is the longest target. The Trie significantly prunes invalid 
		branches but doesn't change that theoretical worst case. 
		Space is O(S + V + L) for the Trie, visited array, and recursion stack.”	
			
	 * */
	
	// Solution starts...
	
	static class Node {
		// helps with visited array, where this been used 
		// as index of visited array to mark node visited
		int id;
		char ch;
		List<Node> neighbors;
		
		Node(int id, char ch) {
			this.id = id;
			this.ch = ch;
			neighbors = new ArrayList<>();
		}
		@Override
		public String toString() {
		    return ch + "(" + id + ")";
		}
	}
	
	// Trie node
    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();

        // Useful for follow-up: complete-word matches.
        boolean isWord;
    }
	
    public static long countPrefixPaths(
            List<Node> graph,
            List<String> words) {

        if (graph == null || graph.isEmpty()
                || words == null || words.isEmpty()) {
            return 0;
        }

        TrieNode root = buildTrie(words);

        boolean[] visited = new boolean[graph.size()];
        long count = 0;

        // Every graph node can potentially start a matching path.
        for (Node node : graph) {

            TrieNode nextTrieNode =
                    root.children.get(node.ch);

            // No target starts with this character.
            if (nextTrieNode == null) {
                continue;
            }

            count += dfs(
                    node,
                    nextTrieNode,
                    visited);
        }

        return count;
    }

    private static long dfs(
            Node node,
            TrieNode trieNode,
            boolean[] visited) {

        // Current graph path already matches
        // a prefix represented by this Trie node.
        long count = 1;

        visited[node.id] = true;

        // Try extending both the graph path and Trie path.
        for (Node neighbor : node.neighbors) {

            // Keep the graph path simple.
            if (visited[neighbor.id]) {
                continue;
            }

            // Does any target prefix continue
            // with this graph character?
            TrieNode nextTrieNode =
                    trieNode.children.get(neighbor.ch);

            if (nextTrieNode == null) {
                continue;
            }

            count += dfs(
                    neighbor,
                    nextTrieNode,
                    visited);
        }

        // Backtrack so node can participate
        // in another independent graph path.
        visited[node.id] = false;

        return count;
    }

    private static TrieNode buildTrie(List<String> words) {

        TrieNode root = new TrieNode();

        for (String word : words) {

            if (word == null || word.isEmpty()) {
                continue;
            }

            TrieNode current = root;

            for (char ch : word.toCharArray()) {

                TrieNode next =
                        current.children.get(ch);

                if (next == null) {
                    next = new TrieNode();
                    current.children.put(ch, next);
                }

                current = next;
            }

            current.isWord = true;
        }

        return root;
    }
	
	
    public static void main(String[] args) {

	    System.out.println("================================");
	    System.out.println("Test 1: Normal shared-prefix case");
	    System.out.println("================================");

	    /*
	            c(2)
	            |
	    a(0) -- b(1) -- d(3)
	            |
	            e(4)

	    words = ["abc", "abd"]

	    Valid prefix paths:
	    "a"
	    "ab"
	    "abc"
	    "abd"

	    Expected = 4
	    */

	    Node a1 = new Node(0, 'a');
	    Node b1 = new Node(1, 'b');
	    Node c1 = new Node(2, 'c');
	    Node d1 = new Node(3, 'd');
	    Node e1 = new Node(4, 'e');

	    connect(a1, b1);
	    connect(b1, c1);
	    connect(b1, d1);
	    connect(b1, e1);

	    List<Node> graph1 =
	            Arrays.asList(a1, b1, c1, d1, e1);

	    List<String> words1 =
	            Arrays.asList("abc", "abd");

	    long expected1 = 4;
	    long actual1 = countPrefixPaths(graph1, words1);

	    printResult(expected1, actual1);


	    System.out.println("\n================================");
	    System.out.println("Test 2: No matching starting node");
	    System.out.println("================================");

	    /*
	    x(0) -- y(1)

	    words = ["abc"]

	    No graph node starts with 'a'.

	    Expected = 0
	    */

	    Node x2 = new Node(0, 'x');
	    Node y2 = new Node(1, 'y');

	    connect(x2, y2);

	    List<Node> graph2 =
	            Arrays.asList(x2, y2);

	    List<String> words2 =
	            Arrays.asList("abc");

	    long expected2 = 0;
	    long actual2 = countPrefixPaths(graph2, words2);

	    printResult(expected2, actual2);


	    System.out.println("\n================================");
	    System.out.println("Test 3: Single-character target");
	    System.out.println("================================");

	    /*
	    a(0) -- a(1) -- b(2)

	    words = ["a"]

	    Each 'a' node represents a valid path.

	    Valid:
	    node0 -> "a"
	    node1 -> "a"

	    Expected = 2
	    */

	    Node a3a = new Node(0, 'a');
	    Node a3b = new Node(1, 'a');
	    Node b3 = new Node(2, 'b');

	    connect(a3a, a3b);
	    connect(a3b, b3);

	    List<Node> graph3 =
	            Arrays.asList(a3a, a3b, b3);

	    List<String> words3 =
	            Arrays.asList("a");

	    long expected3 = 2;
	    long actual3 = countPrefixPaths(graph3, words3);

	    printResult(expected3, actual3);


	    System.out.println("\n================================");
	    System.out.println("Test 4: Multiple starting points");
	    System.out.println("================================");

	    /*
	    a(0) -- b(1)

	    a(2) -- b(3)

	    words = ["ab"]

	    Valid paths:

	    a0
	    a0 -> b1

	    a2
	    a2 -> b3

	    Expected = 4
	    */

	    Node a4a = new Node(0, 'a');
	    Node b4a = new Node(1, 'b');
	    Node a4b = new Node(2, 'a');
	    Node b4b = new Node(3, 'b');

	    connect(a4a, b4a);
	    connect(a4b, b4b);

	    List<Node> graph4 =
	            Arrays.asList(a4a, b4a, a4b, b4b);

	    List<String> words4 =
	            Arrays.asList("ab");

	    long expected4 = 4;
	    long actual4 = countPrefixPaths(graph4, words4);

	    printResult(expected4, actual4);


	    System.out.println("\n================================");
	    System.out.println("Test 5: Cycle - node cannot repeat");
	    System.out.println("================================");

	    /*
	           a(0)
	          /   \
	       b(1)---c(2)

	    words = ["aba"]

	    Valid prefixes:

	    Starting at a:
	    "a"
	    "ab"

	    We CANNOT do:

	    a -> b -> a

	    because the same a(0) would repeat.

	    Expected = 2
	    */

	    Node a5 = new Node(0, 'a');
	    Node b5 = new Node(1, 'b');
	    Node c5 = new Node(2, 'c');

	    connect(a5, b5);
	    connect(b5, c5);
	    connect(c5, a5);

	    List<Node> graph5 =
	            Arrays.asList(a5, b5, c5);

	    List<String> words5 =
	            Arrays.asList("aba");

	    long expected5 = 2;
	    long actual5 = countPrefixPaths(graph5, words5);

	    printResult(expected5, actual5);


	    System.out.println("\n================================");
	    System.out.println("Test 6: Trie pruning");
	    System.out.println("================================");

	    /*
	             b(1)
	            / | \
	         c(2)d(3)x(4)
	          |
	        a(0)

	    words = ["abc", "abd"]

	    From:

	    a -> b

	    Trie allows:
	    c
	    d

	    Trie rejects:
	    x

	    Valid:
	    "a"
	    "ab"
	    "abc"
	    "abd"

	    Expected = 4
	    */

	    Node a6 = new Node(0, 'a');
	    Node b6 = new Node(1, 'b');
	    Node c6 = new Node(2, 'c');
	    Node d6 = new Node(3, 'd');
	    Node x6 = new Node(4, 'x');

	    connect(a6, b6);
	    connect(b6, c6);
	    connect(b6, d6);
	    connect(b6, x6);

	    List<Node> graph6 =
	            Arrays.asList(a6, b6, c6, d6, x6);

	    List<String> words6 =
	            Arrays.asList("abc", "abd");

	    long expected6 = 4;
	    long actual6 = countPrefixPaths(graph6, words6);

	    printResult(expected6, actual6);


	    System.out.println("\n================================");
	    System.out.println("Test 7: Different target branches");
	    System.out.println("================================");

	    /*
	               b(1)
	              /
	           a(0)
	              \
	               x(2)
	                \
	                 y(3)

	    words = ["ab", "axy"]

	    Valid:
	    "a"
	    "ab"
	    "ax"
	    "axy"

	    Expected = 4
	    */

	    Node a7 = new Node(0, 'a');
	    Node b7 = new Node(1, 'b');
	    Node x7 = new Node(2, 'x');
	    Node y7 = new Node(3, 'y');

	    connect(a7, b7);
	    connect(a7, x7);
	    connect(x7, y7);

	    List<Node> graph7 =
	            Arrays.asList(a7, b7, x7, y7);

	    List<String> words7 =
	            Arrays.asList("ab", "axy");

	    long expected7 = 4;
	    long actual7 = countPrefixPaths(graph7, words7);

	    printResult(expected7, actual7);


	    System.out.println("\n================================");
	    System.out.println("Test 8: Empty graph");
	    System.out.println("================================");

	    List<Node> graph8 = new ArrayList<>();

	    List<String> words8 =
	            Arrays.asList("abc");

	    long expected8 = 0;
	    long actual8 = countPrefixPaths(graph8, words8);

	    printResult(expected8, actual8);


	    System.out.println("\n================================");
	    System.out.println("Test 9: Empty words");
	    System.out.println("================================");

	    Node a9 = new Node(0, 'a');

	    List<Node> graph9 =
	            Arrays.asList(a9);

	    List<String> words9 =
	            new ArrayList<>();

	    long expected9 = 0;
	    long actual9 = countPrefixPaths(graph9, words9);

	    printResult(expected9, actual9);


	    System.out.println("\n================================");
	    System.out.println("Test 10: Duplicate target words");
	    System.out.println("================================");

	    /*
	    a(0) -- b(1)

	    words = ["ab", "ab"]

	    Trie merges duplicate words.

	    Valid graph paths:
	    "a"
	    "ab"

	    We count paths, not number of target copies.

	    Expected = 2
	    */

	    Node a10 = new Node(0, 'a');
	    Node b10 = new Node(1, 'b');

	    connect(a10, b10);

	    List<Node> graph10 =
	            Arrays.asList(a10, b10);

	    List<String> words10 =
	            Arrays.asList("ab", "ab");

	    long expected10 = 2;
	    long actual10 =
	            countPrefixPaths(graph10, words10);

	    printResult(expected10, actual10);
	}

	private static void printResult(long expected, long actual) {

	    System.out.println("Expected : " + expected);
	    System.out.println("Actual   : " + actual);

	    if (expected == actual) {
	        System.out.println("Result   : PASS");
	    } else {
	        System.out.println("Result   : FAIL");
	    }
	}
	private static void connect(Node a, Node b) {
        a.neighbors.add(b);
        b.neighbors.add(a);
    }
	
	
	
	
	
	
	
}


/*
 C5. Prefix Paths in an Undirected Character Graph | Priority: EXTREMELY HIGH
	
	Because the source description is incomplete, use this practice formulation:
	
	
	You are given an undirected graph. Each vertex contains a character.
	
	Given string word, count simple paths whose generated sequence equals a prefix of word.
	
	Example:
	
	       b
	       |
	a ---- b ---- c
	
	word = "abc"
	
	Paths spelling:
	
	"a"
	"ab"
	"abc"
	
	qualify.
	
	A vertex cannot occur twice in the same path.
	
	Follow-ups
	
	Count only complete matches.
	
	Multiple target strings.
	
	Allow repeated vertices.
	
	Return the paths rather than count.
	
	
	
	Similar LC
	79 Word Search
	212 Word Search II
	
	
	
	The graph instead of grid makes it custom.
 */