package Google;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

public class SplitOverlappingTextAnnotations {

	public static void main(String[] args) {

	    // Test 1: Overlapping annotations
	    List<Annotation> test1 = Arrays.asList(
	        new Annotation(0, 4, "X"),
	        new Annotation(5, 8, "Y"),
	        new Annotation(3, 6, "Z")
	    );

	    printResult("Test 1 - Overlapping", splitAnnotations(test1));
	    /*
	     * Expected:
	     * [0,3) -> [X]
	     * [3,4) -> [X, Z]
	     * [4,5) -> [Z]
	     * [5,6) -> [Y, Z]
	     * [6,8) -> [Y]
	     */


	    // Test 2: Fully overlapping / nested intervals
	    List<Annotation> test2 = Arrays.asList(
	        new Annotation(1, 7, "A"),
	        new Annotation(3, 5, "B")
	    );

	    printResult("Test 2 - Nested", splitAnnotations(test2));
	    /*
	     * Expected:
	     * [1,3) -> [A]
	     * [3,5) -> [A, B]
	     * [5,7) -> [A]
	     */


	    // Test 3: Touching but not overlapping
	    List<Annotation> test3 = Arrays.asList(
	        new Annotation(0, 3, "A"),
	        new Annotation(3, 6, "B")
	    );

	    printResult("Test 3 - Touching", splitAnnotations(test3));
	    /*
	     * Expected:
	     * [0,3) -> [A]
	     * [3,6) -> [B]
	     */


	    // Test 4: Multiple annotations start at same position
	    List<Annotation> test4 = Arrays.asList(
	        new Annotation(0, 5, "A"),
	        new Annotation(0, 3, "B"),
	        new Annotation(0, 2, "C")
	    );

	    printResult("Test 4 - Same Start", splitAnnotations(test4));
	    /*
	     * Expected:
	     * [0,2) -> [A, B, C]
	     * [2,3) -> [A, B]
	     * [3,5) -> [A]
	     */


	    // Test 5: No overlap with a gap
	    List<Annotation> test5 = Arrays.asList(
	        new Annotation(0, 2, "A"),
	        new Annotation(5, 7, "B")
	    );

	    printResult("Test 5 - Gap", splitAnnotations(test5));
	    /*
	     * Expected:
	     * [0,2) -> [A]
	     * [5,7) -> [B]
	     *
	     * [2,5) is not returned because no annotation is active.
	     */


	    // Edge Case 1: Single annotation
	    List<Annotation> test6 = Arrays.asList(
	        new Annotation(2, 10, "A")
	    );

	    printResult("Test 6 - Single Annotation", splitAnnotations(test6));
	    /*
	     * Expected:
	     * [2,10) -> [A]
	     */


	    // Edge Case 2: Empty input
	    List<Annotation> test7 = new ArrayList<>();

	    printResult("Test 7 - Empty Input", splitAnnotations(test7));
	    /*
	     * Expected:
	     * empty result
	     */


	    // Edge Case 3: All intervals exactly overlap
	    List<Annotation> test8 = Arrays.asList(
	        new Annotation(0, 5, "A"),
	        new Annotation(0, 5, "B"),
	        new Annotation(0, 5, "C")
	    );

	    printResult("Test 8 - Exact Same Range", splitAnnotations(test8));
	    /*
	     * Expected:
	     * [0,5) -> [A, B, C]
	     */


	    // Edge Case 4: Multiple events at same boundary
	    List<Annotation> test9 = Arrays.asList(
	        new Annotation(0, 3, "A"),
	        new Annotation(1, 3, "B"),
	        new Annotation(3, 5, "C"),
	        new Annotation(3, 6, "D")
	    );

	    printResult("Test 9 - Multiple Events At Same Position",
	                splitAnnotations(test9));
	    /*
	     * Expected:
	     * [0,1) -> [A]
	     * [1,3) -> [A, B]
	     * [3,5) -> [C, D]
	     * [5,6) -> [D]
	     */
	}

	private static void printResult(String testName, List<Segment> result) {

	    System.out.println("\n" + testName);

	    if (result.isEmpty()) {
	        System.out.println("[]");
	        return;
	    }

	    for (Segment segment : result) {
	        System.out.println(
	            "[" + segment.start + "," + segment.end + ") -> "
	            + segment.labels
	        );
	    }
	}
	
	
	
