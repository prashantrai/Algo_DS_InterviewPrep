package Apple;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class LoadBalancer_SimilarToRandomPickWeight_528_Medium {
    
	
	public static void main(String[] args) {

        // =========================
        // Test 1: Equal Probability
        // =========================
        System.out.println("Test 1: Equal Probability");

        String[] ips1 = {"10.0.0.1", "10.0.0.2", "10.0.0.3"};
        LoadBalancer_Array lb1 = new LoadBalancer_Array(ips1);

        Map<String, Integer> count1 = new HashMap<>();

        for (int i = 0; i < 10000; i++) {
            String ip = lb1.getIP();
            count1.put(ip, count1.getOrDefault(ip, 0) + 1);
        }

        System.out.println("Distribution (should be ~equal): " + count1);


        // =========================
        // Test 2: Weighted Probability
        // =========================
        System.out.println("\nTest 2: Weighted Probability");

        String[] ips2 = {"10.0.0.1", "10.0.0.2", "10.0.0.3"};
        int[] weights2 = {1, 3, 2};

        LoadBalancer_Array lb2 = new LoadBalancer_Array(ips2, weights2);

        Map<String, Integer> count2 = new HashMap<>();

        for (int i = 0; i < 10000; i++) {
            String ip = lb2.getIP();
            count2.put(ip, count2.getOrDefault(ip, 0) + 1);
        }

        System.out.println("Distribution (expected ~1:3:2): " + count2);


        // =========================
        // Test 3: Single IP
        // =========================
        System.out.println("\nTest 3: Single IP");

        String[] ips3 = {"10.0.0.1"};
        LoadBalancer_Array lb3 = new LoadBalancer_Array(ips3);

        for (int i = 0; i < 5; i++) {
            System.out.println(lb3.getIP());
        }


        // =========================
        // Test 4: Large Weight Skew
        // =========================
        System.out.println("\nTest 4: Large Weights");

        String[] ips4 = {"A", "B"};
        int[] weights4 = {1000, 1};

        LoadBalancer_Array lb4 = new LoadBalancer_Array(ips4, weights4);

        Map<String, Integer> count4 = new HashMap<>();

        for (int i = 0; i < 10000; i++) {
            String ip = lb4.getIP();
            count4.put(ip, count4.getOrDefault(ip, 0) + 1);
        }

        System.out.println("Distribution (A >> B): " + count4);


        // =========================
        // Test 5: Invalid Inputs
        // =========================
        System.out.println("\nTest 5: Invalid Inputs");

        try {
            new LoadBalancer_Array(new String[]{});
        } catch (Exception e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        try {
            new LoadBalancer_Array(new String[]{"A", "B"}, new int[]{1});
        } catch (Exception e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        try {
            new LoadBalancer_Array(new String[]{"A", "B"}, new int[]{1, -2});
        } catch (Exception e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }


        // =========================
        // Test 6: Small Run Sanity
        // =========================
        System.out.println("\nTest 6: Small Run");

        LoadBalancer_Array lb6 = new LoadBalancer_Array(
                new String[]{"X", "Y"},
                new int[]{1, 1}
        );

        for (int i = 0; i < 10; i++) {
            System.out.print(lb6.getIP() + " ");
        }
        System.out.println();
    }
	
	
	
	/* Problem: Load Balancer IP Selection
		
		You are designing a load balancer that distributes incoming 
		requests across a pool of backend servers.
		Each server is identified by its IP address and may optionally 
		have a weight indicating its capacity.
		
		Requirements:
		The load balancer should support two modes:
		
		Mode 1: Equal Probability (Uniform Distribution)
		Every server should have an equal chance of being selected.
		
		Example:
		IPs = ["10.0.0.1", "10.0.0.2", "10.0.0.3"]
		Each IP should be selected with probability = 1/3
		
		Mode 2: Weighted Probability
		Each server has a weight representing its capacity.
		Probability of selection is proportional to its weight.
		
		Example:
		IPs = ["10.0.0.1", "10.0.0.2", "10.0.0.3"]
		Weights = [1, 3, 2]
		
		Probabilities:
		
		10.0.0.1 → 1/6
		
		10.0.0.2 → 3/6
		
		10.0.0.3 → 2/6
	 * */
	
	
    /* Interview Script: 
    For equal probability, I simply pick a random index in O(1).
	For weighted probability, I convert weights into a prefix sum 
	array to form a cumulative distribution.
	
	Then I generate a random number between 1 and total weight and 
	use binary search to map it to the correct server.
	
	This ensures selection probability proportional to weights while 
	keeping getIP efficient at O(log n)."
	
	
   Complexity
	    Intilization (prefix-sum):
	        Time: O(n)
	        Space: O(n)
	    getIP():
	        Time: O(log n) (binary search)
	        Extra space: O(1)
     * */

    
    // Using Arrays
    private static class LoadBalancer_Array {
        private String[] ips;
        private int[] prefixSum;
        private int totalWeight;
        private Random random;

        // Constructor for equal probability
        public LoadBalancer_Array(String[] ips) {
            if (ips == null || ips.length == 0) {
                throw new IllegalArgumentException("IP array cannot be empty");
            }
            this.ips = ips;
            this.random = new Random();
        }

        // Constructor for weighted probability
        public LoadBalancer_Array(String[] ips, int[] weights) {
            if (ips == null || ips.length == 0 || weights == null || weights.length == 0) {
                throw new IllegalArgumentException("IPs and weights cannot be empty");
            }
            if (ips.length != weights.length) {
                throw new IllegalArgumentException("IPs and weights must be same size");
            }

            this.ips = ips;
            this.random = new Random();

            int n = weights.length;
            this.prefixSum = new int[n];

            if (weights[0] <= 0) {
                throw new IllegalArgumentException("Weights must be positive");
            }
            prefixSum[0] = weights[0];

            for (int i = 1; i < n; i++) {
                if (weights[i] <= 0) {
                    throw new IllegalArgumentException("Weights must be positive");
                }
                prefixSum[i] = prefixSum[i - 1] + weights[i];
            }

            this.totalWeight = prefixSum[n - 1];
        }

        public String getIP() {
            // Equal probability
            if (prefixSum == null) {
                int index = random.nextInt(ips.length);
                return ips[index];
            }

            // Weighted probability
            int target = random.nextInt(totalWeight) + 1;
            int index = binarySearch(target);
            return ips[index];
        }

        private int binarySearch(int target) {
            int left = 0, right = prefixSum.length - 1;

            while (left < right) {
                int mid = left + (right - left) / 2;

                if (prefixSum[mid] < target) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            return left;
        }
    }
    
    /* Thread-Safe Version
    We need thread safety because multiple concurrent requests call getIP(). 
    We must ensure correctness, visibility, and high throughput without 
    introducing contention bottlenecks.
    
    I made the load balancer thread-safe by making all shared state immutable, 
    using defensive copies, and replacing Random with ThreadLocalRandom to avoid 
    contention. This ensures correctness and high throughput under concurrent 
    traffic without locking.
    
    */

    private static class LoadBalancer_Array_ThreadSafe {

        private final String[] ips;
        private final int[] prefixSum;   // null → equal probability
        private final int totalWeight;

        // Constructor for equal probability
        public LoadBalancer_Array_ThreadSafe(String[] ips) {
            if (ips == null || ips.length == 0) {
                throw new IllegalArgumentException("IP array cannot be empty");
            }
            this.ips = ips.clone(); // defensive copy
            this.prefixSum = null;
            this.totalWeight = 0;
        }

        // Constructor for weighted probability
        public LoadBalancer_Array_ThreadSafe(String[] ips, int[] weights) {
            if (ips == null || ips.length == 0 || weights == null || weights.length == 0) {
                throw new IllegalArgumentException("IPs and weights cannot be empty");
            }
            if (ips.length != weights.length) {
                throw new IllegalArgumentException("IPs and weights must be same size");
            }

            this.ips = ips.clone(); // defensive copy

            int n = weights.length;
            this.prefixSum = new int[n];

            if (weights[0] <= 0) {
                throw new IllegalArgumentException("Weights must be positive");
            }

            prefixSum[0] = weights[0];

            for (int i = 1; i < n; i++) {
                if (weights[i] <= 0) {
                    throw new IllegalArgumentException("Weights must be positive");
                }
                prefixSum[i] = prefixSum[i - 1] + weights[i];
            }

            this.totalWeight = prefixSum[n - 1];
        }

        public String getIP() {
            // Equal probability
            if (prefixSum == null) {
                int index = ThreadLocalRandom.current().nextInt(ips.length);
                return ips[index];
            }

            // Weighted probability
            int target = ThreadLocalRandom.current().nextInt(totalWeight) + 1;
            int index = binarySearch(target);
            return ips[index];
        }

        private int binarySearch(int target) {
            int left = 0, right = prefixSum.length - 1;

            while (left < right) {
                int mid = left + (right - left) / 2;

                if (prefixSum[mid] < target) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            return left;
        }
    }
    
    
    
    // Alias Method: O(1) IP selection, i.e. no binary search
    
    /**
	========================================
	ALIAS METHOD (O(1) WEIGHTED RANDOM PICK)
	========================================
	PROBLEM:
	We need to pick an index based on weights.
	Example:
	    A = 1, B = 3, C = 2
	    Total = 6
	
	Expected probabilities:
	    A = 1/6, B = 3/6, C = 2/6
	
	----------------------------------------
	WHY NOT PREFIX SUM?
	----------------------------------------
	Prefix sum + binary search:
	    - Build cumulative array → O(n)
	    - Pick → O(log n)
	
	Goal:
	    Improve pick time to O(1)
	
	----------------------------------------
	CORE IDEA (MOST IMPORTANT)
	----------------------------------------
	Instead of variable-sized probability ranges,
	we convert everything into EQUAL-SIZED BUCKETS.
	
	Step 1:
	Pick an index uniformly:
	    i = random(0 to n-1)
	
	Now each index has probability = 1/n
	This gives O(1) selection (no binary search)
	BUT this breaks original weight distribution.
	
	----------------------------------------
	FIX: ADJUST PROBABILITY INSIDE EACH BUCKET
	----------------------------------------
	Each bucket will store:
	    1. prob[i]   → probability of choosing i
	    2. alias[i]  → fallback index
	
	----------------------------------------
	STEP-BY-STEP WITH EXAMPLE
	----------------------------------------
	
	Input:
	    weights = [1, 3, 2]
	    n = 3
	    sum = 6
	
	Step 1: Normalize & scale
	----------------------------------------
	Scale weights by n:
	    scaled[i] = (weights[i] * n) / sum
	
	    A = (1 * 3) / 6 = 0.5
	    B = (3 * 3) / 6 = 1.5
	    C = (2 * 3) / 6 = 1.0
	
	WHY?
	    Because each bucket has size = 1
	    We want to distribute probabilities into these equal buckets
	
	----------------------------------------
	Step 2: Classify into small and large
	----------------------------------------
	small (<1): needs more probability
	large (>1): has extra probability
	
	    small = [A (0.5)]
	    large = [B (1.5), C (1.0)]
	
	----------------------------------------
	Step 3: Balance (CORE STEP)
	----------------------------------------
	Take one small and one large:
	    A needs 0.5
	    B has extra 0.5
	
	Fill A using B:
	    prob[A] = 0.5
	    alias[A] = B
	
	Update B:
	    B = 1.5 - 0.5 = 1.0
	
	Now B is balanced.
	
	----------------------------------------
	Step 4: Remaining elements
	----------------------------------------
	Any element with value = 1:
	    prob[i] = 1 (always pick itself)
	
	----------------------------------------
	FINAL STRUCTURE
	----------------------------------------
	prob  = [0.5, 1.0, 1.0]
	alias = [B,   -,   - ]
	
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
	Example for A:
	Probability of picking bucket A = 1/3
	Inside bucket A:
	    50% → A
	    50% → B
	So:
	    A = (1/3) * (0.5) = 1/6 ✅
	
	Correct distribution achieved!
	
	----------------------------------------
	TIME COMPLEXITY
	----------------------------------------
	Preprocessing: O(n)
	Pick:           O(1)
	Space:          O(n)
	
	----------------------------------------
	WHEN TO USE
	----------------------------------------
	Use when:
	    - get() is called very frequently
	    - weights are static
	
	Avoid when:
	    - weights change frequently
	
	----------------------------------------
	INTERVIEW SCRIPT
	----------------------------------------
	"I convert the weighted distribution into equal-sized buckets so I can pick an index uniformly in O(1).
	Then for each bucket, I store a probability and an alias index.
	At runtime, I pick a random index and use a biased coin flip to decide between the index and its alias.
	This ensures correct weighted probability while keeping selection O(1)."
	*/
     
    
    private static class LoadBalancer_AliasMethod {
        private String[] ips;
        private double[] prob;
        private int[] alias;
        private int n;
        private Random random = new Random();

        public LoadBalancer_AliasMethod(String[] ips, int[] weights) {
            this.ips = ips;
            this.n = ips.length;

            prob = new double[n];
            alias = new int[n];

            build(weights);
        }

        private void build(int[] weights) {
            double sum = 0;
            for (int w : weights) sum += w;

            double[] scaled = new double[n];

            // Step 1: scale probabilities
            for (int i = 0; i < n; i++) {
                scaled[i] = weights[i] * n / sum;
            }

            Queue<Integer> small = new LinkedList<>();
            Queue<Integer> large = new LinkedList<>();

            // Step 2: classify
            for (int i = 0; i < n; i++) {
                if (scaled[i] < 1) small.offer(i);
                else large.offer(i);
            }

            // Step 3: balance
            while (!small.isEmpty() && !large.isEmpty()) {
                int s = small.poll();
                int l = large.poll();

                prob[s] = scaled[s];
                alias[s] = l;

                scaled[l] -= (1 - scaled[s]);

                if (scaled[l] < 1) small.offer(l);
                else large.offer(l);
            }

            // Step 4: remaining
            while (!small.isEmpty()) prob[small.poll()] = 1;
            while (!large.isEmpty()) prob[large.poll()] = 1;
        }

        public String getIP() {
            int i = random.nextInt(n);

            if (random.nextDouble() < prob[i]) {
                return ips[i];
            } else {
                return ips[alias[i]];
            }
        }
    }
    

    
    /** ------ Using List --------- */
    
    static void main_List(String[] args) {

        // =========================
        // Test 1: Equal Probability
        // =========================
        System.out.println("Test 1: Equal Probability");

        List<String> ips1 = Arrays.asList("10.0.0.1", "10.0.0.2", "10.0.0.3");
        LoadBalancer_WithList lb1 = new LoadBalancer_WithList(ips1);

        Map<String, Integer> count1 = new HashMap<>();

        for (int i = 0; i < 10000; i++) {
            String ip = lb1.getIP();
            count1.put(ip, count1.getOrDefault(ip, 0) + 1);
        }

        System.out.println("Distribution (should be ~equal): " + count1);


        // =========================
        // Test 2: Weighted Probability
        // =========================
        System.out.println("\nTest 2: Weighted Probability");

        List<String> ips2 = Arrays.asList("10.0.0.1", "10.0.0.2", "10.0.0.3");
        List<Integer> weights2 = Arrays.asList(1, 3, 2);

        LoadBalancer_WithList lb2 = new LoadBalancer_WithList(ips2, weights2);

        Map<String, Integer> count2 = new HashMap<>();

        for (int i = 0; i < 10000; i++) {
            String ip = lb2.getIP();
            count2.put(ip, count2.getOrDefault(ip, 0) + 1);
        }

        System.out.println("Distribution (expected ~1:3:2): " + count2);


        // =========================
        // Test 3: Single IP (Edge Case)
        // =========================
        System.out.println("\nTest 3: Single IP");

        List<String> ips3 = Arrays.asList("10.0.0.1");
        LoadBalancer_WithList lb3 = new LoadBalancer_WithList(ips3);

        for (int i = 0; i < 5; i++) {
            System.out.println(lb3.getIP()); // always same
        }


        // =========================
        // Test 4: Large Weights
        // =========================
        System.out.println("\nTest 4: Large Weights");

        List<String> ips4 = Arrays.asList("A", "B");
        List<Integer> weights4 = Arrays.asList(1000, 1);

        LoadBalancer_WithList lb4 = new LoadBalancer_WithList(ips4, weights4);

        Map<String, Integer> count4 = new HashMap<>();

        for (int i = 0; i < 10000; i++) {
            String ip = lb4.getIP();
            count4.put(ip, count4.getOrDefault(ip, 0) + 1);
        }

        System.out.println("Distribution (A >> B): " + count4);


        // =========================
        // Test 5: Invalid Inputs (Edge Cases)
        // =========================
        System.out.println("\nTest 5: Invalid Inputs");

        try {
            new LoadBalancer_WithList(new ArrayList<>());
        } catch (Exception e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        try {
            new LoadBalancer_WithList(Arrays.asList("A", "B"), Arrays.asList(1));
        } catch (Exception e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        try {
            new LoadBalancer_WithList(Arrays.asList("A", "B"), Arrays.asList(1, -2));
        } catch (Exception e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }


        // =========================
        // Test 6: Deterministic sanity (small runs)
        // =========================
        System.out.println("\nTest 6: Small run sanity");

        LoadBalancer_WithList lb6 = new LoadBalancer_WithList(
                Arrays.asList("X", "Y"),
                Arrays.asList(1, 1)
        );

        for (int i = 0; i < 10; i++) {
            System.out.print(lb6.getIP() + " ");
        }
        System.out.println();
    }
    
    
	private static class LoadBalancer_WithList {
	    private List<String> ips;
	    private int[] prefixSum;
	    private int totalWeight;
	    private Random random;
	
	    // Constructor for equal probability
	    public LoadBalancer_WithList(List<String> ips) {
	        if (ips == null || ips.isEmpty()) {
	            throw new IllegalArgumentException("IP list cannot be empty");
	        }
	        this.ips = ips;
	        this.random = new Random();
	    }
	
	    // Constructor for weighted probability
	    public LoadBalancer_WithList(List<String> ips, List<Integer> weights) {
	        if (ips == null || ips.isEmpty() || weights == null || weights.isEmpty()) {
	            throw new IllegalArgumentException("IPs and weights cannot be empty");
	        }
	        if (ips.size() != weights.size()) {
	            throw new IllegalArgumentException("IPs and weights must be same size");
	        }
	
	        this.ips = ips;
	        this.random = new Random();
	
	        int n = weights.size();
	        this.prefixSum = new int[n];
	
	        prefixSum[0] = weights.get(0);
	        for (int i = 1; i < n; i++) {
	            if (weights.get(i) <= 0) {
	                throw new IllegalArgumentException("Weights must be positive");
	            }
	            prefixSum[i] = prefixSum[i - 1] + weights.get(i);
	        }
	
	        this.totalWeight = prefixSum[n - 1];
	    }
	
	    public String getIP() {
	        // Equal probability
	        if (prefixSum == null) {
	            int index = random.nextInt(ips.size());
	            return ips.get(index);
	        }
	
	        // Weighted probability
	        int target = random.nextInt(totalWeight) + 1;
	        int index = binarySearch(target);
	        return ips.get(index);
	    }
	
	    private int binarySearch(int target) {
	        int left = 0, right = prefixSum.length - 1;
	
	        while (left < right) {
	            int mid = left + (right - left) / 2;
	
	            if (prefixSum[mid] < target) {
	                left = mid + 1;
	            } else {
	                right = mid;
	            }
	        }
	        return left;
	    }
	}

}
