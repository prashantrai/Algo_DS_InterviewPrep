package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class EmployeeFreeTime_759_Hard {

	public static void main(String[] args) {
		
		//[[[1,2],[5,6]],[[1,3]],[[4,10]]];
		List<List<Interval>> schedule = new ArrayList<>();
		schedule.add(Arrays.asList(new Interval(1,2), new Interval(5,6)));
		schedule.add(Arrays.asList(new Interval(1,3)));
		schedule.add(Arrays.asList(new Interval(4,10)));
		
		System.out.println("Input: " + schedule);
		
		// with priority queue
		List<Interval> res = employeeFreeTime(schedule);
		System.out.println("1. Expeted: [[3,4]]; Actual: " + res);
		
		//with array list
		res = employeeFreeTime2(schedule);
		System.out.println("2. Expeted: [[3,4]]; Actual: " + res);
		
		
		// [[[1,3],[6,7]],[[2,4]],[[2,5],[9,12]]]
		List<List<Interval>> schedule2 = new ArrayList<>();
		schedule2.add(Arrays.asList(new Interval(1,3), new Interval(6,7)));
		schedule2.add(Arrays.asList(new Interval(2,4)));
		schedule2.add(Arrays.asList(new Interval(2,5), new Interval(9,12)));
		
		System.out.println("\n\nInput: " + schedule2);
		
		// with priority queue
		res = employeeFreeTime(schedule2);
		System.out.println("3. Expeted: [[5,6],[7,9]]; Actual: " + res);
		
		//with array list
		res = employeeFreeTime2(schedule2);
		System.out.println("4. Expeted: [[5,6],[7,9]]; Actual: " + res);

	}

	// Hint: Similar to merge interval - MergeIntervals_56_Medium.java
	/*
	 * 1. Add the data in a sorted order to a new list (use Collections.sort) 
	 * 		OR use Priority Queue 
	 * 2. Iterate the new list of intervals and add compare 2 intervals based prev and current and
	 * 		add the new interval in list i.e. prev.end and curr.start if they are not overlapping  
	 * */
	
	
	/* Time : O(n logn), N is working time period of all employees
	 * Space : O(n)
	 * 
	 * Priority Queue :: 
	 * 	Insertion cost in priorityQueue is log(N) for each element i.e. for N elements it's N Log(N)
	 * 	it can be O(n) if the input is sorted
	 * 	From Java Doc [https://docs.oracle.com/javase/7/docs/api/java/util/PriorityQueue.html]: 
	 * 		O(log n) time for the enqueing and dequeing methods (offer, poll, remove() and add)
	 * 		O(n) for the remove(Object) and contains(Object) methods
	 *	 	O(1) for the retrieval methods (peek, element, and size)
	 *
	 * Reference:: 
	 * http://shibaili.blogspot.com/2019/02/759-employee-free-time.html
	 * https://protegejj.gitbook.io/algorithm-practice/leetcode/heap/759-employee-free-time
	 * */
	
	//With Priority Queue
	public static List<Interval> employeeFreeTime(List<List<Interval>> schedule) {

		List<Interval> res = new ArrayList<>();
		
		if (schedule == null || schedule.size() == 0) {
            return res;
        }
		
		PriorityQueue<Interval> pq = new PriorityQueue<>((a,b) -> (a.start - b.start));
		for(List<Interval> interval : schedule) {
			for(Interval task : interval) {
				pq.add(task); // or offer
			}
		}
		
		Interval prev = pq.poll();
		
		while(!pq.isEmpty()) {
			Interval curr = pq.poll();
			
			if(prev.end < curr.start) {
				res.add(new Interval(prev.end, curr.start));
				prev = curr;
			} else {
				prev = prev.end < curr.end ? curr : prev;
			}
		}
		
		return res; 
		
	}
	
	
	// Time: N LogN, Space: O(N)
	
	// With ArrayList [https://yangwg.gitbooks.io/leetcode-young/content/759-employee-free-time.html]
	public static List<Interval> employeeFreeTime2(List<List<Interval>> schedule) {
		List<Interval> res = new ArrayList<>();
		
		if (schedule == null || schedule.size() == 0) {
            return res;
        }
		
		List<Interval> tasks = getSortedIntervals(schedule);
		
		if(tasks.size() < 1) return res;
		
		Interval prev = tasks.get(0);
		
		for(Interval curr : tasks) {		// O(N)
			if(prev.end < curr.start) {
				res.add(new Interval(prev.end, curr.start));
				prev = curr;
			} else {
				prev = prev.end < curr.end ? curr : prev;
			}
		}
		
		return res;
	}
	
	
	private static List<Interval> getSortedIntervals(List<List<Interval>> schedule) {
		List<Interval> tasks = new ArrayList<>();
		for(List<Interval> interval : schedule) { // // O(N), N is the total number of intervals
			tasks.addAll(interval);
		}
		
		// N log(N) - modified merge sort offers guaranteed nlogn performance 
		Collections.sort(tasks, (interval_1, interval_2)->(interval_1.start - interval_2.start));
		//or
		//Collections.sort(tasks, (interval_1, interval_2)-> Integer.compare(interval_1.start, interval_2.start));
		/* or
		Collections.sort(merged, new Comparator<Interval>() {
			@Override
			public int compare(Interval v1, Interval v2) {
				if(v1.start == v2.start && v1.end == v2.end) return 0;
				return Integer.compare(v1.start, v2.start);
				
			}
		}); */
		
		return tasks;
	} 
	
}

/**
 * Definition for an interval.
 */
class Interval {
	int start;
	int end;

	Interval() {
		start = 0;
		end = 0;
	}

	Interval(int s, int e) {
		start = s;
		end = e;
	}
	
	@Override
	public String toString() {
//		return "Interval [start=" + start + ", end=" + end + "]";
		return "[" + start + "," + end + "]";
	}
}


/*Question
We are given a list schedule of employees, which represents the working time for each employee.

Each employee has a list of non-overlapping Intervals, and these intervals are in sorted order.

Return the list of finite intervals representing common, positive-length free time for all employees, also in sorted order.

Example 1:

Input: schedule = [[[1,2],[5,6]],[[1,3]],[[4,10]]]
Output: [[3,4]]
Explanation:
There are a total of three employees, and all common
free time intervals would be [-inf, 1], [3, 4], [10, inf].
We discard any intervals that contain inf as they aren't finite.

Example 2:

Input: schedule = [[[1,3],[6,7]],[[2,4]],[[2,5],[9,12]]]
Output: [[5,6],[7,9]]
(Even though we are representing Intervals in the form [x, y], the objects inside are Intervals, not lists or arrays. For example, schedule[0][0].start = 1, schedule[0][0].end = 2, and schedule[0][0][0] is not defined.)

Also, we wouldn't include intervals like [5, 5] in our answer, as they have zero length.

Note:

schedule and schedule[i] are lists with lengths in range [1, 50].
0 <= schedule[i].start < schedule[i].end <= 10^8.
*/
