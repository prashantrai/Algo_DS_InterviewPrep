package Google.extremelyHigh;

import java.util.HashMap;
import java.util.*;

public class LongestDictionaryWordFrom_9_Letters {

	public static void main(String[] args) {

		List<String> dictionary 
			= Arrays.asList("cat", "act", "cart", "trace", "crate", 
					"react", "cater", "create", "dog", "apple", 
					"stone", "tones", "notes", "ones", "set", "babel", "label", "bell");

		LongestDictionaryWordFrom_9_Letters solver 
				= new LongestDictionaryWordFrom_9_Letters(dictionary);

		runTest(solver, 
				"aaceertxy", // letters
				6, 			// expectedLen
				"create");	// expectedStr

		runTest(solver, "aenostxyz", 5, "stone / tones / notes");
		runTest(solver, "aacdrstxy", 4, "cart");
		runTest(solver, "aabbellxy", 5, "babel");
		runTest(solver, "bcfijkqxy", 0, "");
	}

	private static void runTest(LongestDictionaryWordFrom_9_Letters solver, 
			String letters, int expectedLen,
			String expectedStr) {

		String actual = solver.findLongest(letters);

		System.out.println("Letters          : " + letters);
		System.out.println("Expected length  : " + expectedLen);
		System.out.println("Expected         : " + expectedStr);
		System.out.println("Actual           : " + actual);
		System.out.println("Actual length    : " + actual.length());
		System.out.println("PASS             : " + (actual.length() == expectedLen));
		System.out.println();
	}
	
    
    /** Solution Starts */
    
	/*
	 * Interview Explanation Before Coding
	 * 
	 * This is how I would explain it before writing code:
	 * 
	 * Since preprocessing is allowed and the dictionary is much larger than each
	 * query, I don't want to scan the dictionary for every query.
	 * 
	 * Each query contains exactly 9 letters, so it has only 2^9 = 512 possible
	 * subsets. That is a very small constant.
	 * 
	 * I'll preprocess every dictionary word of length at most 9 into a canonical
	 * signature by sorting its characters. For example, care, race, and acre all
	 * map to acer.
	 * 
	 * Then for a query, I'll enumerate all subsets of the 9 positions, generate the
	 * sorted signature for each subset, and check whether that signature exists in
	 * the dictionary index.
	 * 
	 * I'll track the longest matching word.
	 * 
	 * This handles duplicates correctly because subset enumeration works on
	 * positions rather than unique characters.
	 * 
	 * The preprocessing cost is linear in the dictionary size up to sorting at most
	 * 9 characters per useful word, and each query checks at most 511 signatures,
	 * so query time is effectively constant with respect to the dictionary size.
	 */
	
	/* Step-by-Step Algorithm
		A. Preprocessing the Dictionary
			1. Create: Map<String, String> index
			
			2. For every dictionary word:
			
			   a. If word.length() > 9:
			          skip it
			
			      Reason:
			      a query has only 9 letters, so such a word can never be formed.
			
			   b. Generate its canonical signature:
			          convert word to char[]
			          sort characters
			          convert back to String
			
			      Example:
			          "race" -> "acer"
			          "care" -> "acer"
			
			   c. Store: signature -> word
			
			      Use putIfAbsent() because for the base problem
			      any word with the same exact signature is acceptable.
			      
		B. Process One Query
			1. Initialize:
			
			      best = ""
			      current = empty StringBuilder
			
			2. Start DFS:
			
			      generateSubsets(letters, 0, current, best)
		
		C. DFS / Subset Generation
			
		
	 	Duplicate Letters Work Automatically
	
		Example: letters = "aab......"
		
		The two as are different positions.
		
		DFS can independently choose:
		
		first a  -> take / skip
		second a -> take / skip
		
		Therefore it can generate:
		
		"a"
		"aa"
		"ab"
		"aab"
		
		A word requiring two as can only match if two a positions actually exist.
		
		So: No separate frequency validation is needed.
		
	*/
	/*	Complexity Analysis
			Let:
			D = number of dictionary words
			K = query length
			
			Here: K = 9
			
			Preprocessing Time
			
			For every dictionary word of length at most 9:
			generate signature by sorting at most 9 characters
			
			Cost per useful word: O(K log K)
			
			Therefore: O(D * K log K)
			
			Since: K = 9
			
			this becomes: O(D * 9 log 9)
			
			and because 9 is a fixed constant: Effectively O(D)
			
			
			Preprocessing Space
			
			The HashMap stores signatures for useful dictionary words.
			
			Worst case: O(D)
			
			Only words of length at most 9 need to be indexed.
			
			Query Time
			
			DFS generates: 2^K subsets.
			
			For each non-empty subset:
			build subset
			sort at most K characters
			HashMap lookup
			
			So: O(2^K * K log K)
			
			For this problem: O(2^9 * 9 log 9)
			
			There are only: 512 possible subsets.
			
			Therefore query time is effectively: O(1)
			
			with respect to dictionary size.
			
			
			Important interview wording:
			
			I would first state the exact complexity:
			O(2^9 * 9 log 9)
			
			Then say:
			Since 9 is fixed, this is effectively constant per query
			relative to the size of the dictionary.
			
			Query Auxiliary Space
			
			DFS recursion depth: O(K)
			
			current also contains at most: K characters.
			
			So auxiliary query space: O(K)
			
			For this problem: O(9)
			
			which is effectively constant.
			
			Final Complexity Summary
			Preprocessing Time:
			O(D * 9 log 9)
			≈ O(D)
			
			Preprocessing Space:
			O(D)
			
			Per Query Time:
			O(2^9 * 9 log 9)
			= at most 512 tiny subset checks
			≈ O(1) relative to dictionary size
			
			Per Query Auxiliary Space:
			O(9)
			≈ O(1)
		
	 * */
	
    
	final Map<String, String> index = new HashMap<>();
	final Map<String, String> queryCache = new HashMap<>();	// Follow-up 1: millions of query, handled efficiently
	
