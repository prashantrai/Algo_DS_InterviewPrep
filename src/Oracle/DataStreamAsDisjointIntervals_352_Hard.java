package Oracle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

public class DataStreamAsDisjointIntervals_352_Hard {

	public static void main(String[] args) {
		SummaryRanges summaryRanges = new SummaryRanges();
		summaryRanges.addNum(1);      // arr = [1]
		System.out.println("Expected: [[1, 1]], Actual: "+ Arrays.deepToString(summaryRanges.getIntervals())); 
		
		summaryRanges.addNum(3);      // arr = [1, 3]
		System.out.println("Expected: [[1, 1], [3, 3]], Actual: "+ Arrays.deepToString(summaryRanges.getIntervals()));
		
		summaryRanges.addNum(7);      // arr = [1, 3, 7]
		System.out.println("Expected: [[1, 1], [3, 3], [7, 7]], Actual: "+ Arrays.deepToString(summaryRanges.getIntervals()));
		
		summaryRanges.addNum(2);      // arr = [1, 2, 3, 7]
		System.out.println("Expected: [[1, 3], [7, 7]], Actual: "+ Arrays.deepToString(summaryRanges.getIntervals()));
		
		summaryRanges.addNum(6);      // arr = [1, 2, 3, 6, 7]
		System.out.println("Expected: [[1, 3], [6, 7]], Actual: "+ Arrays.deepToString(summaryRanges.getIntervals()));
	}

	/* TreeMap for faster i.e. O(LogN), create the range as we add the value
	 * https://leetcode.com/problems/data-stream-as-disjoint-intervals/discuss/82553/Java-solution-using-TreeMap-real-O(logN)-per-adding.
	 * 
	 * Can Also Use TreeSet with Comparator: https://tenderleo.gitbooks.io/leetcode-solutions-/content/GoogleHard/352.html
 	 * 
	 * */
	// Time: O(logN)
	//Using Array instead of Interval class as LeetCode method returns array
	// reference (ckcz123): https://leetcode.com/problems/data-stream-as-disjoint-intervals/discuss/82553/Java-solution-using-TreeMap-real-O(logN)-per-adding.
	static class SummaryRanges {
	    
	    TreeMap<Integer, int[]> map;
	    
	    public SummaryRanges() {
	    	map = new TreeMap<>();
	    }
	    
	    public void addNum(int val) {
	    	Integer floor = map.floorKey(val);
	    	if(floor == null || map.get(floor)[1] < val-1) {
	    		// we fetch (and remove) the immediate next interval and then merge the intervals
	    		int[] interval = map.remove(val + 1); 
	    		int[] newIntrvl = new int[] {val, (interval == null ? val : interval[1])};
	    		map.put(val, newIntrvl);
	    	} else {
	    		int[] interval = map.get(floor);
	    		// is new value is within range of interval/floor then return  
	    		if(interval[1] >= val) return;
	    		
	    		int[] next = map.remove(val+1);
	    		interval[1] = next == null ? val : next[1];
	    		
	    		map.put(floor, interval);
	    	}
	    
	    }
	    
	    public int[][] getIntervals() {
	    	List<int[]> res = new ArrayList<>(map.values());
	    	return res.toArray(new int[res.size()][2]);
	    }
	}
	
	// Using Interval class
	static class SummaryRangesTreeMap {
	    
	    TreeMap<Integer, Interval> map;
	    
	    public SummaryRangesTreeMap() {
	    	map = new TreeMap<>();
	    }
	    
	    public void addNum(int val) {
	    	Integer floor = map.floorKey(val);
	    	if(floor == null || map.get(floor).end < val-1) {
	    		// we fetch (and remove) the immediate next interval and then merge the intervals
	    		Interval interval = map.remove(val + 1); 
	    		Interval newIntrvl = new Interval(val, (interval == null ? val : interval.end));
	    		map.put(val, newIntrvl);
	    	} else {
	    		Interval interval = map.get(floor);
	    		// is new value is within range of interval/floor then return  
	    		if(interval.end >= val) return;
	    		
	    		Interval next = map.remove(val+1);
	    		interval.end = next == null ? val : next.end;
	    		
	    		map.put(floor, interval);
	    	}
	    
	    }
	    
	    public List<Interval> getIntervals() {
	    	List<Interval> intervals = (List<Interval>) map.values();
	    	return intervals;
	    }
	}
	
	static class Interval {
		 int start;
		 int end;
		 Interval() { start = 0; end = 0; }
		 Interval(int s, int e) { start = s; end = e; }
	}
	
	
	// 2nd solution
	static class SummaryRanges2 {

	    /*
	    Run time: O(N)
	    	The time complexity for adding in TreeSet is O(logN). 
	    	It would be O(N logN) overall as we have used an ArrayList and remove an interval from it.
	    	But we can say it as O(N) as filnal as O(longN) will be dwarfed by O(N)
	    
	    Sapce: O(N)
	    
	    Another Approach with Comparator (log N solution): 
	    https://tenderleo.gitbooks.io/leetcode-solutions-/content/GoogleHard/352.html
	    
	    ** For O(LogN) solution use BST: 
	    *   refer: https://leetcode.com/problems/data-stream-as-disjoint-intervals/discuss/82610/Java-fast-log-(N)-solution-(186ms)-without-using-the-TreeMap-but-a-customized-BST 
	    
	    */
	    
	    TreeSet<Integer> set;
	    
	    public SummaryRanges2() {
	        set = new TreeSet<>();
	    }
	    
	    public void addNum(int val) {
	        set.add(val);
	    }
	    
	    public int[][] getIntervals() {
	        List<int[]> list = new ArrayList<>();
	        
	        int prev = -1, start = 0, end = 0; // problem states non-negative numbers
	        
	        for(int curr : set) {
	            if(curr - prev > 1) {
	                if(prev >= 0) {
	                    list.add(new int[]{start, end});
	                }
	                start = curr;
	                end = curr;
	                
	            } else {          
	                end = curr;
	            }
	            prev = curr;
	        }
	        list.add(new int[]{start, end});
	        return list.toArray(new int[list.size()][]);
	    }
	}
	
}
