package ctci.ch4.trees.and.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BuildOrderDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	//--Project[] findBuildOrder
	public static Project[] findBuildOrder(String[] nodes, String[][] dependencies) {
		
		Graph_2 graph = buildGraph(nodes, dependencies);
		
		return orderProject(graph.getNodes());
		
	}
	
	public static Graph_2 buildGraph(String[] nodes, String[][] dependencies) {
		
		Graph_2 graph = new Graph_2();
		
		//--Create Graph
		for(String node : nodes) {
			graph.getOrCreateNode(node);
		}
		
		//--Add children
		for (String[] dependency : dependencies) {
			String start = dependency[0];
			String end = dependency[1];
			
			graph.addEdges(start, end);
		}
		
		return graph;
	}
	
	
	public static Project[] orderProject(ArrayList<Project> nodes) {
		
		Project[] order = new Project[nodes.size()];
		
		//--add nodes with no dependency in order
		int endOfIndex = addNonDependentNodes(nodes, order, 0);
		
		int toBeProcessed = 0;
		
		while(toBeProcessed < order.length) {
			
			Project current = order[toBeProcessed];
			
			if(current == null) return null;
		
			//--reduce depedencies
			ArrayList<Project> children = current.getChildren();
			for (Project child : children) {
				child.decrementDependencies();
			}
			
			endOfIndex = addNonDependentNodes(nodes, order, endOfIndex);
			
			toBeProcessed++;
		}
		return order;
		
	}
	
	public static int addNonDependentNodes(ArrayList<Project> nodes, Project[] order, int offset) {
		
		for(Project node : nodes) {
			if(node.getDependency() == 0) {
				order[offset] = node;
				offset++;
			}
		}
		
		return offset;
	}
	
	
	
}

class Graph_2 {
	
	ArrayList<Project> nodes = new ArrayList<Project> ();
	Map<String, Project> map = new HashMap<String, Project>();

	public void getOrCreateNode(String name) {
		if(!map.containsKey(name)) {
			Project project = new Project(name);
			map.put(name, project);
			nodes.add(project);
		}
	}
	
	public void addEdges(String startName, String endName) {
		
		Project start = map.get(startName);
		Project end = map.get(endName);
		
		start.addNeighbour(end);
		
	}
	
	public ArrayList<Project> getNodes() {
		return nodes;
	}
	
}

class Project{
	
	ArrayList<Project> children = new ArrayList<Project> ();
	Map<String, Project> map = new HashMap<String, Project>();
	int dependencies = 0;
	String name;

	public Project(String name) {
		this.name = name;
	}
	
	public String getName() { return name; }
	
	public void addNeighbour(Project node) {

		if(!map.containsKey(node.getName())) {
			map.put(node.getName(), node);
			children.add(node);
			
			node.incrementDependencies();
		}
		
	}
	
	public void incrementDependencies() { dependencies++; }
	public void decrementDependencies() { dependencies--; }
	public int getDependency() { return dependencies; }
	
	public ArrayList<Project> getChildren() {
		return children;
	}
	
}



	//--buildGraph
	/*public static Graph buildGraph(String[] nodes, String[][] dependencies) {
		
		//--build Graph
		Graph graph = new Graph();
		for (String node : nodes) {
			graph.getOrCreateNode(node);
		}
		
		//--create edges
		for (String[] dependency : dependencies) {
			String start = dependency[0];
			String end = dependency[1];
			graph.addEdge(start, end);
		}

		return graph;
		
	}
	//--order project
	public static Project[] orderProject(ArrayList<Project> projects) {
		
		Project[] order = new Project[projects.size()];
		
		int endOfList = addNonDepedent(order, projects, 0);
		
		int toBeProcessed = 0;
		
		while(toBeProcessed < order.length) {
			
			Project current = order[toBeProcessed];
			
			if(current == null) return null;
			
			ArrayList<Project> children = current.getChildren();
			
			for(Project child : children) {
				child.decrementDependencies();
			}
			
			endOfList = addNonDepedent(order, projects, endOfList);
			toBeProcessed++;
		}
		
		return order;
	}
	
	//--addNonDependent 
	public static int addNonDepedent(Project[] order, ArrayList<Project> projects, int offset) {
		for (Project project : projects) {
			if(project.getDependencies() == 0) {
				order[offset] = project;
				offset++;
			}
		}
		return offset;
	}
	
	
}


//--Graph
	class Graph {
		public ArrayList<Project> nodes = new ArrayList<Project> ();
		public Map<String, Project> map = new HashMap<String, Project>();
		
		
		public Project getOrCreateNode(String name) {
			
			if(!map.containsKey(name)) {
				Project project = new Project(name);
				nodes.add(project);
				map.put(name, project);	
			}
			return map.get(name);
		}
		
		public void addEdge(String startName, String endName) {
			Project start = getOrCreateNode(startName);
			Project end = getOrCreateNode(endName);

			start.addNeighbour(end);
		}
		
		
		public ArrayList<Project> getNodes() {
			return nodes;
		}
	}
	
	//--Project
	class Project {
		ArrayList<Project> children = new ArrayList<Project> ();
		Map<String, Project> map = new HashMap<String, Project>();
		String name;
		int dependencies = 0;
		
		public Project(String name) {
			this.name = name;
		}
		
		public void addNeighbour(Project end) {
			if(!map.containsKey(end.getName())) {
				map.put(end.getName(), end);
				children.add(end);
			}
			
		}
		
		public String getName() {
			return name;
		}
		
		public void incrementDependencies() { dependencies++;}
		public void decrementDependencies() { dependencies--;}
		
		public int getDependencies() {return dependencies;}
		public ArrayList<Project> getChildren() {
			return children;
		}
		
	}
*/
