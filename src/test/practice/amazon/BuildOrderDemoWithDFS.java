package test.practice.amazon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class BuildOrderDemoWithDFS {

	public static void main(String[] args) {

		//String[] projects = {"f", "c", "b", "a", "h", "e"};//, "d", "g"};
		String[] projects = {"f", "c", "b", "a", "e"};//, "d", "g"};
		String[][] dependencies = {
										{"f", "b"},
										{"f", "c"},
										{"f", "a"},
										{"b", "a"},
										{"c", "a"},
										//{"b", "h"},
										{"b", "e"},
										{"a", "e"}//,
										//{"d", "g"}
									};
		
		Stack<Project> stack = findBuildOrder(projects, dependencies);
		
		System.out.println("buildOrder:: "+stack); //--it prints in opposite order
		System.out.println("peek:: "+stack.peek());
		
		
	}
	
	public static Stack<Project> findBuildOrder(String[] projects, String[][] dependencies) {
		
		Graph graph = buildGraph(projects, dependencies);
		
		return orderProject(graph.getNodes());
		
	}
	
	public static Graph buildGraph(String[] projects, String[][] dependencies) {
		
		Graph graph = new Graph();
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
	
	public static Stack<Project> orderProject(ArrayList<Project> projects) {
		
		Stack<Project> stk = new Stack<Project>();
		HashSet<Project> visited = new HashSet<Project>();
		
		for(Project project : projects) {
			
			if (visited.contains(project)) {
				continue;
			}
			doDFS (project, stk, visited);
		}
		return stk;
		
	}
	
	public static void doDFS(Project project, Stack<Project> stack, HashSet<Project> visited){
		
		visited.add(project);
		ArrayList<Project> children = project.getChildren();
		
		for(Project child : children) {
			
			if (visited.contains(child)) {
				continue;
			}
			doDFS (child, stack, visited);
		}
		stack.push(project);
		//return true;
	}

}



class Graph {
	
	public ArrayList<Project> nodes = new ArrayList<Project>();
	public HashMap<String, Project> map = new HashMap<String, Project>();
	
	public void getOrCreateNode(String name) {
		if(!map.containsKey(name)) {
			Project project = new Project(name);
			nodes.add(project);
			map.put(name, project);
		}
	}
	
	public void createEdges(String startName, String endName) {
		Project project = map.get(startName);
		project.addNeighbour(map.get(endName));
	}

	public ArrayList<Project> getNodes() {
		return nodes;
	}
	
}

class Project {
	
	public enum State {COMPLETE, PARTIAL, BLANK};
	private ArrayList<Project> children = new ArrayList<Project>();
	private HashMap<String, Project> map = new HashMap<String, Project>();
	private String name;
	private State state = State.BLANK;
	
	public Project(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void addNeighbour(Project node) {
		if(!map.containsKey(node.getName())) {
			children.add(node);
			map.put(name, node);
		}
	}
	
	public ArrayList<Project> getChildren() {
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