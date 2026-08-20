package Oracle;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class CourseSchedule_207_Medium {

	public static void main(String[] args) {
		
		int numCourses = 2; 
		int[][] prerequisites = {
									{1,0}
								};
		
		System.out.println("Expected: true, Actual: "+ canFinish(numCourses, prerequisites));
		
		numCourses = 4;  
		int[][] prerequisites2 = { {0,1}, {1,2}, {2,3}, {3,1} }; // cycle
		System.out.println("Expected: false, Actual: "+ canFinish(numCourses, prerequisites2));

	}

	
	/* BFS Solution  */
	
	/*
	Time Complexity: O(V + E)
	- Creating the graph array and indegree array takes O(V)
	- Building the graph from prerequisites takes O(E)
	- Adding all 0-indegree courses to the queue takes O(V)
	- In BFS/topological sort:
		each course is processed at most once → O(V)
		each edge is visited once when reducing indegree → O(E)
	
	So, total = O(V + E)
	
	Space Complexity: O(V + E)
	- Adjacency list graph stores all edges → O(V + E)
	- indegree array → O(V)
	- Queue can hold up to all courses → O(V)
	
	So, total auxiliary space = O(V + E)
	
	 * */
	
	// This will work for Leecode 210 as well i.e. Course Schedule ii
	// Time O(m+n)  
	// Space: O(m+n)
    public static boolean canFinish(int numCourses, int[][] prerequisites) {
    	
    	// Edge case: no courses
        if(numCourses == 0) {
            return false;
        }
			
		Queue<Integer> q = new ArrayDeque<>();
		
		List<Integer>[] graph = new List[numCourses];
		for(int i=0; i<numCourses; i++) {
            graph[i] = new ArrayList<Integer>();
        }
		
		// hold the num/count of prereq course before we take a course
        // idx of this array is the course that we can take only once all
        // the prereq are completed or 0, value at each index in this array
        // is the count of prereq courses
		int[] indegree = new int[numCourses];
		
		for(int[] prereq : prerequisites) {
			int from = prereq[1];
			int to = prereq[0];
			graph[from].add(to);
			indegree[to]++;
		}
		
		// Add every course with 0 prereq in the Queue
        // We can take them first
		for(int i=0; i<indegree.length; i++) {
			if(indegree[i] == 0) {
				q.offer(i);
			}
		}
	
		int count = 0;
		//int[] order = new int[numCourses]; // this can be used for Leetcode 210 as well
		
		while(!q.isEmpty()) {
			int course = q.poll();
			//order[count] = course; // only when you need course order propblem 210
		    count++;	
			for(int neighbour : graph[course]) {
				indegree[neighbour]--;
				if(indegree[neighbour] == 0) {
					q.offer(neighbour);
				}
			}
		}
		
		return count == numCourses;
	}
	
	
}