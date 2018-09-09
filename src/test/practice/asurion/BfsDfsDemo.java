package test.practice.asurion;

import java.util.HashSet;
import java.util.LinkedList;

/* Route between nodes */

/*
 * For time complexity of both BFS and DFS, see below link
 * https://stackoverflow.com/questions/11468621/why-is-the-time-complexity-of-both-dfs-and-bfs-o-v-e
 * https://stackoverflow.com/questions/26549140/breadth-first-search-time-complexity-analysis
 * */

public class BfsDfsDemo {

	public static void main(String[] args) {
		
		Node node_0 = new Node();
		Node node_1 = new Node();
		Node node_2 = new Node();
		Node node_3 = new Node();
		Node node_4 = new Node();
		Node node_5 = new Node();
		Node node_6 = new Node();
		
		Node node_7 = new Node();
		node_7.name = "7";
		
		node_0.name = "0";
		node_0.setChildren(node_1);
		node_1.name = "1";
		node_1.setChildren(node_2);
		node_2.name = "2";
		node_2.setChildren(node_0, node_3);
		node_3.name = "3";
		node_3.setChildren(node_2);
		node_4.name = "4";
		node_4.setChildren(node_6);
		node_5.name = "5";
		node_5.setChildren(node_4);
		node_6.name = "6";
		node_6.setChildren(node_5);

		System.out.println("=>search_BFS::  "+searchBFS(node_0, node_7));
		System.out.println("=>search_DFS::  "+searchDFS(node_0, node_3));
		
	}
	
	
	private static String searchBFS(Node start, Node end) {
		
		if(start == end) {
			return "Found";
		}
		
		HashSet<Node> visited = new HashSet<Node>();
		LinkedList<Node> q = new LinkedList<Node>();
		
		q.add(start);
		
		while (!q.isEmpty()) {
			
			Node u = q.remove();
			
			if(u == end) {
				return "Found";
			}
			
			if(visited.contains(u)) {
				continue;
			}
			
			visited.add(u);
			
			//--get node children and add to list
			for(Node child : u.getAdjacent()) {
				q.add(child);
			}
			
		}
		
		
		return "Not Found";
	}

	

	public static boolean searchDFS(Node start, Node end) {
		
		HashSet<Node> visited = new HashSet<Node>();
		return searchDFS(start, end, visited);
	
	}

	private static boolean searchDFS(Node start, Node end, HashSet<Node> visited) {
		
		if(visited.contains(start)) return false;
		
		if(start == end) {
			return true;
		}
		
		visited.add(start);
		
		for(Node child : start.getAdjacent()) {

			boolean isFound = searchDFS(child, end, visited);
			
			if(isFound) return true;
			
		}
		return false;
		
	}
	
	

}





/*class Graph {
    List<Node> adjcencyList;
}*/

class Node {
	String name;
	Node[] children;
	
	public void setChildren(Node... nodes) {
		children = nodes;
	}
	
	public Node[] getAdjacent() {
		return children;
	}
	
	
}