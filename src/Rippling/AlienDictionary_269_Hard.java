package Rippling;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class AlienDictionary_269_Hard {
	public static void main(String[] args) {

		String[] w1 = { "wrt", "wrf", "er", "ett", "rftt" }; // -> wertf
		String[] w2 = { "wrt", "wrf", "er", "ett" }; // -> Not enough Info

		String[] w3 = { "q", "p", "q" };

		findOrder(w1); // passed
		findOrder(w2);
		findOrder(w3);

	}

	public static void findOrder(String[] words) {
		HashMap<Character, Set<Character>> graph = new HashMap<>();
		int[] inDegree = new int[26];

		buildGraph(words, graph, inDegree);
		String order = bfs(graph, inDegree);

		if (order.length() == 0) {
			System.out.println("Not enough info");
		} else
			System.out.println("res:: " + order);

	}

	public static String bfs(HashMap<Character, Set<Character>> graph, int[] inDegree) {

		Queue<Character> q = new LinkedList<>();
		for (char c : graph.keySet()) {
			if (inDegree[c - 'a'] == 0) {
				q.offer(c);
			}
		}

		StringBuilder sb = new StringBuilder();
		while (!q.isEmpty()) {
			char c = q.poll();
			sb.append(c);

			for (char child : graph.get(c)) {
				inDegree[child - 'a']--;
				if (inDegree[child - 'a'] == 0) {
					q.offer(child);
				}
			}
		}
		return sb.toString();
	}

	public static void buildGraph(String[] words, HashMap<Character, Set<Character>> graph, int[] inDegree) {

		for (String word : words) {
			for (char c : word.toCharArray()) {
				graph.put(c, new HashSet<Character>());
			}
		}

		for (int i = 1; i < words.length; i++) {

			String first = words[i - 1];
			String second = words[i];

			int len = Math.min(first.length(), second.length());

			for (int j = 0; j < len; j++) {
				char parent = first.charAt(j);
				char child = second.charAt(j);

				if (parent != child) {

					if (!graph.get(parent).contains(child)) {
						graph.get(parent).add(child);
						inDegree[child - 'a']++;
					}
					break;
				}
			}

		}
	}
}

/*
 * 
 * I have a dictionary of words in alphabetical order. The only problem is, it's
 * in an exotic alphabet. While still using the characters a-z, the order is not
 * necessarily a,b,c...z. Given this dictionary of words, we want to define the
 * alphabetical order of this language, or if not enough information, provide
 * that as the answer.
 * 
 * 
 * Sample test case:
 * 
 * ["wrt","wrf","er","ett","rftt"] -> wertf 
 * ["wrt","wrf","er","ett"] -> Not enough Info
 * 
 */
