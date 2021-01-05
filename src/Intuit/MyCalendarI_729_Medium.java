package Intuit;

import java.util.TreeMap;

public class MyCalendarI_729_Medium {

	public static void main(String[] args) {
		MyCalendar obj = new MyCalendar();
		obj.book(10, 20); // returns true
		obj.book(15, 25); // returns false
		obj.book(20, 30); // returns true
		
		MyCalendar obj2 = new MyCalendar();
		obj2.book2(10, 20); // returns true
		obj2.book2(15, 25); // returns false
		obj2.book2(20, 30); // returns true
	}
	
	
	
	/* TreeMap is a red/black tree implementation of NavigableMap interface
    	O(log n) insertion, O(log n) search
    
    	Maintains keys in a sorted order, can pass in a comparator class to maintain order

		Complexity Analysis: 

		Time: 	O(NlogN), where N is the number of events booked. 
				For each new event, we search that the event is legal 
				in O(logN) time, then insert it in O(1) time.
		
		Space: O(N), the size of the data structures used. 
	 * */

	private static class MyCalendar {
		TreeMap<Integer, Integer> calendar;
		public MyCalendar() {
			calendar = new TreeMap<>();
	    }
	    
		/*
		 * Why floorKey and ceilingKey? 
		 * 
		 * Note: TreeMap will keep the data in sorted order (by key) 
		 * 
		 * After add the first entry in TreeMap entry will be like {start=end} (e.g. {10=20})
		 * If we have multiple entry in TreeMap and we want to add a new (i.e. start and end)
		 * 
		 * Now, to find out if the new slot is valid we will check that existing endTime/prevEnd (value against the floor key (e.g. in {10=20} floorKey is 10 and value is 20) 
		 * closest to start) is less than and equal to input "start" 
		 * 
		 * AND  input "end" is less than or equal to the ceiling key 
		 * and NOT VALUE (e.g. in {10=15, 20=30} ceiling key for input 15,20 will be 20 )
		 * 
		 * 
		 * */
		
	    public boolean book(int start, int end) {
	        Integer prev = calendar.floorKey(start);  // returns the greatest key less than or equal to key, or null if there is no such key.
	        Integer prevEnd = prev != null ? calendar.get(prev) : null;
	        
	        Integer next = calendar.ceilingKey(start); // returns the least key greater than or equal to key, or null if there is no such key.
	        
	        if( (prevEnd == null || prevEnd <= start) 
	        		&& (next == null || end <= next) ) { // end should be less than other start i.e. it should lie between 2 slots
	        	
	        	calendar.put(start, end);
	        	return true;
	        }
	        
	        return false;
	    }
	    
	    // using lower key - 2nd approach
	    public boolean book2(int start, int end) {
	    	Integer low = calendar.lowerKey(end); // Returns the greatest key strictly less than the given key, or null if there is no such key.
	    	
	    	if(low == null || calendar.get(low) <= start) { // prevEnd <= start
	    		calendar.put(start, end);
	        	return true;
	        }
	        
	        return false;
	    }
	}
}
