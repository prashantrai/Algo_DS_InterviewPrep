package leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CourseSchedule_207_Medium {

	public static void main(String[] args) {
		
		int numCourses = 2; 
		int[][] prerequisites = {
									{1,0}
								};
		
		System.out.println("Expected: true, Actual: "+ canFinish(numCourses, prerequisites));
		System.out.println("Expected: true, Actual: "+ canFinish2(numCourses, prerequisites));
		
		numCourses = 4;  
		int[][] prerequisites2 = { {0,1}, {1,2}, {2,3}, {3,1} }; // cycle
		System.out.println("Expected: false, Actual: "+ canFinish(numCourses, prerequisites2));
		System.out.println("Expected: false, Actual: "+ canFinish2(numCourses, prerequisites2));

	}

	
	/* BFS Solution References: 
	 * 
	 * https://leetcode.com/problems/course-schedule/discuss/58516/Easy-BFS-Topological-sort-Java
	 * https://leetcode.com/problems/course-schedule/discuss/58669/Concise-JAVA-solutions-based-on-BFS-and-DFS-with-explanation
	 * https://leetcode.com/problems/course-schedule/discuss/58532/5ms-DFS-beat-98-and-9ms-BFS-in-java
	 * https://leetcode.com/problems/course-schedule/discuss/162743/JavaC%2B%2BPython-BFS-Topological-Sorting-O(N-%2B-E)
	 * 
	 * https://www.youtube.com/watch?v=u4v_kvOfumU&ab_channel=leetuition
	 * 
	 * READ: https://leetcode.com/problems/course-schedule-ii/discuss/190393/Topological-Sort-Template-General-Approach!!
	 * 	
	 * DFS : 
	 * https://www.programcreek.com/2014/05/leetcode-course-schedule-java/
	 * https://zhuhan0.blogspot.com/2017/07/leetcode-207-course-schedule.html#:~:text=Time%20complexity%3A,O(V%20%2B%20E).
	 * https://www.programmersought.com/article/61453920179/
	 * https://blog.baozitraining.org/2020/06/leetcode-solution-207-course-schedule.html
	 */
	
	// this can be used for Leetcode 210 (i.e. CourseSchedule ii) as well just update the return nothing else
	/*
	 * Time:  O(N+M), traversing a graph requires access to all nodes and all edges, N and M are the number of nodes and the number of edges, respectively;
	 * Space: O(N+M)
	 * 
	 * Time complexity: O(V + E). 
	 * Space complexity: O(V + E), since we use adjacency lists to represent a graph
	 * */
	
    public static boolean canFinish(int numCourses, int[][] prerequisites) {
			
			Queue<Integer> q = new ArrayDeque<>();
			
			List<Integer>[] graph = new List[numCourses];
			for(int i=0; i<numCourses; i++) {
	            graph[i] = new ArrayList<Integer>();
	        }
			
			int[] indegree = new int[numCourses];
			
			for(int[] prereq : prerequisites) {
				int from = prereq[1];
				int to = prereq[0];
				graph[from].add(to);
				indegree[to]++;
			}
			
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
	
	
	
	// Another approach - https://leetcode.com/problems/course-schedule/discuss/58532/5ms-DFS-beat-98-and-9ms-BFS-in-java 
    
    // Time O(V+E)
    public static boolean canFinish2(int numCourses, int[][] prerequisites) {
        
        int[] indegree = new int[numCourses];
        Queue<Integer> q = new LinkedList<>();
        List<List<Integer>> graph = new ArrayList<>();
        
        // creating ajacecny list
        for(int i=0; i<numCourses; i++) {
            graph.add(new ArrayList<Integer>());
        }
        
        for(int[] prereq : prerequisites) {
            graph.get(prereq[1]).add(prereq[0]); // adding to adjacency list
            
            indegree[prereq[0]]++; // record num of prereqs for each course
        }
        
        //cycle: if the length is equal to bfs elements, we iterate all nodes, hence no cycle. If cycle exist, you can not iterate the the elements in the cycle, degree[j] == 0 won't valid.
        for(int i=0; i<indegree.length; i++) {
            if(indegree[i] == 0) {
                q.offer(i);
            }
        }
        
        int count = 0;
        while(!q.isEmpty()) {
            
            int course = q.poll();
            
            for(int adj : graph.get(course)) {
                indegree[adj]--;
                count++;
                
                if(indegree[adj] == 0) {
                    q.offer(adj);
                }
            }
        }
        
        return count == prerequisites.length;
    }
	
	
	/* https://leetcode.com/problems/course-schedule/
	
	 Refernce -refer comment evilcoder and subcomment rajatdada
    https://leetcode.com/problems/course-schedule/discuss/58516/Easy-BFS-Topological-sort-Java
   
    Time O(V+E)
    *
    * BFS
    */
	public static boolean canFinish3(int numCourses, int[][] prerequisites) {
       
       int[] indegree = new int[numCourses];
       Queue<Integer> q = new LinkedList<>();
       
       // counting the numof dependecy/prereq for each course
       // e.g. for [[1, 0]] indegree will be [1] i.e. for course '1' we have 1 prereq
       for(int[] prerequisite : prerequisites) {
           indegree[prerequisite[0]]++;
       }
       
       // add course with zero prereq/dependency in q
       for(int i=0; i<indegree.length; i++) {
           if(indegree[i] == 0) {
               q.offer(i); //no prerequisite  needed course
           }
       }
       
       while(!q.isEmpty()) {
           numCourses--;
           int course = q.poll();
           
           for(int[] pair : prerequisites) { // course pair, this is fine we are iterating the entire prerequisites array ever time
               if(pair[1] == course) {
                   indegree[pair[0]]--; // decrease prereq/dependency
                   // check course with no prereq and add to queue if prereq is ZERO
                   if(indegree[pair[0]] == 0) {
                       q.offer(pair[0]);  // add course with no prereq left
                   }
               }
           }
       } 
       
       return numCourses == 0;
	}
	
}
