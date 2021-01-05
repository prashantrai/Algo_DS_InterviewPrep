package leetcode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class AlienDictionary_269_Hard {

	/**
	 * Reference:
	 * https://massivealgorithms.blogspot.com/2019/04/leetcode-269-alien-dictionary.html
	 * https://zhuhan0.blogspot.com/2017/06/leetcode-269-alien-dictionary.html
	 * https://evanyang.gitbooks.io/leetcode/content/LeetCode/alien_dictionary.html
	 * https://tenderleo.gitbooks.io/leetcode-solutions-/GoogleHard/269.html
	 * http://interviewsource.blogspot.com/2016/09/269-alien-dictionary.html
	 * https://www.geeksforgeeks.org/given-sorted-dictionary-find-precedence-characters/
	 * https://leetcode.com/discuss/interview-question/248131/microsoft-interview-round-1-alien-dictionary
	 * 
	 * READ: https://leetcode.com/problems/course-schedule-ii/discuss/190393/Topological-Sort-Template-General-Approach!!
	 * 
	 * 
	 * For complete Question and Solution:
	 * https://massivealgorithms.blogspot.com/2019/04/leetcode-269-alien-dictionary.html
	 * 
	 * For question and it's description:
	 * https://www.lintcode.com/problem/alien-dictionary/description
	 */

	/*
	 * https://www.lintcode.com/problem/alien-dictionary/description
	 * Example 1:
	 * 
	 * Input：["wrt","wrf","er","ett","rftt"] 
	 * Output："wertf" 
	 * 
	 * Explanation： 
	 * 	from "wrt" and "wrf" , we can get 't'<'f' 
	 * 	from "wrt" and "er" , we can get 'w'<'e' 
	 * 	from "er"and"ett" , we can get 'r'<'t' 
	 * 	from "ett"and"rftt" ,we can get 'e'<'r' 
	 * 
	 * So return "wertf"
	 * 
	 * Example 2:
	 * Input：["z","x"] 
	 * 
	 * Output："zx" 
	 * Explanation： 
	 * 		from "z" and "x"，we can get 'z' < 'x' 
	 * 		So return "zx"
	 */
	
	/*
	 * Building the graph takes O(n). 
	 * Topological sort takes O(V + E). V <= n. E also can't be larger than n. 
	 * So the overall time complexity is O(n).
	 * */

	public static void main(String[] args) {
		String[] words = {
		                  "wrt",
		                  "wrf",
		                  "er",
		                  "ett",
		                  "rftt"
						};
		
		String res = alienOrder(words);
		//String res = alienOrder2(words);
		System.out.println(">>>Expected: wertf, Actual: "+res+"\n\n");
		
		String[] words2 = {"z","x"};
		res = alienOrder(words2);
		//String res = alienOrder2(words2);
		System.out.println(">>>Expected: zx, Actual: "+res+"\n\n");
		
		String[] words3 = {
		                   "z",
		                   "x",
		                   "z"
		                 };
		
		res = alienOrder(words3);
		//String res = alienOrder2(words2);
		System.out.println(">>>Expected: <EMPTY>, Actual: "+res);
		
		String[] words4 = {"abc", "ab"};
		res = alienOrder(words4); System.out.println("1. >>>Expected: <EMPTY>, Actual: "+res);
		res = alienOrder2(words4); System.out.println("2. >>>Expected: <EMPTY>, Actual: "+res);
		res = alienOrder_BFS(words4);
		System.out.println("3. >>>Expected: <EMPTY>, Actual: "+res);
		
	}
	
	
	
	
	public static String alienOrder(String[] words) {
		Map<Character, Set<Character>> graph = null;
		
		//--Here we can also use map to keep track of dependecy refer another implementation 'alienOrder()' below in this file
		int[] inDegree = new int[26]; //--to keep track of number of parents or or node should be build first 
		
		graph = buildGraph(words, inDegree);
		
		if(graph == null) 
            return "";
		
		String orderStr = topologicalSort(graph, inDegree);
		//System.out.println("orderStr:: "+orderStr);
		orderStr = orderStr.length() == graph.size() ? orderStr : "";
		
		return orderStr;
	}
	
	//--BFS
	public static String topologicalSort(Map<Character, Set<Character>> graph, int[] inDegree) {
		Queue<Character> queue = new LinkedList<>();
		for(char c : graph.keySet()) {
			if(inDegree[c - 'a'] == 0) {
				queue.offer(c);
			}
		}
		
		StringBuilder sb = new StringBuilder();
		while(!queue.isEmpty()) {
			char c = queue.poll();
			sb.append(c);
			
			for(char child : graph.get(c)) {
				inDegree[child - 'a']--;
				if(inDegree[child - 'a'] == 0) {
					queue.offer(child);
				}
			}
		}
		
		if(sb.length() < graph.size()) 
			return "";
		
		return sb.toString();
	}
	
	public static Map<Character, Set<Character>> buildGraph(String[] words, int[] inDegree) {
		Map<Character, Set<Character>> graph = new HashMap<>();
		for(String word : words) {
			for(char c : word.toCharArray()) {
				graph.put(c, new HashSet<Character>());
			}
		}
		
		for(int i = 1; i<words.length; i++) {
			String first  = words[i-1];
			String second = words[i];
			
			// Check that second is not a prefix of first.
			/* The input can contain words followed by their prefix, 
			 * for example, abcd and then ab. These cases will never result in a valid alphabet 
			 * (because in a valid alphabet, prefixes are always first). 
			 */
	        if (first.length() > second.length() && first.startsWith(second)) {
	            return null;
	        }
			
			int len = Math.min(first.length(), second.length());
			
			/*
			 * Iterate through the smaller string len and create graph
			 * because beyond the small string len (when we are comparing with the larger string)
			 * we don't have and char left to compare with
			 * */
			for(int j=0; j<len; j++) {
				char parent = first.charAt(j);
				char child = second.charAt(j);
				if(parent != child) {
					if (!graph.get(parent).contains(child)) {
						graph.get(parent).add(child);
						inDegree[child - 'a']++;
					}
					break;
				}
			}
		}
		
		/*
		inDegree:: [0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0]
		graph:: {r=[t], t=[f], e=[r], f=[], w=[e]}
		 * */
		System.out.println("inDegree:: "+Arrays.toString(inDegree));
		System.out.println("graph:: "+graph);
		
		return graph;
	}
	
	
	
	//https://evanyang.gitbooks.io/leetcode/content/LeetCode/alien_dictionary.html
	public static String alienOrder2(String[] words) {

        if(words == null || words.length == 0) 
            return "";
        Map<Character, Set<Character>> map = new HashMap<Character, Set<Character>>();
        Map<Character, Integer> degree = new HashMap<Character, Integer>();
        StringBuilder sb = new StringBuilder();

        // put all word in-degree 0
        for(String s: words){
            for(char c: s.toCharArray()){
                degree.put(c,0);
            }
        }

        // compare each word and its pre-word char by char, 
        // if different, since c1 is in front of c2, put c2 into c1's next set, c2 in-dgree + 1
        for(int i=0; i < words.length-1; i++){
            String cur = words[i];
            String next = words[i+1];
            
            if(cur.length() > next.length() && cur.startsWith(next)) {
            	return "";
            }
            
            // using longer one
            int length = Math.min(cur.length(), next.length());
            for(int j=0; j<length; j++){
                char c1=cur.charAt(j);
                char c2=next.charAt(j);
                if(c1!=c2){
                    Set<Character> set = map.getOrDefault(c1, new HashSet());
                    if(!set.contains(c2)){
                        set.add(c2);
                        map.put(c1, set);
                        degree.put(c2, degree.get(c2) + 1);
                    }
                    break;
                }
            }
        }

        System.out.println("map: "+map);
        System.out.println("degree: "+degree);
        
        // topological sort via BFS
        Queue<Character> q = new LinkedList();
        // put all 0 in-degree into queue
        for(char c: degree.keySet()){
            if(degree.get(c) == 0) q.add(c);
        }
        while(!q.isEmpty()){
            char c = q.poll();
            sb.append(c);
            if(map.containsKey(c)){
                for(char c2: map.get(c)){
                    // all next chars' in-degree abstract 1
                    degree.put(c2,degree.get(c2) - 1);
                    if(degree.get(c2) == 0) q.add(c2);
                }
            }
        }
        if(sb.length() != degree.size()) return "";

        return sb.toString();
    }
	
	
	//--Solutions from Leetcode Article ---------------------- 
	
	// Approach 1 : BFS  [Copied from Leetcode Article]
	public static String alienOrder_BFS(String[] words) {
	    
	    // Step 0: Create data structures and find all unique letters.
	    Map<Character, List<Character>> adjList = new HashMap<>();
	    Map<Character, Integer> counts = new HashMap<>();
	    for (String word : words) {
	        for (char c : word.toCharArray()) {
	            counts.put(c, 0);
	            adjList.put(c, new ArrayList<>());
	        }
	    }
	    
	    // Step 1: Find all edges.
	    for (int i = 0; i < words.length - 1; i++) {
	        String word1 = words[i];
	        String word2 = words[i + 1];
	        // Check that word2 is not a prefix of word1.
	        if (word1.length() > word2.length() && word1.startsWith(word2)) {
	            return "";
	        }
	        // Find the first non match and insert the corresponding relation.
	        for (int j = 0; j < Math.min(word1.length(), word2.length()); j++) {
	            if (word1.charAt(j) != word2.charAt(j)) {
	                adjList.get(word1.charAt(j)).add(word2.charAt(j));
	                counts.put(word2.charAt(j), counts.get(word2.charAt(j)) + 1);
	                break;
	            }
	        }
	    }
	    
	    // Step 2: Breadth-first search.
	    StringBuilder sb = new StringBuilder();
	    Queue<Character> queue = new LinkedList<>();
	    for (Character c : counts.keySet()) {
	        if (counts.get(c).equals(0)) {
	            queue.add(c);
	        }
	    }
	    while (!queue.isEmpty()) {
	        Character c = queue.remove();
	        sb.append(c);
	        for (Character next : adjList.get(c)) {
	            counts.put(next, counts.get(next) - 1);
	            if (counts.get(next).equals(0)) {
	                queue.add(next);
	            }
	        }
	    }
	    
	    if (sb.length() < counts.size()) {
	        return "";
	    }
	    return sb.toString();
	}
	
	
	// Approach 2 : DFS [Copied from Leetcode Article]
	class Solution {
	    
	    private Map<Character, List<Character>> reverseAdjList = new HashMap<>();
	    private Map<Character, Boolean> seen = new HashMap<>();
	    private StringBuilder output = new StringBuilder();
	    
	    public String alienOrder(String[] words) {
	        
	        // Step 0: Put all unique letters into reverseAdjList as keys.
	        for (String word : words) {
	            for (char c : word.toCharArray()) {
	                reverseAdjList.putIfAbsent(c, new ArrayList<>());
	            }
	        }
	        
	        // Step 1: Find all edges and add reverse edges to reverseAdjList.
	        for (int i = 0; i < words.length - 1; i++) {
	            String word1 = words[i];
	            String word2 = words[i + 1];
	            // Check that word2 is not a prefix of word1.
	            if (word1.length() > word2.length() && word1.startsWith(word2)) {
	                return "";
	            }
	            // Find the first non match and insert the corresponding relation.
	            for (int j = 0; j < Math.min(word1.length(), word2.length()); j++) {
	                if (word1.charAt(j) != word2.charAt(j)) {
	                    reverseAdjList.get(word2.charAt(j)).add(word1.charAt(j));
	                    break;
	                }
	            }
	        }
	        
	        // Step 2: DFS to build up the output list.
	        for (Character c : reverseAdjList.keySet()) {
	            boolean result = dfs(c);
	            if (!result) return "";
	        }
	        
	        
	        if (output.length() < reverseAdjList.size()) {
	            return "";
	        }
	        return output.toString();
	    }
	    
	    // Return true iff no cycles detected.
	    private boolean dfs(Character c) {
	        if (seen.containsKey(c)) {
	            return seen.get(c); // If this node was grey (false), a cycle was detected.
	        }
	        seen.put(c, false);
	        for (Character next : reverseAdjList.get(c)) {
	            boolean result = dfs(next);
	            if (!result) return false;
	        }
	        seen.put(c, true);
	        output.append(c);
	        return true;
	    }    
	}
	
	

}