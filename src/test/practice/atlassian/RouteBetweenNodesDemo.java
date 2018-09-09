package test.practice.atlassian;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


enum State {Unvisited, Visiting, Visited}

public class RouteBetweenNodesDemo {

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
		
		System.out.println("=> "+search(graph, node_0, node_3));
		System.out.println("=>search_2::  "+search_2(graph, node_0, node_3));
		System.out.println("=>search_BFS::  "+searchBFS(node_0, node_3));
		System.out.println("=>search_DFS::  "+searchDFS(node_0, node_3));
		
		
		/*HashMap<Node, List<Node>> adjacencyList = new HashMap<Node, List<Node>>();
		adjacencyList.put(node_0, listOfAdjacentNodes(node_1));
		adjacencyList.put(node_1, listOfAdjacentNodes(node_2));
		adjacencyList.put(node_2, listOfAdjacentNodes(node_0, node_3));
		adjacencyList.put(node_3, listOfAdjacentNodes(node_2));
		adjacencyList.put(node_4, listOfAdjacentNodes(node_6));
		adjacencyList.put(node_5, listOfAdjacentNodes(node_4));
		adjacencyList.put(node_6, listOfAdjacentNodes(node_5));
		
		System.out.println(">>> adjacencyList :: "+adjacencyList);*/
		
	}
	
	
	public static boolean searchDFS(Node start, Node end) {
		
		HashSet<Node> visited = new HashSet<Node>();
		return searchDFS(start, end, visited);
	}
	
	private static boolean searchDFS(Node start, Node end, HashSet<Node> visited) {
		
		if(visited.contains(start)) 
			return false;
		
		visited.add(start);
		
		if(start == end) 
			return true;
		
		for(Node child :start.children) {
			
			if(searchDFS(child, end, visited)){
				return true;
			}

			//Below code will not work coz it will break the loop on first return however we want to return for only TRUE else the loop should run till end
			// return searchDFS(child, end, visited);;  
		}
		return false;
	}


	public static boolean searchBFS(Node start, Node end) {
		
		if(start == end) 
			return true;
		
		if(start == null || end == null) 
			return false;
		
		HashSet<Node> visited = new HashSet<Node>();
		LinkedList<Node> q = new LinkedList<Node>(); 
		
		q.add(start);
		
		while(!q.isEmpty()) {
			
			Node u = q.remove();
			
			if(u.name.equals(end.name)) { //if(u==end) --this will work too
				System.out.println("Found");
				return true;
			}
			if(visited.contains(u)) {
				continue;
			}
			visited.add(u);
			for(Node child : u.children) {
				q.add(child);
			}
		}
		
		return false;
	}
	
	public static boolean search_2 (Graph graph, Node start, Node end) {
		
		if(start == end) return true;
		
		LinkedList<Node> q = new LinkedList<Node>();
		q.add(start);
		
		List<Node> nodes = graph.nodes;
		for (Node node : nodes) {
			node.state = State.Unvisited;
		}
		
		Node u;
		while(!q.isEmpty()) {
				
			u = q.removeFirst();
			
			if(u == end) {
				return true;
			}
			else {//get adjacent
				
				Node[] adj = u.getAdjacent();
				for (Node node : adj) {
					if(node == end) {
						return true;
					}
					else if(node.state == State.Unvisited) {
						node.state = State.Visiting;
						q.add(node);
					}
				}
				u.state = State.Visited;
			}
			
		}
		
		return false;
		
	}
	
	
	/*
	 * BFS : 
	 * 
	 * 1. Mark all the node in Graph as UNVISITED
	 * 2. Mark Start as VISITING
	 * 3. Add start to a queue
	 * 4. Dequeue queue while it's not empty
	 * 		a. dequeue queue -> get first element
	 * 		b. Get the adjacent
	 * 		c. Iterate through adjacent node and
	 * 			i. if UNVISITED and NOT equal to end node continue
	 * 			ii. if NODE := end return TRUE 
	 * 
	 *      d. Mark current Node VISITED 
	 * 
	 * */
	
	public static boolean search(Graph graph, Node start, Node end) {
		
		//--Mark all the node
		List<Node> nodes = graph.nodes;
		for(Node node : nodes) {
			node.state = State.Unvisited;
		}
		
		start.state = State.Visiting;
	
		LinkedList<Node> q = new LinkedList<Node>();
		q.add(start);
	
		Node u;
		while (q != null && !q.isEmpty()) {
			
			u = q.removeFirst();
			
			for(Node node : u.getAdjacent()) {
				if(node.state == State.Unvisited) {
					if(node.name == end.name) {
						return true;
						
					} else {
						q.add(node);
						node.state = State.Visiting;
					}
				}
				
			}
			u.state = State.Visited;
			
		}
		
		return false;
		
	}

}

class Graph {
	
	public List<Node> nodes;
//	public Node[] nodes;
}

class Node {
	
	public String name;
	public Node[] children;
	public State state;
	
	public void setChildren(Node...nodes) {
		children = nodes;
	}
	
	public Node[] getAdjacent () {
		return children;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
}
