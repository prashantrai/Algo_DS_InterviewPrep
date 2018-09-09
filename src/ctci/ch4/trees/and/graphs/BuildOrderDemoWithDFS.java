package ctci.ch4.trees.and.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class BuildOrderDemoWithDFS {

	public static void main(String[] args) {

		String[] projects = {"f", "c", "b", "a", "h", "e"};//, "d", "g"};
		String[][] dependencies = {
										{"f", "b"},
										{"f", "c"},
										{"b", "a"},
										{"c", "a"},
										{"b", "h"},
										{"b", "e"},
										{"a", "e"}//,
										//{"d", "g"}
									};
		
		Stack<Project_2> stack = findBuildOrder(projects, dependencies);
		
		System.out.println("buildOrder:: "+stack); //--it prints in opposite order
		System.out.println("peek:: "+stack.peek());
		
		
	}
	
	public static Stack<Project_2> findBuildOrder(String[] projects, String[][] dependencies) {
		
		Graph_3 graph = buildGraph(projects, dependencies);
		
		return orderProject(graph.getNodes());
		
	}
	
	public static Graph_3 buildGraph(String[] projects, String[][] dependencies) {
		
		Graph_3 graph = new Graph_3();
		for(String project : projects) {
			graph.getOrCreateNode(project);
		}
		
		for (String[] dependency : dependencies) {
			String start = dependency[0];
			String end   = dependency[1];
			graph.createEdges(start, end);
		}
		return graph;
	}
	
	public static Stack<Project_2> orderProject(ArrayList<Project_2> projects) {
		
		Stack<Project_2> stack = new Stack<Project_2>();
		for (Project_2 project : projects) {
			
			if(project.getState() == Project_2.State.BLANK) {
				if(!doDFS(project, stack)) 
					return null;
			}
		}
		return stack;
	}
	
	public static boolean doDFS(Project_2 project, Stack<Project_2> stack){
		
		if(project.getState() == Project_2.State.PARTIAL)  //--cycle 
			return false; 
		
		if(project.getState() == Project_2.State.BLANK) {
			project.setState(Project_2.State.PARTIAL);
			
			for(Project_2 child : project.getChildren()) {
				if(!doDFS(child, stack)) {
					return false;
				}
			}
			project.setState(Project_2.State.COMPLETE);
			stack.push(project);
		}
		
		return true;
	}

}

class Graph_3 {
	
	public ArrayList<Project_2> nodes = new ArrayList<Project_2>();
	public HashMap<String, Project_2> map = new HashMap<String, Project_2>();
	
	public void getOrCreateNode(String name) {
		if(!map.containsKey(name)) {
			Project_2 project = new Project_2(name);
			nodes.add(project);
			map.put(name, project);
		}
	}
	
	public void createEdges(String startName, String endName) {
		Project_2 project = map.get(startName);
		project.addNeighbour(map.get(endName));
	}

	public ArrayList<Project_2> getNodes() {
		return nodes;
	}
	
}

class Project_2 {
	
	public enum State {COMPLETE, PARTIAL, BLANK};
	private ArrayList<Project_2> children = new ArrayList<Project_2>();
	private HashMap<String, Project_2> map = new HashMap<String, Project_2>();
	private String name;
	private State state = State.BLANK;
	
	public Project_2(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void addNeighbour(Project_2 node) {
		if(!map.containsKey(node.getName())) {
			children.add(node);
			map.put(name, node);
		}
	}
	
	public ArrayList<Project_2> getChildren() {
		return children;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return state;
	}
	
	public String toString() {
		return name;
	}
}

