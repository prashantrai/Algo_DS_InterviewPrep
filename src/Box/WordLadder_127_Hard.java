package Box;

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
    Time: O(M*N), where M is length of the word and N is total number of words in the input LIST. Finding out all the transformation takes M iterations for each N words.
    
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

	
	// Working: Leet code solutions (mine)
	/*
    Time: O(M*N), where M is length of the word and N is total number of words in the input LIST. Finding out all the transformation takes M iterations for each N words.
    
    Check for sapce one more time:
    Space: O(M*N), to store all the transformation for each of N words.
    */
    
    /*public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> dict = new HashSet<>(wordList);
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
                    if(arr[i] != c) {
                        arr[i] = c;
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
		int step;

		public Word(String w, int step) {
			this.word = w;
			this.step = step;
		}

	}*/
	
}
