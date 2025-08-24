package LinkedIn;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class NestedListWeightSum_II_364_medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

    /* 	This solution uses BFS + cumulative levelSum trick is 
     * 	mathematically equivalent to val × (maxDepth - depth + 1)
     
		Use BFS (level-order traversal) and accumulate the sum 
		bottom-up to avoid calculating maxDepth separately.
		
		The BFS bottom-up approach is the best fit. It avoids 
		computing maxDepth in advance and demonstrates your ability
		to reason about weighting in a smart way.
		
		Algorithm (Using BFS - Best Approach)
		- We use level-order traversal:
		- Use a queue to traverse level by level.
		- At each level:
			- Keep a levelSum of all integers found at that level.
			- Add levelSum to a totalSum after each level — but in a way where the deepest level contributes only once and upper levels accumulate more.
		
		This way, upper levels get added multiple times, simulating the inverse depth weighting.
		
		✅ The Intuition (how formula is satisfied)
		- Each level’s numbers are added to levelSum.
		- Then, at every deeper level, levelSum carries all shallower numbers forward.
		- This means:
			-- A number at depth d gets counted once for each level from d to maxDepth.
			-- That is exactly (maxDepth – d + 1) times — which is the required weight.
		
		So the BFS accumulation is just a clever way of reusing counts instead 
		of explicitly multiplying by weights.
		
		----
		
		** Final Explanation (Interview-Ready) **

		When interviewer asks “How are you multiplying by weights?”, you can say:
		- Instead of computing maxDepth and multiplying each 
		  number by (maxDepth - depth + 1),
		  
		- I use a cumulative level sum. Each shallow number 
		  stays in the running total for more levels, so it 
		  contributes multiple times — exactly once per level 
		  from its depth down to maxDepth.
		  
		- This naturally gives each number the correct weight, 
		  without an explicit multiplication step.
		----		
  		Time: O(N) — each integer/list processed once.
		Space: O(N) — queue may hold all elements at a given level.
    */
	// This is the interface that allows for creating nested lists.
	public interface NestedInteger {
		/** find implementation at the bottom of this file */
	    boolean isInteger();
	    Integer getInteger();
	    public void setInteger(int value);
	    public void add(NestedInteger ni);
	    List<NestedInteger> getList();
	}

	public int depthSumInverse(List<NestedInteger> nestedList) {
        if (nestedList == null) return 0;

        // Add nestedList to a queue
        Queue<NestedInteger> queue = new LinkedList<>(nestedList);
        List<Integer> levelSums = new ArrayList<>();
        while (!queue.isEmpty()) {
            int size = queue.size();
            int curLevelSum = 0;

            for (int i = 0; i < size; i++) {
                NestedInteger current = queue.poll();
                if (current.isInteger()) {
                    curLevelSum += current.getInteger();
                } else {
                    queue.addAll(current.getList());
                }
            }
            // Push to levelSum list for each level
            levelSums.add(curLevelSum);
        }
        // Compute inverse depth sum
        int totalSum = 0;
        int weight = 1;
        for (int i = levelSums.size() - 1; i >= 0; i--) {
            totalSum += levelSums.get(i) * weight;
            weight++; // increment weight by each level
        }
        return totalSum;
    }
	
	// Another approach
	public int depthSumInverse4(List<NestedInteger> nestedList) {
        if (nestedList == null) return 0;

        Queue<NestedInteger> queue = new LinkedList<>(nestedList);
        int totalSum = 0; // Final result
        int levelSum = 0; // Accumulative sum across levels

        while (!queue.isEmpty()) {
            int size = queue.size();
            int currentLevelSum = 0;

            for (int i = 0; i < size; i++) {
                NestedInteger current = queue.poll();
                if (current.isInteger()) {
                    currentLevelSum += current.getInteger();
                } else {
                    queue.addAll(current.getList());
                }
            }

            // Accumulate this level’s sum into levelSum
            levelSum += currentLevelSum;

            // Add levelSum to totalSum
            totalSum += levelSum;
        }
        return totalSum;
    }
    
    
    /** Follow-up: What if the list is too big size and can't be stored in memory. */
    /*
    🔹 Key Insight
	    - Our original BFS solution requires a Queue<NestedInteger> that 
	      holds all elements of the current level. If the nested list is 
	      huge, this might not fit in memory.
	    - To reduce memory use, we can stream through the nested structure 
	      level by level instead of storing everything.
	    - This means:
	    -- We only need the running sums (levelSum and totalSum),
	    -- And we need an iterator (or generator) that gives us the next level on demand.
     */
    /*
    🔹 Step-by-Step Algorithm (Memory-Efficient)
	    1. Initialize: totalSum = 0, levelSum = 0.
	    2. Process the list one level at a time:
		   - Instead of enqueuing all items, iterate through 
		     the list with an iterator/stream.
		   - For each integer, add to currentLevelSum.
		   - For each sublist, don’t fully expand — just pass its 
		     iterator to the next round.
	    3. Accumulate sums as before:
			- Add currentLevelSum to levelSum.
	    	- Add levelSum to totalSum.
	    4. Repeat until there are no more nested lists to expand.
	
	    This avoids holding the entire list in memory. At any time, 
	    we only store one level’s worth of data.
	    
	    ✅ Algorithm Update (Streaming Approach)
		1. Instead of List<NestedInteger> nestedList in the method 
		   signature, we accept either:
			- an Iterator<NestedInteger> (pull model), or
			- a Stream<NestedInteger> / generator (push model).
		
		2. We process elements level by level (like BFS) without 
		   keeping the full nested structure.
			- Maintain a queue of elements for current level.
			- Read next level on-demand from the iterator/stream.
			- Keep track of sums at each level.
		
		3. Once traversal is done, compute the inverse depth sum 
		   using stored level sums.
	    
    */

    /* If the list is too large to fit in memory, then passing 
     * List<NestedInteger> directly as a parameter won’t be possible. 
     * Instead of materializing the full structure in memory, we should treat 
     * the input as a stream of NestedInteger objects, consuming one item at a time.
     */ 
     /* ✅ Interview Script to Say:
		“If memory is limited, I wouldn’t accept a full List<NestedInteger> 
		in the method. Instead, I’d accept an Iterator<NestedInteger> so I 
		can process the input as a stream. This way, I only hold a small part 
		of the input in memory at any time, just like how streaming parsers work. 
		The algorithm itself doesn’t change much — I still do BFS level by level, 
		but I consume from the stream instead of a pre-built list. This makes the 
		solution scalable to very large inputs.”
     * */
    
    // Memory-efficient BFS using iterators
	/* When memory heavy, fetch data from stream and then pass to the method to calculate sum.
	 * Iterator<NestedInteger> nestedStream = streamFromJsonFile(...); // param-update
	 * int result = depthSumInverse(nestedStream); // param-update
	 * Now, we only read one element at a time.
	 */
	/* 
     * Time: O(N) — each integer/list visited once.
     * Space: O(W) — only the current level’s elements in memory.
     * 	Much smaller than storing the entire nested structure.
     */
	public int depthSumInverse(Iterator<NestedInteger> nestedStream) { // param-update
	    Queue<NestedInteger> queue = new LinkedList<>();
	    // param-update: Load only what's available from stream
	    while (nestedStream.hasNext()) {
	        queue.offer(nestedStream.next());
	    }
	    List<Integer> levelSums = new ArrayList<>();
	    while (!queue.isEmpty()) {
	        int size = queue.size();
	        int levelSum = 0;
	        for (int i = 0; i < size; i++) {
	            NestedInteger ni = queue.poll();
	            if (ni.isInteger()) {
	                levelSum += ni.getInteger();
	            } else {
	                for (NestedInteger child : ni.getList()) {
	                    queue.offer(child); 
	                }
	            }
	        }
	        levelSums.add(levelSum);
	    }
	    // Compute inverse depth sum
	    int total = 0;
	    int weight = 1;
	    for (int i = levelSums.size() - 1; i >= 0; i--) {
	        total += levelSums.get(i) * weight;
	        weight++;
	    }
	    return total;
	}

    
    /*
	Interview Explanation Script
	
	The base problem is to compute the weighted sum where weight = maxDepth – depth + 1.
	
	A clean way to do this is BFS level by level. At each level, 
	I add the integers I see into a running levelSum, then add 
	levelSum into totalSum. This automatically applies the inverse 
	weights: shallow numbers get added more times, deep numbers fewer 
	times. Time is O(N), space is O(W).
	
	Now for the follow-up: what if the list is too large to fit in memory? 
	The naive BFS solution keeps an entire queue of elements, which could 
	blow up memory. To fix this, I switch from storing all elements to 
	storing only iterators. Instead of enqueuing every sublist fully, 
	I iterate through the current level, collect only the next level’s elements, 
	and enqueue just their iterators.
	
	That way, at any point I only hold one level’s worth of data, not the 
	whole nested list. The logic is otherwise identical:
	- For each level, sum integers into currentLevelSum.
	- Add it into levelSum.
	- Add levelSum into totalSum.
	
	This reduces memory from O(N) to O(W), where W is just the width 
	of the current level. Time complexity stays O(N).
	
	The trade-off is that we need an API that can give us iterators 
	over sublists, instead of requiring all of them in memory at once. 
	If the nested list came from a file or database stream, this works well. 
	The only limitation is if the API forces us to materialize everything 
	in memory first, in which case this optimization isn’t possible.
     * */

    
    // NestedIntegerImpl - concrete class
    static class NestedIntegerImpl implements NestedInteger {
        private Integer value;               // holds integer if present
        private List<NestedInteger> list;    // holds list if present

        public NestedIntegerImpl() {
            this.list = new ArrayList<>();
        }
        public NestedIntegerImpl(int value) {
            this.value = value;
        }
        public boolean isInteger() {
            return value != null;
        }
        public Integer getInteger() {
            return value;
        }
        public void setInteger(int value) {
            this.value = value;
            this.list = null; // reset list
        }
        public void add(NestedInteger ni) {
            if (list == null) {
                list = new ArrayList<>();
                if (value != null) { // if was integer, move it into list
                    list.add(new NestedIntegerImpl(value));
                    value = null;
                }
            }
            list.add(ni);
        }
        public List<NestedInteger> getList() {
            return list == null ? new ArrayList<>() : list;
        }
        public String toString() {
            return isInteger() ? value.toString() : list.toString();
        }
    }


}
