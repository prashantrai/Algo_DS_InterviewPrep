package Lyft;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/*
 """
Given two words (beginWord and endWord), and a dictionary's word list, 
find the length of shortest transformation sequence from beginWord to endWord, such that:

Only one letter can be changed at a time.
Each transformed word must exist in the word list. Note that beginWord is not a transformed word.

Note:
Return -1 if there is no such transformation sequence.
All words in the dictionary contain only lowercase alphabetic characters.
Words in the dictionary may or may not be the same length.

"""

"""
wordList = ["hot","dot","dog","lot","log","cog","cat","hat"]

Input:
beginWord = "hot",
endWord = "dog",
Output: 2
Explanation: As one shortest transformation is "hot" -> "dot" -> "dog",
return its length 2.

Input:
beginWord = "cat",
endWord = "dog",
Output: 4
Explanation: As one shortest transformation is "cat" -> "hat" -> "hot" -> "dot" -> "dog",
return its length 4.

"""
 */

public class WordLadder_Lyft_127_Medium {
	public static void main(String[] args) {
		Set<String> dict = new HashSet<>(Arrays.asList("hot","dot","dog","lot","log","cog"));

		int res = findEndWord(dict, "hot", "dot");
		System.out.println("Expected: 2, Actual: " + res);
		
		Set<String> dict2 = new HashSet<>(Arrays.asList("hot","dot","dog"));	
		res = findEndWord(dict2, "hot", "dog");
		System.out.println("Expected: 3, Actual: " + res);
		
		Set<String> dict3 = new HashSet<>(Arrays.asList("hot","dot","dog","lot","log","cog"));
		res = findEndWord(dict3, "hit", "cog");
		System.out.println("Expected: 5, Actual: " + res);

		Set<String> dict4 = new HashSet<>(Arrays.asList("hot","dot","dog","lot","log"));
		res = findEndWord(dict4, "hit", "cog");
		System.out.println("Expected: 0, Actual: " + res);
		
	}
	
	
	 /*
    Time: O(M*N), where M is length of the word and N is total number of words in the input LIST. Finding out all the transformation takes M iterations for each N words.
    
    Check for sapce one more time:
    Space: O(M*N), to store all the transformation for each of N words.
    */
	
	// BFS - Working
	public static int findEndWord(Set<String> dict, String beginWord, String endWord) {

		// --sanity check
		//Set<String> dict = new HashSet<>(wordList);
        if (beginWord == null || endWord == null || beginWord.isEmpty() || endWord.isEmpty() || dict.isEmpty()) {
			return 0;
		}

		LinkedList<Word> queue = new LinkedList<>();
        queue.add(new Word(beginWord, 1));
 
        while(!queue.isEmpty()){
            Word top = queue.remove();
            String word = top.word;
 
            if(word.equals(endWord)){
                return top.step;
            }
 
            char[] arr = word.toCharArray();
            for(int i=0; i<arr.length; i++){
                for(char c='a'; c<='z'; c++){
                    char temp = arr[i];
                    if(arr[i]!=c){
                        arr[i]=c;
                    }
 
                    String newWord = new String(arr);
                    if(dict.contains(newWord)){
                        queue.add(new Word(newWord, top.step+1));
                        dict.remove(newWord);
                    }
 
                    arr[i]=temp;
                }
            }
        }
 
        return 0;

	}
	
	private static class Word {
		String word;
		int step=1; //set current distance to 1

		public Word(String w, int step) {
			this.word = w;
			this.step = step;
		}

	}

}
