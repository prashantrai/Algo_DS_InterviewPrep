package Bloomberg;

import java.util.*;
//import javafx.util.*;

public class DesignUndergroundSystem_1396_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	class UndergroundSystem {
	    
	    /*
	    In Java 8, a Pair<V, K> is added in javafx.util package. The class represent key-value pairs and supports very basic operations like getKey(), getValue(), hashCode(), equals(java.lang.Object o).
	    */
	    
	    /*
	    Time: O(1) for all i.e. checkIn(), checkOut() and getAverageTime();
	    Space:  O(P + S^2), where S is the number of stations on the network, and P is the number of passengers making a journey concurrently during peak time.
	    
	        i. The program uses two HashMaps. We need to determine the maximum sizes these could become.

	        ii. Firstly, we'll consider checkInData. This HashMap holds one entry for each passenger who has checkIn(...)ed, but not checkOut(...)ed. Therefore, the maximum size this HashMap could be is the maximum possible number of passengers making a journey at the same time, which we defined to be P. Therefore, the size of this HashMap is O(P).

	        iii. Secondly, we need to consider journeyData. This HashMap has one entry for each pair of stations that has had at least one passenger start and end a journey at those stations. Over time, we could reasonably expect every possible pair of the S stations on the network to have an entry in this HashMap, which would be O(S^2).

	        iv. Seeing as we don't know whether S^2 or P is larger, we need to add these together, giving a total space complexity of O(P + S^2).
	    */

	    Map<Integer, Pair<String, Integer>> checkInMap;  // Uid - {StationName, Time}
	    Map<String, Pair<Double, Integer>> routeMap;    // RouteName - {TotalTime, Count}
	    
	    public UndergroundSystem() {
	        checkInMap = new HashMap<>();
	        routeMap = new HashMap<>();
	    }
	    
	    public void checkIn(int id, String stationName, int t) {
	        checkInMap.put(id, new Pair(stationName, t));
	    }
	    
	    public void checkOut(int id, String stationName, int t) {
	        Pair<String, Integer> checkIn = checkInMap.get(id);
	        checkInMap.remove(id); // trip completed and we don't need checIn details in checkIn Map
	        
	        String routeName = checkIn.getKey() + "_" + stationName;
	        int totalTimeTaken = t - checkIn.getValue(); // time taken to complete the trip
	        
	        // if entry exist for the routeName in routeMap 
	        // then fetch that value i.e. Pair and update TotalTime and Count
	        
	        Pair<Double, Integer> route 
	        	= routeMap.getOrDefault(routeName, new Pair<Double, Integer>(0.0, 0));
	        Pair<Double, Integer> currRoute
	            = new Pair<>(route.getKey() + totalTimeTaken, route.getValue() + 1);
	        
	        routeMap.put(routeName, currRoute);
	    }
	    
	    public double getAverageTime(String startStation, String endStation) {
	        String routeName = startStation + "_" + endStation;
	        Pair<Double, Integer> route = routeMap.get(routeName);
	        return route.getKey() / route.getValue();
	    }
	}

	
	class Pair<K, V>{
	    K key;
	    V value;
	    public Pair(K key , V value){
	        this.key = key;
	        this.value =  value;
	    }
		public K getKey() {
			return key;
		}
		public void setKey(K key) {
			this.key = key;
		}
		public V getValue() {
			return value;
		}
		public void setValue(V value) {
			this.value = value;
		}
		@Override
		public int hashCode() {
			return Objects.hash(key, value);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Pair other = (Pair) obj;
			
			return Objects.equals(this, other);
		}
		
	}
	
	
	/**
	 * Your UndergroundSystem object will be instantiated and called as such:
	 * UndergroundSystem obj = new UndergroundSystem();
	 * obj.checkIn(id,stationName,t);
	 * obj.checkOut(id,stationName,t);
	 * double param_3 = obj.getAverageTime(startStation,endStation);
	 */

}