	public LongestDictionaryWordFrom_9_Letters(List<String> dict) {
		preprocessDict(dict);
	}
	
	public String findLongest(String letters) {
		if(letters == null || letters.length() != 9) {
			throw new IllegalArgumentException(
                    "Query must contain exactly 9 letters");
		}
		
		// Follow-up 1: millions of query, handled efficiently.. Start
		// Normalize the full query.
	    String querySig = signature(letters);

	    // If we already solved this exact multiset of letters,
	    // return the cached answer immediately.
	    if (queryCache.containsKey(querySig)) {
	        return queryCache.get(querySig);
	    }
	    // Follow-up 1: ends
	    
		
		String[] best = {""};
		
		StringBuilder current = new StringBuilder();
		
		generateSubsets(letters, 0, current, best);
		
		// Follow-up 1: millions of query, handled efficiently
		// Cache the final result for future identical queries.
	    queryCache.put(querySig, best[0]);
		
		return best[0];
	}
	
	/* generateSubsets: 
	 	For every character, we have exactly 2 choices:
		1. Skip it
		2. Take it
		
		That is the entire idea. 
		
		Suppose we simplify the input to: letters = "abc"
		
		We start with: i = 0
		current = ""
		
		At index 0, the character is: a
		We have two choices.
		First: skip a
		
		So we call: generateSubsets(letters, 1, current, best);
		
		Now: i = 1
		current = ""
		
		Notice we did not change current.
		
		The second choice is: take a
		So we do: current.append('a');
		
		Now: current = "a"
		and recurse: generateSubsets(letters, 1, current, best);
		
		So conceptually:

                    ""
                 character a

              skip          take
               /              \
             ""               "a"

		Then we repeat exactly the same thing for b, and similarly for c, 
		where each branch makes the same decision
		
                            ""
                         process a
                      /             \
                    ""               "a"
                 process b         process b
                /         \       /         \
              ""          "b"   "a"         "ab"
             /  \        /  \   /  \        /   \
           ""   "c"    "b" "bc" "a" "ac"  "ab" "abc"
		
		So the final subsets are: ["", "c", "b", "bc", "a", "ac", "ab", "abc"]
	 * */
	
	/*
	 * Time Complexity: O(2^n * n log n)
	 *
	 * - For n characters, we generate 2^n subsets because each character
	 *   has 2 choices: take it or skip it.
	 * - At each leaf/subset, signature() sorts up to n characters,
	 *   which costs O(n log n).
	 * - HashMap lookup is O(1) on average.
	 *
	 * Therefore:
	 *      O(2^n * n log n)
	 *
	 * Since n = 9 in this problem:
	 *      O(2^9 * 9 log 9), which is very small in practice.
	 *
	 *
	 * Space Complexity: O(n)
	 *
	 * - Recursion depth is at most n -> O(n).
	 * - current StringBuilder can contain at most n characters -> O(n).
	 * - signature() temporarily creates/sorts an array of at most n characters
	 *   -> O(n).
	 *
	 * We do NOT store all 2^n subsets at once, so space is not O(2^n).
	 *
	 * Therefore:
	 *      O(n)
	 */
	
