package test.practice.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class SlidingWindow {

	public static void main(String[] args) {

		//int[] arr = {1,3,-1,-3,5,3,6,7};
		int[] arr = {1, -1};
		int[] res = maxSlidingWindow3(arr, 1);
		System.out.println("##>>res:: "+Arrays.toString(res));
		
	}
	
	//--https://leetcode.com/problems/sliding-window-maximum/discuss/465502/Java-Super-Simple-PriorityQueue-Solution
	public static int[] maxSlidingWindow3(int[] nums, int k) {
		PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
		List<Integer> r = new ArrayList<>();
		for (int i = 0; i < nums.length; ++i) {
			if (i >= k) 
				pq.remove(nums[i - k]);
			
			pq.add(nums[i]);
			
			if (i >= k - 1) 
				r.add(pq.peek());
		}
		return r.stream().mapToInt(x -> x).toArray();
	}
	
	
	//--https://leetcode.com/problems/sliding-window-maximum/discuss/465221/Java-O(n)-Solution-Easy-to-understand-~
	public static int[] maxSlidingWindow2(int[] nums, int k) {
        Deque<Integer> deque = new LinkedList<>();
        if(nums==null || nums.length==0)
            return new int[0];
        int[] res = new int[nums.length-k+1];
        for(int i=0;i<nums.length;i++)
        {
            if(deque.peekFirst()!=null && deque.peekFirst()==(i-k))
                deque.pollFirst();
            while(deque.peekLast()!=null && nums[i]>nums[deque.peekLast()])
                deque.pollLast();
            deque.offerLast(i);
            if(i>=k-1)
                res[i-k+1]=nums[deque.peekFirst()];
        }
        return res;
    }
	
	public static int[] maxSlidingWindow(int[] arr, int k) {
        
        if(arr == null || arr.length == 0 || arr.length < k) {
            return new int[0];
        }
        
        int max_seen = 0;
        ArrayList<Integer> res = new ArrayList<>();
        
        for(int i=0; i<k; i++) {        
            max_seen = Math.max(max_seen, arr[i]);
        }
        res.add(max_seen);
        
        for(int i=k; i<arr.length; i++) {
            max_seen = Math.max(max_seen, arr[i]);
            res.add(max_seen);
            
        }
        
        Integer[] wrapperArr = res.toArray(new Integer[res.size()]);
        int[] resArr = toPrimitive(wrapperArr);
        
        return resArr;
        
    }
    
    public static int[] toPrimitive(Integer[] IntegerArray) {

		int[] result = new int[IntegerArray.length];
		for (int i = 0; i < IntegerArray.length; i++) {
			result[i] = IntegerArray[i].intValue();
		}
		return result;
	}

}
