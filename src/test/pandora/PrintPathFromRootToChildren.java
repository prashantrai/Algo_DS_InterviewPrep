package test.pandora;

import java.util.LinkedList;
import java.util.List;

public class PrintPathFromRootToChildren {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	static class Node{
		   String value;
		   List<Node> children;
		   State state;

		   public String getValue(){
		        return value;
		   }

		   public List<Node> getChildren(){
		        return children;
		   }
		}

		//public String getPathToValue(String value, Node root);

		//example: getPathToValue("Dog",root) returns "Horse-Cat-Truck-Patriots-Dog";

		//--There's only a one to one relationship downward.  
		//--Meaning a node only can have one parent. Every parent can have 0..n children.

		//—Values are unique.

		enum State {Unvisited, Visited, Visiting}
		public String getPathToValue(String value, Node root) {

		   List<Node> children = root.getChildren();
		   
		   for(Node node : children) {
		       node.state = State.Unvisited;
		   
		   }
		   
		   root.state = State.Visiting;
		   
		   LinkedList<Node> q = new LinkedList<Node>(); //--acting as queue
		   q.add(root);
		   
		   //String result = "";
		   //result += root.value;
		   
		   StringBuilder result = new StringBuilder();
		   result.append(root.value);
		   
		   Node u;
		   while(q != null && !q.isEmpty()) {
		   
		       u = q.removeFirst();   //dequeue element
		       
		       for(Node node : children){
		           if(node.state == State.Unvisited) {
		               if(node.value == value) {     //-Dog
		                   result.append(root.value);
		                   
		                   return result.toString();
		               } else {
		                   q.add(node);
		                   node.state = State.Visiting;
		                   result.append(node.value);
		                   result.append("-");
		                   //result += node.value;
		               }
		           }
		       }
		       
		       u.state = State.Visited;
		   }
		   
		   return null;

		}

}
