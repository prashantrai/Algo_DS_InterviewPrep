package leetcode;

import java.util.Arrays;
import java.util.PriorityQueue;

public class CourseSchedule_III_630_Hard {

	public static void main(String[] args) {

		int[][] courses = {{100,200},{200,1300},{1000,1250},{2000,3200}};
		System.out.println("Expected: 3, Actual: " + scheduleCourse(courses));
	}

	/* Reference: 
        https://leetcode.com/problems/course-schedule-iii/discuss/104845/Short-Java-code-using-PriorityQueue
        
    	Time: O(nlogn), t most nn elements are added to the queuequeue. 
            Adding each element is followed by heapification, which takes O(logn) time.
    	Space: O(n)
	 */
	public static  int scheduleCourse(int[][] courses) {
    
    /* Sort the courses by their deadlines  increasing order
    (Greedy! We have to deal with courses with early deadlines first)
    */
    Arrays.sort(courses, (a,b) -> a[1] - b[1]);
    PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> b - a); //max heap
    int time = 0;
    for(int[] course : courses) {
        time += course[0]; // total time to take current course
        pq.offer(course[0]); // add current course to a priority queue
        
        // If time exceeds, drop the previous course which costs the most time. 
        if(time > course[1]) {
            time -= pq.poll();
        }
    }
    
    return pq.size();
    
}
}
