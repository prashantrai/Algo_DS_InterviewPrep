package Google;

import java.util.ArrayList;
import java.util.List;

public class GuessTheWord_843_Hard {

	public static void main(String[] args) {

	}
	

	/*
	Watch below only for of (minimax way):: 
	     *  c++ [See this exlaination to undertande 80% probablity part]: https://www.youtube.com/watch?v=5hWmbr62K10&ab_channel=ShivamPatel
		 * 	JavaScript: https://www.youtube.com/watch?v=emO9IbHcA9M&ab_channel=CatRacket
		 * 	Python: https://www.youtube.com/watch?v=KCWGtEcBN6c&ab_channel=HappyCoding
		 
	Approach::     
	     1. Create a data structure (a 2d array in this case) to map all the chars and their frequency for each word in list
		 2. Iterate the 2d array to find the word with max score (max score word here will be the word matching most of the chars among all the words). 
		    Why? because this word will be more closer to SECRET word than any other word in the list.

	https://leetcode.com/problems/guess-the-word/discuss/133862/Random-Guess-and-Minimax-Guess-with-Comparison


	Since the words are generated randomly. So a word most probably has 0 matches with the secret word(80% possibility). That's why we minimize the 0 matches rather than minimize the maximum matches.


	Time complexity O(N)
	Space complexity O(N)

	*/
	
	// https://leetcode.com/problems/guess-the-word/
	// https://leetcode.com/problems/guess-the-word/submissions/
	
	public void findSecretWord(String[] wordlist, Master master) {
        
        int x = 0;
        
        for(int t=0; t<10 && x<6; t++) {
            
            // create frequency array and capture freq of each char for all the words in the list            
            int[][] count = new int[6][26];
            
            for(String w : wordlist) {
                for (int i=0; i<6; i++) {
                    count[i][w.charAt(i) - 'a']++;                    
                }
            }
            
            // calculate best score
            String guess = wordlist[0];
            int bestScore = 0;
            for(String w : wordlist) {
                int score = 0;
                for (int i=0; i<6; i++) {
                    score += count[i][w.charAt(i) - 'a'];                    
                }
                if(score > bestScore) {
                    bestScore = score;
                    guess = w;
                }
            }
            
            // find the match
            x = master.guess(guess);
            List<String> wordlist2 = new ArrayList<>();
            for(String w : wordlist) {
                int matches = match(w, guess);
                if(matches == x) {
                    wordlist2.add(w);
                }
            }
            wordlist = wordlist2.toArray(new String[0]);
        } 
    }
    
    public int match(String s1, String s2) {
        int matches = 0;
        for(int i=0; i<Math.min(s1.length(), s2.length()); i++) {
            if(s1.charAt(i) == s2.charAt(i)) {
                matches++;
            }
        }
        return matches;
    }

}

/**
 * // This is the Master's API interface.
 * // You should not implement it, or speculate about its implementation
 * */
 interface Master {
      public int guess(String word);
 }
