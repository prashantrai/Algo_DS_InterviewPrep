package Coinbase;

import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class CurrencyConversion {

	public static void main(String[] args) {
		
		List<Node> data = new ArrayList<Node>();
        data.add(new Node("USD", "JPY", 110));
        data.add(new Node("USD", "AUD", 1.45));
        data.add(new Node("JPY", "GBP", 0.0070));
        
        Double result = getRatio("GBP", "AUD", data);
        System.out.println("1. Result: " + result);
        
        
        System.out.format("2. Result : %.2f", result);
        
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println("\n3. Result: " + df.format(result));
        
        result = Math.round(result * 100.0) / 100.0; //for 2 decimal places
        System.out.println("4. Result: " + result);
        

	}

	/*
	Question: https://leetcode.com/discuss/interview-question/483660/google-phone-currency-conversion
	
	array of currency conversion rates. E.g. ['USD', 'GBP', 0.77] which means 1 USD is equal to 0.77 GBP
	an array containing a 'from' currency and a 'to' currency
	Given the above parameters, find the conversion rate that maps to the 'from' currency to the 'to' currency.
	Your return value should be a number.

	Example:
	You are given the following parameters:

	Rates: ['USD', 'JPY', 110] ['US', 'AUD', 1.45] ['JPY', 'GBP', 0.0070]
	To/From currency ['GBP', 'AUD']
	Find the rate for the 'To/From' currency. In this case, the correct result is 1.89.
	*/
	
	private static double getRatio(String start, String end, List<Node> data) {
		
		Map<String, Map<String, Double>> map = buildAndGetAdjacencyMap(data);
		
		Queue<String> q = new ArrayDeque<>();
		Queue<Double> val = new ArrayDeque<>();
		Set<String> visited = new HashSet<>();
		
		q.offer(start);
		val.offer(1.0);
		
		while (!q.isEmpty()) {
			String cur = q.poll();
			double num = val.poll(); 
			if(visited.contains(cur)) 
				continue;
			
			visited.add(cur);
			
			if(map.containsKey(cur)) {
				Map<String, Double> next = map.get(cur);
				for(String key : next.keySet()) {
					
					if(!visited.contains(key)) {
						if(key.equals(end)) 
							return num * next.get(key);
						
						q.offer(key);
						val.offer(num * next.get(key));
					}
				}
			}
			
			
		}
		
		return -1;
	}
	
	// build graph to store the relationship
	private static Map<String, Map<String, Double>> buildAndGetAdjacencyMap(List<Node> data) {
		
		Map<String, Map<String, Double>> graph = new HashMap<>();
		
		for(Node node : data) {
			//map start with end
			if(!graph.containsKey(node.start))
				graph.put(node.start, new HashMap<String, Double>());
			
			graph.get(node.start).put(node.end, node.ratio);
			
			//map end with start along with ratio e.g. 1.0/ratio
			if(!graph.containsKey(node.end))
				graph.put(node.end, new HashMap<String, Double>());
			
			graph.get(node.end).put(node.start, 1.0/node.ratio);
		}
		
		return graph;
	}
	
	private static class Node {
        String start;
        String end;
        double ratio;
        public Node(String s, String e, double r) {
            this.start = s;
            this.end = e;
            this.ratio = r;
        }
    }
}
