package LinkedIn;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class WordLadder_127_Hard {

	public static void main(String[] args) {
		//List<String> dict = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog", "cat", "hat");
		
		List<String> dict = Arrays.asList("hot","dot","dog","lot","log","cog");
		
		int res = ladderLength("hit", "cog", dict);
		System.out.println("Expected: 5, Actual: " + res);
		
		
		dict = Arrays.asList("hot","dot","dog","lot","log");
		
		int res2 = ladderLength("hit", "cog", dict);
		System.out.println("Expected: 0, Actual: " + res2);

		int res3 = ladderLength("cat", "", dict);
		System.out.println("Expected: 0, Actual: " + res3);

		int res4 = ladderLength("cat", "xxx", dict);
		System.out.println("Expected: 0, Actual: " + res4);
	}
	
	/* Working Solution: 
	 * 
    Time: O(M* (N*N)), where 
    M -> is length of the word 
    N*N -> 1. N is total number of words in the input LIST. Finding out all the transformation takes M iterations for each N words.
           2. Generating a new String from char[] takes O(N)
    
    
    Check for sapce one more time:
    Space: O(M*N), to store all the transformation for each of N words.
    */
	
	public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
	
		Set<String> dict = new HashSet<>(wordList);
		
		if(beginWord == null || endWord == null || !dict.contains(endWord) || dict.isEmpty()) 
			return 0;
		
		Queue<Word> q = new ArrayDeque<>();
		q.offer(new Word(beginWord, 1));
		
		while(!q.isEmpty()) {
			Word top = q.poll();
			String curr = top.word;
			
			if(curr.equals(endWord)) { 
				return top.step; 
			}
			
			char[] arr = curr.toCharArray();
			for(int i=0; i<arr.length; i++) {
				for(char c='a'; c<='z'; c++) {
					
					if(arr[i] == c) continue;
					
					char temp = arr[i];
					arr[i] = c;
					
					String newWord = new String(arr);
					if(dict.contains(newWord)) {
						q.offer(new Word(newWord, top.step+1));
						dict.remove(newWord); // dict also works as visited set, to avoid infinite loop
					}
					
					arr[i] = temp;
				}
			}
			
		}
		
		return 0;
	}
	
	private static class Word {
		String word;
		int step;
		
		public Word(String word, int step) {
			this.word = word;
			this.step = step;
		}
	}
	
	
	// Bi-Directional bfs - working solution

	public int ladderLength_BiDirectionalBFS(String beginWord, String endWord, List<String> wordList) {

		Set<String> dict = new HashSet<>(wordList);

        if(beginWord == null || endWord == null || dict.isEmpty() || !dict.contains(endWord)) {
            return 0;
        }

        // Two sets for bidirectional BFS
        Set<String> beginSet = new HashSet<>(); 
        Set<String> endSet = new HashSet<>(); 
        beginSet.add(beginWord);
        endSet.add(endWord);
        dict.remove(beginWord);
        dict.remove(endWord);

        int steps = 1;

        while (!beginSet.isEmpty() && !endSet.isEmpty()) {
            // Always expand the smaller set first (optimization)
            if(beginSet.size() > endSet.size()) {
                Set<String> temp = beginSet;
                beginSet = endSet;
                endSet = temp;
            }

            Set<String> nextLevel = new HashSet<>();
            for(String word : beginSet) {
                char[] arr = word.toCharArray();
                for(int i=0; i<arr.length; i++) {
                    char original = arr[i];
                    for(char c='a'; c<='z'; c++) {
                        arr[i] = c;
                        String newWord = new String(arr);
                        
                        if(endSet.contains(newWord)) {
                            return steps + 1; // Found connection/path
                        }

                        if(dict.contains(newWord)) {
                            nextLevel.add(newWord);
                            dict.remove(newWord); // mark as visited
                        }
                    }
                    // revert changes
                    arr[i] = original;
                }
            }
            beginSet = nextLevel;
            steps++;
        }
        return 0; // no path found

	}
	
}

/*
Time Complexity:
The time complexity of this solution can be analyzed as follows:

Initial Setup:
Converting the wordList into a HashSet takes O(N), where N is the number of words in wordList.

BFS Traversal:

In the worst case, every word in wordList is processed once. This gives O(N) operations.

For each word, we generate all possible transformations by changing each character to every other lowercase letter (a-z).

	- Each word has length L, and for each position, we try 26 possibilities (a-z).

	- Thus, generating all transformations for a word takes O(L × 26) = O(L) (since 26 is a constant).

Checking if the transformed word exists in the dict (HashSet) is O(1) on average.

Therefore, processing all transformations for all words takes O(N × L).


Overall Time Complexity:

The dominant factor is the BFS traversal, so the total time complexity is O(N × L).

Space Complexity:
The space complexity is determined by the following:

HashSet for Dictionary:

Storing the dictionary takes O(N × L) space (since each word has length L and there are N words).

Queue for BFS:

In the worst case, the queue can hold all N words, each with their step count. This takes O(N × L) space (since each word is of length L).

Temporary Variables:

The char[] arr used for transformation takes O(L) space, but this is negligible compared to the other components.

Overall Space Complexity:

The dominant factors are the HashSet and the queue, so the total space complexity is O(N × L).

Summary:
Time Complexity: O(N × L)

Space Complexity: O(N × L)

Where:

N = number of words in wordList.

L = length of each word (assuming all words are of the same length).
 * */
