package Apple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SnapshotArray_1146_Medium {

	public static void main(String[] args) {
		
		int length = 5;
		
		SnapshotArray obj = new SnapshotArray(length);
//		obj.set(index, val);
//		int param_2 = obj.snap();
//		int param_3 = obj.get(index,snap_id);

	}
	
	
	/*
	 * "A brute-force solution would copy the entire array whenever we take a
	 * snapshot, which costs O(n) per snapshot. We can optimize this by keeping a
	 * version history for each index, storing pairs of snapshot ID and value.
	 * 
	 * When we call set, we either update the latest entry if we're still in the
	 * same snapshot or append a new version. The snap operation simply increments a
	 * counter, making it O(1).
	 * 
	 * For get, we binary-search the index's history to find the latest snapshot ID
	 * less than or equal to the requested ID. This gives O(log k) lookup time and
	 * O(n + S) total space."
	 */
	
	
	/* Using TreeMap, this avoids manual Binary Tree implementation
	 * */
	static class SnapshotArray {

	    // Each index stores [snapshotId, value] pairs.
	    TreeMap<Integer, Integer>[] history;
	    int snapId;

	    public SnapshotArray(int length) {
	        history = new TreeMap[length];
	        snapId = 0;

	        for(int i=0; i<length; i++) {
	            history[i] = new TreeMap<>();

	            /* History of each index
	            Index 0 history:
	                (snapId=0, value=5)
	                (snapId=1, value=6)
	                (snapId=3, value=9)

	            Index 1 history:
	                (snapId=0, value=0)
	                (snapId=2, value=7)
	            */
	            history[i].put(0, 0);
	        }

	    }
	    
	    // Time: O(1)
	    public void set(int index, int val) {
	       history[index].put(snapId, val);
	    }
	    
	    // Time: O(1)
	    public int snap() {
	        return snapId++;
	    }
	    
	    // Time: O(log k)
	    public int get(int index, int snapId) {
	        
	        Map.Entry<Integer, Integer> entry = 
	        history[index].floorEntry(snapId);

	        return entry.getValue();

	    }
	}

	// Efficient solution using List and manual Binary Search
	static class SnapshotArray_WithListAndBinarySearch {

	    // Each index stores [snapshotId, value] pairs.
	    List<int[]>[] history;
	    int snapId;

	    public SnapshotArray_WithListAndBinarySearch(int length) {
	        history = new ArrayList[length];
	        snapId = 0;

	        for(int i=0; i<length; i++) {
	            history[i] = new ArrayList<>();

	            /* History of each index
	            Index 0 history:
	                (snapId=0, value=5)
	                (snapId=1, value=6)
	                (snapId=3, value=9)

	            Index 1 history:
	                (snapId=0, value=0)
	                (snapId=2, value=7)
	            */
	            history[i].add(new int[]{0,0});
	        }

	    }
	    
	    // Time: O(1)
	    public void set(int index, int val) {
	        List<int[]> list = history[index];
	        int last = list.size() - 1;

	        // If updated in the same snapshot,
	        // overwrite the previous value.
	        // Each index stores [snapshotId, value] pairs.
	        if (list.get(last)[0] == snapId) {
	            list.get(last)[1] = val;
	        } else {
	            list.add(new int[]{snapId, val});
	        }
	    }
	    
	    // Time: O(1)
	    public int snap() {
	        return snapId++;
	    }
	    
	    // Time: O(log k)
	    public int get(int index, int snapId) {
	        List<int[]> list = history[index];

	        int left = 0;
	        int right = list.size() - 1;
	        int result = 0;

	        while(left <= right) {
	            int mid = left + (right - left)/2;
	            if(list.get(mid)[0] <= snapId) {
	                result = mid;
	                left = mid+1;
	            } else {
	                right = mid - 1;
	            }
	        }

	        return list.get(result)[1];

	    }
	}
	
}


