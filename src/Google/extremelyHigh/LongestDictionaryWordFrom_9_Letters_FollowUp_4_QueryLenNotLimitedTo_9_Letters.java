package Google.extremelyHigh;

import java.util.HashMap;
import java.util.*;

public class LongestDictionaryWordFrom_9_Letters_FollowUp_4_QueryLenNotLimitedTo_9_Letters {
    
	/* Step-by-Step Algorithm
		Preprocessing
		
		For every dictionary word:
		
			1. Build a 26-element frequency array.
			
			2. Group the word by its length.
			
			3. Store:
			       word
			       frequency array
			
			4. Track maximum dictionary word length.
		
		Conceptually: length -> List<WordInfo>
		
		Example:
			5 ->
			    stone
			    crate
			    apple
			
			4 ->
			    care
			    boat
			
			3 ->
			    cat
			    dog
		
	  *	Query
			For each query:
		
			1. Build queryFreq[26].
			2. Compute:
			   maxLen = min(query.length(), maxDictionaryWordLength)
			
			3. Start from maxLen and move downward.
			4. For each word of that length: compare its 26 freq against queryFreq.
			
			5. If: wordFreq[c] <= queryFreq[c]
			
			   for every character:
			       return that word.
			
			6. If nothing matches:
			       return ""
	  
	* Why This Is Better Than Subset Enumeration? 
	  
		  Old approach: O(2^Q)
		  where: Q = query length
		  
		  For: Q = 30
		  that's roughly: 1 billion subsets
		  
		  Not acceptable.
		  
		  New approach depends primarily on: no of dict candidates examined
		  rather than: 2^Q
		  
		  That is the crucial redesign.
	 */
	
	
	/* COMPLEXITY ANALYSIS
	 * Let:
		D = number of dictionary words
		T = total number of characters in dictionary
		Q = query length
		C = number of dictionary candidates examined
		A = alphabet size = 26
		
	 * 	Preprocessing Time
		We scan every dictionary character once to build word freq:
			O(T)
		
		Equivalent to: O(D * average word length)
		
	 *	Preprocessing Space
		
		Each dictionary word stores:
			word
			26 frequency counts
		So: O(D * 26)
		
		Since 26 is constant: O(D)
		
	 *	Query Time
		
		Building query frequency: O(Q)
		Checking one candidate: O(26)
		If we examine C candidates: O(Q + C * 26)
		Since 26 is fixed: O(Q + C)
		
		Worst case: C = D
		
		so: O(Q + D)
		
		But grouping by length and stopping after finding a match 
		often avoids scanning the whole dictionary. 
	 */
	
	/* Interview Explanation Before Coding
	 * 
	 * The original solution relies on the query having exactly nine letters,
	 * because enumerating all subsets costs only 2^9.
	 * 
	 * If the query can have 20, 30, or 50 letters, that becomes exponential and is
	 * no longer practical, so I would change the direction of the search.
	 * 
	 * During preprocessing, I'll store each dictionary word with a 26-element
	 * character-frequency array and group words by their length.
	 * 
	 * For each query, I'll build its frequency array once. Then I'll start from the
	 * largest possible dictionary-word length and move downward. For each candidate
	 * word, I'll check whether every character count required by the word is less
	 * than or equal to the query count.
	 * 
	 * As soon as I find a valid word at some length, I can return it because all
	 * shorter lengths can no longer beat it.
	 * 
	 * This removes the exponential 2^Q behavior and makes query time proportional
	 * to the number of dictionary candidates we actually examine.
	 */
	

    /*
     * Follow-up 4 - Large Query of length 20, 30 or 50
     *
     * Interview script:
     * "Since subset generation becomes exponential for large queries,
     * I preprocess each dictionary word into a frequency array and
     * group words by length."
     */
    private final Map<Integer, List<WordInfo>> wordsByLength =
            new HashMap<>();

    // Follow-up 4 - Large Query of length 20, 30 or 50
    private int maxDictionaryWordLength = 0;

    /*
     * Follow-up 4 - Large Query of length 20, 30 or 50
     *
     * Stores the original word plus its character counts so we do not
     * recompute frequencies for every query.
     */
    private static class WordInfo {

        String word;
        int[] freq;

        WordInfo(String word, int[] freq) {
            this.word = word;
            this.freq = freq;
        }
    }

