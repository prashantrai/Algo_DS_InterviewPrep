package test.anuj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class DijkshtraDemo {

	public static void main(String[] args) {
		
		Node_2 node_0 = new Node_2();
		Node_2 node_1 = new Node_2();
		Node_2 node_2 = new Node_2();
		Node_2 node_3 = new Node_2();
		
		node_0.name = "S";
		LinkedList<Edge> edges_0 = new LinkedList<Edge>();
		edges_0.add(new Edge("B", 24));
		edges_0.add(new Edge("C", 3));
		edges_0.add(new Edge("D", 20));
		node_0.edges = edges_0;
		
		//node_0.setChildren(node_1);
		
		node_1.name = "B";
		//node_1.setChildren(node_2);
		LinkedList<Edge> edges_1 = new LinkedList<Edge>();
		edges_1.add(new Edge("S", 24));
		node_1.edges = edges_1;
		
		node_2.name = "C";
		LinkedList<Edge> edges_2 = new LinkedList<Edge>();
		edges_2.add(new Edge("S", 3));
		edges_2.add(new Edge("D", 12));
		node_2.edges = edges_2;
		//node_2.setChildren(node_0, node_3);

		node_3.name = "D";
		LinkedList<Edge> edges_3 = new LinkedList<Edge>();
		edges_3.add(new Edge("S", 20));
		edges_3.add(new Edge("C", 12));
		node_3.edges = edges_3;

		//node_3.setChildren(node_2);
		
		List<Node_2> nodes = new ArrayList<Node_2>();
		nodes.add(node_0);
		nodes.add(node_1);
		nodes.add(node_2);
		nodes.add(node_3);
		
		HashMap<String, Node_2> nodesMap = new HashMap<String, Node_2>();
		nodesMap.put("S", node_0);
		nodesMap.put("B", node_1);
		nodesMap.put("C", node_2);
		nodesMap.put("D", node_3);
		

		Graph_2 graph = new Graph_2();
//		graph.nodes = nodes; 
		graph.nodesMap = nodesMap; 
		
		System.out.println(">>> "+ graph.nodesMap);
		
		
	}
	
	enum State {Unvisted, Vistited, Visiting};
	public static void shortestReach(Graph_2 graph, Node_2 start) {
		
		HashMap<String, Node_2> nodesMap = graph.nodesMap;
		
		LinkedList<Node_2> q = new LinkedList<Node_2>();
		q.add(start);
	
		/*for()
		
		
		while(q!= null || !q.isEmpty()) {
			
			Node_2 node = q.removeFirst();
			
			while(q != null || !q.isEmpty()) {
				
				if (node) 
			}
			
			
		}*/
		
		/*Node_2 node = nodesMap.get(start.name);
		List<Edge> edges = node.edges;

		for (Edge edge : edges) {
			
			String name = edge.nodeName;
			int cost = edge.cost;
			
			List<Edge> edges1 = nodesMap.get(name).edges;
			
		}*/
		
		
	}

}


class Graph_2 {
	public HashMap<String, Node_2> nodesMap;
}

class Node_2 {
	
	public String name;
	//public State state;
	
	public List<Edge> edges;
	
	public String toString() {
		
		return "{ name="+name
				+", edges" + edges + " }";
	}
}

class Edge {
	public String nodeName;
	public int cost;
	
	public Edge(String nodeName, int cost) {
		this.cost = cost;
		this.nodeName = nodeName;
	}
	
	public String toString() {
		
		return "{ nodeName="+nodeName
				+", cost=" + cost + " }";
	}
}



/*

{
	B={ name=B, edges[	{ nodeName=S, cost=24 }] }, 
	S={ name=S, edges[	{ nodeName=B, cost=24 }, { nodeName=C, cost=3 }, { nodeName=D, cost=20 }] }, 
	C={ name=C, edges[	{ nodeName=S, cost=3 }, { nodeName=D, cost=12 }] }, 
	D={ name=D, edges[	{ nodeName=S, cost=20 }, { nodeName=C, cost=12 }] }}

* */