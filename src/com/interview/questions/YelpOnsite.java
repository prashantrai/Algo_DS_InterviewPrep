package com.interview.questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class YelpOnsite {

	/**
	 * 1. Implement algo to solve overlapping times
	 * 	e.g Input 1 : start time = 10 and end time = 14 same day  
	 * 	    Input 2 : start time = 8 and end time = 11  same day
	 * 	    Input 3 : start time = 15 and end time = 18  same day
	 *      Output: 8, 14  and 15, 18
	 *      
	 *      Solution: 1. Create two arrays for start time and end time and sort them
	 *      2. Now iterate through both the array and compare the time of one index in both the array to next 
	 *      3. save them in result arr
	 *      
	 * 2. Design a DS to maintain a system Yelp-Love. 
	 * 	   In this system one person can appreciate other person/colleague. Implement a data structure 
	 * 	   to display the person/s
	 *     with maximum number of appreciation. Follow-up: Print top 5
	 *     
	 *     Solution: One approach could be to use the HashMap<Ranking(Integer), List<Employees with same rank>>
	 *     get the all the keys (hashmap) and sort them and then take the top 5 value from HashMap
	 *     
	 * 3. Implement an n-ary tree for below,
	 *    In an organization one manager can have n people reporting and then those people may other people 
	 *    reporting to them.
	 *    Design and n-ary tree to maintain this. What would be run-time complexity of implementing this DS.
	 *    
	 * 4. Design APIs to print the menu of restaurant serving a dish. Write API, design DB and implement a 
	 *    query to fetch that data.  
	 *     
	 * */
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		overLappingTimes();

		//--using comparator
		ArrayList<Times> mergedIntervals = overLappingIntervals(getIntervals());
		
	}
	
	public static void overLappingTimes() {
		
		int[] startArr = {10, 8, 15, 16};
		int[] endArr = {14, 11, 16, 18};
		
		Arrays.sort(startArr);
		Arrays.sort(endArr);
		
		System.out.println("startArr="+Arrays.toString(startArr));
		System.out.println("endArr="+Arrays.toString(endArr));
		
		List<Times> res = new ArrayList<Times>();
		
		int s = 0;
		int e = 0;
		Times time = null;
		
		/*      Input 1 : start time = 10 and end time = 14 same day  
		 * 	    Input 2 : start time = 8 and end time = 11  same day
		 * 	    Input 3 : start time = 15 and end time = 18  same day
		 *      Output: 8, 14  and 15, 18
		 *      
		 *      8, 10, 15
		 *      11, 14, 18
		*/
		while (s < startArr.length) {
			int stTime = startArr[s];
			int endTime = endArr[e];
			
			if(s == 0 && e == 0) {
				time = new Times(stTime, endTime);
				res.add(time);
			} else {
				
				if(stTime > time.start && stTime < time.end ) {
					if(endTime > time.end) {  //--start time overlapped
						time.end = endTime;
					}
				}
				if(stTime > time.start && stTime > time.end) { //No overlapping
					res.add(new Times(stTime, endTime));
				}
				if(stTime > time.start && endTime < time.end) {
					//do nothing
				}
				
			}
			s++;
			e++;
		}
		
		System.out.println(">>"+res);
			
	}

	
	public static ArrayList<Times> overLappingIntervals (ArrayList<Times> intervals) {
		
		if(intervals == null || intervals.size() == 0) 
			return intervals;
		
		if(intervals.size() == 1) {
			return intervals;
		}
		
		System.out.println("Before sorting:: " + intervals);
		Collections.sort(intervals, new TimesComparator());
		System.out.println("After sorting:: " + intervals);
		
		ArrayList<Times> result = new ArrayList<Times>();

		int start = intervals.get(0).start;
		int end = intervals.get(0).end;
		
		for (int i=1; i<intervals.size(); i++) {
			
			Times current = intervals.get(i);
			
			if(current.start <= end) {  //--If start times lies between previous interval, update the end time with max  
				end = Math.max(end, current.end);
			} else {
				result.add(new Times(start, end));
				start = current.start;
				end = current.end;
			}
		
		}
		result.add(new Times(start, end));
		
		System.out.println("result:: "+result);
		
		return result;
	} 
	
	public static ArrayList<Times> getIntervals() {
		int[] startArr = {10, 8, 15, 16};
		int[] endArr = {14, 11, 16, 18};
		
		ArrayList<Times> times = new ArrayList<Times>();
		
		for(int i=0; i<startArr.length; i++) {
			times.add(new Times(startArr[i], endArr[i]));
		}
		
		return times;
	}


}
	
	
class Times {
	int start;
	int end;
	public Times(int start, int end) {
		this.start = start;
		this.end = end;
	}
	public String toString() {
		return "{"+start
				+", "+end + "}";
	}
}

class TimesComparator implements Comparator {

	
	public int compare(Object o1, Object o2) {
		
		Times t1 = (Times) o1;
		Times t2 = (Times) o2;
		
		return t1.start - t2.start;
	}
}
