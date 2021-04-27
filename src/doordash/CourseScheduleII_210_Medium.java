package doordash;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CourseScheduleII_210_Medium {

	public static void main(String[] args) {

		int numCourses = 2; 
		int[][] prerequisites = {{1,0}};
		
		int[] order = findOrder(numCourses, prerequisites);
		System.out.println("1. Expected: [0,1], Actual: "+ Arrays.toString(order));
	}
	
	/* Topological sort
	 * 
	 * Reference: https://leetcode.com/problems/course-schedule-ii/discuss/59330/Concise-JAVA-solution-based-on-BFS-with-comments
	 * READ: https://leetcode.com/problems/course-schedule-ii/discuss/190393/Topological-Sort-Template-General-Approach!!
	 * 
	 * Complexity Analysis

        Time Complexity: O(V + E) where V represents the number of vertices and E represents 
        the number of edges. We pop each node exactly once from the zero in-degree queue and 
        that gives us V. Also, for each vertex, we iterate over its adjacency list and in totality, 
        we iterate over all the edges in the graph which gives us E. Hence, O(V + E)

        Space Complexity: O(V + E) We use an intermediate queue data structure to keep all the 
        nodes with 0 in-degree. In the worst case, there won't be any prerequisite relationship 
        and the queue will contain all the vertices initially since all of them will have 0 in-degree. 
        That gives us O(V). Additionally, we also use the adjacency list to represent our graph initially. 
        The space occupied is defined by the number of edges because for each node as the key, 
        we have all its adjacent nodes in the form of a list as the value. Hence, O(E). 
        So, the overall space complexity is O(V + E).
	 * */
	
	public static int[] findOrder(int numCourses, int[][] prerequisites) {
        
        List<Integer>[] graph = new List[numCourses]; 
        int[] inDegree = new int[numCourses];
        
        for(int i=0; i<numCourses; i++) {
            graph[i] = new ArrayList<Integer>();
        }
        
        for(int[] prereq : prerequisites) {
            int from = prereq[1]; // prerequisite course
            int to = prereq[0];    
            graph[from].add(to);
            inDegree[to]++;  // increment prerequisite count for course to be completed
        }
        
        Queue<Integer> q = new ArrayDeque<>();
        for(int i=0; i<numCourses; i++) {
            if(inDegree[i] == 0) 
                q.offer(i);
        }
        
        int count = 0; // idx for order
        int[] order = new int[numCourses];
        
        while(!q.isEmpty()) {
            int course = q.poll();
            order[count++] = course;
            
            for(int neighbr : graph[course]) { 
                inDegree[neighbr]--;
                if(inDegree[neighbr] == 0) {
                    q.offer(neighbr);
                }
            }
        }
        
        return count == numCourses ? order : new int[0];
    }
	
}