	// Solution Starts...
	
	static class Annotation {
		int start, end;
		String label;
		Annotation(int start, int end, String label) {
            this.start = start;
            this.end = end;
            this.label = label;
        }
	}
	static class Segment {
		int start, end;
		List<String> labels;
		Segment(int start, int end, List<String> labels) {
            this.start = start;
            this.end = end;
            this.labels = labels;
        }
	}
	static class Event {
		int position;
		String label;
		boolean isStart;
		Event(int position, String label, boolean isStart) {
            this.position = position;
            this.label = label;
            this.isStart = isStart;
        }
	}
	
	/*
	 	Input:

		[0,10) -> A
		[2,8)  -> B
		[4,6)  -> C
		
		Output:
		
		[0,2)  -> [A]
		[2,4)  -> [A,B]
		[4,6)  -> [A,B,C]
		[6,8)  -> [A,B]
		[8,10) -> [A]
	 * */
	
	/*
     * Interview approach:
     *
     * 1. Convert every annotation into two events:
     *      start -> add label
     *      end   -> remove label
     *
     * 2. Sort all events by coordinate.
     *
     * 3. Sweep from left to right.
     *
     * 4. Between two consecutive event coordinates, the active annotation
     *    set does not change, so that range forms one output segment.
     *
     * 5. Process ALL events at the same coordinate together before moving
     *    to the next coordinate.
     *
     * Time:  O(n log n)
     * Space: O(n)
     */
	
