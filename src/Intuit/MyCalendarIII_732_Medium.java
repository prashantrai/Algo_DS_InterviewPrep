package Intuit;

import java.util.TreeMap;

public class MyCalendarIII_732_Medium {

	public static void main(String[] args) {
		MyCalendarThree obj = new MyCalendarThree();
		System.out.println(obj.book(10, 20)); // returns 1
		System.out.println(obj.book(50, 60)); // returns 1
		System.out.println(obj.book(10, 40)); // returns 2
		System.out.println(obj.book(5, 15)); // returns 3
		System.out.println(obj.book(5, 10)); // returns 3
		System.out.println(obj.book(25, 55)); // returns 3
	}

	// TODO - Solve this problem using Segment Tree for better Runtime complexity 
	
	/* 
	 * 
	 * 
	 * https://leetcode.com/problems/my-calendar-iii/
	 * https://leetcode.com/problems/my-calendar-iii/solution/
	 * 
	 */
	private static class MyCalendarThree {
	    TreeMap<Integer, Integer> map;
	    public MyCalendarThree() {
	        map = new TreeMap<>();
	    }
	    
	    /*Complexity Analysis
	        Time Complexity: O(N^2), where N is the number of events booked. For each new event, we traverse map in O(N) time.

	        Space Complexity: O(N), size of map.
	        
	        NB: Use Segment tree for better runtime complexity
	    */
	    
	    public int book(int start, int end) {
	        map.put(start, map.getOrDefault(start, 0)+1); // a new event starts at 'start' that's why +1 the count
	        map.put(end, map.getOrDefault(end, 0)-1); //an old/existing event ends at 'end' that's why -1 the count
	        
	        int active = 0;
	        int ans = 0;
	        
	        for(int event : map.values()) {
	            active += event;
	            ans = active > ans ? active : ans;
	        } 
	        return ans;
	    }
	}
}