	private void generateSubsets(String letters, int i, 
			StringBuilder current, String[] best) {

		// We have made a take/skip decision
        // for every query character.
		if(i == letters.length()) {
			if(current.length() == 0) return;
			
			String sig = signature(current.toString());
			String word = index.get(sig);
			
			if(word != null && word.length() > best[0].length()) {
				best[0] = word;
			}
			
			// Add below for Follow-up 3 — Deterministic Tie Breaking ... start
			/* if (word != null && (word.length() > best[0].length() ||
			        (word.length() == best[0].length() &&  // Follow-up 3
			                word.compareTo(best[0]) < 0))) { // // Follow-up 3

			    best[0] = word;
			} */
			// Follow-up 3 changes ...End
			
			
			return;
		}
		
		// Choice 1: skip current character.
		generateSubsets(letters, i+1, current, best);
		
		// Choice 2: take current character.
		current.append(letters.charAt(i));
		generateSubsets(letters, i+1, current, best);
		
		// Backtrack.
		current.deleteCharAt(current.length()-1);
	}

	/* Preprocess the dictionary once. sort its characters
		store in map: signature -> word
		Example:
		"race"  -> "acer"
		"care"  -> "acer"
		"crate" -> "acert"
	*/
	private void preprocessDict(List<String> dict) {
		for(String word : dict) {
			// Query always has 9 letters.
            // A longer word can never be constructed.
			if(word.length() > 9) continue;
			
			String sig = signature(word); // key
			
			
			String old = index.get(sig);  // Follow-up 3 — Deterministic Tie Breaking
			
			// Base problem allows any word when multiple
            // words have the same exact signature.
			index.putIfAbsent(sig, word);
			
			// For Follow-up 3 — Deterministic Tie Breaking: Update the above code as below ... Start
			// -- uncomment below for Follow-up 3
			//if (old == null || word.compareTo(old) < 0) {
			//    index.put(sig, word);
			//}
			
			// Follow-up 3 changes: Ends
		}
	}
	
	// sort the word in canonical order and return
	
	/* Why sorting is good here?
	 	We could use a 26-element frequency vector/arr.
		But during a 15 to 20 minute interview, sorting is simpler.
		
		Every useful word has at most 9 characters.
		
		So sorting costs at most: O(9 log 9)
		
		which is effectively constant.
		
		Likewise, every query subset contains at most 9 characters.
	 * */
	
	private String signature(String word) {
		char[] arr = word.toCharArray();
		Arrays.sort(arr);
		return new String(arr);
	}
	
	
	
	/** Follow-up 1 — Many Queries Against the Same Dictionary */
	
	/*
	 * Interview Explanation
	 * 
	 * You can say:
	 * 
	 * Our existing solution already fits this follow-up well. I would preprocess
	 * the dictionary once into a HashMap from a canonical character signature to a
	 * dictionary word. Since queries always contain exactly nine letters, each
	 * query can enumerate at most 511 non-empty subsets and perform HashMap lookups
	 * rather than scanning the dictionary.
	 * 
	 * The preprocessing is effectively O(D), because I only sort words of length at
	 * most nine. Each query is O(2^9 times 9 log 9), which is effectively constant
	 * relative to dictionary size.
	 * 
	 * Memory is O(D) for the index, although multiple words with the same character
	 * multiset collapse into one entry for the base problem.
	 * 
	 * For the signature, I would use a sorted String because it's simple and the
	 * maximum length is only nine. If throughput or memory became critical, I could
	 * use a more compact frequency representation.
	 * 
	 * Since there are millions of queries, I'd also consider caching results by the
	 * normalized nine-letter query signature so repeated queries become a single
	 * HashMap lookup. I can add a second cache keyed by
	 * the normalized 9-letter query itself. The first time a unique multiset of
	 * letters appears, I run the normal DFS over at most 511 subsets and store the
	 * final result. If the same letters appear again, even in a different order,
	 * they normalize to the same query signature, so I can return the cached answer
	 * immediately. This is especially useful when query patterns repeat frequently,
	 * where queryCache become a single HashMap lookup.
	 */
	
