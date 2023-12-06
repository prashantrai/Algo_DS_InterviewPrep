package Snowflake;

import java.util.Comparator;
import java.util.TreeSet;

public class SlidingWindowMedian_480_Hard {

	public static void main(String[] args) {

	}

	/*
    Reference: https://leetcode.com/problems/sliding-window-median/discuss/96346/Java-using-two-Tree-Sets-O(n-logk)/653496
    
    Inspired by solution to the problem: 295. Find Median from Data Stream

    Why TreeSet?
    Instead of using two priority queue's we use two TreeSet as we want O(logk) for remove(element). 
    Priority Queue would have been O(k) for remove(element) giving us an overall time complexity of 
    O(nk) instead of O(nlogk)
    
    
    Time: O(nlogk)
    
    Space: O(k)
    
    */
    
    public double[] medianSlidingWindow(int[] nums, int k) {
        
        double[] result = new double[nums.length - k + 1];
        int start = 0;
        
        Comparator<Integer> comparator 
            = (a, b) -> nums[a] != nums[b] ? Integer.compare(nums[a], nums[b]) : a - b;
        
        TreeSet<Integer> lo = new TreeSet<>(comparator);
        TreeSet<Integer> high = new TreeSet<>(comparator);
        
        for(int i=0; i < nums.length; i++) {
            lo.add(i);
            high.add(lo.pollLast());
            
            if(high.size() > lo.size()) {
                lo.add(high.pollFirst());
            }
            
            if(high.size() + lo.size() == k) {
                if(high.size() == lo.size()) {
                    // int last = nums[lo.last()];
                    // int first = nums[high.first()]
                    result[start] = nums[lo.last()]/2.0 + nums[high.first()]/2.0;
                } else {
                    result[start] = (double) nums[lo.last()]; //or nums[lo.last()]/1.0;
                }
                
                lo.remove(start);
                high.remove(start);
                
                start++;
            }
        }
        return result;
    }
}

/*
 https://leetcode.com/problems/sliding-window-median/discuss/96346/Java-using-two-Tree-Sets-O(n-logk)/653496
 * 
public double[] medianSlidingWindow(int[] nums, int k) {
       double[] result = new double[nums.length - k + 1];
       int start = 0;
          
       TreeSet<Integer> lo = new TreeSet<>((a, b) -> (nums[a] != nums[b] ? Integer.compare(nums[a], nums[b]) : a - b));
       TreeSet<Integer> hi = new TreeSet<>((a, b) -> (nums[a] != nums[b] ? Integer.compare(nums[a], nums[b]) : a - b));
        
       for (int i = 0; i < nums.length; i++) {
            lo.add(i);
            hi.add(lo.pollLast());
            if(hi.size()>lo.size()) lo.add(hi.pollFirst());
            if (lo.size() + hi.size() == k) {
                result[start]=lo.size()==hi.size()? nums[lo.last()]/2.0+ nums[hi.first()]/2.0: nums[lo.last()]/1.0;
                if (!lo.remove(start)) hi.remove(start);
                start++;
            }
        }
        return result;
    }

*/
}
