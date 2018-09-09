package test.practice.ebay;

import java.util.HashSet;
import java.util.LinkedList;

public class RoutesBetweeNodesDemo {
	
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

	public static String searchBFS(Node start, Node end) {
		
		HashSet<Node> visited = new HashSet<Node>();
		LinkedList<Node> q = new LinkedList<Node>();
		
		
		q.addLast(start);
		
		while (!q.isEmpty()) {
			
			Node node = q.remove(); //--remove the head
			
			if(node == end) {
				return "Found";
			}
			
			if(visited.contains(node)) 
				continue;
			
			visited.add(node);
			
			for(Node child : node.getAdjacents()) {
				q.add(child);
			}
			
		}
		return "Not found";
	}


	public static boolean  searchDFS(Node start, Node end) {
		HashSet<Node> visited = new HashSet<Node>();
		
		return searchDFS(start, end, visited);
	}
	
	public static boolean searchDFS(Node start, Node end, HashSet<Node> visited) {
		
		if(visited.contains(start)) {
			return false;
		}
		
		if(start == end) {
			System.out.println(">> Found");
			return true;
		}
		visited.add(start);
		for(Node child : start.getAdjacents()) {
			boolean isFound = searchDFS(child, end, visited);
			
			if(isFound) 
				return true;
		}
		System.out.println(">> Not Found");
		return false;
	}
}



class Node {
	
	String name;
	Node[] children;
	
	public Node() {}
	public void setName(String name) {
		this.name = name;
	}
	
	public void setChildren(Node...children) {
		this.children = children;
	}
	
	public Node[] getAdjacents() {
		return children;
	}
	
	public String toString() {
		return "name="+name;
	}
}