	/* Revision Card::
	 
	 FOLLOW-UP: Millions of queries, same dictionary

		Observation:
		Dictionary is huge but static.
		Queries are tiny: exactly 9 letters.
		
		Preprocess once:
		    ignore words > 9
		    sort each useful word
		    signature -> word
		
		Example:
		    race -> acer
		    care -> acer
		    crate -> acert
		
		Query:
		    generate all subsets using DFS
		    max = 2^9 - 1 = 511
		
		For each subset:
		    sort subset
		    lookup signature in HashMap
		    keep longest word
		
		Preprocessing:
		    O(D * 9 log 9)
		    ≈ O(D)
		
		Query:
		    O(2^9 * 9 log 9)
		    ≈ O(1) relative to dictionary size
		
		Memory:
		    O(D)
		
		Signature:
		    sorted String
		    simple + handles duplicates correctly
		
		For millions of queries:
		    optional query cache
		
		    normalized 9-letter signature -> answer
		
		Repeated query:
		    O(1) lookup
		
		Important:
		    Never scan dictionary during query processing.
		
		Existing solution already supports this follow-up.
		Only optional addition:
		    query-result cache.
	 * */
	
	
	/** Follow-up 2 — Return All Longest Words */
	/*
	 This may require changing preprocessing from:

		signature -> one word
		
		to something closer to:
		
		signature -> collection of words
	 * */
	
	
	/** Follow-up 3 — Deterministic Tie Breaking | Priority: HIGH
		If several longest words can be formed, return the lexicographically smallest one. */
	
	/* Interview Explanation
	 * 
	 * I would handle most of the tie-breaking during preprocessing. Since all words
	 * with the same signature are anagrams, they also have the same length. So
	 * instead of storing an arbitrary word for each signature, I'll store the
	 * lexicographically smallest word for that signature.
	 * 
	 * During query processing, I still need one small tie check because different
	 * signatures can produce words of the same maximum length. So when a candidate
	 * has the same length as the current best, I'll compare the two strings
	 * lexicographically.
	 * 
	 * Since every word is at most nine characters, that comparison is
	 * constant-sized and does not change the overall query complexity.
	 */
	
	
	/** Follow-up 4 — Query Length Is No Longer Fixed at 9 | Priority: MEDIUM HIGH */
	
	/* Interview Script:: 
	 * 
	 * The original solution works because 2^9 is only 512. Once query length
	 * becomes 20, 30, or 50, subset enumeration becomes exponential and is no
	 * longer viable.
	 * 
	 * I would reverse the search. During preprocessing, I'd group dictionary words
	 * by length and store a 26-character frequency vector for every word.
	 * 
	 * For each query, I'd build its own frequency vector and start from the longest
	 * possible dictionary-word length. For every candidate at that length, I check
	 * whether every required character count is less than or equal to the query
	 * count.
	 * 
	 * The first length where I find a valid word is the maximum answer length, so I
	 * can stop searching shorter buckets.
	 * 
	 * This changes the query complexity from exponential in query length to roughly
	 * proportional to the number of dictionary candidates examined, with only 26
	 * comparisons per candidate.
	 */
	
	
	/*
		Query length becomes 20 / 30 / 50.
		
		Problem:
		Subset enumeration no longer works.
		
		2^20 ≈ 1M
		2^30 ≈ 1B
		
		Key change:
		STOP generating subsets.
		
		Reverse the search:
		
		dictionary candidates
		        ↓
		check if each word fits query
		
		
		PREPROCESS:
		
		For each dictionary word:
		
		    build int[26] frequency
		
		    group by word length
		
		Structure:
		
		    length -> list of (word, freq)
		
		
			WordInfo {
			    String word;
			    int[] freq; // int[26]
			}
			Map<Integer, List<WordInfo>> byLength;
		
		QUERY:
		
		1. Build queryFreq[26].
		
		2. Start at:
		
		   min(query.length,
		       maxDictionaryWordLength)
		
		3. Search lengths descending.
		
		4. For each candidate:
		
		   for c = 0..25:
		
		       if wordFreq[c] > queryFreq[c]:
		           invalid
		
		5. First length containing a valid word
		   gives the longest answer.
		
		Complexity:
		
		Preprocessing:
		    O(total dictionary characters)
		
		Space:
		    O(D * 26)
		    ≈ O(D)
		
		Query:
		    O(Q + C * 26)
		
		where:
		    Q = query length
		    C = candidates examined
		
		Worst case:
		    O(Q + D)
		
		But:
		    grouping by length
		    +
		    longest-to-shortest search
		    +
		    early stopping
		
		can reduce candidates significantly.
		
		
		Signature change:
		
		Old:
		    sorted String
		
		New:
		    int[26] frequency vector
		
		
		Why:
		
		Old problem:
		    exact subset lookup
		
		New problem:
		    character-count containment check
		
		
		IMPORTANT:
		
		Original algorithm depends on:
		    query size = 9
		
		Once query grows:
		    exponential enumeration must be removed. 
	 * */
	
