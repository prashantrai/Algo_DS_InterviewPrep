package doordash;

import java.util.Arrays;
import java.util.PriorityQueue;

public class MiceAndCheese_2611_Medium {

	public static void main(String[] args) {
		int reward1[] = {1,1,3,4}, reward2[] = {4,4,1,1}, k = 2;
		System.out.println("Expected: 15, Actual: " + miceAndCheese(reward1, reward2, k));
		
//		int reward1[] = {1,1}, reward2[] = {1,1}, k = 2;
//		System.out.println("Expected: 2, Actual: " + miceAndCheese(reward1, reward2, k));
	}

	/*
	 * Refer: https://leetcode.com/problems/mice-and-cheese/discuss/3368601/Simple-Java-solution
	 * 
	Algo: 
	Step1 : Assume we select all the elements in reward 2.
	Step2: Now, we wanted to select k from reward1 so, lets pick those k elements 
		from reward1 which are highest from their corresponding values in reward2 
		on the same index. In other words, just pick those elements who differ from 
		their corresponding reward2 by max amount and add them. Now, note that since 
		we already added the reward2 values into the answer, we just need to add the 
		difference of these elements.
	
	To implement step2 just create a heap of size k containing k maximum differences between reward1 and reward 2 elements at any given index.
	
    Time: O(klogk), adding to minHeap
    Space: O(k), size of minHeap
    */
    
    public static int miceAndCheese(int[] reward1, int[] reward2, int k) {
        int sum = 0;
        for(int r : reward2) {
            sum += r;
        }
        
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for(int i=0; i<reward1.length; i++) {
            minHeap.offer(reward1[i] - reward2[i]);
            
            if(minHeap.size() > k) 
                minHeap.poll();
        }
        
        while(!minHeap.isEmpty()) {
            sum += minHeap.poll();
        }
        
        return sum;
    }
    
    
    // Another approach using Array
    // Refer: https://leetcode.com/problems/mice-and-cheese/discuss/3368322/JavaC%2B%2BPython-K-Largest-Ai-Bi
    // Time O(n log n) Space O(n)
    public static int miceAndCheese2(int[] A, int[] B, int k) {
        int res = 0, n = A.length, diff[] = new int[n];
        for (int i = 0; i < n; i++) {
            diff[i] = A[i] - B[i];
            res += B[i];
        }
        Arrays.sort(diff);
        for (int i = 0; i < k; i++)
            res += diff[n - 1 - i];
        return res;
    }
}
