package Google.extremelyHigh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrefixPathsInAnUndirectedCharacterGraph {

	public static void main(String[] args) {

	    /*
	                b(3)
	                 |
	        a(0) -- b(1) -- c(2)
	    */

	    Node a = new Node(0, 'a');
	    Node b = new Node(1, 'b');
	    Node c = new Node(2, 'c');
	    Node b2 = new Node(3, 'b');

	    connect(a, b);
	    connect(b, c);
	    connect(b, b2);

	    List<Node> graph = Arrays.asList(a, b, c, b2);

	    runTest(
	            "Test 1 - Basic example",
	            graph,
	            "abc",
	            3
	    );

	    /*
	        Valid prefixes:
	        a
	        a -> b
	        a -> b -> c
	    */


	    runTest(
	            "Test 2 - Only first character matches",
	            graph,
	            "ax",
	            1
	    );

	    /*
	        Valid:
	        a

	        No neighbor of a contains 'x'.
	    */


	    runTest(
	            "Test 3 - Single-character word",
	            graph,
	            "b",
	            2
	    );

	    /*
	        Two different nodes contain 'b':

	        b(1)
	        b(3)
	    */


	    runTest(
	            "Test 4 - No starting character exists",
	            graph,
	            "xyz",
	            0
	    );


	    runTest(
	            "Test 5 - Empty word",
	            graph,
	            "",
	            0
	    );


	    runTest(
	            "Test 6 - Null word",
	            graph,
	            null,
	            0
	    );


	    runTest(
	            "Test 7 - Empty graph",
	            new ArrayList<>(),
	            "abc",
	            0
	    );


	    runTest(
	            "Test 8 - Null graph",
	            null,
	            "abc",
	            0
	    );


	    /*
	        Branching graph:

	                   b(1)
	                  /    \
	              a(0)      c(3)
	                  \
	                   b(2)
	                    \
	                     c(4)

	        There are two paths beginning with "abc":

	        a -> b(1) -> c(3)
	        a -> b(2) -> c(4)

	        Prefixes:
	        a
	        a -> b(1)
	        a -> b(1) -> c(3)
	        a -> b(2)
	        a -> b(2) -> c(4)

	        Total = 5
	    */

	    Node x0 = new Node(0, 'a');
	    Node x1 = new Node(1, 'b');
	    Node x2 = new Node(2, 'b');
	    Node x3 = new Node(3, 'c');
	    Node x4 = new Node(4, 'c');

	    connect(x0, x1);
	    connect(x0, x2);
	    connect(x1, x3);
	    connect(x2, x4);

	    List<Node> branchingGraph =
	            Arrays.asList(x0, x1, x2, x3, x4);

	    runTest(
	            "Test 9 - Multiple matching paths",
	            branchingGraph,
	            "abc",
	            5
	    );


	    /*
	        Cycle:

	        a(0) -- b(1)
	         |       |
	         |       |
	        c(2) ----+

	        word = "abca"

	        We must NOT reuse a(0).

	        Valid prefixes from a:

	        a
	        a -> b
	        a -> b -> c

	        Cannot do:
	        a -> b -> c -> a

	        because a is already visited.

	        Total = 3
	    */

	    Node y0 = new Node(0, 'a');
	    Node y1 = new Node(1, 'b');
	    Node y2 = new Node(2, 'c');

	    connect(y0, y1);
	    connect(y1, y2);
	    connect(y2, y0);

	    List<Node> cycleGraph =
	            Arrays.asList(y0, y1, y2);

	    runTest(
	            "Test 10 - Cycle prevents node reuse",
	            cycleGraph,
	            "abca",
	            3
	    );


	    /*
	        Repeated character path:

	        a(0) -- a(1) -- a(2)

	        word = "aaa"

	        Starting from node 0:
	            a
	            a -> a
	            a -> a -> a

	        Starting from node 1:
	            a
	            a -> a(0)
	            a -> a(2)

	        Starting from node 2:
	            a
	            a -> a
	            a -> a -> a

	        Total:
	            3 + 3 + 3 = 9

	        Notice paths are directional:
	        0 -> 1 is different from 1 -> 0.
	    */

	    Node z0 = new Node(0, 'a');
	    Node z1 = new Node(1, 'a');
	    Node z2 = new Node(2, 'a');

	    connect(z0, z1);
	    connect(z1, z2);

	    List<Node> repeatedGraph =
	            Arrays.asList(z0, z1, z2);

	    runTest(
	            "Test 11 - Repeated characters",
	            repeatedGraph,
	            "aaa",
	            9
	    );
	}
	private static void connect(Node a, Node b) {
        a.neighbors.add(b);
        b.neighbors.add(a);
    }
	
	/*
	 * Small test helper.
	 *
	 * Interview script:
	 * "I'll print expected and actual values so each test is easy
	 * to verify while running."
	 */
	private static void runTest(String testName, List<Node> graph,
	        String word, long expected) {
	    System.out.println();
	    System.out.println("--------------------------------------------------");
	    System.out.println(testName);
	    System.out.println("Word     : " + word);

	    long actual = countPrefixPaths(graph, word);

	    System.out.println("Expected : " + expected);
	    System.out.println("Actual   : " + actual);
	    System.out.println(
	            "Result   : " + (expected == actual ? "PASS" : "FAIL")
	    );
	}
	
	
	// Solution starts...
	
	/*
	 2. What This Problem Really Is

		This is essentially DFS/backtracking on a graph, where the current 
		depth corresponds to the character position in word.
		
		Every successful DFS step represents one valid prefix path.
		
		3. Pattern Recognition
		
		Pattern: Graph DFS + Backtracking
		
		Recognition signal:
		
		“Find/count paths matching a sequence, and a node cannot 
		be reused within one path” → think DFS + visited + backtracking.
		
		Very similar conceptually to LeetCode 79 — Word Search, except here:
		
		the structure is a general undirected graph instead of a grid;
		we count every matching prefix, not just whether the complete word exists.
		
		4. Key Insight
		
		If I am currently at node u matching word[index], then the 
		path ending at u is already one valid answer because it spells
		 a prefix of length index + 1.
		
		So:
		
		match current node
		       ↓
		count this path
		       ↓
		try neighbors matching next character
		       ↓
		backtrack
		
		
		We start DFS from every vertex whose character equals word.charAt(0).
		
		
		5. Thought Process
		
		A normal BFS/DFS traversal is not sufficient because we're not simply 
		determining reachability; we need to enumerate different paths.
		
		The important constraint is that a vertex cannot repeat within the same 
		path, so DFS with backtracking is a natural fit.
		
		At each matching vertex, I count the current path immediately because 
		even if I cannot extend it further, it still matches some prefix of word.
		
		Then I recursively try neighbors matching the next required character.
		
		
		6. Interview Explanation Before Coding
		
		“I’ll use DFS with backtracking. I’ll try every vertex that matches 
		the first character of the target word as a possible starting point.
		
		During DFS, index tells me which character of the word the current 
		vertex needs to match. Once it matches, the path from the starting 
		vertex through the current vertex forms a valid prefix, so I increment 
		the count by one.
		
		Then, if there are more characters in the word, I explore each unvisited 
		neighbor whose character matches the next character.
		
		I’ll maintain a visited array only for the current DFS path. When I return 
		from a node, I unmark it, allowing that vertex to participate in a different path.
		
		This correctly counts distinct simple paths because every DFS branch represents 
		one specific path, while the visited array prevents cycles within that path.”
		
		*/
		
		/*
		7. Complexity Analysis Before Coding
		
		Let:
		
		V = number of vertices
		E = number of edges
		L = length of word
		
		The graph traversal can potentially enumerate many simple paths, so this is 
		inherently exponential in the worst case.
		
		Time
		
		Worst case:
		
		O(V × Δ^(L−1))
		
		where Δ is the maximum degree of a vertex.
		
		A looser bound is exponential in L.
		
		This is unavoidable in the general case because the output itself may contain 
		exponentially many matching paths.
		
		Space
		
		O(V + L)
		
		O(V) visited array
		O(L) maximum recursion depth
		
		The graph itself takes O(V + E) space.
		
	    *** Revision card for Time complexity ***
	    
		Time Complexity: Prefix Paths in an Undirected Character Graph
		Problem Pattern
		
		We use DFS + backtracking to explore simple paths whose characters match prefixes of word.
		
		Unlike normal DFS, we may revisit the same vertex in different DFS branches after backtracking, so this is not simply:
		
		O(V + E)
		
		We are potentially enumerating many different paths.
		
		Variables
		V = number of vertices
		E = number of edges
		L = length of word
		Δ = maximum degree of the graph
		What is Δ?
		
		Δ is pronounced delta.
		
		It means:
		
		the maximum number of neighbors any single vertex has
		
		It may not be explicitly given in the problem statement. We introduce it because the DFS contains:
		
		for (Node neighbor : node.neighbors)
		
		To analyze the worst case, ask:
		
		What is the maximum number of recursive choices one node can create?
		
		That maximum is Δ.
		
		Example
		        b(3)
		         |
		a(0) -- b(1) -- c(2)
		
		Degrees:
		
		a(0) = 1
		b(1) = 3
		c(2) = 1
		b(3) = 1
		
		Therefore:
		
		Δ = 3
		
		because b(1) has the largest number of neighbors.
		
		Interview Script: What is Delta?
		
		“I’m using delta to represent the maximum degree of the graph, meaning the maximum number of neighbors any vertex can have. The problem doesn’t explicitly provide this value, but I introduce it to express the DFS branching factor.”
		
		Shorter version:
		
		“Delta is the maximum number of neighbors of any node, so it represents the worst-case branching factor of DFS.”
		
		How to Derive the Time Complexity
		Step 1: Starting vertices
		
		We may try every vertex as a possible starting point.
		
		Worst case:
		
		V starting points
		Step 2: Branching at each node
		
		From one vertex, DFS may explore up to Δ neighbors.
		
		So conceptually:
		
		            start
		          /   |   \
		       up to Δ choices
		
		At the next level, each branch may again have up to Δ choices.
		
		Step 3: Recursion depth
		
		Each recursive call matches one character of word.
		
		Therefore maximum recursion depth is:
		
		L
		
		Since the starting node already matches word[0], there are at most:
		
		L - 1
		
		additional moves.
		
		The recursion tree is roughly:
		
		Level 0:      1
		Level 1:      Δ
		Level 2:      Δ²
		Level 3:      Δ³
		...
		Level L-1:    Δ^(L-1)
		
		Total work from one starting vertex:
		
		1 + Δ + Δ² + ... + Δ^(L-1)
		
		This is a geometric series dominated by its largest term:
		
		O(Δ^(L-1))
		
		Since we may start from up to V vertices:
		
		O(V × Δ^(L-1))
		Small Example
		
		Suppose:
		
		V = 100
		Δ = 3
		L = 4
		
		From one starting node:
		
		Level 0: 1
		Level 1: 3
		Level 2: 9
		Level 3: 27
		
		So roughly:
		
		1 + 3 + 9 + 27
		
		states may be explored.
		
		The dominant term is:
		
		3³
		
		Across up to 100 starting vertices:
		
		O(100 × 3³)
		
		General form:
		
		O(V × Δ^(L-1))
		Why It Is Not O(V + E)
		
		Normal DFS typically visits each vertex/edge once.
		
		Here we backtrack:
		
		visited[node.id] = false;
		
		That means the same vertex can participate again in a different path.
		
		Example:
		
		a -> b1 -> c1
		a -> b1 -> c2
		a -> b2 -> c3
		a -> b2 -> c4
		
		We are enumerating possible matching paths, not simply traversing the graph once.
		
		Therefore the complexity can be exponential in L.
		
		If We Also Store / Copy Every Path
		
		If we do:
		
		result.add(new ArrayList<>(currentPath));
		
		copying one path can cost up to:
		
		O(L)
		
		Therefore:
		
		Count only
		O(V × Δ^(L-1))
		Return/store all paths
		O(V × Δ^(L-1) × L)
		
		because every matching DFS state may create a copy of a path of length up to L.
		
		If the Interviewer Doesn't Like Δ
		
		Sometimes an interviewer may want the complexity expressed only using variables from the problem, such as V and L.
		
		In a general undirected graph, any vertex can have at most:
		
		V - 1
		
		neighbors.
		
		Therefore:
		
		Δ <= V - 1
		
		Substitute V - 1 for Δ:
		
		O(V × (V - 1)^(L - 1))
		
		A looser simplified bound is:
		
		O(V^L)
		
		So you can say either:
		
		More informative:
		O(V × Δ^(L-1))
		
		or:
		
		Using only V and L:
		O(V × (V - 1)^(L - 1))
		
		Loosely:
		O(V^L)
		Interview Script: If They Don't Want Delta
		
		“If you prefer the complexity only in terms of V and L, a node can have at most V minus 1 neighbors in a general graph. So I can substitute V minus 1 for delta, which gives O(V × (V−1)^(L−1)), or loosely O(V^L).”
		
		If the Problem Gives a Degree Limit
		
		Suppose the problem says:
		
		Each vertex has at most 4 neighbors.
		
		Then:
		
		Δ <= 4
		
		and the complexity becomes:
		
		O(V × 4^(L-1))
		
		This is the same reason grid DFS problems often use a constant branching factor.
		
		Full Interview Script
		
		“This is not a normal O(V + E) DFS because backtracking allows the same vertex to participate in different paths. I can start from up to V vertices. I’ll define delta as the maximum degree of the graph, meaning the maximum number of neighbors any vertex has. At each recursive level, DFS can branch to up to delta neighbors, and the recursion depth is bounded by the word length L. So the worst-case time is O(V × delta^(L−1)). If I also copy and store every path, each copy can cost up to O(L), making it O(V × delta^(L−1) × L). If you prefer not to use delta, since delta is at most V−1, I can express the bound as O(V × (V−1)^(L−1)), or loosely O(V^L).”
		
		Quick Mental Model
		How many starting points?
		        V
		
		How many choices per recursive step?
		        Δ
		
		How deep?
		        L
		
		Therefore:
		        O(V × Δ^(L-1))
		
		And remember:
		
		Δ comes from the branching factor of node.neighbors.
		
	*/
	/* 8. Step-by-Step Algorithm
		If word is empty, return 0.
		Iterate through every graph vertex.
		If its character matches word[0], start DFS.
		In DFS:
			verify the current character matches word[index];
			mark the vertex visited;
			count 1 for the current prefix;
			explore unvisited neighbors matching word[index + 1];
			unmark the vertex before returning.
		Sum the results from all starting vertices. 
	 * */
	
	
	
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
	
	public static long countPrefixPaths(List<Node> graph, String word) {
		if(graph == null || graph.isEmpty() 
				|| word == null || word.isEmpty()) {
			return 0;
		}
		
		/* 	Store Node in the list for better debugging. 
		 	Suppose the graph has two different b nodes:

				a(0) -- b(1)
				  \
				   b(2)

				Result may print:
					[a, b]
					[a, b]

				That is technically correct, but we can't tell which path used which b.
				That's why for debugging, I would store Node in the path.
				List<List<Node>> result = new ArrayList<>();
				List<Node> currentPath = new ArrayList<>();
		 * */
		List<List<Node>> result = new ArrayList<>(); // follow-up 4: print the path
		
		boolean[] visited = new boolean[graph.size()];
		long count = 0;
		
		// Every node matching the first character can start a path.
		for(Node node : graph) {
			if(node.ch == word.charAt(0)) {
				List<Node> currentPath = new ArrayList<>(); // follow-up 4: print the path
				count += dfs(node, word, 0, visited, currentPath, result);
			}
		}
		
		System.out.println("Result: Path: "+result);
		
		return count;
	}
	
	
	private static long dfs(Node node, String word, int idx, 
			boolean[] visited, List<Node> currentPath, List<List<Node>> result) {
		
		// Defensive check: current node must match this word position.
        if (node.ch != word.charAt(idx)) {
            return 0;
        }
		
		visited[node.id] = true;
		currentPath.add(node); // follow-up 4: print the path
		
		/* Follow-up 4: print the path
	     * 
	     * Every node reached represents one valid prefix.
	     *
	     * IMPORTANT:
	     * Take a copy because currentPath will change
	     * during backtracking.
	     */
		result.add(new ArrayList<>(currentPath)); // follow-up 4: print the path
		
		// Reaching this node means the current path is a valid prefix.
		long count = 1;
		
		// Try extending the prefix with the next required character.
		if(idx + 1 < word.length()) {
			char nextChar = word.charAt(idx+1);
			
			for(Node neighbor : node.neighbors) {
				if(!visited[neighbor.id] &&
						neighbor.ch == nextChar) {
					
					count += dfs(neighbor, word, idx+1, visited, currentPath, result);
					
				}
			}
		}
		
		/*
	     * Backtrack.
	     *
	     * Undo BOTH states that we changed:
	     * 1. visited
	     * 2. currentPath
	     */
		currentPath.remove(currentPath.size()-1);
		
		// Backtrack so this node can be used by another path.
		visited[node.id] = false;
		
		return count;
	}
	
	
	
	/** Follow-up 1: Count only complete matches */
	/*
	Very small change. Right now we do: long count = 1;
	because every prefix counts.

	For complete-word matches, count only when:

	if (index == word.length() - 1) {
	    visited[node.id] = false;
	    return 1;
	}
	
	Interview script
	“The traversal stays exactly the same. The only semantic change 
	is that I stop counting intermediate prefixes and increment the 
	result only when index reaches the last character.”
	*/
	
	
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
	
	/** Follow-up 3: Allow repeated vertices */
	/*	
	 * Remove the path-level restriction: boolean[] visited
		
		However, there is an important detail:
		Because the target word has finite length L, 
		recursion still terminates after at most L vertices.
		
		Interview script
		“If repeated vertices are allowed, I don't need the 
		visited array. Cycles are safe because the word length 
		bounds DFS depth, so we can make at most L recursive steps.” 
	 * */
	
	/** Follow-up 4: Return paths instead of count */
	/*	Maintain: List<Node> currentPath
		
		During DFS:
			currentPath.add(node);
		
			and for every valid prefix:
			result.add(new ArrayList<>(currentPath));
		
			Then backtrack:
			currentPath.remove(currentPath.size() - 1);
		
		Interview script
		“Instead of incrementing a counter whenever I find a prefix, 
		I'd snapshot the current DFS path into the result. 
		Everything else remains the same.”
		
		Example:  
		
			For graph:
			        b(3)
			         |
			a(0) -- b(1) -- c(2)
		
			word = "abc"
		
			we'll get:
			
			Valid paths:
			[a]
			[a, b]
			[a, b, c]
		
			and: count = 3
	 * */
	
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