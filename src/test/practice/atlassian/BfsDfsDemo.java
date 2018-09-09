package test.practice.atlassian;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

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
		node_0.state = State.Unvisited;
		node_1.name = "1";
		node_1.setChildren(node_2);
		node_1.state = State.Unvisited;
		node_2.name = "2";
		node_2.setChildren(node_0, node_3);
		node_2.state = State.Unvisited;
		node_3.name = "3";
		node_3.setChildren(node_2);
		node_3.state = State.Unvisited;
		node_4.name = "4";
		node_4.setChildren(node_6);
		node_4.state = State.Unvisited;
		node_5.name = "5";
		node_5.setChildren(node_4);
		node_5.state = State.Unvisited;
		node_6.name = "6";
		node_6.setChildren(node_5);
		node_6.state = State.Unvisited;

		List<Node> nodes = new ArrayList<Node>();
		nodes.add(node_0);
		nodes.add(node_1);
		nodes.add(node_2);
		nodes.add(node_3);
		nodes.add(node_4);
		nodes.add(node_5);
		nodes.add(node_6);

		Graph graph = new Graph();
		graph.nodes = nodes; 
		
		System.out.println("DFS :: "+hasPathDFS(node_0, node_3));
		System.out.println("BFS :: "+hasPathBFS(node_0, node_3));
		
		
	}
	
	//--BFS
	public static boolean hasPathBFS (Node source, Node destination) {
		
		HashSet<Node> visited = new HashSet<Node>();
		LinkedList<Node> nextToVisit = new LinkedList<Node>();
		nextToVisit.add(source);
		
		while(!nextToVisit.isEmpty()) {
			Node node = nextToVisit.remove();
			if(node == destination) {
				return true;
			}
			
			if(visited.contains(node)) {
				continue;
			}
			
			visited.add(node);
			
			for (Node child : node.children) {
				nextToVisit.add(child);
			}
		}
		
		return false;
		
	}
	
	//--DFS
	public static boolean hasPathDFS(Node source, Node destination) {
		
		HashSet<String> visited = new HashSet<String>();
		return hasPathDFS(source, destination, visited);
		
	}

	private static boolean hasPathDFS(Node source, Node destination, HashSet<String> visited) {

		if(visited.contains(source.name)) {
			return false;
		}
		
		visited.add(source.name);
		if(source == destination) {
			return true;
		}
		
		for (Node child : source.children) {
			if(hasPathDFS(child, destination, visited)) {
				return true;
			}
		}
		
		return false;
	}

	

}
