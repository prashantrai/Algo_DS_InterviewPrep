package ctci.ch4.trees.and.graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/*
 * For time complexity of both BFS and DFS, see below link
 * 
 * https://stackoverflow.com/questions/11468621/why-is-the-time-complexity-of-both-dfs-and-bfs-o-v-e
 * https://stackoverflow.com/questions/26549140/breadth-first-search-time-complexity-analysis
 * */


enum State {Unvisited, Visited, Visiting;}

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
	
	public static boolean search(Graph graph, Node start, Node end) {
		
		if(start == end) return true;
		
		LinkedList<Node> q = new LinkedList<Node>();
		
		for (Node node : graph.nodes) {
			node.state = State.Unvisited;
		}
		
		start.state = State.Visiting;
		q.add(start);
		Node u;
		while (q != null && !q.isEmpty()) {
			u = q.removeFirst();  //--dequeue
			if(u != null) {
				
				for(Node node : u.getAdjacent()) {
					
					if(node.state == State.Unvisited) {
						if(node.name == end.name) {
							return true;
							
						} else {
							node.state = State.Visiting;
							q.add(node);
						}
					}
				}
				u.state = State.Visited;
			}
			
		}
		
		
		return false;
	}
	
	public static List<Node> listOfAdjacentNodes(Node ...nodes) {
		
		List<Node> adjacentNodes = new ArrayList<Node>();
		for (Node node : nodes) {
			adjacentNodes.add(node);
		}
		return adjacentNodes;
	}
 }

class Graph {
	//public Node[] nodes;
	public List<Node> nodes;
}

class Node {
	public String name;
	public Node[] children;
	public State state;
	
	public Node[] getChildren() {
		return children;
	}
	public void setChildren(Node... children) {
		this.children = children;
	}
	
	public Node[] getAdjacent() {
		return children;
	}
	
	
	
}

