package Google.extremelyHigh;

import java.util.*;

public class BestAnagramFinder {

	public static void main(String[] args) {

        /*
         * Normal Test 1
         *
         * Anagrams of "care":
         *
         * race:
         * |r-a| + |a-c| + |c-e|
         * = 17 + 2 + 2
         * = 21
         *
         * acre:
         * |a-c| + |c-r| + |r-e|
         * = 2 + 15 + 13
         * = 30
         *
         * Best = acre
         */
        BestAnagramFinder finder1 = new BestAnagramFinder(
                Arrays.asList(
                        "care",
                        "race",
                        "acre",
                        "stone",
                        "tones",
                        "lamp"
                )
        );

        System.out.println("Test 1");
        System.out.println("Expected: acre");
        System.out.println("Actual:   "
                + finder1.findBestAnagram("care"));
        System.out.println();


        /*
         * Normal Test 2
         *
         * abc is excluded.
         *
         * bac:
         * |b-a| + |a-c|
         * = 1 + 2
         * = 3
         *
         * cab:
         * |c-a| + |a-b|
         * = 2 + 1
         * = 3
         *
         * Tie-breaking is unspecified,
         * so the first maximum found is returned.
         */
        BestAnagramFinder finder2 = new BestAnagramFinder(
                Arrays.asList(
                        "abc",
                        "bac",
                        "cab",
                        "xyz"
                )
        );

        System.out.println("Test 2");
        System.out.println("Expected: bac or cab");
        System.out.println("Actual:   "
                + finder2.findBestAnagram("abc"));
        System.out.println();


        /*
         * Edge Test 1
         *
         * Query exists in dictionary,
         * but there is no other anagram.
         */
        BestAnagramFinder finder3 = new BestAnagramFinder(
                Arrays.asList(
                        "cat",
                        "dog",
                        "bird"
                )
        );

        System.out.println("Test 3 - No alternative anagram");
        System.out.println("Expected: null");
        System.out.println("Actual:   "
                + finder3.findBestAnagram("cat"));
        System.out.println();


        /*
         * Edge Test 2
         *
         * Query does NOT need to exist in the dictionary.
         */
        BestAnagramFinder finder4 = new BestAnagramFinder(
                Arrays.asList(
                        "race",
                        "acre",
                        "stone"
                )
        );

        System.out.println("Test 4 - Query not in dictionary");
        System.out.println("Expected: acre");
        System.out.println("Actual:   "
                + finder4.findBestAnagram("care"));
        System.out.println();


        /*
         * Invalid Test
         *
         * Null input should throw IllegalArgumentException.
         */
        System.out.println("Test 5 - Invalid input");

        try {
            finder4.findBestAnagram(null);

            System.out.println(
                    "Expected: IllegalArgumentException"
            );
            System.out.println(
                    "Actual:   No exception"
            );

        } catch (IllegalArgumentException e) {
            System.out.println(
                    "Expected: IllegalArgumentException"
            );
            System.out.println(
                    "Actual:   IllegalArgumentException"
            );
        }
    }


	
	/* Why secondBest? 
	 * because findBestAnagram(word) must exclude the query word itself, 
	 * storing only the single best word is not enough. We should store 
	 * the best and second-best candidate for each group.
	 * */
	static class BestTwo {
	    String best;
	    int bestScore = Integer.MIN_VALUE;

	    String secondBest;
	    int secondBestScore = Integer.MIN_VALUE;
	}
	
	List<String> dict;
	Map<String, List<String>> group;
	
	// when we want to calculate bestScore/anagram while pre-processing
	Map<String, BestTwo> bestByGroup; 
	
	public BestAnagramFinder(List<String> dictionary) {
        if (dictionary == null) {
            throw new IllegalArgumentException("Dictionary cannot be null");
        }

        this.dict = dictionary;
        this.group = new HashMap<>();

        // Preprocess dictionary into anagram groups.
        groupAnagrams();
    }

	/*
     * Return the score of the given word.
     *
     * Example: "bad"
     * |b-a| + |a-d| = 1 + 3 = 4
     *
     * Time: O(K)
     * Space: O(1)
     */
    public int calculateScore(String word) {
    	if (word == null) {
            throw new IllegalArgumentException("Word cannot be null");
        }

    	char[] ch = word.toCharArray();
    	
    	int score = 0;
    	for(int i=1; i<ch.length; i++) {
    		score += Math.abs(ch[i-1] - ch[i]);
    	}
    	
    	return score;
    }

