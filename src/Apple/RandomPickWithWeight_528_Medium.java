package Apple;


import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class RandomPickWithWeight_528_Medium {

	public static void main(String[] args) {
		int idx = -99;
		int[] w = {1, 3};
		Solution solution = new Solution(w);
		idx = solution.pickIndex(); // return 1. It's returning the second element (index = 1) that has probability of 3/4.
		System.out.println("1. idx="+idx);
		
		idx = solution.pickIndex(); // return 1
		System.out.println("2. idx="+idx);
		
		idx = solution.pickIndex(); // return 1
		System.out.println("3. idx="+idx);
		
		idx = solution.pickIndex(); // return 1
		System.out.println("4. idx="+idx);
		
		idx = solution.pickIndex(); // return 0. It's returning the first element (index = 0) that has probability of 1/4.
		System.out.println("5. idx="+idx);
	}

	/*
	This solution uses Prefix Sum + Binary Search
	
	Why Prefix Sum? 
	I use prefix sums to turn each weight into a contiguous range on 
	[1, total]. Then picking a random number and binary-searching the 
	first prefix ≥ that number tells me which range—and thus which 
	index—I hit. This gives O(n) preprocessing and O(log n) per pick, 
	instead of O(n) every pick.
	
	 In an interview
		If the statement is ambiguous, the best move is to ask:
		
		“Do you want all indices equally likely, or should the probabilities follow the given weights?”
		
		Then choose:
		
		Uniform → rand.nextInt(n). - then we don't need to do all this work just get 
						the random index and return the value from array for that index
		Weighted → prefix-sum solution.
	
	
	Interview script:
	1. “We need to pick index i with probability w[i] / sum(w). 
		I’ll convert this into picking a random point on a number line and 
		see which index owns that point.”
	
	2. I’ll build a prefix-sum array of the weights. If prefix = [2, 5, 9], that means:
		index 0 covers range (0,2]
		index 1 covers (2,5]
		index 2 covers (5,9]
		Then I pick a random integer from 1 to 9 and find which range it falls into.”
	
	3. Binary search: 
	To find the index, I’ll binary search the prefix array for the first 
	element ≥ random value. That gives O(log n) per query after an O(n) preprocessing.
	
	 
	Complexity
	    Constructor:
	        Time: O(n)
	        Space: O(n)
	    pickIndex():
	        Time: O(log n) (binary search)
	        Extra space: O(1)
	*/
	static class Solution {
	    int[] prefix;
	    int total;
	    Random rand; // Use Random once as a field, not per call.

	    public Solution(int[] w) {
	        int n = w.length;
	        prefix = new int[n];
	        int sum = 0;
	        for(int i=0; i<n; i++) {
	            sum += w[i];
	            prefix[i] = sum;
	        }
	
	        total = sum;
	        rand = new Random();
	    }
	    
	    public int pickIndex() {
	        // Random integer in [1, total]
	    	//int target = rand.nextInt(total) + 1; // works
	        int target = rand.nextInt(total);
	
	        // Binary search for first prefix[i] >= target
	        int low = 0;
	        int high = prefix.length - 1;
	        
	        while(low < high) {
	            
	        	int mid = low + (high - low) / 2;
	            
	            // if(prefix[mid] < target) { // works
	            if(prefix[mid] <= target) {
	                low = mid + 1;
	            } else {
	                high = mid;
	            }
	        }
	
	        return low;
	    }
	}

	
	/** 
	ALIAS METHOD (LeetCode 528 - O(1) PICK)
	========================================
	ALIAS METHOD (LeetCode 528 - O(1) PICK)
	========================================

	PROBLEM:
	Pick an index with probability proportional to weights.

	Example:
	    w = [1, 3, 2]

	Expected probability:
	    index 0 → 1/6
	    index 1 → 3/6
	    index 2 → 2/6

	----------------------------------------
	WHY NOT PREFIX SUM?
	----------------------------------------
	Prefix sum + binary search:
	    - pickIndex() = O(log n)

	Goal:
	    Optimize pickIndex() to O(1)

	----------------------------------------
	CORE IDEA
	----------------------------------------
	Instead of having variable-sized probability ranges,
	we convert them into EQUAL-SIZED BUCKETS.

	WHY?
	    Equal buckets allow:
	        - direct random index selection → O(1)
	        - no binary search needed

	----------------------------------------
	STEP 1: FORCE EQUAL BUCKETS
	----------------------------------------
	Pick index uniformly:
	    i = random(0 to n-1)

	Each index gets probability = 1/n

	Problem:
	    This breaks original weighted distribution.

	----------------------------------------
	STEP 2: FIX LOCALLY USING prob[] + alias[]
	----------------------------------------
	Each bucket stores:
	    prob[i]  → probability of choosing i
	    alias[i] → fallback index

	----------------------------------------
	STEP 3: SCALE PROBABILITIES
	----------------------------------------
	scaled[i] = (w[i] * n) / sum

	Example:
	    w = [1, 3, 2], n = 3, sum = 6

	    scaled = [0.5, 1.5, 1.0]

	WHY?
	    Each bucket has size = 1
	    We redistribute probability into these equal buckets

	----------------------------------------
	STEP 4: CLASSIFY
	----------------------------------------
	small (<1) → needs probability
	large (>1) → has extra

	Example:
	    small = [0]
	    large = [1, 2]

	----------------------------------------
	STEP 5: BALANCE (CORE STEP)
	----------------------------------------
	Take:
	    small index s
	    large index l

	Fill bucket s:
	    prob[s] = scaled[s]
	    alias[s] = l

	WHY?
	    Bucket size must be 1
	    s contributes scaled[s]
	    remaining comes from l

	Update:
	    scaled[l] -= (1 - scaled[s])

	Repeat until balanced.

	----------------------------------------
	STEP 6: REMAINING
	----------------------------------------
	Any index left:
	    prob[i] = 1.0
	    (always picks itself)

	----------------------------------------
	FINAL STRUCTURE
	----------------------------------------
	prob[i]  → probability threshold
	alias[i] → fallback index

	----------------------------------------
	PICK OPERATION (O(1))
	----------------------------------------
	1. Pick random index:
	       i = random(0 to n-1)

	2. Flip coin:
	       if random < prob[i]
	           return i
	       else
	           return alias[i]

	----------------------------------------
	WHY THIS WORKS
	----------------------------------------
	We simulate weighted probability by:
	    - uniform bucket selection (1/n)
	    - local adjustment inside bucket

	This ensures correct global distribution.

	----------------------------------------
	TIME COMPLEXITY
	----------------------------------------
	Build: O(n)
	pickIndex(): O(1)
	Space: O(n)

	----------------------------------------
	WHEN TO USE
	----------------------------------------
	Use when:
	    - pickIndex() is called frequently
	    - weights are static

	Avoid when:
	    - weights change often

	----------------------------------------
	INTERVIEW SCRIPT
	----------------------------------------
	"I first solved this using prefix sum and binary search with O(log n) pick time.
	To optimize further, I used the Alias Method, which converts the distribution into equal-sized buckets.
	Each bucket stores a probability and an alias index.
	At runtime, I pick a random index and use a biased coin flip to decide between the index and its alias.
	This gives O(1) weighted random selection."
	*/
	

	static class Solution_AliasMethod {
	    double[] prob;   // probability threshold
	    int[] alias;     // alias index
	    int n;
	    Random rand;

	    public Solution_AliasMethod(int[] w) {
	        n = w.length;
	        prob = new double[n];
	        alias = new int[n];
	        rand = new Random();

	        buildAliasTable(w);
	    }

	    private void buildAliasTable(int[] w) {
	        double sum = 0;
	        for (int weight : w) {
	            sum += weight;
	        }

	        // Step 1: scale probabilities by n
	        double[] scaled = new double[n];
	        for (int i = 0; i < n; i++) {
	            scaled[i] = (w[i] * 1.0 * n) / sum;
	        }

	        // Step 2: classify into small and large
	        Queue<Integer> small = new LinkedList<>();
	        Queue<Integer> large = new LinkedList<>();

	        for (int i = 0; i < n; i++) {
	            if (scaled[i] < 1.0) {
	                small.offer(i);
	            } else {
	                large.offer(i);
	            }
	        }

	        // Step 3: balance
	        while (!small.isEmpty() && !large.isEmpty()) {
	            int s = small.poll();
	            int l = large.poll();

	            prob[s] = scaled[s];
	            alias[s] = l;

	            scaled[l] = scaled[l] - (1.0 - scaled[s]);

	            if (scaled[l] < 1.0) {
	                small.offer(l);
	            } else {
	                large.offer(l);
	            }
	        }

	        // Step 4: remaining
	        while (!large.isEmpty()) {
	            prob[large.poll()] = 1.0;
	        }

	        while (!small.isEmpty()) {
	            prob[small.poll()] = 1.0;
	        }
	    }

	    public int pickIndex() {
	        int i = rand.nextInt(n);   // O(1) uniform pick
	        if (rand.nextDouble() < prob[i]) {
	            return i;
	        } else {
	            return alias[i];
	        }
	    }
	}

}
