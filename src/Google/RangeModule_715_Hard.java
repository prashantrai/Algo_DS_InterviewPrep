package Google;

import java.util.TreeMap;

public class RangeModule_715_Hard {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/*
	 What This Problem Really Is
		This is basically maintaining the union of intervals dynamically while supporting:
			merge on add
			containment check on query
			split/shrink on remove
	 * */
	
	/* Step-by-Step Algorithm
		addRange(left, right)
			Find the interval starting immediately before or at left.
			If it overlaps/touches [left, right), merge it and remove it.
			Find intervals starting at or before the current right.
			Merge each overlapping interval and remove it.
			Insert the final merged interval.
		queryRange(left, right)
			Find floorKey(left).
			If none exists, return false.
			Return whether its end is at least right.
		removeRange(left, right)
			Check whether an interval crossing left exists.
			Preserve its left piece [start, left) if necessary.
			Preserve [right, end) if that same interval extends past right.
			Remove intervals starting inside [left, right).
			If one of them extends beyond right, preserve its right piece.
	 * */
	
	/* Time Complexity
	 *  Let n be the number of currently stored disjoint intervals and 
	 *  k be the number of intervals affected by an add/remove operation.
	 * 
		queryRange:  O(log n)
		
		addRange:    O((k + 1) log n)
		
		removeRange: O((k + 1) log n)
		
		Each TreeMap search, insertion, or deletion costs O(log n).
		
		Importantly, intervals removed while merging cannot remain in the map, 
		so the work is proportional to the intervals actually affected.
		
	   Space Complexity: O(n)
		We store only the current disjoint intervals.
	 * */
	
	
	// start -> end for disjoint, merged intervals [start, end)
	private final TreeMap<Integer, Integer> ranges;
	
	public RangeModule_715_Hard() {
		this.ranges = new TreeMap<>();
	}

	
	/* The mental model to use in the interview

		Think of addRange() as:
		
		1. Look LEFT once.
		2. Sweep RIGHT while ranges overlap.
		3. Store one final merged interval.
		
		Or even shorter:
		
		        floorKey()
		            ↓
		      check left neighbor
		
		[existing] [ NEW RANGE ] [existing] [existing]
		                └─────────────→
		                    ceilingKey()
		                    merge right
		
		The critical invariant is:
		
		The TreeMap always contains sorted, non-overlapping intervals.
	 * */
	public void addRange(int left, int right) {
		// Merge with the interval immediately before left, if overlapping.
		Integer start = ranges.floorKey(left);
		
		if(start != null && ranges.get(start) >= left) {
			left = start;
			right = Math.max(right, ranges.get(start));
			ranges.remove(start);
		}
		
		// Merge all intervals that overlap/touch the new range.
		Integer next = ranges.ceilingKey(left);
		
		while (next != null && next <= right) {
			right = Math.max(right, ranges.get(next));
			ranges.remove(next);
			next = ranges.ceilingKey(left);
		}
		ranges.put(left, right);
		
    }
    
    public boolean queryRange(int left, int right) {
    	// Find the interval with the greatest start <= left.
    	Integer start = ranges.floorKey(left);
    	
    	return start != null && ranges.get(start) >= right;
        
    }
    
    /*removeRange(left, right)
		Check whether an interval crossing left exists.
		Preserve its left piece [start, left) if necessary.
		Preserve [right, end) if that same interval extends past right.
		Remove intervals starting inside [left, right).
		If one of them extends beyond right, preserve its right piece.
     * */
    public void removeRange(int left, int right) {
    	// Handle an interval that starts before left
        // but overlaps the removal range.
    	Integer start = ranges.floorKey(left);
    	
    	if(start != null) {
    		int end = ranges.get(start);
    		
    		if(left < end) {
    			ranges.remove(start);
    			
    			// Preserve the portion before left.
    			if(start < left) {
    				ranges.put(start, left);
    			}
    			
    			// The same interval extends beyond right.
    			if(right < end) {
    				ranges.put(right, end);
    			}
    		}
    	}
    }
    
    
}
