package Google;

import java.util.*;

public class IntervalSet {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/* Complexity
	add():      O((k + 1) log n)
	contains(): O(log n)
	
	space:      O(n)
	 * */
	
	// In TreeMap, every lookup/remove/put is O(log n).
	private TreeMap<Integer, Integer> ranges;
	
	// Follow-up 3: Return total covered length
    // Keep the total number of covered integer points.
    private long totalCovered;
	
	public IntervalSet() {
		ranges = new TreeMap();	
	}
	
	// add range
	public void add(int start, int end) {

        // Merge with the interval immediately before start,
        // if it overlaps or touches the new interval.
        Integer left = ranges.floorKey(start);
        
        /* Why +1/-1 for touching intervals?
         	
         	consider touching/adjacent inclusive integer ranges:
			existing = [1, 5]
			new      = [6, 10]
			
			There is technically no shared integer:
			[1 2 3 4 5] [6 7 8 9 10]
			          ↑ ↑
			          5 6
			
			Normal overlap check says: 5 >= 6     // false
			
			But the problem says these should merge:
			
			[1,5] + [6,10]
			        ↓
			[1,10]
			
			Why? Because for integer coordinates, 6 is immediately after 5.
			
			So we want to recognize: existing.end + 1 >= new.start
			
			For our example:
			
			5 + 1 >= 6
			6 >= 6
			true
			
			That's the entire +1 idea.
         * */
        
        // -1, because statement says touching intervals should also merge.
        // could also be written like, ranges.get(left)+1 >= start
        // ranges.get(left) + 1 >= start and ranges.get(left) >= start - 1 are mathematically same
        if (left != null && ranges.get(left) >= start - 1) {
        	
        	int leftEnd = ranges.get(left);
        	
        	// Follow-up 3: Return total covered length
            // Remove the old interval's contribution before merging it.
            totalCovered -= intervalLength(left, leftEnd);
        	
            start = left;
            end = Math.max(end, leftEnd);
            ranges.remove(left);
        }
        
        // Merge all following intervals that overlap or touch.
        Integer next = ranges.ceilingKey(start);

        // +1, because statement says touching intervals should also merge.
        while (next != null && next <= end + 1) {
            
        	int nextEnd = ranges.get(next);

            // Follow-up 3: Return total covered length
            // This interval will be absorbed into the merged interval,
            // so remove its previous contribution.
            totalCovered -= intervalLength(next, nextEnd);
        	
        	end = Math.max(end, nextEnd);
            ranges.remove(next);

            next = ranges.ceilingKey(start); // Returns the least key greater than or equal to the specified key
        }

        // Store the final merged interval.
        ranges.put(start, end);
        
        // Follow-up 3: Return total covered length
        // Add the contribution of the final merged interval.
        totalCovered += intervalLength(start, end);
    }
	
	
	public boolean contains(int x) {

	    // Find the interval with the largest start <= x.
	    Integer left = ranges.floorKey(x);

	    // No interval starts before x.
	    if (left == null) {
	        return false;
	    }

	    // x is covered if it does not exceed that interval's end.
	    return x <= ranges.get(left);
	}
	
	// Follow-up 1: Query an entire interval | Priority: EXTREMELY HIGH
	// Return true only if every point in [start, end] is currently covered.
	// O(log n)
	public boolean contains_EntireInterval(int start, int end) {
		
		// Find the only interval that could fully cover [start, end].
		Integer left = ranges.floorKey(start);
		
		if(left == null) return false;
		
		return end <= ranges.get(left);
	}
	
	// Follow-up 2: Remove an interval | Priority: VERY HIGH
	/* Example:
		Stored: [1,10]
		remove(4,7)

		Result: [1,3], [8,10]
	 * */
	// O((k + 1) log n)
	public void remove(int start, int end) {

        Integer left = ranges.floorKey(start);

        if (left != null) {

            int right = ranges.get(left);

            if (right >= start) {

                // Follow-up 3: Return total covered length
                // The original interval is being changed/removed,
                // so subtract its full old contribution first.
                totalCovered -= intervalLength(left, right);

                ranges.remove(left);

                // Keep the portion before the removed range.
                if (left < start) {
                    ranges.put(left, start - 1);

                    // Follow-up 3: Return total covered length
                    totalCovered += intervalLength(left, start - 1);
                }

                // Same interval extends beyond the removal range.
                if (right > end) {
                    ranges.put(end + 1, right);

                    // Follow-up 3: Return total covered length
                    totalCovered += intervalLength(end + 1, right);

                    return;
                }
            }
        }

        Integer next = ranges.ceilingKey(start);

        while (next != null && next <= end) {

            int right = ranges.get(next);

            // Follow-up 3: Return total covered length
            // This existing interval is being removed/modified.
            totalCovered -= intervalLength(next, right);

            ranges.remove(next);

            // Preserve the part beyond the removed range.
            if (right > end) {
                ranges.put(end + 1, right);

                // Follow-up 3: Return total covered length
                totalCovered += intervalLength(end + 1, right);

                break;
            }

            next = ranges.ceilingKey(start);
        }
    } // remove() close
	
	
	// Follow-up 3: Return total covered length
    // O(1) because we maintain the value incrementally.
    public long coveredLength() {
        return totalCovered;
    }