    /*
     * Follow-up 4 - Large Query of length 20, 30 or 50
     *
     * Interview script:
     * "I preprocess once because the dictionary is fixed.
     * Each word is grouped by length and gets a 26-character
     * frequency representation."
     */
    public LongestDictionaryWordFrom_9_Letters_FollowUp_4_QueryLenNotLimitedTo_9_Letters(
    		List<String> dictionary) {

        for (String word : dictionary) {

            int[] freq = buildFrequency(word);

            WordInfo info = new WordInfo(word, freq);

            wordsByLength
                    .computeIfAbsent(
                            word.length(),
                            k -> new ArrayList<>())
                    .add(info);

            maxDictionaryWordLength =
                    Math.max(
                            maxDictionaryWordLength,
                            word.length());
        }
    }

    /*
     * Follow-up 4 - Large Query of length 20, 30 or 50
     *
     * Interview script:
     * "Instead of generating every subset, I build the query's
     * frequency array once and search dictionary words from the
     * longest possible length downward."
     */
    public String findLongest(String letters) {

        if (letters == null || letters.length() == 0) {
            return "";
        }

        int[] queryFreq = buildFrequency(letters);

        int maxLen = Math.min(
                letters.length(),
                maxDictionaryWordLength
        );

        /*
         * Follow-up 4 - Large Query of length 20, 30 or 50
         *
         * Crucial logic:
         * Search longest lengths first.
         * The first valid word we find is automatically a longest word.
         */
        for (int len = maxLen; len >= 1; len--) {

            List<WordInfo> candidates =
                    wordsByLength.get(len);

            if (candidates == null) {
                continue;
            }

            for (WordInfo candidate : candidates) {

            	// A word is constructible only if, for every letter,
                // the word requires no more copies than the query provides.
                if (canForm(
                        candidate.freq,
                        queryFreq)) {

                    return candidate.word;
                }
            }
        }

        return "";
    }

    /*
     * Follow-up 4 - Large Query of length 20, 30 or 50
     *
     * Interview script:
     * "A word is constructible only if, for every letter,
     * the word requires no more copies than the query provides."
     */
    private boolean canForm(
            int[] wordFreq,
            int[] queryFreq) {

        for (int i = 0; i < 26; i++) {

            if (wordFreq[i] > queryFreq[i]) {
                return false;
            }
        }

        return true;
    }

    /*
     * Follow-up 4 - Large Query of length 20, 30 or 50
     *
     * Build the fixed-size character-count representation.
     */
    private int[] buildFrequency(String s) {

        int[] freq = new int[26];

        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        return freq;
    }


    
    public static void main(String[] args) {

		List<String> dictionary = Arrays.asList("cat", "act", "cart", "trace", "crate", "react", "cater", "create",
				"stone", "tones", "notes", "ones", "apple", "dog", "elephant", "algorithm", "character", "frequency");

		LongestDictionaryWordFrom_9_Letters_FollowUp_4_QueryLenNotLimitedTo_9_Letters solver 
		= new LongestDictionaryWordFrom_9_Letters_FollowUp_4_QueryLenNotLimitedTo_9_Letters(
				dictionary);

		/*
		 * Follow-up 4 - Large Query of length 20, 30 or 50
		 *
		 * Test larger query sizes instead of assuming exactly 9 letters.
		 */
		runTest(solver, "aaceertxyabcdefghijkl", 9);

		runTest(solver, "aenostxyzabcdefghijklmnopqrst", 5);

		runTest(solver, "aapplexyzabcdefghijklmnopqrst", 5);

		runTest(solver, "elephantxyzabcdefghijklmnopqrst", 8);

		runTest(solver, "bcfijkqxyzmnopqrstuvw", 0);
	}
	
	private static void runTest(LongestDictionaryWordFrom_9_Letters_FollowUp_4_QueryLenNotLimitedTo_9_Letters solver,
			String letters, int expectedLength) {

		String actual = solver.findLongest(letters);

		System.out.println("Query           : " + letters);

		System.out.println("Query length    : " + letters.length());

		System.out.println("Expected length : " + expectedLength);

		System.out.println("Actual          : " + actual);

		System.out.println("Actual length   : " + actual.length());

		System.out.println("PASS            : " + (actual.length() == expectedLength));

		System.out.println();
	}
	
	
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