	public static List<Segment> splitAnnotations(List<Annotation> annotations) {
		
		List<Segment> result = new ArrayList<>();
		
		if (annotations == null || annotations.isEmpty()) {
		    return result;
		}
		
		// Convert every annotation into two events:
	    //      start -> add label
	    //      end   -> remove label
		// Build start/end events.
		
		List<Event> events = new ArrayList<>();
		
		for(Annotation a : annotations) {
			events.add(new Event (a.start, a.label, true));
			events.add(new Event (a.end, a.label, false));
		}
		
		// Sweep events from left to right.
		/* Events list after sorting: 
		 * 	0 start X
			3 start Z
			4 end   X
			5 start Y
			6 end   Z
			8 end   Y
		 * */
		events.sort((a,b) -> Integer.compare(a.position, b.position));
		
		// TreeSet keeps labels deterministic/sorted in the output.
		// active keeps each label only once for the output.
		Set<String> active = new TreeSet<>();
		
		/* Follow-up 1: labelFreqMap keeps how many intervals with that label
	     are currently active. 
	     Example:
      		[0,5) -> X
      		[2,7) -> X
     
      		Between [2,5):
	      	active       = [X]
	      	labelFreqMap = {X=2} 
	    */
		Map<String, Integer> labelFreqMap = new HashMap<>(); // Follow-up 1
		
		int i = 0;
		int prevPos = events.get(0).position;
		
		while (i < events.size()) {
			int currPos = events.get(i).position;
			
			/* The active set represents everything active between
             * previousPosition and currentPosition. */
			
			if(prevPos < currPos && !active.isEmpty()) {
				
				// Follow-up 1:
			    // If the visible label set did not change,
			    // extend the previous segment instead of creating a new one.
				List<String> currentLabels = new ArrayList<>(active);
				if(!result.isEmpty()) {
					
					Segment last = result.get(result.size()-1);
					
					if(last.end == prevPos && last.labels.equals(currentLabels)) {
						
						last.end = currPos;
						
					} else {
						result.add(new Segment(prevPos, currPos, new ArrayList<>(active)));
					}
				}
				else {
					result.add(new Segment(prevPos, currPos, new ArrayList<>(active)));
				}
			}
			
			
			/* Process every event occurring at this same coordinate.
             *
             * Example:
             * [0,3) -> A
             * [3,5) -> B
             *
             * At position 3:
             *   remove A
             *   add B
             *
             * So the next range [3,5) correctly contains only B. */
			
			while(i < events.size() && events.get(i).position == currPos) {
				Event event = events.get(i);
				
				if(event.isStart) {
					
					// Follow-up 1:Another interval with this label becomes active.
					// maintain label freq 
					labelFreqMap.put(event.label, labelFreqMap.getOrDefault(event.label, 0) + 1);
					
					// Set guarantees the label appears only once.
					active.add(event.label);
				} else {
					// Follow-up 1: One interval with this label ended.
	                int count = labelFreqMap.get(event.label) - 1;
	                
	                /* Only remove the label when ALL intervals
	                 * having this label have ended. */
	                
	                if(count == 0) {
	                	labelFreqMap.remove(event.label); // Follow-up 1
	                	active.remove(event.label);
	                } 
	                else { // Follow-up 1
	                	labelFreqMap.put(event.label, count);
	                }
					
				}
				
				i++;
			}
			
			prevPos = currPos;
			
		}
		
		return result;
	}
	
	
	//Follow-up 2: Case 1 ; when annotation are cmpletely sorted: 
	// 				Then we can remove the sorting step and rest can stay as it is.  
	
	
	/* Follow-up 2: Case 2: Annotations are only sorted by start → not enough

		Suppose:
		[0,100) -> A
		[1,2)   -> B
		[3,4)   -> C
		
		They are sorted by start:
		
		starts:
		0, 1, 3
		
		But their events are:
		
		0 START A
		100 END A
		
		1 START B
		2 END B
		
		3 START C
		4 END C
		
		If you simply process annotation-by-annotation, you'd effectively see:
		
		0, 100, 1, 2, 3, 4
		
		which is not coordinate sorted.
		
		The actual sweep order must be:
		
		0 START A
		1 START B
		2 END B
		3 START C
		4 END C
		100 END A
		
		So knowing:
		
		annotations are sorted by start
		
		does not mean:
		
		all start/end events are sorted.
		
		A likely interviewer follow-up
		
		They might then ask:
		
		"If annotations are already sorted by start, can we still avoid sorting all 2n events?"
		
		Yes. There is a more interesting solution.
		
		Because the start events are already sorted, you can maintain the end events separately in a min-heap by end coordinate.
		
		Conceptually:
		
		Sorted annotations:
		[0,100) A
		[1,2)   B
		[3,4)   C
		
		next start = 0
		next end   = none
		
		process start 0
		end heap = [100]
		
		next start = 1
		next end   = 100
		→ process start 1
		
		end heap = [2,100]
		
		next start = 3
		next end   = 2
		→ process end 2
		
		next start = 3
		next end   = 100
		→ process start 3
		...
		
		You repeatedly process whichever comes first:
		
		next start coordinate
		vs.
		smallest active end coordinate
		
		This avoids explicitly creating and sorting all 2n events.
		
		But because of the heap:
		
		Time: O(n log n)
		Space: O(n)
		
		So asymptotically it is still O(n log n), although it takes advantage of 
		the start-sorted input.
	 * */
	/* Interview Script:
		"If all start/end events are already globally sorted, I can remove sorting 
		and sweep in linear time. But if only the intervals are sorted by start, 
		that isn't sufficient because their end coordinates can appear in arbitrary order. 
		I would still need to order the end boundaries, for example using a min-heap."
	*/	
	
