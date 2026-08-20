package Oracle;

import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;

public class TaskSchehduler_621_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	// Time: O(N)
    // O(26) = O(1)
    public int leastInterval(char[] tasks, int n) {
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
