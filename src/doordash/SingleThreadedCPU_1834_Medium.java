package doordash;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class SingleThreadedCPU_1834_Medium {

	public static void main(String[] args) {
		int[][] tasks = {{1,2},{2,4},{3,2},{4,1}};
		System.out.println("Expected: [0,2,3,1], Actual: " + Arrays.toString(getOrder(tasks)));
	}
	
	/*
    1. Create a list of task available at current time.
    2. Now, from this list select the task with the shortest 
    	processing time, i.e., here we can sort all available task 
    	in increasing order of their processing time.
    3. Create a min heap to store the value by processing time 
        and if processing time is same then store them by their task index
    4. Now, while there are tasks in the sortedTasks array that have not 
    	been added to the min-heap, or there are tasks remaining in the min-heap:

     - Check if the min-heap is empty and if the enqueue time of the next 
        task is greater than currTime. If so, then update the currTime to 
        the next task's enqueue time.
     - Insert all the available tasks (tasks whose enqueue time is less than 
       or equal to currTime), into the min-heap.
     - Pick the task on the top of the min-heap, increment currTime by its 
       processing time, and add its index to the tasksProcessingOrder array.
       
       
    Time: O(n logn)
            - creating sortedTask takes O(N)
            - Arrays.sort taks O(N logN)
            - Each heap operation lakes O(logN) and we are performing for 
                N elements, i.e. O(N logN)
                
    Space: O(N), sortedTask array will store all N tasks, 
       and min-heap will also contain at most N tasks.
    */
    
    public static int[] getOrder(int[][] tasks) {
        int n = tasks.length;
        int[] ans = new int[n];
        
        // Store task {enqueue time, processing time, index}.
        int[][] sortedTask = new int[n][3];
        for(int i=0; i<n; i++) { // O(N)
            sortedTask[i][0] = tasks[i][0]; //enque time
            sortedTask[i][1] = tasks[i][1]; //processing time
            sortedTask[i][2] = i; //task idx in tasks array
        }
        
        // sort the array sortedTask by enque time
        // in incresing order
        Arrays.sort(sortedTask, (a,b) -> Integer.compare(a[0],b[0])); // O(N logN)
        
        // min heap to store the value by processing time and if
        // processing time is same then store them by their task index
        Queue<int[]> minPQ 
            = new PriorityQueue<>((a,b) -> (a[1] != b[1] 
                                                // processing time
                                                ? Integer.compare(a[1], b[1]) 
                                                // task index
                                                : Integer.compare(a[2], b[2]) 
                                        ));
        
        int currTime = 0;
        int taskIdx = 0;
        int ansIdx = 0;
        
        while(taskIdx < n || !minPQ.isEmpty()) {
            
            // Check if the min-heap is empty and if the enqueue time 
            // of the next task is greater than currTime,
            // then update the currTime to the next task's enqueue time.
            if(minPQ.isEmpty() && currTime < sortedTask[taskIdx][0]) {
                currTime = sortedTask[taskIdx][0];
            }
            
            // Push all the tasks whose enqueueTime <= currTime into the heap.
            while(taskIdx < n && sortedTask[taskIdx][0] <= currTime) {
                minPQ.offer(sortedTask[taskIdx]);
                taskIdx++;
                
            }
            
            // Pick the task on the top of the min-heap, 
            // increment currTime by its processing time, 
            // and add its index to the ans array.
            int[] nextTask = minPQ.poll();
            int processingTime = nextTask[1];
            int index = nextTask[2];
            
            currTime += processingTime;
            ans[ansIdx++] = index;
            
        }
        return ans;
    }

}