	boolean _temp;
	
}


/*
# Longest Dictionary Word from 9 Letters

**Reported:** September 3, 2026
**Round:** Google coding interview, exact round not identified
**Priority:** **EXTREMELY HIGH**

## Problem

You are given a very large dictionary containing unique lowercase English words.

You may preprocess the dictionary once before receiving any queries.

For each query, you are given exactly **9 lowercase letters**.

Return the **longest dictionary word** that can be formed using only the letters in the query.

Each occurrence of a letter may be used **at most once**. Therefore, duplicate letters in the query matter.

If multiple dictionary words of the same maximum length can be formed, you may return any one of them unless otherwise specified.

If no dictionary word can be formed, return an empty string.

---

## Example 1

```text
dictionary =
["cat", "act", "cart", "trace", "crate", "react", "cater", "create"]

letters = "aaceertxy"
```

Possible words include:

```text
cat
act
trace
crate
react
cater
create
```

`create` can be formed using:

```text
c, r, e, a, t, e
```

The query contains two `e`s, so the word is valid.

```text
Output: "create"
```

---

## Example 2 — Duplicate Letters Matter

```text
dictionary =
["able", "ball", "label", "bell", "babel"]

letters = "aabbellxy"
```

`label` requires:

```text
l x 2
```

but the query contains only one `l`.

Therefore:

```text
"label" -> invalid
```

`babel` requires:

```text
b x 2
a x 1
e x 1
l x 1
```

which the query contains.

```text
Output: "babel"
```

---

## Example 3

```text
dictionary =
["stone", "tones", "notes", "ones", "set"]

letters = "aenostxyz"
```

The longest constructible words are:

```text
stone
tones
notes
```

Each has length 5.

If any longest word may be returned:

```text
Output: "stone"
```

`"tones"` or `"notes"` would also be valid.

---

## Example 4 — Not Every Query Letter Must Be Used

```text
dictionary =
["car", "card", "cards", "scar"]

letters = "aacdrstxy"
```

The query contains 9 letters, but the returned word does not need to use all 9.

```text
"cards" -> valid
"scar"  -> valid
"card"  -> valid
```

Therefore:

```text
Output: "cards"
```

---

## Example 5 — No Match

```text
dictionary =
["dog", "cat", "apple"]

letters = "bcfijkqxy"
```

No dictionary word can be constructed.

```text
Output: ""
```

---

# Important Constraints

A reasonable interview version may use constraints such as:

```text
1 <= dictionary.size() <= 1,000,000
1 <= dictionary[i].length() <= 50

Each query contains exactly 9 lowercase English letters.

The dictionary is fixed.
Many queries may be executed against the same dictionary.
Dictionary preprocessing is allowed.
```

Words longer than 9 characters can never be returned and therefore may be ignored during preprocessing.

---

# Important Observation

The key asymmetry is:

```text
dictionary = extremely large
query      = only 9 letters
```

Scanning the entire dictionary for every query would waste the preprocessing opportunity.

Because the query contains only 9 positions, the number of subsets of its positions is:

```text
2^9 = 512
```

This is extremely small.

Therefore, an attractive direction is:

```text
preprocess dictionary once

then for every query:
    enumerate possible subsets of its 9 letters
    convert each subset into a canonical signature
    perform hash-map lookup
```

Duplicate letters must remain represented correctly in the signature.

For example:

```text
"aabc"
```

must be distinguishable from:

```text
"abc"
```

---

# What This Problem Tests

This problem primarily tests:

```text
Recognizing preprocessing opportunities

Exploiting asymmetric input sizes

Hash-map based indexing

Canonical representations / signatures

Correctly handling duplicate characters

Trading preprocessing memory for query speed

Avoiding repeated scans of a very large dataset
```

---

# Closest LeetCode Problems

There is no exact LeetCode equivalent.

Related problems include:

**LC 1160 — Find Words That Can Be Formed by Characters**

Relevant because it checks whether a word can be formed from an available multiset of characters.

**LC 318 — Maximum Product of Word Lengths**

Only conceptually related through compact character representations and preprocessing.

The interview problem is more focused on:

```text
huge static dictionary
+
tiny repeated query
+
preprocessing
```

---

# Likely Optimal Direction

Preprocess all useful dictionary words by a **canonical character signature**.

Since no answer can contain more than 9 characters, dictionary words longer than 9 can immediately be skipped.

For example:

```text
"care"  -> "acer"
"race"  -> "acer"
"acre"  -> "acer"
```

Words with duplicate characters preserve those duplicates:

```text
"letter" -> "eelrtt"
```

The preprocessing structure could conceptually contain:

```text
signature -> best dictionary word
```

or, depending on follow-up requirements:

```text
signature -> list of dictionary words
```

For a query containing 9 letters, enumerate its subsets and generate the corresponding signatures.

Because there are at most:

```text
512
```

subsets, query processing can be independent of the total dictionary size after preprocessing.

---

# Follow-ups

## Follow-up 1 — Many Queries Against the Same Dictionary

### Priority: EXTREMELY HIGH

The dictionary is loaded once, but the service must answer millions of different 9-letter queries.

How would you preprocess the dictionary so that each query can be answered without scanning dictionary words?

Discuss:

```text
preprocessing time
query time
memory usage
signature representation
```

This is the most likely follow-up because it reinforces the central idea of the problem: move expensive work to preprocessing.

---

## Follow-up 2 — Return All Longest Words

### Priority: VERY HIGH

Instead of returning any one longest word, return **all dictionary words having the maximum constructible length**.

Example:

```text
dictionary =
["stone", "tones", "notes", "ones"]

letters = "aenostxyz"
```

Return:

```text
["stone", "tones", "notes"]
```

The returned ordering does not matter unless otherwise specified.

This may require changing preprocessing from:

```text
signature -> one word
```

to something closer to:

```text
signature -> collection of words
```

---

## Follow-up 3 — Deterministic Tie Breaking

### Priority: HIGH

If several longest words can be formed, return the **lexicographically smallest** one.

Example:

```text
dictionary =
["stone", "tones", "notes"]

letters = "aenostxyz"
```

All three have length 5.

Lexicographically:

```text
notes < stone < tones
```

Therefore:

```text
Output: "notes"
```

How would you modify preprocessing so that query complexity does not significantly increase?

---

## Follow-up 4 — Query Length Is No Longer Fixed at 9

### Priority: MEDIUM HIGH

Suppose queries may now contain up to:

```text
20
30
or 50
```

letters.

Enumerating every subset is no longer practical because:

```text
2^20  ~= 1 million
2^30  ~= 1 billion
```

How would you redesign the solution?

Possible directions worth discussing include:

```text
frequency-vector lookup
grouping words by length
inverted indexes
bit masks when characters are unique
pruning candidate words
different strategies depending on query length
```

The interviewer is testing whether you recognize that the original approach depends heavily on the small constant `9`.

---

## Follow-up 5 — Dictionary Updates

### Priority: MEDIUM

The dictionary is no longer completely static.

You must support operations such as:

```text
addWord(word)
removeWord(word)
query(letters)
```

while still answering queries quickly.

How would your preprocessing structure change?

Discuss:

```text
updating signature buckets
maintaining the best word for each signature
handling removal of the current best candidate
memory and update/query trade-offs
```

---

# Why This Is a Strong Google Interview Problem

This problem initially looks like a straightforward character-frequency problem, but the real challenge is recognizing the constraints.

A weak approach would repeatedly scan the huge dictionary.

A stronger approach notices:

```text
Dictionary is huge but static.
Query is extremely small.
Preprocessing is explicitly allowed.
```

That leads naturally to indexing the dictionary and shifting work away from query time.

The fixed query length of 9 is especially important because:

```text
2^9 = 512
```

which makes subset enumeration practical.

## Recommendation

**Keep this problem in the EXTREMELY HIGH priority group.**

It is particularly useful for Google preparation because it tests the kind of constraint-driven reasoning interviewers often look for:

```text
Do not optimize the obvious loop.

First ask:

What is large?
What is small?
What is repeated?
What can be precomputed?
```


*/