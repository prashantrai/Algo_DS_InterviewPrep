package leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CloneGraph_133_Medium {

	public static void main(String[] args) {

		Node node_1 = new Node(1);
		Node node_2 = new Node(2);
		Node node_3 = new Node(3);
		Node node_4 = new Node(4);
		
		node_1.setNeighbors(node_2, node_4);
		node_2.setNeighbors(node_3, node_1);
		node_3.setNeighbors(node_2, node_4);
		node_4.setNeighbors(node_3, node_1);
		
		Node res = cloneGraph(node_1); 
		System.out.println(res);
		
	}
	
	/* Looks good in Leetcode submission
	 * 
	 * https://www.programcreek.com/2012/12/leetcode-clone-graph-java/
	 * 
	 * 	Time complexity:
	 *  BFS takes O(V + E). Copying neighbors also takes O(V + E). The overall time complexity is O(V + E).
	 *  
	 *  Space: O(N).
	 */
		
	public static Node cloneGraph(Node node) {
        if(node == null) 
            return node;
        
        Deque<Node> dq = new ArrayDeque<>();
        dq.offer(node);

        Map<Node, Node> map = new HashMap<>();
        map.put(node, new Node(node.val));
        
        while (!dq.isEmpty()) {
            Node curr = dq.poll();
            for(Node n : curr.neighbors) {
                if(!map.containsKey(n)) {
                    map.put(n, new Node(n.val));
                    dq.offer(n);
                }
                map.get(curr).neighbors.add(map.get(n));
            }
        }
        return map.get(node);
    }
	
	
	// Definition for a Node.
	private static class Node {
	    public int val;
	    public List<Node> neighbors;
	    
	    public Node() {
	        val = 0;
	        neighbors = new ArrayList<Node>();
	    }
	    
	    public Node(int _val) {
	        val = _val;
	        neighbors = new ArrayList<Node>();
	    }
	    
	    public Node(int _val, ArrayList<Node> _neighbors) {
	        val = _val;
	        neighbors = _neighbors;
	    }
	    
	    public void setNeighbors(Node... nodes) {
	    	for(Node node : nodes) {
	    		neighbors.add(node);
	    	}
	    }

		@Override
		public String toString() {
//			return "Node [val=" + val + ", neighbors=" + neighbors + "]";
			return "[val=" + val + ", neighbors=" + neighbors + "]";
			//return "[neighbors=" + neighbors + "]";
		}
	}

}
