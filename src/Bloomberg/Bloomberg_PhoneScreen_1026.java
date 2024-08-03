package Bloomberg;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class Bloomberg_PhoneScreen_1026 {

	public static void main(String[] args) {

		executeTrade("MSFT", 400);
		executeTrade("IBM", 1000);
		executeTrade("AAPL", 500);
		executeTrade("AAPL", 600);
		printTopStocks(2);
		System.out.println("\n -------- \n");
		executeTrade("NFLX", 1000);
		executeTrade("AMZN", 700);
		executeTrade("GOGL", 300);
		printTopStocks(4);

		/*
		 * the above code should produce the following output AAPL|1100 IBM|1000
		 * AAPL|1100 NFLX|1000 IBM|1000 AMZN|700
		 */
	}

	// MostTradedStocks
	static TreeMap<String, Integer> map 
    = new TreeMap<>(valueComparator());

	public static void executeTrade(String name, int amt) {

		//System.out.println("map: " + map);

		if (!map.containsKey(name)) {
			map.put(name, amt);
		} else {
			map.put(name, map.get(name) + amt);
		}

	}

	// O(k) where k is top k
	public static void printTopStocks(int n) {
		
		System.out.println(">>> sorted by value treemap:: " + map);
		
		TreeMap<Integer, String> map2 
	    = new TreeMap<>(Collections.reverseOrder());
		
		for (String name : map.keySet()) {
			map2.put(map.get(name), name);
		}
		
		int count = n;
		for (int i : map2.keySet()) {
			count--;
			System.out.println(map2.get(i) + "/" + i);

			if (count == 0)
				break;
		}
		
	}
	
	public static Comparator valueComparator() {
		
		Comparator comp = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String k1 = (String) o1;
				String k2 = (String) o2;
				int v1 = map.get(k1);
				int v2 = map.get(k2);
				
				return Integer.compare(v2, v1);
			}
			
		};
		
		return comp;
	}

}
