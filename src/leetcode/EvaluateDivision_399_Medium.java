package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EvaluateDivision_399_Medium {

	public static void main(String[] args) {
		double[] values = {2.0,3.0}; 
		
		List<List<String>> equations = new ArrayList<>();
		equations.add(Arrays.asList("a","b"));
		equations.add(Arrays.asList("b","c"));
		
		List<List<String>> queries = new ArrayList<>();
		queries.add(Arrays.asList("a","c"));
		queries.add(Arrays.asList("b","a"));
		queries.add(Arrays.asList("a","e"));
		queries.add(Arrays.asList("a","a"));
		queries.add(Arrays.asList("x","x"));
		
		double[] result = calcEquation(equations, values, queries);
		System.out.println("Expected: [6.00000,0.50000,-1.00000,1.00000,-1.00000]");
		System.out.println("Actual: " + Arrays.toString(result));
		
	}
	
		/*
	    a/b=2, b/c=3, we could derive the following equations:
	    1) b/a=1.2 , c/b=1/3
	    2) a/c = a/b * b/c = 2*3 = 6
	
	    Reference: https://leetcode.com/problems/evaluate-division/discuss/171649/1ms-DFS-with-Explanations
	    
	    Time Complexity: O(M*N)
	
	        1. First of all, we iterate through the equations to build a graph. Each equation takes O(1) time to process. Therefore, this step will take O(N) time in total.
	
	        2. For each query, we need to traverse the graph. In the worst case, we might need to traverse the entire graph, which could take O(N). Hence, in total, the evaluation of queries could take M*(N) = M*O(N) = O(M*N).
	
	        3. To sum up, the overall time complexity of the algorithm is O(N) + O(M*N) = O(M*N)
	        
	    Space Complexity: O(N)
	        1. We build a graph out the equations. In the worst case where there is no overlapping among the equations, we would have N edges and 2N nodes in the graph. Therefore, the sapce complexity of the graph is O(N+2N) = O(3N) =O(N).
	
	        2. Since we employ the recursion in the backtracking, we would consume additional memory in the function call stack, which could amount to O(N) space.
	
	        3. In addition, we used a set visited to keep track of the nodes we visited during the backtracking. The space complexity of the visited set would be O(N).
	
	        4. To sum up, the overall space complexity of the algorithm is O(N)+O(N)+O(N)=O(N).
	
	        5. Note that we did not take into account the space needed to hold the results. Otherwise, the total space complexity would be O(N+M).
	*/
	
	public static double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
	    
	    /* Build graph. */
	    Map<String, Map<String, Double>> graph = buildGraph(equations, values);
	    
	    /* DFS - process every query*/
	    double[] result = new double[queries.size()];
	    for(int i = 0; i < queries.size(); i++) {
	        String start = queries.get(i).get(0);
	        String end = queries.get(i).get(1);
	        
	        result[i] = getPathWeight(start, end, graph, new HashSet<String>());
	    }
	    return result;
	}
	
	//DSF
	private static double getPathWeight(String start, String end, Map<String, Map<String, Double>> graph, Set<String> visited) {
	    
	    /* Rejection case. */
	    if(!graph.containsKey(start) || !graph.containsKey(end)) 
	        return -1.0;
	    
	    /* Accepting case: If the weight is already present */
	    if(graph.get(start).containsKey(end))
	        return graph.get(start).get(end);
	    
	    visited.add(start);
	    for(Map.Entry<String, Double> neighbour : graph.get(start).entrySet()) {
	        
	        if(visited.contains(neighbour.getKey())) continue;
	        
	        double productWeight = getPathWeight(neighbour.getKey(), end, graph, visited);
	        
	        if(productWeight != -1.0)
	            return neighbour.getValue() * productWeight;
	        
	    }
	    
	    return -1.0;
	}
	
	/* Build graph. */
	public static Map<String, Map<String, Double>> buildGraph(List<List<String>> equations, double[] values) {
	 
	    Map<String, Map<String, Double>> graph = new HashMap<>();
	    String src, dst;
	    for (int i = 0; i < equations.size(); i++) {
	        src = equations.get(i).get(0);  // source/start node
	        dst = equations.get(i).get(1);   // destination/end node
	        double ratio = values[i];
	        
	        graph.putIfAbsent(src, new HashMap<String, Double>());
	        graph.get(src).put(dst, ratio);
	        graph.putIfAbsent(dst, new HashMap<String, Double>());
	        graph.get(dst).put(src, 1.0/ratio);
	    }
	    return graph;
	}

}
