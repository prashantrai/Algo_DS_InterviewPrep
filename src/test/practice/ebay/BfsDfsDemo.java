package test.practice.ebay;

import java.util.HashSet;
import java.util.LinkedList;


public class BfsDfsDemo {

	/* Route between nodes */

	/*
	 * For time complexity of both BFS and DFS, see below link
	 * https://stackoverflow.com/questions/11468621/why-is-the-time-complexity-of-both-dfs-and-bfs-o-v-e
	 * https://stackoverflow.com/questions/26549140/breadth-first-search-time-complexity-analysis
	 * */
	
	
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

		System.out.println("=>search_BFS::  "+searchBFS(node_0, node_3));
		System.out.println("=>search_DFS::  "+searchDFS(node_0, node_3));
		
	}
	
	
	/*public static boolean searchBFS(Node src, Node dest) {
		
		if(src == dest) {
			return true;
		}
		
		HashSet<Node> visited = new HashSet<Node>(); 
		LinkedList<Node> q = new LinkedList<Node>();
		
		q.add(src);
		while(!q.isEmpty()) {
			
			Node n = q.removeFirst();
			
			if(visited.contains(n)) continue;
			
			visited.add(n);
			
			if(n == dest) {
				return true;
			}
			
			for(Node child : n.getAdjacents()) {
				q.addLast(child);
			}
			
		}
		
		return false;
		
	}*/
	
	
	/*public static boolean searchDFS(Node src, Node dest) {
		
		HashSet<Node> visited = new HashSet<Node>(); 
		return searchDFS(src, dest, visited);
		
	}
	
	public static boolean searchDFS(Node src, Node dest, HashSet<Node> visited) {
		if(visited.contains(src)) {
			return false;
		}
		
		if(src == dest) {
			return true;
		}
		
		visited.add(src);
		Node[] children = src.getAdjacents();
		
		for(Node child : children) {
			boolean isFound = searchDFS(child, dest, visited);
			
			if(isFound) return true;
		}
		return false;
	} */
	
	
	public static boolean searchBFS(Node src, Node dest) {
		
		if(src == dest) return true;
		
		HashSet<Node> visited = new HashSet<Node>();	
		LinkedList<Node> q = new LinkedList<Node>();
		q.add(src);
		
		while(!q.isEmpty()) {
			
			Node u = q.removeFirst();
			
			if(u == dest) return true;
			
			if(visited.contains(u)) continue;
			
			visited.add(u);
			
			for(Node child : u.getAdjacents()) {
				q.addLast(child);
			}
		}
		
		return false;
		
	}
	
	
	public static boolean searchDFS(Node src, Node dest) {
		HashSet<Node> visited = new HashSet<Node>();
		
		return searchDFS(src, dest, visited);
		
	}
	
	public static boolean searchDFS(Node src, Node dest, HashSet<Node> visited) {
		
		if(visited.contains(src)) {
			return false;
		}
		
		if(src == dest) return true;
		
		visited.add(src);
		
		for(Node child : src.getAdjacents()) {
			boolean isFound = searchDFS(child, dest, visited);
			
			if(isFound) return true;
		}
		return false;
	} 
	
	
	class Node {
		String name;
		Node[] children;
		
		public void setChildren(Node... nodes) {
			children = nodes;
		}
		
		public Node[] getAdjacents() {
			return children;
		}
	}

}