    /* Use the preprocessed anagram group instead of scanning
     * the entire dictionary.
     *
     * Then find the highest-scoring word while excluding
     * the query itself.
     * 
     * Time:: 
     * 	hash query                  O(K)
		lookup matching group       O(1) average
		scan G candidates           O(G * K)

		Total: O(G * K)
     * 
     */
    // Best anagram for word: when there are more than one anagams for a workd then
    // return the one with the hightest score
    public String findBestAnagram(String word) {
    	if (word == null) {
            throw new IllegalArgumentException("Word cannot be null");
        }
    	
    	String key = hashAnagram(word);
    	List<String> anagrams = group.get(key);
    	
    	if(anagrams == null) return null;
    	
    	int bestScore = 0;
    	String bestAnagram = null;
    	for(String candidate : anagrams) {
    		
    		if (candidate.equals(word)) continue;
    		
    		int currScore = calculateScore(candidate);
    		
    		if(currScore > bestScore) {
    			bestScore = currScore;
    			bestAnagram = candidate;
    		}
    	}
    	
    	return bestAnagram;
    }
    
    
    public String findBestAnagram2(String word) {

        String key = hashAnagram(word);

        BestTwo bestTwo = bestByGroup.get(key);

        if (bestTwo == null) {
            return null;
        }

        if (!word.equals(bestTwo.best)) {
            return bestTwo.best;
        }

        return bestTwo.secondBest;
    }

    /*
     * Build an anagram signature using character frequencies.
     *
     * Anagrams produce exactly the same key.
     *
     * Time: O(K)
     */
    private String hashAnagram(String word) {
    	int[] count = new int[26];
    	
    	for(char c : word.toCharArray()) {
    		count[c - 'a']++;
    	}
    	return Arrays.toString(count);
    }
    
    /*
     * Preprocess the dictionary by grouping all anagrams.
     *
     * Time: O(N * K)
     * Space: O(N * K)
     */
    // preprocess the dictionary, group anagram
    // O(NK), where N is the length of strs, and K is the maximum 
    // length of a string in strs. Counting each string is linear 
    // in the size of the string, and we count every string.
    private void groupAnagrams() {
    	for(String s : dict) {
    		String key = hashAnagram(s);
    		group.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
    	}
    }
    
    /* 
     * Calculate best score during pre-processing
     * 
     * Time: O(N * K)
     * 		where:
				- N = number of words in the dictionary
				- K = maximum/average word length
				
		Note: This approach also good for the use case when we limited memory
		as we are only storing 2 anagram values instead of entire list for anagrams. 
     * */
    private void groupAnagrams2() {

        for (String word : dict) {				// N words
            String key = hashAnagram(word); 	// O(K)
            int score = calculateScore(word);	// O(K)

            BestTwo bestTwo =
                    bestByGroup.computeIfAbsent(
                            key,
                            k -> new BestTwo()
                    );

            if (score > bestTwo.bestScore) {

                // Current best becomes second best.
                bestTwo.secondBest = bestTwo.best;
                bestTwo.secondBestScore = bestTwo.bestScore;

                // New word becomes best.
                bestTwo.best = word;
                bestTwo.bestScore = score;

            } else if (score > bestTwo.secondBestScore) {

                bestTwo.secondBest = word;
                bestTwo.secondBestScore = score;
            }
        }
    }
    
}


