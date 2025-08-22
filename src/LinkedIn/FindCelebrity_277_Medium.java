package LinkedIn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class FindCelebrity_277_Medium {

	public static void main(String[] args) {

		boolean[][] celeb = {	{false, false, true},
								{true,  false, true},
								{false, false, false}
							};

		
		boolean[][] celeb_1 = {	{false, false, true},
								{false,  false, true},
								{true, false, false}
							};

		
		
		System.out.println (findCelebrity(celeb));
	}
	
	
	
	/*
	We want to minimize calls to knows(a, b).
	
	1) Single-pass candidate elimination (O(n))
	- Start with candidate = 0.
	- Scan i = 1 … n-1:
		- If knows(candidate, i) is true, then candidate cannot be 
		  the celebrity (a celeb knows nobody). Set candidate = i.
		- Otherwise, i cannot be the celebrity (since candidate does not know i), 
		  keep candidate as-is.
	- After this pass, candidate is the only possible celebrity.
	
	Why this works:
	Each comparison discards exactly one person from being the celebrity. 
	After n-1 checks, only one candidate remains.
	
	2) Verify the candidate (O(n))
	
	- For every i != candidate, check both:
		- knows(candidate, i) must be false (celebrity knows nobody).
		- knows(i, candidate) must be true (everyone knows the celebrity).
	- If any check fails → return -1.
	- Otherwise → return candidate.
	
	Total calls: up to (n-1) in step 1 + up to 2(n-1) in step 2 → O(n) calls.
	 */
	

	// 	Time: O(n) knows-queries.
	//	Space: O(1) extra space.
	 
	// Note: signature has been update to test here, actual leetcode question just 
	// passes the length and alrady provide a Relation class that takes care of knows() implementation
	//--Leetcode video:: https://www.youtube.com/watch?v=LZJBZEnoYLQ
	public static int findCelebrity(boolean[][] celeb) {
		
		if(celeb == null || celeb.length == 0) {
			return -1;
		}
		
		// 1) Candidate elimination in one pass
		int candidate = 0;
		for (int i=1; i<celeb.length; i++) {
			if(knows(candidate, i, celeb)) {
				// candidate knows i => candidate cannot be celebrity
				candidate = i;
			}
		}
		
		// 2) Verification pass
		for (int i=0; i<celeb.length; i++) {
			
			 /**
			  * candidate != i :: Candidate is not himself
			  * &&
			  * knows(candidate, i, celeb) :: candidate knows the person - then also candidate is not a celeb
			  * OR
			  * !knows(i, candidate, celeb) :: If the person doesn't know the candidate
			  */
			// Candidate must know nobody, and everyone must know candidate
			if(candidate != i && knows(candidate, i, celeb) || !knows(i, candidate, celeb)) {
				return -1;
			}
		
		}		
		return candidate;	
				
	}
	
	private static boolean knows(int a, int b, boolean[][] celeb) {
		
		return celeb[a][b];
	}
	
	
	/** Using Stack */
	// Time & Space: O(n)
	public static void findCelebrity2(boolean[][] celeb) {
		
		if(celeb == null || celeb.length == 0) {
			return;
		}
		
		Stack<Integer> stack = new Stack<Integer>();

		for(int i=0; i<celeb.length; i++) {
			stack.push(i);
		}
		
		while (stack.size() > 1) {
			int a = stack.pop();
			int b = stack.pop();
		
			if(knows(a, b, celeb)) {
				stack.push(b);
			} else {
				stack.push(a);
			}
		}
		
		if (stack.isEmpty()) {
			System.out.println("No celeb");
		} else {
			System.out.println("Celeb is "+ (stack.peek() + 1));
		}
		
	}
	/* 
	 // Leetcode version - solution that's implemented in Leetcode
	 public int findCelebrity_LC_Version(int n) {
        if(n <= 0) return -1;

        // find the candidate (possible celebrity)
        int candidate = 0;
        for(int i=1; i<n; i++) {
            if(knows(candidate, i)) {
                candidate = i;
            }
        }

        // verify if the candidate is celebrity
        for(int i=0; i<n; i++) {
            if(i == candidate) continue;
            // Candidate must know nobody, and everyone must know candidate
            if(knows(candidate, i) || !knows(i, candidate)) {
                return -1;
            }
        }
        return candidate;
    } 
    */
	
	/** Follow-up: Multiple celebrities */
	/* If the definition is strict, at most one celebrity exists. 
	 * But to find and return more than one celebrity, satisfying condition check is 
	 *  - known by everyone and 
	 * 	- knows nobody, 
	 * I simply verify every person. That’s O(n²) 
	 * knows calls, but very simple and robust.”
	 */
	
	// follow-up: multiple celebrities
    // Return all people who: (1) everyone knows them, (2) they know nobody.
    static List<Integer> findAllCelebrities(Party p){
        int n = p.n();
        List<Integer> ans = new ArrayList<>();
        for(int c=0;c<n;c++){
            boolean ok = true;                           // follow-up multiple celebrities
            for(int i=0;i<n && ok;i++){
                if(i==c) continue;
                if(p.knows(c,i) || !p.knows(i,c)) ok=false; // follow-up multiple celebrities
            }
            if(ok) ans.add(c);                           // follow-up multiple celebrities
        }
        return ans;
    }

	
	
	/**  Follow-up: Top-k “almost celebrities  */
	
	/*
	Idea (what to say)

	“Define ‘almost celebrity score’ = out-degree (# they know). 
	Compute out-degree for each person in O(n²), then return the 
	k with the smallest out-degree (tie-break by largest in-degree to prefer people widely known).”
	
	Short answer (what we’ll compute): 
	- out-degree out[i] = number of other people person i knows. 
		(Lower is better; celebrity has out = 0.)
	
	- in-degree in[i] = number of other people who know person i. 
		(Higher is better; celebrity has in = n-1.)
	
	- For top-k almost celebrities: sort people by (out ascending, 
		in descending) and return first k.
	
		- Rationale: we prefer people who know fewer others 
			(closer to celebrity). If tied, prefer the more widely-known 
			(higher in-degree).
	
	*/

	// Simple Party harness (same as earlier)
	class Party {
	    final boolean[][] g;
	    Party(boolean[][] g){ this.g = g; }
	    boolean knows(int a, int b){ return g[a][b]; }
	    int n(){ return g.length; }
	}

	class TopKAlmostCelebrities {

	    // follow-up top-k
	    // Return top k people who are 'closest' to being celebrities:
	    // primary sort: fewer people they know (out-degree ascending)  // follow-up top-k
	    // tiebreaker: more people know them (in-degree descending)   // follow-up top-k
	    List<Integer> topKAlmostCelebrities(Party p, int k){
	        int n = p.n();
	        if(n == 0 || k <= 0) return Collections.emptyList();   // follow-up top-k

	        int[] out = new int[n]; // out-degree: how many others each person knows (follow-up top-k)
	        int[] in  = new int[n]; // in-degree: how many others know the person (follow-up top-k)

	        // Compute in/out by checking all pairs (a,b). Exclude self-checks if desired.
	        for(int a = 0; a < n; a++){
	            for(int b = 0; b < n; b++){
	                if(a == b) continue; // ignore self-knowledge for these metrics (follow-up top-k)
	                if(p.knows(a,b)){
	                    out[a]++;        // follow-up top-k
	                    in[b]++;         // follow-up top-k
	                }
	            }
	        }

	        // Create index list and sort by our comparator (O(n log n) on keys)
	        List<Integer> ids = new ArrayList<>();
	        for(int i = 0; i < n; i++) ids.add(i);                 // follow-up top-k

	        ids.sort((i, j) -> {
	            if(out[i] != out[j]) 
	            	return Integer.compare(out[i], out[j]); // fewer known first // follow-up top-k
	            if(in[i] != in[j])   
	            	return Integer.compare(in[j], in[i]);   // prefer higher in-degree // follow-up top-k
	            
	            return Integer.compare(i, j); // stable tie-breaker by index (follow-up top-k)
	        });

	        if(k > n) k = n;
	        return ids.subList(0, k); // first k after sorting (follow-up top-k)
	    }

	    // --- Small test + dry run ---
	    /*public static void main(String[] args){
	        // Build example graph (n=5). We'll set relations so person 3 is the true celebrity.
	        // Matrix: g[a][b] == true means a knows b.
	        boolean[][] g = new boolean[5][5];
	        for(int i = 0; i < 5; i++) g[i][i] = true; // self-knowledge (ignored in counts)
	        // Edges:
	        // 0 -> {1,3}
	        g[0][1] = true; g[0][3] = true;
	        // 1 -> {3}
	        g[1][3] = true;
	        // 2 -> {0,3,4}
	        g[2][0] = true; g[2][3] = true; g[2][4] = true;
	        // 3 -> {} (knows nobody)
	        // 4 -> {3}
	        g[4][3] = true;

	        Party p = new Party(g);
	        // Get top-3 almost celebs
	        List<Integer> top3 = topKAlmostCelebrities(p, 3);
	        System.out.println("Top-3 almost celebs: " + top3);

	        // Also print degrees for clarity (dry-run helper)
	        printDegrees(p);
	    }

	    // follow-up top-k - helper: print in/out degrees
	    static void printDegrees(Party p){
	        int n = p.n();
	        int[] out = new int[n], in = new int[n];
	        for(int a=0;a<n;a++){
	            for(int b=0;b<n;b++){
	                if(a==b) continue;
	                if(p.knows(a,b)){ out[a]++; in[b]++; }
	            }
	        }
	        System.out.println("index | out (how many they know) | in (how many know them)");
	        for(int i=0;i<n;i++){
	            System.out.printf("  %d   |          %d            |         %d%n", i, out[i], in[i]);
	        }
	    }*/
	}

	
	
	
	
}