	// Time: O(n log n), Space: O(n)
	public static List<Segment> splitAnnotations_Case2(List<Annotation> annotations) {

	    List<Segment> result = new ArrayList<>();

	    if (annotations == null || annotations.isEmpty()) {
	        return result;
	    }

	    // TreeSet keeps labels deterministic/sorted in output.
	    Set<String> active = new TreeSet<>();

	    /*
	     * Follow-up 1:
	     * Track how many intervals of each label are currently active.
	     */
	    Map<String, Integer> labelFreqMap = new HashMap<>();


	    // Case 2: Annotations are only sorted by start → not enough
	    // Since starts are already sorted, we process them directly from annotations.
	    int startIndex = 0;

	    // Case 2: Annotations are only sorted by start → not enough
	    // End positions are NOT necessarily sorted, so keep active intervals
	    // ordered by end position using a min-heap.
	    PriorityQueue<Annotation> endMinHeap =
	        new PriorityQueue<>(
	            (a, b) -> Integer.compare(a.end, b.end)
	        );


	    // Case 2: Annotations are only sorted by start → not enough
	    // Our first boundary is the first annotation's start.
	    int prevPos = annotations.get(0).start;


	    // Case 2: Annotations are only sorted by start → not enough
	    // Continue while there are either unprocessed starts
	    // or active intervals waiting to end.
	    while (startIndex < annotations.size() || !endMinHeap.isEmpty()) {

	        // Case 2: Annotations are only sorted by start → not enough
	        // Next possible start coordinate.
	        int nextStart = startIndex < annotations.size()
	                ? annotations.get(startIndex).start
	                : Integer.MAX_VALUE;

	        // Case 2: Annotations are only sorted by start → not enough
	        // Smallest pending end coordinate.
	        int nextEnd = !endMinHeap.isEmpty()
	                ? endMinHeap.peek().end
	                : Integer.MAX_VALUE;


	        // Case 2: Annotations are only sorted by start → not enough
	        // The next sweep-line boundary is whichever comes first:
	        // a new interval starting or an active interval ending.
	        int currPos = Math.min(nextStart, nextEnd);


	        /*
	         * Between prevPos and currPos, the visible active set
	         * remains unchanged.
	         */
	        if (prevPos < currPos && !active.isEmpty()) {

	            List<String> currentLabels = new ArrayList<>(active);

	            if (!result.isEmpty()) {

	                Segment last = result.get(result.size() - 1);

	                // Follow-up 1:
	                // Same labels + no gap => merge with previous segment.
	                if (last.end == prevPos
	                        && last.labels.equals(currentLabels)) {

	                    last.end = currPos;

	                } else {
	                    result.add(
	                        new Segment(prevPos, currPos, currentLabels)
	                    );
	                }

	            } else {
	                result.add(
	                    new Segment(prevPos, currPos, currentLabels)
	                );
	            }
	        }


	        /*
	         * Case 2: Annotations are only sorted by start → not enough
	         *
	         * Process ALL end events at currPos.
	         *
	         * Example:
	         *
	         * [0,3) -> A
	         * [3,5) -> B
	         *
	         * At position 3, A must end and B must start.
	         *
	         * Since everything at the same coordinate is processed
	         * before generating the next segment, the ordering of
	         * start-vs-end at that coordinate does not affect the result.
	         */
	        while (!endMinHeap.isEmpty()
	                && endMinHeap.peek().end == currPos) {

	            // Case 2: Annotations are only sorted by start → not enough
	            Annotation ending = endMinHeap.poll();

	            int count = labelFreqMap.get(ending.label) - 1;

	            if (count == 0) {
	                labelFreqMap.remove(ending.label);
	                active.remove(ending.label);
	            } else {
	                labelFreqMap.put(ending.label, count);
	            }
	        }


	        /*
	         * Case 2: Annotations are only sorted by start → not enough
	         *
	         * Process ALL annotations starting at currPos.
	         */
	        while (startIndex < annotations.size()
	                && annotations.get(startIndex).start == currPos) {

	            // Case 2: Annotations are only sorted by start → not enough
	            Annotation starting = annotations.get(startIndex);

	            // Follow-up 1:
	            // Increase frequency for this label.
	            labelFreqMap.put(
	                starting.label,
	                labelFreqMap.getOrDefault(starting.label, 0) + 1
	            );

	            active.add(starting.label);

	            // Case 2: Annotations are only sorted by start → not enough
	            // Its end is not necessarily in sorted order,
	            // so add it to the end min-heap.
	            endMinHeap.offer(starting);

	            // Case 2: Annotations are only sorted by start → not enough
	            startIndex++;
	        }


	        // Case 2: Annotations are only sorted by start → not enough
	        prevPos = currPos;
	    }

	    return result;
	}
	
	
	
	
	/* Follow-up 3: Millions of Annotations / Streaming Input | Priority: HIGH
	   Suppose there are millions of annotations and you cannot store all 2n events in memory.
	 * */
	/*
	 For this follow-up, the key is to distinguish whether the incoming annotations are already ordered.

		If they are not ordered, you cannot perform an exact sweep without somehow ordering the boundaries first. With millions of annotations, instead of keeping all 2n events in memory, use external sorting / chunking.
		
		Interview answer
		
		"The sweep-line algorithm itself is still the right approach. The memory problem comes from materializing and sorting all 2n events in memory. For a very large input, I would process the annotations in chunks, generate start/end events for each chunk, sort each chunk independently, spill those sorted runs to disk, and then perform a k-way merge of the sorted runs. During that merge, I can run the sweep directly, so I never need all events in memory at once."
		
		Conceptually:
		
		Millions of annotations
		        |
		        v
		Read chunk
		        |
		        v
		Create start/end events
		        |
		        v
		Sort chunk in memory
		        |
		        v
		Write sorted run to disk
		
		Repeat...
		
		Then:
		
		Sorted Run 1 ----\
		Sorted Run 2 -----\
		Sorted Run 3 ------> K-way merge ---> Sweep Line ---> Output
		...---------------/
		
		You only need to keep:
		
		one/few events from each sorted run
		+
		active labels / frequency map
		+
		current output segment
		
		in memory.
		
		Modification to our current solution
		
		Right now we do:
		
		List<Event> events = new ArrayList<>();
		
		for (Annotation a : annotations) {
		    events.add(new Event(a.start, a.label, true));
		    events.add(new Event(a.end, a.label, false));
		}
		
		events.sort(...);
		
		For millions of annotations, we do not build this giant list.
		
		Instead:
		
		1. Read, say, 100K annotations.
		2. Generate their 200K events.
		3. Sort those events.
		4. Write the sorted events to a temporary file.
		5. Clear memory.
		6. Repeat.
		7. K-way merge all temporary sorted files.
		8. Feed merged events directly into the existing sweep.
		
		The actual sweep logic barely changes.
		
		If the input events are already globally sorted
		
		This is the best case.
		
		For example, if we receive:
		
		0 START X
		2 START X
		4 START Y
		5 END X
		6 END Y
		7 END X
		...
		
		as a stream, then we don't need either sorting or storing all events.
		
		We can process events as they arrive:
		
		read next coordinate
		     ↓
		emit [prevPos, currPos) using active labels
		     ↓
		process all events at currPos
		     ↓
		update active/frequency map
		     ↓
		move forward
		
		Memory becomes approximately:
		
		O(number of currently active labels)
		
		rather than:
		
		O(n)
		
		This is the ideal streaming solution.
		
		Important catch
		
		If annotations arrive completely unsorted like:
		
		[100,200) -> A
		[0,5)     -> B
		[50,60)   -> C
		
		you cannot safely emit output immediately.
		
		For example, after seeing:
		
		[100,200) -> A
		
		you cannot conclude that [100,200) is the first output range because a later annotation might start at 0.
		
		So true one-pass streaming with bounded memory requires some ordering guarantee.
		
		Without one, use external sorting.
		
		What if annotations are sorted by start?
		
		Then Follow-up 2's heap solution becomes useful.
		
		You don't have to retain all annotations:
		
		Incoming start-sorted annotations
		            |
		            v
		    process next start
		            |
		            v
		Min-heap of pending ends
		
		You retain only intervals that have started but haven't ended yet.
		
		Memory:
		
		O(k)
		
		where k is the maximum number of simultaneously active intervals, rather than O(n).
		
		Time:
		
		O(n log k)
		
		This can be substantially better when:
		
		k << n
		
		For example:
		
		100 million total annotations
		but at most 1,000 simultaneously active
		
		Memory ≈ O(1,000)
		instead of O(100 million)
		The answer I'd give Google
		
		"I would first ask whether the input has any ordering guarantee. If events are already globally sorted, I can make the sweep fully streaming and keep only the active label counts in memory, giving roughly O(n) time and O(k) memory, where k is the number of active labels. If annotations are sorted only by start, I can stream starts and maintain pending ends in a min-heap, giving O(n log k) time and O(k) memory. If the input is completely unsorted, exact output requires ordering the boundaries, so for data that doesn't fit in memory I'd use external sort: sort chunks, spill them to disk, k-way merge the runs, and feed that merged stream directly into the sweep."
		
		That's a strong interview response because it shows you recognize that "streaming" alone doesn't eliminate the need for ordering.
	  
	  
	 * */
	
