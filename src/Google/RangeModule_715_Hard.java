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

        if (start != null) {
            int end = ranges.get(start);

            if (end > left) {
                ranges.remove(start);

                // Preserve the portion before left.
                if (start < left) {
                    ranges.put(start, left);
                }

                // The same interval extends beyond right.
                if (end > right) {
                    ranges.put(right, end);
                    return;
                }
            }
        }

        // Remove intervals that start inside [left, right).
        Integer next = ranges.ceilingKey(left);

        while (next != null && next < right) {

            int end = ranges.get(next);
            ranges.remove(next);

            // Preserve the portion extending beyond right.
            if (end > right) {
                ranges.put(right, end);
                break;
            }

            next = ranges.ceilingKey(left);
        }
    }
    
    
    
    /** Using Segment Tree */
    
    
    /* Interview Explanation Before Coding
		“Since the coordinate range is very large, I won't use an array-based segment tree. 
		I'll use a dynamic segment tree where the root represents the entire coordinate domain, 
		but children are created only when an operation actually needs them.
		
		Each node stores whether its entire interval is tracked. Add and remove are 
		range-assignment operations: add assigns true and remove assigns false. 
		I'll use lazy propagation so that if an update completely covers a node, 
		I can update that node without descending further.
		
		For a query, if the queried interval completely covers the current node, 
		I return whether that node is fully tracked. Otherwise I recursively query 
		the relevant children, and since the entire requested range must be covered, 
		I combine results using logical AND.
		
		This gives logarithmic tree depth because the coordinate domain is roughly one billion.”
     * */
    
    static class RangeModule_SegmentTree {

        private static class Node {
            Node left;
            Node right;

            // true if this entire segment is tracked
            boolean tracked;

            // -1 = no pending assignment
            //  0 = assign false
            //  1 = assign true
            int lazy = 0;
        }

        private final Node root;

        private static final int MIN = 1;
        private static final int MAX = 1_000_000_000;

        public RangeModule_SegmentTree() {
            root = new Node();
        }

        public void addRange(int left, int right) {
            update(root, MIN, MAX, left, right - 1, true);
        }

        public boolean queryRange(int left, int right) {
            return query(root, MIN, MAX, left, right - 1);
        }

        public void removeRange(int left, int right) {
            update(root, MIN, MAX, left, right - 1, false);
        }

        private void update(
                Node node,
                int start,
                int end,
                int left,
                int right,
                boolean value) {

            // No overlap.
            if (right < start || end < left) {
                return;
            }

            // Complete overlap.
            if (left <= start && end <= right) {
                node.tracked = value;
                node.lazy = value ? 1 : 0;
                node.left = null;
                node.right = null;
                return;
            }

            pushDown(node);

            int mid = start + (end - start) / 2;

            if (left <= mid) {
                update(node.left, start, mid, left, right, value);
            }

            if (right > mid) {
                update(node.right, mid + 1, end, left, right, value);
            }

            // Entire parent is tracked only if both children are tracked.
            node.tracked = node.left.tracked && node.right.tracked;
            node.lazy = -1;
        }

        private boolean query(
                Node node,
                int start,
                int end,
                int left,
                int right) {

            // Current node completely inside query.
            if (left <= start && end <= right) {
                return node.tracked;
            }

            // If this entire segment has one known value,
            // every subsection has that same value.
            if (node.lazy != -1) {
                return node.tracked;
            }

            int mid = start + (end - start) / 2;

            if (right <= mid) {
                return query(node.left, start, mid, left, right);
            }

            if (left > mid) {
                return query(node.right, mid + 1, end, left, right);
            }

            // Query crosses the midpoint.
            return query(node.left, start, mid, left, mid)
                    && query(node.right, mid + 1, end, mid + 1, right);
        }

        private void pushDown(Node node) {

            if (node.left == null) {
                node.left = new Node();
            }

            if (node.right == null) {
                node.right = new Node();
            }

            // Propagate parent's known assignment.
            if (node.lazy != -1) {
                boolean value = node.lazy == 1;

                node.left.tracked = value;
                node.right.tracked = value;

                node.left.lazy = node.lazy;
                node.right.lazy = node.lazy;

                node.lazy = -1;
            }
        }
    }
    
}
