package test.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class GraphDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

class Graph {
	
	private HashMap<Integer, Node> nodeLookup = new HashMap<Integer, Node>();
	
	public static class Node {
		private int id;
		private LinkedList<Node> anjacent = new LinkedList<Node>();
		
		private Node(int id) {
			this.id = id;
		}
	}
	
	public boolean hasPathDFS(Node source, Node destination) {
		HashSet<Integer> visited = new HashSet<Integer>();
		return hasPathDFS(source, destination, visited);
	}

	private boolean hasPathDFS(Node source, Node destination, HashSet<Integer> visited) {
		
		if(visited.contains(source.id)) {
			return false;
		}
		
		visited.add(source.id);
		if(source == destination) {
			return true;
		}
		
		for (Node child : source.anjacent) {
			if(hasPathDFS(child, destination, visited)) {
				return true;
			}
		}
		return false;
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
			
			for (Node child : node.anjacent) {
				nextToVisit.add(child);
			}
		}
		
		return false;
		
	}
	
}