	void test() {}// not in use, but keep, as it enables the collapse option for comment block 
	

}



/* Complete Problem Statement
Split Overlapping Text Annotations
Priority: EXTREMELY HIGH
Problem

	You are given a list of text annotations. Each annotation covers a continuous 
	range of positions and has an associated label.
	
	An annotation is represented as:
	
	[start, end) -> label
	
	where:
	start is inclusive.
	end is exclusive.
	label identifies the annotation.
	Multiple annotations may overlap.
	
	Split the coordinate space into the smallest non-overlapping consecutive 
	segments such that, within each returned segment, the set of active annotation 
	labels remains unchanged.
	
	Do not return segments that have no active annotations.
	
		# What "annotation" means here?
	
		An annotation is just a labeled interval — a piece of metadata that says 
		"this label applies to positions start up to (but not including) end." Think 
		of it like a highlight or tag placed over a range of text (or numbers on a 
		line.
		
		So [0,4) -> X means: "label X is active for every position from 0 up to, but not including, 4."
		
		Since end is exclusive, two annotations [0,3) and [3,6) touch but don't 
		overlap — position 3 belongs only to the second one.
		
		The actual problem: 
		
		You get several of these labeled intervals, and they can overlap arbitrarily. 
		You need to re-partition the number line into the smallest set of non-overlapping, 
		back-to-back segments such that:
		
		- Within any single output segment, the exact set of labels that are "active" 
		  (i.e., whose interval covers that segment) never changes. 
		- Segments with zero active labels are dropped entirely (gaps disappear from the output).
		
		In other words: find all the points where the "active set" could possibly change, 
		and cut the line into pieces at those points.
		
	
	
	
	Example 1
	Input:
	
	[0,4) -> X
	[5,8) -> Y
	[3,6) -> Z
	
	Visualization:
	
	X:  [0-----------4)
	Z:           [3-----------6)
	Y:                   [5-----------8)
	
	     0       3   4   5   6       8
	
	Output:
	
	[0,3) -> [X]
	[3,4) -> [X,Z]
	[4,5) -> [Z]
	[5,6) -> [Y,Z]
	[6,8) -> [Y]
	
	Explanation:
	
	The set of active annotations changes only at coordinates:
	
	0, 3, 4, 5, 6, 8
	
	Therefore, those coordinates define the segment boundaries.
	
	Example 2 — Fully overlapping annotations
	Input:
	
	[1,7) -> A
	[3,5) -> B
	
	Output:
	
	[1,3) -> [A]
	[3,5) -> [A,B]
	[5,7) -> [A]
	Example 3 — Touching but not overlapping
	Input:
	
	[0,3) -> A
	[3,6) -> B
	
	Output:
	
	[0,3) -> [A]
	[3,6) -> [B]
	
	Because the intervals are half-open, A ends exactly where B begins. There is no overlap.
	
	Example 4 — Multiple annotations starting at the same position
	Input:
	
	[0,5) -> A
	[0,3) -> B
	[0,2) -> C
	
	Output:
	
	[0,2) -> [A,B,C]
	[2,3) -> [A,B]
	[3,5) -> [A]
	Example 5 — Gap between annotations
	Input:
	
	[1,3) -> A
	[5,7) -> B
	
	Output:
	
	[1,3) -> [A]
	[5,7) -> [B]
	
	The range [3,5) is omitted because no annotation is active.
	
	Example 6 — Nested annotations
	Input:
	
	[0,10) -> A
	[2,8)  -> B
	[4,6)  -> C
	
	Output:
	
	[0,2)  -> [A]
	[2,4)  -> [A,B]
	[4,6)  -> [A,B,C]
	[6,8)  -> [A,B]
	[8,10) -> [A]
	Constraints
	
	A reasonable interview version could use:
	
	1 <= annotations.length <= 100,000
	0 <= start < end <= 10^9
	label is a non-empty string
	
	The input annotations are not guaranteed to be sorted.
	
	Expected approach
	Sweep Line + Active Set
	
	Create two events for every annotation:
	
	start -> add label
	end   -> remove label
	
	Process all event coordinates from left to right. Between two consecutive event coordinates, 
	the active set cannot change, so that range becomes one output segment whenever the active 
	set is non-empty.
	
	Important interview detail
	
	When multiple events occur at the same coordinate, process them together before generating 
	the next segment.
	
	For example:
	
	[0,3) -> A
	[3,5) -> B
	
	At coordinate 3:
	
	remove A
	add B
	
	Then [3,5) correctly contains only B.
	
	Complexity
	
	For n annotations:
	
	2n events
	
	Sorting events: O(n log n)
	Sweep:          O(n) plus active-set maintenance
	Space:          O(n)
	Closest LeetCode problems
	
	There is no exact LeetCode equivalent.
	
	The closest patterns are:
	
	LC 56 — Merge Intervals: interval boundary reasoning, but it does not maintain overlapping labels.
	LC 732 — My Calendar III: sweep-line/event processing over overlapping intervals.
	LC 253 — Meeting Rooms II: start/end event processing and active intervals.
	
	The core interview pattern is closer to LC 732, but instead of maintaining only an overlap count, 
	you maintain the actual set of active annotations.
	
   	
   	* 
   	* Follow-up 1: Duplicate / Repeated Labels
	Priority: VERY HIGH
	
	Suppose multiple input annotations may have the same label, and their ranges may overlap.
	
	For example:
	
	[0,5) -> X
	[2,7) -> X
	[4,6) -> Y
	
	The output should contain each label only once, regardless of how many annotations with 
	that label are active.
	
	Return:
	
	[0,4) -> [X]
	[4,6) -> [X,Y]
	[6,7) -> [X]
	Important catch
	
	A simple:
	
	Set<String> active
	
	is no longer sufficient.
	
	Consider:
	
	[0,5) -> X
	[2,7) -> X
	
	At position 5, the first X ends, but the second X is still active.
	
	If we simply execute:
	
	active.remove("X");
	
	we would incorrectly remove X.
	
	Expected modification
	
	Maintain an active frequency map:
	
	label -> number of currently active intervals
	
	Example:
	
	X -> 2
	Y -> 1
	
	On a start event:
	
	count[label]++
	
	On an end event:
	
	count[label]--
	
	if count == 0:
	    remove label
	
	So the underlying approach becomes:
	
	Sweep Line + Active Frequency Map
	
	
	* 
	* 
	* Follow-up 2: Input Is Already Sorted by Coordinate | Priority: HIGH
	
	Suppose the annotations or their start/end events are already provided in coordinate order.
	
	Can you avoid sorting?
	
	If the input consists directly of sorted events such as:
	
	0 START X
	3 START Z
	4 END X
	5 START Y
	6 END Z
	8 END Y
	
	then the sweep itself becomes:
	
	O(n)
	
	instead of:
	
	O(n log n)
	
	because the sorting step disappears.
	
	If only the annotation list is sorted by start, however, the end coordinates are not 
	necessarily sorted:
	
	[0,100)
	[1,2)
	[3,4)
	
	So merely having annotations sorted by start does not automatically eliminate the 
	need to order all boundary events.
	
	An interviewer may ask you to recognize this distinction.
	
	
	* 
	* 
	* Follow-up 3: Millions of Annotations / Streaming Input | Priority: HIGH
	
	Suppose there are millions of annotations and you cannot store all 2n events in memory.
	
	How would you modify the design?
	
	One possible formulation:
	
	Annotations arrive from external storage and may be much larger than available memory. 
	Produce the same segmented output while minimizing memory usage.
	
	If events can be obtained in sorted coordinate order, the algorithm can operate as a streaming sweep:
	
	previousCoordinate
	active annotations
	current group of events
	
	Process one coordinate at a time and immediately emit completed segments.
	
	Memory then depends primarily on:
	
	number of simultaneously active annotations
	
	rather than the total number of annotations.
	
	If the raw input is completely unsorted, some mechanism for ordering the events is 
	still required, such as external sorting.
	
	
	
	* Follow-up 4: Avoid Sorting When Coordinates Are Bounded | Priority: MEDIUM
	
	Suppose:
	
	0 <= coordinate <= 1,000,000
	
	Can you avoid comparison-based sorting?
	
	Yes. Because the coordinate range is bounded, events can be bucketed by coordinate:
	
	events[coordinate]
	
	Then scan coordinates from left to right.
	
	Complexity becomes roughly:
	
	O(n + C)
	
	where C is the size of the coordinate space.
	
	This trades potentially significant memory usage for eliminating:
	
	O(n log n)
	
	sorting. 


*/