/**
 
	Below is a cleaner interview-ready version. I also checked the reported 
	September 24, 2026 experience; the report describes essentially this problem 
	and specifically calls out preprocessing for repeated `findBestAnagram()` queries. 
	
	
	# Best Anagram Finder
	
	### Priority: **EXTREMELY HIGH**
	
	This problem was reported in a **Google Senior Software Engineer first-round 
	interview on September 24, 2026**.
	
	## Problem Statement
	
	You are given a dictionary of words.
	
	Implement a class:
	
	```java
	class BestAnagramFinder
	```
	
	that supports finding the **best anagram** of a query word.
	
	Two words are anagrams if they contain exactly the same characters with the 
	same frequencies, possibly in a different order.
	
	For example:
	
	```text
	"care", "race", "acre"
	```
	
	are all anagrams of one another.
	
	You may assume that the following method is provided:
	
	```java
	long hashAnagram(String word)
	```
	
	It has this property:
	
	```text
	hashAnagram(a) == hashAnagram(b)
	```
	
	if and only if:
	
	```text
	a and b are anagrams
	```
	
	You do **not** need to implement `hashAnagram()` unless explicitly asked.
	
	---
	
	## Word Score
	
	The score of a word is the sum of the absolute differences between every 
	pair of adjacent characters.
	
	For:
	
	```text
	c1 c2 c3 ... cn
	```
	
	the score is:
	
	```text
	|c1 - c2|
	+ |c2 - c3|
	+ ...
	+ |c(n-1) - cn|
	```
	
	Treat characters according to their normal alphabet/character values.
	
	For example:
	
	```text
	word = "bad"
	```
	
	Using:
	
	```text
	a = 0
	b = 1
	d = 3
	```
	
	we get:
	
	```text
	|b - a| + |a - d|
	= |1 - 0| + |0 - 3|
	= 1 + 3
	= 4
	```
	
	So:
	
	```text
	calculateScore("bad") = 4
	```
	
	The same result is obtained if ASCII/Unicode character values are used 
	because only differences matter.
	
	---
	
	# Required API
	
	```java
	class BestAnagramFinder {
	
	    public BestAnagramFinder(List<String> dictionary);
	
	    public int calculateScore(String word);
	
	    public String findBestAnagram(String word);
	
	    // Provided by interviewer
	    private long hashAnagram(String word);
	}
	```
	
	---
	
	# `calculateScore(word)`
	
	Return the score of the given word.
	
	### Example 1
	
	```text
	word = "bad"
	
	score =
	|b-a| + |a-d|
	= 1 + 3
	= 4
	```
	
	Output:
	
	```text
	4
	```
	
	### Example 2
	
	```text
	word = "abc"
	
	score =
	|a-b| + |b-c|
	= 1 + 1
	= 2
	```
	
	Output:
	
	```text
	2
	```
	
	### Example 3
	
	```text
	word = "az"
	
	score =
	|a-z|
	= 25
	```
	
	Output:
	
	```text
	25
	```
	
	---
	
	# `findBestAnagram(word)`
	
	Given a query word:
	
	1. Find all dictionary words that are anagrams of `word`.
	2. Exclude the query word itself.
	3. Calculate the score of every remaining candidate.
	4. Return the candidate having the largest score.
	
	If no valid alternative anagram exists, return:
	
	```text
	null
	```
	
	Assume initially that if multiple candidates have the same maximum score, 
	returning any one of them is acceptable.
	
	---
	
	# Example 1
	
	Dictionary:
	
	```text
	["care", "race", "acre", "stone", "tones", "lamp"]
	```
	
	Query:
	
	```text
	"care"
	```
	
	Candidates:
	
	```text
	"race"
	"acre"
	```
	
	Both have the same anagram hash as `"care"`.
	
	Suppose:
	
	```text
	score("race") = 19
	score("acre") = 16
	```
	
	Then:
	
	```text
	findBestAnagram("care")
	```
	
	returns:
	
	```text
	"race"
	```
	
	---
	
	# Example 2
	
	Dictionary:
	
	```text
	["abc", "bac", "cab", "xyz"]
	```
	
	Query:
	
	```text
	"abc"
	```
	
	Candidates:
	
	```text
	"bac"
	"cab"
	```
	
	Scores:
	
	```text
	"bac"
	
	|b-a| + |a-c|
	= 1 + 2
	= 3
	```
	
	```text
	"cab"
	
	|c-a| + |a-b|
	= 2 + 1
	= 3
	```
	
	Both are optimal, so either may be returned:
	
	```text
	"bac"
	```
	
	or:
	
	```text
	"cab"
	```
	
	---
	
	# Example 3 — No Other Anagram
	
	Dictionary:
	
	```text
	["cat", "dog", "bird"]
	```
	
	Query:
	
	```text
	"cat"
	```
	
	Although `"cat"` exists in the dictionary, the query word itself cannot be returned.
	
	There are no other anagrams.
	
	Output:
	
	```text
	null
	```
	
	---
	
	# Example 4 — Query Does Not Have To Be In Dictionary
	
	Dictionary:
	
	```text
	["race", "acre", "stone"]
	```
	
	Query:
	
	```text
	"care"
	```
	
	Both:
	
	```text
	"race"
	"acre"
	```
	
	are valid candidates even though `"care"` itself is not in the dictionary.
	
	Return whichever candidate has the larger score.
	
	---
	
	# Example 5 — Multiple Anagram Groups
	
	Dictionary:
	
	```text
	[
	    "listen",
	    "silent",
	    "enlist",
	    "stone",
	    "tones",
	    "notes",
	    "apple"
	]
	```
	
	Query:
	
	```text
	"stone"
	```
	
	Only words belonging to the same anagram group are considered:
	
	```text
	"tones"
	"notes"
	```
	
	The `"listen"` group is irrelevant even though those words have the same length.
	
	---
	
	# Constraints
	
	A reasonable interview version could use:
	
	```text
	1 <= dictionary.size() <= 100,000
	1 <= word.length() <= 100
	dictionary words contain lowercase English letters
	```
	
	Assume:
	
	```text
	hashAnagram(word)
	```
	
	correctly identifies anagram groups.
	
	---
	
	# Important Observation
	
	A straightforward implementation could scan the entire dictionary on every call:
	
	```text
	for each dictionary word:
	    if hash matches:
	        calculate score
	```
	
	That gives roughly:
	
	```text
	O(N)
	```
	
	work per query, ignoring the bounded word length.
	
	That becomes expensive when `findBestAnagram()` is called many times.
	
	The reported interview explicitly discussed preprocessing the dictionary 
	so repeated queries can be answered efficiently.
	
	---
	
	# Follow-ups
	
	## Follow-up 1 — Repeated Queries / Preprocess the Dictionary
	
	### Priority: **EXTREMELY HIGH**
	
	Suppose:
	
	```text
	findBestAnagram()
	```
	
	will be called millions of times, while the dictionary remains unchanged.
	
	Scanning every dictionary word for every query is too expensive.
	
	How would you preprocess the dictionary so queries become close to:
	
	```text
	O(1)
	```
	
	?
	
	A natural direction is:
	
	```text
	anagramHash
	      ↓
	best candidate information
	```
	
	For example:
	
	```java
	Map<Long, ...>
	```
	
	During construction:
	
	```text
	compute hash
	compute score
	group words by their anagram hash
	```
	
	Then queries perform a direct hash lookup.
	
	### Important complication
	
	You cannot necessarily store only:
	
	```text
	hash -> highest-scoring word
	```
	
	because the highest-scoring word might be the query itself.
	
	Example:
	
	```text
	dictionary = ["abc", "bac", "cab"]
	```
	
	Suppose:
	
	```text
	"cab" has the highest score
	```
	
	For:
	
	```text
	findBestAnagram("abc")
	```
	
	returning `"cab"` works.
	
	But for:
	
	```text
	findBestAnagram("cab")
	```
	
	you must exclude `"cab"` and return the **second-best candidate**.
	
	This naturally leads to storing at least:
	
	```text
	best candidate
	second-best candidate
	```
	
	for each anagram group.
	
	Expected discussion:
	
	```text
	Preprocessing: O(total dictionary characters)
	Query: O(1) average
	Space: O(N)
	```
	
	This is the most important follow-up because it directly matches the 
	optimization emphasized in the reported interview.
	
	---
	
	## Follow-up 2 — Duplicate Words in the Dictionary
	
	### Priority: **VERY HIGH**
	
	Suppose the dictionary can contain duplicates:
	
	```text
	["care", "race", "race", "acre"]
	```
	
	For query:
	
	```text
	"race"
	```
	
	what does:
	
	> exclude the query word itself
	
	mean?
	
	Possible definitions:
	
	```text
	A. Exclude every dictionary entry equal to "race"
	
	or
	
	B. Exclude only one occurrence corresponding to the query
	```
	
	Clarify the desired semantics and modify the design accordingly.
	
	If identical strings should all be excluded, the preprocessing structure 
	may need to retain more than simply the top two dictionary entries.
	
	---
	
	## Follow-up 3 — Deterministic Tie Breaking
	
	### Priority: **VERY HIGH**
	
	Currently, if several candidates have the same maximum score, returning any one is allowed.
	
	Change the requirement:
	
	> If multiple candidates have the same score, return the lexicographically smallest candidate.
	
	Example:
	
	```text
	candidates:
	"bac" -> 3
	"cab" -> 3
	```
	
	Return:
	
	```text
	"bac"
	```
	
	Discuss how this affects preprocessing.
	
	The comparison becomes conceptually:
	
	```text
	higher score first
	then lexicographically smaller word
	```
	
	---
	
	## Follow-up 4 — Return Top K Anagrams
	
	### Priority: **HIGH**
	
	Instead of returning only the best candidate:
	
	```java
	String findBestAnagram(String word)
	```
	
	support:
	
	```java
	List<String> findTopKAnagrams(String word, int k)
	```
	
	Return the `k` highest-scoring anagrams, excluding the query word.
	
	Sort by:
	
	```text
	1. score descending
	2. lexicographical order ascending
	```
	
	Example:
	
	```text
	dictionary =
	["care", "race", "acre", "erca"]
	
	query = "care"
	k = 2
	```
	
	Return the two highest-ranked valid candidates.
	
	This changes the preprocessing discussion from:
	
	```text
	top 2
	```
	
	toward:
	
	```text
	sorted candidates
	heap
	or top-K structure per anagram group
	```
	
	---
	
	## Follow-up 5 — Dictionary Updates
	
	### Priority: **HIGH**
	
	The initial problem assumes the dictionary is immutable.
	
	Now support:
	
	```java
	void addWord(String word)
	
	void removeWord(String word)
	```
	
	while continuing to support:
	
	```java
	findBestAnagram(word)
	```
	
	efficiently.
	
	Discuss how your data structure changes.
	
	A simple precomputed:
	
	```text
	best + second best
	```
	
	representation becomes harder to maintain when the best word is removed.
	
	Possible discussion areas:
	
	```text
	TreeSet / ordered structure per anagram group
	PriorityQueue with lazy deletion
	balanced BST
	score + lexicographical ordering
	```
	
	---
	
	## Follow-up 6 — `hashAnagram()` Is Not Provided
	
	### Priority: **HIGH**
	
	Implement an anagram key yourself.
	
	For lowercase English letters, one option is a frequency signature:
	
	```text
	"aabbc"
	
	→ [2,2,1,0,0,...]
	```
	
	Words with identical character frequencies have the same signature.
	
	Example:
	
	```text
	"care"
	"race"
	"acre"
	```
	
	all produce the same frequency representation.
	
	Expected complexity:
	
	```text
	O(L)
	```
	
	per word, where `L` is the word length.
	
	This is related to **LeetCode 49 — Group Anagrams**.
	
	---
	
	## Follow-up 7 — Very Large Dictionary
	
	### Priority: **MEDIUM-HIGH**
	
	Suppose the dictionary contains:
	
	```text
	hundreds of millions of words
	```
	
	and no longer fits comfortably on one machine.
	
	How would you scale preprocessing and lookup?
	
	One natural partitioning key is:
	
	```text
	hashAnagram(word)
	```
	
	Words from the same anagram group must be routed to the same partition.
	
	Potential topics:
	
	```text
	partitioning by hash
	distributed preprocessing
	persistent key-value store
	caching popular groups
	hot partitions
	replication
	```
	
	This turns the coding problem into a lightweight system-design discussion.
	
	---
	
	## Follow-up 8 — Concurrent Queries
	
	### Priority: **MEDIUM**
	
	Suppose many threads call:
	
	```java
	findBestAnagram()
	```
	
	concurrently.
	
	If the dictionary is immutable after construction, how would you make reads thread-safe?
	
	A useful observation is that an immutable preprocessed map can safely support concurrent 
	reads without per-query locking.
	
	If:
	
	```text
	addWord/removeWord
	```
	
	are also allowed, discuss:
	
	```text
	read-write locks
	copy-on-write snapshots
	concurrent maps
	per-anagram-group locking
	```
	
	---
	
	## Follow-up 9 — Memory Optimization
	
	### Priority: **MEDIUM**
	
	Suppose memory is limited and you only need:
	
	```java
	findBestAnagram()
	```
	
	but never need to enumerate every anagram.
	
	Do you really need:
	
	```text
	hash -> List<String>
	```
	
	?
	
	For the original query requirement, you may only need a small amount of information per group, 
	such as:
	
	```text
	best candidate
	second-best candidate
	their scores
	```
	
	This can significantly reduce memory compared with storing every group as a separately 
	sorted collection.
	
	---
	
	# Related LeetCode Problems
	
	There is no direct LeetCode equivalent for the complete problem.
	
	The closest is:
	
	**LC 49 — Group Anagrams**
	
	It covers:
	
	```text
	creating an anagram signature
	grouping words by that signature
	```
	
	but does not cover:
	
	```text
	adjacent-character scoring
	excluding the query itself
	finding the highest-scoring alternative
	O(1) repeated queries
	best / second-best preprocessing
	```
	
	That last detail is likely the key insight interviewers are looking for here: 
	**for repeated queries, storing only the highest-scoring word per anagram group 
	*is insufficient because that word may itself be the query.
	*** A compact `best + secondBest` representation is enough for the original 
	*immutable-dictionary problem.
 
 
 */