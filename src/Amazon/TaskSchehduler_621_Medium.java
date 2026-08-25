package Amazon;

import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;

public class TaskSchehduler_621_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/* Interview Explanation Before Coding

		I will count the frequency of each task because the task with the highest 
		frequency determines how many idle slots we may need.
		
		Suppose the most frequent task appears maxFreq times. It creates 
		maxFreq - 1 gaps between its executions. Each gap has size n, 
		so the initial schedule size is:
		
			(maxFreq - 1) * (n + 1)
		
		The last occurrence of the most frequent task does not need a cooldown 
		after it, so we subtract the extra slot and handle multiple tasks having 
		the same maximum frequency.

		Finally, I compare this calculated size with the total number of tasks. 
		If there are enough different tasks to fill all gaps, the answer is simply 
		the number of tasks because no idle time is needed.
	 
	 * */

	/* Step-by-Step Algorithm
		1. Count frequency of each task.
		2. Find the maximum frequency among all tasks.
		3. Count how many tasks have this maximum frequency.
		
		4. Calculate the minimum intervals needed using:
		
			(maxFreq - 1) * (n + 1) + numberOfMaxFreqTasks
			
		5. Return the maximum of:
			- calculated schedule length
			- total number of tasks
		
	 * */
	
	
	/* Interview Script + Explanation: 
	 	The task that appears most frequently is what constrains the schedule, 
	 	because its repeated occurrences need to be separated by at least n intervals.

		So I'll first find the maximum frequency, say maxFreq.
		
		If a task appears maxFreq times, those occurrences create maxFreq - 1 gaps. 
		I can think of each gap as a block containing one occurrence of the task 
		followed by n cooldown positions, so each full block has size n + 1.
		
		That gives me (maxFreq - 1) * (n + 1).
		
		The last block doesn't need a cooldown afterward. Also, there may be multiple 
		tasks tied for the maximum frequency, so the final block contains maxFreqCount 
		tasks. Therefore the constrained schedule length is 
		(maxFreq - 1) * (n + 1) + maxFreqCount.
		
		Finally, if there are enough other tasks to fill all the cooldown gaps, 
		we won't need any idle time, in which case the answer is just the number of tasks. 
		So I return the maximum of the formula and tasks.length.
	 * 
	 * Explanation, how we derive the formula: (maxFreq - 1) * (n + 1) + maxFreqCount;
	 
	 "(maxFreq - 1)" : 
	 If A appears maxFreq times (say 3), it creates maxFreq - 1 empty blocks (or frames.
	 So, if max frquency is "n" and then empty block is "n-1"
	 
	 no of empty blocks = maxFrq - 1
	 
	 A [ _ _ ] A [ _ _ ] A
   		 gap 1    gap 2
	 
	 because if the most frequent task appears maxFreq times, there are: 
	 maxFreq - 1 gaps between them.
	 
	 For our example:
		maxFreq = 3
		gaps = 3 - 1 = 2
	 
	 * "(n + 1)": Where does n + 1 come from?
		Instead of looking at: A _ _ A
		as "one A + two cooldown positions", think of it as a block:
		
		A _ _
		
		Its size is: 1 + n  or  n + 1
		
		For n = 2: A _ _  has length 3.
		
		Since we have maxFreq - 1 complete blocks:
		
		A _ _ | A _ _ | A
		
		we get: (maxFreq - 1) * (n + 1)
		
		For our example: (3 - 1) * (2 + 1)  = 2 * 3 = 6
		
		Then we still need to add the last A: 
		
		A _ _ | A _ _ | A
		                    ↑
		
		So if only one task has maximum frequency:
		
		(maxFreq - 1) * (n + 1) + 1
		
		For this example: 2 * 3 + 1 = 7
		
		And indeed: A B _ A B _ A
		
		requires 7 intervals.
		
		But our code doesn't always add 1. It adds: maxFreqCount
		
		That's the next important part. 
		
		
	 * Why do we need maxFreqCount?

		Consider: tasks = [A,A,A,B,B,B] n = 2
		
		Both A and B have frequency 3.
		
		So: maxFreq = 3
		maxFreqCount = 2
		
		If we only considered A:
		
		A _ _ | A _ _ | A
		
		But B also appears 3 times, so we can arrange:
		
		A B _ | A B _ | A B
		
		Look at the final group: A B
		
		There isn't just one task at the end anymore. There are 2 
		tasks tied for maximum frequency.
		
		Therefore: (maxFreq - 1) * (n + 1) + maxFreqCount
		
		becomes:
		(3 - 1) * (2 + 1) + 2
		= 6 + 2 = 8
		
		And the valid schedule is: 
		
		A B idle A B idle A B
		
		1 2  3   4 5  6   7 8
		
		Answer = 8.
	 
	 * */
	
	/* But why Math.max(intervals, tasks.length)?

	The formula calculates the minimum length when cooldown causes idle slots.
	But sometimes there are enough other tasks to completely fill those gaps.

	Example: tasks = [A,A,B,B,C,C,D,D]
	n = 2

	Every task appears twice: maxFreq = 2
	maxFreqCount = 4

	Formula: (maxFreq - 1) * (n + 1) + maxFreqCount
	= (2 - 1) * 3 + 4
	= 7

	But we have:

	8 tasks

	Obviously we cannot execute 8 tasks in 7 time units.

	And because there are plenty of different tasks, we can arrange 
	all of them without idle time, for example:

	A B C A B D C D

	So answer is simply: 8

	That's why:

	return Math.max(intervals, tasks.length);

	Think of it as two lower bounds:

	Cooldown constraint says: schedule must be at least `intervals` long.

	Number of tasks says: schedule must be at least `tasks.length` long.

	Therefore:

	answer = max(both lower bounds)
			
	*/
	
	// Time: O(n), Space: O(1)
	public int leastInterval(char[] tasks, int n) {
        int[] frequency = new int[26];

        for (char task : tasks) {
            frequency[task - 'A']++;
        }

        int maxFreq = 0;
        int maxFreqCount = 0;

        for (int count : frequency) {
            if (count > maxFreq) {
                maxFreq = count;
                maxFreqCount = 1;
            } else if (count == maxFreq) {
                maxFreqCount++;
            }
        }

        int intervals = (maxFreq - 1) * (n + 1) + maxFreqCount;

        return Math.max(intervals, tasks.length);
    }
	
	
	
	
	
	/** For interview use above approach as that's more optimal */
	
	// Time: O(N log K), Each task is inserted and removed from the heap once.
    // O(K), Heap and cooldown queue store unique tasks.
    public int leastInterval2(char[] tasks, int n) {
        int[] freq = new int[26];

        for(int i=0; i<tasks.length; i++) {
            freq[tasks[i] - 'A']++;
        }

        /* 
            A task with higher frequency is more dangerous because:
                It needs to be scheduled many times.
                It is more likely to create future idle time.

            So the natural greedy choice is: Always execute the task with the highest remaining frequency, that's why maxHeap to have the higher frequency on top to execute first
        */

        PriorityQueue<Integer> maxPQ = new PriorityQueue<>((a,b) -> Integer.compare(b, a));

        for(int count : freq) {
            if(count > 0) {
                maxPQ.offer(count);
            }
        }

        Queue<int[]> coolDownQueue = new ArrayDeque<>();

        int time = 0;

        while (!maxPQ.isEmpty() || !coolDownQueue.isEmpty()) {
            time++;
            
            // poll from the PQ, reduce the count and if count > 0 add in cooldownQueue
            if(!maxPQ.isEmpty()) {
                int remaining = maxPQ.poll() - 1;
                if(remaining > 0) {
                    // `time + n`: queue stores the next time the task can be picked, e.g. in 
                    coolDownQueue.offer(new int[] {remaining, time + n});
                }
            }
            if(!coolDownQueue.isEmpty() && coolDownQueue.peek()[1] == time) {
                maxPQ.offer(coolDownQueue.poll()[0]);
            }
        } 

        return time;
    }

}

/* Resoning is very natural: 
Need most frequent task first
        |
        v
Use Max Heap

Need to prevent immediate reuse
        |
        v
Use Cooldown Queue

Need minimum intervals
        |
        v
Simulate time

*/