    // Follow-up 3: Return total covered length
    // Inclusive interval length: [start, end] has end - start + 1 points.
    private long intervalLength(int start, int end) {
        return (long) end - start + 1;
    }

}


/* C8. IntervalSet | Priority: EXTREMELY HIGH
	
	Design and implement an IntervalSet that stores a dynamic set of integer intervals.
	
	The data structure must support:
	
		void add(int start, int end);
		boolean contains(int x);
	
	Calling add(start, end) adds the inclusive interval [start, end] to the set.
	
	Intervals may overlap, be contained inside one another, or touch an existing interval. 
	The IntervalSet should internally maintain the union of all added intervals as 
	a collection of sorted, non-overlapping intervals.
	
	For this problem, assume touching intervals should also be merged.
	
	For example:
	
	[1, 5] + [6, 10]
	
	becomes
	
	[1, 10]
	
	You may assume:
	
	start <= end
	
	The calls arrive online, so all intervals are not known in advance.
	
	Example 1 — Basic merge
	IntervalSet set = new IntervalSet();
	
	set.add(1, 5);
	set.add(8, 10);
	set.add(4, 9);
	
	After the first operation:
	
	[1, 5]
	
	After the second:
	
	[1, 5]
	[8, 10]
	
	After:
	
	add(4, 9)
	
	the new interval overlaps both existing intervals:
	
	[1, 5]
	   [4, 9]
	       [8, 10]
	
	so the stored representation becomes:
	
	[1, 10]
	
	
	Queries:
	
	contains(1)  -> true
	contains(7)  -> true
	contains(10) -> true
	contains(11) -> false
	contains(20) -> false
	Example 2 — Completely disjoint intervals
	add(2, 4)
	add(10, 12)
	add(20, 25)
	
	Stored:
	
	[2, 4]
	[10, 12]
	[20, 25]
	
	
	Queries:
	
	contains(3)  -> true
	contains(5)  -> false
	contains(12) -> true
	contains(18) -> false
	contains(21) -> true
	Example 3 — New interval contains existing intervals
	add(3, 5)
	add(8, 10)
	add(12, 15)
	
	add(1, 20)
	
	Before:
	
	[3,5] [8,10] [12,15]
	
	After:
	
	[1,20]
	
	The new interval absorbs all existing intervals.
	
	Example 4 — New interval is already covered
	add(1, 10)
	add(3, 7)
	
	Stored representation remains:
	
	[1,10]
	
	because [3,7] adds no new coverage.
	
	Example 5 — Bridge multiple ranges
	add(1, 3)
	add(7, 9)
	add(13, 15)
	
	add(3, 13)
	
	Before:
	
	[1,3] [7,9] [13,15]
	
	After:
	
	[1,15]
	
	This is an important case because one insertion can merge several existing intervals.
	
	Expected Data Structure
	
	A natural Java implementation is:
	
	TreeMap<Integer, Integer>
	
	where:
	
	key   = interval start
	value = interval end
	
	Maintain the invariant:
	
	Intervals are sorted by start.
	
	No two stored intervals overlap.
	
	No two stored intervals touch.
	
	For example:
	
	1  -> 5
	10 -> 14
	20 -> 25
	
	represents:
	
	[1,5], [10,14], [20,25]
	
	This structure makes contains(x) especially clean: find the interval with 
	the greatest start <= x using floorEntry(x) and check whether its end reaches x. 
	
	This predecessor-query idea is also the core approach described in reported 
	Google versions of the problem.
	
	What the Interviewer Is Testing
	The problem is less about ordinary “Merge Intervals” and more about maintaining 
	a data-structure invariant incrementally.
	
	The important insight is:
	
	Do the merging during add(),
	so contains() never needs to scan or rebuild intervals.
	
	You should recognize:
	
	ordered intervals
	        +
	predecessor/successor lookup
	        ↓
	TreeMap
	
	
	Closest LeetCode Problems: LC 715 — Range Module
	
	Closest match.
	
	It additionally supports:
	
	addRange(...)
	queryRange(...)
	removeRange(...)
	
	and normally uses half-open intervals [left, right).
	
	Your base problem is significantly simpler because it only needs:
	
	add()
	contains(point)
	
	Also useful:
	
	LC 56 — Merge Intervals
	
	Same merging concept, but LC 56 is an offline batch problem, whereas 
	IntervalSet must maintain the merged representation dynamically.
	
	
	
	Likely GOOGLE Follow-ups:: 
	
	* 
	* Follow-up 1 — Query an entire interval | Priority: EXTREMELY HIGH
	
	Add:
	boolean contains(int start, int end);
	
	Return true only if every point in [start, end] is currently covered.
	
	Example:
	
	Stored:
	
	[1,10]
	[20,30]
	
	Queries:
	
	contains(3,7)   -> true
	contains(1,10)  -> true
	contains(5,15)  -> false
	contains(15,25) -> false
	contains(21,29) -> true
	
	Key observation:
	
	You do not need to scan all intervals.
	
	Find:
	
	floorEntry(start)
	
	and check whether that interval's end is:
	
	>= end
	
	Expected complexity:
	
	O(log n)
	
	This moves the problem closer to LC 715.
	
	* 
	* 
	* Follow-up 2 — Remove an interval | Priority: VERY HIGH
	
	Add: void remove(int start, int end);
	
	Remove all points in the given inclusive interval from the set.
	
	Example:
	
		Stored:
		[1,10]
		
		remove(4,7)
		
		Result:
		[1,3]
		[8,10]
	
	Another example:
	
		Stored:
		
		[1,5] [10,20]
		
		remove(3,12)
		
		Result:
		
		[1,2]
		[13,20]
		
		This is substantially harder because removal can:
		
		delete an interval
		shrink an interval
		or
		split one interval into two
	
	This is the most natural extension toward the full LC 715 Range Module problem.
	
	
	* 
	* 
	* Follow-up 3 — Return total covered length | Priority: HIGH
	
	Add:
	
	long coveredLength();
	
	It should return the total number of integer points currently covered.
	
	Example:
	
	Stored:
	
	[1,5]
	[10,12]
	
	For inclusive integer ranges:
	
	[1,5]   -> 5 points
	[10,12] -> 3 points
	
	coveredLength() -> 8
	
	After:
	
	add(4,11)
	
	Stored:
	
	[1,12]
	
	Therefore:
	
	coveredLength() -> 12
	
	The important follow-up question becomes:
	
	Can we return the answer in O(1) instead of iterating over every stored interval?
	
	Maintain:
	
	long totalCovered;
	
	and adjust it whenever intervals are removed/merged/inserted during add().
	
	This tests whether you can augment the data structure without breaking its invariant.
	
	
	* 
	* 
	* Follow-up 4 — Very large scale / concurrent queries | Priority: MEDIUM-HIGH
	
	Suppose the system receives:
	
	millions of intervals
	
	and the workload contains far more:
	
	contains()
	
	operations than:
	
	add()
	
	How would you optimize or redesign it?
	
	Things worth discussing:
	
	TreeMap:
	    contains -> O(log n)
	
	If updates become rare:
	    periodically materialize the merged intervals
	    into a sorted array.
	
	Then:
	    contains -> binary search O(log n)
	
	Advantages:
	    lower memory overhead
	    better cache locality
	    immutable snapshots can support concurrent readers
	
	If the integer universe is small and bounded, another possible trade-off is 
	direct addressing / a bitset, potentially giving constant-time membership at 
	the cost of memory. 
	
	Similar query-vs-memory trade-offs have appeared in reported Google interval-membership 
	interview variants.
	
	
	Priority order I would prepare
	Base IntervalSet                     EXTREMELY HIGH
	
	1. contains(start, end)              EXTREMELY HIGH
	2. remove(start, end)                VERY HIGH
	3. maintain total covered length     HIGH
	4. scale / read-heavy optimization   MEDIUM-HIGH
	
	
	For phone-screen preparation, I would be comfortable treating 
	the base problem + Follow-ups 1 and 2 as must-know. 
	
	The reported Google versions strongly center on dynamic insertion, 
	point membership, and preserving merged/disjoint ranges, 
	making TreeMap.floorEntry() / neighboring-interval manipulation the 
	main skill to be ready to explain.

 */