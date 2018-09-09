package test.practice.misc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class FlatMapDemo {

	public static void main(String[] args) {
		Map<String, Object> map0 = new HashMap<>();
        map0.put("key1", "value1");
        map0.put("key2", "value2");
        Map<String, Object> map1 = new HashMap<>();
        map0.put("key3", map1);
        map1.put("key3.1", "value3.1");
        map1.put("key3.2", "value3.2");
        Map<String, Object> map2 = new HashMap<>();
        map1.put("key3.3", map2);
        map2.put("key3.3.1", "value3.3.1");
        map2.put("key3.3.2", "value3.3.2");
        
        System.out.println("Input >> "+ map0);
        
        Map<String, Object> out = new HashMap<>();
        flatMap(map0, out);
        
        System.out.println(out);
        
        PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(Collections.reverseOrder());
        
	}
	
	//--Working solution
	public static void flatMap (Map<String, Object> in_map, Map<String, Object> out_map) {
		
		if(in_map == null || in_map.size() == 0) return;
		
		Set<String> keySet = in_map.keySet();
		for (String key : keySet) {
			
			Object o = in_map.get(key); 
			
			if( o instanceof Map<?, ?>) {
				
				flatMap((Map<String, Object>) o, out_map); 
			} else {
				out_map.put(key, in_map.get(key));
			}
			
		}
		
	} 

}
