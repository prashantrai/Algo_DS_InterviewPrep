package TikTok;

public class SentenceScreenFitting_418_Medium {

	public static void main(String[] args) {
		String[] sentence = {"hello","world"};
		int rows = 2; 
		int cols = 8;
		System.out.println("Expected: 1, Actual: " + wordsTyping(sentence, rows, cols));
		
		String[] sentence2 = {"a", "bcd", "e"};
		rows = 3; 
		cols = 6;
		System.out.println("Expected: 2, Actual: " + wordsTyping(sentence2, rows, cols));
		
		String[] sentence3 = {"i","had","apple","pie"};
		rows = 4; 
		cols = 5;
		System.out.println("Expected: 1, Actual: " + wordsTyping(sentence3, rows, cols));

	}
	
	/*
    Refer: https://www.youtube.com/watch?v=0TUjcxfYQ5I&ab_channel=codinginterviewquestions
    
    Time: O(N)
    Space: O(1)
    */
    
    public static int wordsTyping(String[] sentence, int rows, int cols) {
        
        // keep track of the result/count, if we reached the end of sentence and it fits
        int ans = 0;
        
        // index, to keep track of position in sentence array and reset this once 
        // we reach end to restart the same process
        int index = 0;
        
        //columns left in a row after adding a word. Reset it before moving to next row
        int colsLeft = cols;
        
        // while loop, to iterate over each row while we have row left with space (column)
        // to fit the complete word
        
        while (rows > 0 && colsLeft >= 0) {
            
            String word = sentence[index];
            
            // if word fits in the row
            if(word.length() <= colsLeft) {
                colsLeft -= word.length();
                index++; //move to next word
                
                // update colsLeft for 1 space between each word 
                if(colsLeft > 0) {
                    colsLeft--;
                }
            }
            else { 
                // decrent the row count (move to next row) 
                // because we started row from top/decreasing order
                rows--;  
                colsLeft = cols; // reset colsLeft to cols count
            }
            if(index == sentence.length) {
                // we finish sentence, increment count/ans
                ans++;
                index = 0; //reset index value to repeath the filling
            }
            
        }
        
        return ans;
    }

}
