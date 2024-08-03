package Square;

import java.util.Stack;

public class DesignATextEditor_2296_Hard {

	public static void main(String[] args) {
		TextEditor textEditor = new TextEditor(); // The current text is "|". (The '|' character represents the cursor)
	
		textEditor.addText("leetcode"); // The current text is "leetcode|".
		int pos = textEditor.deleteText(4); // return 4
		                          // The current text is "leet|". 
		                          // 4 characters were deleted.
		System.out.println("Expected: 4, Actual: " + pos);
		
		textEditor.addText("practice"); // The current text is "leetpractice|". 
		String str = textEditor.cursorRight(3); // return "etpractice"
		                           // The current text is "leetpractice|". 
		                           // The cursor cannot be moved beyond the actual text and thus did not move.
		                           // "etpractice" is the last 10 characters to the left of the cursor.
		System.out.println("Expected: etpractice, Actual: " + str);
		
		str = textEditor.cursorLeft(8); // return "leet"
		                          // The current text is "leet|practice".
		                          // "leet" is the last min(10, 4) = 4 characters to the left of the cursor.
		System.out.println("Expected: leet, Actual: " + str);
		
		
		pos = textEditor.deleteText(10); // return 4
		                           // The current text is "|practice".
		                           // Only 4 characters were deleted.
		System.out.println("Expected: 4, Actual: " + pos);
		
		str = textEditor.cursorLeft(2); // return ""
		                          // The current text is "|practice".
		                          // The cursor cannot be moved beyond the actual text and thus did not move. 
		                          // "" is the last min(10, 0) = 0 characters to the left of the cursor.
		System.out.println("Expected: EMPTY, Actual: " + str);
		
		str = textEditor.cursorRight(6); // return "practi"
		                           // The current text is "practi|ce".
		                           // "practi" is the last min(10, 6) = 6 characters to the left of the cursor.}
		System.out.println("Expected: practi, Actual: " + str);
		
		
	}
	private static class TextEditor {

	    /*
	    Time Complexity:
	        AddText(): O(n) where n is text length
	        deleteText(): O(k)
	        cursorRight(): O(k)
	        cursorLeft(): O(k)
	    */
	    
	    Stack<Character> left;
	    Stack<Character> right;
	    //Deque<Character> left;  // can be used as well
	    //Deque<Character> right;
	    
	    public TextEditor() {
	        left = new Stack<>();
	        right = new Stack<>();
	        //left = new ArrayDeque<>();
	        //right = new ArrayDeque<>();
	    }
	    
	    public void addText(String text) {
	        for(char c : text.toCharArray()) {
	            left.push(c);
	        }
	    }
	    
	    public int deleteText(int k) {
	        int count = 0;
	        while(!left.isEmpty() && k-- > 0) {
	            left.pop();
	            count++;
	        }
	        return count;
	    }
	    
	    public String cursorLeft(int k) {
	        //pop from left and push to right
	        while(!left.isEmpty() && k-- > 0) {
	            right.push(left.pop());
	        }
	        return getLeftString();
	    }
	    
	    public String cursorRight(int k) {
	        //pop from left and push to right
	        while(!right.isEmpty() && k-- > 0) {
	            left.push(right.pop());
	        }
	        return getLeftString();
	    }
	    
	    // Limiting to 10 char as per Leetcode problem
	    private String getLeftString() {
	        int count = 10;
	        StringBuilder sb = new StringBuilder();
	        
	        for(int i=left.size()-1; i>=0 && count-- > 0; i--) {
	            // return min(10, len) i.e. return max lenght of 10 and not more
	            sb.append(left.get(i));
	            //sb.insert(0, left.get(i)); // works, no need to use reverse with this
	        }
	        
	        return sb.reverse().toString();
	        // return sb.toString(); // use this with insert
	    }
	    
	    
	    /** Solution ends here but below are 2 different 
	     * variations only for getLeftString()*/ 
	    
	    // Limiting to 10 char as per Leetcode problem
	    // Can be used on interview
	    // Working but causing TLE in leetcode
	    private String getLeftString2() {
            StringBuilder sb = new StringBuilder();
            int count = 0;
            for (char ch : left) {
                if (count >= left.size() - 10) {
                    sb.append(ch);
                }
                count++;
            }
            return sb.toString();
        }
	    
	    /** without limiting to 10 characters  */
        // Time: O(L) time, where L is the size of the leftStack. 
	    // Space: O(L) space for the StringBuilder.
	    
	    // Note: One test is not passing but rest are
	    // Failing for: Expected: etpractice, Actual: leetpractice
	    private String getLeftString3() {
	        StringBuilder sb = new StringBuilder();
	        for (char ch : left) {
	            sb.append(ch);
	        }
	        return sb.toString();
	    }
	    
	}
	
}
