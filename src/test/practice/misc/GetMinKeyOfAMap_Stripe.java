package test.practice.misc;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class GetMinKeyOfAMap_Stripe {

	public static void main(String[] args) {

		
		Map<String, Double> map = new HashMap<String, Double>();
		map.put("1.1", 1.11);
		map.put("0.1", 0.1);
		map.put("2.1", 2.1);

		//Double min = Collections.min(map.values());
		//System.out.println(min); // 0.1
		
		Entry<String, Double> min = null;
		for (Entry<String, Double> entry : map.entrySet()) {
		    if (min == null || min.getValue() > entry.getValue()) {
		        min = entry;
		    }
		}
		System.out.println(min);
		System.out.println(min.getKey()); 
		System.out.println(min.getValue());
		
		TreeMap<String, Double> treeMap = new TreeMap<>();
		treeMap.putAll(map);
		System.out.println("MinKey: " + treeMap.firstKey());
	}
	
	

}
