package Facebook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class QueueReconstructionByHeight_406_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/* Asked during Meta onsite (round 2, 2nd question)
    Time: O(n^2)
    Space: O(n), becasue of result array
    */
    public int[][] reconstructQueue(int[][] people) {
        // Sort the array based on height in descending order. If heights 
        // are equal, sort based on the number of taller people in ascending order.
        // O(nlogn)
        Arrays.sort(people, (a, b) -> a[0] != b[0] ? b[0] - a[0] : a[1] - b[1]);
        
        List<int[]> result = new ArrayList<>();
        
        // Insert each person into the result list at the position 
        // indicated by the number of taller people in front.
        // Overall: O(n^2)
        for (int[] person : people) { // O(n)
            // inserting an element into the list at 
            // a specific position can take O(n) time
            result.add(person[1], person);
        }
        
        // Convert the result list back to an array.
        return result.toArray(new int[people.length][]);
    }
    
    // with PQ - works
    // Time: O(n^2)
    // Space: O(n), becasue of result array
    public int[][] reconstructQueue2(int[][] people) {
        // Priority Queue to order the people primarily by height in descending order,
        // and by the number of taller people in ascending order.
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) {
                return b[0] - a[0];
            } else {
                return a[1] - b[1];
            }
        });

        // Add all people to the priority queue
        for (int[] person : people) {
            pq.offer(person);
        }

        List<int[]> result = new ArrayList<>();
        
        // Extract each person from the priority queue and insert them into the result list
        while (!pq.isEmpty()) {
            int[] person = pq.poll();
            result.add(person[1], person);
        }

        // Convert the result list back to an array
        return result.toArray(new int[people.length][]);
    }
}

/*
-- Asked during onsite - 2nd coding round
// Leetcode 406: https://leetcode.com/problems/queue-reconstruction-by-height/

Question 2: Before recess, the children of a classroom line up. 
	Each child knows two pieces of information: 
	1. his or her own height and 
	2. the number of children in front of him/her who are taller. 

During recess, they scramble and loss this order and after recess, 
they must reconstruct the original order of the line. Design an algorithm to do so.

*/

// Original Line & Goal Line
// Height:  [front]  155  160  145  150  170  165  140    [back]
// No. Taller:         0    0    2    2    0    1    6

// Example input:   [(145, 2), (170, 0), (140, 6), (165, 1), (150, 2), (155, 0), (160, 0)]
	
// Expected output: [(155, 0), (160, 0), (145, 2), (150, 2), (170, 0), (165, 1), (140, 6)]

// [(140, 6), (145, 2), (150, 2), (155, 0), (160, 0), (165, 1), (170, 0)]
// x x x x x x x
// x x x x x x 140
// x x 145 x x x 140
// x x 145 150 x x 140
// 155 x 145 150 x x 140
// 155 160 145 150 x x 140
// 155 160 145 150 x 165 140
// 155 160 145 150 170 165 140