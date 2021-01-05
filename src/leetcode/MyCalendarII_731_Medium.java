package leetcode;

import java.util.ArrayList;
import java.util.List;

public class MyCalendarII_731_Medium {

	public static void main(String[] args) {

		MyCalendarTwo obj = new MyCalendarTwo();
		System.out.println("Expected: true, Actual: " + obj.book(10, 20)); // returns true
		System.out.println("Expected: true, Actual: " + obj.book(50, 60)); // returns true
		System.out.println("Expected: true, Actual: " + obj.book(10, 40)); // returns true
		System.out.println("Expected: false, Actual: " + obj.book(5, 15)); // returns false
		System.out.println("Expected: true, Actual: " + obj.book(5, 10)); // returns true
		System.out.println("Expected: true, Actual: " + obj.book(25, 55)); // returns true
		
	}
	
	private static class MyCalendarTwo {
	    
	    List<int[]> events;
	    List<int[]> overlaps;
	    
	    public MyCalendarTwo() {
	        events = new ArrayList<>();
	        overlaps = new ArrayList<>();
	    }
	    
	    public boolean book(int start, int end) {
	        
	        for (int[] overlap : overlaps) {
	            if(overlap[0] < end && overlap[1] > start) 
	                return false;
	        }
	        
	        for (int[] event : events) {
	            if(event[0] < end && event[1] > start) {
	                overlaps.add(new int[]{Math.max(event[0], start), Math.min(event[1], end)});
	            }
	        }
	        
	        events.add(new int[]{start, end});
	        
	        return true;
	    }
	}

}
